import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaCheckCircle, FaExclamationCircle, FaPrint, FaFileExport, FaShareAlt, FaSignOutAlt } from "react-icons/fa";
import { MdOutlineFoodBank, MdOutlineAnalytics } from "react-icons/md";

const PatientDashboard = () => {
  const [responseData, setResponseData] = useState({
    patient: {
      name: "",
      age: "",
      gender: "",
      phoneNumber: "",
      prakruti: "",
      vikruti: "",
      bmr: "",
      preferredFoodGenre: "",
      macroNutrient: {},
      waterIntake: "",
      mealFrequency: "",
      assessmentDone: false,
      rasa: [],
      agni: "",
    },
    healthResponse: {
      dayPlan: [],
      guidelines: [],
    },
  });

  const navigate = useNavigate();
  const backendURL = import.meta.env.VITE_BACKEND_URL;
  const authToken = localStorage.getItem("token");

  // Token check
  useEffect(() => {
    if (!authToken) {
      navigate("/login");
      return;
    }
  }, []);

  // Fetch patient data
  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await fetch(`${backendURL}/patient/diet`, {
          method: "POST",
          headers: {
            Authorization: `Bearer ${authToken}`,
            "Content-Type": "application/json",
          },
        });

        if (!res.ok) throw new Error("Failed to fetch patient data");

        const data = await res.json();
        setResponseData(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  const { patient, healthResponse } = responseData;

  const nutrientSummary = healthResponse.dayPlan.reduce(
    (acc, meal) => {
      acc.calories += meal.calories ?? 0;
      return acc;
    },
    { calories: 0 }
  );

  const handleSignout = () => {
    localStorage.clear()
    navigate("/")
  }

  return (
    <div className="w-full min-h-screen bg-gradient-to-br from-green-50 via-white to-green-100 p-6">
      {/* header */}
      <div className="flex flex-col md:flex-row gap-6 items-start md:items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl md:text-3xl font-bold text-gray-800">Patient Dashboard</h1>
          <p className="text-sm text-gray-600 mt-1">Ayurvedic diet & health overview — actionable insights at a glance</p>
        </div>

        {/* Quick actions buttons */}
        <div className="grid grid-cols-2 md:flex gap-2 items-center justify-center md:justify-start">
          <button className="flex items-center gap-2 bg-white px-3 py-2 rounded-xl shadow hover:shadow-md transition cursor-pointer">
            <FaPrint className="text-green-600" />
            <span className="text-sm">Print Diet</span>
          </button>
          <button className="flex items-center gap-2 bg-white px-3 py-2 rounded-xl shadow hover:shadow-md transition cursor-pointer">
            <FaFileExport className="text-green-600" />
            <span className="text-sm">Export</span>
          </button>
          <button className="flex items-center gap-2 bg-white px-3 py-2 rounded-xl shadow hover:shadow-md transition cursor-pointer">
            <FaShareAlt className="text-green-600" />
            <span className="text-sm">Share</span>
          </button>
          <button className="flex items-center gap-2 bg-white px-3 py-2 rounded-xl shadow hover:shadow-md transition cursor-pointer">
            <FaSignOutAlt className="text-red-600" />
            <span onClick={() => handleSignout()} className="text-sm text-red-600 font-medium">Log out</span>
          </button>

        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-12 gap-6">
        {/* left-col */}
        <div className="col-span-1 lg:col-span-3 space-y-6">
          {/* profile-card */}
          <div className="bg-white rounded-2xl shadow p-5">
            <div className="flex items-center gap-4">
              <div className="w-14 h-14 rounded-full bg-green-100 flex items-center justify-center text-green-700 font-bold text-xl">
                {patient.name.split(" ").map(n => n[0]).slice(0, 2).join("")}
              </div>
              <div>
                <div className="text-lg font-semibold text-gray-800">{patient.name}</div>
                <div className="text-sm text-gray-500">{patient.age} • {patient.gender}</div>
                <div className="text-sm text-gray-500 mt-1">{patient.phoneNumber}</div>
              </div>
            </div>

            <div className="mt-4 border-t pt-4 text-sm text-gray-600">
              <div className="flex justify-between">
                <span>Assessment Done</span>
                <span className="font-medium">{patient.assessmentDone ? "Yes" : "No"}</span>
              </div>
              <div className="flex justify-between mt-2">
                <span>Assigned Diet</span>
                <span className="font-medium">{healthResponse.dayPlan.length ? "Yes" : "No"}</span>
              </div>
            </div>
          </div>
        </div>

        {/* middle: diet-chart */}
        <div className="col-span-1 lg:col-span-6 space-y-6">
          <div className="bg-white rounded-2xl shadow p-5">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold">Today's Diet Chart</h3>
            </div>
            <div className="overflow-x-auto">
              <table className="w-full text-left table-auto border-collapse">
                <thead>
                  <tr className="text-sm text-gray-500">
                    <th className="py-2 px-3">Meal</th>
                    <th className="py-2 px-3">Items</th>
                    <th className="py-2 px-3">Calories</th>
                    <th className="py-2 px-3">Rasa / Property</th>
                  </tr>
                </thead>
                <tbody>
                  {healthResponse.dayPlan.map((meal, i) => (
                    <tr key={i} className="border-t">
                      <td className="py-3 px-3 font-medium whitespace-nowrap">{meal.meal}</td>
                      <td className="py-3 px-3 break-words max-w-[400px]">{meal.items}</td>
                      <td className="py-3 px-3 whitespace-nowrap">{meal.calories}</td>
                      <td className="py-3 px-3 break-words max-w-[200px]">{meal.rasa} • {meal.property}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

          </div>
        </div>

        {/* right-col: nutrient, total calorie intake. */}
        <div className="col-span-1 lg:col-span-3 space-y-6">
          <div className="bg-white rounded-2xl shadow p-5">
            <h4 className="text-lg font-semibold mb-2">Nutrition Summary</h4>
            <p className="text-sm text-gray-600">Total calories: {nutrientSummary.calories} kcal</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PatientDashboard;
