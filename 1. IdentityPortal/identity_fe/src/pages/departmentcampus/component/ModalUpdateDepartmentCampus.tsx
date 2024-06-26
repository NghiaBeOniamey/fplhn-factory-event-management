import { faBridgeWater } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Form, Modal, Popconfirm, Select } from "antd";
import { useEffect, useMemo, useState } from "react";
import { toast } from "react-toastify";
import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import {
  useGetListCampus,
  useListHeadDepartmentCampusApi,
  useUpdateDepartmentCampus,
} from "#service/action/department.action";

const ModalUpdateDepartmentCampus = ({
  isModalUpdateMonHocTheoCoSoOpen,
  setIsModalUpdateMonHocTheoCoSoOpen,
  departmentId,
  departmentName,
  dataUpdate,
}: {
  isModalUpdateMonHocTheoCoSoOpen: boolean;
  setIsModalUpdateMonHocTheoCoSoOpen: any;
  departmentId?: number;
  departmentName?: string;
  dataUpdate: any;
}) => {
  const [form] = Form.useForm();

  const [isButtonDisabled, setIsButtonDisabled] = useState(false);

  const { data: dataCampusFetching } = useGetListCampus();

  const { data: dataStaffFetching } = useListHeadDepartmentCampusApi();

  const { mutateAsync: updateDepartmentCampus, isLoading: loading } =
    useUpdateDepartmentCampus();

  const dataCampus = useMemo(() => {
    return (
      dataCampusFetching?.data.map((item) => {
        return {
          value: item.campusId,
          label: item.campusName,
          key: item.campusId,
        };
      }) ?? []
    );
  }, [dataCampusFetching]);

  const dataStaff = useMemo(() => {
    return (
      dataStaffFetching?.data.map((item) => {
        return {
          value: item.staffId,
          label: item.staffName + " - " + item.staffCode,
          key: item.staffId,
        };
      }) ?? []
    );
  }, [dataStaffFetching]);

  const handleCloseUpdateMonHocTheoCoSoOpen = () => {
    setIsModalUpdateMonHocTheoCoSoOpen(false);
    setIsButtonDisabled(false);
  };

  const handleSubmitUpdateBoMonTheoCoSo = async () => {
    try {
      const formValue = await form.getFieldsValue();
      const res = await updateDepartmentCampus({
        data: {
          // @ts-ignore
          departmentId: departmentId,
          campusId: dataUpdate.campusId,
          headDepartmentCampusId: formValue.headDepartmentCampusId,
        },
        id: dataUpdate.departmentCampusId,
      });
      console.log(res);

      if (res?.success) {
        toast.success(res?.message);
      } else {
        toast.error(res?.message);
      }
      form.resetFields();
      setIsButtonDisabled(false);
      setIsModalUpdateMonHocTheoCoSoOpen(false);
    } catch (error) {
      toast.error(error?.response?.data?.message || INTERNAL_SERVER_ERROR);
    }
  };

  const handleFieldsChange = (changedFields, allFields) => {
    const allFieldsFilled = Object.keys(allFields).every(
      (field) => !!allFields[field].value
    );
    setIsButtonDisabled(allFieldsFilled);
  };

  useEffect(() => {
    if (dataUpdate) {
      form.setFieldsValue({
        campusId: dataUpdate.campusId,
        headDepartmentCampusId: dataUpdate.headDepartmentCampusId,
      });
    }
  }, [dataUpdate, form]);

  return (
    <>
      {loading && <MagnifyingGlassLoading />}
      <Modal
        title={
          <h5 style={{ color: "#052C65" }} className='p-3'>
            <FontAwesomeIcon icon={faBridgeWater} size='2xl' />
            <span className='ml-2 text-lg'>
              Cập nhật bộ môn theo cơ sở cho bộ môn:
            </span>
            <span className='text-[red] text-lg ml-2'>{departmentName}</span>
          </h5>
        }
        open={isModalUpdateMonHocTheoCoSoOpen}
        onCancel={handleCloseUpdateMonHocTheoCoSoOpen}
        width={"40vw"}
        footer={[
          isButtonDisabled && (
            <Popconfirm
              placement='top'
              title={"Thông báo"}
              description={"Bạn có muốn cập nhật không ?"}
              okText='Có'
              cancelText='Không'
              onConfirm={() => handleSubmitUpdateBoMonTheoCoSo()}
            >
              <Button
                type='primary'
                className='bg-[#052c65] border-[#052c65] hover:bg-[#052c65] hover:border-[#052c65] m-2'
              >
                Xác nhận
              </Button>
            </Popconfirm>
          ),
        ]}
      >
        <Form
          className='mt-4'
          form={form}
          onFieldsChange={(changedFields, allFields) =>
            handleFieldsChange(changedFields, allFields)
          }
        >
          <Form.Item
            tooltip='Không cập nhật trường này'
            name='campusId'
            label='Chọn Cơ sở'
            wrapperCol={{ span: 24 }}
            labelCol={{ span: 24 }}
            validateTrigger={"onBlur"}
            rules={[
              {
                required: true,
                message: "Tên cơ sở không được để trống!",
              },
            ]}
          >
            <Select
              showSearch
              style={{
                width: "100%",
                backgroundColor: "#fff",
              }}
              size='large'
              placeholder='Tìm kiếm cơ sở'
              optionFilterProp='children'
              filterOption={(input, option) =>
                (option?.label ?? "").includes(input)
              }
              filterSort={(optionA, optionB) =>
                (optionA?.label ?? "")
                  .toLowerCase()
                  .localeCompare((optionB?.label ?? "").toLowerCase())
              }
              options={dataCampus ? dataCampus : []}
              disabled
            />
          </Form.Item>
          <Form.Item
            name='headDepartmentCampusId'
            label='Chọn chủ nhiệm bộ môn'
            wrapperCol={{ span: 24 }}
            labelCol={{ span: 24 }}
            validateTrigger={"onBlur"}
            rules={[
              {
                required: true,
                message: "Tên chủ nhiệm bộ môn không được để trống!",
              },
            ]}
          >
            <Select
              showSearch
              style={{
                width: "100%",
              }}
              size='large'
              placeholder='Tìm kiếm chủ nhiệm bộ môm'
              optionFilterProp='children'
              filterOption={(input, option) =>
                (option?.label ?? "").includes(input)
              }
              filterSort={(optionA, optionB) =>
                (optionA?.label ?? "")
                  .toLowerCase()
                  .localeCompare((optionB?.label ?? "").toLowerCase())
              }
              options={dataStaff ? dataStaff : []}
            />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default ModalUpdateDepartmentCampus;
