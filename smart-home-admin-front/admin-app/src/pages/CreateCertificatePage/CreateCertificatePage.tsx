import "./CreateCertificatePage.css";

import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";

const CreateCertificatePage = () => {
  const [serialNumber, setSerialNumber] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [commonName, setCommonName] = useState("");
  const [organizationName, setOrganizationName] = useState("");
  const [organizationUnit, setOrganizationUnit] = useState("");
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [country, setCountry] = useState("");
  const [email, setEmail] = useState("");

  const [csrId, setCsrId] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();

    const postBody = {
      serialNumber: serialNumber,
      startDate: startDate,
      endDate: endDate,
      commonName: commonName,
      organizationName: organizationName,
      organizationUnit: organizationUnit,
      city: city,
      state: state,
      country: country,
      email: email,
    };

    axios.post(`http://localhost:3000/certificates/create/${csrId}`, postBody);
  };

  return (
    <div>
      <Link to="/" className="btn btn-back">
        Home
      </Link>
      <h1 className="title">Create Certificate</h1>
      <div className="container">
        <form onSubmit={handleSubmit}>
          <label>CSR ID</label>
          <input type="text" onChange={(e) => setCsrId(e.target.value)} />

          <label>Serial Number</label>
          <input
            type="text"
            onChange={(e) => setSerialNumber(e.target.value)}
          />

          <label>Start Date</label>
          <input type="text" onChange={(e) => setStartDate(e.target.value)} />

          <label>End Date</label>
          <input type="text" onChange={(e) => setEndDate(e.target.value)} />

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

export default CreateCertificatePage;
