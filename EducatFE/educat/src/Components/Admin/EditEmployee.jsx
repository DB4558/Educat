import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './EditEmployee.css';  
import api from './api';

const EditEmployee = () => {
    const { employeeId } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        title: '',
        fname: '',
        lname: '',
        departmentId: ''
    });
    const [departments, setDepartments] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        fetchEmployeeDetails();
        fetchDepartments();
    }, [employeeId]);

    const fetchEmployeeDetails = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/viewEmployeeById/${employeeId}`);
            setFormData({
                ...response.data,
                departmentId: response.data.departments.departmentId
            });
        } catch (err) {
            if (err.response && err.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchEmployeeDetails();
                } else {
                    navigate('/login');
                }
            } else {
                setError('Failed to fetch employee details');
                console.error('Error fetching employee details:', err);
            }
        }
    };

    const fetchDepartments = async () => {
        try {
            const response = await api.get('http://localhost:8090/admin/viewDepartments');
            setDepartments(response.data);
        } catch (error) {
            console.error('Error fetching departments:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const updatedData = {
            title: formData.title,
            fname: formData.fname,
            lname: formData.lname,
            departments: { departmentId: formData.departmentId }
        };
        try {
            const response = await api.put(`http://localhost:8090/admin/editEmployee/${employeeId}`, updatedData);
            alert("Employee updated successfully");
            navigate('/viewEmployee');
        } catch (error) {
            if (error.response && error.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    handleSubmit(e);  
                } else {
                    navigate('/login');
                }
            } else {
                setError('Error updating employee');
                console.error('Error submitting form:', error);
            }
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const getNewAccessToken = async () => {
        try {
            const response = await api.post('http://localhost:8090/admin/refresh-token', {}, {
                withCredentials: true
            });
            return response.data.accesstoken;
        } catch (error) {
            console.error('Unable to refresh token');
            return null;
        }
    };

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className="edit-employee-container">
            <AdminSidebar />
            <form className="employee-form" onSubmit={handleSubmit}>
                <h2>Edit Employee</h2>
                <label htmlFor="title">Title:</label>
                <input type="text" id="title" name="title" value={formData.title} onChange={handleChange} required />

                <label htmlFor="firstName">First Name:</label>
                <input type="text" id="firstName" name="fname" value={formData.fname} onChange={handleChange} required />

                <label htmlFor="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lname" value={formData.lname} onChange={handleChange} required />

                <label htmlFor="departmentId">Department:</label>
                <select id="departmentId" name="departmentId" value={formData.departmentId} onChange={handleChange} required>
                    {departments.map(department => (
                        <option key={department.departmentId} value={department.departmentId}>
                            {department.name}
                        </option>
                    ))}
                </select>

                <button type="submit" className="submit-btn">Update</button>
            </form>
        </div>
    );
};

export default EditEmployee;
