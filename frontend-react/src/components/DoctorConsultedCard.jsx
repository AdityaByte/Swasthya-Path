import React from "react";
import { useNavigate } from "react-router-dom";

const DoctorConsultedCard = ({ event, status = "Pending" }) => {
  const navigate = useNavigate();

  const handleApprove = () => {
    navigate(`/dashboard/doctor/approve/${event.patientId}`);
  };

  const statusColor =
    status === "Approved"
      ? "bg-green-100 text-green-700 border-green-300"
      : "bg-yellow-100 text-yellow-700 border-yellow-300";

  return (
    <div
      className="
        bg-white/80 backdrop-blur-sm border border-gray-200
        shadow-md hover:shadow-lg
        rounded-2xl p-5
        transition-all duration-300
        w-full mx-auto
      "
    >
      <div className="flex justify-between items-center mb-3">
        <h2 className="text-lg font-semibold text-gray-800 truncate">
          {event.patientName}
        </h2>
        <span
          className={`px-3 py-1 rounded-full text-xs sm:text-[12px] border ${statusColor}`}
        >
          {status}
        </span>
      </div>

      <p className="text-sm text-gray-600 mb-4">
        <strong>Patient ID:</strong> {event.patientId}
      </p>

      <div className="text-right">
        <button
          onClick={handleApprove}
          className="
            bg-gradient-to-r from-green-500 to-emerald-600
            hover:from-green-600 hover:to-emerald-700
            text-white font-medium
            text-sm px-4 py-2
            rounded-xl shadow-md hover:shadow-lg
            transition-all duration-300
          "
        >
          View & Approve
        </button>
      </div>
    </div>
  );
};

export default DoctorConsultedCard;
