import API from "./api";

export const getDashboardSummary = (year) =>
  API.get(`/dashboard/summary?year=${year}`);