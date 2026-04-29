import React, { useEffect, useState } from "react";
import API from "../services/api";// adjust if your path differs

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

      {/* Report Display */}
      {report ? (
        <div>
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