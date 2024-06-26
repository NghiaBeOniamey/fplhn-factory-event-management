import {
  ModifyRoleRequest,
  RolePaginationParams,
  createRole,
  detailRole,
  getAllRoleApi,
  getListsRole,
  updateRole,
} from "#service/api/role.api";
import { queryKey } from "#type/queryClientKey";
import { sortObjectKeys } from "#utils/common.helper";
import {
  UseQueryOptions,
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";

export const useGetAllRole = (
  params?: RolePaginationParams,
  option?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.roles, sortObjectKeys(params)],
    () => getAllRoleApi(params),
    {
      ...option,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getAllRoleApi>>>;
};

export const useGetAllListRole = (option?: UseQueryOptions) => {
  return useQuery([queryKey.role_search], () => getListsRole(), {
    ...option,
  }) as UseQueryResult<Awaited<ReturnType<typeof getListsRole>>>;
};

export const useGetDetailRole = (id: number, option?: UseQueryOptions) => {
  return useQuery([queryKey.detail_role, id], () => detailRole(id), {
    ...option,
    enabled: !!id,
  }) as UseQueryResult<Awaited<ReturnType<typeof detailRole>>>;
};

export const useCreateRole = () => {
  const queryClient = useQueryClient();
  return useMutation(async (data: ModifyRoleRequest) => createRole(data), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.roles]);
    },
  });
};

export const useUpdateRole = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async ({ id, role }: { id: number; role: ModifyRoleRequest }) =>
      updateRole(role, id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.roles]);
      },
    }
  );
};
