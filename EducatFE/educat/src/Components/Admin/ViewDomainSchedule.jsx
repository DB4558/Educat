import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import axios from 'axios';
import './ViewDomainSchedule.css'; 
import api from './api';

const ViewDomainSchedule = () => {
    const { domainId } = useParams();
    const [schedules, setSchedules] = useState([]);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        fetchSchedules();
        const intervalId = setInterval(fetchSchedules, 60000);

        return () => clearInterval(intervalId);
    }, [location]);

    const fetchSchedules = async () => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const response = await api.get(`http://localhost:8090/admin/getCourseandScheduleByDomain/${domainId}`);
            setSchedules(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                try {
                    const newAccessToken = await getNewAccessToken();
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchSchedules();
                } catch (refreshError) {
                    console.error('Failed to refresh token:', refreshError);
                    navigate('/login');
                }
            } else {
                console.error('Error fetching schedules:', error);
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
            console.error('Unable to refresh token');
            return null;
        }
    };

    return (
        <div className="view-schedules-container">
            <AdminSidebar />
            <div className="schedules-table-container">
                <h2>Schedule List</h2>
                <table className="schedules-table">
                    <thead>
                        <tr>
                            <th>Course Code</th>
                            <th>Name</th>
                            <th>Credits</th>
                            <th>Capacity</th>
                            <th>Time</th>
                            <th>Day</th>
                            <th>Room</th>
                        </tr>
                    </thead>
                    <tbody>
                        {schedules.length ? (schedules.map(schedule => (
                            <tr key={schedule.id}>
                                <td>{schedule.courseCode}</td>
                                <td>{schedule.name}</td>
                                <td>{schedule.credits}</td>
                                <td>{schedule.capacity}</td>
                                <td>{schedule.time}</td>
                                <td>{schedule.day}</td>
                                <td>{schedule.room}</td>
                            </tr>
                        ))): <p>"Nothing to Display"</p>}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ViewDomainSchedule;
