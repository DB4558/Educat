import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewDomainId.css';
import api from './api';

const ViewDomainId = () => {
    const { domainId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const [domainData, setDomainData] = useState({ program: '', batch: '', capacity: '' });
    const [error, setError] = useState('');

    const fetchDomainDetails = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/viewDomainById/${domainId}`);
            setDomainData(response.data);
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
                setError('Failed to fetch domain details.');
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
        fetchDomainDetails();
    }, [location]);

    return (
        <div className="main-content">
            <AdminSidebar />
            <div className="domain-card">
                <h2>Domain Details</h2>
                {error ? <p>{error}</p> : (
                    <div>
                        <p><strong>Program:</strong> {domainData.program}</p>
                        <p><strong>Batch:</strong> {domainData.batch}</p>
                        <p><strong>Capacity:</strong> {domainData.capacity}</p>
                    </div>
                )}
            </div>
            <div className="domain-actions">
                <button className='action-btn-edit' onClick={() => navigate(`/editDomain/${domainId}`)}>Edit Domain</button>
                <button className='action-btn-student' onClick={() => navigate(`/viewDomainStudent/${domainId}`)}>View Students</button>
                <button className='action-btn-course' onClick={() => navigate(`/viewDomainCourse/${domainId}`)}>View Courses</button>
                <button className='action-btn-timetable' onClick={() => navigate(`/viewDomainTimeTable/${domainId}`)}>View Timetable</button>
            </div>
        </div>
    );
};

export default ViewDomainId;
