import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import api from './api'; 
import './AddCourseDomain.css'; 

const AddCourseDomain = () => {
    const { courseId, courseCode } = useParams();
    const [domains, setDomains] = useState([]);
    const [formData, setFormData] = useState({
        domainId: ''
    });
    const navigate = useNavigate();
    const location=useLocation();

    useEffect(() => {

        fetchDomains();
        const intervalId = setInterval(fetchDomains, 60000); 
      
        return () => clearInterval(intervalId); 
      }, [location]);
      
    const fetchDomains = async () => {
        try {
            const response = await api.get('/viewDomainList');
            setDomains(response.data);
        } catch (error) {
            console.error('Error fetching domains:', error);
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
            const { domainId } = formData;
            const response = await api.post(`/addCoursetoDomain/${courseId}/${domainId}`);
            alert("Course successfully added to domain");
            navigate(`/viewCourseId/${courseId}`); 
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    return (
        <div className="add-course-domain-container">
            <AdminSidebar />
            <form className="course-domain-form" onSubmit={handleSubmit}>
                <h2>Add Course to Domain</h2>

                <label htmlFor="domain">Select Domain:</label>
                <select id="domain" name="domainId" value={formData.domainId} onChange={handleChange} required>
                    <option value="">Select a domain</option>
                    {domains.map(domain => (
                        <option key={domain.domainId} value={domain.domainId}>
                            {domain.program}
                        </option>
                    ))}
                </select>

                <button type="submit" className="submit-btn">Submit</button>
            </form>
        </div>
    );
};

export default AddCourseDomain;
