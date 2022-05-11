import "./UserModal.css";

import React from "react";

const UserModal = (props) => {
  var ime = "Uros";
  if (!props.show) {
    return null;
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
          <form action="">
            <label htmlFor="fname">First Name</label>
            <input type="text" id="fname" name="firstname" value={ime} />

            <label htmlFor="lname">Last Name</label>
            <input type="text" id="lname" name="lastname" placeholder="" />

            <input type="submit" value="Submit" />
          </form>
        </div>
        {/* <div className="modal-footer"></div> */}
      </div>
    </div>
  );
};

export default UserModal;
