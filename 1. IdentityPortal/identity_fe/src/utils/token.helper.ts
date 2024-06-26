import { DecodedToken, UserInformation } from "#type/index.t";
import { jwtDecode } from "jwt-decode";

export const getUserInformation = (token: string): UserInformation => {
  const decoded = jwtDecode<DecodedToken>(token);
  return {
    fullName: decoded.fullName,
    userId: decoded.userId,
    userCode: decoded.userCode,
    rolesName: decoded.rolesName,
    hostId: decoded.host,
    email: decoded.email,
    pictureUrl: decoded.pictureUrl,
    campusCode: decoded.trainingFacilityCode,
    userType: decoded.userType,
    campusId: decoded.trainingFacilityId,
    campusName: decoded.trainingFacilityName,
    moduleAvailableResponse: decoded.moduleAvailableResponses,
    subjectCode: decoded.subjectCode,
  };
};

export const getPermissionsUser = (token: string): string[] => {
  const decoded = jwtDecode<DecodedToken>(token);
  return decoded.rolesCode;
};

export const getExpireTime = (token: string): number => {
  const decoded = jwtDecode<DecodedToken>(token);
  return decoded.exp;
};

export const isTokenExpired = (token: string): boolean => {
  const expireTime = getExpireTime(token);
  return Date.now() >= expireTime * 1000;
};
