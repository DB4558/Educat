import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate,useLocation } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import api from './api'; 
import './AddEmployee.css';

const AddEmployee = () => {
    const [departments, setDepartments] = useState([]);
    const [employees,setEmployees]=useState([]);
    const [formData, setFormData] = useState({
        title: '',
        fname: '',
        lname: '',
        departmentId: ''
    });
    const navigate = useNavigate();
    const location=useLocation();


    const fetchDepartments = async () => {
        try {
            const response = await api.get('http://localhost:8090/admin/viewDepartments');
            setDepartments(response.data);
        } catch (error) {
            console.error('Error fetching departments:', error);
        }
    };
    const fetchEmployees = async () => {
        try {
            const response = await api.get('http://localhost:8090/admin/viewEmployee');
            setEmployees(response.data);
        } catch (error) {
            console.error('Error fetching departments:', error);
        }
    };


    useEffect(() => {

        fetchDepartments();
        const intervalId = setInterval(fetchDepartments, 60000); 
      
        return () => clearInterval(intervalId); 
      }, [location]);

      useEffect(() => {

        fetchEmployees();
      }, []);
      

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post(`http://localhost:8090/admin/addEmployee/${formData.departmentId}`, formData);
            alert("Employee added successfully");
            setFormData({
                title: '',
                fname: '',
                lname: '',
                departmentId: ''
            });
            navigate('/addEmployee'); 
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    const handleView = () => {
        navigate('/viewEmployee');
    };


    return (
        <div className="add-employee-container">
            <AdminSidebar />
            <form className="employee-form" onSubmit={handleSubmit}>
                <h2>Add Employee</h2>

                <label>Title:</label>
                <input type="text" name="title" value={formData.title} onChange={handleChange} required />

                <label>First Name:</label>
                <input type="text" name="fname" value={formData.fname} onChange={handleChange} required />

                <label>Last Name:</label>
                <input type="text" name="lname" value={formData.lname} onChange={handleChange} required />
                
                <label htmlFor="department">Select Department:</label>
                <select id="department" name="departmentId" value={formData.departmentId} onChange={handleChange} required>
                    <option value="">Select a department</option>
                    {departments.map(department => (
                        <option key={department.departmentId} value={department.departmentId}>
                            {department.name}
                        </option>
                    ))}
                </select>

                <button type="submit" className="submit-btn">Submit</button>
                {employees.length >0 ? (<button type="button" onClick={handleView} className="view-btn">View</button>): null}
            </form>
        </div>
    );
};

export default AddEmployee;
