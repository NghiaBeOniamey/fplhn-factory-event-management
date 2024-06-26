export interface DecodedToken {
  fullName: string;
  userId: string;
  userCode: string;
  rolesName: string;
  host: string;
  rolesCode: string[];
  exp: number;
  email: string;
  pictureUrl: string;
  trainingFacilityCode: string;
  userType: string;
  moduleAvailableResponses: any[];
  trainingFacilityId: string;
  trainingFacilityName: string;
  subjectName: string;
  subjectCode: string;
}

export interface UserInformation {
  fullName: string;
  userId: string;
  userCode: string;
  rolesName: string;
  hostId: string;
  email: string;
  pictureUrl: string;
  campusCode: string;
  userType: string;
  moduleAvailableResponse: any[];
  campusId: string;
  campusName: string;
  subjectCode: string;
}

export interface Staff {
  id: number;
  orderNumber: number;
  staffCode: string;
  staffName: string;
  accountFe: string;
  accountFpt: string;
  departmentName: string;
  phoneNumber: string;
}

export interface ModifyStaff {
  staffCode: string;
  staffName: string;
  campusId?: number;
  departmentId?: number;
  phoneNumber: string;
  emailFpt: string;
  emailFe: string;
}

export interface Campus {
  id: number;
  name: string;
}

export interface Department {
  id: number;
  name: string;
}

export interface Client {
  id: number;
  clientId: string;
  clientSecret: string;
}

export interface PaginationParams {
  page: number;
  size: number;
  orderBy?: string;
  sortBy?: string;
  q?: string;
}

export type Major = {
  majorId: number;
  majorCode: string;
  majorName: string;
  majorStatus: string;
};

export interface ModifyMajor {
  majorCode: string;
  majorName: string;
}

export interface ModifyMajorCampus {
  majorId: number;
  headMajorId: number;
  departmentCampusId: number;
}

export interface ModifySemester {
  name: string;
  startTime: number;
  endTime: number;
  startTimeFirstBlock: number;
  endTimeFirstBlock: number;
  startTimeSecondBlock: number;
  endTimeSecondBlock: number;
}
