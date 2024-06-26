import { PREFIX_API_CAMPUS } from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { PaginationParams } from "#type/index.t";
import { AxiosResponse } from "axios";

export interface ResponseFetchListCampus {
  orderNumber: number;
  campusId: number;
  campusCode: string;
  campusName: string;
  campusStatus: string;
}

export interface ResponseFetchAllListCampus {
  campusId: number;
  campusCode: string;
  campusName: string;
}

export interface CampusPaginationParams extends PaginationParams {
  searchValues?: string;
}

export type CreateCampus = {
  campusCode: string;
  campusName: string;
};

export const getAllCampusApi = async (params: CampusPaginationParams) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_CAMPUS}`,
    params: params,
  })) as AxiosResponse<
    ResponseObject<PageableObject<Array<ResponseFetchListCampus>>>
  >;

  return res.data;
};

export const createCampus = async (data: CreateCampus) => {
  const res = (await request({
    method: "POST",
    url: `${PREFIX_API_CAMPUS}`,
    data: data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const updateCampus = async (data, id) => {
  const res = (await request({
    method: "PUT",
    url: `${PREFIX_API_CAMPUS}/${id}`,
    data: data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const updateCampusStatus = async (id: number) => {
  const res = (await request({
    method: "PUT",
    url: `${PREFIX_API_CAMPUS}/${id}`,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const getListAllCampusSearch = async () => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_CAMPUS}/search`,
  })) as AxiosResponse<ResponseObject<Array<ResponseFetchAllListCampus>>>;

  return res.data;
};
