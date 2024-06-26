import {
  createDepartment,
  DepartmentPaginationParams,
  getAllDepartmentApi,
  getListDepartment,
  ModifyDepartment,
  updateDepartment,
} from "#service/api/department.api";
import {
  createDepartmentCampus,
  deleteDepartmentCampus,
  DepartmentCampusPaginationParams,
  getAllDepartmentCampusApi,
  getDepartmentNameApi,
  getListCampusApi,
  getListDepartmentCampus,
  getListHeadDepartmentCampusApi,
  ModifyDepartmentCampus,
  updateDepartmentCampus,
} from "#service/api/departmentcampus.api";
import { queryKey } from "#type/queryClientKey";
import { sortObjectKeys } from "#utils/common.helper";
import {
  useMutation,
  useQuery,
  useQueryClient,
  UseQueryOptions,
  UseQueryResult,
} from "@tanstack/react-query";

//department action

export const useGetAllDepartment = (
  params: DepartmentPaginationParams,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.department, sortObjectKeys(params)],
    () => getAllDepartmentApi(params),
    {
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getAllDepartmentApi>>>;
};

export const useGetAllDepartmentDataSearch = (options?: UseQueryOptions) => {
  return useQuery([queryKey.department_search], () => getListDepartment(), {
    ...options,
  }) as UseQueryResult<Awaited<ReturnType<typeof getListDepartment>>>;
};

export const useCreateDepartment = () => {
  const queryClient = useQueryClient();
  return useMutation(async (data: ModifyDepartment) => createDepartment(data), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.department]);
    },
    onError: (error) => {},
  });
};

export const useUpdateDepartment = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async ({ data, id }: { data: ModifyDepartment; id: number }) =>
      updateDepartment(data, id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.department]);
      },
      onError: (error) => {},
    }
  );
};

//department-campus action

export const useGetAllDepartmentCampus = (
  id: number,
  params: DepartmentCampusPaginationParams,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.department_campus, id, sortObjectKeys(params)],
    () => getAllDepartmentCampusApi(id, params),
    {
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getAllDepartmentCampusApi>>>;
};

export const useCreateDepartmentCampus = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async (data: ModifyDepartmentCampus) => createDepartmentCampus(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.department_campus]);
      },
      onError: (error) => {},
    }
  );
};

export const useUpdateDepartmentCampus = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async ({ data, id }: { data: ModifyDepartmentCampus; id: number }) =>
      updateDepartmentCampus(data, id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.department_campus]);
      },
      onError: (error) => {},
    }
  );
};

export const useDeleteDepartmentCampus = () => {
  const queryClient = useQueryClient();
  return useMutation(async (id: number) => deleteDepartmentCampus(id), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.department_campus]);
    },
    onError: (error) => {},
  });
};

export const useGetListCampus = (options?: UseQueryOptions) => {
  return useQuery(
    [queryKey.department_campus_campus_select],
    () => getListCampusApi(),
    {
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getListCampusApi>>>;
};

export const useListHeadDepartmentCampusApi = (options?: UseQueryOptions) => {
  return useQuery(
    [queryKey.department_campus_head_department_select],
    () => getListHeadDepartmentCampusApi(),
    {
      ...options,
    }
  ) as UseQueryResult<
    Awaited<ReturnType<typeof getListHeadDepartmentCampusApi>>
  >;
};

export const useGetDepartmentName = (id: number, options?: UseQueryOptions) => {
  return useQuery(
    [queryKey.department_campus_department_name, id],
    () => getDepartmentNameApi(id),
    {
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getDepartmentNameApi>>>;
};

export const useGetListDepartmentCampus = (
  id: number,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.department_campus_all, id],
    () => getListDepartmentCampus(id),
    {
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getListDepartmentCampus>>>;
};
