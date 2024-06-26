import axios from "axios";
import Cookies from "js-cookie";
import { dispatch } from "../app/store";
import {
  SetCountCong, SetCountTru,
} from "../app/reducer/Loading.reducer";

export const request = axios.create();
request.interceptors.request.use((config) => {
  dispatch(SetCountCong());
  const token = Cookies.get("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  } else {
    window.location.href = "/not-authorization";
  }
  // if(!config.url.includes("/roles?idUser=")) {
  //   dispatch(SetLoadingTrue());
  // }
  return config;
});

request.interceptors.response.use(
  (response) => {
    dispatch(SetCountTru())
    return response;
  },
  (error) => {
    dispatch(SetCountTru())
    if (error.response && error.response.status === 401) {
      Cookies.remove("token");
      Cookies.remove("userCurrent");
      window.location.href = "/not-authorization";
    } else if (error.response && error.response.status === 403) {
      // console.log("error, 403", error);
      // window.location.href = "/forbidden";
    } else if (error.response && error.response.status === 406) {
      Cookies.remove("token");
      Cookies.remove("userCurrent");
      window.location.href = "/not-aceptable/status=" + error.response.data;
    } else if (error.response && error.response.status === 500) {
      //console.log(error);
      // message.error('Vui lòng liên hệ nhà phát triển');
      // window.location.href = "/unknown-error";
    }
    // if (error.response != null && error.response.status === 400) {
    //     message.warning(error.response.data.message);
    // }
    throw error;
  }
);
