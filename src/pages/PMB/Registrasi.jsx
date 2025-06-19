import React, { useState } from "react";

export default function Registrasi() {
  const [nomorPeserta, setNomorPeserta] = useState("");
  const [tanggalLahir, setTanggalLahir] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!/^\d{9}$|^\d{13}$/.test(nomorPeserta)) {
      setError("Nomor Peserta harus 9 atau 13 digit angka.");
      return;
    }

    if (!/^\d{8}$/.test(tanggalLahir)) {
      setError("Tanggal Lahir harus format DDMMYYYY tanpa simbol.");
      return;
    }

    setError("");
    alert("Form valid. Proses login/registrasi bisa dikembangkan lebih lanjut.");
  };

  return (
    <section className="container mx-auto max-w-3xl p-6 text-gray-800">
      <div className="bg-blue-50 p-6 rounded-2xl shadow-md mb-8">
        <h1 className="text-3xl font-bold text-blue-600 mb-2">SIDU</h1>
        <h2 className="text-xl font-semibold text-blue-700 mb-6">Beranda Registrasi</h2>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block font-medium mb-1">
              Nomor Peserta
            </label>
            <input
              type="text"
              value={nomorPeserta}
              onChange={(e) => setNomorPeserta(e.target.value)}
              placeholder="9 atau 13 Digit"
              maxLength={13}
              className="w-full p-2 border border-blue-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none"
            />
          </div>

          <div>
            <label className="block font-medium mb-1">
              Tanggal Lahir
            </label>
            <input
              type="text"
              value={tanggalLahir}
              onChange={(e) => setTanggalLahir(e.target.value)}
              placeholder="DDMMYYYY"
              maxLength={8}
              className="w-full p-2 border border-blue-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none"
            />
          </div>

          {error && (
            <p className="text-red-600 font-medium">{error}</p>
          )}

          <button
            type="submit"
            className="bg-blue-600 text-white px-4 py-2 rounded-xl hover:bg-blue-700 transition"
          >
            Login
          </button>
        </form>
      </div>

      <section className="bg-white border border-blue-100 p-6 rounded-2xl shadow mb-8">
        <h3 className="text-xl font-semibold text-blue-700 mb-4">Prosedur Registrasi Daftar Ulang</h3>
        <p className="mb-4">
          Untuk memudahkan dan meminimalisir kesalahan pada proses daftar ulang online, silakan unduh dan pelajari panduan registrasi daftar ulang melalui layanan sistem informasi daftar ulang (SIDU) berikut:
        </p>

        <ul className="list-disc list-inside space-y-2 text-blue-600 font-medium">
          <li>
            <a href="https://example.com/daftar-ulang-snbp-2025.pdf" target="_blank" rel="noopener noreferrer" className="hover:underline">
              DAFTAR ULANG SNBP 2025 - DOWNLOAD
            </a>
          </li>
          <li>
            <a href="https://example.com/pengumuman-camaba-snbp-2025.pdf" target="_blank" rel="noopener noreferrer" className="hover:underline">
              Pengumuman Camaba SNBP 2025 - DOWNLOAD
            </a>
          </li>
          <li>
            <a href="https://example.com/panduan-registrasi-daftar-ulang.pdf" target="_blank" rel="noopener noreferrer" className="hover:underline">
              Panduan Registrasi Daftar Ulang - SIDU - DOWNLOAD
            </a>
          </li>
        </ul>

        <p className="mt-6">
          Jika menemukan kendala pada proses daftar ulang online, silakan bergabung di Grup TELEGRAM OpenChat resmi SIDU SNBP 2025 pada link berikut :
          <br />
          <a
            href="https://t.me/joinchat/ExampleLink"
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-600 hover:underline font-medium"
          >
            Grup TELEGRAM OpenChat SIDU SNBP 2025
          </a>
        </p>

        <p className="mt-4 font-medium text-gray-700">
          Dapatkan informasi resmi daftar ulang hanya pada laman resmi kami.
        </p>

        <p className="mt-4">
          <strong>SIDU V 4.8</strong><br />
          Jaraka (J) SIDU adalah layanan jejaring akademik yang digunakan oleh Politeknik Negeri Lampung untuk layanan sistem informasi validasi dan registrasi daftar ulang calon mahasiswa baru online.
          <br />
          <a
            href="https://sidu.pmb.polinela.ac.id"
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-600 hover:underline"
          >
            sidu.pmb.polinela.ac.id
          </a>
        </p>
      </section>

      <div className="bg-blue-100 p-6 rounded-xl shadow-inner text-sm leading-relaxed">
        <address className="not-italic text-gray-700">
          <strong className="text-blue-700">Politeknik Negeri Lampung</strong><br />
          Jalan Soekarno-Hatta No.10, Rajabasa<br />
          Bandar Lampung, Lampung, Indonesia. 35141.<br />
          Telp: 0721 703 995<br />
          Email: <a href="mailto:humas@polinela.ac.id" className="text-blue-600 hover:underline">humas@polinela.ac.id</a><br />
          Website: <a href="http://www.polinela.ac.id" target="_blank" rel="noopener noreferrer" className="text-blue-600 hover:underline">www.polinela.ac.id</a><br /><br />

          <strong className="text-blue-700">Panitia PMB</strong><br />
          Sekretariat PMB, Lantai 1, Gedung A<br />
          Kampus Utama Politeknik Negeri Lampung.<br />
          Telp: 0721 703 995<br />
          Email: <a href="mailto:pmb@polinela.ac.id" className="text-blue-600 hover:underline">pmb@polinela.ac.id</a><br />
          Website: <a href="http://pmb.polinela.ac.id" target="_blank" rel="noopener noreferrer" className="text-blue-600 hover:underline">pmb.polinela.ac.id</a>
        </address>
      </div>
    </section>
  );
}
