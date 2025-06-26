import React, { useEffect, useState } from "react";
import { getAllMahasiswa } from "../../../api/cmhsApi";
import * as XLSX from "xlsx";

export default function MahasiswaListPage() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [size] = useState(10);
  const [expandedRows, setExpandedRows] = useState({});

  const priorityKeys = ["idcmhsbaru", "namaasli", "tgllahir", "npm"];
  const humanReadableKeys = {
    idcmhsbaru: "ID Mahasiswa",
    namaasli: "Nama Asli",
    tgllahir: "Tanggal Lahir",
    npm: "NPM",
    tahun: "Tahun",
    jurusan: "Jurusan",
    kdprodi: "Program Studi",
    created_at: "Tanggal Registrasi",
  };

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await getAllMahasiswa(page, size, "created_at,asc");
        setData(response.data);
        setTotalPages(response.totalPages);
      } catch (err) {
        setError(
          err.response?.data?.message ||
            err.message ||
            "Terjadi kesalahan saat mengambil data."
        );
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [page]);

  const handleDownload = () => {
    const exportData = data.map((item) => ({
      "ID Mahasiswa": item.idcmhsbaru,
      "Nama Asli": item.namaasli,
      "Tanggal Lahir": item.tgllahir
        ? new Date(item.tgllahir).toLocaleDateString("id-ID")
        : "Tidak tersedia",
      NPM: item.npm,
      Tahun: item.tahun,
      Jurusan: item.jurusan,
      "Program Studi": item.kdprodi,
      "Tanggal Registrasi": item.created_at
        ? new Date(item.created_at).toLocaleDateString("id-ID")
        : "Tidak tersedia",
    }));

    const worksheet = XLSX.utils.json_to_sheet(exportData);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Daftar Mahasiswa");
    XLSX.writeFile(workbook, `daftar_mahasiswa_${new Date().toISOString().split("T")[0]}.xlsx`);
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setPage(newPage);
    }
  };

  const toggleRow = (id) => {
    setExpandedRows((prev) => ({
      ...prev,
      [id]: !prev[id],
    }));
  };

  const formatValue = (key, value) => {
    if (key === "tgllahir" || key === "created_at") {
      return value
        ? new Date(value).toLocaleDateString("id-ID")
        : "Tidak tersedia";
    }
    return value || "Tidak tersedia";
  };

  if (loading) {
    return (
      <div className="container p-4">
        <div className="main-content animate-pulse">
          <div className="h-8 bg-gray-200 dark:bg-gray-700 rounded w-1/3 mb-4"></div>
          <div className="overflow-x-auto rounded-lg shadow-md">
            <table className="w-full border-collapse table-auto bg-white dark:bg-gray-800">
              <thead>
                <tr className="bg-teal-700 text-white">
                  {priorityKeys.map((key) => (
                    <th key={key} className="border p-3 h-10">
                      {humanReadableKeys[key]}
                    </th>
                  ))}
                  <th className="border p-3 h-10 md:hidden">Detail</th>
                </tr>
              </thead>
              <tbody>
                {[...Array(5)].map((_, index) => (
                  <tr key={index} className="bg-gray-50 dark:bg-gray-700">
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
          <div className="error p-4 rounded-lg shadow-sm animate-fade-in dark:bg-red-900/20 dark:text-red-300">
            {error}
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="container p-4">
      <div className="main-content">
        <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-6 gap-4">
          <h1 className="text-2xl font-bold">Daftar Mahasiswa Terdaftar</h1>
          <button
            onClick={handleDownload}
            className="btn-submit flex items-center justify-center gap-2 text-sm"
            aria-label="Download Excel"
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
        </div>
        <div className="overflow-x-auto rounded-lg shadow-md">
          <table className="w-full border-collapse table-auto bg-white dark:bg-gray-800">
            <thead>
              <tr className="bg-teal-700 text-white sticky top-0 z-10">
                {priorityKeys.map((key) => (
                  <th
                    key={key}
                    className="border p-3 text-left text-xs font-semibold table-cell"
                  >
                    {humanReadableKeys[key]}
                  </th>
                ))}
                <th className="border p-3 text-left text-xs font-semibold md:hidden">
                  Detail
                </th>
              </tr>
            </thead>
            <tbody>
              {data.map((item) => (
                <React.Fragment key={item.idcmhsbaru}>
                  <tr
                    className={`transition-colors duration-200 ${
                      expandedRows[item.idcmhsbaru]
                        ? "bg-teal-100 dark:bg-teal-900/50"
                        : item.idcmhsbaru % 2 === 0
                        ? "bg-gray-50 dark:bg-gray-700"
                        : "bg-white dark:bg-gray-800"
                    } hover:bg-teal-50 dark:hover:bg-teal-900/30`}
                  >
                    {priorityKeys.map((key) => (
                      <td key={key} className="border p-3 text-xs table-cell">
                        {formatValue(key, item[key])}
                      </td>
                    ))}
                    <td className="border p-3 text-xs md:hidden">
                      <button
                        onClick={() => toggleRow(item.idcmhsbaru)}
                        className="text-teal-600 hover:text-teal-800 font-semibold transition-colors duration-200"
                        aria-label={expandedRows[item.idcmhsbaru] ? "Sembunyikan detail" : "Tampilkan detail"}
                      >
                        {expandedRows[item.idcmhsbaru] ? "Sembunyikan" : "Tampilkan"}
                      </button>
                    </td>
                  </tr>
                  {expandedRows[item.idcmhsbaru] && (
                    <tr className="md:hidden">
                      <td
                        colSpan={priorityKeys.length + 1}
                        className="border p-4 bg-gray-100 dark:bg-gray-700"
                      >
                        <div className="grid grid-cols-1 gap-3 animate-slide-down">
                          {["tahun", "jurusan", "kdprodi", "created_at"].map((key) => (
                            <div key={key} className="flex flex-col">
                              <span className="font-semibold text-xs text-gray-700 dark:text-gray-300">
                                {humanReadableKeys[key]}:
                              </span>
                              <span className="text-xs text-gray-600 dark:text-gray-400">
                                {formatValue(key, item[key])}
                              </span>
                            </div>
                          ))}
                        </div>
                      </td>
                    </tr>
                  )}
                </React.Fragment>
              ))}
            </tbody>
          </table>
        </div>
        <div className="flex flex-col sm:flex-row justify-center items-center mt-6 gap-4">
          <button
            onClick={() => handlePageChange(page - 1)}
            disabled={page === 0}
            className="btn-submit flex items-center justify-center gap-2 w-full sm:w-32 text-sm disabled:opacity-50 disabled:cursor-not-allowed"
            aria-label="Halaman Sebelumnya"
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
            aria-label="Halaman Berikutnya"
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