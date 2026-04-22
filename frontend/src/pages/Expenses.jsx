import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { addExpense, getExpenses, updateExpense, deleteExpense } from "../services/expenseService";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import {
    IconButton, Dialog, DialogTitle,
    DialogContent, DialogActions
} from "@mui/material";

import {
    Container, Typography, Box, TextField,
    Button, MenuItem, Grid, Paper,
    Table, TableBody, TableCell,
    TableContainer, TableHead, TableRow
} from "@mui/material";

const quarters = ["Q1", "Q2", "Q3", "Q4"];

const Expenses = () => {
    const currentYear = new Date().getFullYear();

    const [form, setForm] = useState({
        description: "",
        amount: "",
        quarter: "Q1",
        year: currentYear,
    });

    const [filterYear, setFilterYear] = useState(currentYear);
    const [filterQuarter, setFilterQuarter] = useState("Q1");

    const [expenses, setExpenses] = useState([]);
    const [editOpen, setEditOpen] = useState(false);
    const [selectedExpense, setSelectedExpense] = useState(null);
    const role = localStorage.getItem("role");
    const isAdmin = role === "ADMIN";

    const loadExpenses = async () => {
        const res = await getExpenses(filterYear, filterQuarter);
        setExpenses(res.data);
    };

    useEffect(() => {
        loadExpenses();
    }, [filterYear, filterQuarter]);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleEdit = (expense) => {
        setSelectedExpense(expense);
        setEditOpen(true);
    };

    const handleDelete = async (id) => {
        await deleteExpense(id);
        loadExpenses();
    };

    const handleSubmit = async () => {
        if (!form.description || !form.amount) return;

        await addExpense(form);

        setForm({
            description: "",
            amount: "",
            quarter: "Q1",
            year: currentYear,
        });

        loadExpenses();
    };

    return (
        <Container maxWidth="lg">
            <Box sx={{ my: 4 }}>
                <Typography variant="h4" gutterBottom>
                    Expenses
                </Typography>

                {/* 🔹 Add Expense Form */}
                <Paper sx={{ p: 3, mb: 3 }}>
                    <Typography variant="h6" gutterBottom>
                        Add Expense
                    </Typography>

                    <Grid container spacing={2}>
                        <Grid item xs={12} md={4}>
                            <TextField
                                label="Description"
                                name="description"
                                value={form.description}
                                onChange={handleChange}
                                fullWidth
                            />
                        </Grid>

                        <Grid item xs={12} md={2}>
                            <TextField
                                label="Amount"
                                name="amount"
                                type="number"
                                value={form.amount}
                                onChange={handleChange}
                                fullWidth
                            />
                        </Grid>

                        <Grid item xs={12} md={2}>
                            <TextField
                                label="Date"
                                type="date"
                                fullWidth
                                margin="dense"
                                InputLabelProps={{ shrink: true }}
                                value={form.date || ""}
                                onChange={(e) =>
                                    setForm({ ...form, date: e.target.value })
                                }
                            />
                        </Grid>

                        <Grid item xs={12} md={2}>
                            <TextField
                                label="Year"
                                name="year"
                                type="number"
                                value={form.year}
                                onChange={handleChange}
                                fullWidth
                            />
                        </Grid>



                        <Grid item xs={12} md={2}>
                            {role === "ADMIN" && (
                                <Button variant="contained" onClick={handleSubmit}>
                                    Add Expense
                                </Button>
                            )}
                        </Grid>
                    </Grid>
                </Paper>

                {/* 🔹 Filters */}
                <Box sx={{ display: "flex", gap: 2, mb: 2 }}>
                    <TextField
                        label="Year"
                        type="number"
                        value={filterYear}
                        onChange={(e) => setFilterYear(e.target.value)}
                    />

                    <TextField
                        select
                        label="Quarter"
                        value={filterQuarter}
                        onChange={(e) => setFilterQuarter(e.target.value)}
                    >
                        {quarters.map((q) => (
                            <MenuItem key={q} value={q}>
                                {q}
                            </MenuItem>
                        ))}
                    </TextField>
                </Box>

                {/* 🔹 Expense Table */}
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><b>Description</b></TableCell>
                                <TableCell><b>Amount</b></TableCell>
                                <TableCell><b>Quarter</b></TableCell>
                                <TableCell><b>Date</b></TableCell>
                                <TableCell><b>Actions</b></TableCell>
                            </TableRow>
                        </TableHead>

                        <TableBody>
                            {expenses.map((exp) => (
                                <TableRow key={exp.id}>
                                    <TableCell>{exp.description}</TableCell>
                                    <TableCell>₹ {exp.amount}</TableCell>
                                    <TableCell>{exp.quarter}</TableCell>
                                    <TableCell>{exp.date}</TableCell>
                                    <TableCell>
                                        {isAdmin && (
                                            <>
                                                <IconButton onClick={() => handleEdit(exp)}>
                                                    <EditIcon />
                                                </IconButton>

                                                <IconButton onClick={() => handleDelete(exp.id)}>
                                                    <DeleteIcon color="error" />
                                                </IconButton>
                                            </>
                                        )}
                                    </TableCell>
                                </TableRow>
                            ))}

                            {expenses.length === 0 && (
                                <TableRow>
                                    <TableCell colSpan={4} align="center">
                                        No expenses found
                                    </TableCell>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>
                <Dialog open={editOpen} onClose={() => setEditOpen(false)}>
                    <DialogTitle>Edit Expense</DialogTitle>

                    <DialogContent>
                        <TextField
                            label="Description"
                            fullWidth
                            margin="dense"
                            value={selectedExpense?.description || ""}
                            onChange={(e) =>
                                setSelectedExpense({
                                    ...selectedExpense,
                                    description: e.target.value,
                                })
                            }
                        />

                        <TextField
                            label="Amount"
                            type="number"
                            fullWidth
                            margin="dense"
                            value={selectedExpense?.amount || ""}
                            onChange={(e) =>
                                setSelectedExpense({
                                    ...selectedExpense,
                                    amount: e.target.value,
                                })
                            }
                        />
                    </DialogContent>

                    <DialogActions>
                        <Button onClick={() => setEditOpen(false)}>Cancel</Button>

                        <Button
                            variant="contained"
                            onClick={async () => {
                                await updateExpense(selectedExpense.id, selectedExpense);
                                setEditOpen(false);
                                loadExpenses();
                            }}
                        >
                            Save
                        </Button>
                    </DialogActions>
                </Dialog>
            </Box>
        </Container>
    );
};

export default Expenses;