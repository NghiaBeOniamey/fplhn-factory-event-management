import { faCircleInfo } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Height } from "@mui/icons-material";
import {
  Button,
  Popconfirm,
  Modal,
  Form,
  Input,
  message,
  Icon,
  Checkbox,
} from "antd";
import { useState } from "react";
import "./ModalEventReorganization.css";
import OREventDetailApi from "../../OREventDetailApi";
import { useNavigate, useParams } from "react-router-dom";

const CheckboxGroup = Checkbox.Group;

const ModalEventReorganization = ({
  visible,
  onCancel,
  detailEvent,
  isRole,
  loadAllData,
}) => {
  const [checkedList, setCheckedList] = useState([]);
  const [indeterminate, setIndeterminate] = useState(false);
  const [checkAll, setCheckAll] = useState(false);
  const { id } = useParams();

  const plainOptions = [
    { label: "Tên sự kiện", value: 1 },
    { label: "Thể loại", value: 4 },
    { label: "Mô tả", value: 6 },
    { label: "Sự kiện cho", value: 2 },
    { label: "Bộ môn", value: 3 },
    { label: "Đối tượng hướng đến", value: 5 },
    { label: "Danh sách người tổ chức", value: 7 },
    { label: "Danh sách agenda", value: 8 },
  ];

  const onChange = (checkedValues) => {
    setCheckedList(checkedValues);
    setIndeterminate(
      !!checkedValues.length && checkedValues.length < plainOptions.length
    );
    setCheckAll(checkedValues.length === plainOptions.length);
  };

  const onCheckAllChange = (e) => {
    setCheckedList(
      e.target.checked ? plainOptions.map((option) => option.value) : []
    );
    setIndeterminate(false);
    setCheckAll(e.target.checked);
  };

  const navigate = useNavigate();
  const handleGetChecked = () => {
    if (checkedList.length === 0) {
      message.error("Bạn cần chọn ít nhất một trường thông tin của sự kiện");
    }
    if (!checkedList.includes(7) && checkedList.includes(8)) {
      message.error(
        "Không thể lấy Danh sách agenda khi chưa chọn Danh sách người tổ chức"
      );
    } else {
      OREventDetailApi.eventReorganization(id, checkedList)
        .then((response) => {
          message.success("Tái tổ chức thành công");
          onCancel();
          navigate(
            "/organizer-management/event-detail/" + response.data.data.id
          );
        })
        .catch((error) => {});
    }
  };

  return (
    <>
      <Modal
        title={
          <div>
            <FontAwesomeIcon icon={faCircleInfo} style={{ color: "#172b4d" }} />{" "}
            Thông tin sự kiện
          </div>
        }
        visible={visible}
        onCancel={onCancel}
        footer={null}
        width={"50%"}
      >
        <hr />
        <div
          style={{
            backgroundColor: "#e3e3e3",
            textAlign: "center",
            borderRadius: "15px",
            marginTop: "5px",
          }}
        >
          <p style={{ lineHeight: 3 }}>
            Chọn những thông tin muốn sử dụng của sự kiện
          </p>
        </div>
        <div style={{ borderBottom: "1px solid #E9E9E9", marginTop: "10px" }}>
          <Checkbox
            indeterminate={indeterminate}
            onChange={onCheckAllChange}
            checked={checkAll}
          >
            Chọn tất cả
          </Checkbox>
        </div>
        <br />
        <div>
          <CheckboxGroup
            style={{ marginLeft: "30px" }}
            options={plainOptions}
            value={checkedList}
            onChange={onChange}
          />
        </div>
        <br />
        <div style={{ textAlign: "right" }}>
          <div style={{ paddingTop: "15px" }}>
            <Popconfirm
              title="Bạn có chắc muốn tái tổ chức sự kiện này không?"
              onConfirm={() => {
                handleGetChecked();
              }}
              okText="Có"
              cancelText="Không"
            >
              <Button
                style={{
                  marginRight: "5px",
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
              >
                Lưu
              </Button>
            </Popconfirm>

            <Button
              style={{
                backgroundColor: "red",
                color: "white",
              }}
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
export default ModalEventReorganization;
