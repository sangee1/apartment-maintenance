import React, { useState } from "react";
import { TextField, Button, Container, Typography, Box } from "@mui/material";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

const Login = ({ setIsLoggedIn }) => {
  const [form, setForm] = useState({ username: "", password: "" });
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await API.post("/auth/login", form);
      const token = res.data.token;
      const decoded = jwtDecode(token);
      localStorage.setItem("token", token);
      localStorage.setItem("role", decoded.role);

      setIsLoggedIn(true);   // 🔥 triggers UI update

      navigate("/");

    } catch (err) {
      alert("Invalid credentials");
    }
  };

  return (
    <Container maxWidth="sm">
      <Box sx={{ mt: 10 }}>
        <Typography variant="h5">Login</Typography>

        <TextField
          label="Username"
          fullWidth
          sx={{ mt: 2 }}
          onChange={(e) => setForm({ ...form, username: e.target.value })}
        />

        <TextField
          label="Password"
          type="password"
          fullWidth
          sx={{ mt: 2 }}
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />

        <Button
          variant="contained"
          fullWidth
          sx={{ mt: 3 }}
          onClick={handleLogin}
        >
          Login
        </Button>
      </Box>
    </Container>
  );
};

export default Login;