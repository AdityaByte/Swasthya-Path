import React, { useState } from "react";

const DoctorSignup = ({ authToken }) => {

  const backendURL = import.meta.env.VITE_BACKEND_URL;

  const [formData, setFormData] = useState({
    name: "",
    gender: "",
    dob: "",
    email: "",
    phoneNumber: "",
    password: "",
    registrationNumber: "",
    qualification: "",
    experienceYears: "",
    specialization: "",
    doshaExpertise: "",
    assessment: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(
      {
        ...formData,
        [name]: name === "experienceYears" ? parseFloat(value) : value,
      }
    );
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Sending data to the backend Route;
    fetch(`${backendURL}/api/admin/create/doctor`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${authToken}`,
      },
      body: JSON.stringify(formData),
    })
    .then(async response => {
      const body = await response.json();
      if (!response.ok) {
        throw new Error(`Something went wrong, ${body}`);
        return;
      }
      // If the response is ok we have to Generate an alert.
      alert("Doctor entity has been created successfully");
    })
    .catch(error => {
      console.error(error.message);
    })
  };

  return (
    <div className="w-full min-h-[100vh] flex items-center justify-center">
      <div className="w-full max-w-4xl bg-white rounded-2xl border-2 border-green-400 p-10">
        <h1 className="text-3xl font-bold text-center text-green-700 mb-8">
          Doctor Signup
        </h1>

        <form
          onSubmit={handleSubmit}
          className="grid grid-cols-1 md:grid-cols-2 gap-6"
        >
          {/* Basic Details */}
          <div className="flex flex-col">
            <label className="block font-semibold mb-1">Full Name</label>
            <input
              type="text"
              name="name"
              placeholder="Enter your full name"
              value={formData.name}
              onChange={handleChange}
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
          </div>

          <div className="flex flex-col">
            <label className="block font-semibold mb-1">Gender</label>
            <select
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

          <div className="flex flex-col">
            <label className="block font-semibold mb-1">Date of Birth</label>
            <input
              type="date"
              name="dob"
              value={formData.dob}
              onChange={handleChange}
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
          </div>

          <div className="flex flex-col">
            <label className="block font-semibold mb-1">Email Address</label>
            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleChange}
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
          </div>

          <div className="flex flex-col">
            <label className="block font-semibold mb-1">Phone Number</label>
            <input
              type="text"
              name="phoneNumber"
              placeholder="Enter phone number"
              value={formData.phoneNumber}
              onChange={handleChange}
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
          </div>

          <div className="flex flex-col">
            <label className="block font-semibold mb-1">Password</label>
            <input
              type="password"
              name="password"
              placeholder="Enter password"
              value={formData.password}
              onChange={handleChange}
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
          </div>

          {/* Professional Details */}
          <div className="flex flex-col">
            <label className="block font-semibold mb-1">
              AYUSH Registration Number
            </label>
            <input
              type="text"
              name="registrationNumber"
              placeholder="Enter registration number"
              value={formData.registrationNumber}
              onChange={handleChange}
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
          </div>

          <div className="flex flex-col">
            <label className="block font-semibold mb-1">Qualification</label>
            <input
              type="text"
              name="qualification"
              placeholder="e.g. BAMS, MD"
              value={formData.qualification}
              onChange={handleChange}
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
          </div>

          <div className="flex flex-col">
            <label className="block font-semibold mb-1">
              Experience (in years)
            </label>
            <input
              type="number"
              name="experienceYears"
              placeholder="Enter experience"
              value={formData.experienceYears}
              onChange={handleChange}
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
              required
            />
          </div>

          {/* Ayurvedic Specialization */}
          <div className="flex flex-col">
            <label className="block font-semibold mb-1">
              Ayurvedic Specialization
            </label>
            <input
              type="text"
              name="specialization"
              placeholder="e.g. Panchakarma, Rasayana"
              value={formData.specialization}
              onChange={handleChange}
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>

          <div className="flex flex-col">
            <label className="block font-semibold mb-1">
              Dosha Expertise
            </label>
            <input
              type="text"
              name="doshaExpertise"
              placeholder="e.g. Vata, Pitta, Kapha"
              value={formData.doshaExpertise}
              onChange={handleChange}
              className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>

          <button
            type="submit"
            className="md:col-span-2 bg-green-600 text-white font-semibold py-3 rounded-md hover:bg-green-700 transition duration-300"
          >
            Submit
          </button>
        </form>
      </div>
    </div>
  );
};

export default DoctorSignup;
