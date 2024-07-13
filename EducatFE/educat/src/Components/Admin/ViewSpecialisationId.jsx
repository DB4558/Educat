import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import axios from 'axios';
import './ViewSpecialisationId.css';
import api from './api';

const ViewSpecialisationId = () => {
    const { specialisationId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const [specialisationData, setSpecialisationData] = useState({code:'', name: '', description: '', year: '' ,credits_required:''});
    const [error, setError] = useState('');

    const fetchSpecialisationDetails = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/viewSpecialisationById/${specialisationId}`);
            setSpecialisationData(response.data);
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
                setError('Failed to fetch specialisation details.');
            }
        }
    };

    

    const getNewAccessToken = async () => {
        try {
            const response = await axios.post('http://localhost:8090/admin/refresh-token', {}, { withCredentials: true });
            return response.data.accesstoken;
        } catch (error) {
            console.error('Unable to refresh token');
            return null;
        }
    };

    useEffect(() => {
        fetchSpecialisationDetails();
    }, [location]);

    return (
        <div className="main-content">
            <AdminSidebar />
            <div className="specialisation-card">
                <h2>Specialisation Details</h2>
                {error ? <p>{error}</p> : (
                    <div>
                         <p><strong>Code:</strong> {specialisationData.code}</p>
                        <p><strong>Name:</strong> {specialisationData.name}</p>
                        <p><strong>Description:</strong> {specialisationData.description}</p>
                        <p><strong>Year:</strong> {specialisationData.year}</p>
                        <p><strong>Credits Required</strong> {specialisationData.credits_required}</p>
                    </div>
                )}
            </div>
            <div className="specialisation-actions">
                <button className='action-btn-edit' onClick={() => navigate(`/editSpecialisation/${specialisationId}`)}>Edit Specialisation</button>
                <button className='action-btn-student' onClick={() => navigate(`/viewSpecialisationStudent/${specialisationId}`)}>View Students</button>
                <button className='action-btn-course' onClick={() => navigate(`/viewSpecialisationCourse/${specialisationId}`)}>View Courses</button>
            </div>
        </div>
    );
};

export default ViewSpecialisationId;
