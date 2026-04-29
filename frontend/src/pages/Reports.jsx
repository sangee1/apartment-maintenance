import React, { useEffect, useState } from "react";
import API from "../services/api";// adjust if your path differs
import jsPDF from "jspdf";
import html2canvas from "html2canvas";

const Reports = () => {
    const [year, setYear] = useState(new Date().getFullYear());
    const [quarter, setQuarter] = useState(1);
    const [report, setReport] = useState(null);

    const loadReport = async () => {
        try {
            const res = await API.get("/reports/quarterly", {
                params: { year, quarter },
            });
            setReport(res.data);
        } catch (err) {
            console.error("Error loading report:", err);
        }
    };

    const downloadPDF = async () => {
        const input = document.getElementById("report-content");

        const canvas = await html2canvas(input);
        const imgData = canvas.toDataURL("image/png");

        const pdf = new jsPDF();
        const imgWidth = 190;
        const imgHeight = (canvas.height * imgWidth) / canvas.width;

        pdf.addImage(imgData, "PNG", 10, 10, imgWidth, imgHeight);
        pdf.save("Quarterly_Report.pdf");
    };

    const downloadCSV = () => {
    const rows = report.payments.map((p) => [
      p.flat?.flatNumber,
      p.status,
      p.amount,
    ]);

    const csvContent =
      "Flat,Status,Amount\n" +
      rows.map((e) => e.join(",")).join("\n");

    const blob = new Blob([csvContent], { type: "text/csv" });
    const url = window.URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;
    a.download = "report.csv";
    a.click();
  };

    useEffect(() => {
        loadReport();
    }, [year, quarter]);

    return (
        <div style={{ padding: "20px" }}>
            <h2>Quarterly Report</h2>

            {/* Filters */}
            <div style={{ marginBottom: "20px" }}>
                <label>Year: </label>
                <input
                    type="number"
                    value={year}
                    onChange={(e) => setYear(Number(e.target.value))}
                />

                <label style={{ marginLeft: "10px" }}>Quarter: </label>
                <select
                    value={quarter}
                    onChange={(e) => setQuarter(Number(e.target.value))}
                >
                    <option value={1}>Q1</option>
                    <option value={2}>Q2</option>
                    <option value={3}>Q3</option>
                    <option value={4}>Q4</option>
                </select>
            </div>

            <button onClick={downloadPDF}>
                Download PDF
            </button>
            <button onClick={downloadCSV}>
  Download CSV
</button>

            
            {/* Report Display */}
            {report ? (
                <div id="report-content">
                    <p><strong>Total Expenses:</strong> ₹{report.totalExpenses}</p>
                    <p><strong>Total Paid:</strong> ₹{report.totalPaid}</p>
                    <p><strong>Pending:</strong> ₹{report.pending}</p>

                    <h3>Payments</h3>
                    <table border="1" cellPadding="8">
                        <thead>
                            <tr>
                                <th>Flat</th>
                                <th>Status</th>
                                <th>Amount</th>
                            </tr>
                        </thead>
                        <tbody>
                            {report.payments.map((p, index) => (
                                <tr key={index}>
                                    <td>{p.flat?.name}</td>
                                    <td>{p.status}</td>
                                    <td>₹{p.amount}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            ) : (
                <p>Loading report...</p>
            )}
        </div>
    );
};

export default Reports;