import axios from "axios";


const instanceNoAuth = axios.create({
  baseURL: `${process.env.REACT_APP_IDENTITY_WEB}api/`,
});

export { instanceNoAuth };
