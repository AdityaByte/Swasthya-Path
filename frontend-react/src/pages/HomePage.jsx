import React from "react";
import Navbar from "../components/Navbar";
import Feature from "../components/Feature";
import Footer from "../components/Footer";

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

            {/* What swasthya path does? */}
            <Feature />

            {/* How swasthya path works section */}
            <section className="px-6 md:px-20 py-16 relative">
                <h2 className="text-4xl font-bold text-center mb-12">How Swasthya Path Works <span className="text-green-800">?</span></h2>

                <div className="relative flex flex-col md:flex-row gap-10">
                    <div className="hidden md:flex flex-col items-center w-[5%] relative">
                        <div className="w-[2px] bg-green-300 h-full absolute top-0 left-1/2 -translate-x-1/2"></div>
                        {Array(4)
                            .fill(0)
                            .map((_, idx) => (
                                <div
                                    key={idx}
                                    className="w-6 h-6 bg-green-500 rounded-full border-2 border-white z-10 relative mt-24"
                                ></div>
                            ))}
                    </div>

                    <div className="w-full md:w-[95%] flex flex-col gap-10">
                        {[
                            {
                                title: "Input Patient Data",
                                desc: "Add age, gender, dosha, and meal habits quickly and easily.",
                            },
                            {
                                title: "Generate Diet Chart",
                                desc: "Automatic Ayurveda-compliant diet charts tailored to each patient.",
                            },
                            {
                                title: "Review & Customize",
                                desc: "Doctors can tweak meals or ingredients to personalize further.",
                            },
                            {
                                title: "Share with Patient",
                                desc: "Send diet charts digitally or print them for patient handouts.",
                            },
                        ].map((item, idx) => (
                            <div
                                key={idx}
                                className="border-2 border-green-200 rounded-lg p-6 shadow-lg hover:scale-102 transition-transform duration-300 bg-white relative md:ml-12"
                            >
                                <h3 className="text-xl font-semibold mb-2">{item.title}</h3>
                                <p className="text-gray-600">{item.desc}</p>
                            </div>
                        ))}
                    </div>
                </div>
            </section>


            {/* Footer section  */}
            <Footer />


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
