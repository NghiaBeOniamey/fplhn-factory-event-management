import {
  ModifyModuleRequest,
  ModulePaginationParams,
  createModule,
  deleteModule,
  getAllModule,
  getClientByModuleId,
  getListModule,
  updateModule,
} from "#service/api/module.api";
import { queryKey } from "#type/queryClientKey";
import { sortObjectKeys } from "#utils/common.helper";
import {
  UseQueryOptions,
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";

export const useGetAllModule = (
  params: ModulePaginationParams,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.modules, sortObjectKeys(params)],
    () => getAllModule(params),
    {
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getAllModule>>>;
};

export const useGetListModule = (options?: UseQueryOptions) => {
  return useQuery([queryKey.module_search], () => getListModule(), {
    ...options,
  }) as UseQueryResult<Awaited<ReturnType<typeof getListModule>>>;
};

export const useCreateModule = () => {
  const queryClient = useQueryClient();
  return useMutation(async (data: ModifyModuleRequest) => createModule(data), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.modules]);
    },
    onError: (error) => {},
  });
};

export const useUpdateModule = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async ({ data, id }: { data: ModifyModuleRequest; id: number }) =>
      updateModule(data, id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.modules]);
      },
      onError: (error) => {},
    }
  );
};

export const useDeleteModule = () => {
  const queryClient = useQueryClient();
  return useMutation(async (id: number) => deleteModule(id), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.modules]);
    },
    onError: (error) => {},
  });
};

export const useGetClientByModuleId = (id: number) => {
  return useQuery([queryKey.module_detail, id], () => getClientByModuleId(id), {
    enabled: !!id,
  }) as UseQueryResult<Awaited<ReturnType<typeof getClientByModuleId>>>;
};
