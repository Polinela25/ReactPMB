import React, { useState } from "react";
import { saveMahasiswa, getMahasiswaCountByYearAndProdi } from "../../../api/cmhsApi";

export default function MahasiswaInputPage() {
  const [formData, setFormData] = useState({
    namaasli: "",
    tgllahir: "",
    tahun: "",
    jurusan: "",
    kdprodi: "",
  });
  const [npm, setNpm] = useState("");
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [loading, setLoading] = useState(false);

  // Options for dropdowns
  const yearOptions = [2023, 2024, 2025];
  const departmentOptions = [
    { code: "TI", name: "Teknik Informatika" },
    { code: "SI", name: "Sistem Informasi" },
    { code: "AK", name: "Akuntansi" },
  ];
  const prodiOptions = [
    { code: "01", name: "Teknik Informatika" },
    { code: "02", name: "Sistem Informasi" },
    { code: "03", name: "Akuntansi" },
  ];

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setNpm(""); // Reset NPM when form changes
    setError(null);
    setSuccess(null);
  };

  const validateForm = () => {
    if (!formData.namaasli) return "Nama wajib diisi.";
    if (!formData.tgllahir) return "Tanggal lahir wajib diisi.";
    if (!formData.tahun) return "Tahun wajib dipilih.";
    if (!formData.jurusan) return "Jurusan wajib dipilih.";
    if (!formData.kdprodi) return "Program studi wajib dipilih.";
    return null;
  };

  const generateNpm = async () => {
    const validationError = validateForm();
    if (validationError) {
      setError(validationError);
      return;
    }

    setLoading(true);
    setError(null);
    try {
      const count = await getMahasiswaCountByYearAndProdi(formData.tahun, formData.kdprodi);
      const registrationNumber = (count + 1).toString().padStart(2, "0");
      const newNpm = `${formData.tahun}${formData.jurusan}${formData.kdprodi}${registrationNumber}`;
      setNpm(newNpm);
      setSuccess("NPM berhasil digenerate!");
    } catch (err) {
      setError(err.message || "Gagal menghubungi server untuk generate NPM.");
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async () => {
    if (!npm) {
      setError("Generate NPM terlebih dahulu.");
      return;
    }

    setLoading(true);
    setError(null);
    try {
      await saveMahasiswa({
        namaasli: formData.namaasli,
        tgllahir: formData.tgllahir,
        npm,
        tahun: formData.tahun,
        jurusan: formData.jurusan,
        kdprodi: formData.kdprodi,
      });
      setSuccess("Data mahasiswa berhasil disimpan!");
      setFormData({ namaasli: "", tgllahir: "", tahun: "", jurusan: "", kdprodi: "" });
      setNpm("");
    } catch (err) {
      setError(err.message || "Gagal menyimpan data ke database.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container p-4">
      <div className="main-content animate-fade-in">
        <h1 className="text-2xl font-bold mb-6">Input Data Mahasiswa</h1>
        {error && (
          <div className="error p-4 mb-4 rounded-lg shadow-sm animate-fade-in dark:bg-red-900/20 dark:text-red-300">
            {error}
          </div>
        )}
        {success && (
          <div className="bg-green-100 text-green-700 p-4 mb-4 rounded-lg shadow-sm animate-fade-in dark:bg-green-900/20 dark:text-green-300">
            {success}
          </div>
        )}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label htmlFor="namaasli" className="block font-semibold mb-2 text-sm">
              Nama Lengkap
            </label>
            <input
              type="text"
              id="namaasli"
              name="namaasli"
              value={formData.namaasli}
              onChange={handleChange}
              className="w-full p-2 border rounded-lg shadow-sm focus:ring-2 focus:ring-teal-500 text-sm dark:bg-gray-700 dark:text-white dark:border-gray-600"
              placeholder="Masukkan nama lengkap"
              aria-label="Nama Lengkap"
            />
          </div>
          <div>
            <label htmlFor="tgllahir" className="block font-semibold mb-2 text-sm">
              Tanggal Lahir
            </label>
            <input
              type="date"
              id="tgllahir"
              name="tgllahir"
              value={formData.tgllahir}
              onChange={handleChange}
              className="w-full p-2 border rounded-lg shadow-sm focus:ring-2 focus:ring-teal-500 text-sm dark:bg-gray-700 dark:text-white dark:border-gray-600"
              aria-label="Tanggal Lahir"
            />
          </div>
          <div>
            <label htmlFor="tahun" className="block font-semibold mb-2 text-sm">
              Tahun Akademik
            </label>
            <select
              id="tahun"
              name="tahun"
              value={formData.tahun}
              onChange={handleChange}
              className="w-full p-2 border rounded-lg shadow-sm focus:ring-2 focus:ring-teal-500 text-sm dark:bg-gray-700 dark:text-white dark:border-gray-600"
              aria-label="Tahun Akademik"
            >
              <option value="">Pilih Tahun</option>
              {yearOptions.map((year) => (
                <option key={year} value={year}>
                  {year}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label htmlFor="jurusan" className="block font-semibold mb-2 text-sm">
              Jurusan
            </label>
            <select
              id="jurusan"
              name="jurusan"
              value={formData.jurusan}
              onChange={handleChange}
              className="w-full p-2 border rounded-lg shadow-sm focus:ring-2 focus:ring-teal-500 text-sm dark:bg-gray-700 dark:text-white dark:border-gray-600"
              aria-label="Jurusan"
            >
              <option value="">Pilih Jurusan</option>
              {departmentOptions.map((dept) => (
                <option key={dept.code} value={dept.code}>
                  {dept.name} ({dept.code})
                </option>
              ))}
            </select>
          </div>
          <div>
            <label htmlFor="kdprodi" className="block font-semibold mb-2 text-sm">
              Program Studi
            </label>
            <select
              id="kdprodi"
              name="kdprodi"
              value={formData.kdprodi}
              onChange={handleChange}
              className="w-full p-2 border rounded-lg shadow-sm focus:ring-2 focus:ring-teal-500 text-sm dark:bg-gray-700 dark:text-white dark:border-gray-600"
              aria-label="Program Studi"
            >
              <option value="">Pilih Prodi</option>
              {prodiOptions.map((prodi) => (
                <option key={prodi.code} value={prodi.code}>
                  {prodi.name} ({prodi.code})
                </option>
              ))}
            </select>
          </div>
          <div className="flex items-end">
            <button
              onClick={generateNpm}
              disabled={loading}
              className="btn-submit w-full flex items-center justify-center gap-2 text-sm disabled:opacity-50 disabled:cursor-not-allowed"
              aria-label="Generate NPM"
            >
              {loading ? (
                <svg className="w-4 h-4 animate-spin" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
              ) : (
                <svg
                  className="w-4 h-4"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M12 4v16m8-8H4"
                  ></path>
                </svg>
              )}
              {loading ? "Memproses..." : "Generate NPM"}
            </button>
          </div>
        </div>
        {npm && (
          <div className="mt-4 p-4 bg-gray-100 dark:bg-gray-700 rounded-lg shadow-sm animate-slide-down">
            <p className="font-semibold text-sm">NPM Generated:</p>
            <p className="text-lg font-bold text-teal-600 dark:text-teal-400">{npm}</p>
            <button
              onClick={handleSubmit}
              disabled={loading}
              className="btn-submit mt-4 flex items-center justify-center gap-2 text-sm disabled:opacity-50 disabled:cursor-not-allowed"
              aria-label="Simpan Data"
            >
              {loading ? (
                <svg className="w-4 h-4 animate-spin" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
              ) : (
                <svg
                  className="w-4 h-4"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="M8 7H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-3m-1 4l-3 3m0 0l-3-3m3 3V4"
                  ></path>
                </svg>
              )}
              {loading ? "Menyimpan..." : "Simpan Data"}
            </button>
          </div>
        )}
      </div>
    </div>
  );
}