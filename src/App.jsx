import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";

import Beranda from "./pages/PMB/Beranda";
import Registrasi from "./pages/PMB/Registrasi";
import SpoPmbBeranda from "./pages/PMB/SpoPmbBeranda";
import SpoPmbLogin from "./pages/PMB/SpoPmbLogin";
import RegistrasiSpoPmb from "./pages/PMB/RegistrasiSpoPmb";
import MahasiswaPage from "./pages/PMB/admin/MahasiswaPage";
import MahasiswaInputPage from "./components/PMB/admin/MahasiswaInputPage";
import MahasiswaListPage from "./components/PMB/admin/MahasiswaListPage";

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
                <Route path="/mahasiswaPage" element={<MahasiswaPage />} />
                <Route path="/mahasiswa/input" element={<MahasiswaInputPage />} />
                <Route path="/mahasiswa/list" element={<MahasiswaListPage />} />
                {/* Redirect old paths for backward compatibility */}
                <Route path="/mahasiswainput" element={<Navigate to="/mahasiswa/input" replace />} />
                <Route path="/mahasiswalist" element={<Navigate to="/mahasiswa/list" replace />} />
                {/* Catch-all for 404 */}
              </Routes>
          </main>
          <Footer />
        </div>
      </div>
    </Router>
  );
}