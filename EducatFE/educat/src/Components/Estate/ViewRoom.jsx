import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import EmployeeSidebar from './EmployeeSidebar';
import './ViewRoom.css';
import estateapi from './estateapi';

const ViewRooms = () => {
    const [rooms, setRooms] = useState([]);
    const [displayedRooms, setDisplayedRooms] = useState([]);
    const [filter, setFilter] = useState('all'); // Default filter
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        fetchRooms();
    }, [location]);

    useEffect(() => {
        filterRooms();
    }, [rooms, filter]);

    const fetchRooms = async () => {
        try {
            const response = await estateapi.get(`/viewAllRooms`);
            setRooms(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                navigate('/login');
            } else {
                console.error('Error fetching rooms:', error);
            }
        }
    };

    const filterRooms = () => {
        switch (filter) {
            case 'all':
                setDisplayedRooms(rooms);
                break;
            case 'available':
                setDisplayedRooms(rooms.filter(room => !room.students));
                break;
            case 'occupied':
                setDisplayedRooms(rooms.filter(room => room.students));
                break;
            default:
                setDisplayedRooms(rooms);
                break;
        }
    };

    const handleFilterChange = (event) => {
        setFilter(event.target.value);
    };

    const handleBack = () => {
        navigate('/EstateHome');
    };

    return (
        <div className="view-rooms-container">
            <EmployeeSidebar />
            <div className="filter-dropdown">
                <select onChange={handleFilterChange} value={filter}>
                    <option value="all">All Rooms</option>
                    <option value="available">Available Rooms</option>
                    <option value="occupied">Occupied Rooms</option>
                </select>
            </div>
            <div className="rooms-table-container">
                <h2>Room List</h2>
                <table className="rooms-table">
                    <thead>
                        <tr>
                            <th>Room</th>
                            <th>Name</th>
                            <th>Floor</th>
                            <th>Tenant</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {displayedRooms.map(room => (
                            <tr key={room.hostelId}>
                                <td>{room.room}</td>
                                <td>{room.name}</td>
                                <td>{room.floor}</td>
                                <td>
                                    {room.students ?
                                        `${room.students.first_name} ${room.students.last_name}, ${room.students.roll_number}, ${room.students.email}`
                                        : 'N/A'}
                                </td>
                                <td><button onClick={() => navigate(`/editRoom/${room.hostelId}`)}>Edit</button></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <button onClick={handleBack} className="back-btn">Back</button>
        </div>
    );
};


export default ViewRooms;
