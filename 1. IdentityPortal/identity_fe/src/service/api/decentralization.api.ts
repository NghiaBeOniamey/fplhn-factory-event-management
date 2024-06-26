import { PREFIX_API_DECENTRALIZATION } from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { AxiosResponse } from "axios";

export type FetchListModuleDecentralization = {
  staffId: number;
  staffInfo: string;
  listRoleCode: string;
};

export type FetchStaffSearchResponse = {
  staffCode: string;
  staffName: string;
};

export const getAllRoleAvailable = async () => {
  return await request({
    url: `${PREFIX_API_DECENTRALIZATION}/get-list-role-available`,
    method: "GET",
  });
};

export const getStaffModuleDecentralization = async (
  moduleId: number,
  listStaffCode: string,
  params: any
) => {
  const res = (await request({
    url: `${PREFIX_API_DECENTRALIZATION}/get-staff-role-module`,
    method: "GET",
    params: {
      moduleId,
      listStaffCode,
      ...params,
    },
  })) as AxiosResponse<
    ResponseObject<PageableObject<Array<FetchListModuleDecentralization>>>
  >;

  return res.data;
};

export const updateStaffModuleDecentralization = async (data: any) => {
  const res = (await request({
    url: `${PREFIX_API_DECENTRALIZATION}/update-staff-role-module`,
    method: "POST",
    data: data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const getStaffSearch = async (staffCode: string) => {
  const res = (await request({
    url: `${PREFIX_API_DECENTRALIZATION}/get-list-staff-info-search`,
    method: "GET",
    params: {
      staffCode,
    },
  })) as AxiosResponse<ResponseObject<Array<FetchStaffSearchResponse>>>;

  return res.data;
};
