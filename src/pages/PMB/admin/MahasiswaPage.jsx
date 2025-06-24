import { useEffect, useState } from "react";
import { getAllMahasiswa } from "../../../api/cmhsApi";
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

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await getAllMahasiswa(page, size, "idcmhsbaru,asc");
        const cmhsList = response.data;

        const detailedData = cmhsList.map((item, index) => ({
          ...item,
          namaasli: item.namaasli || "Tidak diketahui",
          tgllahir: item.tgllahir || null,
          npm: `${npmPrefix}${(page * size + index + 1).toString().padStart(2, "0")}`, // Continuous NPM across pages
          alert: !item.tgllahir,
        }));

        setData(detailedData);
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
  }, [page, npmPrefix]);

  const handleDownload = () => {
    const exportData = data.map((item) => ({
      ID: item.idcmhsbaru,
      Nama: item.namaasli,
      "Tanggal Lahir": item.tgllahir
        ? new Date(item.tgllahir).toLocaleDateString("id-ID")
        : "Tidak tersedia",
      NPM: item.npm || "Gagal dibuat",
    }));

    const worksheet = XLSX.utils.json_to_sheet(exportData);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Mahasiswa");
    XLSX.writeFile(workbook, "data_mahasiswa.xlsx");
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setPage(newPage);
    }
  };

  if (loading) return <div className="p-4">Memuat data...</div>;
  if (error) return <div className="p-4 text-red-500">{error}</div>;

  return (
    <div className="p-4">
      <div className="flex justify-between items-center mb-4 max-w-4xl mx-auto">
        <h1 className="text-2xl font-bold">Halaman Mahasiswa</h1>
        <button
          onClick={handleDownload}
          className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded"
        >
          Download Excel
        </button>
      </div>
      <MahasiswaTable
        data={data}
        npmPrefix={npmPrefix}
        setNpmPrefix={setNpmPrefix}
      />
      <div className="flex justify-center items-center mt-4 space-x-2">
        <button
          onClick={() => handlePageChange(page - 1)}
          disabled={page === 0}
          className="px-4 py-2 bg-blue-600 text-white rounded disabled:bg-gray-400"
        >
          Previous
        </button>
        <span>
          Page {page + 1} of {totalPages}
        </span>
        <button
          onClick={() => handlePageChange(page + 1)}
          disabled={page === totalPages - 1}
          className="px-4 py-2 bg-blue-600 text-white rounded disabled:bg-gray-400"
        >
          Next
        </button>
      </div>
    </div>
  );
}