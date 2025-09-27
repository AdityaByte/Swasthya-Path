import React from "react";

const Assessment = () => {
  return (
    <div className="max-w-4xl mx-auto p-6 bg-white shadow-lg rounded-2xl space-y-8">
      <h1 className="text-2xl font-bold text-center text-green-700">Ayurvedic Assessment Form</h1>

      {/* Prakruti Assessment */}
      <section>
        <h2 className="text-xl font-semibold text-green-600 mb-3">Prakruti Assessment</h2>
        <label className="block mb-2">Body Frame:</label>
        <select className="w-full border rounded-lg p-2">
          <option>Lean</option>
          <option>Medium</option>
          <option>Sturdy</option>
        </select>
      </section>

      {/* Vikruti Assessment */}
      <section>
        <h2 className="text-xl font-semibold text-green-600 mb-3">Vikruti Assessment</h2>
        <label className="block mb-2">Current Health Imbalance:</label>
        <textarea className="w-full border rounded-lg p-2" rows="3" placeholder="Describe current issues..."></textarea>
      </section>

      {/* Dosha Assessment */}
      <section>
        <h2 className="text-xl font-semibold text-green-600 mb-3">Dosha Assessment</h2>
        <label className="block mb-2">Which describes you best?</label>
        <div className="space-y-2">
          <label className="flex items-center gap-2">
            <input type="radio" name="dosha" /> Vata (Light, cold, dry, quick)
          </label>
          <label className="flex items-center gap-2">
            <input type="radio" name="dosha" /> Pitta (Hot, sharp, oily, intense)
          </label>
          <label className="flex items-center gap-2">
            <input type="radio" name="dosha" /> Kapha (Heavy, slow, cool, stable)
          </label>
        </div>
      </section>

      {/* Rasa Assessment */}
      <section>
        <h2 className="text-xl font-semibold text-green-600 mb-3">Rasa Assessment</h2>
        <label className="block mb-2">Preferred Taste:</label>
        <select className="w-full border rounded-lg p-2">
          <option>Madhura (Sweet)</option>
          <option>Amla (Sour)</option>
          <option>Lavana (Salty)</option>
          <option>Katu (Pungent)</option>
          <option>Tikta (Bitter)</option>
          <option>Kashaya (Astringent)</option>
        </select>
      </section>

      {/* Guna Assessment */}
      <section>
        <h2 className="text-xl font-semibold text-green-600 mb-3">Guna Assessment</h2>
        <label className="block mb-2">Mental Qualities:</label>
        <div className="space-y-2">
          <label className="flex items-center gap-2">
            <input type="checkbox" /> Sattva (Clarity, calmness, wisdom)
          </label>
          <label className="flex items-center gap-2">
            <input type="checkbox" /> Rajas (Activity, drive, restlessness)
          </label>
          <label className="flex items-center gap-2">
            <input type="checkbox" /> Tamas (Inertia, dullness, confusion)
          </label>
        </div>
      </section>

      {/* Lifestyle Section */}
      <section>
        <h2 className="text-xl font-semibold text-green-600 mb-3">Lifestyle Factors</h2>
        <label className="block mb-2">Digestion Quality:</label>
        <select className="w-full border rounded-lg p-2">
          <option>Weak</option>
          <option>Normal</option>
          <option>Strong</option>
        </select>

        <label className="block mt-4 mb-2">Sleep Pattern:</label>
        <select className="w-full border rounded-lg p-2">
          <option>Disturbed</option>
          <option>Normal</option>
          <option>Deep</option>
        </select>

        <label className="block mt-4 mb-2">Stress Level:</label>
        <select className="w-full border rounded-lg p-2">
          <option>Low</option>
          <option>Moderate</option>
          <option>High</option>
        </select>

        <label className="block mt-4 mb-2">Food Preference:</label>
        <select className="w-full border rounded-lg p-2">
          <option>Vegetarian</option>
          <option>Non-Vegetarian</option>
          <option>Vegan</option>
        </select>
      </section>

      {/* Submit */}
      <div className="text-center">
        <button className="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition">
          Submit Assessment
        </button>
      </div>
    </div>
  );
};

export default Assessment;
