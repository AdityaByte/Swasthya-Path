import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const PatientSignupPage = () => {

    const backendURL = import.meta.env.VITE_BACKEND_URL;
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: "",
        email: "",
        phoneNumber: "",
        age: "",
        dob: "",
        gender: "",
        weight: "",
        height: "",
        password: "",
        confirmPassword: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target
        setFormData({
            ...formData,
            [name]: name == "weight" || name == "height" ? parseFloat(value) : value,
        })
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (formData.password !== formData.confirmPassword) {
            console.log(typeof (password), typeof (confirmPassword))
            console.log(password, confirmPassword)
            alert("Password doesn't match")
            return;
        }

        fetch(`${backendURL}/auth/signup/patient`, {
            headers: {
                "Content-Type": "application/json",
            },
            method: "POST",
            body: JSON.stringify(formData),
        })
            .then(async response => {
                // I am not sending the json data from backend.
                if (response.status != 200) {
                    throw new Error(response.body);
                    return;
                }
                // Else we need to proceed further to the assessment page.
                navigate("/signup/patient/otp", { state: { email: formData.email } })
            })
            .catch(async error => {
                console.error(error.message);
                return;
            })
    };

    return (
        <div className="w-full min-h-[100vh] flex items-center justify-center bg-gradient-to-br from-green-50 via-white to-green-100 px-4">
            <div className="w-full max-w-4xl bg-white rounded-3xl shadow-2xl p-10">
                <h1 className="text-3xl font-bold text-center text-green-700 mb-8">
                    Patient Signup
                </h1>

                <form
                    className="grid grid-cols-1 md:grid-cols-2 gap-6"
                    onSubmit={handleSubmit}
                >
                    {/* Name */}
                    <div className="flex flex-col">
                        <label htmlFor="name" className="font-semibold mb-1">
                            Full Name
                        </label>
                        <input
                            type="text"
                            id="name"
                            name="name"
                            placeholder="Aditya Pawar"
                            value={formData.name}
                            onChange={handleChange}
                            className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        />
                    </div>

                    {/* Email */}
                    <div className="flex flex-col">
                        <label htmlFor="email" className="font-semibold mb-1">
                            Email
                        </label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            placeholder="xyz@gmail.com"
                            value={formData.email}
                            onChange={handleChange}
                            className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        />
                    </div>

                    {/* Phone */}
                    <div className="flex flex-col">
                        <label htmlFor="phone" className="font-semibold mb-1">
                            Phone Number
                        </label>
                        <input
                            type="text"
                            id="phoneNumber"
                            name="phoneNumber"
                            placeholder="+91 9876543210"
                            value={formData.phoneNumber}
                            onChange={handleChange}
                            className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        />
                    </div>

                    {/* DOB */}
                    <div className="flex flex-col">
                        <label htmlFor="dob" className="font-semibold mb-1">
                            Date of Birth
                        </label>
                        <input
                            type="date"
                            id="dob"
                            name="dob"
                            value={formData.dob}
                            onChange={handleChange}
                            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        />
                    </div>

                    {/* Gender */}
                    <div className="flex flex-col">
                        <label htmlFor="gender" className="font-semibold mb-1">
                            Gender
                        </label>
                        <select
                            id="gender"
                            name="gender"
                            value={formData.gender}
                            onChange={handleChange}
                            className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        >
                            <option value="">Select Gender</option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="OTHER">Other</option>
                        </select>
                    </div>

                    {/* Weight */}
                    <div className="flex flex-col">
                        <label htmlFor="weight" className="font-semibold mb-1">
                            Weight (kg)
                        </label>
                        <input
                            type="number"
                            id="weight"
                            name="weight"
                            placeholder="e.g. 65"
                            value={formData.weight}
                            onChange={handleChange}
                            className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        />
                    </div>

                    {/* Height */}
                    <div className="flex flex-col">
                        <label htmlFor="height" className="font-semibold mb-1">
                            Height (cm)
                        </label>
                        <input
                            type="number"
                            id="height"
                            name="height"
                            placeholder="e.g. 170"
                            value={formData.height}
                            onChange={handleChange}
                            className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        />
                    </div>

                    {/* Password */}
                    <div className="flex flex-col">
                        <label htmlFor="password" className="font-semibold mb-1">
                            Password
                        </label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Enter password"
                            value={formData.password}
                            onChange={handleChange}
                            className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        />
                    </div>

                    {/* Confirm Password */}
                    <div className="flex flex-col">
                        <label htmlFor="confirmPassword" className="font-semibold mb-1">
                            Confirm Password
                        </label>
                        <input
                            type="password"
                            id="confirmPassword"
                            name="confirmPassword"
                            placeholder="Confirm password"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                            required
                        />
                    </div>

                    {/* Submit Button spans both columns */}
                    <div className="md:col-span-2 flex justify-center mt-4">
                        <button
                            type="submit"
                            className="bg-green-600 text-white font-semibold px-8 py-3 rounded-lg hover:bg-green-700 transition-colors shadow-lg cursor-pointer"
                        >
                            Sign Up
                        </button>
                    </div>
                </form>

                <p className="text-center text-gray-500 text-sm mt-6">
                    Already have an account?{" "}
                    <Link to={"/login"}>
                        <span className="text-green-600 font-semibold cursor-pointer hover:underline">
                            Login
                        </span>
                    </Link>
                </p>
            </div>
        </div>
    );
};

export default PatientSignupPage;
