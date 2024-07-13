import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './AddCourse.css';  
import api from './api';

const AddCourse = () => {
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
            
            const response = await api.post('/addCourse', formData);
            alert("Course added successfully");
            navigate('/admin/courses'); 
        } catch (error) {
            if (error.response && error.response.status === 401) {
               
                    alert("Session Expired!! Login again")
                    navigate('/');
                
            } else {
                console.error('Error submitting form:', error);
            }
        }
    };

   

    return (
        <div className="add-course-container">
            <AdminSidebar />
            <form className="course-form" onSubmit={handleSubmit}>
                <h2>Add Course</h2>
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

                <button type="submit" className="submit-btn">Submit</button>
            </form>
        </div>
    );
};

export default AddCourse;
