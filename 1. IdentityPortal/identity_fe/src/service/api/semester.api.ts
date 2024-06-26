import { PREFIX_API_SEMESTER } from "#service/base/api.constant";
import request from "#service/base/request";
import { PageableObject, ResponseObject } from "#type/index.i";
import { ModifySemester, PaginationParams } from "#type/index.t";
import { AxiosResponse } from "axios";

export type FetchListSemesterResponse = {
  id: number;
  orderNumber: number;
  semesterName: string;
  startTime: number;
  endTime: number;
  startTimeFirstBlock: number;
  endTimeFirstBlock: number;
  startTimeSecondBlock: number;
  endTimeSecondBlock: number;
};

export type SemesterDetailResponse = {
  id: number;
  semesterName: string;
  startTime: number;
  endTime: number;
  startTimeFirstBlock: number;
  endTimeFirstBlock: number;
  startTimeSecondBlock: number;
  endTimeSecondBlock: number;
};

export interface SemesterPaginationParams extends PaginationParams {
  semesterName?: string;
  startTime?: number;
  endTime?: number;
  startTimeFirstBlock?: number;
  endTimeFirstBlock?: number;
  startTimeSecondBlock?: number;
  endTimeSecondBlock?: number;
}

export const findAllSemester = async (params: SemesterPaginationParams) => {
  const res = (await request({
    url: `${PREFIX_API_SEMESTER}`,
    method: "GET",
    params: params,
  })) as AxiosResponse<
    ResponseObject<PageableObject<Array<FetchListSemesterResponse>>>
  >;
  return res.data;
};

export const createSemester = async (data: ModifySemester) => {
  const res = (await request({
    url: `${PREFIX_API_SEMESTER}`,
    method: "POST",
    data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const updateSemester = async (
  semesterId: number,
  data: ModifySemester
) => {
  const res = (await request({
    url: `${PREFIX_API_SEMESTER}/${semesterId}`,
    method: "PUT",
    data,
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const getDetailSemester = async (semesterId: number) => {
  const res = (await request({
    url: `${PREFIX_API_SEMESTER}/${semesterId}`,
    method: "GET",
  })) as AxiosResponse<ResponseObject<SemesterDetailResponse>>;
  return res.data;
};
