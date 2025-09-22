import React from 'react'
import bg from "../assets/images/bg.png"
import Navbar from '../components/Navbar'
import { Link } from 'react-router-dom'

function HomePage() {
    return (
        <div
            style={{
                backgroundImage: `url(${bg})`,
                backgroundSize: 'cover',
                backgroundPosition: 'center',
                minHeight: '100vh',
            }}
        >
            <Navbar />
            <div className=" flex justify-center flex-col w-1/2 h-[90vh] box-border px-20 items-start gap-10">
                <h1 className='text-3xl leading-relaxed'>
                    Reconnect with nature, restore your balance, and nurture your well-being the Ayurvedic way.
                </h1>
                <Link to="/survey" className="bg-[var(--color-primary)] text-white lg:w-1/2 py-3 rounded-full transform transition duration-300 hover:scale-105 active:scale-95 cursor-pointer inline-block text-center">
                    Prepare Diet
                </Link>

            </div>
        </div>
    )
}

export default HomePage