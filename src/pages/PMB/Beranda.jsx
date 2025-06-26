import React from "react";
import { GraduationCap, Wallet, FileCheck2, School2, Globe2, Mail, Phone } from "lucide-react";

export default function Beranda() {
  return (
    <section className="container mx-auto p-6 max-w-4xl text-gray-800">
      <div className="bg-gradient-to-br from-blue-100 to-blue-50 p-8 rounded-2xl shadow-lg mb-10 border border-blue-200">
        <h1 className="text-3xl font-bold text-blue-700 mb-4">
          🎓 Selamat Datang Calon Mahasiswa Baru!
        </h1>
        <p className="mb-4 text-lg leading-relaxed">
          <span className="font-semibold">Registrasi Daftar Ulang</span><br />
          Validasi data dan registrasi daftar ulang calon mahasiswa baru secara online. Mahasiswa yang lulus seleksi dan diterima resmi sebagai mahasiswa Politeknik Negeri Lampung wajib mengikuti tahapan berikut:
        </p>

        <ul className="space-y-4 mt-4 text-base">
          <li className="flex items-start gap-3">
            <GraduationCap className="text-blue-500" /> 
            <div>
              <strong>Registrasi Data Induk Mahasiswa</strong><br />
              Registrasi data induk mahasiswa baru secara online.
            </div>
          </li>
          <li className="flex items-start gap-3">
            <Wallet className="text-blue-500" />
            <div>
              <strong>Registrasi Pembayaran UKT</strong><br />
              Proses pembayaran Uang Kuliah Tunggal (UKT).
            </div>
          </li>
          <li className="flex items-start gap-3">
            <FileCheck2 className="text-blue-500" />
            <div>
              <strong>Finalisasi dan Pemberkasan</strong><br />
              Pengumpulan dokumen fisik untuk verifikasi.
            </div>
          </li>
        </ul>

        <p className="mt-6 text-lg font-medium text-blue-700">
          Selamat bergabung dalam keluarga besar Politeknik Negeri Lampung!
        </p>
      </div>

      <div className="bg-white p-6 rounded-xl shadow-md mb-8 border border-blue-100">
        <h2 className="text-2xl font-bold text-blue-600 mb-4">
          Calon Mahasiswa Baru
        </h2>
        <p className="mb-3">
          <strong>Pendaftar Beasiswa KIP-KULIAH</strong><br />
          Perhatikan prosedur pembayaran UKT sesuai status beasiswa.
        </p>
        <p className="mb-3">
          <strong>SIDU V 4.8</strong><br />
          Platform registrasi online resmi PMB Polinela.<br />
          Website: <a href="https://sidu.pmb.polinela.ac.id" className="text-blue-500 hover:underline" target="_blank" rel="noopener noreferrer">sidu.pmb.polinela.ac.id</a>
        </p>
      </div>

      <div className="bg-blue-50 p-6 rounded-xl shadow-inner border-l-4 border-blue-500">
        <address className="not-italic text-sm leading-relaxed text-gray-700">
          <School2 className="inline mr-2 text-blue-700" /> <strong className="text-blue-700">Politeknik Negeri Lampung</strong><br />
          Jalan Soekarno-Hatta No.10, Rajabasa<br />
          Bandar Lampung, Indonesia 35141<br />
          <Phone className="inline mr-2 text-blue-500" /> Telp: 0721 703 995<br />
          <Mail className="inline mr-2 text-blue-500" /> Email: <a href="mailto:humas@polinela.ac.id" className="text-blue-500 hover:underline">humas@polinela.ac.id</a><br />
          <Globe2 className="inline mr-2 text-blue-500" /> Website: <a href="http://www.polinela.ac.id" className="text-blue-500 hover:underline" target="_blank" rel="noopener noreferrer">www.polinela.ac.id</a><br /><br />

          <strong className="text-blue-700">Panitia PMB</strong><br />
          Sekretariat PMB, Gedung A Lantai 1<br />
          Telp: 0721 703 995<br />
          Email: <a href="mailto:pmb@polinela.ac.id" className="text-blue-500 hover:underline">pmb@polinela.ac.id</a><br />
          Website: <a href="http://pmb.polinela.ac.id" className="text-blue-500 hover:underline" target="_blank" rel="noopener noreferrer">pmb.polinela.ac.id</a>
        </address>
      </div>
    </section>
  );
}
