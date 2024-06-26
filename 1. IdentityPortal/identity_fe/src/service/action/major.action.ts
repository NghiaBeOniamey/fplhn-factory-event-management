import {
  MajorPaginationParams,
  changeMajorStatus,
  createMajor,
  getAllMajor,
  getDetailMajor,
  updateMajor,
} from "#service/api/major.api";
import {
  PaginationParamsMajorCampus,
  changeMajorCampusStatus,
  createMajorCampus,
  getAllMajorList,
  getAllStaffList,
  getDetailMajorCampus,
  getMajorCampus,
  updateMajorCampus,
} from "#service/api/majorcampus.api";
import { ModifyMajor, ModifyMajorCampus } from "#type/index.t";
import { queryKey } from "#type/queryClientKey";
import { sortObjectKeys } from "#utils/common.helper";
import {
  UseQueryOptions,
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";

export const useGetAllMajor = (
  params?: MajorPaginationParams,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.majors, sortObjectKeys(params)],
    () => getAllMajor(params),
    {
      keepPreviousData: true,
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getAllMajor>>>;
};

export const useGetDetailMajor = (
  majorId: number | null,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.majors, majorId],
    () => (majorId ? getDetailMajor(majorId) : Promise.resolve(null)),
    {
      keepPreviousData: true,
      ...options,
      enabled: !!majorId,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getDetailMajor>>>;
};

export const useCreateMajor = () => {
  const queryClient = useQueryClient();
  return useMutation(async (major: ModifyMajor) => createMajor(major), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.majors]);
    },
  });
};

export const useUpdateMajor = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async ({ id, major }: { id: number; major: ModifyMajor }) =>
      updateMajor(id, major),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.majors]);
      },
    }
  );
};

export const useChangeMajorStatus = () => {
  const queryClient = useQueryClient();
  return useMutation(async (id: number) => changeMajorStatus(id), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.majors]);
    },
  });
};

export const useGetAllMajorCampus = (
  params?: PaginationParamsMajorCampus,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.major_campus, sortObjectKeys(params)],
    () => getMajorCampus(params),
    {
      keepPreviousData: true,
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getMajorCampus>>>;
};

export const useCreateMajorCampus = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async (majorCampus: ModifyMajorCampus) => createMajorCampus(majorCampus),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.major_campus]);
      },
    }
  );
};

export const useUpdateMajorCampus = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async ({
      id,
      majorCampus,
    }: {
      id: number;
      majorCampus: ModifyMajorCampus;
    }) => updateMajorCampus(id, majorCampus),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.major_campus]);
      },
    }
  );
};

export const useChangeMajorCampusStatus = () => {
  const queryClient = useQueryClient();
  return useMutation(async (id: number) => changeMajorCampusStatus(id), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.major_campus]);
    },
  });
};

export const useGetDetailMajorCampus = (
  majorCampusId: number | null,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.major_campus, majorCampusId],
    () =>
      majorCampusId
        ? getDetailMajorCampus(majorCampusId)
        : Promise.resolve(null),
    {
      keepPreviousData: true,
      ...options,
      enabled: !!majorCampusId,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getDetailMajorCampus>>>;
};

export const useGetAllStaffList = () => {
  return useQuery([queryKey.staff_common_select], () =>
    getAllStaffList()
  ) as UseQueryResult<Awaited<ReturnType<typeof getAllStaffList>>>;
};

export const useGetAllMajorList = () => {
  return useQuery([queryKey.major_common_select], () =>
    getAllMajorList()
  ) as UseQueryResult<Awaited<ReturnType<typeof getAllMajorList>>>;
};
