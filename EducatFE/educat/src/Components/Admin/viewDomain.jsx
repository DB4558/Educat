import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewDomains.css';
import api from './api';

const ViewDomains = () => {
    const [domains, setDomains] = useState([]);
    const navigate = useNavigate();
    const location=useLocation();

    useEffect(() => {
        fetchDomains();
        const intervalId = setInterval(fetchDomains, 60000);

        return () => clearInterval(intervalId);
    }, [location]);

    const fetchDomains = async () => {
        try {
            
            const response = await api.get('/viewDomainList');
            setDomains(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
               
                    
                    navigate('/login');
                
            } else {
                console.error('Error fetching domains:', error);
            }
        }
    };

   

    const handleBack = () => {
        navigate(`/admin/domains`);
    };
    

    return (
        <div className="view-domains-container">
            <AdminSidebar />
            <div className="domains-table-container">
            <h2>Domain List</h2>
                <table className="domains-table">
                    <thead>
                        <tr>
                            <th>Program</th>
                            <th>Batch</th>
                            <th>Capacity</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {domains.map(domain => (
                            <tr key={domain.domainId}>
                                <td>{domain.program}</td>
                                <td>{domain.batch}</td>
                                <td>{domain.capacity}</td>
                                <td><button onClick={() => navigate(`/viewDomainId/${domain.domainId}`)}>Click Here</button></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <button onClick={handleBack} className="back-btn">Back to Domain</button>
        </div>
    );
};

export default ViewDomains;
