import {
  PREFIX_API_FILE_IO,
  PREFIX_API_MODULE,
} from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { PaginationParams } from "#type/index.t";
import { AxiosResponse } from "axios";

export type FetchListModuleResponse = {
  orderNumber: number;
  moduleId: number;
  moduleName: string;
  moduleCode: string;
  moduleUrl: string;
  moduleStatus: string;
};

export type FetchListAllModuleResponse = {
  moduleId: number;
  moduleName: string;
  moduleCode: string;
  moduleUrl: string;
};

export type FetchClientByModuleIdResponse = {
  clientId: string;
  clientSecret: string;
};

export interface ModulePaginationParams extends PaginationParams {
  listId?: any;
  deleteStatus?: string;
}

export type ModifyModuleRequest = {
  moduleName: string;
  moduleCode: string;
  moduleUrl: string;
  redirectRoute?: string;
};

export const getAllModule = async (params: ModulePaginationParams) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_MODULE}`,
    params: params,
  })) as AxiosResponse<
    ResponseObject<PageableObject<Array<FetchListModuleResponse>>>
  >;

  return res.data;
};

export const getListModule = async () => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_MODULE}/search`,
  })) as AxiosResponse<ResponseObject<Array<FetchListAllModuleResponse>>>;

  return res.data;
};

export const createModule = async (data: ModifyModuleRequest) => {
  const res = (await request({
    method: "POST",
    url: `${PREFIX_API_MODULE}`,
    data: data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const updateModule = async (data: ModifyModuleRequest, id: number) => {
  const res = (await request({
    method: "PUT",
    url: `${PREFIX_API_MODULE}/${id}`,
    data: data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const deleteModule = async (id: number) => {
  const res = (await request({
    method: "PUT",
    url: `${PREFIX_API_MODULE}/status/${id}`,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const getClientByModuleId = async (id: number) => {
  const res = (await request({
    method: "GET",
    url: `${PREFIX_API_MODULE}/client/${id}`,
  })) as AxiosResponse<ResponseObject<FetchClientByModuleIdResponse>>;

  return res.data;
};

export const downloadTemplateRole = async (moduleCode: string) => {
  return await request({
    url: `${PREFIX_API_FILE_IO}/download-template-role/${moduleCode}`,
    method: "GET",
    responseType: "blob",
  });
};

export const uploadFileRole = async (file: File) => {
  const formData = new FormData();
  formData.append("file", file);
  return await request({
    url: `${PREFIX_API_FILE_IO}/upload-role`,
    method: "POST",
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
