import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewSpecialisation.css'; 
import api from './api';

const ViewSpecialisations = () => {
    const [specialisations, setSpecialisations] = useState([]);
    const navigate = useNavigate();
    const location=useLocation();

    useEffect(() => {

        fetchSpecialisations();
        const intervalId = setInterval(fetchSpecialisations, 60000); 
      
        return () => clearInterval(intervalId); 
      }, [location]);
      

    const fetchSpecialisations = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get('http://localhost:8090/admin/viewSpecialisation'
               );
            setSpecialisations(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchSpecialisations();
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error fetching specialisations:', error);
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
        <div className="view-specialisations-container">
            <AdminSidebar />
            <div className="specialisations-table-container">
                <h2>Specialisation List</h2>
                <table className="specialisations-table">
                    <thead>
                        <tr>
                            <th>Code</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Year</th>
                            <th>Credits Required</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {specialisations.map(specialisation => (
                            <tr key={specialisation.specialisationId}>
                                <td>{specialisation.code}</td>
                                <td>{specialisation.name}</td>
                                <td>{specialisation.description}</td>
                                <td>{specialisation.year}</td>
                                <td>{specialisation.credits_required}</td>
                                <td><button onClick={() => navigate(`/viewSpecialisationId/${specialisation.specialisationId}`)}>Click Here</button></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ViewSpecialisations;
