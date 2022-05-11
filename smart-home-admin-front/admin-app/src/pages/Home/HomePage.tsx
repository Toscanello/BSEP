import "./HomePage.css";

import React from "react";
import { Link } from "react-router-dom";

const HomePage = () => {
  return (
    <div>
      <h1 className="title">Best admin app</h1>
      <div className="container-buttons">
        <Link to="/csr" className="btn">
          Create CSR
        </Link>
        <Link to="/create-certificate" className="btn">
          Create Certificate
        </Link>
        <Link to="/certificates" className="btn">
          View Certificates
        </Link>
        <Link to="/users" className="btn">
          Users
        </Link>
      </div>
    </div>
  );
};

export default HomePage;
