import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { HTTP_STATUS } from "#constant/index";
import { useUpdateCampus } from "#service/action/campus.action";
import { showToast } from "#utils/common.helper";
import {
  faBuildingCircleArrowRight,
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
import { ChangeEvent, memo } from "react";
import { CampusDetail } from "..";

export interface ModalUpdateCampusProps {
  isModalUpdateCoSoOpen: boolean;
  setIsModalUpdateCoSoOpen: (value: boolean) => void;
  dataUpdate: CampusDetail;
  setDataUpdate: (value: CampusDetail) => void;
}

const ModalUpdateCampus = ({
  isModalUpdateCoSoOpen,
  setIsModalUpdateCoSoOpen,
  dataUpdate,
  setDataUpdate,
}: ModalUpdateCampusProps) => {
  const { mutateAsync: updateCampus, isLoading: loadingUpdate } =
    useUpdateCampus();

  const handleValueChangeInput = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setDataUpdate({ ...dataUpdate, [name]: value });
  };

  const handleUpdateCampus = async () => {
    try {
      if (!dataUpdate.campusName || !dataUpdate.campusCode) {
        showToast("error", "Vui lòng nhập đủ thông tin");
        return;
      }
      const response = await updateCampus({
        data: {
          campusName: dataUpdate.campusName,
          campusCode: dataUpdate.campusCode,
        },
        id: dataUpdate.campusId,
      });
      if (response.status === HTTP_STATUS.OK) {
        showToast("success", response.message);
        setIsModalUpdateCoSoOpen(false);
      } else {
        showToast("error", response.message);
      }
    } catch (error) {
      if (error.code && error.code === "ERR_BAD_REQUEST") {
        showToast("error", error?.response?.data?.message);
      }
    }
  };

  return (
    <>
      {loadingUpdate && <MagnifyingGlassLoading />}
      <Modal
        title={
          <h3 className='flex gap-2 items-center text-2xl text-[#052C65] font-semibold'>
            <FontAwesomeIcon icon={faBuildingCircleArrowRight} />
            Cập nhật cơ sở
          </h3>
        }
        open={isModalUpdateCoSoOpen}
        onCancel={() => setIsModalUpdateCoSoOpen(false)}
        width='70vw'
        footer={<></>}
      >
        <Form layout='vertical'>
          <div className='shadow-lg p-4 rounded-lg mt-4'>
            <Row gutter={16}>
              <Col flex={6}>
                <Form.Item className={"pb-1"} label='Tên:'>
                  <Input
                    className='mb-3'
                    size='large'
                    placeholder='Tên cơ sở'
                    onChange={(e) => handleValueChangeInput(e)}
                    type='text'
                    name='campusName'
                    value={dataUpdate ? dataUpdate.campusName : null}
                    required
                  />
                </Form.Item>
                <Form.Item className={"pb-1"} label='Mã:'>
                  <Input
                    className='mb-3'
                    size='large'
                    placeholder='Mã cơ sở'
                    onChange={(e) => handleValueChangeInput(e)}
                    type='text'
                    name='campusCode'
                    value={dataUpdate ? dataUpdate.campusCode : null}
                    required
                  />
                </Form.Item>
              </Col>
            </Row>
            <Row className='flex justify-end'>
              <Tooltip title='Cập nhật tên cơ sở' color={"#052C65"}>
                <Popconfirm
                  placement='top'
                  title={"Thông báo"}
                  description={"Bạn có muốn cập nhật tên cơ sở không ?"}
                  okText='Có'
                  cancelText='Không'
                  onConfirm={handleUpdateCampus}
                >
                  <Button
                    icon={<FontAwesomeIcon icon={faPenToSquare} size='lg' />}
                    size={"large"}
                    type={"primary"}
                    className='bg-[#052C65]'
                  >
                    Xác nhận
                  </Button>
                </Popconfirm>
              </Tooltip>
            </Row>
          </div>
        </Form>
      </Modal>
    </>
  );
};

export default memo(ModalUpdateCampus);
