import { useGetStaffInfoSearch } from "#service/action/decentralization.action";
import { debounce } from "lodash";
import { useMemo, useState } from "react";

export const useSearchOptions = () => {
  const [searchValue, setSearchValue] = useState<string>("");

  const handleSearchValue = debounce((value: string) => {
    setSearchValue(value);
  }, 500);

  const { data: staffInfoSearch, isFetched: isLoadingStaffInfoSearch } =
    useGetStaffInfoSearch(searchValue);

  const dataStaffSearch = useMemo(() => {
    return staffInfoSearch?.data?.map((item) => ({
      key: item.staffCode,
      value: item.staffCode,
      label: item.staffCode + " - " + item.staffName,
    }));
  }, [staffInfoSearch]);

  return {
    searchValue,
    handleSearchValue,
    dataStaffSearch,
    isLoadingStaffInfoSearch,
  };
};
