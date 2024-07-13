import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './EditDepartment.css';  
import api from './api';

const EditDepartment = () => {
    const { departmentId } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: '',
        capacity: ''
    });
    const [error, setError] = useState('');

    useEffect(() => {
        fetchDepartmentDetails();
    }, [departmentId]);

    const fetchDepartmentDetails = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/viewDepartmentById/${departmentId}`);
            setFormData(response.data);
        } catch (err) {
            if (err.response && err.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchDepartmentDetails();  
                } else {
                    navigate('/login');
                }
            } else {
                setError('Failed to fetch department details');
                console.error('Error fetching department details:', err);
            }
        }
    };
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.put(`http://localhost:8090/admin/editDepartment/${departmentId}`, formData);
            alert("Department updated successfully");
            navigate(`/viewDepartment`); 
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
                setError('Error updating department');
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
            const response = await axios.post('http://localhost:8090/admin/refresh-token', {}, {
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
        <div className="edit-department-container">
            <AdminSidebar />
            <form className="department-form" onSubmit={handleSubmit}>
                <h2>Edit Department</h2>
                <label htmlFor="name">Name:</label>
                <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />

                <label htmlFor="capacity">Capacity:</label>
                <input type="number" id="capacity" name="capacity" value={formData.capacity} onChange={handleChange} required />

                <button type="submit" className="submit-btn">Update</button>
            </form>
        </div>
    );
};

export default EditDepartment;
