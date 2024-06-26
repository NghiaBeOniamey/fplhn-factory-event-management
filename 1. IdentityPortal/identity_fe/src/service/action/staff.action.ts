import {
  StaffPagination,
  addStaff,
  fetchListCampus,
  fetchListDepartment,
  findStaffPagination,
  getDetailStaff,
  updateStaff,
  updateStaffStatus,
} from "#service/api/staff.api";
import { ModifyStaff } from "#type/index.t";
import { queryKey } from "#type/queryClientKey";
import { sortObjectKeys } from "#utils/common.helper";
import {
  UseQueryOptions,
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";

export const useGetAllStaff = (
  paginationParams: StaffPagination,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.staffs, sortObjectKeys(paginationParams)],
    () => findStaffPagination(paginationParams),
    {
      ...options,
      refetchInterval: 10_000,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof findStaffPagination>>>;
};

export const useGetDetailStaff = (
  staffId: number | null,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.detail_staff, staffId],
    () => (staffId ? getDetailStaff(staffId) : Promise.resolve(null)),
    {
      keepPreviousData: true,
      ...options,
      enabled: !!staffId,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getDetailStaff>>>;
};

export const useGetAllDepartment = (
  options?: Omit<
    UseQueryOptions<unknown, unknown, unknown, {}[]>,
    "queryFn" | "queryKey"
  >
) => {
  return useQuery([queryKey.department_common], () => fetchListDepartment(), {
    keepPreviousData: true,
    ...options,
  }) as UseQueryResult<Awaited<ReturnType<typeof fetchListDepartment>>>;
};

export const useGetAllCampus = (
  options?: Omit<
    UseQueryOptions<unknown, unknown, unknown, {}[]>,
    "queryFn" | "queryKey"
  >
) => {
  return useQuery([queryKey.campus_common], () => fetchListCampus(), {
    keepPreviousData: true,
    ...options,
  }) as UseQueryResult<Awaited<ReturnType<typeof fetchListCampus>>>;
};

export const useAddStaff = () => {
  const queryClient = useQueryClient();
  return useMutation(async (staff: ModifyStaff) => addStaff(staff), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.staffs]);
    },
    onError: (error) => {},
  });
};

export const useUpdateStaff = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async ({ id, staff }: { id: number; staff: ModifyStaff }) =>
      updateStaff(id, staff),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.staffs]);
      },
      onError: (error) => {},
    }
  );
};

export const useUpdateStaffStatus = () => {
  const queryClient = useQueryClient();
  return useMutation(async (id: number) => updateStaffStatus(id), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.staffs]);
    },
    onError: (error) => {},
  });
};
