import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import HomePage from './pages/HomePage'
import SurveyPage from './pages/SurveyPage'
import ResultPage from './pages/Resultpage'
import LoginPage from './pages/LoginPage'
import SignupPage1 from './pages/signup_pages/SignupPage1'
import PatientSignupPage from './pages/signup_pages/PatientSignupPage'
import OtpPage from './pages/signup_pages/OtpPage'
import PatientDashboard from './pages/PatientDashboard'
import Assessment from './pages/assessment_page/Assessment'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<HomePage />} />
        <Route path='/survey' element={<SurveyPage />} />
        <Route path='/result' element={<ResultPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup">
          <Route path="/signup/" element={<SignupPage1 />} />
          <Route path="/signup/patient" element={<PatientSignupPage />} />
          <Route path="/signup/otp" element={<OtpPage />} />
        </Route>
        <Route path="/dashboard">
          <Route path="/dashboard/patient" element={<PatientDashboard />} />
        </Route>
        <Route path="/assessment" element={<Assessment />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App