import React, { useState } from "react";

const DoctorSignup = () => {
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
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Doctor Signup Data:", formData);
    alert("Doctor Signup Successful!");
  };

  return (
    <div className="w-full min-h-[100vh] flex items-center justify-center bg-gradient-to-br from-green-50 via-white to-green-100 px-4">
      <div className="w-full max-w-4xl bg-white rounded-2xl shadow-2xl p-10">
        <h1 className="text-3xl font-bold text-center text-green-700 mb-8">
          Doctor Signup
        </h1>

        <form
          onSubmit={handleSubmit}
          className="grid grid-cols-1 md:grid-cols-2 gap-6"
        >
          {/* Basic Info */}
          <div>
            <label className="block font-semibold mb-1">Full Name</label>
            <input
              type="text"
              name="name"
              placeholder="Enter your full name"
              value={formData.name}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
              required
            />
          </div>

          <div>
            <label className="block font-semibold mb-1">Gender</label>
            <select
              name="gender"
              value={formData.gender}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
              required
            >
              <option value="">Select Gender</option>
              <option value="MALE">Male</option>
              <option value="FEMALE">Female</option>
              <option value="OTHER">Other</option>
            </select>
          </div>

          <div>
            <label className="block font-semibold mb-1">Date of Birth</label>
            <input
              type="date"
              name="dob"
              value={formData.dob}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
              required
            />
          </div>

          <div>
            <label className="block font-semibold mb-1">Email Address</label>
            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
              required
            />
          </div>

          <div>
            <label className="block font-semibold mb-1">Phone Number</label>
            <input
              type="text"
              name="phoneNumber"
              placeholder="Enter phone number"
              value={formData.phoneNumber}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
              required
            />
          </div>

          <div>
            <label className="block font-semibold mb-1">Password</label>
            <input
              type="password"
              name="password"
              placeholder="Enter password"
              value={formData.password}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
              required
            />
          </div>

          {/* Professional Details */}
          <div>
            <label className="block font-semibold mb-1">
              AYUSH Registration Number
            </label>
            <input
              type="text"
              name="registrationNumber"
              placeholder="Enter registration number"
              value={formData.registrationNumber}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
              required
            />
          </div>

          <div>
            <label className="block font-semibold mb-1">Qualification</label>
            <input
              type="text"
              name="qualification"
              placeholder="e.g. BAMS, MD"
              value={formData.qualification}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
              required
            />
          </div>

          <div>
            <label className="block font-semibold mb-1">
              Experience (in years)
            </label>
            <input
              type="number"
              name="experienceYears"
              placeholder="Enter experience"
              value={formData.experienceYears}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
              required
            />
          </div>

          {/* Ayurvedic Specialization */}
          <div>
            <label className="block font-semibold mb-1">
              Ayurvedic Specialization
            </label>
            <input
              type="text"
              name="specialization"
              placeholder="e.g. Panchakarma, Rasayana"
              value={formData.specialization}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
            />
          </div>

          <div>
            <label className="block font-semibold mb-1">
              Dosha Expertise
            </label>
            <input
              type="text"
              name="doshaExpertise"
              placeholder="e.g. Vata, Pitta, Kapha"
              value={formData.doshaExpertise}
              onChange={handleChange}
              className="border p-3 rounded-md w-full"
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