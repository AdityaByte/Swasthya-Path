import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import InputWithLabel from "../../components/InputWithLabel";

const Assessment = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { email } = location.state || {};
  const backendURL = import.meta.env.VITE_BACKEND_URL;

  const [formData, setFormData] = useState({
    basicAssessment: {
      activityLevel: "",
      waterIntake: "",
      sleepingSchedule: "",
      mealFrequency: "",
      hoursOfSleep: "",
      preferredFoodGenre: "",
    },
    prakrutiAssessment: {
      bodyType: "",
      skinNature: "",
      digestionStrength: "",
      energyPattern: "",
      sleepNature: "",
    },
    vikrutiAssessment: {
      currentConcerns: "",
      currentEnergy: "",
      currentSleep: "",
      digestionToday: "",
    },
    healthAssessment: {
      healthIssues: "",
      allergies: "",
      preferredTastes: "",
      guna: [],
    },
  });

  const [loader, setLoader] = useState(false);

  const updateSection = (section, name, value) => {
    setFormData((prev) => ({
      ...prev,
      [section]: {
        ...prev[section],
        [name]: value,
      },
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoader(true);

    const payload = { ...formData, email };

    fetch(`${backendURL}/api/assessment`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then(async (res) => {
        setLoader(false);
        if (!res.ok) throw new Error("Submission failed");
        const data = await res.json();
        navigate("/dashboard/patient", { state: data });
      })
      .catch((err) => console.error(err));
  };

  const sectionClass =
    "bg-white shadow-lg rounded-2xl p-6 border border-gray-200 space-y-4";

  const gridClass = "grid grid-cols-1 md:grid-cols-2 gap-4";

  return (
    <div className="min-h-screen py-8 px-4 md:px-10">
      <h1 className="text-4xl font-bold mb-8 text-center text-green-700">
        Ayurvedic Assessment Form
      </h1>

      <form onSubmit={handleSubmit} className="max-w-7xl mx-auto space-y-8">
        {/* Basic Assessment */}
        <div className={sectionClass}>
          <h2 className="text-2xl font-semibold text-gray-800 text-center border-b pb-2 mb-4">
            Basic Assessment
          </h2>
          <div className={gridClass}>
            <InputWithLabel
              label="Activity Level"
              name="activityLevel"
              value={formData.basicAssessment.activityLevel}
              onChange={(e) =>
                updateSection("basicAssessment", e.target.name, e.target.value)
              }
              type="select"
              options={["Sedentary", "Moderate", "Active"]}
            />
            <InputWithLabel
              label="Water Intake (Litres/Day)"
              name="waterIntake"
              value={formData.basicAssessment.waterIntake}
              onChange={(e) =>
                updateSection("basicAssessment", e.target.name, e.target.value)
              }
              type="number"
            />
            <InputWithLabel
              label="Sleeping Schedule"
              name="sleepingSchedule"
              value={formData.basicAssessment.sleepingSchedule}
              onChange={(e) =>
                updateSection("basicAssessment", e.target.name, e.target.value)
              }
              type="select"
              options={["Early", "Late"]}
            />
            <InputWithLabel
              label="Meal Frequency"
              name="mealFrequency"
              value={formData.basicAssessment.mealFrequency}
              onChange={(e) =>
                updateSection("basicAssessment", e.target.name, e.target.value)
              }
              type="select"
              options={["2", "3", "4"]}
            />
            <InputWithLabel
              label="Hours of Sleep"
              name="hoursOfSleep"
              value={formData.basicAssessment.hoursOfSleep}
              onChange={(e) =>
                updateSection("basicAssessment", e.target.name, e.target.value)
              }
              type="number"
            />
            <InputWithLabel
              label="Preferred Food Genre"
              name="preferredFoodGenre"
              value={formData.basicAssessment.preferredFoodGenre}
              onChange={(e) =>
                updateSection("basicAssessment", e.target.name, e.target.value)
              }
              type="select"
              options={["Veg", "Non-Veg", "Mixed"]}
            />
          </div>
        </div>

        {/* Prakruti Assessment */}
        <div className={sectionClass}>
          <h2 className="text-2xl font-semibold text-gray-800 text-center border-b pb-2 mb-4">
            Prakruti Assessment (Innate Constitution)
          </h2>
          <div className={gridClass}>
            {[
              { label: "Body Type", name: "bodyType", options: ["Slim", "Medium", "Sturdy"] },
              { label: "Skin Nature", name: "skinNature", options: ["Dry", "Oily", "Normal"] },
              { label: "Digestion Strength", name: "digestionStrength", options: ["Weak", "Normal", "Strong"] },
              { label: "Energy Pattern", name: "energyPattern", options: ["Low", "Moderate", "High"] },
              { label: "Sleep Nature", name: "sleepNature", options: ["Light", "Moderate", "Deep"] },
            ].map((field) => (
              <InputWithLabel
                key={field.name}
                label={field.label}
                name={field.name}
                value={formData.prakrutiAssessment[field.name]}
                onChange={(e) =>
                  updateSection("prakrutiAssessment", e.target.name, e.target.value)
                }
                type="select"
                options={field.options}
              />
            ))}
          </div>
        </div>

        {/* Vikruti Assessment */}
        <div className={sectionClass}>
          <h2 className="text-2xl font-semibold text-gray-800 text-center border-b pb-2 mb-4">
            Vikruti Assessment (Current Imbalance)
          </h2>
          <div className={gridClass}>
            <InputWithLabel
              label="Current Main Concerns"
              name="currentConcerns"
              value={formData.vikrutiAssessment.currentConcerns}
              onChange={(e) =>
                updateSection("vikrutiAssessment", e.target.name, e.target.value)
              }
              type="textarea"
              placeholderText="Stress, acidity, poor sleep..."
            />
            <InputWithLabel
              label="Current Energy Pattern"
              name="currentEnergy"
              value={formData.vikrutiAssessment.currentEnergy}
              onChange={(e) =>
                updateSection("vikrutiAssessment", e.target.name, e.target.value)
              }
              type="select"
              options={["Low", "Moderate", "High"]}
            />
            <InputWithLabel
              label="Current Sleep Pattern"
              name="currentSleep"
              value={formData.vikrutiAssessment.currentSleep}
              onChange={(e) =>
                updateSection("vikrutiAssessment", e.target.name, e.target.value)
              }
              type="select"
              options={["Light", "Moderate", "Deep"]}
            />
            <InputWithLabel
              label="Digestion Today"
              name="digestionToday"
              value={formData.vikrutiAssessment.digestionToday}
              onChange={(e) =>
                updateSection("vikrutiAssessment", e.target.name, e.target.value)
              }
              type="select"
              options={["Poor", "Moderate", "Good"]}
            />
          </div>
        </div>

        {/* Health Assessment */}
        <div className={sectionClass}>
          <h2 className="text-2xl font-semibold text-gray-800 text-center border-b pb-2 mb-4">
            Health & Preferences
          </h2>
          <div className={gridClass}>
            <InputWithLabel
              label="Health Issues"
              name="healthIssues"
              value={formData.healthAssessment.healthIssues}
              onChange={(e) =>
                updateSection("healthAssessment", e.target.name, e.target.value)
              }
              type="textarea"
              placeholderText="Diabetes, BP..."
            />
            <InputWithLabel
              label="Allergies"
              name="allergies"
              value={formData.healthAssessment.allergies}
              onChange={(e) =>
                updateSection("healthAssessment", e.target.name, e.target.value)
              }
              type="textarea"
              placeholderText="Peanuts, Gluten..."
            />
            <InputWithLabel
              label="Preferred Tastes (RASA)"
              name="preferredTastes"
              value={formData.healthAssessment.preferredTastes}
              onChange={(e) =>
                updateSection("healthAssessment", e.target.name, e.target.value)
              }
              type="textarea"
              placeholderText="Sweet, Salty..."
            />
            <div className="col-span-1 md:col-span-2">
              <label className="block mb-1 font-medium text-gray-700">GUNA</label>
              <div className="flex gap-4">
                {["Sattva", "Rajas", "Tamas"].map((g) => (
                  <label key={g} className="flex items-center gap-2">
                    <input
                      type="checkbox"
                      name="guna"
                      value={g}
                      checked={formData.healthAssessment.guna.includes(g)}
                      onChange={(e) => {
                        const checked = e.target.checked;
                        setFormData((prev) => {
                          const newGuna = checked
                            ? [...prev.healthAssessment.guna, g]
                            : prev.healthAssessment.guna.filter((item) => item !== g);
                          return {
                            ...prev,
                            healthAssessment: { ...prev.healthAssessment, guna: newGuna },
                          };
                        });
                      }}
                    />
                    {g}
                  </label>
                ))}
              </div>
            </div>
          </div>
        </div>

        {/* Submit Button */}
        <div className="text-center">
          <button
            type="submit"
            className="bg-green-600 hover:bg-green-700 text-white px-8 py-3 rounded-full font-semibold shadow-lg transition duration-300 cursor-pointer hover:scale-105 active:scale-95"
          >
            {loader ? "Submitting..." : "Submit Assessment"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default Assessment;
