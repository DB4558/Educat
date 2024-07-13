import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import './EditGrade.css'; 
import api from './api';

const EditGrade = () => {
    const { gradeId } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        letterGrade: '',
        gradePoints: '',
        comments: ''
    });
    const [error, setError] = useState('');

    useEffect(() => {
        fetchGradeDetails();
    }, [gradeId]);

    const fetchGradeDetails = async () => {
        try {
            const response = await api.get(`/viewGradeById/${gradeId}`);
            setFormData(response.data);
        } catch (err) {
            if (err.response && err.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    fetchGradeDetails();  // Retry fetching grade details
                } else {
                    navigate('/login');
                }
            } else {
                setError('Failed to fetch grade details');
                console.error('Error fetching grade details:', err);
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.put(`/editGrade/${gradeId}`, formData);
            alert("Grade updated successfully");
            navigate('/viewGrade');  
        } catch (error) {
            if (error.response && error.response.status === 401) {
                const newAccessToken = await getNewAccessToken();
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken);
                    handleSubmit(e);  
                } else {
                    navigate('/login');
                }
            } else {
                setError('Error updating grade');
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
        <div className="edit-grade-container">
            <AdminSidebar />
            <form className="grade-form" onSubmit={handleSubmit}>
                <h2>Edit Grade</h2>
                <label htmlFor="letterGrade">Letter Grade:</label>
                <input type="text" id="letterGrade" name="letterGrade" value={formData.letterGrade} onChange={handleChange} required />

                <label htmlFor="gradePoints">Grade Points:</label>
                <input type="number" id="gradePoints" name="gradePoints" value={formData.gradePoints} onChange={handleChange} required />

                <label htmlFor="comments">Comments:</label>
                <textarea id="comments" name="comments" value={formData.comments} onChange={handleChange} />

                <button type="submit" className="submit-btn">Update</button>
            </form>
        </div>
    );
};

export default EditGrade;
