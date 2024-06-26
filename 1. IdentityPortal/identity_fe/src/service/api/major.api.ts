import { PREFIX_API_MAJOR } from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { ModifyMajor, PaginationParams } from "#type/index.t";
import { AxiosResponse } from "axios";

export type FetchListMajorResponse = {
  orderNumber: number;
  majorId: number;
  majorName: string;
  majorCode: string;
  majorStatus: string;
};

export type FetchDetailMajorResponse = {
  majorId: number;
  majorCode: string;
  majorName: string;
  majorStatus: string;
};

export interface MajorPaginationParams extends PaginationParams {
  majorCode?: string;
  majorName?: string;
  departmentId?: number;
}

export const getAllMajor = async (params: PaginationParams) => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR}`,
    method: "GET",
    params,
  })) as AxiosResponse<
    ResponseObject<PageableObject<Array<FetchListMajorResponse>>>
  >;

  return res.data;
};

export const createMajor = async (data: ModifyMajor) => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR}`,
    method: "POST",
    data,
  })) as AxiosResponse<ResponseObject<any>>;
  return res.data;
};

export const updateMajor = async (majorId: number, data: ModifyMajor) => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR}/${majorId}`,
    method: "PUT",
    data,
  })) as AxiosResponse<ResponseObject<any>>;
  return res.data;
};

export const changeMajorStatus = async (majorId: number) => {
  return await request({
    url: `${PREFIX_API_MAJOR}/status/${majorId}`,
    method: "PUT",
  });
};

export const getDetailMajor = async (majorId: number) => {
  const res = (await request({
    url: `${PREFIX_API_MAJOR}/${majorId}`,
    method: "GET",
  })) as AxiosResponse<ResponseObject<FetchDetailMajorResponse>>;

  return res.data;
};
