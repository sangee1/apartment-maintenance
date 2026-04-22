import React from "react";
import { Grid, Card, CardContent, Typography } from "@mui/material";

const SummaryCards = ({ summary }) => {
  if (!summary) return null;

  const cards = [
    { title: "Total Expenses", value: summary.totalExpenses, color: "#1976d2" },
    { title: "Collected", value: summary.totalCollected, color: "#2e7d32" },
    { title: "Balance", value: summary.balance, color: "#ed6c02" },
    { title: "Pending", value: summary.pending, color: "#d32f2f" },
  ];

  return (
    <Grid container spacing={3} sx={{ mb: 3 }}>
      {cards.map((card, index) => (
        <Grid item xs={12} sm={6} md={3} key={index}>
          <Card sx={{ borderLeft: `6px solid ${card.color}` }}>
            <CardContent>
              <Typography variant="subtitle2" color="text.secondary">
                {card.title}
              </Typography>
              <Typography variant="h5" fontWeight="bold">
                ₹ {card.value}
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
};

export default SummaryCards;