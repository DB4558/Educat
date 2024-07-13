import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './EditStudent.css';  
import api from './api';

const EditStudent = () => {
    const { studentId } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        first_name: '',
        last_name: '',
        total_credits: '',
        graduation_year: '',
        domainId: ''
    });
    const [domains, setDomains] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        fetchStudentDetails();
        fetchDomains();
    }, [studentId]);

    const fetchStudentDetails = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/viewStudentById/${studentId}`);
            setFormData({
                ...response.data,
                domainId: response.data.domain.domainId 
            });
        } catch (err) {
            if (err.response && err.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchStudentDetails();  
                } else {
                    navigate('/login');
                }
            } else {
                setError('Failed to fetch student details');
                console.error('Error fetching student details:', err);
            }
        }
    };

    const fetchDomains = async () => {
        try {
            const response = await api.get('http://localhost:8090/admin/viewDomainList');
            setDomains(response.data);
        } catch (error) {
            console.error('Error fetching domains:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const updatedData = {
            first_name:formData.first_name,
            last_name:formData.last_name,
            cgpa:formData.cgpa,
            total_credits:formData.total_credits,
            graduation_year:formData.graduation_year,
            domain: { domainId: formData.domainId }
        };
        try {
            const response = await api.put(`http://localhost:8090/admin/editStudent/${studentId}`, updatedData);
            alert("Student updated successfully");
            navigate('/viewStudent');
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
                setError('Error updating student');
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
        <div className="edit-student-container">
            <AdminSidebar />
            <form className="student-form" onSubmit={handleSubmit}>
                <h2>Edit Student</h2>
                <label htmlFor="firstName">First Name:</label>
                <input type="text" id="firstName" name="first_name" value={formData.first_name} onChange={handleChange} required />

                <label htmlFor="lastName">Last Name:</label>
                <input type="text" id="lastName" name="last_name" value={formData.last_name} onChange={handleChange} required />

                <label htmlFor="totalCredits">Total Credits:</label>
                <input type="number" id="totalCredits" name="total_credits" value={formData.total_credits} onChange={handleChange} required />

                <label htmlFor="graduationYear">Graduation Year:</label>
                <input type="number" id="graduation_year" name="graduationYear" value={formData.graduation_year} onChange={handleChange} required />

                <label htmlFor="domainId">Domain:</label>
                <select id="domainId" name="domainId" value={formData.domainId} onChange={handleChange} required>
                    {domains.map(domain => (
                        <option key={domain.domainId} value={domain.domainId}>
                            {domain.program} 
                        </option>
                    ))}
                </select>

                <button type="submit" className="submit-btn">Update</button>
            </form>
        </div>
    );
};

export default EditStudent;
