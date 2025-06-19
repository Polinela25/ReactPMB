export default function Registrasi() {
  return (
    <div className="max-w-4xl mx-auto p-6 space-y-6 font-sans text-gray-800">
      <h1 className="text-3xl font-bold mb-4">Registrasi User Akun Pendaftar</h1>

      <p className="text-sm italic text-gray-600">
        Entrikan data akun pendaftaran Anda dengan lengkap dan benar.
        Kami tidak bertanggungjawab atas kesalahan yang Anda entrikan.
      </p>

      <form className="space-y-4">
        {/* NISN */}
        <div>
          <label className="block font-medium">NISN *</label>
          <input type="text" className="w-full border px-3 py-2 rounded" placeholder="Nomor Induk Siswa Nasional" />
        </div>

        {/* Nama */}
        <div>
          <label className="block font-medium">Nama Lengkap *</label>
          <input type="text" className="w-full border px-3 py-2 rounded" placeholder="Nama lengkap tanpa disingkat" />
        </div>

        {/* Tanggal Lahir */}
        <div>
          <label className="block font-medium">Tanggal Lahir *</label>
          <input type="text" className="w-full border px-3 py-2 rounded" placeholder="dd/mm/yyyy" />
        </div>

        {/* Nama Sekolah */}
        <div>
          <label className="block font-medium">Nama Sekolah *</label>
          <input type="text" className="w-full border px-3 py-2 rounded" placeholder="Contoh: SMA NEGERI 1 BANDAR LAMPUNG" />
        </div>

        {/* Tipe Sekolah */}
        <div>
          <label className="block font-medium">Tipe Sekolah *</label>
          <select className="w-full border px-3 py-2 rounded">
            <option value="">- Pilih Tipe Sekolah -</option>
            <option value="SMA">SMA</option>
            <option value="SMK">SMK</option>
          </select>
        </div>

        {/* Keahlian / Jurusan */}
        <div>
          <label className="block font-medium">Keahlian / Jurusan *</label>
          <select className="w-full border px-3 py-2 rounded">
            <option value="">- Pilih Jurusan -</option>
            <option value="IPA">IPA</option>
            <option value="IPS">IPS</option>
            <option value="Teknik">Teknik</option>
            {/* Tambahkan opsi lainnya */}
          </select>
        </div>

        {/* Lulus Tahun */}
        <div>
          <label className="block font-medium">Lulus Tahun *</label>
          <input type="text" className="w-full border px-3 py-2 rounded" defaultValue="2025" />
        </div>

        {/* Email */}
        <div>
          <label className="block font-medium">Email *</label>
          <input type="email" className="w-full border px-3 py-2 rounded" />
        </div>

        {/* Konfirmasi Email */}
        <div>
          <label className="block font-medium">Konfirmasi Email *</label>
          <input type="email" className="w-full border px-3 py-2 rounded" />
        </div>

        {/* No HP */}
        <div>
          <label className="block font-medium">No. Handphone *</label>
          <input type="tel" className="w-full border px-3 py-2 rounded" placeholder="+62" />
        </div>

        {/* Pernyataan */}
        <div className="mt-4">
          <label className="block font-medium mb-1">Pernyataan</label>
          <p className="text-sm text-gray-700">
            Dengan ini saya menyatakan bahwa saya mendaftarkan akun ke layanan SPMB Online untuk tujuan pendaftaran mahasiswa baru.
          </p>
        </div>

        {/* Submit */}
        <button type="submit" className="mt-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">
          Submit Registrasi
        </button>
      </form>

      <section className="mt-8 text-sm text-gray-600 space-y-4">
        <h2 className="font-semibold">Validitas Data</h2>
        <p>Bagi Pendaftar, wajib untuk mengisi data yang sebenar-benarnya...</p>

        <h2 className="font-semibold">Email Wajib Dimiliki</h2>
        <p>Anda wajib membuka email Anda untuk melihat email konfirmasi...</p>

        <h2 className="font-semibold">Cek SPAM Email</h2>
        <p>Jika Anda telah melakukan registrasi namun tidak menerima email...</p>
      </section>

      <footer className="mt-10 border-t pt-4 text-xs text-gray-500">
        <p>© J Admission. All rights reserved | jaraka</p>
        <p>Client ID: 01012019 Politeknik Negeri Lampung</p>
      </footer>
    </div>
  );
}
