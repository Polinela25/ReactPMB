import React, { useState } from "react";

export default function MahasiswaTable({ data, npmPrefix, setNpmPrefix }) {
  const [expandedRows, setExpandedRows] = useState({});

  const formatDate = (date) => {
    if (!date) return "Tidak tersedia";
    try {
      const d = new Date(date);
      return d.toLocaleDateString("id-ID", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric",
      });
    } catch (error) {
      return "Tanggal tidak valid";
    }
  };

  const formatBoolean = (value) => {
    if (value === "T") return "Ya";
    if (value === "F") return "Tidak";
    return value || "Tidak tersedia";
  };

  const formatSensitive = (value) => {
    return value ? "********" : "Tidak tersedia";
  };

  const formatValue = (key, value) => {
    if (["tgllahir", "tgllastlogin", "tglbayar", "tglvalreg", "vaexpdate"].includes(key)) {
      return formatDate(value);
    }
    if (["publish", "supdate", "step0", "step1", "step2", "step3", "step4", "step5", "step6"].includes(key)) {
      return formatBoolean(value);
    }
    if (["password", "paskey", "email", "nohpne", "mpin"].includes(key)) {
      return formatSensitive(value);
    }
    return value === 0 ? "" : (value || "Tidak tersedia");
  };

  const keys = [
    "idcmhsbaru", "namaasli", "username", "email", "password", "activation", "publish",
    "idrumpun", "noid", "tgllahir", "tuser", "tgllastlogin", "jamlastlogin", "supdate",
    "idsistempmb", "step0", "step1", "step2", "step3", "step4", "step5", "step6",
    "tglbayar", "idjenisbayar", "jumlahbayar", "norekbank", "nmbank", "nmnasabah",
    "bukti", "trx", "mpin", "idukt", "nilai", "kdprodi", "noreg", "keyreg", "paskey",
    "nohpne", "userip", "tglvalreg", "idrekening", "idjalurpmb", "tahun", "beasiswa",
    "statusbayar", "spi", "bukti_spi", "jumlahbayar_spi", "va", "vaexpdate", "npm"
  ];

  const humanReadableKeys = {
    idcmhsbaru: "ID Mahasiswa",
    namaasli: "Nama Asli",
    username: "Username",
    email: "Email",
    password: "Password",
    activation: "Aktivasi",
    publish: "Publikasi",
    idrumpun: "ID Rumpun",
    noid: "No ID",
    tgllahir: "Tanggal Lahir",
    tuser: "Tipe User",
    tgllastlogin: "Tanggal Login Terakhir",
    jamlastlogin: "Jam Login Terakhir",
    supdate: "Status Update",
    idsistempmb: "ID Sistem PMB",
    step0: "Langkah 0",
    step1: "Langkah 1",
    step2: "Langkah 2",
    step3: "Langkah 3",
    step4: "Langkah 4",
    step5: "Langkah 5",
    step6: "Langkah 6",
    tglbayar: "Tanggal Bayar",
    idjenisbayar: "ID Jenis Bayar",
    jumlahbayar: "Jumlah Bayar",
    norekbank: "No Rekening Bank",
    nmbank: "Nama Bank",
    nmnasabah: "Nama Nasabah",
    bukti: "Bukti",
    trx: "Transaksi",
    mpin: "MPIN",
    idukt: "ID UKT",
    nilai: "Nilai",
    kdprodi: "Kode Prodi",
    noreg: "No Registrasi",
    keyreg: "Kunci Registrasi",
    paskey: "Kunci Password",
    nohpne: "No HP",
    userip: "IP User",
    tglvalreg: "Tanggal Validasi Registrasi",
    idrekening: "ID Rekening",
    idjalurpmb: "ID Jalur PMB",
    tahun: "Tahun",
    beasiswa: "Beasiswa",
    statusbayar: "Status Bayar",
    spi: "SPI",
    bukti_spi: "Bukti SPI",
    jumlahbayar_spi: "Jumlah Bayar SPI",
    va: "Virtual Account",
    vaexpdate: "Tanggal Kedaluwarsa VA",
    npm: "NPM"
  };

  const priorityKeys = ["idcmhsbaru", "namaasli", "npm"];

  const toggleRow = (id) => {
    setExpandedRows((prev) => ({
      ...prev,
      [id]: !prev[id],
    }));
  };

  return (
    <div className="main-content">
      <h1 className="text-2xl font-bold mb-4">Data Mahasiswa</h1>

      <div className="mb-4">
        <label htmlFor="npmPrefix" className="block font-semibold mb-2 text-sm">
          Awalan NPM:
        </label>
        <input
          type="text"
          id="npmPrefix"
          value={npmPrefix}
          onChange={(e) => setNpmPrefix(e.target.value)}
          className="w-full p-2 border rounded-lg shadow-sm focus:ring-2 focus:ring-teal-500 focus:border-teal-500 text-sm dark:bg-gray-700 dark:text-white dark:border-gray-600"
          placeholder="Masukkan awalan NPM (misal: 227530)"
        />
      </div>

      {data.length === 0 ? (
        <p className="text-gray-500 text-center py-4">Tidak ada data tersedia</p>
      ) : (
        <div className="overflow-x-auto rounded-lg shadow-md">
          <table className="w-full border-collapse table-auto bg-white dark:bg-gray-800">
            <thead>
              <tr className="bg-teal-700 text-white sticky top-0 z-10">
                
                {priorityKeys.map((key) => (
                  <th
                    key={key}
                    className="border p-3 text-left text-xs font-semibold table-cell"
                  >
                    {humanReadableKeys[key] || key}
                  </th>
                ))}
                <th className="border p-3 text-left text-xs font-semibold md:hidden">
                  Detail
                </th>
              </tr>
            </thead>
            <tbody>
              {data.map((item, index) => (
                <React.Fragment key={item.idcmhsbaru}>
                  <tr
                    className={`transition-colors duration-200 ${
                      item.alert
                        ? "bg-red-50 text-red-700 dark:bg-red-900/20 dark:text-red-300"
                        : index % 2 === 0
                        ? "bg-gray-50 dark:bg-gray-700"
                        : "bg-white dark:bg-gray-800"
                    } hover:bg-teal-50 dark:hover:bg-teal-900/30`}
                  >
                    
                    {priorityKeys.map((key) => (
                      <td
                        key={key}
                        className="border p-3 text-xs table-cell"
                      >
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
                          {keys
                            .filter((key) => !priorityKeys.includes(key))
                            .map((key) => (
                              <div key={key} className="flex flex-col">
                                <span className="font-semibold text-xs text-gray-700 dark:text-gray-300">
                                  {humanReadableKeys[key] || key}:
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
      )}
    </div>
  );
}