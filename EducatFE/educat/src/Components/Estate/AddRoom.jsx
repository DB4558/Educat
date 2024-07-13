import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import EmployeeSidebar from './EmployeeSidebar';
import './AddRoom.css';  
import estateapi from './estateapi';


const AddRoom = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: '',
        room: '',
        floor: ''
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
            const response = await estateapi.post('/addRoom', formData);
            alert("Room added successfully");
            navigate('/EstateHome');
        } catch (error) {
            if (error.response && error.response.status === 401) {
                alert("Session Expired!! Login again");
                navigate('/');
            } else {
                console.error('Error submitting form:', error);
            }
        }
    };

    return (
        <div className="add-room-container">
            <EmployeeSidebar />
            <form className="room-form" onSubmit={handleSubmit}>
                <h2>Add Room</h2>
                <label htmlFor="name">Room Name:</label>
                <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />

                <label htmlFor="room">Room Number:</label>
                <input type="text" id="room" name="room" value={formData.room} onChange={handleChange} required />

                <label htmlFor="floor">Floor Number:</label>
                <input type="number" id="floor" name="floor" value={formData.floor} onChange={handleChange} required />

                <button type="submit" className="submit-btn">Submit</button>
            </form>
        </div>
    );
};

export default AddRoom;
