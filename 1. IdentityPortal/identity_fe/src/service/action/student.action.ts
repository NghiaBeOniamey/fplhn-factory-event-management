import {
  ModifyStudentParams,
  StudentPaginationParams,
  addStudent,
  detailStudent,
  getAllStudent,
  getListAllCampus,
  getListAllDepartment,
  getListAllDepartmentCampus,
  getListAllStudent,
  updateStudent,
  updateStudentStatus,
} from "#service/api/student.api";
import { queryKey } from "#type/queryClientKey";
import { sortObjectKeys } from "#utils/common.helper";
import {
  UseQueryOptions,
  UseQueryResult,
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query";

export const useGetAllStudent = (
  params?: StudentPaginationParams,
  option?: UseQueryOptions
) => {
  return useQuery(
    [queryKey.students, sortObjectKeys(params)],
    () => getAllStudent(params),
    {
      ...option,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getAllStudent>>>;
};

export const useGetDetailStudent = (id: number, option?: UseQueryOptions) => {
  return useQuery([queryKey.student_detail, id], () => detailStudent(id), {
    ...option,
    enabled: !!id,
  }) as UseQueryResult<Awaited<ReturnType<typeof detailStudent>>>;
};

export const useCreateStudent = () => {
  const queryClient = useQueryClient();
  return useMutation(async (data: ModifyStudentParams) => addStudent(data), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.students]);
    },
  });
};

export const useUpdateStudent = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async ({ id, student }: { id: number; student: ModifyStudentParams }) =>
      updateStudent(id, student),
    {
      onSuccess: () => {
        queryClient.invalidateQueries([queryKey.students]);
      },
    }
  );
};

export const useGetListAllStudent = (option?: UseQueryOptions) => {
  return useQuery([queryKey.student_search_all], () => getListAllStudent(), {
    ...option,
  }) as UseQueryResult<Awaited<ReturnType<typeof getListAllStudent>>>;
};

export const useGetListAllDepartmentCampus = (option?: UseQueryOptions) => {
  return useQuery(
    [queryKey.department_campus_all],
    () => getListAllDepartmentCampus(),
    {
      ...option,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getListAllDepartmentCampus>>>;
};

export const useUpdateStudentStatus = () => {
  const queryClient = useQueryClient();
  return useMutation(async (id: number) => updateStudentStatus(id), {
    onSuccess: () => {
      queryClient.invalidateQueries([queryKey.students]);
    },
  });
};

export const useGetAllListCampus = (option?: UseQueryOptions) => {
  return useQuery([queryKey.student_campus_all], () => getListAllCampus(), {
    ...option,
  }) as UseQueryResult<Awaited<ReturnType<typeof getListAllCampus>>>;
};

export const useGetAllListDepartment = (option?: UseQueryOptions) => {
  return useQuery(
    [queryKey.student_department_all],
    () => getListAllDepartment(),
    {
      ...option,
    }
  ) as UseQueryResult<Awaited<ReturnType<typeof getListAllDepartment>>>;
};
