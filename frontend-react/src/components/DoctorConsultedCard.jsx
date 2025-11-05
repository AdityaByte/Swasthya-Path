import React from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

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
    <motion.div
      whileHover={{ scale: 1.02 }}
      transition={{ duration: 0.3 }}
      className="
        bg-white/80 backdrop-blur-sm border border-gray-200
        shadow-md hover:shadow-xl
        rounded-2xl p-5
        transition-all duration-300
        w-full mx-auto
      "
    >
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-lg font-semibold text-gray-800 truncate">
          {event.patientName}
        </h2>
        <span
          className={`px-3 py-1 rounded-full text-xs sm:text-[12px] border ${statusColor}`}
        >
          {status}
        </span>
      </div>

      <div className="grid grid-cols-2 gap-x-4 gap-y-2 text-[13px] text-gray-700">
        <p>
          <strong>Patient ID:</strong> {event.patientId}
        </p>
        <p>
          <strong>Prakruti:</strong> {event.prakruti}
        </p>
        <p>
          <strong>Vikruti:</strong> {event.vikruti}
        </p>
        <p>
          <strong>Agni:</strong> {event.agni}
        </p>
        <p>
          <strong>Guna:</strong> {event.guna}
        </p>
        <p>
          <strong>Activity:</strong> {event.activityLevel}
        </p>
        <p>
          <strong>Sleep:</strong> {event.sleepingSchedule}
        </p>
        <p>
          <strong>Meal Freq:</strong> {event.mealFrequency}
        </p>
      </div>

      {event.rasa?.length > 0 && (
        <div className="mt-4">
          <strong className="text-gray-700 text-sm">Rasa:</strong>
          <div className="flex flex-wrap gap-2 mt-2">
            {event.rasa.map((r, i) => (
              <span
                key={i}
                className="px-2 py-1 bg-blue-50 text-blue-700 rounded-full text-xs border border-blue-200 shadow-sm"
              >
                {r}
              </span>
            ))}
          </div>
        </div>
      )}

      <div className="mt-6 text-right">
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
    </motion.div>
  );
};

export default DoctorConsultedCard;
