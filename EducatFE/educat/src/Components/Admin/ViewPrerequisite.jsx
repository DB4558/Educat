import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewPrerequisite.css'; 
import api from './api';

const ViewPrerequisites = () => {
    const [prerequisites, setPrerequisites] = useState([]);
    const { courseId, courseCode } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        fetchPrerequisites();
        const intervalId = setInterval(fetchPrerequisites, 60000); 

        return () => clearInterval(intervalId);
    }, []);

    const fetchPrerequisites = async () => {
        try {
            const response = await api.get(`http://localhost:8090/admin/viewPrerequisiteList/${courseId}`);
            console.log(response.data);
            setPrerequisites(response.data);
        } catch (error) {
            console.error('Error fetching prerequisites:', error);
            if (error.response && error.response.status === 401) {
            
            }
        }
    };

    const handleDelete = (prerequisiteId) => async (e) => {
        e.preventDefault();
        try {
            const response = await api.delete(`/removeCoursePrerequisite/${courseId}/${prerequisiteId}`);
            alert("Prerequisite deleted successfully");
            fetchPrerequisites();  
        } catch (error) {
            if (error.response && error.response.status === 401) {
                
                console.error('Error deleting prerequisite:', error);
                alert('Failed to delete the prerequisite');
            }
        }
    };

    const handleBack = () => {
        navigate(`/viewCourseId/${courseId}`);
    };
    

   
   

    return (
        <div className="view-prerequisites-container">
            <AdminSidebar />
            <div className="prerequisites-table-container">
                <h2>View Prerequisites for {courseCode}</h2>
                {prerequisites.length > 0 ? (
                    <table className="prerequisites-table">
                        <thead>
                            <tr>
                                <th>Code</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Year</th>
                                <th>Credits</th>
                                <th>Capacity</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {prerequisites.map(prerequisite => (
                                <tr key={prerequisite.courseId}>
                                    <td>{prerequisite.courseCode}</td>
                                    <td>{prerequisite.name}</td>
                                    <td>{prerequisite.description}</td>
                                    <td>{prerequisite.year}</td>
                                    <td>{prerequisite.credits}</td>
                                    <td>{prerequisite.capacity}</td>
                                    <td>
                                        <button onClick={handleDelete(prerequisite.courseId)}>
                                            <i className="fa fa-trash" />
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No prerequisites to display.</p>
                )}
                 <button onClick={handleBack} className="back-btn">Back to Course</button>
            </div>
        </div>
    );
};
export default ViewPrerequisites;
