import {
  getStaffModuleDecentralization,
  getStaffSearch,
  updateStaffModuleDecentralization,
} from "#service/api/decentralization.api";
import { queryKey } from "#type/queryClientKey";
import {
  UseQueryOptions,
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";

export const useStaffModuleDecentralization = (
  moduleId: number,
  listStaffCode: string,
  params: any
) => {
  return useQuery(
    [queryKey.decentralization, moduleId, listStaffCode, params],
    () => getStaffModuleDecentralization(moduleId, listStaffCode, params),
    {}
  ) as UseQueryResult<
    Awaited<ReturnType<typeof getStaffModuleDecentralization>>
  >;
};

export const useUpdateStaffModuleDecentralization = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async (data: any) => updateStaffModuleDecentralization(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.decentralization]);
      },
    }
  );
};

export const useGetStaffInfoSearch = (
  staffCode: string,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.decentralization_staff_info_search, staffCode],
    () => getStaffSearch(staffCode),
    {
      keepPreviousData: true,
      enabled: staffCode !== "" || staffCode?.length > 0,
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getStaffSearch>>>;
};
