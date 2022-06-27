import "./LoginPage.css";

import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";

const LoginPage = ({ setToken }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const loginUser = (credentials) => {
    return axios
      .post(`http://localhost:3000/auth/login`, credentials)
      .then((res) => res.data.accessToken)
      .catch((err) => alert("Wrong login or username"));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = await loginUser({
      username,
      password,
    });
    setToken(token);
  };

  return (
    <div>
      <div className="container-login">
        <form onSubmit={handleSubmit}>
          <label htmlFor="username">Username</label>
          <input type="text" onChange={(e) => setUsername(e.target.value)} />
          <label htmlFor="password">Password</label>
          <input
            type="password"
            onChange={(e) => setPassword(e.target.value)}
          />

          <input type="submit" />
        </form>
      </div>
    </div>
  );
};

LoginPage.propTypes = {
  setToken: PropTypes.func.isRequired,
};

export default LoginPage;
