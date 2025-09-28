import React, { useState } from "react";
import logo from "../assets/images/logo.png";
import LoginButton from "./LoginButton";
import SignupButton from "./SignupButton";
import { HiOutlineMenu, HiOutlineX } from "react-icons/hi";
import { Link } from "react-router-dom";
import LoginPage from "../pages/LoginPage";

const Navbar = () => {
    const [isOpen, setIsOpen] = useState(false);

    const menuItems = ["Home", "Diet Planner", "Wellness", "Remedies", "About"];

    return (
        <nav className="px-2 md:px-10 py-3 md:py-4 flex justify-between items-center
                    bg-white/70 backdrop-blur-md shadow-lg border border-gray-200
                    rounded-2xl mx-1 md:mx-16 md:my-4 sticky top-4 z-50">

            <div className="flex items-center gap-3">
                <img src={logo} width={48} height={48} alt="Swasthya Path Logo" />
                <h1 className="md:text-2xl font-bold text-gray-800 tracking-tight">
                    Swasthya <span className="text-green-600">Path</span>
                </h1>
            </div>

            <ul className="hidden md:flex items-center gap-10 text-[15px] font-medium text-gray-600">
                {menuItems.map((item) => (
                    <li
                        key={item}
                        className="hover:text-green-600 hover:underline underline-offset-4 transition-all cursor-pointer"
                    >
                        {item}
                    </li>
                ))}
            </ul>

            <div className="hidden md:flex items-center gap-4">
                <Link to={"/login"}>
                    <LoginButton />
                </Link>
                <Link to={"/signup/"}>
                    <SignupButton />
                </Link>
            </div>

            <div className="md:hidden flex items-center">
                <button
                    onClick={() => setIsOpen(!isOpen)}
                    className="text-gray-700 focus:outline-none mr-2"
                >
                    <HiOutlineMenu size={28} />
                </button>
            </div>

            {
                isOpen && (
                    <div className="absolute top-0 left-0 w-full h-[100vh] bg-white shadow-lg rounded-b-2xl md:hidden z-40">
                        <button onClick={() => setIsOpen(false)} className="text-gray-700 absolute top-2 right-3" >
                            <HiOutlineX size={28} />
                        </button>
                        <ul className="flex flex-col items-center gap-6 py-6 text-gray-700 font-medium">
                            {menuItems.map((item) => (
                                <li key={item} className="hover:text-green-600 transition cursor-pointer">
                                    {item}
                                </li>
                            ))}
                            <div className="flex gap-4 mt-4">
                                <Link to={"/login"}>
                                    <LoginButton />
                                </Link>
                                <Link to={"/signup/"}>
                                    <SignupButton />
                                </Link>
                            </div>
                        </ul>
                    </div>
                )
            }
        </nav >
    );
};

export default Navbar;
