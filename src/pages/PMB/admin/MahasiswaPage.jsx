import { useEffect, useState } from "react";
import { getAllMahasiswa, saveMahasiswa } from "../../../api/cmhsApi";
import MahasiswaTable from "../../../components/PMB/admin/MahasiswaTable";
import * as XLSX from "xlsx";

export default function MahasiswaPage() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [size] = useState(10);
  const [npmPrefix, setNpmPrefix] = useState("227530");
  const [saveStatus, setSaveStatus] = useState(null);

  // Define priorityKeys to match MahasiswaTable.jsx
  const priorityKeys = ["idcmhsbaru", "namaasli", "npm"];

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await getAllMahasiswa(page, size, "idcmhsbaru,asc", npmPrefix);
        const cmhsList = response.data;

        const detailedData = cmhsList.map((item, index) => ({
          ...item,
          namaasli: item.namaasli || "Tidak diketahui",
          tgllahir: item.tgllahir || null,
          npm: item.npm || `${npmPrefix}${(page * size + index + 1).toString().padStart(2, "0")}`,
          alert: !item.tgllahir,
        }));

        setData(detailedData);
        setTotalPages(response.totalPages);
      } catch (err) {
        setError(
          err.response?.data?.message ||
            err.message ||
            "Terjadi kesalahan saat mengambil data. Pastikan backend berjalan dan tabel 'cmhs_baru' ada di database."
        );
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [page, npmPrefix]);

  const handleDownload = () => {
    const exportData = data.map((item) => {
      const formattedItem = {
        "ID Mahasiswa": item.idcmhsbaru,
        "Nama Asli": item.namaasli,
        "Username": item.username,
        "Email": item.email || "Tidak tersedia",
        "Password": "********",
        "Aktivasi": item.activation || "Tidak tersedia",
        "Publikasi": item.publish === "T" ? "Ya" : "Tidak",
        "ID Rumpun": item.idrumpun || 0,
        "No ID": item.noid || "Tidak tersedia",
        "Tanggal Lahir": item.tgllahir
          ? new Date(item.tgllahir).toLocaleDateString("id-ID")
          : "Tidak tersedia",
        "Tipe User": item.tuser || "Tidak tersedia",
        "Tanggal Login Terakhir": item.tgllastlogin
          ? new Date(item.tgllastlogin).toLocaleDateString("id-ID")
          : "Tidak tersedia",
        "Jam Login Terakhir": item.jamlastlogin || "Tidak tersedia",
        "Status Update": item.supdate === "T" ? "Ya" : "Tidak",
        "ID Sistem PMB": item.idsistempmb || 0,
        "Langkah 0": item.step0 === "T" ? "Ya" : "Tidak",
        "Langkah 1": item.step1 === "T" ? "Ya" : "Tidak",
        "Langkah 2": item.step2 === "T" ? "Ya" : "Tidak",
        "Langkah 3": item.step3 === "T" ? "Ya" : "Tidak",
        "Langkah 4": item.step4 === "T" ? "Ya" : "Tidak",
        "Langkah 5": item.step5 === "T" ? "Ya" : "Tidak",
        "Langkah 6": item.step6 === "T" ? "Ya" : "Tidak",
        "Tanggal Bayar": item.tglbayar
          ? new Date(item.tglbayar).toLocaleDateString("id-ID")
          : "Tidak tersedia",
        "ID Jenis Bayar": item.idjenisbayar || 0,
        "Jumlah Bayar": item.jumlahbayar || 0,
        "No Rekening Bank": item.norekbank || "Tidak tersedia",
        "Nama Bank": item.nmbank || "Tidak tersedia",
        "Nama Nasabah": item.nmnasabah || "Tidak tersedia",
        "Bukti": item.bukti || "Tidak tersedia",
        "Transaksi": item.trx || "Tidak tersedia",
        "MPIN": item.mpin || "Tidak tersedia",
        "ID UKT": item.idukt || 0,
        "Nilai": item.nilai || 0,
        "Kode Prodi": item.kdprodi || "Tidak tersedia",
        "No Registrasi": item.noreg || "Tidak tersedia",
        "Kunci Registrasi": item.keyreg || "Tidak tersedia",
        "Kunci Password": "********",
        "No HP": item.nohpne || "Tidak tersedia",
        "IP User": item.userip || "Tidak tersedia",
        "Tanggal Validasi Registrasi": item.tglvalreg
          ? new Date(item.tglvalreg).toLocaleDateString("id-ID")
          : "Tidak tersedia",
        "ID Rekening": item.idrekening || 0,
        "ID Jalur PMB": item.idjalurpmb || 0,
        "Tahun": item.tahun || "Tidak tersedia",
        "Beasiswa": item.beasiswa || "Tidak tersedia",
        "Status Bayar": item.statusbayar || "Tidak tersedia",
        "SPI": item.spi || 0,
        "Bukti SPI": item.bukti_spi || "Tidak tersedia",
        "Jumlah Bayar SPI": item.jumlahbayar_spi || 0,
        "Virtual Account": item.va || "Tidak tersedia",
        "Tanggal Kedaluwarsa VA": item.vaexpdate
          ? new Date(item.vaexpdate).toLocaleDateString("id-ID")
          : "Tidak tersedia",
        "NPM": item.npm || "Gagal dibuat",
      };
      return formattedItem;
    });

    const worksheet = XLSX.utils.json_to_sheet(exportData);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Mahasiswa");
    XLSX.writeFile(workbook, `data_mahasiswa_${new Date().toISOString().split("T")[0]}.xlsx`);
  };

  const handleSaveToDatabase = async () => {
    setSaveStatus(null);
    try {
      for (const item of data) {
        await saveMahasiswa({
          ...item,
          npm: item.npm,
        });
      }
      setSaveStatus("Data berhasil disimpan ke database!");
    } catch (err) {
      setSaveStatus(
        err.response?.data?.message ||
          err.message ||
          "Gagal menyimpan data ke database. Pastikan backend berjalan dan tabel 'cmhs_baru' ada."
      );
    }
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setPage(newPage);
    }
  };

  if (loading) {
    return (
      <div className="container p-4">
        <div className="main-content animate-pulse">
          <div className="h-8 bg-gray-200 dark:bg-gray-700 rounded w-1/3 mb-4"></div>
          <div className="h-10 bg-gray-200 dark:bg-gray-700 rounded w-full mb-4"></div>
          <div className="overflow-x-auto rounded-lg">
            <table className="w-full border-collapse table-auto bg-white dark:bg-gray-800">
              <thead>
                <tr className="bg-teal-700 text-white">
                  <th className="border p-3 h-10"></th>
                  {priorityKeys.map((key) => (
                    <th key={key} className="border p-3 h-10">
                      {key === "idcmhsbaru" ? "ID Mahasiswa" : key === "namaasli" ? "Nama Asli" : "NPM"}
                    </th>
                  ))}
                  <th className="border p-3 h-10 md:hidden">Detail</th>
                </tr>
              </thead>
              <tbody>
                {[...Array(5)].map((_, index) => (
                  <tr key={index} className="bg-gray-50 dark:bg-gray-700">
                    <td className="border p-3 h-10 bg-gray-200 dark:bg-gray-600"></td>
                    {priorityKeys.map((_, i) => (
                      <td key={i} className="border p-3 h-10 bg-gray-200 dark:bg-gray-600"></td>
                    ))}
                    <td className="border p-3 h-10 bg-gray-200 dark:bg-gray-600 md:hidden"></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container p-4">
        <div className="main-content">
          <div className="error animate-fade-in">{error}</div>
        </div>
      </div>
    );
  }

  return (
    <div className="container p-4">
      <div className="main-content">
        <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-6 gap-4">
          <h1 className="text-2xl font-bold">Halaman Mahasiswa</h1>
          <div className="flex flex-col sm:flex-row gap-3 w-full md:w-auto">
            <button
              onClick={handleDownload}
              className="btn-submit flex items-center justify-center gap-2 text-sm"
            >
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
              Download Excel
            </button>
            <button
              onClick={handleSaveToDatabase}
              className="btn-submit flex items-center justify-center gap-2 text-sm"
            >
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
              Simpan ke Database
            </button>
          </div>
        </div>
        {saveStatus && (
          <div
            className={`p-4 mb-6 rounded-lg shadow-sm animate-fade-in ${
              saveStatus.includes("berhasil")
                ? "bg-green-100 text-green-700 dark:bg-green-900/20 dark:text-green-300"
                : "error dark:bg-red-900/20 dark:text-red-300"
            }`}
          >
            {saveStatus}
          </div>
        )}
        <MahasiswaTable
          data={data}
          npmPrefix={npmPrefix}
          setNpmPrefix={setNpmPrefix}
        />
        <div className="flex flex-col sm:flex-row justify-center items-center mt-6 gap-4">
          <button
            onClick={() => handlePageChange(page - 1)}
            disabled={page === 0}
            className="btn-submit flex items-center justify-center gap-2 w-full sm:w-32 text-sm disabled:opacity-50 disabled:cursor-not-allowed"
          >
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
                d="M15 19l-7-7 7-7"
              ></path>
            </svg>
            Previous
          </button>
          <span className="text-sm font-semibold">
            Page {page + 1} of {totalPages}
          </span>
          <button
            onClick={() => handlePageChange(page + 1)}
            disabled={page === totalPages - 1}
            className="btn-submit flex items-center justify-center gap-2 w-full sm:w-32 text-sm disabled:opacity-50 disabled:cursor-not-allowed"
          >
            Next
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
                d="M9 5l7 7-7 7"
              ></path>
            </svg>
          </button>
        </div>
      </div>
    </div>
  );
}