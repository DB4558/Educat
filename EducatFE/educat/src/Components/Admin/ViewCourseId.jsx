import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewCourseId.css'; 
import api from './api';

const ViewCourseId = () => {
    const { courseId } = useParams();
    const navigate = useNavigate();
    const [courseData, setCourseData] = useState({ courseId: '', courseCode: '', name: '', description: '', year: '', term: '', capacity: '', credits: '' });
    const [error, setError] = useState('');
    const location=useLocation();

    const fetchCourseDetails = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/viewCoursesByid/${courseId}`);
            setCourseData(response.data);
        } catch (err) {
            if (err.response && err.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchCourseDetails();
                } else {
                    navigate('/login');
                }
            } else {
                setError('Failed to fetch course details.');
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

        fetchCourseDetails();
      }, [location]);
      

    return (
        <>
            <div className="main-content">
                <AdminSidebar />
                <div className="course-card">
                    <h2>Course Details</h2>
                    {error ? <p>{error}</p> : (
                        <div>
                            <p><strong>Course Code:</strong> {courseData.courseCode}</p>
                            <p><strong>Name:</strong> {courseData.name}</p>
                            <p><strong>Description:</strong> {courseData.description}</p>
                            <p><strong>Year:</strong> {courseData.year}</p>
                            <p><strong>Term:</strong> {courseData.term}</p>
                            <p><strong>Capacity:</strong> {courseData.capacity}</p>
                            <p><strong>Credits:</strong> {courseData.credits}</p>
                        </div>
                    )}
                </div>
                <div className="course-actions">
                    <button className='action-btn-edit' onClick={() => navigate(`/editCourse/${courseData.courseId}`)}>Edit Course</button>
                    <button className='action-btn-student' onClick={() => navigate(`/viewStudentCourse/${courseData.courseId}`)}>View Students</button>
                    <button className='action-btn-schedule' onClick={() => navigate(`/addSchedule/${courseData.courseId}/${courseData.courseCode}`)}>Add Schedule</button>
                    <button  className='action-btn-prerequisite' onClick={() => navigate(`/addPrerequisite/${courseData.courseId}/${courseData.courseCode}`)}>Add Prerequisite</button>
                    <button className='action-btn-domain' onClick={() => navigate(`/addCourseDomain/${courseData.courseId}/${courseData.courseCode}`)}>Register to Domain</button>
                    <button  className='action-btn-specialisation' onClick={() => navigate(`/addCourseSpecialisation/${courseData.courseId}/${courseData.courseCode}`)}>Register to Specialisation</button>
                </div>
            </div>
        </>
    );
};

export default ViewCourseId;
