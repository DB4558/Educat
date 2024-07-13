import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './EditSchedule.css'; 
import api from './api';

const EditSchedule = () => {
    const { Id, courseId, courseCode } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        time: '',
        day: '',
        room: ''
    });
    const [error, setError] = useState('');

    useEffect(() => {
        fetchScheduleDetails();
    }, [Id]);

    const fetchScheduleDetails = async () => {
        try {
            const response = await api.get(`/viewScheduleById/${Id}`);
            setFormData(response.data);
        } catch (err) {
            if (err.response && err.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchScheduleDetails();  // Retry fetching schedule details
                } else {
                    navigate('/login');
                }
            } else {
                setError('Failed to fetch schedule details');
                console.error('Error fetching schedule details:', err);
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.put(`/editCourseSchedule/${Id}`, formData);
            alert("Schedule updated successfully");
            navigate(`/viewSchedule/${courseId}/${courseCode}`);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    handleSubmit(e);  // Retry submission
                } else {
                    navigate('/login');
                }
            } else {
                setError('Error updating schedule');
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
            const response = await api.post('/refresh-token', {}, {
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
        <div className="edit-schedule-container">
            <AdminSidebar />
            <form className="schedule-form" onSubmit={handleSubmit}>
                <h2>Edit Schedule for {courseCode}</h2>
                <label htmlFor="time">Time:</label>
                <input type="text" id="time" name="time" value={formData.time} onChange={handleChange} required />

                <label htmlFor="day">Day:</label>
                <select id="day" name="day" value={formData.day} onChange={handleChange} required>
                    <option value="">Select a day</option>
                    <option value="MONDAY">Monday</option>
                    <option value="TUESDAY">Tuesday</option>
                    <option value="WEDNESDAY">Wednesday</option>
                    <option value="THURSDAY">Thursday</option>
                    <option value="FRIDAY">Friday</option>
                </select>

                <label htmlFor="room">Room:</label>
                <input type="text" id="room" name="room" value={formData.room} onChange={handleChange} required />

                <button type="submit" className="submit-btn">Update</button>
            </form>
        </div>
    );
};

export default EditSchedule;
