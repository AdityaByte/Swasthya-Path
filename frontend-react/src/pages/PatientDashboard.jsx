import React from "react";
import { FaCheckCircle, FaExclamationCircle, FaPrint, FaFileExport, FaShareAlt } from "react-icons/fa";
import { MdOutlineFoodBank, MdOutlineAnalytics } from "react-icons/md";
import { GiCook } from "react-icons/gi";

/**
 * PatientDashboard component
 * - Paste into src/components/PatientDashboard.jsx
 * - Uses Tailwind CSS classes (assumes Tailwind is configured)
 * - Accepts a `patient` prop but works with default mock data
 */
const PatientDashboard = ({ patient: p }) => {
  // Mock fallback patient (so component renders even without a prop)
  const patient = p ?? {
    name: "Aditya Pawar",
    age: 22,
    gender: "Male",
    contact: "+91 9876543210",
    dosha: "Vata-Pitta", // or null if not assessed
    dietPlan: null, // or an object with plan data
    stats: { weight: 68, bmi: 23.1, waterIntake: "2.5L" },
    lastAssessment: "2025-09-10",
    appointments: [
      { date: "2025-10-01", doctor: "Dr. Sharma", department: "Ayurveda" },
    ],
  };

  // Example diet chart rows (recipe-based). In real app this comes from backend.
  const dietChart = patient.dietPlan?.meals ?? [
    { meal: "Morning", items: "Warm lemon water", cal: 20, rasa: "Amla", property: "Hot" },
    { meal: "Breakfast", items: "Moong dal chilla + mint chutney", cal: 320, rasa: "Katu", property: "Easy" },
    { meal: "Lunch", items: "Steamed rice + vegetable kitchari", cal: 520, rasa: "Lavana", property: "Easy" },
    { meal: "Evening", items: "Herbal tea + roasted makhana", cal: 120, rasa: "Tikta", property: "Light" },
    { meal: "Dinner", items: "Millet khichdi + ghee", cal: 450, rasa: "Madhura", property: "Warm" },
  ];

  // Nutrient summary (computed client-side here as an example)
  const nutrientSummary = dietChart.reduce(
    (acc, row) => {
      acc.calories += row.cal ?? 0;
      return acc;
    },
    { calories: 0 }
  );

  return (
    <div className="w-full min-h-screen bg-gradient-to-br from-green-50 via-white to-green-100 p-6">
      {/* Header */}
      <div className="flex flex-col md:flex-row gap-6 items-start md:items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl md:text-3xl font-bold text-gray-800">Patient Dashboard</h1>
          <p className="text-sm text-gray-600 mt-1">Ayurvedic diet & health overview — actionable insights at a glance</p>
        </div>

        {/* Quick actions */}
        <div className="flex gap-2 items-center">
          <button className="flex items-center gap-2 bg-white px-3 py-2 rounded-xl shadow hover:shadow-md transition">
            <FaPrint className="text-green-600" /> <span className="text-sm">Print Diet</span>
          </button>
          <button className="flex items-center gap-2 bg-white px-3 py-2 rounded-xl shadow hover:shadow-md transition">
            <FaFileExport className="text-green-600" /> <span className="text-sm">Export</span>
          </button>
          <button className="flex items-center gap-2 bg-white px-3 py-2 rounded-xl shadow hover:shadow-md transition">
            <FaShareAlt className="text-green-600" /> <span className="text-sm">Share</span>
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-4 gap-6">
        {/* Left column: Profile & Dosha */}
        <div className="col-span-1 space-y-6">
          {/* Profile card */}
          <div className="bg-white rounded-2xl shadow p-5">
            <div className="flex items-center gap-4">
              <div className="w-14 h-14 rounded-full bg-green-100 flex items-center justify-center text-green-700 font-bold text-xl">
                {patient.name.split(" ").map(n=>n[0]).slice(0,2).join("")}
              </div>
              <div>
                <div className="text-lg font-semibold text-gray-800">{patient.name}</div>
                <div className="text-sm text-gray-500">{patient.age} • {patient.gender}</div>
                <div className="text-sm text-gray-500 mt-1">{patient.contact}</div>
              </div>
            </div>

            <div className="mt-4 border-t pt-4 text-sm text-gray-600">
              <div className="flex justify-between">
                <span>Last Assessment</span>
                <span className="font-medium">{patient.lastAssessment ?? "—"}</span>
              </div>
              <div className="flex justify-between mt-2">
                <span>Assigned Diet</span>
                <span className="font-medium">{patient.dietPlan ? "Yes" : "No"}</span>
              </div>
            </div>
          </div>

          {/* Dosha card */}
          <div className={`rounded-2xl p-5 shadow flex flex-col items-center text-center
                          ${patient.dosha ? "bg-green-50 border border-green-200" : "bg-orange-50 border border-orange-200"}`}>
            <div className="text-3xl mb-2">
              {patient.dosha ? (
                <FaCheckCircle className="text-green-600" />
              ) : (
                <FaExclamationCircle className="text-orange-500" />
              )}
            </div>
            <h3 className="font-semibold text-lg">{patient.dosha ? "Dosha Assessed" : "Dosha Assessment Needed"}</h3>
            <p className="text-sm text-gray-600 mt-2">
              {patient.dosha ? `Type: ${patient.dosha}` : "Take the Dosha assessment to personalize diet & lifestyle recommendations."}
            </p>
            {!patient.dosha ? (
              <button className="mt-4 px-4 py-2 rounded-lg bg-orange-500 text-white font-medium hover:bg-orange-600">Take Assessment</button>
            ) : (
              <button className="mt-4 px-4 py-2 rounded-lg bg-green-600 text-white font-medium hover:bg-green-700">View Assessment</button>
            )}
          </div>

          {/* Quick stats */}
          <div className="bg-white rounded-2xl shadow p-4 grid grid-cols-3 gap-3 text-center">
            <div>
              <div className="text-sm text-gray-500">Weight</div>
              <div className="font-semibold text-green-600">{patient.stats?.weight ?? "--"} kg</div>
            </div>
            <div>
              <div className="text-sm text-gray-500">BMI</div>
              <div className="font-semibold text-green-600">{patient.stats?.bmi ?? "--"}</div>
            </div>
            <div>
              <div className="text-sm text-gray-500">Water</div>
              <div className="font-semibold text-green-600">{patient.stats?.waterIntake ?? "--"}</div>
            </div>
          </div>

          {/* Compliance / Integration */}
          <div className="bg-white rounded-2xl shadow p-4">
            <div className="flex items-center justify-between">
              <div>
                <div className="text-sm text-gray-500">Compliance</div>
                <div className="font-semibold text-gray-800">HIPAA-ready</div>
              </div>
              <div className="text-sm text-green-600 font-semibold">Secure</div>
            </div>
            <div className="mt-3 text-xs text-gray-500">
              Integrations: EHR / HIS (configurable)
            </div>
          </div>
        </div>

        {/* Middle: Core features (span 2) */}
        <div className="col-span-1 lg:col-span-2 space-y-6">
          {/* Food Search + quick nutrient analyzer */}
          <div className="bg-white rounded-2xl shadow p-5">
            <div className="flex flex-col md:flex-row gap-4 md:items-center md:justify-between">
              <div className="flex items-center gap-3 w-full md:w-2/3">
                <input
                  type="text"
                  placeholder="Search food (8,000+ items) — e.g., rice, moong dal, ghee..."
                  className="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                />
                <button className="px-4 py-3 bg-green-600 text-white rounded-lg">Search</button>
              </div>

              <div className="flex gap-3">
                <button className="px-3 py-2 bg-white border rounded-lg shadow-sm">Quick Analyzer</button>
                <button className="px-3 py-2 bg-white border rounded-lg shadow-sm">Upload Recipe</button>
              </div>
            </div>

            <div className="mt-4 grid grid-cols-1 md:grid-cols-4 gap-3">
              <div className="p-3 border rounded-lg text-center">
                <div className="text-xs text-gray-500">Calories</div>
                <div className="font-bold text-green-600 text-lg">{nutrientSummary.calories} kcal</div>
              </div>
              <div className="p-3 border rounded-lg text-center">
                <div className="text-xs text-gray-500">Protein</div>
                <div className="font-bold text-gray-800 text-lg">— g</div>
              </div>
              <div className="p-3 border rounded-lg text-center">
                <div className="text-xs text-gray-500">Dominant Rasa</div>
                <div className="font-bold text-gray-800 text-lg">Madhura</div>
              </div>
              <div className="p-3 border rounded-lg text-center">
                <div className="text-xs text-gray-500">Food Property</div>
                <div className="font-bold text-gray-800 text-lg">Easy → Hot</div>
              </div>
            </div>
          </div>

          {/* Diet Chart */}
          <div className="bg-white rounded-2xl shadow p-5">
            <div className="flex items-center justify-between mb-4">
              <div className="flex items-center gap-3">
                <MdOutlineFoodBank className="text-2xl text-green-600" />
                <h3 className="text-lg font-semibold">Today's Diet Chart</h3>
              </div>

              <div className="flex gap-2">
                <button className="px-3 py-2 bg-white border rounded-lg shadow-sm">Edit</button>
                <button className="px-3 py-2 bg-green-600 text-white rounded-lg">Generate</button>
              </div>
            </div>

            <div className="overflow-x-auto">
              <table className="w-full text-left">
                <thead>
                  <tr className="text-sm text-gray-500">
                    <th className="py-2">Meal</th>
                    <th className="py-2">Items</th>
                    <th className="py-2">Calories</th>
                    <th className="py-2">Rasa / Property</th>
                  </tr>
                </thead>
                <tbody>
                  {dietChart.map((row, i) => (
                    <tr key={i} className="border-t">
                      <td className="py-3 font-medium">{row.meal}</td>
                      <td className="py-3">{row.items}</td>
                      <td className="py-3">{row.cal}</td>
                      <td className="py-3">{row.rasa} • {row.property}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            <div className="mt-4 flex gap-3">
              <button className="px-4 py-2 bg-white border rounded-lg">Export PDF</button>
              <button className="px-4 py-2 bg-green-600 text-white rounded-lg">Print Chart</button>
              <button className="px-4 py-2 bg-white border rounded-lg">Share</button>
            </div>
          </div>

          {/* Analytics / Reports */}
          <div className="bg-white rounded-2xl shadow p-5 flex flex-col md:flex-row gap-4 items-stretch">
            <div className="flex-1">
              <div className="flex items-center gap-3 mb-3">
                <MdOutlineAnalytics className="text-2xl text-green-600" />
                <h4 className="font-semibold">Nutrition Summary</h4>
              </div>
              <p className="text-sm text-gray-600">
                View trends over time for calories, macros, and Ayurvedic properties like rasa and food temperature.
              </p>

              <div className="mt-4 grid grid-cols-2 gap-3">
                <div className="p-3 border rounded">
                  <div className="text-xs text-gray-500">Avg Calories / day</div>
                  <div className="font-semibold text-gray-800">1420 kcal</div>
                </div>
                <div className="p-3 border rounded">
                  <div className="text-xs text-gray-500">Meals Logged / week</div>
                  <div className="font-semibold text-gray-800">18</div>
                </div>
                <div className="p-3 border rounded">
                  <div className="text-xs text-gray-500">Avg Water Intake</div>
                  <div className="font-semibold text-gray-800">2.4 L</div>
                </div>
                <div className="p-3 border rounded">
                  <div className="text-xs text-gray-500">Assessment Status</div>
                  <div className="font-semibold text-gray-800">{patient.dosha ? "Completed" : "Pending"}</div>
                </div>
              </div>
            </div>

            {/* Recent reports list */}
            <div className="w-full md:w-64 bg-gray-50 rounded p-3">
              <div className="text-sm text-gray-500 mb-2">Recent Reports</div>
              <ul className="space-y-2">
                <li className="flex items-center justify-between text-sm">
                  <span>Diet Chart - Sep 10</span>
                  <button className="text-green-600 text-sm">View</button>
                </li>
                <li className="flex items-center justify-between text-sm">
                  <span>Dosha Result - Sep 10</span>
                  <button className="text-green-600 text-sm">View</button>
                </li>
                <li className="flex items-center justify-between text-sm">
                  <span>Nutrition Summary</span>
                  <button className="text-green-600 text-sm">View</button>
                </li>
              </ul>
            </div>
          </div>
        </div>

        {/* Right column: Actions & history */}
        <div className="col-span-1 space-y-6">
          {/* Upcoming appointment card */}
          <div className="bg-white rounded-2xl shadow p-5">
            <div className="text-sm text-gray-500">Upcoming</div>
            {patient.appointments?.length ? (
              <div className="mt-3">
                <div className="font-medium">{patient.appointments[0].doctor}</div>
                <div className="text-sm text-gray-500">{patient.appointments[0].date}</div>
                <div className="text-xs text-gray-400 mt-2">{patient.appointments[0].department}</div>
              </div>
            ) : (
              <div className="text-gray-500 mt-2">No upcoming appointments</div>
            )}
          </div>

          {/* Recent activity */}
          <div className="bg-white rounded-2xl shadow p-5">
            <div className="text-sm text-gray-500">Recent Activity</div>
            <ul className="mt-3 space-y-2 text-sm text-gray-600">
              <li>Logged breakfast - Sep 24</li>
              <li>Dosha assessment completed - Sep 10</li>
              <li>Diet chart generated - Sep 10</li>
            </ul>
          </div>

          {/* CTA / Quick actions */}
          <div className="bg-white rounded-2xl shadow p-5 flex flex-col gap-3">
            <button className="w-full px-4 py-2 bg-green-600 text-white rounded-lg">Start Dosha Assessment</button>
            <button className="w-full px-4 py-2 bg-white border rounded-lg">Generate Diet Plan</button>
            <button className="w-full px-4 py-2 bg-white border rounded-lg">Schedule Follow-up</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PatientDashboard;
