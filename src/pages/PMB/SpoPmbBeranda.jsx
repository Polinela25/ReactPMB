import React from "react";
import { useNavigate } from "react-router-dom";

export default function SpoPmbBeranda() {
  const navigate = useNavigate(); // INI YANG PENTING

  return (
    <div className="max-w-4xl mx-auto p-6 space-y-6 font-sans text-gray-800">
      <header className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Beranda Login</h1>
        <nav>
          <a href="/spo-pmb/login" className="text-blue-600 hover:underline">
            Login
          </a>
        </nav>
      </header>

      <section>
        <h2 className="text-xl font-semibold mb-2">
          Politeknik Negeri Lampung<br />
          Sistem Pendaftaran Online Mahasiswa Baru
        </h2>
        <p>
          Sudah punya akun!.{" "}
          <a href="/spo-pmb/login" className="text-blue-600 underline">
            Login disini.
          </a>
        </p>
      </section>

      <section>
        <h3 className="text-lg font-semibold mb-2">Pendaftaran Online</h3>
        <p>
          Daftar pendaftaran online berdasarkan jalur penerimaan mahasiswa baru
          seleksi Politeknik Negeri Lampung yang dibuka pada tahun 2025
        </p>
        <p className="italic mt-2">
          Sebelum mengklik tombol 'DAFTAR'. Pastikan bahwa jalur seleksi yang
          ada pilih sudah sesuai dan benar!.
        </p>
      </section>

      <section className="space-y-4">
        <div className="border p-4 rounded shadow-sm">
          <h4 className="font-bold">SMBP-PAN</h4>
          <p>
            Seleksi Mahasiswa Baru Polinela Jalur Prestasi Akademik dan
            Nonakademik
          </p>
          <p>Seleksi Prestasi Akademik dan Non Akademik 2025</p>
          <p>
            Jadwal Pendaftaran: 07/01/2025 s.d. 16/05/2025 -{" "}
            <span className="font-semibold">[SELESAI]</span> [0 hari tersisa]
          </p>
        </div>

        <div className="border p-4 rounded shadow-sm">
          <h4 className="font-bold">SMBP-T (UTBK)</h4>
          <p>Seleksi Mahasiswa Baru Polinela Jalur Tes (UTBK)</p>
          <p>Seleksi Tes UTBK Konsorsium Politeknik Negeri</p>
          <p>
            Jadwal Pendaftaran: 25/05/2025 s.d. 19/06/2025 -{" "}
            <span className="font-semibold text-green-700">[Berlangsung]</span>{" "}
            [16 hari tersisa]
          </p>
          <button
            onClick={() => navigate("/registrasi-spo-pmb")}
            className="mt-2 bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            Daftar
          </button>
        </div>
      </section>

     <section>
        <h3 className="font-semibold">Informasi</h3>
        <p>
          Selamat mengikuti pendaftaran online Tahun 2025 Politeknik Negeri Lampung, dan selanjutnya Untuk meminimalisir kesalahan proses pendaftaran online harap diperhatikan informasi terkait pendaftaran online berikut.
        </p>
        <ul className="list-disc list-inside space-y-1 mt-2">
          <li>Jika ada kendala pada proses pendaftaran online, silakan join untuk Q&A pada Grup TELEGRAM OpenChat resmi SPO 2025 pada link berikut : <a href="#" className="text-blue-600 underline">SPO 2024</a></li>
          <li>Data yang diinputkan merupakan data yang benar dan valid. Data yang salah dan keliru atau tidak lengkap akan berpengaruh proses seleksi.</li>
          <li>Info lebih seputar program studi, jalur penerimaan dan lainnya dapat dilihat pada laman <a href="https://pmb.polinela.ac.id" target="_blank" rel="noreferrer" className="text-blue-600 underline">https://pmb.polinela.ac.id</a> dan Instagram <a href="https://instagram.com/politeknik_negeri_lampung" target="_blank" rel="noreferrer" className="text-blue-600 underline">@politeknik_negeri_lampung</a></li>
        </ul>
      </section>

      <section>
        <h3 className="font-semibold">Portal Resmi PMB?</h3>
        <p>
          Politeknik Negeri Lampung merupakan satu bagian dari Sistem Pendidikan Nasional khususnya pendidikan tinggi vokasional yang mengembangkan Sumber Daya Manusia (SDM) agar memiliki keterampilan praktis memadai, membekali lulusannya dengan keterampilan yang didukung dengan pengetahuan dasar teoritis yang cukup dan sikap disiplin yang tangguh.
        </p>
      </section>

      <section>
        <h3 className="font-semibold">SPOMB / Jadmission V.12</h3>
        <p>
          Sistem Pendaftaran Online Mahasiswa Baru (SPOMB) adalah layanan resmi sistem pendaftaran online pada penerimaan mahasiswa baru khusus jalur penerimaan lokal Politeknik Negeri Lampung. Aplikasi Jaraka admission (Jadmission) V.12. pmb.polinela.ac.id
        </p>
      </section>

      <footer className="mt-8 border-t pt-4 text-sm text-gray-600 space-y-2">
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
