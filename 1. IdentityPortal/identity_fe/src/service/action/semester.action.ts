import {
  SemesterPaginationParams,
  createSemester,
  findAllSemester,
  getDetailSemester,
  updateSemester,
} from "#service/api/semester.api";
import { ModifySemester } from "#type/index.t";
import { queryKey } from "#type/queryClientKey";
import { sortObjectKeys } from "#utils/common.helper";
import {
  UseQueryOptions,
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";

export const useGetAllSemesters = (
  params: SemesterPaginationParams,
  options?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.semesters, sortObjectKeys(params)],
    () => findAllSemester(params),
    {
      keepPreviousData: true,
      ...options,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof findAllSemester>>>;
};

export const useCreateSemester = () => {
  const queryClient = useQueryClient();
  return useMutation(async (data: ModifySemester) => createSemester(data), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.semesters]);
    },
  });
};

export const useUpdateSemester = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async ({ id, semester }: { id: number; semester: ModifySemester }) =>
      updateSemester(id, semester),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.semesters]);
      },
    }
  );
};

export const useGetSemesterDetail = (semesterId: number) => {
  return useQuery(
    [queryKey.semester_detail, semesterId],
    () => getDetailSemester(semesterId),
    {
      keepPreviousData: true,
      enabled: !!semesterId,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getDetailSemester>>>;
};
