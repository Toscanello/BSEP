import { useState } from 'react';
import { Redirect } from "react-router";

export default function useToken() {

  const getToken = () => {
    const tokenString = localStorage.getItem('token');
    if(tokenString !== "undefined") {
        const userToken = JSON.parse(tokenString);
        return userToken;
    }
  };

  const [token, setToken] = useState(getToken());

  const saveToken = userToken => {
    localStorage.setItem('token', JSON.stringify(userToken));
    setToken(userToken.token);
  };

  return {
    setToken: saveToken,
    token
  }
}