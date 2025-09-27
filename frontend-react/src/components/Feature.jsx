import React from "react";
import { HiOutlineSparkles, HiOutlineDocumentText, HiOutlineUserGroup } from "react-icons/hi";
import { GiMeal, GiNotebook } from "react-icons/gi";

const Feature = () => {
  const features = [
    {
      icon: <HiOutlineSparkles className="w-16 h-16 text-green-500" />,
      title: "Automatic Ayurvedic Diets",
      desc: "Generates diet charts tailored to patient's age, gender, and dosha.",
    },
    {
      icon: <GiMeal className="w-16 h-16 text-green-500" />,
      title: "Nutrient Tracking",
      desc: "Tracks calories, vitamins, and Ayurvedic food properties like Hot/Cold and Rasa.",
    },
    {
      icon: <HiOutlineUserGroup className="w-16 h-16 text-green-500" />,
      title: "Patient Dashboard",
      desc: "Keeps patient info, meals, water intake, bowel habits, and more.",
    },
    {
      icon: <GiNotebook className="w-16 h-16 text-green-500" />,
      title: "Recipe-based Diets",
      desc: "Suggests meals with detailed nutrient and taste analysis.",
    },
    {
      icon: <HiOutlineDocumentText className="w-16 h-16 text-green-500" />,
      title: "Printable Reports",
      desc: "Generate easy-to-read diet charts to share with patients.",
    },
  ];

  return (
    <section className="py-20 bg-gray-50 px-6 md:px-20 text-center">
      <h2 className="text-4xl font-bold mb-12">What Swasthya Path Does <span className="text-green-800">?</span></h2>
      <div className="flex flex-col md:flex-row gap-10 justify-center flex-wrap">
        {features.map((item, idx) => (
          <div
            key={idx}
            className="flex flex-col items-center gap-4 p-6 bg-white rounded-2xl shadow-lg
                       hover:scale-105 hover:shadow-xl transition-transform duration-300"
          >
            {item.icon}
            <h3 className="font-semibold text-xl">{item.title}</h3>
            <p className="text-gray-600 text-sm md:text-base">{item.desc}</p>
          </div>
        ))}
      </div>
    </section>
  );
};

export default Feature;
