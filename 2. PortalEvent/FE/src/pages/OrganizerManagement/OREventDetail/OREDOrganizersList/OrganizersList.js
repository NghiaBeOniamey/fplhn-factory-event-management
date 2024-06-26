import React, { useEffect, useRef, useState } from "react";
import { Button, Input, Popconfirm, Select, Space, Table, Tooltip, message } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faList,
  faPencilAlt,
  faPlus,
  faTrash,
} from "@fortawesome/free-solid-svg-icons";
import OREventDetailApi from "../../OREventDetail/OREventDetailApi";
import { useParams } from "react-router-dom";
import { DeleteOutlined } from "@ant-design/icons";
import ModalAddOrganizer from "./modal-add-organizer/ModalAddOrganizer";
import { organizerCurent } from "../../../../helper/UserCurrent";
import ModalUpdateOrganizer from "./modal-update-organizer/ModalUpdateOrganizer";
import { getCurrentUser } from "../../../../utils/Common";

const OrganizersList = ({
  isDisable,
  checkRole,
  checkRoleCoHost,
  isRoleHost,
  isRoleCoHost,
  loadOrganizers,
}) => {
  const [listOrganizer, setListOrganizer] = useState([]);
  const [listAgenda, setListAgenda] = useState([]);
  let [organizer, setOrganizer] = useState({});
  const [loadings, setLoadings] = useState([]);
  const { id } = useParams();
  // Tạo một tham chiếu đến thẻ input
  const fileInputRef = useRef(null);

  const [loadingOrganizers, setloadingOrganizers] = useState([]);

  useEffect(() => {
    loadOrganizersByIdEvent();
    loadDataAgenda();
  }, [id]);

  // Lấy danh sách người tổ chức
  const loadOrganizersByIdEvent = () => {
    OREventDetailApi.getOrganizersByIdEvent(id)
      .then((res) => {
        setListOrganizer(res.data.data);
        checkRole();
        checkRoleCoHost();
      })
      .catch((err) => {
        message.error(
          "Lỗi hệ thống. Không thể lấy danh sách người tổ chức của sự kiện"
        );
        //  //console.log(err);
      });
  };

  // Delete người tổ chức
  const deleteEventOrganizer = (idEventOrganizer, organizerId) => {
    let data = {
      id: idEventOrganizer,
      organizerId: organizerId,
      eventId: id,
      idUserCurrent: organizerCurent.id,
    };
    OREventDetailApi.deleteOrganizer(data)
      .then((res) => {
        let updateListOrganizer = listOrganizer.filter(
          (record) => record.id !== res.data.data
        );
        setListOrganizer(updateListOrganizer);
        const user = getCurrentUser();
        if (user.userName === data.organizerId) {
          window.location.reload();
        }
        message.success("Xóa thành công");
      })
      .catch((err) => {
        message.warning(err.response.data.message);
      });
  };

  // Lấy danh sách agenda
  const loadDataAgenda = () => {
    OREventDetailApi.getAgendasByIdEvent(id)
      .then((res) => {
        setListAgenda(res.data.data);
      })
      .catch((err) => {
        message.error(
          "Lỗi hệ thống. Không thể lấy danh sách agenda của sự kiện"
        );
      });
  };

  // Đóng / mở của modal add người tổ chức
  const [isModalAddOrganizer, setIsModalAddOrganizer] = useState(false);
  const openModalAddOrganizer = () => {
    setIsModalAddOrganizer(true);
  };
  const onCancelModalAddOrganizer = () => {
    setIsModalAddOrganizer(false);
  };

  // Đóng / mở của modal update người tổ chức
  const [isModalUpdateOrganizer, setIsModalUpdateOrganizer] = useState(false);
  const openModalUpdateOrganizer = (record) => {
    setOrganizer(record);
    setIsModalUpdateOrganizer(true);
  };
  const onCancelModalUpdateOrganizer = () => {
    loadOrganizersByIdEvent();
    setIsModalUpdateOrganizer(false);
  };

  // Các cột của bảng người tổ chức
  const columns = [
    {
      title: "STT",
      dataIndex: "recordNumber",
      key: "recordNumber",
      render: (text, record, index) => <span>{index + 1}</span>,
    },
    {
      title: "Họ tên",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Tên tài khoản",
      dataIndex: "username",
      key: "username",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Vai trò",
      dataIndex: "eventRole",
      key: "eventRole",
      render: (text, record) => (
        <Space>
          {record.eventRole === 0 && <span>Chủ trì</span>}
          {record.eventRole === 1 && <span>Đồng chủ trì</span>}
        </Space>
      ),
    },
    {
      title: "Số giờ F",
      dataIndex: "meetingHour",
      key: "meetingHour",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) =>
        (isRoleHost) && (
          <Space size="middle">
            <FontAwesomeIcon
              className="custom-icon"
              icon={faPencilAlt}
              style={{ marginRight: "15px" }}
              onClick={() => {
                openModalUpdateOrganizer(record);
              }}
            />
            {/* {record.eventRole !== 0 && ( */}
            <Popconfirm
              title="Bạn chắc chắn muốn xóa người tổ chức này không?"
              onConfirm={() => {
                deleteEventOrganizer(record.id, record.organizerId);
              }}
              okText="Có"
              cancelText="Không"
            >
              <Button type="link" icon={<DeleteOutlined />} />
            </Popconfirm>
            {/* )} */}
          </Space>
        ),
    },
  ];
  const dynamicColumns = isDisable
    ? columns.filter((column) => column.key !== "actions")
    : columns;

  // Thêm dòng trong bảng agenda
  const handleAddRow = () => {
    let randomNumber = "";
    let check = true;
    while (check) {
      randomNumber = Math.floor(Math.random() * 1000) + 1 + "";
      // eslint-disable-next-line no-loop-func
      let obj = listAgenda.find((item) => {
        return item.index === randomNumber;
      });
      if (typeof obj === "undefined") {
        check = false;
      }
    }
    if (listAgenda.length < 1) {
      const newRow = {
        index: randomNumber,
        id: "",
        eventId: id,
        organizerId: "",
        key: listAgenda.length,
        startTime: null,
        endTime: null,
        description: "",
      };
      setListAgenda([...listAgenda, newRow]);
    } else {
      const newRow = {
        index: randomNumber,
        id: "",
        eventId: id,
        organizerId: "",
        key: listAgenda.length,
        startTime: listAgenda[listAgenda.length - 1].endTime,
        endTime: null,
        description: "",
      };
      setListAgenda([...listAgenda, newRow]);
    }
  };

  // Bảng Agenda
  const handleInputChange = (e, key, dataIndex) => {
    const updatedData = [...listAgenda];
    const record = updatedData.find((item) => item.index === key);
    if (record) {
      record[dataIndex] = e.target.value;
      setListAgenda(updatedData);
    }
  };

  const handleSelectChange = (e, key, dataIndex) => {
    const updatedData = [...listAgenda];
    const record = updatedData.find((item) => item.index === key);
    if (record) {
      record[dataIndex] = e;
      setListAgenda(updatedData);
    }
  };

  const columnsAgenda = [
    {
      title: "STT",
      dataIndex: "index",
      key: "index",
      render: (text, record, index) => <span>{index + 1}</span>,
      width: "3%",
    },
    {
      title:
        <Tooltip title="vd: 00:00">
          Thời gian bắt đầu
        </Tooltip>,
      dataIndex: "startTime",
      key: "startTime",
      width: "15%",
      editable: true,
      render: (text, record, index) => (
        <Input
          readOnly={isDisable ? true : false}
          value={text}
          onChange={(e) => handleInputChange(e, record.index, "startTime")}
        />
      ),
    },
    {
      title:
        <Tooltip title="vd: 00:00">
          Thời gian kết thúc
        </Tooltip>,
      dataIndex: "endTime",
      key: "endTime",
      width: "15%",
      editable: true,
      render: (text, record, index) => (
        <Input
          readOnly={isDisable ? true : false}
          value={text}
          onChange={(e) => handleInputChange(e, record.index, "endTime")}
        />
      ),
    },
    {
      title: "Mô tả",
      dataIndex: "description",
      key: "description",
      width: "25%",
      render: (text, record, index) => (
        <Input
          readOnly={isDisable ? true : false}
          value={text}
          onChange={(e) => handleInputChange(e, record.index, "description")}
        />
      ),
    },
    {
      title: "Người phụ trách",
      dataIndex: "organizerId",
      key: "organizerId",
      width: "20%",
      render: (text, record, index) => (
        <Select
          style={{ width: "100%", pointerEvents: isDisable ? "none" : "" }}
          value={text}
          options={listOrganizer?.map((organizer) => ({
            value: organizer.organizerId,
            label: organizer.name,
          }))}
          onChange={(e) => handleSelectChange(e, record.index, "organizerId")}
          placeholder="Select Item..."
          maxTagCount="responsive"
          showSearch
          filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
        />
      ),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record, index) => (
        <Space size="middle">
          <Popconfirm
            title="Bạn chắc chắn muốn xóa agenda này không?"
            onConfirm={() => {
              deleteAgenda(record.id, record.index);
            }}
            okText="Có"
            cancelText="Không"
          >
            <FontAwesomeIcon icon={faTrash} className="custom-icon" />
          </Popconfirm>

          {index + 1 === listAgenda.length && (
            <FontAwesomeIcon
              icon={faPlus}
              className="custom-icon"
              style={{ marginRight: "15px" }}
              onClick={() => {
                handleAddRow();
              }}
            />
          )}
        </Space>
      ),
      width: "10%",
    },
  ];

  const dynamicColumnsAgenda = isDisable
    ? columnsAgenda.filter((column) => column.key !== "actions")
    : columnsAgenda;
  // End Bảng Agenda

  function addLeadingZero(number) {
    return number < 10 ? "0" + number : number;
  }

  // Lưu danh sách agenda
  const saveListAgenda = () => {
    let hasEmptyFields = false;
    setLoadings((prevLoadings) => {
      const newLoadings = [...prevLoadings];
      newLoadings[1] = true;
      return newLoadings;
    });

    if (listAgenda.length === 0) {
      message.error("Vui lòng nhập thông tin agenda.");
      setTimeout(() => {
        setLoadings((prevLoadings) => {
          const newLoadings = [...prevLoadings];
          newLoadings[1] = false;
          return newLoadings;
        });
      }, 2000);
      return;
    }

    const REGEX = /^([0-1]?[0-9]|2[0-3]):([0-5][0-9])$/;

    const listAgendaNew = listAgenda.map((item, index) => {
      const newItem = {
        id: item.id,
        eventId: item.eventId,
        organizerId: item.organizerId,
        startTime: item.startTime,
        endTime: item.endTime,
        description: item.description ? item.description.trim() : "",
      };

      if (newItem.startTime == null || newItem.startTime === "") {
        message.error(
          `Vui lòng nhập thời gian bắt đầu cho agenda ở dòng ${index + 1}.`
        );
        hasEmptyFields = true;
      } else {
        const startTimeParts = newItem.startTime.split(":");
        newItem.startTime =
          addLeadingZero(parseInt(startTimeParts[0])) + ":" + startTimeParts[1];
        if (!REGEX.test(newItem.startTime)) {
          message.error(
            `Thời gian bắt đầu cho agenda ở dòng ${index + 1} không hợp lệ.`
          );
          hasEmptyFields = true;
        }
      }

      if (newItem.endTime == null || newItem.endTime === "") {
        message.error(
          `Vui lòng nhập thời gian kết thúc cho agenda ở dòng ${index + 1}.`
        );
        hasEmptyFields = true;
      } else {
        const endTimeParts = newItem.endTime.split(":");
        newItem.endTime =
          addLeadingZero(parseInt(endTimeParts[0])) + ":" + endTimeParts[1];
        if (!REGEX.test(newItem.endTime)) {
          message.error(
            `Thời gian kết thúc cho agenda ở dòng ${index + 1} không hợp lệ.`
          );
          hasEmptyFields = true;
        }
      }

      if (newItem.description == null || newItem.description === "") {
        message.error(`Vui lòng nhập mô tả cho agenda ở dòng ${index + 1}.`);
        hasEmptyFields = true;
      } else if (newItem.description.length > 254) {
        message.error(`Mô tả cho agenda ở dòng ${index + 1} sai định dạng.`);
        hasEmptyFields = true;
      }

      if (newItem.organizerId == null || newItem.organizerId === "") {
        message.error(
          `Vui lòng chọn người phụ trách cho agenda ở dòng ${index + 1}.`
        );
        hasEmptyFields = true;
      }
      return newItem;
    });

    if (hasEmptyFields) {
      setTimeout(() => {
        setLoadings((prevLoadings) => {
          const newLoadings = [...prevLoadings];
          newLoadings[1] = false;
          return newLoadings;
        });
      }, 2000);
      return;
    }

    OREventDetailApi.saveListAgenda(listAgendaNew)
      .then((res) => {
        message.success("Lưu thông tin thành công");
        setListAgenda([]);
        loadDataAgenda();
        setTimeout(() => {
          setLoadings((prevLoadings) => {
            const newLoadings = [...prevLoadings];
            newLoadings[1] = false;
            return newLoadings;
          });
        }, 2000);
      })
      .catch((err) => {
        setTimeout(() => {
          setLoadings((prevLoadings) => {
            const newLoadings = [...prevLoadings];
            newLoadings[1] = false;
            return newLoadings;
          });
        }, 2000);
        message.warning(err.response.data.message);
      });
  };

  // Delete agenda
  const deleteAgenda = (id, index) => {
    if (id === "") {
      let updateListAgenda = listAgenda.filter(
        (record) => record.index !== index
      );
      setListAgenda(updateListAgenda);
      message.success("Xóa thành công");
    } else {
      OREventDetailApi.deleteAgenda(id)
        .then((res) => {
          let updateListAgenda = listAgenda.filter(
            (record) => record.id !== res.data.data
          );
          setListAgenda(updateListAgenda);
          message.success("Xóa thành công");
        })
        .catch((err) => {
          message.warning(err.response.data.message);
        });
    }
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      // Gọi phương thức import với file được chọn
      // importMethod(file);
      importExcelEventOrganizers(file);
    }
    // Xóa giá trị của thẻ input để cho phép chọn cùng một file nhiều lần
    event.target.value = null;
  };

  const openFileDialog = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  const importExcelEventOrganizers = (file) => {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("eventId", id);
    setloadingOrganizers((prevLoadings) => {
      const newLoadings = [...prevLoadings];
      newLoadings[1] = true;
      return newLoadings;
    });

    // if (organizerId) {
    OREventDetailApi.importExcelEventOrganizers(formData)
      .then((response) => {
        loadOrganizersByIdEvent(id);
        message.success("Thêm thành công");
      })
      .catch((err) => {
        message.warning(err.response.data.message);
      })
      .finally(() => {
        setTimeout(() => {
          setloadingOrganizers((prevLoadings) => {
            const newLoadings = [...prevLoadings];
            newLoadings[1] = false;
            return newLoadings;
          });
        }, 2000);
      })
  };

  const exportExcelEventOrganizers = (idEvent) => {
    setloadingOrganizers((prevLoadings) => {
      const newLoadings = [...prevLoadings];
      newLoadings[1] = true;
      return newLoadings;
    });

    if (idEvent) {
      OREventDetailApi.exportOrganizersInEvent(idEvent);
      setTimeout(() => {
        setloadingOrganizers((prevLoadings) => {
          const newLoadings = [...prevLoadings];
          newLoadings[1] = false;
          return newLoadings;
        });
      }, 2000);
    } else {
      setTimeout(() => {
        setloadingOrganizers((prevLoadings) => {
          const newLoadings = [...prevLoadings];
          newLoadings[1] = false;
          return newLoadings;
        });
      }, 2000);
      message.warning("Vui lòng chọn người tổ chức");
    }
  };

  const exportTemplateExcelEventOrganizers = () => {
    setloadingOrganizers((prevLoadings) => {
      const newLoadings = [...prevLoadings];
      newLoadings[1] = true;
      return newLoadings;
    });
    OREventDetailApi.exportTemplateOrganizersInEvent();
    setTimeout(() => {
      setloadingOrganizers((prevLoadings) => {
        const newLoadings = [...prevLoadings];
        newLoadings[1] = false;
        return newLoadings;
      });
    }, 2000);
  };

  return (
    <>
      {/* ************ Bảng người tổ chức ************** */}
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
        }}
      >
        <h4 className="title-comment">
          <FontAwesomeIcon icon={faList} style={{ color: "#172b4d" }} />
          <span style={{ marginLeft: "7px" }}>Danh sách người tổ chức</span>
        </h4>
        {(isRoleHost) && (
          <div
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "flex-end",
            }}
          >
            <div
              style={{
                display: "grid",
                // gridAutoFlow: "column",
                gridTemplateColumns: "repeat(4, 1fr)",
                gap: "10px",
              }}
            >
              <Button
                onClick={() => exportTemplateExcelEventOrganizers()}
                style={{
                  color: "white",
                  backgroundColor: "rgb(38, 144, 214)",
                  display: isDisable ? "none" : "",
                }}
              >
                Download template
              </Button>
              <Button
                onClick={() => exportExcelEventOrganizers(id)}
                style={{
                  color: "white",
                  backgroundColor: "rgb(38, 144, 214)",
                  display: isDisable ? "none" : "",
                }}
              >
                Export excel
              </Button>
              <Button
                onClick={openFileDialog}
                style={{
                  color: "white",
                  backgroundColor: "rgb(38, 144, 214)",
                  display: isDisable ? "none" : "",
                }}
              >
                Import excel
              </Button>
              {/* Thẻ input ẩn */}
              <input
                ref={fileInputRef}
                type="file"
                accept=".xls,.xlsx" // Chỉ cho phép chọn file Excel
                style={{ display: "none" }}
                onChange={handleFileChange}
              />
              <Button
                onClick={openModalAddOrganizer}
                style={{
                  color: "white",
                  backgroundColor: "rgb(38, 144, 214)",
                  display: isDisable ? "none" : "",
                }}
              >
                Thêm người tổ chức
              </Button>
            </div>
          </div>
        )}
      </div>
      <br />
      <Table
        dataSource={listOrganizer}
        columns={dynamicColumns}
        pagination={false}
        rowKey="id"
        scroll={{
          x: 1300,
        }}
      />
      <ModalAddOrganizer
        visible={isModalAddOrganizer}
        onCancel={onCancelModalAddOrganizer}
        loadOrganizers={loadOrganizersByIdEvent}
      />
      <ModalUpdateOrganizer
        Organizer={organizer}
        visible={isModalUpdateOrganizer}
        onCancel={onCancelModalUpdateOrganizer}
        checkRole={() => checkRole()}
      />
      {/* ************ END Bảng người tổ chức ************ */}

      {/* ************ Bảng Agenda ************ */}
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          marginTop: "20px",
        }}
      >
        <h4 className="title-comment">
          <FontAwesomeIcon icon={faList} style={{ color: "#172b4d" }} />
          <span style={{ marginLeft: "7px" }}>Danh sách Agenda</span>
        </h4>
        <Button
          onClick={() => saveListAgenda()}
          loading={loadings[1]}
          style={{
            color: "white",
            backgroundColor: "rgb(38, 144, 214)",
            display: isDisable ? "none" : "",
          }}
        >
          Lưu
        </Button>
      </div>
      <br />
      <Table
        dataSource={listAgenda}
        columns={dynamicColumnsAgenda}
        rowKey="id"
        pagination={false}
        locale={{
          emptyText: (
            <>
              {!isDisable && (
                <Button
                  type="primary"
                  onClick={() => {
                    handleAddRow();
                  }}
                >
                  <FontAwesomeIcon icon={faPlus} />
                </Button>
              )}
            </>
          ),
        }}
        scroll={{
          x: 1300,
        }}
      />
      {/* ************ END Bảng Agenda ************ */}
    </>
  );
};

export default OrganizersList;
