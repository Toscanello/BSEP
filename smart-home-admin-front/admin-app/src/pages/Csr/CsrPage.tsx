import "./CsrPage.css";

import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";
import useToken from "../../components/useToken";

const CsrPage = () => {
  const [commonName, setCommonName] = useState("");
  const [organizationName, setOrganizationName] = useState("");
  const [organizationUnit, setOrganizationUnit] = useState("");
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [country, setCountry] = useState("");
  const [email, setEmail] = useState("");
  const { token } = useToken();

  const handleSubmit = (event) => {
    event.preventDefault();

    const postBody = {
      commonName: commonName,
      organizationName: organizationName,
      organizationUnit: organizationUnit,
      city: city,
      state: state,
      country: country,
      email: email,
    };

    console.log(postBody);
    console.log(token);
    axios.post(`https://localhost:3000/csr`, postBody, {
      headers: {
        Authorization: "Bearer " + token,
      },
    });
  };

  return (
    <div>
      <Link to="/home" className="btn btn-back">
        Home
      </Link>
      <h1 className="title">Create Certificate Request</h1>
      <div className="container">
        <form onSubmit={handleSubmit}>
          <label>Common Name</label>
          <input type="text" onChange={(e) => setCommonName(e.target.value)} />

          <label>Organization Name</label>
          <input
            type="text"
            onChange={(e) => setOrganizationName(e.target.value)}
          />

          <label>Organization Unit</label>
          <input
            type="text"
            onChange={(e) => setOrganizationUnit(e.target.value)}
          />

          <label>City</label>
          <input type="text" onChange={(e) => setCity(e.target.value)} />

          <label>State</label>
          <input type="text" onChange={(e) => setState(e.target.value)} />

          <label>Country</label>
          <input type="text" onChange={(e) => setCountry(e.target.value)} />

          <label>Email</label>
          <input type="text" onChange={(e) => setEmail(e.target.value)} />

          <input type="submit" value="Submit" className="btn" />
        </form>
      </div>
    </div>
  );
};

export default CsrPage;
