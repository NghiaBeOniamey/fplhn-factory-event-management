import {
  CampusPaginationParams,
  CreateCampus,
  createCampus,
  getAllCampusApi,
  getListAllCampusSearch,
  updateCampus,
  updateCampusStatus,
} from "#service/api/campus.api";
import { queryKey } from "#type/queryClientKey";
import { sortObjectKeys } from "#utils/common.helper";
import {
  UseQueryOptions,
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";

export const useGetAllCampus = (
  params: CampusPaginationParams,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.campus, sortObjectKeys(params)],
    () => getAllCampusApi(params),
    {
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getAllCampusApi>>>;
};

export const useGetAllCampusDataSearch = (options?: UseQueryOptions) => {
  return useQuery([queryKey.campus_search], () => getListAllCampusSearch(), {
    ...options,
  }) as UseQueryResult<Awaited<ReturnType<typeof getListAllCampusSearch>>>;
};

export const useCreateCampus = () => {
  const queryClient = useQueryClient();
  return useMutation(async (data: CreateCampus) => createCampus(data), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.campus]);
    },
    onError: (error) => {},
  });
};

export const useUpdateCampus = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async ({ data, id }: { data: CreateCampus; id: number }) =>
      updateCampus(data, id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.campus]);
      },
      onError: (error) => {},
    }
  );
};

export const useUpdateCampusStatus = () => {
  const queryClient = useQueryClient();
  return useMutation(async (id: number) => updateCampusStatus(id), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.campus]);
    },
    onError: (error) => {},
  });
};
