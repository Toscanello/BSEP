import React from "react";
import "./App.css";

import { BrowserRouter, Route, Switch } from "react-router-dom";

import HomePage from "./pages/Home/HomePage";
import CsrPage from "./pages/Csr/CsrPage";
import CreateCertificatePage from "./pages/CreateCertificatePage/CreateCertificatePage";
import ViewCertificatesPage from "./pages/ViewCertificatesPage/ViewCertificatesPage";
import UsersPage from "./pages/UsersPage/UsersPage";

function App() {
  return (
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={HomePage} />
        <Route path="/csr" component={CsrPage} />
        <Route path="/create-certificate" component={CreateCertificatePage} />
        <Route path="/certificates" component={ViewCertificatesPage} />
        <Route path="/users" component={UsersPage} />
      </Switch>
    </BrowserRouter>
  );
}

export default App;
