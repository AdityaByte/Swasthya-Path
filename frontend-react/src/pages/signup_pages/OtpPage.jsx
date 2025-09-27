import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const OtpPage = () => {

  const backendURL = import.meta.env.VITE_BACKEND_URL;
  const [otp, setOtp] = useState(new Array(6).fill(""));
  const navigate = useNavigate();
  const location = useLocation();

  const { email } = location.state || {};

  const handleChange = (element, index) => {
    if (isNaN(element.value)) return;

    let newOtp = [...otp];
    newOtp[index] = element.value;
    setOtp(newOtp);

    if (element.value && element.nextSibling) {
      element.nextSibling.focus();
    }
  };

  const handleKeyDown = (e, index) => {
    if (e.key === "Backspace") {
      if (!otp[index] && e.target.previousSibling) {
        e.target.previousSibling.focus();
      }
      let newOtp = [...otp];
      newOtp[index] = "";
      setOtp(newOtp);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const payload = {
      email: email,
      otp: otp.join(""),
    }

    // Now we need to send the request to the backend that the otp is valid is or not.
    fetch(`${backendURL}/auth/signup/patient/otp`, {
      headers: {
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify(payload)
    })
      .then(async response => {

        const body = await response.body

        if (!response.ok) {
          throw new Error(data.message || "OTP verification failed.");
          return;
        }
        // Else we need to just navigate it to the assessment page.
        // If the assessment is already done then we don't need to show the assessment page since directly forward it to the dashboard ok.
        // Now we need to navigate the patient to the Login Page.

        console.log(body)
        navigate("/login")
      })
      .catch(async error => {
        console.error(error.message);
        return;
      })
  };

  return (
    <div className="w-full min-h-[100vh] flex items-center justify-center bg-gradient-to-br from-green-50 via-white to-green-100 px-4">
      <div className="bg-white shadow-2xl rounded-3xl p-10 w-full max-w-md text-center">
        <h1 className="text-3xl font-bold text-green-600 mb-4">
          OTP Verification
        </h1>
        <p className="text-gray-600 mb-6">
          Please enter the 6-digit code sent to your registered email/phone.
        </p>

        <form onSubmit={handleSubmit} className="flex flex-col items-center gap-6">
          <div className="flex justify-center gap-3">
            {otp.map((data, index) => (
              <input
                key={index}
                type="text"
                maxLength="1"
                value={data}
                onChange={(e) => handleChange(e.target, index)}
                onKeyDown={(e) => handleKeyDown(e, index)}
                className="w-12 h-12 text-center text-lg border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 shadow-sm"
              />
            ))}
          </div>

          <button
            type="submit"
            className="bg-green-600 text-white font-semibold px-8 py-3 rounded-lg hover:bg-green-700 transition-colors shadow-lg"
          >
            Verify OTP
          </button>
        </form>

        <p className="text-sm text-gray-500 mt-6">
          Didn’t receive the code?{" "}
          <span className="text-green-600 font-semibold cursor-pointer hover:underline">
            Resend
          </span>
        </p>
      </div>
    </div>
  );
};

export default OtpPage;
