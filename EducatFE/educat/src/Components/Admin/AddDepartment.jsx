import React, { useState ,useEffect} from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './AddDepartment.css';
import api from './api';

const AddDepartment = () => {
    const navigate = useNavigate();
    const [departments,setDepartment]=useState([]);
    const location=useLocation();
    const [formData, setFormData] = useState({
        name: '',
        capacity: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    useEffect(() => {

        fetchDepartment();
        const intervalId = setInterval(fetchDepartment, 60000); 
      
        return () => clearInterval(intervalId); 
      }, [location]);
      

    const fetchDepartment = async () => {
        try {
            
            const response = await api.get('/viewDepartments');
            setDepartment(response.data); 
        } catch (error) {
            if (error.response && error.response.status === 401) {
               
                    alert("Session Expired,Login again");
                    navigate('/login');
                
            } else {
                console.error('Error fetching department:', error);
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
        
            const response = await api.post('/addDepartment', formData);
            alert("Department added successfully");
            setFormData({
                name: '',
                capacity: ''
            });
            navigate('/addDepartment');
        } catch (error) {
            if (error.response && error.response.status === 401) {
               
                    alert("Session Expired,Login again");
                    navigate('/login');
                
            } else {
                console.error('Error fetching department:', error);
            }
        }
    };

   

    const handleView = () => {
        navigate('/viewDepartment');
    };

    return (
        <div className="add-department-container">
            <AdminSidebar />
            <form className="department-form" onSubmit={handleSubmit}>
                <h2>Add Department</h2>
                <label htmlFor="name">Name:</label>
                <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />

                <label htmlFor="capacity">Capacity:</label>
                <input type="number" id="capacity" name="capacity" value={formData.capacity} onChange={handleChange} required />

                <button type="submit" className="submit-btn">Submit</button>
                {departments.length >0 ? (<button type="button" onClick={handleView} className="view-btn">View</button>): null}
            </form>
        </div>
    );
};

export default AddDepartment;
