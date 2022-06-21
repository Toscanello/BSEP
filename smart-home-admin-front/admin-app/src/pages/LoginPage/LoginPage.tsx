import "./LoginPage.css";

import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";

const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();

    const postBody = {
      username: username,
      password: password,
    };

    console.log(postBody);

    axios.post(`http://localhost:3000/auth/login`, postBody)
    .then(response=>console.log(response.data))
    ;
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

export default LoginPage;
