import React from "react";
import { FaFacebookF, FaTwitter, FaInstagram, FaLinkedinIn } from "react-icons/fa";
import logo from "../assets/images/logo.jpg";

const Footer = () => {
  return (
    <footer className="bg-gray-800 text-gray-200 py-10 px-6 md:px-20">
      <div className="flex flex-col md:flex-row justify-between items-center gap-8 md:gap-0">
        {/* Logo */}
        <div className="flex items-center gap-3">
          <img src={logo} alt="Swasthya Path Logo" className="w-10 h-10 rounded-full" />
          <h1 className="text-xl font-bold text-white">
            Swasthya <span className="text-green-500">Path</span>
          </h1>
        </div>

        {/* Links */}
        <div className="hidden md:flex gap-6 text-gray-300">
          {["Home", "Diet Planner", "Wellness", "Remedies", "About"].map((link) => (
            <a key={link} href={`#${link.toLowerCase()}`} className="hover:text-white transition">
              {link}
            </a>
          ))}
        </div>

        {/* Social Icons */}
        <div className="flex gap-4 text-gray-300">
          <a href="#" className="hover:text-white transition"><FaFacebookF /></a>
          <a href="#" className="hover:text-white transition"><FaTwitter /></a>
          <a href="#" className="hover:text-white transition"><FaInstagram /></a>
          <a href="#" className="hover:text-white transition"><FaLinkedinIn /></a>
        </div>
      </div>

      {/* Divider */}
      <div className="border-t border-gray-700 my-6"></div>

      {/* Copyright */}
      <p className="text-center text-gray-400 text-sm">
        &copy; {new Date().getFullYear()} Swasthya Path. All rights reserved.
      </p>
    </footer>
  );
};

export default Footer;
