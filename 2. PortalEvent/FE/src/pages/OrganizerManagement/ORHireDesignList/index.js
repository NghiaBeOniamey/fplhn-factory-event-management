import React, { useEffect, useState } from "react";
import {
  Button,
  Col,
  DatePicker,
  Form,
  Input,
  Radio,
  Row,
  Select,
  Space,
  Table,
  message,
} from "antd";
import { ORHireDesignListApi } from "./ORHireDesignListApi";
import "../ORHireDesignList/style.css";
import { Tooltip } from "@mui/material";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faList,
  faPenToSquare,
  faPencilAlt,
  faPlus,
  faTrash,
  faXmark,
} from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import { Empty, Modal, Popconfirm, Upload } from "antd/lib";
import { UploadOutlined } from "@ant-design/icons";
import ImageIcon from "@mui/icons-material/Image";
import RemoveRedEyeIcon from "@mui/icons-material/RemoveRedEye";
import EditIcon from "@mui/icons-material/Edit";
import { EventType, Formality } from "../../../constants/EventProperties";
import dayjs from "dayjs";
import { DatePickerProps, RangePickerProps } from "antd/es/date-picker";

export default function ORHireDesignList() {
  const [form] = Form.useForm();
  //List data
  const [listHireDesign, setListHireDesign] = useState([]);
  const [listMajorOfHireDesign, setListMajorOfHireDesign] = useState([]);
  const [listFormality, setListFormality] = useState([]);
  const [listOrganizer, setListOrganizer] = useState([]);
  const [listLocation, setListLocation] = useState([]);
  //phân trang
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  //hứng id người tổ chức và bộ môn
  const [idOrganizer, setIdOrganizer] = useState("");
  const [idMajor, setIdMajor] = useState("");
  //hứng data thời gian của sự kiện cần  booking
  const [startTime, setStartTime] = useState();
  const [endTime, setEndTime] = useState();
  let { RangePicker } = DatePicker;
  //show modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isModalOpen1, setIsModalOpen1] = useState(false);
  const [isModalOpen2, setIsModalOpen2] = useState(false);
  //Danh sách ảnh của sự kiện cần booking
  const [bookingTypes, setBookingTypes] = useState([]);
  const [isSelectedImages, setIsSelectedImages] = useState(false);
  // Update Ảnh
  let [imageType, setImageType] = useState("");
  const [selectedImage, setSelectedImage] = useState("");
  const [selectedImageChange, setSelectedImageChange] = useState("");
  const [banner, setBanner] = useState("");
  const [background, setBackground] = useState("");
  const [standee, setStandee] = useState("");
  // Biến check xem sự kiện trong đã tồn tại ảnh hay chưa
  const [isExisted, setIsExisted] = useState(true);
  const [isUpdate, setIsUpdate] = useState();
  //Thêm và sửa địa điểm
  const [idEvent, setIdEvent] = useState("");
  const [idEventLocation, setIdEventLocation] = useState("");
  const [formality, setFormality] = useState("");
  const [name, setName] = useState("");
  const [path, setPath] = useState("");
  //Thông báo lỗi
  const [errName, setErrName] = useState();
  const [errPath, setErrPath] = useState();
  // key của từng modal
  const [key, setKey] = useState("");

  //Hàm get all data sự kiện cần thuê thiết kế
  const loadDataHireDesign = () => {
    ORHireDesignListApi.fetchAll({
      startTimeLong: null,
      endTimeLong: null,
      formality: null,
      idOrganizer: null,
      idMajor: null,
    })
      .then((response) => {
        setListHireDesign(response.data.data);
        setTotalPages(response.data.totalPages);
      })
      .catch((error) => {
        message.error("Lỗi, Thông báo cho quản trị viên.");
      });
  };

  //END Hàm get all data sự kiện cần thuê thiết kế

  const sendMailImages = () => {
    ORHireDesignListApi.sendMailImages(idEvent).then(
      (res) => {
        //  //console.log("Gửi mail thành công");
      },
      (err) => {
        //  //console.log("Error Api update status event!" + err);
      }
    );
  };

  //Hàm get all data địa điểm cần thuê
  const loadDataLocationOfHireDesign = (id) => {
    ORHireDesignListApi.fetchListLocationOfHireDesign(id)
      .then((response) => {
        setListLocation(response.data);
        setIdEventLocation(id);
      })
      .catch((error) => {
        message.error("Lỗi, Thông báo cho quản trị viên.");
      });
  };
  //END Hàm get all data địa điểm cần thuê

  //Hàm get all data bộ môn
  const loadDataMajorOfHireDesign = () => {
    ORHireDesignListApi.fetchAllMajor()
      .then((response) => {
        setListMajorOfHireDesign(response.data);
      })
      .catch((error) => {
        message.error("Lỗi, Thông báo cho quản trị viên.");
      });
  };
  //END Hàm get all data bộ môn

  //Hàm get all data hình thức booking
  const loadDataFormalityOfHireDesign = () => {
    ORHireDesignListApi.fetchAllFormality()
      .then((response) => {
        setListFormality(response.data);
      })
      .catch((error) => {
        message.error("Lỗi, Thông báo cho quản trị viên.");
      });
  };
  //END Hàm get all data hình thức booking

  //Hàm get all data người tổ chức
  const loadDataOrganizerOfHireDesign = () => {
    ORHireDesignListApi.fetchAllOrganizer()
      .then((response) => {
        setListOrganizer(response.data);
      })
      .catch((error) => {
        message.error("Lỗi, Thông báo cho quản trị viên.");
      });
  };
  //END Hàm get all data người tổ chức

  //Hàm search sự kiện cần booking
  const search = () => {
    //Convert thời gian thành long
    const startDateLong = new Date(startTime).getTime();
    const endDateLong = new Date(endTime).getTime();
    ORHireDesignListApi.fetchAll({
      startTimeLong: startDateLong === 0 ? null : startDateLong,
      endTimeLong: endDateLong === 0 ? null : endDateLong,
      formality: formality === "null" ? null : formality,
      idOrganizer: idOrganizer,
      idMajor: idMajor,
    })
      .then((response) => {
        setListHireDesign(response.data.data);
      })
      .catch((error) => {
        message.error("Lỗi, Thông báo cho quản trị viên.");
      });
  };
  //END booking hàm search sự kiện cần booking

  //get ảnh từ sự kiện
  const getImage = (id) => {
    ORHireDesignListApi.getImageOfHireDesign(id)
      .then((response) => {
        if (response.data) {
          setBackground(response.data.backgroundPath);
        }
        if (response.data) {
          setStandee(response.data.standeePath);
        }
        if (response.data) {
          setBanner(response.data.bannerPath);
        }
      })
      .catch((error) => {
        message.error("Lỗi, Thông báo cho quản trị viên.");
      });
  };
  //END get ảnh từ sự kiện

  //Mở modal cập nhật địa điểm và validate
  const openModalUpdateEventLocation = (item) => {
    setIsUpdate(true);
    setErrName("");
    setErrPath("");
    setIdEventLocation(item.id);
    setName(item.name);
    setPath(item.path);
    setFormality(item.formality);
    showModal2();
  };
  //END mở modal cập nhật địa điểm và validate

  //Mở modal thêm địa điểm và validate
  const openModalAddEventLocation = () => {
    setName("");
    setPath("");
    setErrName("");
    setErrPath("");
    setFormality(0);
    setIsUpdate(false);
    showModal2();
  };
  //END mở modal thêm địa điểm và validate

  //Thao tác thêm hoặc cập nhật địa điểm tổ chức
  const handleAddOrUpdateEventLocation = () => {
    let check = 0;
    if (!name.trim("")) {
      setErrName("Tên địa điểm không được để trống!");
      check += 1;
    } else if (/^\s+|\s+$/.test(name)) {
      setErrName("Tên địa điểm không được chứa dấu cách ở đầu và cuối chuỗi!");
      check += 1;
    } else {
      setErrName("");
    }
    if (!path.trim("")) {
      setErrPath("Địa điểm chi tiết không được để trống!");
      check += 1;
    } else if (/^\s+|\s+$/.test(path)) {
      setErrPath(
        "Địa điểm chi tiết không được chứa dấu cách ở đầu và cuối chuỗi!"
      );
      check += 1;
    } else {
      setErrPath("");
    }
    if (check === 0) {
      if (isUpdate) {
        let data = {
          id: idEventLocation,
          idEvent: idEvent,
          formality: formality,
          name: name,
          path: path,
        };
        ORHireDesignListApi.updateLocationOfHireDesign(idEventLocation, data)
          .then(
            (response) => {
              const index = listLocation.findIndex(
                (item) => item.id === idEventLocation
              );
              const newListEventLocation = [...listLocation];
              newListEventLocation.splice(index, 1);
              newListEventLocation.splice(index, 0, data);
              setListLocation(newListEventLocation);
              // sendMailLocation(idEvent);
              handleCancel2();
              message.success("Cập nhật thành công");
            },
            (err) => {
              message.warning(err.response.data.message);
            }
          )
          .catch((err) => {
            message.error("Lỗi, Thông báo cho quản trị viên.");
          });
      } else {
        let data = {
          idEvent: idEvent,
          formality: formality,
          name: name,
          path: path,
        };
        ORHireDesignListApi.addLocationOfHireDesign(data)
          .then(
            (response) => {
              let eventLocation = {
                id: response.data.data.id,
                formality: formality,
                name: name,
                path: path,
              };
              setListLocation([eventLocation, ...listLocation]);
              // sendMailLocation(idEvent);
              handleCancel2();
              message.success("Thêm thành công");
            },
            (err) => {
              message.warning(err.response.data.message);
            }
          )
          .catch((err) => {
            message.error("Lỗi, Thông báo cho quản trị viên.");
          });
      }
    }
  };
  //END thao tác thêm hoặc cập nhật địa điểm tổ chức

  //Xóa địa điểm cần booking
  const deleteLocationOfHireDesign = (idEventLocation) => {
    ORHireDesignListApi.deleteLocationOfHireDesign(idEventLocation, idEvent)
      .then((response) => {
        loadDataLocationOfHireDesign(idEvent);
        // sendMailLocation(idEvent);
        loadDataHireDesign();
        message.success("Xóa thành công");
      })
      .catch((error) => {
        message.error("Xóa thất bại");
      });
  };
  //END xóa địa điểm cần booking

  //useEffect theo dõi list địa điểm, bộ môn, người tổ chức và danh sách sự kiện cần booking
  useEffect(() => {
    loadDataFormalityOfHireDesign();
    loadDataMajorOfHireDesign();
    loadDataOrganizerOfHireDesign();
    loadDataHireDesign();
  }, [isSelectedImages]);
  //END useEffect theo dõi list địa điểm, bộ môn, người tổ chức và danh sách sự kiện cần booking

  // Xóa hết dữ liệu trong ô input tìm kiếm
  const resets = () => {
    setIdOrganizer(null);
    setIdMajor(null);
    setStartTime(null);
    setEndTime(null);
    setFormality(null);
    loadDataHireDesign(currentPage);
  };
  //END Xóa hết dữ liệu trong ô input tìm kiếm

  //Convert ngày từ long sang date
  const convertDate = (timestamp) => {
    const dateObject = new Date(timestamp);
    const hours = dateObject.getHours();
    const minutes = dateObject.getMinutes();
    const day = dateObject.getDate();
    const month = dateObject.getMonth() + 1;
    const year = dateObject.getFullYear();

    const formattedTime = `${hours.toString().padStart(2, "0")}:${minutes
      .toString()
      .padStart(2, "0")}`;
    const formattedDate = `${day.toString().padStart(2, "0")}/${month
      .toString()
      .padStart(2, "0")}/${year}`;
    return (
      <span>
        {formattedTime} {formattedDate}
      </span>
    );
  };
  //END Convert ngày từ long sang date

  const disabledDate = (current) => {
    const today = dayjs().startOf("day");
    return current < today;
  };

  //Columns hiển thị danh sách sự kiện cần booking
  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      width: "3%",
      align: "center",
      render: (text, record) => (
        <p style={{ alignItems: "center" }}>
          {listHireDesign.indexOf(record) + 1}
        </p>
      ),
    },
    {
      title: "Tên",
      dataIndex: "name",
      key: "name",
      width: "20%",
      align: "center",
      render: (text, record) => (
        <span
          style={{
            maxWidth: "20%",
            whiteSpace: "pre-line",
            overflow: "hidden",
          }}
        >
          {text}
        </span>
      ),
    },
    {
      title: "Thời gian diễn ra",
      dataIndex: "startTimeToEndTime",
      key: "startTimeToEndTime",
      width: "10%",
      align: "center",
      render: (text, record) => (
        <>
          {convertDate(record.startTime)} - {convertDate(record.endTime)}
        </>
      ),
    },
    {
      title: "Người tổ chức",
      dataIndex: "userNameOrganizer",
      key: "userNameOrganizer",
      width: "1%",
      align: "center",
      render: (text, record) => (
        <span
          style={{
            maxWidth: "1%",
            whiteSpace: "pre-line",
            overflow: "hidden",
          }}
        >
          {text}
        </span>
      ),
    },
    {
      title: "Hình thức",
      dataIndex: "formality",
      key: "formality",
      width: "5%",
      align: "center",
      render: (text, record) => {
        return record.formality === "0" ? (
          <span>Online</span>
        ) : record.formality === "1" ? (
          <span>Offline</span>
        ) : record.formality === null ? (
          <span>---</span>
        ) : (
          <span>Offline</span>
        );
      },
    },
    {
      title: "Địa điểm",
      dataIndex: "nameLocation",
      key: "nameLocation",
      width: "300px",
      align: "center",
      clasName: "dia-diem",
      render: (text, record) => (
        <p className="hire-design-location-cell" style={{ width: "300px" }}>
          {text}
        </p>
      ),
    },
    {
      title: "Bộ môn",
      dataIndex: "nameMajor",
      key: "nameMajor",
      width: "10%",
      align: "center",

      render: (text, record) => <p>{text}</p>,
    },
    {
      title: "Kỳ học",
      dataIndex: "semester",
      key: "semester",
      width: "5%",
      align: "center",
      render: (text, record) => <p>{text}</p>,
    },
    {
      title: "Thể loại",
      dataIndex: "nameCategory",
      key: "nameCategory",
      width: "1%",
      align: "center",
      render: (text, record) => <p>{text}</p>,
    },
    {
      title: "Loại booking",
      dataIndex: "photosRequire",
      key: "photosRequire",
      width: "1%",
      align: "center",
      render: (text, record) => {
        let result = "";

        if (
          record.isHireDesignBackground === true &&
          record.isHireDesignBanner === true &&
          record.isHireDesignStandee === true &&
          record.isHireLocation === true
        ) {
          return "Địa điểm và hình ảnh";
        }

        if (
          record.isHireDesignBackground === true &&
          record.isHireDesignBanner === true &&
          record.isHireDesignStandee === true
        ) {
          return "Hình ảnh";
        }

        if (record.isHireLocation === true) {
          result += "Địa điểm";
        }

        if (record.typeBookings.length > 0) {
          if (result !== "") {
            result += ", ";
          }
          result += record.typeBookings.join(", ");
        }

        if (result === "") {
          result = "---";
        }

        return <span>{result}</span>;
      },
    },
    {
      title: "Mục đích",
      dataIndex: "mucdich",
      key: "mucdich",
      width: "10%",
      align: "center",
      render: (text, record) => {
        if (record.eventType === 0) {
          return <span>Dành cho giảng viên</span>;
        } else if (record.eventType === 1) {
          return <span>Dành cho sinh viên</span>;
        } else if (record.eventType === 2) {
          return <span>Dành cho giảng viên và sinh viên</span>;
        } else {
          return <span>Không xác định</span>;
        }
      },
    },
    {
      title: "Action",
      key: "action",
      align: "center",
      width: "5%",
      render: (text, record) => (
        <>
          <Tooltip title="Detail" placement="top">
            <Link to={`/organizer-management/event-detail/${record.id}`}>
              <RemoveRedEyeIcon fontSize="small" style={{ color: "blue" }} />
            </Link>
          </Tooltip>
          &nbsp;
          {record.isHireLocation === true ? (
            <Tooltip title="Update Location" placement="top">
              <button
                style={{
                  background: "none",
                  border: "none",
                  color: "blue",
                  cursor: "pointer",
                }}
                onClick={() => {
                  loadDataLocationOfHireDesign(record.id);
                  showModal1();
                  setIdEvent(record.id);
                }}
              >
                <EditIcon fontSize="small" style={{ color: "blue" }} />
              </button>
            </Tooltip>
          ) : null}
          &nbsp;
          {record.isHireDesignBackground === true ||
            record.isHireDesignBanner === true ||
            record.isHireDesignStandee === true ? (
            <Tooltip title="Update Image" placement="bottom">
              <button
                style={{
                  background: "none",
                  border: "none",
                  color: "blue",
                  cursor: "pointer",
                }}
                onClick={() => {
                  setBookingTypes(record.typeBookings);
                  setImageType(record.typeBookings[0]);
                  setIdEvent(record.id);
                  getImage(record.id);
                  showModal();
                  setKey(key + 1);
                }}
              >
                <ImageIcon fontSize="small" style={{ color: "blue" }} />
              </button>
            </Tooltip>
          ) : null}
        </>
      ),
    },
  ];
  //END Columns hiển thị danh sách sự kiện cần booking

  // modal của cập nhật địa điểm
  const showModal1 = () => {
    setIsModalOpen1(true);
  };
  const handleCancel1 = () => {
    setIsModalOpen1(false);
  };
  const handleOk1 = () => {
    setIsModalOpen1(false);
  };
  //END modal của cập nhật địa điểm

  //Modal của update ảnh
  const showModal = () => {
    setIsModalOpen(true);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };
  const handleOk = () => {
    if (isSelectedImages === false) {
      message.warning("Bạn chưa chọn ảnh cần cập nhật.");
    } else {
      handleSaveImage();
      setIsModalOpen(false);
      setIsExisted(true);
      setIsSelectedImages(false);
    }
  };
  //END Modal của update ảnh

  // modal của thêm địa điểm
  const showModal2 = () => {
    setIsModalOpen2(true);
  };
  const handleCancel2 = () => {
    loadDataHireDesign();
    setIsModalOpen2(false);
  };
  const handleOk2 = () => {
    setIsModalOpen2(false);
  };
  //END modal của thêm địa điểm

  //Column địa điểm
  const columnLocation = [
    {
      title: "STT",
      dataIndex: "recordNumber",
      key: "recordNumber",
      align: "center",
      width: "5%",
      render: (text, record, index) => <span>{index + 1}</span>,
    },
    {
      title: "Tên địa điểm",
      dataIndex: "name",
      key: "name",
      align: "center",
      width: 300,
      render: (text, record) => (
        <p className="hire-design-name-location-cell">{text}</p>
      ),
    },
    {
      title: "Địa điểm cụ thể",
      dataIndex: "path",
      key: "path",
      align: "center",
      width: 300,
      render: (path, record) => {
        if (record.fomality === Formality.ONLINE) {
          return (
            <a
              className="hire-design-name-location-cell"
              href={path}
              target="_blank"
              style={{
                maxWidth: 280,
                whiteSpace: "nowrap",
                overflow: "hidden",
                textOverflow: "ellipsis",
              }}
            >
              {path}
            </a>
          );
        } else {
          return <p className="hire-design-detail-location-cell">{path}</p>;
        }
      },
    },
    {
      width: 100,
      title: "Hình thức tổ chức",
      dataIndex: "formality",
      key: "formality",
      align: "center",
      render: (fomality) =>
        fomality === Formality.ONLINE ? "Online" : "Offline",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      align: "center",
      render: (text, record) => (
        <Space size="middle">
          <FontAwesomeIcon
            icon={faPencilAlt}
            style={{ color: "blue", cursor: "pointer" }}
            onClick={() => {
              setIdEventLocation(record.id);
              openModalUpdateEventLocation(record);
            }}
          />

          <Popconfirm
            title={`Bạn có chắc muốn xóa địa điểm không ?`}
            onConfirm={confirm2}
            onCancel={cancel}
            okText="Yes"
            cancelText="No"
          >
            <FontAwesomeIcon
              icon={faTrash}
              style={{ color: "blue", cursor: "pointer" }}
              onClick={() => {
                setIdEventLocation(record.id);
                setIdEvent(record.idEvent);
              }}
            />
          </Popconfirm>
        </Space>
      ),
      width: "10%",
    },
  ];
  //END Column địa điểm

  //Thao tác lấy data để update ảnh
  const handleSelectChange = (value) => {
    setImageType(value);
  };
  //END thao tác lấy data để update ảnh

  //Conver định dạng ảnh từ String sang Number
  function convertImageTypeToInt(imageType) {
    switch (imageType) {
      case "Background":
        return 0;
      case "Banner":
        return 1;
      case "Standee":
        return 2;
      default:
        return -1;
    }
  }

  const imageTypeInt = convertImageTypeToInt(imageType);
  //Chức năng lưu ảnh
  const handleSaveImage = async () => {
    const formData = new FormData();
    formData.append("file", selectedImageChange); // `file` là đối tượng File hoặc Blob
    formData.append("type", imageTypeInt);
    formData.append("idEvent", idEvent);
    formData.append("backgroundPath", background);
    formData.append("bannerPath", banner);
    formData.append("standeePath", standee);
    await ORHireDesignListApi.uploadImage(formData)
      .then((res) => {
        if (imageTypeInt === 2) {
          setSelectedImage(res.data.data);
          message.success("Cập nhật thành công ảnh standee");
          sendMailImages(idEvent);
        }
        if (imageTypeInt === 0) {
          setSelectedImage(res.data.data);
          message.success("Cập nhật thành công ảnh background");
          sendMailImages(idEvent);
        }
        if (imageTypeInt === 1) {
          setSelectedImage(res.data.data);
          message.success("Cập nhật thành công ảnh banner");
          sendMailImages(idEvent);
        }
      })
      .catch((err) => {
        message.error("Cập nhật thất bại");
        setSelectedImage("");
      });
  };
  //End chức năng lưu ảnh

  //Lấy file ảnh cần update
  const handleImageUpload = (info) => {
    if (info.file.status === "uploading") {
      setSelectedImageChange(info.file.originFileObj);
      setIsSelectedImages(true);
      setIsExisted(false);
    }
  };
  //END Lấy file ảnh cần update

  const confirm = (e) => {
    handleAddOrUpdateEventLocation();
    // setIsModalOpen1(false);
    // setIsModalOpen2(false);
  };

  const confirm2 = (e) => {
    deleteLocationOfHireDesign(idEventLocation);
  };

  const confirm1 = (e) => {
    handleOk();
  };
  const cancel = (e) => { };

  return (
    <>
      {/* Modal sử dụng cập nhật địa điểm tổ chức */}
      <Modal
        title="Cập nhật địa điểm sự kiện booking"
        open={isModalOpen1}
        onCancel={handleCancel1}
        onOk={handleOk1}
        width={1300}
        style={{ textAlign: "center" }}
        footer={null}
      >
        <hr />
        <p
          style={{
            padding: "10px",
            display: "flex",
            justifyContent: "flex-end",
          }}
        >
          <Button
            type="primary"
            onClick={() => {
              openModalAddEventLocation();
            }}
          >
            Thêm địa điểm
          </Button>
        </p>
        <Table
          size={"middle"}
          columns={columnLocation}
          dataSource={listLocation}
          pagination={false}
          scroll={{
            x: 1300,
          }}
        />
      </Modal>
      {/*END modal sử dụng cập nhật địa điểm tổ chức */}

      {/* Modal sử dụng thêm và cập nhật địa điểm */}
      <Modal
        centered
        onCancel={handleCancel2}
        open={isModalOpen2}
        onOk={handleOk2}
        width="55%"
        bodyStyle={{
          maxHeight: "calc(100vh - 150px)",
          overflow: "auto",
        }}
        // closable={false}
        footer={[
          <Popconfirm
            title={`Bạn có chắc muốn  ${isUpdate ? "cập nhật" : "thêm"
              } địa điểm không ?`}
            onConfirm={confirm}
            onCancel={cancel}
          >
            <Button type="primary" className="btn-form-event">
              {isUpdate ? "Cập nhật" : "Thêm"}
              <FontAwesomeIcon
                icon={isUpdate ? faPenToSquare : faPlus}
                style={{ color: "#ffffff", marginLeft: "7px" }}
              />
            </Button>
          </Popconfirm>,
          <Button
            style={{ backgroundColor: "red", color: "white" }}
            key="back"
            onClick={handleCancel2}
          >
            Hủy
            <FontAwesomeIcon
              icon={faXmark}
              style={{ color: "#ffffff", marginLeft: "7px" }}
            />
          </Button>,
        ]}
      >
        <div>
          <span style={{ fontSize: "18px" }}>
            {isUpdate ? "Cập nhật địa điểm" : "Thêm địa điểm"}
          </span>
        </div>
        <hr />
        <br />
        <Form
          style={{
            marginTop: "15px",
            marginBottom: "15px",
          }}
          layout="vertical"
          name="basic"
          autoComplete="off"
        >
          <Row>
            <Col span={12} style={{ marginRight: "60px" }}>
              <Form.Item
                label="Tên địa điểm tổ chức"
                rules={[{ required: true }]}
              >
                <Input
                  placeholder="Nhập vào tên địa điểm tổ chức"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  maxLength={100}
                />
                <span style={{ color: "red" }}>{errName}</span>
              </Form.Item>
            </Col>
            <Col span={9}>
              <Form.Item label="Hình thức tổ chức" rules={[{ required: true }]}>
                <Radio.Group
                  value={formality}
                  onChange={(e) => setFormality(e.target.value)}
                >
                  <Radio value={0} checked={formality === Formality.ONLINE}>
                    Online
                  </Radio>
                  <Radio value={1} checked={formality === Formality.OFFLINE}>
                    Offline
                  </Radio>
                </Radio.Group>
              </Form.Item>
            </Col>
          </Row>

          <Form.Item label="Địa điểm chi tiết" rules={[{ required: true }]}>
            <Input
              placeholder="Nhập vào địa điểm chi tiết"
              value={path}
              onChange={(e) => setPath(e.target.value)}
              maxLength={500}
            />
            <span style={{ color: "red" }}>{errPath}</span>
          </Form.Item>
        </Form>
      </Modal>
      {/*END Modal sử dụng thêm và cập nhật địa điểm */}

      {/* Modal sử dụng cập nhật ảnh */}
      <Modal
        title="Cập nhật ảnh sự kiện booking"
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
        width={900}
        style={{ textAlign: "center" }}
        footer={[
          <Col>
            <Popconfirm
              title={`Bạn có chắc muốn cập nhật ảnh không ?`}
              onConfirm={confirm1}
              onCancel={cancel}
              okText="Yes"
              cancelText="No"
            >
              <Button type="primary" className="btn-form-event">
                Cập nhật
                <FontAwesomeIcon
                  icon={isUpdate ? faPenToSquare : faPlus}
                  style={{ color: "#ffffff", marginLeft: "7px" }}
                />
              </Button>
            </Popconfirm>
            <Button
              style={{ backgroundColor: "red", color: "white" }}
              key="back"
              onClick={handleCancel}
            >
              Hủy
              <FontAwesomeIcon
                icon={faXmark}
                style={{ color: "#ffffff", marginLeft: "7px" }}
              />
            </Button>
          </Col>,
        ]}
      >
        <hr />
        <br />
        <Row style={{ justifyContent: "space-around" }}>
          <Col span={9} offset={-3}>
            <Form.Item
              label="Cập nhật ảnh"
              name="imageType"
              rules={[
                {
                  required: false,
                  message: "Vui lòng chọn loại ảnh muốn cập nhật!",
                },
              ]}
            >
              <Select
                onChange={handleSelectChange}
                value={bookingTypes}
                defaultValue={bookingTypes[0]}
                key={key}
              >
                {bookingTypes.map((item) => {
                  return <Select.Option value={item}>{item}</Select.Option>;
                })}
              </Select>
            </Form.Item>
          </Col>
          <Col span={9} offset={-3}>
            <Form.Item
              label="Chọn ảnh"
              name="imageType"
              rules={[
                {
                  required: false,
                  message: "Vui lòng chọn loại ảnh muốn cập nhật!",
                },
              ]}
            >
              <Upload
                showUploadList={false}
                onChange={handleImageUpload}
                accept=".jpg,.jpeg,.png"
              >
                <Button icon={<UploadOutlined />}>Chọn ảnh</Button>
              </Upload>
            </Form.Item>
          </Col>
        </Row>
        <Row justify="center">
          {isExisted === true ? (
            <div>
              <h4>Ảnh đã lưu:</h4>
              {background != null && imageType === "Background" ? (
                <img
                  src={background}
                  style={{ maxWidth: "800px", borderRadius: "8px" }}
                  alt="Ảnh đã lưu"
                />
              ) : imageType !== "Background" ? (
                ""
              ) : (
                <Empty description="Không có ảnh background" />
              )}

              {banner != null && imageType === "Banner" ? (
                <img
                  src={banner}
                  style={{ maxWidth: "800px", borderRadius: "8px" }}
                  alt="Ảnh đã lưu"
                />
              ) : imageType !== "Banner" ? (
                ""
              ) : (
                <Empty description="Không có ảnh banner" />
              )}

              {standee != null && imageType === "Standee" ? (
                <img
                  src={standee}
                  style={{ maxWidth: "800px", borderRadius: "8px" }}
                  alt="Ảnh đã lưu"
                />
              ) : imageType !== "Standee" ? (
                ""
              ) : (
                <Empty description="Không có ảnh standee" />
              )}
            </div>
          ) : (
            isExisted === false && (
              <div>
                <h4>Ảnh đã chọn:</h4>
                <img
                  src={URL.createObjectURL(selectedImageChange)}
                  alt="Selected"
                  style={{ maxWidth: "800px", borderRadius: "8px" }}
                />
              </div>
            )
          )}
        </Row>
      </Modal>
      {/*END Modal sử dụng cập nhật ảnh */}

      <div className="table-hire-design-container">
        {/* Bộ lọc sự kiện cần booking */}
        <h4 className="title-hire-design">
          <FontAwesomeIcon icon={faFilter} /> &nbsp;Bộ lọc
        </h4>
        <div style={{ paddingBottom: "20px" }}>
          <Form labelCol={{ span: 24 }}>
            <Row style={{ justifyContent: "space-around" }}>
              <Col span={9} offset={-3}>
                <Form.Item
                  label="Người tổ chức"
                  name="idOrganizer"
                  placeholder="Chọn người tổ chức."
                  rules={[
                    {
                      required: false,
                      message: "Vui lòng chọn người tổ chức!",
                    },
                  ]}
                >
                  <Select
                    onChange={(value) => setIdOrganizer(value)}
                    placeholder="Vui lòng chọn người tổ chức."
                  >
                    {listOrganizer.map((item) => (
                      <Select.Option key={item.id} value={item.id}>
                        {item.name + " - " + item.userName}
                      </Select.Option>
                    ))}
                  </Select>
                </Form.Item>
              </Col>
              <Col span={9} offset={-3}>
                <Form.Item
                  label="Bộ môn"
                  name="idMajor"
                  rules={[
                    {
                      required: false,
                      message: "Vui lòng chọn bộ môn!",
                    },
                  ]}
                >
                  <Select
                    onChange={(value) => setIdMajor(value)}
                    placeholder="Vui lòng chọn bộ môn."
                  >
                    {listMajorOfHireDesign.map((item) => (
                      <Select.Option key={item.id} value={item.id}>
                        {item.code + " - " + item.name}
                      </Select.Option>
                    ))}
                  </Select>
                </Form.Item>
              </Col>
            </Row>

            <Row style={{ justifyContent: "space-around" }}>
              <Col span={9} offset={-3}>
                <Form.Item
                  label="Hình thức diễn ra"
                  name="formality"
                  rules={[
                    {
                      required: false,
                      message: "Vui lòng chọn hình thức diễn ra!",
                    },
                  ]}
                >
                  <Select
                    onChange={(value) => setFormality(value)}
                    placeholder="Vui lòng chọn hình thức diễn ra."
                  >
                    <Select.Option key={0} value={0}>
                      Online
                    </Select.Option>
                    <Select.Option key={1} value={1}>
                      Offline
                    </Select.Option>
                  </Select>
                </Form.Item>
              </Col>
              <Col span={9} offset={-3}>
                <Form.Item
                  label="Thời gian sự kiện"
                  rules={[{ required: true }]}
                >
                  <RangePicker
                    showTime={{
                      format: "HH:mm",
                    }}
                    format="YYYY-MM-DD HH:mm"
                    value={[startTime, endTime]}
                    popupStyle={{ maxHeight: "300px" }}
                    style={{ width: "100%" }}
                    onChange={(e) => {
                      if (e && e.length === 2) {
                        setStartTime(e[0]);
                        setEndTime(e[1]);
                      } else {
                        setStartTime(null);
                        setEndTime(null);
                      }
                    }}
                  />
                </Form.Item>
              </Col>
            </Row>
            <Space
              wrap
              style={{
                justifyContent: "center",
                alignItems: "center",
                width: "100%",
              }}
            >
              <Form.Item
                style={{
                  marginBottom: "0",
                }}
              >
                <Button
                  type="primary"
                  htmlType="submit"
                  style={{ marginRight: "0.5rem" }}
                  onClick={() => search()}
                >
                  Tìm kiếm
                </Button>
                <Button
                  type="primary"
                  style={{ backgroundColor: "#dc3545", marginLeft: "0.5rem" }}
                  htmlType="reset"
                  onClick={() => resets()}
                >
                  Làm mới
                </Button>
              </Form.Item>
            </Space>
          </Form>
        </div>
        {/* END Bộ lọc sự kiện cần booking */}
      </div>

      <div className="table-hire-design-container">
        <h4 className="title-hire-design">
          <FontAwesomeIcon icon={faList} /> &nbsp;Sự kiện cần booking
        </h4>
        <Form form={form}>
          {/* Table sự kiện cần booking */}
          <div>
            {listHireDesign.length > 0 ? (
              <Table
                bordered
                size={"middle"}
                style={{ marginBottom: "20px" }}
                columns={columns}
                dataSource={listHireDesign}
                pagination={{
                  pageSize: 10, // Số phần tử trên mỗi trang
                }}
                scroll={{
                  x: 1300,
                }}
              />
            ) : (
              <Empty />
            )}
          </div>
          {/* <div className="phan-trang">
            <Pagination
              simple
              current={currentPage + 1}
              onChange={(page) => {
                setCurrentPage(page - 1);
              }}
              total={totalPages * 10}
            />
          </div>
          nếu muốn phân trang tự làm thì mở ra và sửa lại :)))
          */}
        </Form>
      </div>
    </>
  );
}
