import React, { useState, useEffect, useMemo } from "react";
import { useNavigate } from "react-router-dom";
import {
  FaUserMd,
  FaUsers,
  FaCog,
  FaTachometerAlt,
  FaSignOutAlt,
} from "react-icons/fa";
import DoctorSignup from "./signup_pages/DoctorSignupPage";
import appIcon from "../assets/images/logo.jpg";
import { toast } from "react-toastify";

const AdminDashboard = () => {
  const navigate = useNavigate();
  const backendURL = useMemo(() => import.meta.env.VITE_BACKEND_URL, [])
  const authToken = localStorage.getItem("token");
  const [activeSection, setActiveSection] = useState("dashboard");
  const [backendData, setBackendData] = useState({
    "patients": 0,
    "doctors": 0,
    "pendingPatients": 0,
  });

  useEffect(() => {
    if (!authToken) {
      navigate("/login");
    }

    const fetchData = async () => {
      // Since we have to fetch the patients, pending patients, and doctors data.
      try {

        const response = await fetch(`${backendURL}/admin/fetch/data`, {
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${authToken}`,
          },
          method: "GET",
        })

        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.response);
        }

        // Else we need to save the data.
        setBackendData({
          patients: data.patients,
          doctors: data.doctors,
          pendingPatients: data.pendingPatients,
        })

      } catch (error) {
        toast.error(error.message);
        return;
      }

    }

    fetchData();

  }, []);

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="flex min-h-screen bg-gradient-to-br from-green-50 via-white to-green-100">

      <aside className="w-16 md:w-64 bg-white shadow-lg border-r border-gray-200 flex flex-col justify-between transition-all duration-300">
        <div>

          <div className="p-4 border-b flex items-center justify-center md:justify-start md:gap-3">

            <img
              src={appIcon}
              alt="App Icon"
              width={40}
              height={40}
              className="block md:hidden"
            />

            <div className="hidden md:flex items-center gap-3">
              <img src={appIcon} alt="App Icon" width={50} height={50} />
              <div>
                <h2 className="text-2xl font-bold text-green-600">
                  Admin Panel
                </h2>
                <p className="text-sm text-gray-500 mt-1">Swasthya Path</p>
              </div>
            </div>
          </div>

          <nav className="mt-6">
            {[
              { id: "dashboard", label: "Dashboard", icon: <FaTachometerAlt /> },
              { id: "doctors", label: "Doctors", icon: <FaUserMd /> },
              { id: "patients", label: "Patients", icon: <FaUsers /> },
              { id: "settings", label: "Settings", icon: <FaCog /> },
            ].map((item) => (
              <button
                key={item.id}
                onClick={() => setActiveSection(item.id)}
                className={`flex items-center gap-3 w-full text-left px-5 py-3 text-sm font-medium transition ${activeSection === item.id
                  ? "bg-green-100 text-green-700 border-l-4 border-green-500"
                  : "text-gray-700 hover:bg-gray-50"
                  }`}
              >
                <span className="text-lg">{item.icon}</span>
                <span className="hidden md:inline">{item.label}</span>
              </button>
            ))}
          </nav>
        </div>

        <div className="p-4 border-t">
          <button
            onClick={handleLogout}
            className="flex items-center gap-3 w-full text-left text-red-600 hover:text-red-700 text-sm font-medium transition"
          >
            <FaSignOutAlt className="text-lg" />
            <span className="hidden md:inline">Logout</span>
          </button>
        </div>
      </aside>

      <main className="flex-1 p-6 md:p-8 overflow-y-auto">
        {activeSection === "dashboard" && (
          <div>
            <h1 className="text-2xl font-bold mb-4 text-gray-800">
              Dashboard Overview
            </h1>
            <div className="grid grid-cols-1 sm:grid-cols-3 gap-6">
              <div className="bg-white p-5 rounded-2xl shadow">
                <h3 className="text-gray-500 text-sm mb-2">Total Doctors</h3>
                <p className="text-2xl font-bold text-green-600">{backendData.doctors}</p>
              </div>
              <div className="bg-white p-5 rounded-2xl shadow">
                <h3 className="text-gray-500 text-sm mb-2">Total Patients</h3>
                <p className="text-2xl font-bold text-green-600">{backendData.patients}</p>
              </div>
              <div className="bg-white p-5 rounded-2xl shadow">
                <h3 className="text-gray-500 text-sm mb-2">Pending Approvals</h3>
                <p className="text-2xl font-bold text-green-600">{backendData.pendingPatients}</p>
              </div>
            </div>
          </div>
        )}

        {activeSection === "doctors" && (
          <div className="overflow-y-auto max-h-[90vh]">
            <DoctorSignup />
          </div>
        )}

        {activeSection === "patients" && (
          <div>
            <h1 className="text-2xl font-bold mb-4 text-gray-800">
              Manage Patients
            </h1>
            <div className="bg-white p-5 rounded-2xl shadow text-gray-500">
              Patient management module coming soon...
            </div>
          </div>
        )}

        {activeSection === "settings" && (
          <div>
            <h1 className="text-2xl font-bold mb-4 text-gray-800">Settings</h1>
            <div className="bg-white p-5 rounded-2xl shadow text-gray-500">
              Admin settings and preferences will be configured here.
            </div>
          </div>
        )}
      </main>
    </div>
  );
};

export default AdminDashboard;
