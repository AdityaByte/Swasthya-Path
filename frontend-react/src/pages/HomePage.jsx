import React from "react";
import { motion } from "framer-motion";
import Navbar from "../components/Navbar";
import Feature from "../components/Feature";
import Footer from "../components/Footer";
import { Link } from "react-router-dom";

const fadeInUp = {
  hidden: { opacity: 0, y: 40 },
  visible: { opacity: 1, y: 0 },
};

const fadeInDown = {
  hidden: { opacity: 0, y: -40 },
  visible: { opacity: 1, y: 0 },
};

const staggerContainer = {
  hidden: {},
  visible: {
    transition: {
      staggerChildren: 0.3,
    },
  },
};

const HomePage = () => {
  return (
    <div className="relative overflow-x-hidden">
      <Navbar />

      {/* hero section */}
      <motion.section
        className="relative flex flex-col items-center justify-center gap-6 text-center px-6
                   min-h-[calc(100vh-96px)] md:min-h-[calc(100vh-96px)]
                   bg-gradient-to-b from-white/90 via-white/60 to-green-50 overflow-hidden"
        initial="hidden"
        whileInView="visible"
        viewport={{ once: false, amount: 0.3 }}
        variants={staggerContainer}
      >

        <motion.h1
          variants={fadeInDown}
          transition={{ duration: 0.8, ease: "easeOut" }}
          className="text-4xl md:text-6xl font-extrabold mb-4 z-10 text-gray-800"
        >
          Personalized Diets, Rooted in{" "}
          <span className="text-green-600">Ayurveda</span>
        </motion.h1>

        <motion.p
          variants={fadeInUp}
          transition={{ duration: 0.8, ease: "easeOut", delay: 0.2 }}
          className="text-lg md:text-2xl text-gray-700 mb-6 max-w-xl z-10"
        >
          An intelligent platform blending Ayurvedic wisdom with modern nutrition
          for accurate, holistic diet care.
        </motion.p>

        <motion.div variants={fadeInUp} transition={{ delay: 0.4 }}>
          <Link to={"/login"} className="z-10">
            <motion.button
              whileHover={{ scale: 1.08 }}
              whileTap={{ scale: 0.95 }}
              className="bg-gradient-to-r from-green-500 to-green-600 text-white
                         px-10 py-3 rounded-full font-semibold shadow-lg
                         hover:shadow-xl transition-all duration-300 z-100 cursor-pointer relative"
            >
              Get Started
            </motion.button>
          </Link>
        </motion.div>

        {/* bg text */}
        <motion.span
          initial={{ opacity: 0, y: -200, rotate: -10 }}
          animate={{ opacity: 0.3, y: 0, rotate: -6 }}
          transition={{ duration: 1.5, ease: "easeOut" }}
          className="absolute bottom-0 left-1/2 -translate-x-1/2
                     text-green-200 text-[10rem] md:text-[16rem] font-bold
                     tracking-wide select-none pointer-events-none whitespace-nowrap
                     z-0"
        >
          AYURVEDA
        </motion.span>
      </motion.section>

      {/* What Swasthya Path Does */}
      <Feature />

      {/* How it works */}
      <section className="px-6 md:px-20 py-16 relative">
        <h2 className="text-4xl font-bold text-center mb-12">
          How Swasthya Path Works{" "}
          <span className="text-green-800">?</span>
        </h2>

        <div className="relative flex flex-col md:flex-row gap-10">
          <div className="hidden md:flex flex-col items-center w-[5%] relative">
            <div className="w-[2px] bg-green-300 h-full absolute top-0 left-1/2 -translate-x-1/2"></div>
            {Array(4)
              .fill(0)
              .map((_, idx) => (
                <motion.div
                  key={idx}
                  initial={{ scale: 0, opacity: 0 }}
                  whileInView={{ scale: 1, opacity: 1 }}
                  transition={{ duration: 0.6, delay: idx * 0.2 }}
                  viewport={{ once: false }}
                  className="w-6 h-6 bg-green-500 rounded-full border-2 border-white z-10 relative mt-24"
                ></motion.div>
              ))}
          </div>

          <div className="w-full md:w-[95%] flex flex-col gap-10">
            {[
              { title: "Input Patient Data", desc: "Add age, gender, dosha, and meal habits quickly and easily." },
              { title: "Generate Diet Chart", desc: "Automatic Ayurveda-compliant diet charts tailored to each patient." },
              { title: "Review & Customize", desc: "Doctors can tweak meals or ingredients to personalize further." },
              { title: "Share with Patient", desc: "Send diet charts digitally or print them for patient handouts." },
            ].map((item, idx) => (
              <motion.div
                key={idx}
                variants={fadeInUp}
                transition={{ duration: 0.8, delay: idx * 0.2 }}
                className="border-2 border-green-200 rounded-lg p-6 shadow-lg hover:scale-102 transition-transform duration-300 bg-white relative md:ml-12"
              >
                <h3 className="text-xl font-semibold mb-2">{item.title}</h3>
                <p className="text-gray-600">{item.desc}</p>
              </motion.div>
            ))}
          </div>
        </div>
      </section>

      <Footer />
    </div>
  );
};

export default HomePage;
