import "./UsersPage.css";

import UserModal from "../../modals/UserModal/UserModal";

import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { validSearch } from "../../reg/Regex";
import createDOMPurify from "dompurify";
import { varToken } from "../../reg/Regex";

const UsersPage = () => {

  const DOMPurify=createDOMPurify(window)

  const [show, setShow] = useState(false);

  const [data, setData] = useState([]);

  var [search,setSearch]= useState("");

  useEffect(() => {
    axios.get(`http://localhost:3000/users/getAll`,{
      headers: {
        Authorization: 'Bearer ' + varToken
      }
    }).then((res) => {
      setData(res.data);
    });
  },[]);

  function handleClickEdit(info) {
    axios.put(`http://localhost:3000/users/changeRole/${1}`,info,{
      headers: {
        Authorization: 'Bearer ' + varToken
      }
    }).then((res) => {
      alert("edited user");
    });
  }

  function handleClickDelete(username) {
    axios
      .delete(`http://localhost:3000/users/delete/${username}`,{
        headers: {
          Authorization: 'Bearer ' + varToken
        }
      })
      .then((res) => {
        alert("deleted");
      });
  }
  function handleCliclkSearch() {
    if(search === ""){
      axios.get(`http://localhost:3000/users/getAll`,{
        headers: {
          Authorization: 'Bearer ' + varToken
        }
      }).then((res) => {
        setData(res.data);
      });
    }
    else{
      if(validSearch.test(search)){
        axios
          .get(`http://localhost:3000/users/search/${search}`,{
            headers: {
              Authorization: 'Bearer ' + varToken
            }
          })
          .then((res) => {
            setData(res.data);
          });
      }
      else
        alert("invalid search")
    }
  }

  //   const displayData = data.map((info, key) => {
  //     return (
  //       <tr key={key}>
  //         <td></td>
  //         <td onClick={() => handleClickEdit(info.email)}>Edit</td>
  //         <td onClick={() => handleClickDelete(info.email)}>Delete</td>
  //       </tr>
  //     );
  //   });

  const displayUsers = data.map((info,key)=>{

    return (
      <tr key = {key}>
        <td>{info.name}</td>
        <td>{info.surname}</td>
        <td>{info.username}</td>
        <td>{info.role}</td>
        <td>
          <button className="btn"onClick={()=>handleClickEdit(info)}>Edit</button>
        </td>
        <td>
          <button className="btn" onClick={()=>handleClickDelete(info.username)}>Delete</button>
        </td>
      </tr>
    );
  });


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
            <input type="text" placeholder="Username..." value={search} onChange={e=>setSearch(e.target.value)}/>
            <button className="btn-src" onClick={()=>handleCliclkSearch()}>Search</button>
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
              {displayUsers}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default UsersPage;
