import { useState } from "react";

export default function SpoPmbLogin() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  // Kamu bisa tambahkan fungsi handleSubmit nanti untuk login

  return (
    <div className="max-w-md mx-auto p-6 font-sans text-gray-800">
      <header className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Beranda Login</h1>
        <nav>
          <a href="/spo-pmb" className="text-blue-600 hover:underline">
            Beranda
          </a>
        </nav>
      </header>

      <h2 className="text-2xl font-semibold mb-4">Login</h2>

      <form className="space-y-4">
        <div>
          <label htmlFor="username" className="block font-medium mb-1">
            Username / Email
          </label>
          <input
            id="username"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="w-full border rounded px-3 py-2"
            placeholder="Masukkan username atau email"
          />
        </div>

        <div>
          <label htmlFor="password" className="block font-medium mb-1">
            Password
          </label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="w-full border rounded px-3 py-2"
            placeholder="Masukkan password"
          />
        </div>

        <div className="text-right">
          <a href="#" className="text-blue-600 underline text-sm">
            Lupa password?
          </a>
        </div>

        <button
          type="submit"
          className="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 rounded font-semibold"
        >
          Login
        </button>
      </form>

      <footer className="mt-12 border-t pt-4 text-sm text-gray-600 space-y-2">
        <p><strong>SPOMB / Jadmission V.12</strong></p>
        <p>
          Sistem Pendaftaran Online Mahasiswa Baru (SPOMB) adalah layanan resmi sistem pendaftaran online pada penerimaan mahasiswa baru khusus jalur penerimaan lokal Politeknik Negeri Lampung. Aplikasi Jaraka admission (Jadmission) V.12. pmb.polinela.ac.id
        </p>

        <p><strong>TikTok</strong></p>
        <p>Politeknik Negeri Lampung</p>
        <p>Jalan Soekarno-Hatta No.10, Rajabasa</p>
        <p>Bandar Lampung, Lampung, Indonesia. 35141.</p>
        <p>0721 703 995 | humas@polinela.ac.id | www.polinela.ac.id</p>
        <p>Panitia PMB, Sekretariat PMB, Lantai 1, Gedung A, Kampus Utama Politeknik Negeri Lampung.</p>
        <p>0812 7893 3860 | pmb@polinela.ac.id | pmb.polinela.ac.id</p>
        <p>Copyright © J Admission. All rights reserved | jaraka</p>
        <p>Client ID: 01012019 Politeknik Negeri Lampung</p>
      </footer>
    </div>
  );
}
