import { PERMISSIONS } from "#constant/index";
import { RootState } from "#context/redux/store";
import { useGetAllCampus } from "#service/action/staff.action";
import { Form, Input, Select } from "antd";
import { memo, useMemo } from "react";
import { useSelector } from "react-redux";

export type SearchValue = {
  staffCode?: string | undefined;
  staffName?: string | undefined;
};

const StaffFilter = ({
  onSearch,
}: {
  onSearch: React.Dispatch<React.SetStateAction<SearchValue>>;
}) => {
  const { permissions } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const { data: campusDataFetch } = useGetAllCampus();

  const campusData = useMemo(() => {
    return campusDataFetch?.data?.map((campus) => ({
      value: campus.code,
      label: campus.name,
    }));
  }, [campusDataFetch?.data]);

  return (
    <Form
      layout='vertical'
      className={`grid ${
        permissions?.includes(PERMISSIONS.BAN_DAO_TAO_HO)
          ? "grid-cols-3"
          : "grid-cols-2"
      } gap-3`}
    >
      <Form.Item label='Mã nhân viên'>
        <Input
          type='text'
          placeholder='Tìm kiếm nhân viên theo Mã nhân viên'
          className='mr-2'
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            onSearch((prev: SearchValue) => ({
              ...prev,
              staffCode: e.target.value,
            }))
          }
        />
      </Form.Item>
      <Form.Item label='Tên nhân viên'>
        <Input
          type='text'
          placeholder='Tìm kiếm nhân viên theo Tên nhân viên'
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            onSearch((prev: SearchValue) => ({
              ...prev,
              staffName: e.target.value,
            }))
          }
        />
      </Form.Item>
      {permissions?.includes(PERMISSIONS.BAN_DAO_TAO_HO) && (
        <Form.Item label='Cơ sở'>
          <Select
            placeholder='Chọn cơ sở'
            options={campusData}
            onChange={(value: string) =>
              onSearch((prev: SearchValue) => ({
                ...prev,
                campusCode: value,
              }))
            }
            showSearch
            allowClear
            filterOption={(input, option) =>
              option?.label?.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
          />
        </Form.Item>
      )}
    </Form>
  );
};

export default memo(StaffFilter);
