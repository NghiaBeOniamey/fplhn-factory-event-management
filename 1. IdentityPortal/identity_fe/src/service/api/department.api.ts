import { PREFIX_API_DEPARTMENT } from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { PaginationParams } from "#type/index.t";
import { AxiosResponse } from "axios";

export type FetchListDepartmentResponse = {
  orderNumber: number;
  departmentId: number;
  departmentName: string;
  departmentCode: string;
  departmentStatus: string;
};

export type FetchAllListDepartmentResponse = {
  departmentId: number;
  departmentCode: string;
  departmentName: string;
};

export interface ModifyDepartment {
  departmentCode: string;
  departmentName: string;
}

export interface DepartmentPaginationParams extends PaginationParams {
  searchValues?: string;
}

export const getAllDepartmentApi = async (
  params: DepartmentPaginationParams
) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_DEPARTMENT}`,
    params,
  })) as AxiosResponse<
    ResponseObject<PageableObject<Array<FetchListDepartmentResponse>>>
  >;

  return res.data;
};

export const createDepartment = async (data: ModifyDepartment) => {
  const res = (await request({
    method: "POST",
    url: `${PREFIX_API_DEPARTMENT}`,
    data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const updateDepartment = async (data: ModifyDepartment, id: Number) => {
  const res = (await request({
    method: "PUT",
    url: `${PREFIX_API_DEPARTMENT}/${id}`,
    data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const getListDepartment = async () => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_DEPARTMENT}/search`,
  })) as AxiosResponse<ResponseObject<Array<FetchAllListDepartmentResponse>>>;

  return res.data;
};
