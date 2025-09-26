import React from "react";
import Navbar from "../components/Navbar";

const HomePage = () => {
    return (
        <div className="relative">
            <Navbar />

            <section className="relative flex flex-col items-center justify-center gap-6 text-center px-6
                          min-h-[calc(100vh-96px)] md:min-h-[calc(100vh-96px)] overflow-hidden
                          bg-gradient-to-b from-white/90 via-white/60 to-green-50">

                <h1 className="text-4xl md:text-6xl font-extrabold mb-4 z-10 text-gray-800
                       animate-fadeInDown">
                    Personalized Diets, Rooted in <span className="text-green-600">Ayurveda</span>
                </h1>

                <p className="text-lg md:text-2xl text-gray-700 mb-6 max-w-xl z-10
                      animate-fadeInUp">
                    An intelligent platform blending Ayurvedic wisdom with modern nutrition
                    for accurate, holistic diet care.
                </p>

                <button className="bg-gradient-to-r from-green-500 to-green-600 text-white
                           px-10 py-3 rounded-full font-semibold shadow-lg
                           hover:scale-105 hover:shadow-xl transition-all duration-300 z-10 cursor-pointer active:scale-100">
                    Get Started
                </button>

                {/* Background Text */}
                <span className="absolute bottom-0 left-1/2 -translate-x-1/2
                         text-green-200/30 text-[10rem] md:text-[16rem] font-bold
                         tracking-wide select-none pointer-events-none whitespace-nowrap
                         z-0 -rotate-6">
                    AYURVEDA
                </span>
            </section>

            {/* Fading Animations */}
            <style>
                {`
          @keyframes fadeInDown {
            0% { opacity: 0; transform: translateY(-20px); }
            100% { opacity: 1; transform: translateY(0); }
          }
          @keyframes fadeInUp {
            0% { opacity: 0; transform: translateY(20px); }
            100% { opacity: 1; transform: translateY(0); }
          }
          .animate-fadeInDown { animation: fadeInDown 1s ease-out; }
          .animate-fadeInUp { animation: fadeInUp 1s ease-out 0.3s; }
        `}
            </style>
        </div>
    );
};

export default HomePage;
