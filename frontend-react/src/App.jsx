import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import HomePage from './pages/HomePage'
import LoginPage from './pages/LoginPage'
import SignupPage1 from './pages/signup_pages/SignupPage1'
import PatientSignupPage from './pages/signup_pages/PatientSignupPage'
import OtpPage from './pages/signup_pages/OtpPage'
import PatientDashboard from './pages/PatientDashboard'
import Assessment from "./pages/assessment_page/Assessment"
import AdminPage from './pages/AdminPage'
import DoctorDashboard from './pages/DoctorDashboard'
import { ToastContainer } from 'react-toastify'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup">
          <Route path="/signup/" element={<SignupPage1 />} />
          <Route path="/signup/patient" element={<PatientSignupPage />} />
          <Route path="/signup/patient/otp" element={<OtpPage />} />
        </Route>
        <Route path="/dashboard">
          <Route path="/dashboard/patient" element={<PatientDashboard />} />
          <Route path="/dashboard/doctor" element={<DoctorDashboard />} />
        </Route>
        <Route path="/assessment" element={<Assessment />} />
        <Route path="/admin" element={<AdminPage />} />
      </Routes>
      <ToastContainer position='top-right' autoClose={2000} theme='light' />
    </BrowserRouter>
  )
}

export default App