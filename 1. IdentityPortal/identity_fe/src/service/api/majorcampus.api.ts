import { PREFIX_API_MAJOR_CAMPUS } from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { ModifyMajorCampus, PaginationParams } from "#type/index.t";
import { AxiosResponse } from "axios";

export type FetchMajorCampusResponse = {
  orderNumber: number;
  majorCampusId: number;
  majorCodeName: string;
  headMajorCodeName: string;
  campusCode: string;
};

export type FetchStaffResponse = {
  staffId: number;
  staffNameCode: string;
};

export type FetchMajorResponse = {
  majorId: number;
  majorName: string;
};

export type FetchDetailMajorCampusResponse = {
  majorId: number;
  majorName: string;
  headMajorId: number;
  headMajorCodeName: string;
  campusCode: string;
};

export interface PaginationParamsMajorCampus extends PaginationParams {
  departmentCampusId: number;
  majorCode?: string;
  headMajorName?: string;
  majorName?: string;
  headMajorCode?: string;
}

export const getMajorCampus = async (params: PaginationParamsMajorCampus) => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR_CAMPUS}`,
    method: "GET",
    params,
  })) as AxiosResponse<
    ResponseObject<PageableObject<Array<FetchMajorCampusResponse>>>
  >;

  return res.data;
};

export const createMajorCampus = async (data: ModifyMajorCampus) => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR_CAMPUS}`,
    method: "POST",
    data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const updateMajorCampus = async (
  majorCampusId: number,
  data: ModifyMajorCampus
) => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR_CAMPUS}/${majorCampusId}`,
    method: "PUT",
    data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const changeMajorCampusStatus = async (majorCampusId: number) => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR_CAMPUS}/status/${majorCampusId}`,
    method: "PUT",
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const getDetailMajorCampus = async (majorCampusId: number) => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR_CAMPUS}/${majorCampusId}`,
    method: "GET",
  })) as AxiosResponse<ResponseObject<FetchDetailMajorCampusResponse>>;

  return res.data;
};

export const getAllStaffList = async () => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR_CAMPUS}/staff`,
    method: "GET",
  })) as AxiosResponse<ResponseObject<Array<FetchStaffResponse>>>;

  return res.data;
};

export const getAllMajorList = async () => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR_CAMPUS}/major`,
    method: "GET",
  })) as AxiosResponse<ResponseObject<Array<FetchMajorResponse>>>;

  return res.data;
};
