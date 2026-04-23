import React, { useEffect, useState } from "react";
import { getPaymentGrid, togglePayment } from "../services/paymentService";
import {
  Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, Chip
} from "@mui/material";

const quarters = ["Q1", "Q2", "Q3", "Q4"];
const role = localStorage.getItem("role");
const isAdmin = role === "ADMIN";

const PaymentGrid = ({ year, onUpdate }) => {
  const [data, setData] = useState([]);

  const loadData = async () => {
    const res = await getPaymentGrid(year);
    setData(res.data);
  };

  useEffect(() => {
    loadData();
  }, [year]);

  const handleToggle = async (id) => {
    await togglePayment(id);
    loadData();
    onUpdate && onUpdate();
  };

  return (
    <TableContainer component={Paper} elevation={3}>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell sx={{ py: 0.5, px: 1 }}><b>Flat</b></TableCell>
            {quarters.map((q) => (
              <TableCell key={q} align="center"><b>{q}</b></TableCell>
            ))}
          </TableRow>
        </TableHead>

        <TableBody>
          {data.map((flat) => (
            <TableRow key={flat.flatName}>
              <TableCell>{flat.flatName}</TableCell>

              {quarters.map((q) => {
                const payment = flat.payments?.find(
                  (p) => p.quarter === q
                );

                if (!payment) return <TableCell key={q}>-</TableCell>;

                return (
                  <TableCell key={q} align="center">
                    <Chip
                      label={payment.status}
                      color={payment.status === "PAID" ? "success" : "error"}
                      size="small"
                      onClick={isAdmin ? () => handleToggle(payment.id) : undefined}
                      sx={{ cursor: "pointer" }}
                    />
                  </TableCell>
                );
              })}
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default PaymentGrid;