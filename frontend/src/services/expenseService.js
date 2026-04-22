import API from "./api";

export const addExpense = (data) =>
  API.post("/expenses", data);

export const getExpenses = (year, quarter) =>
  API.get(`/expenses?year=${year}&quarter=${quarter}`);

export const updateExpense = (id, data) =>
  API.put(`/expenses/${id}`, data);

export const deleteExpense = (id) =>
  API.delete(`/expenses/${id}`);