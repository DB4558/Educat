import React, { useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate} from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './AddDomain.css';
import api from './api';

const AddDomain = () => {
    const navigate = useNavigate();
    const location=useLocation();
    const [formData, setFormData] = useState({
        program: '',
        batch: '',
        capacity: ''
    });

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
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.post('http://localhost:8090/admin/addDomain', formData);
            alert("Domain Addded successfully");
            navigate('/admin/domains'); 
        } catch (error) {
            if (error.response && error.response.status === 401) {
                
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    handleSubmit(e); 
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error submitting form:', error);
            }
        }
    };

    const getNewAccessToken = async () => {
        try {
            const response = await axios.post('http://localhost:8090/admin/refresh-token', {}, {
                withCredentials: true
            });
            return response.data.accesstoken;
        } catch (error) {
            throw new Error('Unable to refresh token');
        }
    };

    return (
        <div className="add-domain-container">
            <AdminSidebar />
            <form className="domain-form" onSubmit={handleSubmit}>
                <h2>Add Domain</h2>
                <label htmlFor="program">Program:</label>
                <input type="text" id="program" name="program" value={formData.program} onChange={handleChange} required />

                <label htmlFor="batch">Batch:</label>
                <input type="text" id="batch" name="batch" value={formData.batch} onChange={handleChange} required />

                <label htmlFor="capacity">Capacity:</label>
                <input type="number" id="capacity" name="capacity" value={formData.capacity} onChange={handleChange} required />

                <button type="submit" className="submit-btn">Submit</button>
            </form>
        </div>
    );
};

export default AddDomain;
