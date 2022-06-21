import "./ViewCertificatesPage.css";

import { Link } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import { varToken } from "../../reg/Regex";

const ViewCertificatesPage = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:3000/certificates/findAll`,{
      headers: {
        Authorization: "Bearer " + varToken,
      }
    }).then((res) => {
      setData(res.data);
    });
  });

  function handleClickValidate(email){
    axios.get(`http://localhost:3000/certificates/validate/${email}`,{
      headers: {
        Authorization: "Bearer " + varToken,
      }
    }).then((res)=>{
      alert(res.data)
    })
  }
  function handleClickRevoke(serialNumber){
    axios.put(`http://localhost:3000/certificates/revoke/${serialNumber}`,{
      headers: {
        Authorization: "Bearer " + varToken,
      }
    }).then((res)=>{
      alert('revoked')
    })
  }

  const displayData = data.map((info, key) => {
  
    return (
      <tr key={key}>
        <td></td>
        <td>{info.subjectDN.commonName}</td>
        <td>{info.issuerDN.organization}</td>
        <td>{info.issuerDN.organizationalUnit}</td>
        <td>{info.subjectDN.locality}</td>
        <td>{info.subjectDN.state}</td>
        <td>{info.subjectDN.country}</td>
        <td>{info.subjectDN.name.split(',')[0].substring(13)}</td>
        <td>{info.serialNumber}</td>
        <td>{info.notBefore.split('T')[0]}</td>
        <td>{info.notAfter.split('T')[0]}</td>
        <td>
          <button className="btn" onClick={()=>handleClickValidate(info.subjectDN.name.split(',')[0].substring(13))}>Validate</button>
        </td>
        <td>
          <button className="btn" onClick={()=>handleClickRevoke(info.serialNumber)}>Revoke</button>
        </td>
      </tr>
    );
  });

  return (
    <div>
      <Link to="/home" className="btn btn-back">
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
