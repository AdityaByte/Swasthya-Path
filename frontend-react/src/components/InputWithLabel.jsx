import React from 'react';

const InputWithLabel = ({ label, type, name, value, onChange, placeholderText, options = [] }) => {
  return (
    <div className="flex flex-col w-full">
      <label className="block mb-2 font-medium text-gray-800">{label}</label>

      {type === "select" ? (
        <select
          name={name}
          value={value}
          onChange={onChange}
          className="w-full bg-[var(--color-bg)] border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)] shadow-sm"
          required
        >
          <option value="">Select {label}</option>
          {options.map((opt, idx) => (
            <option key={idx} value={opt}>{opt}</option>
          ))}
        </select>
      ) : type === "textarea" ? (
        <textarea
          name={name}
          value={value}
          onChange={onChange}
          className="w-full bg-[var(--color-bg)] border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)] shadow-sm"
          placeholder={placeholderText}
          rows={3}
          required
        />
      ) : (
        <input
          name={name}
          value={value}
          onChange={onChange}
          className="w-full bg-[var(--color-bg)] border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)] shadow-sm"
          type={type}
          placeholder={placeholderText}
          required
        />
      )}
    </div>
  );
};

export default InputWithLabel;
