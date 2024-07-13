import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './EditSpecialisation.css';  
import api from './api';

const EditSpecialisation = () => {
    const { specialisationId } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        code: '',
        name: '',
        description: '',
        year: '',
        credits_required: ''
    });
    const [error, setError] = useState('');

    useEffect(() => {
        fetchSpecialisationDetails();
    }, [specialisationId]);

    const fetchSpecialisationDetails = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/viewSpecialisationById/${specialisationId}`);
            setFormData(response.data);
        } catch (err) {
            if (err.response && err.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchSpecialisationDetails();
                } else {
                    navigate('/login');
                }
            } else {
                setError('Failed to fetch specialisation details');
                console.error('Error fetching specialisation details:', err);
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.put(`http://localhost:8090/admin/editSpecialisation/${specialisationId}`, formData);
            alert("Specialisation updated successfully");
            navigate(`/viewSpecialisationId/${specialisationId}`);
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
                setError('Error updating specialisation');
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
        <div className="edit-specialisation-container">
            <AdminSidebar />
            <form className="specialisation-form" onSubmit={handleSubmit}>
                <h2>Edit Specialisation</h2>
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

                <button type="submit" className="submit-btn">Update</button>
            </form>
        </div>
    );
};

export default EditSpecialisation;
