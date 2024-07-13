import React, { useState,useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './AddSchedule.css';
import api from './api';

const AddSchedule = () => {
    const { courseId ,courseCode} = useParams();
    const [schedules,setSchedules]=useState([]);
    const navigate = useNavigate();
    const location=useLocation();
    const [formData, setFormData] = useState({
        time: '',
        day: '',
        room: ''
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
            const response = await api.post(`http://localhost:8090/admin/addCourseSchedule/${courseId}`, formData);
            alert("Schedule added successfully");
            navigate(`/viewCourseId/${courseId}`);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    handleSubmit(e);
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error submitting form:', error);
            }
        }
    };

    useEffect(() => {

        fetchSchedules();
        const intervalId = setInterval(fetchSchedules, 60000); 
      
        return () => clearInterval(intervalId); 
      }, [location]);
      

    const fetchSchedules = async () => {
        try {
            const response = await api.get(`http://localhost:8090/admin/viewScheduleByCourse/${courseId}`);
            setSchedules(response.data);
        } catch (error) {
            console.error('Error fetching schedules:', error);
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchSchedules(); 
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
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

    const handleView = () => {
        navigate(`/viewSchedule/${courseId}/${courseCode}`);
    };

   

    return (
        <div className="add-schedule-container">
            <AdminSidebar />
            <form className="schedule-form" onSubmit={handleSubmit}>
                <h2>Add Schedule</h2>

                <label>Time:</label>
                <input type="text"  name="time" value={formData.time} onChange={handleChange} required placeholder='HH:mm:ss' />

                <label htmlFor="day">Day:</label>
                <select id="day" name="day" value={formData.day} onChange={handleChange} required>
                    <option value="">Select a day</option>
                    <option value="MONDAY">MONDAY</option>
                    <option value="TUESDAY">TUESDAY</option>
                    <option value="WEDNESDAY">WEDNESDAY</option>
                    <option value="THURSDAY">THURSDAY</option>
                    <option value="FRIDAY">FRIDAY</option>
                </select>

                <label htmlFor="room">Room:</label>
                <input type="text" id="room" name="room" value={formData.room} onChange={handleChange} required />

                <button type="submit" className="submit-btn">Submit</button>
                
            {schedules.length >0 ? (<button type="button" onClick={handleView} className="view-btn">View</button>): null}
                    
            </form>
            
        </div>
    );
};

export default AddSchedule;
