import React from "react";
import logo from "../assets/images/logo.png";

const Navbar = () => {
    return (
        <nav className="px-20 py-2 flex justify-between shadow-md backdrop-blur">
            <div className="flex items-center gap-2">
                <img src={logo} width={60} height={60}></img>
                <h1 className="text-2xl text-[var(--color-text)] font-semibold">Swasthya Path</h1>
            </div>
            <ul className="flex items-center gap-10">
                <li><a>About Us</a></li>
                <li><a>Contact Us</a></li>
            </ul>
        </nav>
    )
}

export default Navbar