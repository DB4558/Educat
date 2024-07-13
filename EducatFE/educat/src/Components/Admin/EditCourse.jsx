import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './EditCourse.css';  
import api from './api';

const EditCourse = () => {
    const { courseId } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        courseCode: '',
        name: '',
        description: '',
        year: '',
        term: '',
        capacity: '',
        credits: ''
    });
    const [error, setError] = useState('');

    useEffect(() => {
        fetchCourseDetails();
    }, [courseId]);

    const fetchCourseDetails = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/viewCoursesByid/${courseId}`
              );
            setFormData(response.data);
        } catch (err) {
            if (err.response && err.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchCourseDetails();  // Retry fetching course details with new token
                } else {
                    navigate('/login');
                }
            } else {
                setError('Failed to fetch course details');
                console.error('Error fetching course details:', err);
            }
        }
    };
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.put(`http://localhost:8090/admin/editCourse/${courseId}`, formData
               );
            alert("Course updated successfully");
            navigate(`/viewCourseId/${courseId}`);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    handleSubmit(e);  // Retry submission with new token
                } else {
                    navigate('/login');
                }
            } else {
                setError('Error updating course');
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
        <div className="edit-course-container">
            <AdminSidebar />
            <form className="course-form" onSubmit={handleSubmit}>
                <h2>Edit Course</h2>
                <label htmlFor="courseCode">Course Code:</label>
                <input type="text" id="courseCode" name="courseCode" value={formData.courseCode} onChange={handleChange} required />

                <label htmlFor="name">Name:</label>
                <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />

                <label htmlFor="description">Description:</label>
                <textarea id="description" name="description" value={formData.description} onChange={handleChange} required />

                <label htmlFor="year">Year:</label>
                <input type="number" id="year" name="year" value={formData.year} onChange={handleChange} required />

                <label htmlFor="term">Term:</label>
                <input type="text" id="term" name="term" value={formData.term} onChange={handleChange} required />

                <label htmlFor="capacity">Capacity:</label>
                <input type="number" id="capacity" name="capacity" value={formData.capacity} onChange={handleChange} required />

                <label htmlFor="credits">Credits:</label>
                <input type="number" id="credits" name="credits" value={formData.credits} onChange={handleChange} required />

                <button type="submit" className="submit-btn">Update</button>
            </form>
        </div>
    );
};

export default EditCourse;
