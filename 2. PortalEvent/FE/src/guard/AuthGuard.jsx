import { useEffect } from "react";
import Cookies from "js-cookie";
import {portIdentity} from "../pages/ApiUrl";
import jwt_decode from "jwt-decode";
import { RolesAPI } from "../helper/RolesAPI";

const AuthGuard = ({ children }) => {
  // useEffect(() => {
  //   const token = Cookies.get("token");
  //   window.scrollTo(0, 0);
  //   if (token == null) {
  //     window.location.href = portIdentity;
  //     return null;
  //   } else {
  //     const decodedToken = jwt_decode(token);
  //
  //     Cookies.set("userCurrent", JSON.stringify(decodedToken), {
  //       expires: 30,
  //     });
  //     getRolesUser(decodedToken);
  //   }
  // }, [children]);
  //
  // const getRolesUser = (decodedToken) => {
  //   RolesAPI.getRolesUser(decodedToken.id).then(
  //     (response) => {
  //       return children;
  //     },
  //     (error) => {
  //
  //     }
  //   );
  // };

  return children;
};

export default AuthGuard;
