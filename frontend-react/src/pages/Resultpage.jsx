import React from "react";
import { useLocation } from "react-router-dom";

const ResultPage = () => {
    const location = useLocation();
    const data = location.state;
    const backendURL = import.meta.env.VITE_BACKEND_URL;

    if (!data) {
        return (
            <div className="flex w-full h-screen justify-center items-center bg-gray-100">
                <h1 className="text-2xl font-semibold text-red-500">No result data found!</h1>
            </div>
        );
    }

    return (
        <div className="min-h-screen px-10 py-8 bg-[var(--color-secondary)]">
            <h1 className="text-4xl font-bold mb-6 text-green-700 text-center">Your Ayurveda Plan</h1>

            <div className="bg-white shadow-md rounded-lg p-6 mb-6">
                <h2 className="text-2xl font-semibold mb-4 border-b pb-2 text-gray-800">Day Plan</h2>
                <ul className="space-y-2 text-gray-700">
                    <li><span className="font-semibold text-green-600">Breakfast:</span> {data.dayPlan.breakfast}</li>
                    <li><span className="font-semibold text-green-600">Lunch:</span> {data.dayPlan.lunch}</li>
                    <li><span className="font-semibold text-green-600">Dinner:</span> {data.dayPlan.dinner}</li>
                    <li><span className="font-semibold text-green-600">Snacks:</span> {data.dayPlan.snacks}</li>
                </ul>
            </div>

            <div className="bg-white shadow-md rounded-lg p-6 mb-6">
                <h2 className="text-2xl font-semibold mb-4 border-b pb-2 text-gray-800">Guidelines</h2>
                <ul className="list-disc list-inside space-y-2 text-gray-700">
                    {data.guidelines.map((g, idx) => (
                        <li key={idx}>{g}</li>
                    ))}
                </ul>
            </div>

            <div className="text-center">
                <a
                    href={`${backendURL + data.pdfURL}`}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="inline-block bg-green-600 hover:bg-green-700 text-white px-6 py-3 rounded-full font-semibold shadow-lg transition duration-300"
                >
                    Download PDF
                </a>
            </div>
        </div>
    );
};

export default ResultPage;
