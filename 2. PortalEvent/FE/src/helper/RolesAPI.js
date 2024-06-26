import { request } from "./Request.helper";

export class RolesAPI {
  static getRolesUser = (idUser) => {
    return request({
      method: "GET",
      url: `https://103.56.161.210:1626/roles?idUser=` + idUser,
    });
  };

  static callApiIdentityNavbar = (data) => {
    return request({
      method: "POST",
      url: `${process.env.REACT_APP_IDENTITY_WEB}api/auth/log-out`,
      data: data
    });
  };
}
