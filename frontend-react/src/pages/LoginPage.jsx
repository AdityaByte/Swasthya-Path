import React from "react";

const LoginPage = () => {
  const userTypes = ["Patient", "Doctor"];

  return (
    <div className="w-full h-[100vh] flex items-center justify-center bg-gradient-to-br from-green-50 via-white to-green-100">
      <div className="w-[90%] md:w-[400px] bg-white rounded-2xl shadow-xl p-8 flex flex-col gap-8">
        {/* Title */}
        <h1 className="text-center text-3xl font-bold text-gray-800">
          Login
        </h1>

        {/* Form */}
        <form className="flex flex-col gap-6">
          {/* User Type */}
          <div className="flex flex-col gap-2">
            <label htmlFor="user-type" className="font-medium text-gray-700">
              User Type
            </label>
            <select
              id="user-type"
              name="user-type"
              className="w-full bg-white border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 shadow-sm"
              required
            >
              <option value="">Select</option>
              {userTypes.map((opt, idx) => (
                <option key={idx} value={opt}>
                  {opt}
                </option>
              ))}
            </select>
          </div>

          {/* Username */}
          <div className="flex flex-col gap-2">
            <label htmlFor="username" className="font-medium text-gray-700">
              Username
            </label>
            <input
              type="text"
              id="username"
              placeholder="John Doe"
              className="w-full bg-white border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 shadow-sm"
            />
          </div>

          {/* Password */}
          <div className="flex flex-col gap-2">
            <label htmlFor="password" className="font-medium text-gray-700">
              Password
            </label>
            <input
              type="password"
              id="password"
              placeholder="••••••••"
              className="w-full bg-white border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-green-500 shadow-sm"
            />
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="w-full bg-green-600 text-white py-3 rounded-lg font-semibold shadow-md hover:bg-green-700 transition-transform transform hover:scale-105 active:scale-95"
          >
            Proceed
          </button>
        </form>

        {/* Extra Links */}
        <div className="text-sm text-gray-600 text-center">
          <p>
            Don’t have an account?{" "}
            <a href="/signup" className="text-green-600 font-medium hover:underline">
              Sign up
            </a>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
