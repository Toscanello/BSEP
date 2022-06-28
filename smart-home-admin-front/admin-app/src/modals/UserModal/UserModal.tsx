import "./UserModal.css";

import React from "react";
import axios from "axios";
import { validName, validPassword, validUsername } from "../../reg/Regex";
import useToken from "../../components/useToken";

const UserModal = (props) => {
  var [name, setName] = React.useState("");
  var [surname, setSurname] = React.useState("");
  var [usernmae, setUsername] = React.useState("");
  var [password, setPassword] = React.useState("");
  var [role, setRole] = React.useState("ROLE_ADMIN");
  const { token } = useToken();

  if (!props.show) {
    return null;
  }

  function handleSubmit(event) {
    if (
      validName.test(name) &&
      validName.test(surname) &&
      validUsername.test(usernmae) &&
      validPassword.test(password)
    ) {
      axios
        .post(
          `https://localhost:3000/users/addNewUser`,
          {
            name: name,
            surname: surname,
            username: usernmae,
            password: password,
            role: role,
          },
          {
            headers: {
              Authorization: "Bearer " + token,
            },
          }
        )
        .then((res) => alert("added User"));
    } else alert("Wrong input");
    event.preventDefault();
  }

  return (
    <div className="modal" onClick={props.onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h4 className="modal-title">Create user</h4>
          <button className="button" onClick={props.onClose}>
            Close
          </button>
        </div>
        <div className="modal-body">
          <form action="" onSubmit={handleSubmit}>
            <label htmlFor="fname">First Name</label>
            <input
              type="text"
              id="fname"
              name="firstname"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />

            <label htmlFor="lname">Last Name</label>
            <input
              type="text"
              id="lname"
              name="lastname"
              value={surname}
              onChange={(e) => setSurname(e.target.value)}
            />

            <label htmlFor="uname">Username</label>
            <input
              type="text"
              id="uname"
              name="username"
              value={usernmae}
              onChange={(e) => setUsername(e.target.value)}
            />

            <label htmlFor="pass">Password</label>
            <input
              type="text"
              id="pass"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />

            <label htmlFor="role">Role</label>
            <select
              id="role"
              name="role"
              value={role}
              onChange={(e) => setRole(e.currentTarget.value)}
            >
              <option value="ROLE_ADMIN">ROLE_ADMIN</option>
              <option value="ROLE_USER">ROLE_USER</option>
            </select>

            <input type="submit" value="Submit" />
          </form>
        </div>
        {/* <div className="modal-footer"></div> */}
      </div>
    </div>
  );
};

export default UserModal;
