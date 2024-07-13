import React, { useState,useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './AddGrade.css';
import api from './api';

const AddGrade = () => {
    const navigate = useNavigate();
    const [grades,setGrades]= useState([]);
    const [formData, setFormData] = useState({
        letterGrade: '',
        gradePoints: '',
        comments: ''
    });
    const [error, setError] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const fetchGrade = async () => {
        try {
            const response = await api.get('http://localhost:8090/admin/viewGradeList');
            setGrades(response.data);
        } catch (error) {
            console.error('Error fetching Grades:', error);
        }
    };

    const handleView = () => {
        navigate(`/viewGrade`);
    };


    useEffect(() => {

        fetchGrade();
      }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('http://localhost:8090/admin/addGrade', formData);
            alert("Grade added successfully");
            setFormData({
                letterGrade: '',
        gradePoints: '',
        comments: ''
            }

            );
            navigate('/addgrade'); 
        } catch (error) {
            console.error('Error submitting form:', error);
            setError('Failed to add grade');
        }
    };

    return (
        <div className="add-grade-container">
            <AdminSidebar />
            <form className="grade-form" onSubmit={handleSubmit}>
                <h2>Add Grade</h2>

                <label htmlFor="letterGrade">Letter Grade:</label>
                <input type="text" id="letterGrade" name="letterGrade" value={formData.letterGrade} onChange={handleChange} required />

                <label htmlFor="gradePoints">Grade Points:</label>
                <input type="number" step="0.01" id="gradePoints" name="gradePoints" value={formData.gradePoints} onChange={handleChange} required />

                <label htmlFor="comments">Comments:</label>
                <textarea id="comments" name="comments" value={formData.comments} onChange={handleChange} />

                <button type="submit" className="submit-btn">Submit</button>
                {grades.length >0 ? (<button type="button" onClick={handleView} className="view-btn">View</button>): null}
                {error && <p className="error">{error}</p>}
            </form>
        </div>
    );
};

export default AddGrade;
