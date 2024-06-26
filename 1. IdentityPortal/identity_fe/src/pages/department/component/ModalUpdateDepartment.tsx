import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { useUpdateDepartment } from "#service/action/department.action";
import {
  faBookOpenReader,
  faPenToSquare,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  Button,
  Col,
  Form,
  Input,
  Modal,
  Popconfirm,
  Row,
  Tooltip,
} from "antd";
import { useCallback } from "react";
import { toast } from "react-toastify";

const ModalUpdateDepartment = ({
  isModalUpdateBoMonOpen,
  setIsModalUpdateBoMonOpen,
  dataUpdate,
  setDataUpdate,
}: {
  isModalUpdateBoMonOpen: boolean;
  setIsModalUpdateBoMonOpen: (isModalUpdateBoMonOpen: boolean) => void;
  dataUpdate: any;
  setDataUpdate: (dataUpdate: any) => void;
}) => {
  const { mutateAsync: updateDepartment, isLoading: loading } =
    useUpdateDepartment();

  const handleCloseUpdateBoMonOpen = useCallback(() => {
    setIsModalUpdateBoMonOpen(false);
  }, [setIsModalUpdateBoMonOpen]);

  const onChangeInputUpdateDepartment = useCallback(
    (e: any) => {
      const { name, value } = e.target;
      setDataUpdate({ ...dataUpdate, [name]: value });
    },
    [setDataUpdate, dataUpdate]
  );

  const handleSubmitUpdate = useCallback(async () => {
    try {
      if (
        dataUpdate.departmentName.trim() === "" ||
        dataUpdate.departmentName.trim() === null ||
        dataUpdate.departmentCode.trim() === "" ||
        dataUpdate.departmentCode.trim() === null
      ) {
        toast.warning("Vui lòng điền đầy đủ các trường (không rỗng)");
        return;
      }
      const res = await updateDepartment({
        id: dataUpdate.departmentId,
        data: dataUpdate,
      });
      if (res?.success) {
        toast.success(res?.message);
        handleCloseUpdateBoMonOpen();
      } else {
        toast.error(res?.message);
      }
    } catch (error) {
      toast.error(error?.response?.data?.message || "Cập nhật bộ môn thất bại");
    }
  }, [dataUpdate, updateDepartment, handleCloseUpdateBoMonOpen]);

  return (
    <>
      {loading && <MagnifyingGlassLoading />}
      <Modal
        title={
          <h3 className='flex gap-2 items-center'>
            <FontAwesomeIcon
              icon={faBookOpenReader}
              className='text-xl text-[#052C65]'
            />
            <span className='text-xl text-[#052C65]'>Cập nhật bộ môn</span>
          </h3>
        }
        open={isModalUpdateBoMonOpen}
        onCancel={handleCloseUpdateBoMonOpen}
        width={"70vw"}
        footer={null}
      >
        <div className={"shadow-lg p-4 rounded-lg mt-4"}>
          <Row gutter={16}>
            <Col flex={6}>
              <Form.Item className={"pb-1"} label='Tên:'>
                <Input
                  className='mb-3'
                  size='large'
                  placeholder='Tên bộ môn'
                  onChange={onChangeInputUpdateDepartment}
                  type='text'
                  name='departmentName'
                  value={dataUpdate ? dataUpdate.departmentName : null}
                />
              </Form.Item>
              <Form.Item className={"pb-1"} label='Mã:'>
                <Input
                  className='mb-3'
                  size='large'
                  placeholder='Mã bộ môn'
                  onChange={onChangeInputUpdateDepartment}
                  type='text'
                  name='departmentCode'
                  value={dataUpdate ? dataUpdate.departmentCode : null}
                />
              </Form.Item>
            </Col>
          </Row>
          <Row className='flex justify-end'>
            <Tooltip title='Cập nhật tên chuyên ngành' color={"#052C65"}>
              <Popconfirm
                placement='top'
                title={"Thông báo"}
                description='Bạn có muốn cập nhật tên bộ môn không ?'
                okText='Có'
                cancelText='Không'
                onConfirm={handleSubmitUpdate}
              >
                <Button
                  icon={<FontAwesomeIcon icon={faPenToSquare} size='lg' />}
                  size={"large"}
                  type={"primary"}
                  className='update-button'
                />
              </Popconfirm>
            </Tooltip>
          </Row>
        </div>
      </Modal>
    </>
  );
};

export default ModalUpdateDepartment;
