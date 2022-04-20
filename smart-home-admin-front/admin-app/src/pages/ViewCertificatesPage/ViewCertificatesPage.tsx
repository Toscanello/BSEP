import "./ViewCertificatesPage.css";

import { Link } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";

const ViewCertificatesPage = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:3000/certificates/findAll`).then((res) => {
      setData(res.data);
    });
  });

  const displayData = data.map((info, key) => {
    return (
      <tr key={key}>
        <td></td>
        <td>{info.issuerDN.commonName}</td>
        <td>{info.issuerDN.organization}</td>
        <td>{info.issuerDN.organizationUnit}</td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>
          <button className="btn">Validate</button>
        </td>
        <td>
          <button className="btn">Revoke</button>
        </td>
      </tr>
    );
  });

  return (
    <div>
      <Link to="/" className="btn btn-back">
        Home
      </Link>
      <h1 className="title">Certificates</h1>
      <div className="container container-table">
        <table className="certificates-table">
          <thead>
            <tr>
              <th></th>
              <th>Common Name</th>
              <th>Organization Name</th>
              <th>Organization Unit</th>
              <th>City</th>
              <th>State</th>
              <th>Country</th>
              <th>Email</th>
              <th>Serial Number</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th></th>
              <th></th>
            </tr>
          </thead>
          <tbody>{displayData}</tbody>
        </table>
      </div>
    </div>
  );
};

export default ViewCertificatesPage;
