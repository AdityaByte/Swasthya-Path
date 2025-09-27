import React from "react";
import { FaUserInjured, FaUserMd } from "react-icons/fa";
import { Link } from "react-router-dom";

const SignupPage1 = () => {
  return (
    <div className="relative w-full h-[100vh] flex flex-col md:flex-row items-center justify-center gap-10 px-5 overflow-hidden">

      <div className="absolute inset-0 bg-gradient-to-br from-green-100 via-white to-blue-100"></div>
      <div className="absolute w-[600px] h-[600px] bg-green-200/40 rounded-full blur-3xl -top-40 -left-40"></div>
      <div className="absolute w-[500px] h-[500px] bg-blue-200/40 rounded-full blur-3xl bottom-[-150px] right-[-150px]"></div>

      {/* Patient Signup */}
      <Link to={"/signup/patient"}>
        <div className="relative z-10 w-72 h-80 flex flex-col items-center justify-center rounded-2xl shadow-lg bg-white/90 backdrop-blur-sm cursor-pointer
                      hover:scale-105 hover:shadow-2xl transition-transform duration-300 group">
          <FaUserInjured className="text-green-600 text-6xl mb-5 group-hover:scale-110 transition-transform duration-300" />
          <h2 className="text-2xl font-semibold text-green-700 mb-2">Patient</h2>
          <p className="text-gray-500 text-center px-4">
            Create an account to book appointments, manage health records, and connect with doctors.
          </p>
        </div>
      </Link>

      {/* Doctor Signup */}
      <Link to={"/signup/doctor"}>
        <div className="relative z-10 w-72 h-80 flex flex-col items-center justify-center rounded-2xl shadow-lg bg-white/90 backdrop-blur-sm cursor-pointer
                      hover:scale-105 hover:shadow-2xl transition-transform duration-300 group">
          <FaUserMd className="text-blue-600 text-6xl mb-5 group-hover:scale-110 transition-transform duration-300" />
          <h2 className="text-2xl font-semibold text-blue-700 mb-2">Doctor</h2>
          <p className="text-gray-500 text-center px-4">
            Join as a doctor to provide consultations, manage patients, and grow your practice.
          </p>
        </div>
      </Link>
    </div>
  );
};

export default SignupPage1;
