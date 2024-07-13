import React, { useState } from 'react';
import {useNavigate} from 'react-router-dom';
import './Home.css'; 
import { FaArrowRight } from 'react-icons/fa'
import backgroundImage from './background.jpg'; 

export default function Home() {
  const [selectedRole, setSelectedRole] = useState("");
  const navigate=useNavigate();
  
  const handleLogin = (role) => {
    setSelectedRole(role);
    navigate(`/login/${role}`);
    
  };

  return (
    <div className="container">
      <div className="contentContainer">
        <div className="loginContainer">
          <img src={backgroundImage} alt="Background" className="backgroundImage" />
        </div>

        <div className="overlay">
          <div className="logoContainer">
            
          </div>

          <div className="buttonsContainer">
            <h1 className='heading'>Sign in to <FaArrowRight className="loginArrow" /><p className='Educat'>Educat</p></h1>
            {['Admin', 'Student', 'Faculty', 'Accounts', 'Placement', 'Estate', 'HR'].map(role => (
              <button
                key={role}
                className={`button ${selectedRole === role ? 'selected' : '' }`}
                onClick={() => handleLogin(role)}
              >
                {role}
                <FaArrowRight className="Arrow" />
              </button>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
