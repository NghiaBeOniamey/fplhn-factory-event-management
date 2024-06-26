import { Input } from "antd";
import { SearchMajor } from "../hook/useMajor";

const MajorFilter = ({
  handleFilter,
  className,
}: {
  handleFilter: (value: SearchMajor) => void;
  className?: string;
}) => {
  return (
    <div className='flex justify-between'>
      <Input
        placeholder='Tìm Kiếm Chuyên Ngành Theo Mã Chuyên Ngành...'
        onChange={(e) => handleFilter({ majorCode: e.target.value })}
        className={className + " mr-2"}
        allowClear
      />
      <Input
        placeholder='Tìm Kiếm Chuyên Ngành Theo Tên Chuyên Ngành...'
        onChange={(e) => handleFilter({ majorName: e.target.value })}
        className={className}
        allowClear
      />
    </div>
  );
};

export default MajorFilter;
