import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import api from './api'; 
import './AddCourseSpecialisation.css';

const AddCourseSpecialisation = () => {
    const { courseId, courseCode } = useParams();
    const [specialisations, setSpecialisations] = useState([]);
    const [formData, setFormData] = useState({
        specialisationId: ''
    });
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        fetchSpecialisations();
        const intervalId = setInterval(fetchSpecialisations, 60000); 
      
        return () => clearInterval(intervalId);
    }, [location]);

    const fetchSpecialisations = async () => {
        try {
            const response = await api.get('/viewSpecialisation');
            setSpecialisations(response.data);
        } catch (error) {
            console.error('Error fetching specialisations:', error);
        }
    };

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
            const { specialisationId } = formData;
            const response = await api.post(`/addCourseToSpecialisation/${courseId}/${specialisationId}`);
            alert("Course successfully added to specialisation");
            navigate(`/viewCourseId/${courseId}`); 
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    return (
        <div className="add-course-specialisation-container">
            <AdminSidebar />
            <form className="course-specialisation-form" onSubmit={handleSubmit}>
                <h2>Add Course to Specialisation</h2>

                <label htmlFor="specialisation">Select Specialisation:</label>
                <select id="specialisation" name="specialisationId" value={formData.specialisationId} onChange={handleChange} required>
                    <option value="">Select a specialisation</option>
                    {specialisations.map(specialisation => (
                        <option key={specialisation.specialisationId} value={specialisation.specialisationId}>
                            {specialisation.name}
                        </option>
                    ))}
                </select>

                <button type="submit" className="submit-btn">Submit</button>
            </form>
        </div>
    );
};

export default AddCourseSpecialisation;
