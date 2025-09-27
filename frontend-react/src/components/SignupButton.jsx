import React from "react";

const SignupButton = () => {
  return (
    <button
      className="
        text-sm
        px-5 py-2
        rounded-lg
        font-semibold
        bg-gradient-to-r from-orange-400 to-orange-500
        text-white
        hover:from-orange-500 hover:to-orange-600
        transition-all duration-300 ease-in-out
        shadow-sm hover:shadow-md
        cursor-pointer
      "
    >
      Signup
    </button>
  );
};

export default SignupButton;
