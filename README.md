# Educat

Educat is an educational management system designed to streamline institutional workflows and enhance operational efficiency. It includes modules for admin, student, faculty, estate, placement, and accounts. Key functionalities include user management, course registration, facility management, placement coordination, fee processing, and real-time communication. The integration of various technologies ensures secure data handling and seamless communication.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Architecture](#system-architecture)
- [Installation](#installation)
- [Usage](#usage)
- [Modules](#modules)
- [Security](#security)
- [Payment Integration](#payment-integration)
- [Real-Time Communication](#real-time-communication)

## Features

- **User Management**: Admin can manage users (students, faculty, staff) with roles and permissions.
- **Course Registration**: Students can register for courses, and faculty can manage course details.
- **Facility Management**: Estate module for managing institutional facilities.
- **Placement Coordination**: Placement module for managing placement activities and coordination.
- **Fee Processing**: Secure fee processing through Paytm integration.
- **Real-Time Communication**: Using Apache Kafka and Twilio for seamless communication.
- **Secure Data Handling**: AES encryption for sensitive data, JWT for authentication, and secure cookies for session management.

## Technologies Used

- **Frontend**: React.js
- **Backend**: Spring Boot
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Encryption**: AES (Advanced Encryption Standard)
- **Payment Gateway**: Paytm
- **Communication**: Twilio, Apache Kafka

## System Architecture

Educat follows a microservices architecture with a clear separation of concerns between different modules. The frontend is developed using React.js, while the backend services are built with Spring Boot. MySQL is used as the database for storing user and operational data. Apache Kafka is employed for real-time communication, and Twilio handles SMS notifications.

## Installation

### Prerequisites

- Java Development Kit (JDK)
- MySQL
- Maven

### Backend Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/educat-backend.git
   cd educat-backend
2. Configure the database in src/main/resources/application.properties:

   ```bash

      spring.datasource.url=jdbc:mysql://localhost:3306/educat
      spring.datasource.username=root
      spring.datasource.password=yourpassword

3. Build and run the backend:

      ```bash

          mvn clean install
          mvn spring-boot:run

4. Frontend Setup

    Clone the repository:

    ```bash

      git clone https://github.com/yourusername/educat-frontend.git
      cd educat-frontend

5. Install dependencies and run the frontend:

   ```bash

       npm install
       npm start

6.Usage

    Open the browser and navigate to http://localhost:3000.
    Log in as an admin to manage users, courses, and facilities.
    Students can register for courses and view their placements.
    Faculty can manage course details and communicate with students in real time.

## Modules
**Admin**

    Manage users (students, faculty, staff)
    Assign roles and permissions
    Oversee system operations

**Student**

    Register for courses
    View courseDetails,Timetable,Result documents
    Fee payment
    View academic, hostel and placement details
    Communicate with faculty 

**Faculty**

    Manage course details
    Register as course instructor
    Communicate with students
    Provide academic guidance

**Estate**

    Manage institutional facilities
   

**Placement**

    Coordinate placement activities
    Manage company interactions
    Track placement progress

**Accounts**

    Process fee payments
    Generate financial reports

## Security

    JWT: Secure authentication mechanism for API access with refresh and access tokens
    AES Encryption: Encrypt sensitive data to ensure privacy.
    Cookies: Secure session management using HttpOnly and Secure flags.

## Payment Integration

    Paytm: Integrated for secure and efficient fee processing.
## Contribution:


    Fork the repository
    Create a new branch (git checkout -b feature-branch)
    Commit your changes (git commit -am 'Add new feature')
    Push to the branch (git push origin feature-branch)
    Create a new Pull Request




## Real-Time Communication

    Apache Kafka: For handling real-time messaging and notifications.
    Twilio: For Otp sending and verfication.
