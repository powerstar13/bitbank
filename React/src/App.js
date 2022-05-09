import React from 'react'
import { ThemeProvider } from "@mui/styles";
import { createTheme } from '@mui/material/styles'
import { Navigate, BrowserRouter as Router, Routes, Link } from "react-router-dom";
import Section from './components/Section'
import MenuHeader from './components/header/MenuHeader';
import './App.css';

const theme = createTheme({
  breakpoints: {
    values: {
      xs: 0,
      mobile: 400,
      sm: 600,
      md: 800,
      lg: 1400,
      xl: 2560,
    },
  },
  typography: {
    fontFamily: `"Noto Sans KR", "Helvetica", "Arial", sans-serif`,
    fontSize: 12,
  },
  palette: {
    root: {
      main: '#FFFFFF' 
    },
  },
  overrides: {
    MuiButton: {
        root: {
          fontSize: 'inherit',
          backgroundColor: 'inherit',
        },
    },
  },
  components: {
    MuiCssBaseline: {
      styleOverrides: {
        '*, *::before, *::after': {
          transition: 'none !important',
        },
      },
    },
    MuiButtonBase: {
      defaultProps: {
        disableRipple: true, 
      },
      color: "black"
    },
  },
});


function App() {
  return (
   <>
    <ThemeProvider theme={theme}>
      <Router>
        <div></div>
        <MenuHeader />   
        <Section />
        {/* <MenuFooter /> */}
      </Router>
    </ThemeProvider>
   </>
  );
}

export default App;
