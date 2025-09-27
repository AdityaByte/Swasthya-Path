import React, { useState } from "react";

const OtpPage = () => {
  const [otp, setOtp] = useState(new Array(6).fill(""));

  const handleChange = (element, index) => {
    if (isNaN(element.value)) return;

    let newOtp = [...otp];
    newOtp[index] = element.value;
    setOtp(newOtp);

    // Move to next input if a digit is entered
    if (element.value && element.nextSibling) {
      element.nextSibling.focus();
    }
  };

  const handleKeyDown = (e, index) => {
    if (e.key === "Backspace") {
      if (!otp[index] && e.target.previousSibling) {
        e.target.previousSibling.focus();
      }
      let newOtp = [...otp];
      newOtp[index] = "";
      setOtp(newOtp);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert(`Entered OTP is ${otp.join("")}`);
    // Add verification logic here
  };

  return (
    <div className="w-full min-h-[100vh] flex items-center justify-center bg-gradient-to-br from-green-50 via-white to-green-100 px-4">
      <div className="bg-white shadow-2xl rounded-3xl p-10 w-full max-w-md text-center">
        <h1 className="text-3xl font-bold text-green-600 mb-4">
          OTP Verification
        </h1>
        <p className="text-gray-600 mb-6">
          Please enter the 6-digit code sent to your registered email/phone.
        </p>

        <form onSubmit={handleSubmit} className="flex flex-col items-center gap-6">
          <div className="flex justify-center gap-3">
            {otp.map((data, index) => (
              <input
                key={index}
                type="text"
                maxLength="1"
                value={data}
                onChange={(e) => handleChange(e.target, index)}
                onKeyDown={(e) => handleKeyDown(e, index)}
                className="w-12 h-12 text-center text-lg border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 shadow-sm"
              />
            ))}
          </div>

          <button
            type="submit"
            className="bg-green-600 text-white font-semibold px-8 py-3 rounded-lg hover:bg-green-700 transition-colors shadow-lg"
          >
            Verify OTP
          </button>
        </form>

        <p className="text-sm text-gray-500 mt-6">
          Didn’t receive the code?{" "}
          <span className="text-green-600 font-semibold cursor-pointer hover:underline">
            Resend
          </span>
        </p>
      </div>
    </div>
  );
};

export default OtpPage;
