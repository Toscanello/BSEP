import React, { useState } from "react";
import "./App.css";

import { BrowserRouter, Route, Switch } from "react-router-dom";

import HomePage from "./pages/Home/HomePage";
import CsrPage from "./pages/Csr/CsrPage";
import CreateCertificatePage from "./pages/CreateCertificatePage/CreateCertificatePage";
import ViewCertificatesPage from "./pages/ViewCertificatesPage/ViewCertificatesPage";
import UsersPage from "./pages/UsersPage/UsersPage";
import LoginPage from "./pages/LoginPage/LoginPage";
import useToken from './components/useToken';

function App() {
  const { token, setToken } = useToken();

  if(!token) {
    return <LoginPage setToken={setToken} />
  }

  const logout = () => {
    localStorage.removeItem('token');
  }

  return (
    
    <BrowserRouter>
      <button className="btn button" onClick={logout}>
        Logout
      </button>
      <Switch>
        <Route exact path="/" component={LoginPage} />
        <Route path="/home" component={HomePage} />
        <Route path="/csr" component={CsrPage} />
        <Route path="/create-certificate" component={CreateCertificatePage} />
        <Route path="/certificates" component={ViewCertificatesPage} />
        <Route path="/users" component={UsersPage} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;
