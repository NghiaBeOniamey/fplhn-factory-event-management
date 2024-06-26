import { PREFIX_API_STUDENT } from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { PaginationParams } from "#type/index.t";
import { AxiosResponse } from "axios";

export type FetchListStudent = {
  orderNumber: number;
  studentId: number;
  studentCode: string;
  studentName: string;
  studentMail: string;
  studentStatus: string;
  studentPhoneNumber: string;
  departmentCampusId: number;
  departmentNameAndCampusName: string;
};

export type FetchListStudentResponse = {
  studentId: number;
  studentCode: string;
  studentName: string;
};

export type FetchListDepartmentCampusAll = {
  orderNumber: number;
  departmentCampusId: number;
  departmentNameAndCampusName: string;
};

export type DetailStudentResponse = {
  studentId: number;
  studentCode: string;
  studentName: string;
  studentMail: string;
  studentPhoneNumber: string;
  departmentId: number;
  campusId: number;
};

export interface StudentPaginationParams extends PaginationParams {
  campusCode: string;
  studentCode?: string;
  studentName?: string;
  studentMail?: string;
  listDepartmentId?: any;
}

export interface ModifyStudentParams {
  studentCode: string;
  studentName: string;
  studentMail?: string;
  studentPhoneNumber?: string;
  departmentId?: number;
  campusId?: number;
}

export type FetchListDepartmentStudent = {
  id: number;
  name: string;
};

export type FetchListCampusStudent = {
  id: number;
  name: string;
};

export const getAllStudent = async (params?: StudentPaginationParams) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_STUDENT}`,
    params: params || {},
  })) as AxiosResponse<ResponseObject<PageableObject<Array<FetchListStudent>>>>;

  return res.data;
};

export const getListAllStudent = async () => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_STUDENT}/search`,
  })) as AxiosResponse<ResponseObject<FetchListStudentResponse[]>>;

  return res.data;
};

export const getListAllDepartmentCampus = async () => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_STUDENT}/get-list-department-campus`,
  })) as AxiosResponse<ResponseObject<FetchListDepartmentCampusAll[]>>;

  return res.data;
};

export const addStudent = async (data: ModifyStudentParams) => {
  const res = (await request({
    method: "POST",
    url: `${PREFIX_API_STUDENT}`,
    data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const detailStudent = async (id: number) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_STUDENT}/${id}`,
  })) as AxiosResponse<ResponseObject<DetailStudentResponse>>;

  return res.data;
};

export const updateStudent = async (id: number, data: ModifyStudentParams) => {
  const res = (await request({
    method: "PUT",
    url: `${PREFIX_API_STUDENT}/${id}`,
    data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const updateStudentStatus = async (id: number) => {
  const res = (await request({
    method: "PUT",
    url: `${PREFIX_API_STUDENT}/status/${id}`,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const getListAllCampus = async () => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_STUDENT}/campus`,
  })) as AxiosResponse<ResponseObject<FetchListCampusStudent[]>>;

  return res.data;
};

export const getListAllDepartment = async () => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_STUDENT}/department`,
  })) as AxiosResponse<ResponseObject<FetchListDepartmentStudent[]>>;

  return res.data;
};
