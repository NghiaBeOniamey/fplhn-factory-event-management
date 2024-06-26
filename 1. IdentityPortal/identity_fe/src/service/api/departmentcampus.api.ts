import { PREFIX_API_DEPARTMENT_CAMPUS } from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { PaginationParams } from "#type/index.t";
import { AxiosResponse } from "axios";

export type FetchListDepartmentCampusResponse = {
  orderNumber: number;
  departmentCampusId: number;
  campusId: number;
  headDepartmentCampusId: number;
  campusName: string;
  campusCode: string;
  headDepartmentCampusName: string;
  departmentCampusStatus: string;
  headDepartmentCampusCode: string;
};

export interface ModifyDepartmentCampus {
  departmentId: number;
  campusId: number;
  headDepartmentCampusId: number;
}

export interface DepartmentCampusPaginationParams extends PaginationParams {
  searchValues?: string;
}

export type FetchListCampusApi = {
  campusId: number;
  campusName: string;
};

export type FetchListHeadDepartmentCampusApi = {
  staffId: number;
  staffName: string;
  staffCode: string;
};

export type FetchListDepartmentCampusApi = {
  campusId: number;
  campusName: string;
  staffName: string;
  staffCode: string;
};

export const getAllDepartmentCampusApi = async (
  id: number,
  params: DepartmentCampusPaginationParams
) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_DEPARTMENT_CAMPUS}/${id}`,
    params: params,
  })) as AxiosResponse<
    ResponseObject<PageableObject<Array<FetchListDepartmentCampusResponse>>>
  >;

  return res.data;
};

export const createDepartmentCampus = async (data: ModifyDepartmentCampus) => {
  const res = (await request({
    method: "POST",
    url: `${PREFIX_API_DEPARTMENT_CAMPUS}`,
    data: data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const updateDepartmentCampus = async (
  data: ModifyDepartmentCampus,
  id: number
) => {
  const res = (await request({
    method: "PUT",
    url: `${PREFIX_API_DEPARTMENT_CAMPUS}/${id}`,
    data: data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const deleteDepartmentCampus = async (id: number) => {
  const res = (await request({
    method: "PUT",
    url: `${PREFIX_API_DEPARTMENT_CAMPUS}/status/${id}`,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const getListCampusApi = async () => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_DEPARTMENT_CAMPUS}/campus`,
  })) as AxiosResponse<ResponseObject<Array<FetchListCampusApi>>>;

  return res.data;
};

export const getListHeadDepartmentCampusApi = async () => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_DEPARTMENT_CAMPUS}/head-department-campus`,
  })) as AxiosResponse<ResponseObject<Array<FetchListHeadDepartmentCampusApi>>>;

  return res.data;
};

export const getDepartmentNameApi = async (id: number) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_DEPARTMENT_CAMPUS}/department-name/${id}`,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const getListDepartmentCampus = async (id: number) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_DEPARTMENT_CAMPUS}/search/${id}`,
  })) as AxiosResponse<ResponseObject<Array<FetchListDepartmentCampusApi>>>;

  return res.data;
};
