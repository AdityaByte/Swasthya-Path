import React from "react";
import { useNavigate } from "react-router-dom";

const DoctorConsultedCard = ({ event, status = "Pending" }) => {
  const navigate = useNavigate();

  const handleApprove = () => {
    navigate(`/approve/${event.patientId}`);
  };

  const statusColor =
    status === "Approved"
      ? "bg-green-100 text-green-700 border-green-300"
      : "bg-yellow-100 text-yellow-700 border-yellow-300";

  return (
    <div className="bg-white shadow-md rounded-2xl p-5 border hover:shadow-lg transition-all duration-300">
      <div className="flex justify-between items-center mb-3">
        <h2 className="text-xl font-semibold text-gray-800">{event.patientName}</h2>
        <span className={`px-3 py-1 rounded-full text-sm border ${statusColor}`}>
          {status}
        </span>
      </div>

      <div className="grid grid-cols-2 gap-3 text-sm text-gray-600">
        <p><strong>Patient ID:</strong> {event.patientId}</p>
        <p><strong>Prakruti:</strong> {event.prakruti}</p>
        <p><strong>Vikruti:</strong> {event.vikruti}</p>
        <p><strong>Agni:</strong> {event.agni}</p>
        <p><strong>Guna:</strong> {event.guna}</p>
        <p><strong>Activity:</strong> {event.activityLevel}</p>
        <p><strong>Sleep:</strong> {event.sleepingSchedule}</p>
        <p><strong>Meal Frequency:</strong> {event.mealFrequency}</p>
      </div>

      {event.rasa?.length > 0 && (
        <div className="mt-3">
          <strong className="text-gray-700">Rasa:</strong>
          <div className="flex flex-wrap gap-2 mt-1">
            {event.rasa.map((r, i) => (
              <span
                key={i}
                className="px-2 py-1 bg-blue-100 text-blue-700 rounded-full text-xs border border-blue-300"
              >
                {r}
              </span>
            ))}
          </div>
        </div>
      )}

      <div className="mt-5 text-right">
        <button
          onClick={handleApprove}
          className="bg-green-600 hover:bg-green-700 text-white text-sm px-4 py-2 rounded-xl transition-all"
        >
          View & Approve
        </button>
      </div>
    </div>
  );
};

export default DoctorConsultedCard;
