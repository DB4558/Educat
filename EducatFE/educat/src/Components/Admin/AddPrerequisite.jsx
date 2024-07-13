import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import api from './api'; 
import './AddPrerequisite.css';

const AddPrerequisite = () => {
    const { courseId ,courseCode} = useParams();
    const [courses, setCourses] = useState([]);
    const [prerequisite, setPrerequisite] = useState([]);
    const [formData, setFormData] = useState({
        description: '',
        prerequisiteId: ''
    });
    const navigate = useNavigate();

    useEffect(() => {
        fetchCourses();
        fetchPrerequisite();
    }, []);

    const fetchCourses = async () => {
        try {
            const response = await api.get('http://localhost:8090/admin/viewCourseList');
            setCourses(response.data);
        } catch (error) {
            console.error('Error fetching courses:', error);
        }
    };

    const fetchPrerequisite = async () => {
        try {
            const response = await api.get(`http://localhost:8090/admin/viewPrerequisiteList/${courseId}`);
            setPrerequisite(response.data);
        } catch (error) {
            console.error('Error fetching courses:', error);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleView = () => {
        navigate(`/viewPrerequisite/${courseId}/${courseCode}`);
    };

   

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const { description, prerequisiteId } = formData;
            const response = await api.post(`http://localhost:8090/admin/addprerequisite/${courseId}/${prerequisiteId}`, { description });
            alert("Prerequisite added successfully");
            navigate(`/viewCourseId/${courseId}`);
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    return (
        <div className="add-prerequisite-container">
            <AdminSidebar />
            <form className="prerequisite-form" onSubmit={handleSubmit}>
                <h2>Add Prerequisite</h2>

                <label>Description:</label>
                <input type="text" name="description" value={formData.description} onChange={handleChange} required />

                <label htmlFor="prerequisite">Select Course:</label>
                <select id="prerequisite" name="prerequisiteId" value={formData.prerequisiteId} onChange={handleChange} required>
                    <option value="">Select a course</option>
                    {courses.map(course => (
                        <option key={course.courseId} value={course.courseId}>
                            {course.name}
                        </option>
                    ))}
                </select>
                <button type="submit" className="submit-btn">Submit</button>
              {prerequisite.length >0 ? ( <button type="button" onClick={handleView} className="view-btn">View</button>): null}
            </form>
        </div>
    );
};

export default AddPrerequisite;
