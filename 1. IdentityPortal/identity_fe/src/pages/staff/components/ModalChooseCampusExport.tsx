import { useGetAllCampus } from "#service/action/staff.action";
import { Button, Modal, Select } from "antd";
import { useMemo, useState } from "react";

const ModalChooseCampusExport = ({
  isOpen,
  onClose,
  onConfirm,
}: {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: (campusCode: string) => void;
}) => {
  const [campusCode, setCampusCode] = useState<string>();

  const { data: campusDataFetch } = useGetAllCampus();

  const campusData = useMemo(() => {
    return campusDataFetch?.data?.map((campus) => ({
      value: campus.code,
      label: campus.name,
    }));
  }, [campusDataFetch?.data]);

  return (
    <Modal
      centered
      open={isOpen}
      onCancel={onClose}
      title='Cơ sở'
      footer={
        <div className='flex justify-end gap-3'>
          <Button
            onClick={onClose}
            className='text-white bg-orange-500'
            type='primary'
          >
            Hủy
          </Button>
          <Button
            type='primary'
            className='text-white bg-[#052C65]'
            onClick={() => onConfirm(campusCode)}
          >
            Xác nhận
          </Button>
        </div>
      }
      destroyOnClose
      forceRender
    >
      <div className='flex gap-3'>
        <Select
          placeholder='Chọn cơ sở'
          options={campusData}
          className='w-full'
          showSearch
          filterOption={(input, option) =>
            option?.label.toLowerCase().includes(input.toLowerCase())
          }
          allowClear
          onChange={(value: string) => setCampusCode(value)}
        />
      </div>
    </Modal>
  );
};

export default ModalChooseCampusExport;
