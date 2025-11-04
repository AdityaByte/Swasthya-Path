import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaClock, FaUserPlus, FaSignOutAlt } from "react-icons/fa";
import { ClipLoader } from "react-spinners";
import { jwtDecode } from "jwt-decode";
import DoctorConsultedCard from "../components/DoctorConsultedCard";

const DoctorDashboard = () => {
    const navigate = useNavigate();
    const backendURL = import.meta.env.VITE_BACKEND_URL;
    const authToken = localStorage.getItem("token");

    const [loading, setLoading] = useState(true);
    const [claims, setClaims] = useState(null);
    const [pendingPatients, setPendingPatients] = useState([]);
    const [latestArrivals, setLatestArrivals] = useState([]);

    useEffect(() => {
        if (!authToken) {
            navigate("/login");
            return;
        }
        try {
            const decodedToken = jwtDecode(authToken);
            setClaims(decodedToken);
            setLoading(false);
        } catch (error) {
            console.log("Invalid token");
            navigate("/login");
        }
    }, []);

    // Fetch pending patients
    useEffect(() => {
        const fetchPendingPatients = async () => {
            try {
                const response = await fetch(`${backendURL}/doctor/pending/patients`, {
                    headers: { Authorization: `Bearer ${authToken}` },
                });
                if (!response.ok) throw new Error("Failed to fetch pending patients.");
                const text = await response.text();

                const data = text ? JSON.parse(text): [];
                setPendingPatients(data);
            } catch (err) {
                console.error(err.message);
            }
        };
        fetchPendingPatients();
    }, [backendURL, authToken]);

    // SSE for real-time updates
    useEffect(() => {
        if (!authToken) return;

        const eventSource = new EventSource(`${backendURL}/doctor/consult?token=${authToken}`);

        eventSource.onmessage = (event) => {
            try {
                const data = JSON.parse(event.data);
                console.log("Received SSE event:", data);

                if (data.type === "PENDING") {
                    setLatestArrivals((prev) => [data.patient, ...prev].slice(0, 5));
                    setPendingPatients((prev) => [...prev, data.patient]);
                }
            } catch (err) {
                console.error("Error parsing SSE data:", err);
            }
        };

        eventSource.onerror = (error) => {
            console.error("SSE error:", error);
            eventSource.close();
        };

        return () => eventSource.close();
    }, [backendURL, authToken]);

    const handleSignout = () => {
        localStorage.clear();
        navigate("/");
    };

    if (loading || !claims) {
        return (
            <div className="flex flex-col items-center justify-center h-[80vh] text-center">
                <ClipLoader size={60} color="#16a34a" />
                <p className="mt-4 text-lg font-medium text-gray-700 animate-pulse">
                    Loading doctor dashboard...
                </p>
            </div>
        );
    }

    return (
        <div className="w-full min-h-screen bg-gradient-to-br from-green-50 via-white to-green-100 p-6">
            <div className="flex flex-col md:flex-row gap-6 items-start md:items-center justify-between mb-6">
                <div>
                    <h1 className="text-2xl md:text-3xl font-bold text-gray-800">Doctor Dashboard</h1>
                    <p className="text-sm text-gray-600 mt-1">
                        Manage patient consultations and monitor real-time arrivals.
                    </p>
                </div>

                <button
                    className="flex items-center gap-2 bg-white px-3 py-2 rounded-xl shadow hover:shadow-md transition cursor-pointer"
                    onClick={handleSignout}
                >
                    <FaSignOutAlt className="text-red-600" />
                    <span className="text-sm text-red-600 font-medium">Log out</span>
                </button>
            </div>

            <div className="grid grid-cols-1 lg:grid-cols-12 gap-6">
                {/* Doctor Profile */}
                <div className="col-span-1 lg:col-span-3 space-y-6">
                    <div className="bg-white rounded-2xl shadow p-5">
                        <div className="flex items-center gap-4">
                            <div className="w-14 h-14 rounded-full bg-green-100 flex items-center justify-center text-green-700 font-bold text-xl">
                                {claims.name
                                    ? claims.name
                                        .split(" ")
                                        .map((n) => n[0])
                                        .slice(0, 2)
                                        .join("")
                                    : "D"}
                            </div>
                            <div>
                                <div className="text-lg font-semibold text-gray-800">{claims.name}</div>
                                <div className="text-sm text-gray-500">{claims.id}</div>
                            </div>
                        </div>

                        <div className="mt-4 border-t pt-4 text-sm text-gray-600">
                            <div className="flex justify-between">
                                <span>Email</span>
                                <span className="font-medium">{claims.sub}</span>
                            </div>
                        </div>
                    </div>
                </div>

                {/* Latest Arrivals */}
                <div className="col-span-1 lg:col-span-4 space-y-6">
                    <div className="bg-white rounded-2xl shadow p-5">
                        <div className="flex items-center justify-between mb-4">
                            <h3 className="text-lg font-semibold flex items-center gap-2">
                                <FaUserPlus className="text-green-600" /> Latest Arrivals
                            </h3>
                        </div>
                        {latestArrivals.length === 0 ? (
                            <p className="text-sm text-gray-500">No new arrivals yet.</p>
                        ) : (
                            latestArrivals.map((event) => <DoctorConsultedCard event={event} status="pending" />)
                        )}
                    </div>
                </div>

                {/* Pending Patients */}
                <div className="col-span-1 lg:col-span-5 space-y-6">
                    <div className="bg-white rounded-2xl shadow p-5">
                        <div className="flex items-center justify-between mb-4">
                            <h3 className="text-lg font-semibold flex items-center gap-2">
                                <FaClock className="text-yellow-500" /> Pending Consultations
                            </h3>
                        </div>
                        {pendingPatients.length === 0 ? (
                            <p className="text-sm text-gray-500">No pending patients 🎉</p>
                        ) : (
                            pendingPatients.map((pendingPatient) => <DoctorConsultedCard event={pendingPatient} status="pending" />)
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default DoctorDashboard;
