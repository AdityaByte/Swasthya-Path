import React from "react";

const LoginButton = () => {
  return (
    <button
      className="
        text-sm
        px-5 py-2
        rounded-lg
        font-medium
        border-2 border-green-600
        text-green-600
        bg-white
        hover:bg-green-600 hover:text-white
        transition-all duration-300 ease-in-out
        shadow-sm hover:shadow-md
      "
    >
      Login
    </button>
  );
};

export default LoginButton;
