import React, { useState, useContext, createContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import './Login.css';
import { useEmail } from './EmailContext';
import api from './Components/Admin/api';
import estateapi from './Components/Estate/estateapi';




export const Login = () => {
  const { role } = useParams();
  console.log(role);
  const { email, setEmail } = useEmail();
  const navigate = useNavigate();
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  
  const handleLogin = async () => {
    if(role.trim()==='Admin'){
    try {
      const response = await api.post(`/login`, {
        email,
        password
      },{
        withCredentials: true
      });
      const { message, status, accesstoken } = response.data;
      if (status === true) {
        localStorage.setItem('accessToken', accesstoken);
        alert(message);
        setMessage(message);
        navigate("/AdminHome");
      }
    } catch (error) {
    if(error.response){
      alert(error.response.data.message);
    }
    }
  }
  if(role.trim()==='Estate'){
    try {
      const response = await estateapi.post(`/login`, {
        email,
        password
      },{
        withCredentials: true
      });
      const { message, status, accesstoken } = response.data;
      if (status === true) {
        localStorage.setItem('accessToken', accesstoken);
        alert(message);
        setMessage(message);
        navigate("/EstateHome");
      }
    } catch (error) {
    if(error.response){
      alert(error.response.data.message);
    }
    }
  }
  };

  
  return (

      <div className="login-page">
        <div className="login-card">
          <h2>Login as {role}</h2>
          <div className='input-container'>
          <i className="fas fa-envelope icon"></i>
          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          </div>
           <div className='input-container'>
           <i className="fas fa-lock icon"></i>
          <input
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          </div>
          <button onClick={handleLogin}>Login</button>
        </div>
        <button className="back-button" onClick={() => navigate('/')}>Back</button>
      </div>
  );
};
