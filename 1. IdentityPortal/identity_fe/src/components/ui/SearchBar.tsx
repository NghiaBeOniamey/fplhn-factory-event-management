import { Row, Select } from "antd";
import { memo } from "react";
import { FaFilter } from "react-icons/fa";

export interface SearchBarProps {
  dataSearch: { value: number; label: string }[];
  contentSearch: string;
  placeholder: string;
  onChange: (value: number[]) => void;
  loading?: boolean;
}

const SearchBar = ({
  dataSearch,
  contentSearch,
  placeholder,
  onChange,
  loading,
}: SearchBarProps) => {
  return (
    <Row>
      <h3 className='p-4 flex items-center text-primary font-semibold'>
        <FaFilter size={25} color={"#052c65"} />
        <span className='ml-2 text-[#052c65] text-xl'>
          Tìm kiếm {contentSearch}
        </span>
      </h3>
      <Select
        loading={loading}
        size='large'
        mode='multiple'
        showSearch
        style={{
          width: "100%",
        }}
        placeholder={placeholder}
        optionFilterProp='children'
        filterOption={(input, option) => (option?.label ?? "").includes(input)}
        filterSort={(optionA, optionB) =>
          (optionA?.label ?? "")
            .toLowerCase()
            .localeCompare((optionB?.label ?? "").toLowerCase())
        }
        options={dataSearch ? dataSearch : []}
        onChange={(value) => onChange(value as number[])}
      />
    </Row>
  );
};

export default memo(SearchBar);
