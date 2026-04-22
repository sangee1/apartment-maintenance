import API from "./api";

export const getPaymentGrid = (year) =>
  API.get(`/payments/grid?year=${year}`);

export const togglePayment = (id) =>
  API.put(`/payments/${id}/toggle`);