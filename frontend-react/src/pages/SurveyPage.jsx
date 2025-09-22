import React, { useState } from "react";
import InputWithLabel from "../components/InputWithLabel";

const SurveyPage = () => {

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


  // Updating the state of variables.
  const updateSection = (section, name, value) => {
    setFormData(prev => ({
      ...previousData,
      [section]: {
        ...previousData[section],
        [name]: value
      }
    }));
  };

  // Handling the data and sending it to the backend.
  const handleSubmit = (e) => {
    e.preventDefault();

    const payload = {
      ...formData.basicInfo,
      ...formData.healthInfo,
      ...formData.prakrutiAssessment,
      ...formData.vikrutiAssessment,
      age: parseInt(formData.basicInfo.age),
      height: parseFloat(formData.basicInfo.height),
      weight: parseFloat(formData.basicInfo.weight),
      mealFrequency: parseInt(formData.basicInfo.mealFrequency),
      hoursOfSleep: parseFloat(formData.basicInfo.hoursOfSleep),
      waterIntake: parseInt(formData.basicInfo.waterIntake),
      healthIssues: formData.healthInfo.healthIssues.split(",").map(value => value.trim()),
      allergies: formData.healthInfo.allergies.split(",").map(value => value.trim()),
      preferredTastes: formData.healthInfo.preferredTastes.split(",").map(value => value.trim())
    };

    console.log("Payload ready:", payload);

    fetch(`${backendURL}/api/survey`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    })
      .then(resposne => response)
      .catch(error => {
        console.error(error)
      })

  };

  return (
    <div className="min-h-screen p-6 bg-gray-50 flex flex-col items-center">
      <h1 className="text-3xl font-bold mb-6 text-center">Ayurveda Survey</h1>

      <form className="w-full max-w-7xl" onSubmit={handleSubmit}>
        <div className="bg-[var(--color-secondary)] rounded-lg py-6 flex flex-col items-center w-full">

          {/* Basic Info */}
          <h2 className="text-2xl font-semibold mb-4 text-center">Basic Information</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 w-full px-6 mb-6">
            <InputWithLabel label="Name" name="name" value={formData.basicInfo.name} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="John Doe" type="text" />
            <InputWithLabel label="Age" name="age" value={formData.basicInfo.age} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="21" type="number" />
            <InputWithLabel label="Gender" name="gender" value={formData.basicInfo.gender} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} type="select" options={["Male", "Female", "Other"]} />
            <InputWithLabel label="Height (cm)" name="height" value={formData.basicInfo.height} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="170" type="number" />
            <InputWithLabel label="Weight (kg)" name="weight" value={formData.basicInfo.weight} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="65" type="number" />
            <InputWithLabel label="Activity Level" name="activityLevel" value={formData.basicInfo.activityLevel} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} type="select" options={["Sedentary", "Moderate", "Active"]} />
            <InputWithLabel label="Meal Frequency" name="mealFrequency" value={formData.basicInfo.mealFrequency} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} type="select" options={["2", "3", "4"]} />
            <InputWithLabel label="Sleeping Schedule" name="sleepingSchedule" value={formData.basicInfo.sleepingSchedule} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} type="select" options={["Early Sleep", "Late Sleep"]} />
            <InputWithLabel label="Hours of Sleep" name="hoursOfSleep" value={formData.basicInfo.hoursOfSleep} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="7.5" type="number" />
            <InputWithLabel label="Water Intake (glasses/day)" name="waterIntake" value={formData.basicInfo.waterIntake} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} placeholderText="8" type="number" />
            <InputWithLabel label="Preferred Food Genre" name="preferredFoodGenre" value={formData.basicInfo.preferredFoodGenre} onChange={e => updateSection("basicInfo", e.target.name, e.target.value)} type="select" options={["Veg", "Non-Veg", "Vegan", "Mixed"]} />
          </div>

          {/* Health Info */}
          <h2 className="text-2xl font-semibold mb-4 text-center">Health & Preferences</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 w-full px-6 mb-6">
            <InputWithLabel label="Health Issues" name="healthIssues" value={formData.healthInfo.healthIssues} onChange={e => updateSection("healthInfo", e.target.name, e.target.value)} type="textarea" placeholderText="Diabetes, BP" />
            <InputWithLabel label="Allergies" name="allergies" value={formData.healthInfo.allergies} onChange={e => updateSection("healthInfo", e.target.name, e.target.value)} type="textarea" placeholderText="Peanuts, Gluten" />
            <InputWithLabel label="Preferred Tastes" name="preferredTastes" value={formData.healthInfo.preferredTastes} onChange={e => updateSection("healthInfo", e.target.name, e.target.value)} type="textarea" placeholderText="Sweet, Salty" />
          </div>

          {/* Prakruti Assessment */}
          <h2 className="text-2xl font-semibold mb-4 text-center">Prakruti Assessment (Innate Constitution)</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 w-full px-6 mb-6">
            <InputWithLabel label="Body Type" name="bodyType" value={formData.prakrutiAssessment.bodyType} onChange={e => updateSection("prakrutiAssessment", e.target.name, e.target.value)} type="select" options={["Slim & light (Vata)", "Medium & warm (Pitta)", "Solid & steady (Kapha)"]} />
            <InputWithLabel label="Skin Nature" name="skinNature" value={formData.prakrutiAssessment.skinNature} onChange={e => updateSection("prakrutiAssessment", e.target.name, e.target.value)} type="select" options={["Dry & rough", "Warm & oily", "Soft & cool"]} />
            <InputWithLabel label="Digestion Strength" name="digestionStrength" value={formData.prakrutiAssessment.digestionStrength} onChange={e => updateSection("prakrutiAssessment", e.target.name, e.target.value)} type="select" options={["Irregular (sometimes good, sometimes poor)", "Strong & fast", "Slow but steady"]} />
            <InputWithLabel label="Energy Pattern" name="energyPattern" value={formData.prakrutiAssessment.energyPattern} onChange={e => updateSection("prakrutiAssessment", e.target.name, e.target.value)} type="select" options={["Quick bursts, then tired", "High energy, sometimes irritable", "Steady & calm, but slower"]} />
            <InputWithLabel label="Sleep Nature" name="sleepNature" value={formData.prakrutiAssessment.sleepNature} onChange={e => updateSection("prakrutiAssessment", e.target.name, e.target.value)} type="select" options={["Light, easily disturbed", "Moderate, usually good", "Deep, sometimes oversleeping"]} />
          </div>

          {/* Vikruti Assessment */}
          <h2 className="text-2xl font-semibold mb-4 text-center">Vikruti Assessment (Current Imbalance)</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 w-full px-6 mb-6">
            <InputWithLabel label="Current Main Concerns" name="currentConcerns" value={formData.vikrutiAssessment.currentConcerns} onChange={e => updateSection("vikrutiAssessment", e.target.name, e.target.value)} type="textarea" placeholderText="E.g., stress, acidity, poor sleep..." />
            <InputWithLabel label="Current Energy Pattern" name="currentEnergy" value={formData.vikrutiAssessment.currentEnergy} onChange={e => updateSection("vikrutiAssessment", e.target.name, e.target.value)} type="select" options={["Low energy", "High energy, irritable", "Steady, calm"]} />
            <InputWithLabel label="Current Sleep Pattern" name="currentSleep" value={formData.vikrutiAssessment.currentSleep} onChange={e => updateSection("vikrutiAssessment", e.target.name, e.target.value)} type="select" options={["Light, disturbed", "Moderate", "Deep"]} />
            <InputWithLabel label="Digestion Today" name="digestionToday" value={formData.vikrutiAssessment.digestionToday} onChange={e => updateSection("vikrutiAssessment", e.target.name, e.target.value)} type="select" options={["Poor", "Good", "Moderate"]} />
          </div>

          <div className="mt-8 mb-4 w-full flex items-center justify-center">
            <button type="submit" className="bg-[var(--color-primary)] text-white px-6 py-3 rounded-full shadow-md transform transition duration-300 hover:scale-105 active:scale-95 w-[35%] cursor-pointer">
              Submit Survey
            </button>
          </div>

        </div>
      </form>
    </div>
  );
};

export default SurveyPage;
