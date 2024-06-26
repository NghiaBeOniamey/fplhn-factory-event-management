import {
  PREFIX_API_FILE_IO,
  PREFIX_API_STAFF,
} from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { ModifyStaff, PaginationParams } from "#type/index.t";
import { AxiosResponse } from "axios";

export interface ResponseFetchListStaff {
  id: number;
  orderNumber: number;
  staffCode: string;
  staffName: string;
  accountFe: string;
  accountFpt: string;
  departmentName: string;
  campusName: string;
  phoneNumber: string;
}

export interface ResponseFetchDetailStaff {
  staffCode: string;
  staffName: string;
  departmentId: number;
  campusId: number;
  emailFe: string;
  emailFpt: string;
  phoneNumber: string;
}

export interface ResponseFetchListCommon {
  id: number;
  code: string;
  name: string;
}

export interface StaffPagination extends PaginationParams {
  staffCode?: string;
  staffName?: string;
  campusCode?: string;
}

export const findStaffPagination = async (params: StaffPagination) => {
  const res = (await request({
    url: `${PREFIX_API_STAFF}`,
    method: "GET",
    params: params,
  })) as AxiosResponse<
    ResponseObject<PageableObject<Array<ResponseFetchListStaff>>>
  >;

  return res.data;
};

export const addStaff = async (nhanVien: ModifyStaff) => {
  return await request({
    url: PREFIX_API_STAFF,
    method: "POST",
    data: nhanVien,
  });
};

export const updateStaff = async (staffId: number, staffInfo: ModifyStaff) => {
  return await request({
    url: `${PREFIX_API_STAFF}/${staffId}`,
    method: "PUT",
    data: staffInfo,
  });
};

export const downloadTemplate = async (idNhanVien: number | undefined) => {
  return await request({
    url: `${PREFIX_API_FILE_IO}/download-template/${idNhanVien}`,
    method: "GET",
    responseType: "blob",
  });
};

export const downloadDataStaff = async (campusCode: string) => {
  return await request({
    url: `${PREFIX_API_FILE_IO}/export-staff/${campusCode}`,
    method: "GET",
    responseType: "blob",
  });
};

export const downloadTemplateByAdmin = async () => {
  return await request({
    url: `${PREFIX_API_FILE_IO}/download-template`,
    method: "GET",
    responseType: "blob",
  });
};

export const uploadFile = async (file: File) => {
  const formData = new FormData();
  formData.append("file", file);
  return await request({
    url: `${PREFIX_API_FILE_IO}/upload`,
    method: "POST",
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};

export const fetchListDepartment = async () => {
  const res = (await request({
    url: `${PREFIX_API_STAFF}/department`,
    method: "GET",
  })) as AxiosResponse<ResponseObject<Array<ResponseFetchListCommon>>>;
  return res.data;
};

export const fetchListCampus = async () => {
  const res = (await request({
    url: `${PREFIX_API_STAFF}/campus`,
    method: "GET",
  })) as AxiosResponse<ResponseObject<Array<ResponseFetchListCommon>>>;
  return res.data;
};

export const updateStaffStatus = async (idNhanVien: number) => {
  const res = (await request({
    url: `${PREFIX_API_STAFF}/status/${idNhanVien}`,
    method: "PUT",
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const getDetailStaff = async (idNhanVien: number) => {
  const res = (await request({
    url: `${PREFIX_API_STAFF}/${idNhanVien}`,
    method: "GET",
  })) as AxiosResponse<ResponseObject<ResponseFetchDetailStaff>>;
  return res.data;
};
