import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './EditDomain.css';
import api from './api';

const EditDomain = () => {
    const { domainId } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        program: '',
        batch: '',
        capacity: ''
    });
    const [error, setError] = useState('');

    useEffect(() => {
        fetchDomainDetails();
    }, [domainId]);

    const fetchDomainDetails = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/viewDomainById/${domainId}`);
            setFormData(response.data);
        } catch (err) {
            if (err.response && err.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchDomainDetails();  
                } else {
                    navigate('/login');
                }
            } else {
                setError('Failed to fetch domain details');
                console.error('Error fetching domain details:', err);
            }
        }
    };
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.put(`http://localhost:8090/admin/modifyDomain/${domainId}`, formData);
            alert("Domain updated successfully");
            navigate(`/viewDomainId/${domainId}`);
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
                setError('Error updating domain');
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
        <div className="edit-domain-container">
            <AdminSidebar />
            <form className="domain-form" onSubmit={handleSubmit}>
                <h2>Edit Domain</h2>
                <label htmlFor="program">Program:</label>
                <input type="text" id="program" name="program" value={formData.program} onChange={handleChange} required />

                <label htmlFor="batch">Batch:</label>
                <input type="text" id="batch" name="batch" value={formData.batch} onChange={handleChange} required />

                <label htmlFor="capacity">Capacity:</label>
                <input type="number" id="capacity" name="capacity" value={formData.capacity} onChange={handleChange} required />

                <button type="submit" className="submit-btn">Update</button>
            </form>
        </div>
    );
};

export default EditDomain;
