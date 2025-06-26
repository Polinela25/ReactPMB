import React, { useState } from "react";
import { NavLink } from "react-router-dom";

export default function Sidebar() {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className={`fixed md:static w-64 bg-white dark:bg-gray-800 h-full shadow-lg transform ${isOpen ? "translate-x-0" : "-translate-x-full"} md:translate-x-0 transition-transform duration-300 z-50`}>
      <button
        className="md:hidden p-4"
        onClick={() => setIsOpen(!isOpen)}
        aria-label={isOpen ? "Tutup Sidebar" : "Buka Sidebar"}
      >
        <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16" />
        </svg>
      </button>
      <nav className="p-4">
        <NavLink
          to="/"
          end
          className={({ isActive }) => `block py-2 text-sm ${isActive ? "text-teal-600 font-bold" : "text-gray-700 dark:text-gray-300"} hover:text-teal-600 transition-colors`}
        >
          Beranda
        </NavLink>
        <NavLink
          to="/registrasi"
          className={({ isActive }) => `block py-2 text-sm ${isActive ? "text-teal-600 font-bold" : "text-gray-700 dark:text-gray-300"} hover:text-teal-600 transition-colors`}
        >
          Registrasi
        </NavLink>
        <NavLink
          to="/spo-pmb"
          className={({ isActive }) => `block py-2 text-sm ${isActive ? "text-teal-600 font-bold" : "text-gray-700 dark:text-gray-300"} hover:text-teal-600 transition-colors`}
        >
          SPO PMB
        </NavLink>
        <NavLink
          to="/spo-pmb/login"
          className={({ isActive }) => `block py-2 text-sm ${isActive ? "text-teal-600 font-bold" : "text-gray-700 dark:text-gray-300"} hover:text-teal-600 transition-colors`}
        >
          Login SPO PMB
        </NavLink>
        <NavLink
          to="/registrasi-spo-pmb"
          className={({ isActive }) => `block py-2 text-sm ${isActive ? "text-teal-600 font-bold" : "text-gray-700 dark:text-gray-300"} hover:text-teal-600 transition-colors`}
        >
          Registrasi SPO PMB
        </NavLink>
        <NavLink
          to="/mahasiswa"
          className={({ isActive }) => `block py-2 text-sm ${isActive ? "text-teal-600 font-bold" : "text-gray-700 dark:text-gray-300"} hover:text-teal-600 transition-colors`}
        >
          Data Mahasiswa
        </NavLink>
        <NavLink
          to="/mahasiswa/input"
          className={({ isActive }) => `block py-2 text-sm ${isActive ? "text-teal-600 font-bold" : "text-gray-700 dark:text-gray-300"} hover:text-teal-600 transition-colors`}
        >
          Input Mahasiswa
        </NavLink>
        <NavLink
          to="/mahasiswa/list"
          className={({ isActive }) => `block py-2 text-sm ${isActive ? "text-teal-600 font-bold" : "text-gray-700 dark:text-gray-300"} hover:text-teal-600 transition-colors`}
        >
          List Mahasiswa
        </NavLink>
      </nav>
    </div>
  );
}