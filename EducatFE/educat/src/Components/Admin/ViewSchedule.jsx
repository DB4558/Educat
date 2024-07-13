import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewSchedule.css'; 
import api from './api';

const ViewSchedules = () => {
    const [schedules, setSchedules] = useState([]);
    const { courseId, courseCode } = useParams();
    const navigate = useNavigate();
    const location=useLocation();

    useEffect(() => {

        fetchSchedules();
        const intervalId = setInterval(fetchSchedules, 60000); 
      
        return () => clearInterval(intervalId); 
      }, [location]);

    
        const fetchSchedules = async () => {
            try {
                const response = await api.get(`http://localhost:8090/admin/viewScheduleByCourse/${courseId}`);
                console.log(response.data);
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

        const handleDelete = (scheduleId) => async (e) => {
            e.preventDefault();
            try {
                const response = await api.delete(`/deleteCourseSchedule/${scheduleId}`);
                alert("Schedule deleted successfully");
                navigate(`/viewSchedule/${courseId}/${courseCode}`);
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    const newAccessToken = await getNewAccessToken();
                    if (newAccessToken) {
                        localStorage.setItem('accessToken', newAccessToken);
                        handleDelete(scheduleId)(e);  // Retry the delete with the new token
                    } else {
                        navigate('/login');
                    }
                } else {
                    console.error('Error deleting schedule:', error);
                    alert('Failed to delete the schedule');
                }
            }
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
    
        
        const handleBack = () => {
            navigate(`/viewCourseId/${courseId}`);
        };
        
        return (
            <div className="view-schedules-container">
                <AdminSidebar />
                <div className="schedules-table-container">
                    <h2>View Schedule for {courseCode}</h2>
                    {schedules.length > 0 ? (
                        <table className="schedules-table">
                            <thead>
                                <tr>
                                    <th>Time</th>
                                    <th>Day</th>
                                    <th>Room</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {schedules.map(schedule => (
                                    <tr key={schedule.Id}>
                                        <td>{schedule.time}</td>
                                        <td>{schedule.day}</td>
                                        <td>{schedule.room}</td>
                                        <td>
                                            <button onClick={() => navigate(`/editSchedule/${schedule.id}/${courseId}/${courseCode}`)}>
                                                <i className="fa fa-edit" />
                                            </button>
                                            <button onClick={handleDelete(schedule.id)}>
                                                <i className="fa fa-trash" />
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : (
                        <p>No schedules to display.</p>
                    )}
                    <button onClick={handleBack} className="back-btn">Back to Course</button>
                </div>
            </div>
        );
};
export default ViewSchedules;        