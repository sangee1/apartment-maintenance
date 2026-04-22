
import React, { useState } from "react";
import Dashboard from "./pages/Dashboard";
import Expenses from "./pages/Expenses";
import Layout from "./components/Layout";
import Login from "./pages/Login";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import { BrowserRouter, Routes, Route } from "react-router-dom";

const theme = createTheme({
  palette: {
    primary: { main: "#1976d2" },
    secondary: { main: "#2e7d32" },
  },
});

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(
    !!localStorage.getItem("token")
  );

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      {/* 👉 Router goes INSIDE ThemeProvider */}
      <BrowserRouter>
      <Routes>

        <Route
          path="/"
          element={isLoggedIn ? <Layout /> : <Login setIsLoggedIn={setIsLoggedIn} />}
        >
          <Route index element={<Dashboard />} />
          <Route path="expenses" element={<Expenses />} />
        </Route>

        <Route path="/login" element={<Login setIsLoggedIn={setIsLoggedIn} />} />

      </Routes>
    </BrowserRouter>
      
    </ThemeProvider>
    
  );
}

export default App;
