import React, { useCallback,useEffect, useState } from "react";

import PaymentGrid from "../components/PaymentGrid";
import SummaryCards from "../components/SummaryCards";
import { getDashboardSummary } from "../services/dashboardService";

import { Container, Typography, TextField, Box } from "@mui/material";

const Dashboard = () => {
  const [year, setYear] = useState(new Date().getFullYear());
  const [summary, setSummary] = useState(null);

  const loadSummary = useCallback(async () => {
    const res = await getDashboardSummary(year);
    setSummary(res.data);
  }, [year]);

  useEffect(() => {
    loadSummary();
  }, [loadSummary]);

  return (
    <Container maxWidth="lg">
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" gutterBottom>
          Apartment Maintenance Dashboard
        </Typography>
        
        <TextField
          label="Year"
          type="number"
          value={year}
          onChange={(e) => setYear(e.target.value)}
          sx={{ mb: 3 }}
        />

        <SummaryCards summary={summary} />

        <PaymentGrid year={year} onUpdate={loadSummary} />
      </Box>
    </Container>
  );
};

export default Dashboard;