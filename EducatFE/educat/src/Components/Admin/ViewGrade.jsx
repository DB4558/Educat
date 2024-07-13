import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './ViewGrade.css';
import api from './api';

const ViewGrades = () => {
    const [grades, setGrades] = useState([]);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        fetchGrades();
        const intervalId = setInterval(fetchGrades, 60000);

        return () => clearInterval(intervalId);
    }, [location]);

    const fetchGrades = async () => {
        try {
            const response = await api.get(`http://localhost:8090/admin/viewGradeList`);
            setGrades(response.data);
        } catch (error) {
            console.error('Error fetching grades:', error);
        }
    };

    const handleEdit = (gradeId) => {
        navigate(`/editGrade/${gradeId}`);  
    };

    return (
        <div className="view-grades-container">
            <AdminSidebar />
            <div className="grades-table-container">
                <h2>Grades List</h2>
                {grades.length > 0 ? (
                    <table className="grades-table">
                        <thead>
                            <tr>
                                <th>Letter Grade</th>
                                <th>Grade Points</th>
                                <th>Comments</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {grades.map((grade) => (
                                <tr key={grade.gradeId}>
                                    <td>{grade.letterGrade}</td>
                                    <td>{grade.gradePoints}</td>
                                    <td>{grade.comments}</td>
                                    <td>
                                        <button onClick={() => handleEdit(grade.gradeId)}>
                                            <i className="fa fa-edit" />
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No grades to display.</p>
                )}
            </div>
        </div>
    );
};

export default ViewGrades;
