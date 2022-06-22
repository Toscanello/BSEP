import "./ViewCertificatesPage.css";

import { Link } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import useToken from '../../components/useToken';

const ViewCertificatesPage = () => {
  const [data, setData] = useState([]);
  const {token} = useToken();

  useEffect(() => {
    axios.get(`http://localhost:3000/certificates/findAll`,{
      headers: {
        Authorization: "Bearer " + token,
      }
    }).then((res) => {
      setData(res.data);
    });
  },[]);

  function handleClickValidate(email){
    axios.get(`http://localhost:3000/certificates/validate/${email}`,{
      headers: {
        Authorization: "Bearer " + token,
      }
    }).then((res)=>{
      alert(res.data)
    })
  }
  function handleClickRevoke(serialNumber){
    axios.put(`http://localhost:3000/certificates/revoke/${serialNumber}`,{},{
      headers: {
        Authorization: "Bearer " + token,
      }
    }).then((res)=>{
      alert('revoked')
    })
  }

  const displayData = data.map((info, key) => {
  
    return (
      <tr key={key}>
        <td></td>
        <td>{info.commonName}</td>
        <td>{info.organization}</td>
        <td>{info.organizationUnit}</td>
        <td>{info.locality}</td>
        <td>{info.state}</td>
        <td>{info.country}</td>
        <td>{info.name}</td>
        <td>{info.serialNumber}</td>
        <td>{info.notBefore.split('T')[0]}</td>
        <td>{info.notAfter.split('T')[0]}</td>
        <td>
          <button className="btn" onClick={()=>handleClickValidate(info.name)}>Validate</button>
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
