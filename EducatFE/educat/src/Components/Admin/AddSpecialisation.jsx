import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './AddSpecialisation.css'; 
import api from './api';

const AddSpecialisation = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        code: '',
        name: '',
        description: '',
        year: '',
        credits_required: ''
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
            const response = await api.post('http://localhost:8090/admin/addSpecialisation', formData
                );
            alert("Specialisation added successfully");
            navigate('/admin/specialisations'); 
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
        <div className="add-specialisation-container">
            <AdminSidebar />
            <form className="specialisation-form" onSubmit={handleSubmit}>
                <h2>Add Specialisation</h2>
                <label htmlFor="code">Code:</label>
                <input type="text" id="code" name="code" value={formData.code} onChange={handleChange} required />

                <label htmlFor="name">Name:</label>
                <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />

                <label htmlFor="description">Description:</label>
                <textarea id="description" name="description" value={formData.description} onChange={handleChange} required />

                <label htmlFor="year">Year:</label>
                <input type="number" id="year" name="year" value={formData.year} onChange={handleChange} required />

                <label htmlFor="credits_required">Credits Required:</label>
                <input type="number" id="credits_required" name="credits_required" value={formData.credits_required} onChange={handleChange} required />

                <button type="submit" className="submit-btn">Submit</button>
            </form>
        </div>
    );
};

export default AddSpecialisation;
