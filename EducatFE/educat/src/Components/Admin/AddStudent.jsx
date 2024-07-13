import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate ,useLocation} from 'react-router-dom';
import AdminSidebar from './AdminSidebar';
import api from './api'; 
import './AddStudent.css';

const AddStudent = () => {
    const [domains, setDomains] = useState([]);
    const [formData, setFormData] = useState({
        first_name: '',
        last_name: '',
        total_credits: '',
        graduation_year: '',
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
            const response = await api.get('http://localhost:8090/admin/viewDomainList');
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
            const response = await api.post(`http://localhost:8090/admin/addStudent/${formData.domainId}`, formData);
            alert("Student added successfully");
            navigate('/admin/students'); 
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    return (
        <div className="add-student-container">
            <AdminSidebar />
            <form className="student-form" onSubmit={handleSubmit}>
                <h2>Add Student</h2>

                <label>First Name:</label>
                <input type="text" name="first_name" value={formData.first_name} onChange={handleChange} required />

                <label>Last Name:</label>
                <input type="text" name="last_name" value={formData.last_name} onChange={handleChange} required />

                <label>Total Credits:</label>
                <input type="number" name="total_credits" value={formData.total_credits} onChange={handleChange} required />

                <label>Graduation Year:</label>
                <input type="number" name="graduation_year" value={formData.graduation_year} onChange={handleChange} required />

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

export default AddStudent;
