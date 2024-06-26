import { Form, Input } from "antd";
import { SearchMajorCampus } from "../hook/useMajorCampus";
import { memo } from "react";

const MajorCampusFilter = ({
  handleFilter,
  className,
}: {
  handleFilter: (value: SearchMajorCampus) => void;
  className?: string;
}) => {
  return (
    <Form layout='vertical' className='flex justify-around'>
      <Form.Item label='Mã Chuyên Ngành' className='w-[50%]'>
        <Input
          className={className}
          placeholder='Tìm kiếm theo mã chuyên ngành...'
          onChange={(e) => handleFilter({ ...{ majorCode: e.target.value } })}
        />
      </Form.Item>
      <Form.Item label='Mã Trưởng Môn' className='w-[50%] ml-2'>
        <Input
          className={className}
          placeholder='Tìm kiếm theo mã trưởng môn...'
          onChange={(e) =>
            handleFilter({ ...{ headMajorCode: e.target.value } })
          }
        />
      </Form.Item>
    </Form>
  );
};

export default memo(MajorCampusFilter);
