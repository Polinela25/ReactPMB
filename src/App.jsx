import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Beranda from "./pages/PMB/Beranda";
import Registrasi from "./pages/PMB/Registrasi";
import SpoPmbBeranda from "./pages/PMB/SpoPmbBeranda";
import SpoPmbLogin from "./pages/PMB/SpoPmbLogin";
import RegistrasiSpoPmb from "./pages/PMB/RegistrasiSpoPmb";
import MahasiswaPage from "./pages/PMB/MahasiswaPage";

import Sidebar from "./components/PMB/Header";
import Footer from "./components/PMB/Footer";

export default function App() {
  return (
    <Router>
      <div className="flex min-h-screen bg-gray-50">
        <Sidebar />
        
        <div className="flex flex-col flex-1 md:ml-64">
          <main className="flex-grow p-6">
            <Routes>
              <Route path="/" element={<Beranda />} />
              <Route path="/registrasi" element={<Registrasi />} />
              <Route path="/spo-pmb" element={<SpoPmbBeranda />} />
              <Route path="/spo-pmb/login" element={<SpoPmbLogin />} />
              <Route path="/registrasi-spo-pmb" element={<RegistrasiSpoPmb />} />
              <Route path="/mahasiswa" element={<MahasiswaPage />} />
            </Routes>
          </main>
          <Footer />
        </div>
      </div>
    </Router>
  );
}
