
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useEmail } from '../../EmailContext';
import './AdminHome.css';
import { useNavigate,useLocation } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import api from './api';

const AdminHome = () => {
    const { email } = useEmail();
    const location=useLocation(); 
    const [adminData, setAdminData] = useState({ fname: '', lname: '', email: '' });
    const [error, setError] = useState('');
    const [counts, setCounts] = useState({ courses: 0, students: 0, employees: 0, domains: 0, specialisations: 0 });
    const navigate=useNavigate();

      const fetchCounts = async () => {
        try {
          const response = await api.get('/counts');
          setCounts(response.data);
        } catch (err) {
          if (err.response && err.response.status === 401) {
              setError('Session expired. Please log in again.');
              alert('Session expired. Please log in again.');
              navigate('/');
            }
          else {
            setError('Failed to fetch counts.');
          }
        }
      };
      
      useEffect(() => {

        fetchCounts();
        const intervalId = setInterval(fetchCounts, 60000); 
      
        return () => clearInterval(intervalId); 
      }, [location]);
      
      

    useEffect(() => {
        const fetchAdminData = async () => {
            try {
                const response = await api.get(`/viewAdmin/${email}`);
                setAdminData(response.data);
            } catch (err) {
                if (err.response && err.response.status === 401) {
                        setError('Session expired. Please log in again.');
                        alert('Session expired. Please log in again.');
                        navigate('/');
                    
                } else {
                    setError('Failed to fetch admin data.');
                }
            }
        };

            fetchAdminData();
        
    }, [location]);

    return (
        <>
        <div className="main-content">
        <AdminSidebar/>
        <div className="admin-card">
            <h2>Admin Details</h2>
            {error ? <p>{error}</p> : (
                <div>
                    <p><strong>First Name:</strong> {adminData.fname}</p>
                    <p><strong>Last Name:</strong> {adminData.lname}</p>
                    <p><strong>Email:</strong> {adminData.email}</p>
                </div>
            )}
        </div>
         <div className="counts-container">
         <button className="count-btn-courses">{`Courses: ${counts.courses}`}</button>
         <button className="count-btn-students">{`Students: ${counts.students}`}</button>
         <button className="count-btn-employees">{`Employees: ${counts.employees}`}</button>
         <button className="count-btn-domains">{`Domains: ${counts.domains}`}</button>
         <button className="count-btn-specializations">{`Specializations: ${counts.specialisations}`}</button>
       </div>
       </div>
       </>
    );
};

export default AdminHome;
