import useDebounce from "#hooks/useDebounce";
import { useGetAllSemesters } from "#service/action/semester.action";
import { SemesterPaginationParams } from "#service/api/semester.api";
import { useMemo } from "react";
import { useImmer } from "use-immer";

export const useSemester = () => {
  const [paginationParams, setPaginationParams] =
    useImmer<SemesterPaginationParams>({
      page: 1,
      size: 10,
    });

  const [searchValue, setSearchValue] = useImmer<SemesterPaginationParams>({
    ...paginationParams,
    semesterName: undefined,
    startTime: undefined,
    endTime: undefined,
    startTimeFirstBlock: undefined,
    endTimeFirstBlock: undefined,
    startTimeSecondBlock: undefined,
    endTimeSecondBlock: undefined,
    page: 1,
  });

  const searchValueDebounce = useDebounce(searchValue, 500);

  const {
    data: dataSemesterResponse,
    isLoading,
    isError,
  } = useGetAllSemesters({
    ...paginationParams,
    ...searchValueDebounce,
  });

  const semesters = useMemo(() => {
    return (
      dataSemesterResponse?.data.data.map((semester) => ({
        ...semester,
        key: semester.id,
      })) || []
    );
  }, [dataSemesterResponse]);

  const totalPages = useMemo(() => {
    return dataSemesterResponse?.data.totalPages || 0;
  }, [dataSemesterResponse]);

  const handleTableChange = (pagination, filters, sorter) => {
    setPaginationParams((draft) => {
      draft.page = pagination.current;
      draft.size = pagination.pageSize;
      draft.sortBy = sorter.field;
      draft.orderBy = sorter.order === "ascend" ? "asc" : "desc";
    });
  };

  return {
    semesters,
    totalPages,
    isLoading,
    isError,
    setPaginationParams,
    paginationParams,
    setSearchValue,
    handleTableChange,
  };
};
