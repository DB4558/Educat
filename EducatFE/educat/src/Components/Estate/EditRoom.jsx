import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import EmployeeSidebar from './EmployeeSidebar';
import './EditRoom.css';
import estateapi from './estateapi';

const EditRoom = () => {
    const { hostelId } = useParams();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        room: '',
        name: '',
        floor: '',
        studentId: ''
    });
    const [students, setStudents] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        fetchRoomDetails();
        fetchStudents();
    }, [hostelId]);

    const fetchRoomDetails = async () => {
        try {
            const response = await estateapi.get(`/viewRoomById/${hostelId}`);
            setFormData({
                room: response.data.room,
                name: response.data.name,
                floor: response.data.floor,
                studentId: response.data.students ? response.data.students.student_id: ''
            });
        } catch (err) {
            if (err.response && err.response.status === 401) {
                navigate('/login');
            } else {
                setError('Failed to fetch room details');
                console.error('Error fetching room details:', err);
            }
        }
    };

    const fetchStudents = async () => {
        try {
            const response = await estateapi.get('/viewStudent');
            setStudents(response.data);
        } catch (error) {
            console.error('Error fetching students:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
    const updatedData={
            name: formData.name,
            room: formData.room,
            floor: formData.floor,
            students: {
                student_id:formData.studentId
            }};
        try {
            await estateapi.put(`/editRoom/${hostelId}`, updatedData);
            alert("Room updated successfully");
            navigate(`/viewRoom`);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                navigate('/login');
            } else {
                setError('Error updating room');
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

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className="edit-room-container">
            <EmployeeSidebar />
            <form className="room-form" onSubmit={handleSubmit}>
                <h2>Edit Room</h2>
                <label htmlFor="room">Room Number:</label>
                <input type="text" id="room" name="room" value={formData.room} onChange={handleChange} required />

                <label htmlFor="name">Room Name:</label>
                <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />

                <label htmlFor="floor">Floor:</label>
                <input type="number" id="floor" name="floor" value={formData.floor} onChange={handleChange} required />

                <label htmlFor="studentId">Assign Student:</label>
                <select id="studentId" name="studentId" value={formData.studentId} onChange={handleChange}>
                    <option value="">Select Student</option>
                    {students.map(student => (
                        <option key={student.student_id} value={student.student_id}>
                            {student.first_name} {student.last_name}, {student.roll_number}
                        </option>
                    ))}
                </select>

                <button type="submit" className="submit-btn">Update</button>
            </form>
        </div>
    );
};

export default EditRoom;
