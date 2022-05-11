import "./UsersPage.css";

import UserModal from "../../modals/UserModal/UserModal";

import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const UsersPage = () => {
  const [show, setShow] = useState(false);

  //   const [data, setData] = useState([]);

  //   useEffect(() => {
  //     axios.get(`http://localhost:3000/users/findAll`).then((res) => {
  //       setData(res.data);
  //     });
  //   });

  //   function handleClickEdit(email) {
  //     axios.get(`http://localhost:3000/users/edit/${email}`).then((res) => {
  //       alert(res.data);
  //     });
  //   }

  //   function handleClickDelete(email) {
  //     axios
  //       .put(`http://localhost:3000/certificates/revoke/${email}`)
  //       .then((res) => {
  //         alert("revoked");
  //       });
  //   }

  //   const displayData = data.map((info, key) => {
  //     return (
  //       <tr key={key}>
  //         <td></td>
  //         <td onClick={() => handleClickEdit(info.email)}>Edit</td>
  //         <td onClick={() => handleClickDelete(info.email)}>Delete</td>
  //       </tr>
  //     );
  //   });

  return (
    <div>
      <UserModal onClose={() => setShow(false)} show={show} />
      <Link to="/" className="btn btn-back">
        Home
      </Link>
      <div className="container-users">
        <h1>Users</h1>
        <div className="users-search">
          <div className="search">
            <input type="text" placeholder="Username..." />
            <button className="btn-src">Search</button>
          </div>
          <button className="btn" onClick={() => setShow(true)}>
            Create user
          </button>
        </div>
        <div className="container-table">
          <table className="users-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Surname</th>
                <th>Username</th>
                <th>Role</th>
                <th></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Uros</td>
                <td>Petric</td>
                <td>urkep</td>
                <td>Admin</td>
                <td align="center">
                  <button>Edit</button>
                </td>
                <td align="center">
                  <button>Delete</button>
                </td>
              </tr>
              <tr>
                <td>Uros</td>
                <td>Petric</td>
                <td>urkep</td>
                <td>Admin</td>
                <td align="center">
                  <button>Edit</button>
                </td>
                <td align="center">
                  <button>Delete</button>
                </td>
              </tr>
              <tr>
                <td>Uros</td>
                <td>Petric</td>
                <td>urkep</td>
                <td>Admin</td>
                <td align="center">
                  <button>Edit</button>
                </td>
                <td align="center">
                  <button>Delete</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default UsersPage;
