import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import DoctorSignup from "./signup_pages/DoctorSignupPage";

const AdminPage = () => {

  const navigate = useNavigate();
  const authToken = localStorage.getItem("token");

  // This will run when the page loaded to the DOM.
  useEffect(() => {
    // If the token not found redirecting to the login page.
    if (!authToken) {
      navigate("/login");
      return;
    }
  }, []);
  
  return (
    <div>
      <DoctorSignup authToken={authToken}/>
    </div>
  );
}

export default AdminPage;
