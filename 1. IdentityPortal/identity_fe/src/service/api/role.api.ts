import { PREFIX_API_ROLE } from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { PaginationParams } from "#type/index.t";
import { AxiosResponse } from "axios";

export type FetchListRoleResponse = {
  id: number;
  roleCode: string;
  roleName: string;
  roleStatus: string;
};

export type FetchListRoleResponseSearch = {
  id: number;
  roleCode: string;
  roleName: string;
};

export interface RolePaginationParams extends PaginationParams {
  searchValues?: any;
}

export type RoleDetailResponse = {
  roleId: number;
  roleCode: string;
  roleName: string;
  roleStatus: string;
};

export type ModifyRoleRequest = {
  roleCode: string;
  roleName: string;
};

export const getAllRoleApi = async (params?: RolePaginationParams) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_ROLE}`,
    params: params ? params : {},
  })) as AxiosResponse<
    ResponseObject<PageableObject<Array<FetchListRoleResponse>>>
  >;

  return res.data;
};

export const createRole = async (data: ModifyRoleRequest) => {
  const res = (await request({
    method: "POST",
    url: `${PREFIX_API_ROLE}`,
    data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const updateRole = async (data: ModifyRoleRequest, id: number) => {
  const res = (await request({
    method: "PUT",
    url: `${PREFIX_API_ROLE}/${id}`,
    data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const deleteRole = async (id: number) => {
  const res = (await request({
    method: "DELETE",
    url: `${PREFIX_API_ROLE}/${id}`,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const detailRole = async (id: number) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_ROLE}/${id}`,
  })) as AxiosResponse<ResponseObject<RoleDetailResponse>>;

  return res.data;
};

export const getListsRole = async () => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_ROLE}/all`,
  })) as AxiosResponse<ResponseObject<Array<FetchListRoleResponseSearch>>>;

  return res.data;
};
