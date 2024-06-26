import React, { useEffect, useState } from "react";
import {
  Modal,
  Button,
  Select,
  message,
  Form,
  Row,
  Col,
  Radio,
  Input,
} from "antd";
import "./style-modal-add-organizer.css";
import OREventDetailApi from "../../OREventDetailApi";
import { useParams } from "react-router-dom";

const { Option } = Select;

const ModalAddOrganizer = ({ visible, onCancel, loadOrganizers }) => {
  const { id } = useParams();
  const [role, setRole] = useState(0);
  const [meetingHour, setMeetingHour] = useState(0.1);
  const [organizerId, setOrganizerId] = useState("");
  const [listOrganizerNotInEvent, setListOrganizerNotInEvent] = useState([]);
  const [loadings, setLoadings] = useState([]);

  const handleChangeListOrganizer = (e) => {
    setOrganizerId(e);
  };

  useEffect(() => {
    loadDataOrganizerNotInEvent();

    return () => {
      setListOrganizerNotInEvent([]);
    };
  }, [visible]);

  const loadDataOrganizerNotInEvent = () => {
    OREventDetailApi.getOrganizersNotInEvent(id).then((response) => {
      //console.log({ response });
      setListOrganizerNotInEvent(response.data.data);
    });
  };

  const createEventOrganizer = () => {
    console.log("hỏi chấm fdsgfrdgvrdgvrdg");
    if (meetingHour <= 0) {
      message.success("Sai định dạng meetingHour");
      return;
    }
    let data = {
      organizerId: organizerId,
      eventRole: role,
      eventId: id,
      meetingHour: meetingHour,
    };
    setLoadings((prevLoadings) => {
      const newLoadings = [...prevLoadings];
      newLoadings[1] = true;
      return newLoadings;
    });

    if (organizerId) {
      OREventDetailApi.createEventOrganizer(data).then(
        (response) => {
          // window.location.href = "/organizer-management/event-detail/" + id;
          onCancel();
          setOrganizerId("");
          loadOrganizers();
          message.success("Thêm thành công");
        },
        (error) => {
          message.warning(error.response.data.message);
        }
      );
      setTimeout(() => {
        setLoadings((prevLoadings) => {
          const newLoadings = [...prevLoadings];
          newLoadings[1] = false;
          return newLoadings;
        });
      }, 2000);
    } else {
      setTimeout(() => {
        setLoadings((prevLoadings) => {
          const newLoadings = [...prevLoadings];
          newLoadings[1] = false;
          return newLoadings;
        });
      }, 2000);
      message.warning("Vui lòng chọn người tổ chức");
    }
  };

  return (
    <>
      <Modal
        visible={visible}
        onCancel={onCancel}
        width="55%"
        footer={null}
        className="modal_show_detail"
      >
        <div>
          <div
            style={{
              textAlign: "center",
              borderBottom: "1px solid #ccc",
              paddingBottom: "10px",
            }}
          >
            <h2>Thêm người tổ chức</h2>
          </div>
          <div
            style={{
              margin: "15px 0",
              borderBottom: "1px solid #ccc",
              paddingBottom: "15px",
            }}
          >
            <Form labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
              <Row gutter={16}>
                <Col span={10}>
                  <Form.Item label="Người tổ chức">
                    <Select
                      placeholder="Chọn người tổ chức"
                      style={{ width: "100%" }}
                      value={organizerId}
                      onChange={handleChangeListOrganizer}
                      optionLabelProp="label"
                      showSearch
                      filterOption={(input, option) =>
                        (option?.label ?? "")
                          .toLowerCase()
                          .includes(input.toLowerCase())
                      }
                      options={listOrganizerNotInEvent?.map((item) => ({
                        value: item.id,
                        label: item.emailFPT,
                      }))}
                    />
                    {/* {listOrganizerNotInEvent.map((item) => (
                        <Option
                          key={item.id}
                          value={item.id}
                          label={`${item.name} (${item.emailFPT})`}
                        />
                      ))}
                    </Select> */}
                  </Form.Item>
                </Col>
                <Col span={6}>
                  <Form.Item label="Số giờ F">
                    <Input
                      name="meetingHour"
                      value={meetingHour}
                      onChange={(e) => setMeetingHour(e.target.value)}
                      type="number"
                      defaultValue="0.1"
                      min="0.1"
                      step="0.1"
                    />
                  </Form.Item>
                </Col>
                <Col span={6}>
                  <Form.Item label="Vai trò">
                    <Radio.Group
                      name="role"
                      value={role}
                      onChange={(e) => setRole(e.target.value)}
                    >
                      <Radio value={0}>Chủ trì</Radio>
                      <Radio value={1}>Đồng chủ trì</Radio>
                    </Radio.Group>
                  </Form.Item>
                </Col>
              </Row>
            </Form>
          </div>
          <div style={{ textAlign: "right", marginTop: "15px" }}>
            <Button
              style={{
                marginRight: "5px",
                backgroundColor: "#3d8be3",
                color: "white",
              }}
              loading={loadings[1]}
              onClick={() => createEventOrganizer()}
            >
              Thêm
            </Button>
            <Button
              style={{ backgroundColor: "red", color: "white" }}
              onClick={onCancel}
            >
              Hủy
            </Button>
          </div>
        </div>
      </Modal>
    </>
  );
};
export default ModalAddOrganizer;
