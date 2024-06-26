import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { useGetDetailRole, useUpdateRole } from "#service/action/role.action";
import {
  faBuildingWheat,
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
import { useEffect, useMemo } from "react";
import { toast } from "react-toastify";

export interface RoleDataUpdate {
  roleCode: string;
  roleName: string;
  roleId: number;
}

const ModalUpdateRole = ({
  isModalUpdateRoleOpen,
  setIsModalUpdateRoleOpen,
  dataUpdate,
}: {
  isModalUpdateRoleOpen: boolean;
  setIsModalUpdateRoleOpen: (value: boolean) => void;
  dataUpdate: any;
}) => {
  const [form] = Form.useForm();

  const { data: dataRoleFetching } = useGetDetailRole(dataUpdate?.id);

  const { mutateAsync: updateRole, isLoading: loading } = useUpdateRole();

  const dataRole = useMemo(() => {
    return dataRoleFetching?.data;
  }, [dataRoleFetching]);

  const handleCloseUpdateRoleOpen = () => {
    setIsModalUpdateRoleOpen(false);
    form.resetFields();
  };

  useEffect(() => {
    if (dataRole) {
      form.setFieldsValue({
        roleCode: dataUpdate?.roleCode,
        roleName: dataUpdate?.roleName,
        roleId: Number(dataUpdate?.roleId),
      });
    }
  }, [
    dataRole,
    dataUpdate?.roleCode,
    dataUpdate?.roleId,
    dataUpdate?.roleName,
    form,
  ]);

  const handleUpdateRole = async () => {
    try {
      const formValue = await form.getFieldsValue();
      const res = await updateRole({
        id: dataUpdate.id,
        role: {
          roleCode: formValue?.roleCode.trim(),
          roleName: formValue?.roleName.trim(),
        },
      });
      if (res?.success) {
        toast.success(res?.message);
        handleCloseUpdateRoleOpen();
      } else {
        toast.error(res?.message || "Chỉnh sửa vai trò thất bại");
      }
    } catch (error) {
      toast.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  };

  return (
    <>
      {loading && <MagnifyingGlassLoading />}
      <Modal
        title={
          <h3 className='flex gap-2 items-center text-[#052C65]'>
            <FontAwesomeIcon icon={faBuildingWheat} />
            Chỉnh sửa vai trò
          </h3>
        }
        open={isModalUpdateRoleOpen}
        onCancel={handleCloseUpdateRoleOpen}
        width='50vw'
        footer={<></>}
        forceRender
      >
        <div className='shadow p-4 rounded-3 mt-4'>
          <Row gutter={16}>
            <Col flex={6}>
              <Form form={form} layout='vertical'>
                <Form.Item name='roleCode' label='Mã: ' required={true}>
                  <Input size='large' placeholder='Nhập mã vai trò' />
                </Form.Item>
                <Form.Item name='roleName' label='Tên: ' required={true}>
                  <Input size='large' placeholder='Nhập tên vai trò' />
                </Form.Item>
                <Form.Item>
                  <Col>
                    <Tooltip title='' color='#052C65'>
                      <div>
                        <Popconfirm
                          placement='top'
                          title='Thông báo'
                          description='Bạn có muốn chỉnh sửa vai trò không?'
                          okText='Có'
                          cancelText='Không'
                          onConfirm={() => handleUpdateRole()}
                        >
                          <Button
                            icon={
                              <FontAwesomeIcon icon={faPenToSquare} size='lg' />
                            }
                            size='large'
                            type='primary'
                            style={{
                              backgroundColor: "#052C65",
                              width: "100%",
                            }}
                          >
                            Xác nhận
                          </Button>
                        </Popconfirm>
                      </div>
                    </Tooltip>
                  </Col>
                </Form.Item>
              </Form>
            </Col>
          </Row>
        </div>
      </Modal>
    </>
  );
};

export default ModalUpdateRole;
