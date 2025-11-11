import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const LoginPage = () => {

  const backendURL = import.meta.env.VITE_BACKEND_URL;
  const userTypes = ["PATIENT", "DOCTOR", "ADMIN"];

  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    userType: "",
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    fetch(`${backendURL}/auth/login`, {
      headers: {
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify(formData)
    })
      .then(async response => {

        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.response);
        }

        toast.success("Logged In Successfully.");

        // Setting the token.
        localStorage.setItem("token", data.accessToken);
        localStorage.setItem("refreshToken", data.refreshToken);
        // Now based on the assessment I do have to forward the page.

        switch (formData.userType) {
          case "PATIENT":
            if (!data.assessment) {
              navigate("/assessment")
              break;
            }
            // If assessment has been done then we have to forward the request to the dashboard.
            navigate("/dashboard/patient")
            break;
          case "DOCTOR":
            navigate("/dashboard/doctor")
            break;
          case "ADMIN":
            navigate("/admin")
            break;
          default:
            console.log(formData.userType)
            console.log("No user type found");
            navigate("/login");
            break;
        }
      })
      .catch(err => {
        toast.error(err.message);
      })

  };

  return (
    <div className="w-full h-[100vh] flex items-center justify-center bg-gradient-to-br from-green-50 via-white to-green-100">
      <div className="w-[90%] md:w-[400px] bg-white rounded-2xl shadow-xl p-8 flex flex-col gap-8">
        {/* Title */}
        <h1 className="text-center text-3xl font-bold text-gray-800">Login</h1>

        {/* Form */}
        <form className="flex flex-col gap-6" onSubmit={handleSubmit}>
          {/* User Type */}
          <div className="flex flex-col gap-2">
            <label htmlFor="userType" className="font-medium text-gray-700">
              User Type
            </label>
            <select
              id="userType"
              name="userType"
              value={formData.userType}
              onChange={handleChange}
              className="w-full bg-white border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 shadow-sm"
              required
            >
              <option value="">Select</option>
              {userTypes.map((opt, idx) => (
                <option key={idx} value={opt}>
                  {opt}
                </option>
              ))}
            </select>
          </div>

          {/* Email */}
          <div className="flex flex-col gap-2">
            <label htmlFor="email" className="font-medium text-gray-700">
              Email
            </label>
            <input
              type="email"
              id="email"
              name="email"
              placeholder="johndoe@email.com"
              onChange={handleChange}
              value={formData.email}
              className="w-full bg-white border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 shadow-sm"
              required
            />
          </div>

          {/* Password */}
          <div className="flex flex-col gap-2">
            <label htmlFor="password" className="font-medium text-gray-700">
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              placeholder="••••••••"
              onChange={handleChange}
              value={formData.password}
              className="w-full bg-white border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 shadow-sm"
              required
            />
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="w-full bg-green-600 text-white py-3 rounded-lg font-semibold shadow-md hover:bg-green-700 transition-transform transform hover:scale-105 active:scale-95"
          >
            Proceed
          </button>
        </form>

        {/* Extra Links */}
        <div className="text-sm text-gray-600 text-center">
          <p>
            Don’t have an account?{" "}
            <a
              href="/signup"
              className="text-green-600 font-medium hover:underline"
            >
              Sign up
            </a>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
