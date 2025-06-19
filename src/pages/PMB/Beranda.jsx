import React from "react";

export default function Beranda() {
  return (
    <section className="container mx-auto p-6 max-w-4xl text-gray-800">
      <div className="bg-blue-50 p-8 rounded-2xl shadow-md mb-8">
        <h1 className="text-3xl font-bold text-blue-600 mb-4">
          Selamat Datang Calon Mahasiswa Baru!
        </h1>
        <p className="mb-4 text-lg">
          <span className="font-semibold">Registrasi Daftar Ulang</span> <br />
          Sebuah mekanisme validasi data dan registrasi daftar ulang calon mahasiswa baru secara online menggunakan layanan sistem informasi berbasis web. Mahasiswa baru yang dimaksud adalah mahasiswa yang telah mengikuti dan menyelesaikan tahap pendaftaran dan seleksi pada masing-masing jalur penerimaan, dan telah dinyatakan lulus dan diterima secara resmi sebagai Calon Mahasiswa Baru Politeknik Negeri Lampung.
        </p>

        <ol className="list-decimal list-inside space-y-2 text-base">
          <li>
            <strong>Registrasi Data Induk Mahasiswa</strong><br />
            Mekanisme registrasi data induk mahasiswa baru secara online.
          </li>
          <li>
            <strong>Registrasi Pembayaran UKT Mahasiswa</strong><br />
            Mekanisme registrasi pembayaran UKT mahasiswa.
          </li>
          <li>
            <strong>Finalisasi dan Pemberkasan Daftar Ulang</strong><br />
            Mekanisme finalisasi dan pemberkasan data fisik.
          </li>
        </ol>

        <p className="mt-6 text-lg font-medium">
          Selamat Datang Calon Mahasiswa Baru Politeknik Negeri Lampung dan selamat bergabung menjadi keluarga besar sivitas akademika Politeknik Negeri Lampung.
        </p>
      </div>

      <div className="bg-white p-6 rounded-xl shadow-md mb-8 border border-blue-100">
        <h2 className="text-2xl font-bold text-blue-600 mb-4">
          Calon Mahasiswa Baru
        </h2>
        <p className="mb-4">
          <strong>Pendaftar Beasiswa KIP-KULIAH</strong><br />
          Calon mahasiswa baru yang lulus dengan status pengusul beasiswa KIP Kuliah untuk jenjang Pendidikan Diploma III (D3) dan Diploma IV (D4)/Sarjana Terapan (S1.Tr) Politeknik Negeri Lampung tahun 2025 agar memperhatikan prosedur registrasi keuangan (pembayaran pendidikan UKT). Informasi tentang pembayaran UKT dilampirkan pada laman user calon mahasiswa baru.
        </p>
        <p>
          <strong>SIDU V 4.8</strong><br />
          Jaraka (J) SIDU adalah layanan jejaring akademik yang digunakan oleh Politeknik Negeri Lampung untuk layanan sistem informasi validasi dan registrasi daftar ulang calon mahasiswa baru online.
          <br />
          Website:{" "}
          <a
            href="https://sidu.pmb.polinela.ac.id"
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-500 hover:underline"
          >
            sidu.pmb.polinela.ac.id
          </a>
        </p>
      </div>

      <div className="bg-blue-100 p-6 rounded-xl shadow-inner">
        <address className="not-italic text-sm leading-relaxed text-gray-700">
          <strong className="text-blue-700">Politeknik Negeri Lampung</strong><br />
          Jalan Soekarno-Hatta No.10, Rajabasa<br />
          Bandar Lampung, Lampung, Indonesia. 35141.<br />
          Telp: 0721 703 995<br />
          Email: <a href="mailto:humas@polinela.ac.id" className="text-blue-500 hover:underline">humas@polinela.ac.id</a><br />
          Website: <a href="http://www.polinela.ac.id" target="_blank" rel="noopener noreferrer" className="text-blue-500 hover:underline">www.polinela.ac.id</a><br /><br />

          <strong className="text-blue-700">Panitia PMB</strong><br />
          Sekretariat PMB, Lantai 1, Gedung A<br />
          Kampus Utama Politeknik Negeri Lampung.<br />
          Telp: 0721 703 995<br />
          Email: <a href="mailto:pmb@polinela.ac.id" className="text-blue-500 hover:underline">pmb@polinela.ac.id</a><br />
          Website: <a href="http://pmb.polinela.ac.id" target="_blank" rel="noopener noreferrer" className="text-blue-500 hover:underline">pmb.polinela.ac.id</a>
        </address>
      </div>
    </section>
  );
}
