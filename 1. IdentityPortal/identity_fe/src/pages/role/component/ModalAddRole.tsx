import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { useCreateRole } from "#service/action/role.action";
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
import { Dispatch, SetStateAction } from "react";
import { toast } from "react-toastify";

const ModalAddRole = ({
  isModalAddRoleOpen,
  setIsModalAddRoleOpen,
}: {
  isModalAddRoleOpen: boolean;
  setIsModalAddRoleOpen: Dispatch<SetStateAction<boolean>>;
}) => {
  const [form] = Form.useForm();

  const { mutateAsync: createRole, isLoading: loading } = useCreateRole();

  const handleCloseAddRoleOpen = () => {
    setIsModalAddRoleOpen(false);
    form.resetFields();
  };

  const handleCreateRole = async () => {
    try {
      const formValue = await form.getFieldsValue();
      const res = await createRole({
        roleCode: formValue?.roleCode,
        roleName: formValue?.roleName,
      });
      if (res?.success) {
        toast.success(res?.message);
        handleCloseAddRoleOpen();
      } else {
        toast.error(res?.message || "Thêm vai trò thất bại");
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
            Thêm vai trò
          </h3>
        }
        open={isModalAddRoleOpen}
        onCancel={handleCloseAddRoleOpen}
        width='50vw'
        footer={<></>}
      >
        <div className='shadow p-4 rounded-3 mt-4'>
          <Row gutter={16}>
            <Col flex={6}>
              <Form form={form} layout='vertical' initialValues={{}}>
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
                          description='Bạn có muốn thêm vai trò mới không?'
                          okText='Có'
                          cancelText='Không'
                          onConfirm={() => handleCreateRole()}
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
                            Thêm vai trò
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

export default ModalAddRole;
