import React, { useState } from "react";
import InputWithLabel from "../components/InputWithLabel";
import { useNavigate } from "react-router-dom";

const SurveyPage = () => {
  const navigate = useNavigate();
  const backendURL = import.meta.env.VITE_BACKEND_URL;

  const [formData, setFormData] = useState({
    basicInfo: {
      name: "",
      age: "",
      gender: "",
      height: "",
      weight: "",
      activityLevel: "",
      mealFrequency: "",
      sleepingSchedule: "",
      hoursOfSleep: "",
      waterIntake: "",
      preferredFoodGenre: "",
    },
    healthInfo: {
      healthIssues: "",
      allergies: "",
      preferredTastes: "",
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
    }
  });

  const updateSection = (section, name, value) => {
    setFormData(prev => ({
      ...prev,
      [section]: {
        ...prev[section],
        [name]: value
      }
    }));
  };

  const [loader,setloader] = useState(false)
  const handleSubmit = (e) => {
    e.preventDefault();
    setloader(true)

    const payload = {
      basicInfo: {
        ...formData.basicInfo,
        age: parseInt(formData.basicInfo.age),
        height: parseFloat(formData.basicInfo.height),
        weight: parseFloat(formData.basicInfo.weight),
        mealFrequency: parseInt(formData.basicInfo.mealFrequency),
        hoursOfSleep: parseFloat(formData.basicInfo.hoursOfSleep),
        waterIntake: parseInt(formData.basicInfo.waterIntake),
      },
      healthInfo: {
        ...formData.healthInfo,
        healthIssues: formData.healthInfo.healthIssues.split(",").map(v => v.trim()),
        allergies: formData.healthInfo.allergies.split(",").map(v => v.trim()),
        preferredTastes: formData.healthInfo.preferredTastes.split(",").map(v => v.trim())
      },
      prakrutiAssessment: { ...formData.prakrutiAssessment },
      vikrutiAssessment: { ...formData.vikrutiAssessment }
    };

    console.log("Payload ready:", payload);

    fetch(`${backendURL}/api/survey`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then(async response => {
        if (!response.ok) {
          console.log(`Something went wrong, try to check server logs ${response.status}`);
          return;
        }
        const data = await response.json();
        setloader(false)
        navigate("/result", { state: data });
      })
      .catch(error => {
        console.error(`Something went wrong at the server: ${error}`);
      });
  };

  return (
    <div className="min-h-screen bg-[var(--color-secondary)] py-8 px-4 md:px-10">
      <h1 className="text-4xl font-bold mb-8 text-center text-green-700">Ayurveda Survey</h1>

      <form className="max-w-7xl mx-auto" onSubmit={handleSubmit}>
        <div className="space-y-8">

          <div className="bg-white shadow-lg rounded-lg p-6">
            <h2 className="text-2xl font-semibold mb-4 border-b pb-2 text-gray-800 text-center">Basic Information</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <InputWithLabel label="Name" name="name" value={formData.basicInfo.name} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="John Doe" type="text" />
              <InputWithLabel label="Age" name="age" value={formData.basicInfo.age} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="21" type="number" />
              <InputWithLabel label="Gender" name="gender" value={formData.basicInfo.gender} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} type="select" options={["MALE", "FEMALE", "OTHER"]} />
              <InputWithLabel label="Height (cm)" name="height" value={formData.basicInfo.height} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="170" type="number" />
              <InputWithLabel label="Weight (kg)" name="weight" value={formData.basicInfo.weight} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="65" type="number" />
              <InputWithLabel label="Activity Level" name="activityLevel" value={formData.basicInfo.activityLevel} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} type="select" options={["SEDENTARY", "MODERATE", "ACTIVE"]} />
              <InputWithLabel label="Meal Frequency" name="mealFrequency" value={formData.basicInfo.mealFrequency} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} type="select" options={["2", "3", "4"]} />
              <InputWithLabel label="Sleeping Schedule" name="sleepingSchedule" value={formData.basicInfo.sleepingSchedule} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} type="select" options={["EARLY", "LATE"]} />
              <InputWithLabel label="Hours of Sleep" name="hoursOfSleep" value={formData.basicInfo.hoursOfSleep} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="7.5" type="number" />
              <InputWithLabel label="Water Intake (glasses/day)" name="waterIntake" value={formData.basicInfo.waterIntake} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="8" type="number" />
              <InputWithLabel label="Preferred Food Genre" name="preferredFoodGenre" value={formData.basicInfo.preferredFoodGenre} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} type="select" options={["VEG", "NON_VEG", "VEGAN", "MIXED"]} />
            </div>
          </div>

          <div className="bg-white shadow-lg rounded-lg p-6">
            <h2 className="text-2xl font-semibold mb-4 border-b pb-2 text-gray-800 text-center">Health & Preferences</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <InputWithLabel label="Health Issues" name="healthIssues" value={formData.healthInfo.healthIssues} onChange={e => updateSection("healthInfo", e.target.name, e.target.value)} type="textarea" placeholderText="Diabetes, BP" />
              <InputWithLabel label="Allergies" name="allergies" value={formData.healthInfo.allergies} onChange={e => updateSection("healthInfo", e.target.name, e.target.value)} type="textarea" placeholderText="Peanuts, Gluten" />
              <InputWithLabel label="Preferred Tastes" name="preferredTastes" value={formData.healthInfo.preferredTastes} onChange={e => updateSection("healthInfo", e.target.name, e.target.value)} type="textarea" placeholderText="Sweet, Salty" />
            </div>
          </div>

          <div className="bg-white shadow-lg rounded-lg p-6">
            <h2 className="text-2xl font-semibold mb-4 border-b pb-2 text-gray-800 text-center">Prakruti Assessment (Innate Constitution)</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <InputWithLabel label="Body Type" name="bodyType" value={formData.prakrutiAssessment.bodyType} onChange={e => updateSection("prakrutiAssessment", e.target.name, e.target.value)} type="select" options={["SLIM_LIGHT", "MEDIUM_WARM", "SOLID_STEADY"]} />
              <InputWithLabel label="Skin Nature" name="skinNature" value={formData.prakrutiAssessment.skinNature} onChange={e => updateSection("prakrutiAssessment", e.target.name, e.target.value)} type="select" options={["DRY_ROUGH", "WARM_OILY", "SOFT_COOL"]} />
              <InputWithLabel label="Digestion Strength" name="digestionStrength" value={formData.prakrutiAssessment.digestionStrength} onChange={e => updateSection("prakrutiAssessment", e.target.name, e.target.value)} type="select" options={["STRONG", "WEAK", "IRREGULAR"]} />
              <InputWithLabel label="Energy Pattern" name="energyPattern" value={formData.prakrutiAssessment.energyPattern} onChange={e => updateSection("prakrutiAssessment", e.target.name, e.target.value)} type="select" options={["Quick bursts, then tired", "High energy, sometimes irritable", "Steady & calm, but slower"]} />
              <InputWithLabel label="Sleep Nature" name="sleepNature" value={formData.prakrutiAssessment.sleepNature} onChange={e => updateSection("prakrutiAssessment", e.target.name, e.target.value)} type="select" options={["Light, easily disturbed", "Moderate, usually good", "Deep, sometimes oversleeping"]} />
            </div>
          </div>

          <div className="bg-white shadow-lg rounded-lg p-6">
            <h2 className="text-2xl font-semibold mb-4 border-b pb-2 text-gray-800 text-center">Vikruti Assessment (Current Imbalance)</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <InputWithLabel label="Current Main Concerns" name="currentConcerns" value={formData.vikrutiAssessment.currentConcerns} onChange={e => updateSection("vikrutiAssessment", e.target.name, e.target.value)} type="textarea" placeholderText="E.g., stress, acidity, poor sleep..." />
              <InputWithLabel label="Current Energy Pattern" name="currentEnergy" value={formData.vikrutiAssessment.currentEnergy} onChange={e => updateSection("vikrutiAssessment", e.target.name, e.target.value)} type="select" options={["Low energy", "High energy, irritable", "Steady, calm"]} />
              <InputWithLabel label="Current Sleep Pattern" name="currentSleep" value={formData.vikrutiAssessment.currentSleep} onChange={e => updateSection("vikrutiAssessment", e.target.name, e.target.value)} type="select" options={["Light, easily disturbed", "Moderate, usually good", "Deep, sometimes oversleeping"]} />
              <InputWithLabel label="Digestion Today" name="digestionToday" value={formData.vikrutiAssessment.digestionToday} onChange={e => updateSection("vikrutiAssessment", e.target.name, e.target.value)} type="select" options={["Poor", "Good", "Moderate"]} />
            </div>
          </div>

          <div className="text-center">
            <button type="submit" className="bg-green-600 hover:bg-green-700 text-white px-8 py-3 rounded-full font-semibold shadow-lg transition duration-300 cursor-pointer hover:scale-105 active:scale:95">
              {loader ? "loading..." :"Submit Survey" }
            </button>
          </div>

        </div>
      </form>
    </div>
  );
};

export default SurveyPage;
