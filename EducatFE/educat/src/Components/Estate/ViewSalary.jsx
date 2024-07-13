import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import EmployeeSidebar from './EmployeeSidebar';
import './ViewSalary.css';
import estateapi from './estateapi';
import { useEmail } from '../../EmailContext';

const ViewSalary = () => {
    const [salaries, setSalaries] = useState([]);
    const [displayedSalaries, setDisplayedSalaries] = useState([]);
    const [filter, setFilter] = useState('all'); // Default filter
    const navigate = useNavigate();
    const location = useLocation();
    const { email } = useEmail();

    useEffect(() => {
        fetchSalaries();
    }, [location, email]);

    const fetchSalaries = async () => {
        try {
            const response = await estateapi.get(`/viewSalaryHistoryByEmployee/${email}`);
            setSalaries(response.data.map(salary => ({
                
                paymentDate: new Date(salary.paymentDate), 
                amount:salary.amount,
                description:salary.description
            })));
        } catch (error) {
            if (error.response && error.response.status === 401) {
                navigate('/login');
            } else {
                console.error('Error fetching salaries:', error);
            }
        }
    };

    useEffect(() => {
        filterSalaries();
    }, [salaries, filter]);

    const filterSalaries = () => {
        let filtered = salaries;
        if (filter.year !== 'all') {
            filtered = filtered.filter(salary => salary.paymentDate.getFullYear().toString() === filter.year);
        }
        if (filter.month !== 'all') {
            filtered = filtered.filter(salary => salary.paymentDate.getMonth() + 1 === parseInt(filter.month));
        }
        setDisplayedSalaries(filtered);
    };

    const handleFilterChange = (event) => {
        setFilter(prev => ({ ...prev, [event.target.name]: event.target.value }));
    };

    const handleBack = () => {
        navigate('/EstateHome');
    };

    // Extract unique years and months from the salary data
    const uniqueYears = Array.from(new Set(salaries.map(salary => salary.paymentDate.getFullYear()))).sort();
    const months = Array.from({ length: 12 }, (_, i) => new Date(0, i).toLocaleString('default', { month: 'long' }));

    return (
        <div className="view-salary-container">
            <EmployeeSidebar />
            <div className="filter-dropdown">
                <select name="year" onChange={handleFilterChange} value={filter.year}>
                    <option value="all">All Years</option>
                    {uniqueYears.map(year => (
                        <option key={year} value={year}>{year}</option>
                    ))}
                </select>
                <select name="month" onChange={handleFilterChange} value={filter.month}>
                    <option value="all">All Months</option>
                    {months.map((month, index) => (
                        <option key={index} value={index + 1}>{month}</option>
                    ))}
                </select>
            </div>
            <div className="salary-table-container">
                <h2>Salary List</h2>
                <table className="salary-table">
                    <thead>
                        <tr>
                            <th>Payment Date</th>
                            <th>Amount</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        {displayedSalaries.map((salary, index) => (
                            <tr key={index}>
                                <td>{salary.paymentDate.toLocaleDateString()}</td>
                                <td>{salary.amount}</td>
                                <td>{salary.description}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <button onClick={handleBack} className="back-btn">Back</button>
        </div>
    );
};

export default ViewSalary;