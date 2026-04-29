import React from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { Button } from "@mui/material";

import {
  Box, Drawer, List, ListItemButton,
  ListItemIcon, ListItemText, AppBar,
  Toolbar, Typography
} from "@mui/material";

import DashboardIcon from "@mui/icons-material/Dashboard";
import ReceiptIcon from "@mui/icons-material/Receipt";

const drawerWidth = 220;

const Layout = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  
  navigate("/login");
};

  const menuItems = [
    { text: "Dashboard", icon: <DashboardIcon />, path: "/" },
    { text: "Expenses", icon: <ReceiptIcon />, path: "/dashboard/expenses" },
  ];

  return (
    <Box sx={{ display: "flex" }}>

      {/* 🔹 Top App Bar */}
      <AppBar position="fixed" sx={{ zIndex: 1201 }}>
  <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
    
    <Typography variant="h6">
      Apartment Maintenance
    </Typography>

    <Button
      color="inherit"
      variant="outlined"
      onClick={handleLogout}
    >
      Logout
    </Button>

  </Toolbar>
</AppBar>

      {/* 🔹 Sidebar */}
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          "& .MuiDrawer-paper": {
            width: drawerWidth,
            boxSizing: "border-box",
          },
        }}
      >
        <Toolbar /> {/* spacing below appbar */}

        <List>
          {menuItems.map((item) => (
            <ListItemButton
              key={item.text}
              onClick={() => navigate(item.path)}
            >
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItemButton>
          ))}
        </List>
      </Drawer>

      {/* 🔹 Main Content */}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          marginLeft: `${drawerWidth}px`,
        }}
      >
        <Toolbar /> {/* spacing below appbar */}

        <Outlet />
      </Box>

    </Box>
  );
};

export default Layout;