import {
  ExclamationCircleFilled,
  UploadOutlined,
  UserOutlined,
  FileDoneOutlined,
  CloseCircleOutlined,
  InfoCircleOutlined,
} from "@ant-design/icons";
import {
  faCircleInfo,
  faCodePullRequest,
  faFloppyDisk,
  faListCheck,
  faPaperPlane,
  faUserCheck,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  Button,
  Card,
  Col,
  DatePicker,
  Divider,
  Form,
  Input,
  message,
  Popconfirm,
  Radio,
  Row,
  Select,
  Space,
  Upload,
  Checkbox,
  Modal,
  Image,
  InputNumber,
  Tooltip,
} from "antd";
import React, { useContext, useEffect, useState } from "react";
import moment from "moment/moment";
import "../../OREventDetail/OREDEventInformationForm/Form.css";
import FormEditor from "../OREDJoditEditer/JoditEditer";
import OREventDetailApi from "../OREventDetailApi";
import dayjs from "dayjs";
import { Link, useParams } from "react-router-dom";
import { green, yellow } from "@mui/material/colors";
import OrganizersList from "../OREDOrganizersList/OrganizersList";
import OREDLocation from "../OREDLocation";
import OREDResource from "../OREDResource";
import ModalCloseEvent from "./OREDCloseEvent/ModalCloseEvent";
import OREDEvidence from "../OREDEvidence/evidence";
import ModalEventReorganization from "./OREDEventReorganization/ModalEventReorganization";
// import CommentSection from "../OREDComment/Comment";s
import OREDInvitationTime from "../OREDIvitationTime";
import ShowHistoryModal from "../../../../components/ShowHistoryModal";
import { ORGANIZER_EVENT_DETAIL } from "../../../../constants/DisplayName";
import {
  ACTOR_CNBM,
  ACTOR_GV,
  ACTOR_TM,
} from "../../../../constants/ActorConstant";
import { getCurrentUser } from "../../../../utils/Common";
import { South } from "@mui/icons-material";
import { AppContextregister } from "../../OREventRegister/context";
// import { EventStatus } from "../../../../constants/EventProperties";

const { confirm } = Modal;
const { Option } = Select;

const FormEvent = () => {
  const [modal, contextHolder] = Modal.useModal();
  const [listCategory, setListCategory] = useState([]);
  const [listSemester, setListSemester] = useState([]);
  const [listMajor, setListMajor] = useState([]);
  const [listObject, setListObject] = useState([]);
  const [listHoneyCategory, setListHoneyCategory] = useState([]);
  const { RangePicker } = DatePicker;

  // Thông tin sự kiện
  const [eventName, setEventName] = useState("");
  const [eventCategory, setEventCategory] = useState("");
  const [eventSemester, setEventSemester] = useState("");
  const [eventSemesterId, setEventSemesterId] = useState("");
  const [eventBlock, setEventBlock] = useState(false);
  const [eventMajor, setEventMajor] = useState([]);
  const [eventDescription, setEventDescription] = useState("");
  const [eventType, setEventType] = useState(0);
  const [eventExpectedParticipants, setEventExpectedParticipants] = useState();
  const [eventStatus, setEventStatus] = useState();
  const [eventReason, setEventReason] = useState("");
  const [eventApprover, setEventApprover] = useState("");
  const [eventStartDate, setEventStartDate] = useState(0);
  const [eventEndDate, setEventEndDate] = useState(0);
  const [isHireLocation, setIsHireLocation] = useState(false);
  const [isHireDesignBg, setIsHireDesignBg] = useState(false);
  const [isHireDesignBanner, setIsHireDesignBanner] = useState(false);
  const [isHireDesignStandee, setIsHireDesignStandee] = useState(false);
  const [banner, setBanner] = useState("");
  const [background, setBackground] = useState("");
  const [standee, setStandee] = useState("");
  const [eventObject, setEventObject] = useState([]);
  const [semesterStart, setSemesterStart] = useState();
  const [semesterEnd, setSemesterEnd] = useState();
  const [startTimeBlock, setStartTimeBlock] = useState("");
  const [endTimeBlock, setendTimeBlock] = useState("");
  const [object, setObject] = useState("");
  const [countParticipant, setCountParticipant] = useState(0);//số người đăng ký
  const [countNumberParticipant, setCountNumberParticipant] = useState(0); //số người tham gia
  const [numberParticipant, setNumberParticipant] = useState(0);
  const [percentage, setPercentage] = useState("");
  const [isConversionHoneyRequest, setIsConversionHoneyRequest] =
    useState(false);

  const [eventDepartment, setEventDepartment] = useState([]);
  const [listDepartment, setListDepartment] = useState([]);

  // Lưu ảnh hiển thị khi xem
  const [visible, setVisible] = useState(false);
  const [imageSrc, setImageSrc] = useState("");

  // Đóng mở đăng ký
  const [isOpenRegistration, setIsOpenRegistration] = useState(false);
  // Đóng mở điểm danh
  const [isAttendance, setIsAttendance] = useState(false);
  // Phê duyệt sự kiện hàng kỳ
  const [isApprovalPeriodically, setIsApprovalPeriodically] = useState(0);
  const [approvalPeriodicallyReason, setApprovalPeriodicallyReason] =
    useState("");

  // Error
  const [errorEventName, setErrorEventName] = useState("");
  const [errorEventCategory, setErrorEventCategory] = useState("");
  const [errorEventMajor, setErrorEventMajor] = useState("");
  const [errorDate, setErrorDate] = useState("");
  const [errorEventObject, setErrorEventObject] = useState("");
  const [errorBackGround, setErrorBackGround] = useState("");
  const [errorBanner, setErrorBanner] = useState("");
  const [errorEventExpectedParticipants, setErrorEventExpectedParticipants] =
    useState("");
  const [errorDescription, setErrorDescription] = useState("");

  // Ẩn hiện các thuộc tính
  const [isDisabled, setIsDisabled] = useState(true);
  const [isDisabledImg, setIsDisabledImg] = useState(true);
  const [isExisted, setIsExisted] = useState(false);
  const [isRoleHost, setIsRoleHost] = useState(false);
  const [isRoleCoHost, setIsRoleCoHost] = useState(false);
  const { id } = useParams();
  const [eventAp, setEventAp] = useState(false);
  const [isCNBM, setIsCNBM] = useState(false);
  const [isGV, setIsGV] = useState(false);
  const [subjectCode, setSubjectCode] = useState();
  const [trainingFacilityCode, setTrainingFacilityCode] = useState();
  const [eventMajorId, setEventMajorId] = useState([]);

  const enableRole = () => {
    const user = getCurrentUser();
    if (typeof user.role === "object") {
      if (user.role.includes(ACTOR_GV)) {
        setIsGV(true);
      }
      if (user.role.includes(ACTOR_TM) || user.role.includes(ACTOR_CNBM)) {
        setIsCNBM(true);
        setIsGV(true);
      }
      // if (user.role.includes(ACTOR_BDT_CS)) {
      //     setIsAdmin(true);
      //     setIsApprover(true);
      //     setIsOrganizer(true);
      // }
      // if (user.role.includes(ACTOR_TBDT_CS)) {
      //     setIsAdmin(true);
      //     setIsApprover(true);
      //     setIsOrganizer(true);
      //     setIsAdminHO(true);
      // }
    } else if (typeof user.role === "string") {
      user.role.includes(ACTOR_GV) ? setIsGV(true) : setIsGV(false);
      user.role.includes(ACTOR_CNBM)
        ? setIsCNBM(true) && setIsGV(true)
        : setIsCNBM(false);
    }
    setSubjectCode(user.subjectCode);
    setTrainingFacilityCode(user.trainingFacilityCode);
  };

  useEffect(() => {
    enableRole();
  }, []);

  //Thời gian hiện tại
  let currentTime = new Date().getTime();

  const updateCurrentTime = () => {
    currentTime = new Date().getTime();
  };
  setInterval(updateCurrentTime, 60000);

  // State modal Upload ảnh
  const [checkImg, setCheckImg] = useState(0);
  const [selectedImage, setSelectedImage] = useState("");
  const [selectedImageChange, setSelectedImageChange] = useState("");
  const [visibleImage, setVisibleImage] = useState(false);

  //State lưu thiết lập hệ thống
  const [systemOption, setSystemOption] = useState({
    id: 1,
    mandatoryApprovalDays: 1,
    isAllowNotEnoughTimeApproval: false,
    isAllowCloseEvent: false,
    minimumCloseTime: 0,
  });

  // Modal đóng sự kiện
  const [isModalOpen, setIsModalOpen] = useState(false);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  // Upload ảnh
  const showModalImageUpload = (imgResponse, check) => {
    setSelectedImage(imgResponse);
    setCheckImg(check);
    setVisibleImage(true);
  };
  const handleCancelImage = () => {
    setVisibleImage(false);
    setSelectedImage("");
    setSelectedImageChange("");
  };
  const handleSaveImage = async () => {
    const formData = new FormData();
    formData.append("file", selectedImageChange);
    formData.append("type", checkImg);
    await OREventDetailApi.uploadImage(formData, id)
      .then((res) => {
        message.success("Upload thành công");
        if (checkImg === 0) {
          setBackground(res.data.data);
        } else if (checkImg === 1) {
          setBanner(res.data.data);
        } else if (checkImg === 2) {
          setStandee(res.data.data);
        }
        setVisibleImage(false);
        setSelectedImage("");
        setSelectedImageChange("");
      })
      .catch((err) => {
        message.error("Upload thất bại");
        setSelectedImage("");
        setVisibleImage(true);
      });
  };
  const handleImageUpload = (info) => {
    if (info.file.status === "uploading") {
      setSelectedImageChange(info.file.originFileObj);
      setSelectedImage("");
    }
  };

  useEffect(() => {
    if (selectedImage === "" || selectedImage === null) {
      setIsExisted(false);
    } else {
      setIsExisted(true);
    }
  }, [selectedImage]);

  useEffect(() => {
    checkRole();
    checkRoleCoHost();
    loadAllData();
    getSystemOption();
  }, [id, isRoleHost]);

  // Check trạng thái để lấy systemOption
  useEffect(() => {
    if (eventStatus === 1 || eventStatus === 2) {
      getSystemOption();
    }
  }, [eventStatus]);

  const getSystemOption = () => {
    OREventDetailApi.getSystemOption()
      .then((res) => {
        setSystemOption(res.data.data);
      })
      .catch((err) => {
        //  //console.log(err.response);
      });
  };

  const loadAllData = () => {
    // cái cũ
    // OREventDetailApi.getMajor().then(
    //   (res) => {
    //     //console.log("ListMajor: ",res.data.data);
    //     setListMajor(res.data.data);
    //   },
    //   (err) => {
    //     //  //console.log("Error API get all major" + err);
    //   }
    // );

    OREventDetailApi.getAllObject().then(
      (res) => {
        setListObject(res.data.data);
      },
      (err) => {
        //  //console.log("Error API get all object" + err);
      }
    );
    // cái cũ
    // OREventDetailApi.getMajorByIdEvent(id).then(
    //   (res) => {
    //     //console.log("EventMajor: ", res.data.data);
    //     setEventMajor(res.data.data.map((major) => major.id));
    //   },
    //   (err) => {
    //     //  //console.log("Error API get all major" + err);
    //   }
    // );
    OREventDetailApi.getMajorCampusByIdEvent(id).then(
      (res) => {
        setEventMajor(res.data.data.map((major) => {
          return {
            majorId: major.id,
            departmentId: major.departmentId
          }
        }));
      },
      (err) => {
        //  //console.log("Error API get all major" + err);
      }
    );
    OREventDetailApi.getCategory().then(
      (res) => {
        setListCategory(res.data.data);
      },
      (err) => {
        //  //console.log("Error API get all category");
      }
    );
    OREventDetailApi.getDepartmentCampus().then(
      (res) => {
        setListDepartment(res.data.data);
      },
      (err) => {
        //  //console.log("Error API get all category");
      }
    );
    OREventDetailApi.getDepartmentCampusByIdEvent(id).then(
      (res) => {
        let arrIdDepartment = res.data.data.map(item => item.id);
        setEventDepartment(arrIdDepartment);
        OREventDetailApi.getMajorCampus(arrIdDepartment.join(",")).then(response => {
          setListMajor(response.data.data);
        });
      },
      (err) => {
        //  //console.log("Error API get all category");
      }
    );
    OREventDetailApi.getSemester().then(
      (res) => {
        setListSemester(res.data.data);
      },
      (err) => {
        //  //console.log("Error API get all semester");
      }
    );
    OREventDetailApi.getObjectByIdEvent(id)
      .then((res) => {
        setEventObject(res.data.data.map((object) => object.id));
      })
      .catch((err) => {
        // message.error("Lỗi hệ thống. Không thể lấy đối tượng");
      });
    eventDetail(id);
    // OREventDetailApi.getListHoneyCategory().then(
    //   (res) => {
    //     setListHoneyCategory(res.data.data);
    //   },
    //   (err) => {
    //    //  //console.log("Error API get all honey category" + err);
    //   }
    // );
  };

  // Kiểm tra host hay co-host
  const checkRole = () => {
    OREventDetailApi.checkOrganizerRole(id)
      .then((res) => {
        setIsRoleHost(res.data.data);
      })
      .catch((err) => {
        ////  //console.log(err);
      });
  };

  const checkRoleCoHost = () => {
    OREventDetailApi.checkOrganizerRoleCoHost(id)
      .then((res) => {
        setIsRoleCoHost(res.data.data);
      })
      .catch((err) => { });
  };

  // Tìm kiếm học kỳ
  const findSemester = () => {
    let startDate = eventStartDate ? new Date(eventStartDate).getTime() : null;
    let endDate = eventEndDate ? new Date(eventEndDate).getTime() : null;
    let foundSemester = null;
    let foundStartBlock = null;
    let foundEndBlock = null;

    for (const semester of listSemester) {
      if (startDate >= semester.startTime && endDate <= semester.endTime) {
        foundSemester = semester.name;
        setEventSemesterId(semester.id);
        setSemesterStart(semester.startTime);
        setSemesterEnd(semester.endTime);
        if (
          startDate >= semester.startTimeFirstBlock &&
          startDate <= semester.endTimeFirstBlock
        ) {
          foundStartBlock = false;
          setStartTimeBlock(semester.startTimeFirstBlock);
          setendTimeBlock(semester.endTimeFirstBlock);
        } else if (
          startDate >= semester.startTimeSecondBlock &&
          startDate <= semester.endTimeSecondBlock
        ) {
          foundStartBlock = true;
          setStartTimeBlock(semester.startTimeSecondBlock);
          setendTimeBlock(semester.endTimeSecondBlock);
        }

        if (
          endDate >= semester.startTimeFirstBlock &&
          endDate <= semester.endTimeFirstBlock
        ) {
          foundEndBlock = false;
          setStartTimeBlock(semester.startTimeFirstBlock);
          setendTimeBlock(semester.endTimeFirstBlock);
        } else if (
          endDate >= semester.startTimeSecondBlock &&
          endDate <= semester.endTimeSecondBlock
        ) {
          foundEndBlock = true;
          setStartTimeBlock(semester.startTimeSecondBlock);
          setendTimeBlock(semester.endTimeSecondBlock);
        }
        break;
      }
    }
    if (startDate !== null && endDate !== null) {
      if (foundSemester === null) {
        message.error("Không tìm thấy học kỳ phù hợp");
      } else {
        setEventSemester(foundSemester);
        if (foundStartBlock !== null && foundEndBlock !== null) {
          if (foundStartBlock !== foundEndBlock) {
            message.error("Không tìm thấy block phù hợp");
          } else {
            setEventBlock(foundStartBlock);
          }
        }
      }
    }
  };

  useEffect(() => {
    findSemester();
  }, [eventStartDate, eventEndDate, eventBlock]);
  // End Tìm kiếm học kỳ

  // Chi tiết sự kiện
  const eventDetail = (id) => {
    OREventDetailApi.eventDetail(id).then(
      (res) => {
        setEventName(res.data.data.name);
        setEventCategory(res.data.data.category);
        setEventDescription(res.data.data.description);
        setEventStatus(res.data.data.status);
        setEventReason(res.data.data.reason);
        setApprovalPeriodicallyReason(res.data.data.approvalPeriodicallyReason);
        setEventBlock(res.data.data.block);
        setEventExpectedParticipants(res.data.data.expectedParticipants);
        setEventStartDate(res.data.data.startTime);
        setEventEndDate(res.data.data.endTime);
        setEventApprover(res.data.data.approveName);
        setEventSemester(res.data.data.semester);
        setEventSemesterId(res.data.data.semesterId);
        setSemesterStart(res.data.data.semesterStart);
        setSemesterEnd(res.data.data.semesterEnd);

        setBanner(res.data.data.banner);
        setBackground(res.data.data.background);
        setStandee(res.data.data.standee);
        setIsHireLocation(res.data.data.isHireLocation);
        setIsHireDesignBg(res.data.data.isHireDesignBg);
        setIsHireDesignBanner(res.data.data.isHireDesignBanner);
        setIsHireDesignStandee(res.data.data.isHireDesignStandee);
        setIsAttendance(res.data.data.isAttendance);
        setIsOpenRegistration(res.data.data.isOpenRegistration);
        setIsApprovalPeriodically(res.data.data.isWaitApprovalPeriodically);

        setCountParticipant(res.data.data.countParticipant);
        setCountNumberParticipant(res.data.data.countNumberParticipant);
        setNumberParticipant(res.data.data.numberParticipant);
        setIsConversionHoneyRequest(res.data.data.isConversionHoneyRequest);

        setPercentage(res.data.data.percentage);

        if (res.data.data.eventType === 0) {
          setEventType(0);
          setObject("Sinh viên");
        } else if (res.data.data.eventType === 1) {
          setEventType(1);
          setObject("Giảng viên");
        } else if (res.data.data.eventType === 2) {
          setEventType(2);
          setObject("Giảng viên và Sinh viên");
        } else if (res.data.data.eventType === null) {
          setEventType(null);
          setObject("Không xác định");
        }

        if (res.data.data.block === false) {
          setStartTimeBlock(res.data.data.startTimeFirstBlock);
          setendTimeBlock(res.data.data.endTimeFirstBlock);
        } else if (res.data.data.block === true) {
          setStartTimeBlock(res.data.data.startTimeSecondBlock);
          setendTimeBlock(res.data.data.endTimeSecondBlock);
        }
      },
      (err) => {
        //  //console.log("Error API event detail" + err);
      }
    );
  };
  // End Chi tiết sự kiện

  // Date
  const disabledDate = (current) => {
    const today = dayjs().startOf("day");
    return current < today;
  };
  const onOk = (value) => {
    setEventStartDate((prevStartDate) =>
      value[0] != null ? value[0].valueOf() : null
    );
    setEventEndDate((prevEndDate) =>
      value[1] != null ? value[1].valueOf() : null
    );
  };

  useEffect(() => {
    if (isRoleHost === true || isRoleCoHost === true) {
      if (eventStatus === 1 || eventStatus === 2 || eventStatus === 7) {
        setIsDisabled(false);
        setIsDisabledImg(false);
      } else {
        setIsDisabled(true);
        setIsDisabledImg(true);
      }
    } else {
      setIsDisabled(true);
      setIsDisabledImg(true);
    }
  }, [eventStatus, isRoleHost, isRoleCoHost]);

  const renderDateTime = (startTime) => {
    const momentObject = moment(startTime);
    const formattedDateTime = momentObject.format("DD/MM/YYYY");
    return formattedDateTime;
  };

  // Gửi phê duyệt
  const showConfirm = () => {
    confirm({
      title: "Cảnh báo!!!",
      icon: <ExclamationCircleFilled />,
      content: (
        <p>
          Sự kiện bắt buộc gửi yêu cầu phê duyệt trước khi diễn ra sự kiện{" "}
          <strong>{systemOption.mandatoryApprovalDays} ngày</strong>. Bạn có
          chắc chắn muốn gửi yêu cầu?
        </p>
      ),
      onOk() {
        requestApproval();
      },
      onCancel() { },
    });
  };

  const handleUpdateStatusEvent = () => {
    if (isRoleHost === false) {
      message.error("Bạn không có quyền yêu cầu phê duyệt khi là Co-host");
    } else {
      if (validateEvent()) {
        const currentDate = new Date().getTime();
        let dateDiff = Math.floor(
          (eventStartDate - currentDate) / (1000 * 60 * 60 * 24)
        );
        if (dateDiff < systemOption.mandatoryApprovalDays) {
          if (systemOption.isAllowNotEnoughTimeApproval) {
            showConfirm();
          } else {
            modal.error({
              title: "Use Hook!",
              content: (
                <>
                  <p>
                    Sự kiện bắt buộc gửi yêu cầu phê duyệt trước khi diễn ra sự
                    kiện{" "}
                    <strong>{systemOption.mandatoryApprovalDays} ngày</strong>.
                  </p>
                </>
              ),
            });
          }
        } else {
          update();
          requestApproval();
        }
      }
    }
  };

  const requestApproval = () => {
    OREventDetailApi.updateStatusEvent(id).then(
      (res) => {
        setIsDisabled(true);
        setEventStatus(3);
        message.success("Yêu cầu phê duyệt thành công");
      },
      (err) => {
        if (err.response.status === 400) {
          message.warning(err.response.data.message);
        }
      }
    );
  };
  // End Gửi phê duyệt

  // Validate event
  const validateEvent = () => {
    let check = 0;
    if (eventName == null || eventName.trim() === "") {
      setErrorEventName("Tên sự kiện không được để trống");
      check += 1;
    } else if (eventName.length > 500) {
      setErrorEventName("Tên sự kiện vượt quá 500 ký tự");
      check += 1;
    } else {
      setErrorEventName("");
    }
    if (eventCategory == null || eventCategory.trim() === "") {
      setErrorEventCategory("Thể loại của sự kiện không được để trống");
      check += 1;
    } else {
      setErrorEventCategory("");
    }
    if (eventMajor == null || eventMajor.length === 0) {
      setErrorEventMajor("Chuyên ngành không được để trống");
      check += 1;
    } else {
      setErrorEventMajor("");
    }

    if (
      eventStartDate == null ||
      eventEndDate == null ||
      eventStartDate.length === 0 ||
      eventEndDate.length === 0
    ) {
      setErrorDate("Thời gian của sự kiện không được để trống");
      check += 1;
    } else {
      setErrorDate("");
    }

    if (eventObject === null || eventObject.length === 0) {
      setErrorEventObject("Đối tượng hướng đến không được để trống");
      check += 1;
    } else {
      setErrorEventObject("");
    }

    if (eventMajor === undefined || eventMajor === null || eventMajor.length === 0) {
      setErrorEventMajor("Chuyên ngành không được để trống");
      check += 1;
    } else {
      setErrorEventMajor("");
    }

    if (
      eventExpectedParticipants === null ||
      eventExpectedParticipants === 0 || eventExpectedParticipants === undefined
    ) {
      setErrorEventExpectedParticipants(
        "Dự kiến người tham gia không được để trống"
      );
      check += 1;
    } else if (eventExpectedParticipants.length > 5) {
      setErrorEventExpectedParticipants(
        "Dự kiến người tham gia không được quá giới hạn 5 ký tự"
      );
      check += 1;
    } else if (!/^\d+$/.test(eventExpectedParticipants)) {
      setErrorEventExpectedParticipants(
        "Dự kiến người tham gia phải là số nguyên"
      );
      check += 1;
    } else {
      setErrorEventExpectedParticipants("");
    }

    const eventTextDescription = eventDescription.replace(/(<([^>]+)>)/ig, '');

    if (eventTextDescription === null || eventTextDescription === undefined || eventTextDescription.trim().length === 0) {
      setErrorDescription("Mục tiêu sự kiện không được để trống");
      check += 1;
    }
    else if (eventTextDescription !== null && eventTextDescription.length > 4000) {
      setErrorDescription("Số lượng ký tự đã vượt qua giới hạn là 4000 ký tự");
      check += 1;
    } else {
      setErrorDescription("");
    }

    // if (eventDescription !== null) {
    //   if (eventDescription.trim().length > 4000) {
    //     setErrorDescription(
    //       "Số lượng ký tự đã vượt qua giới hạn là 4000 ký tự"
    //     );
    //     check += 1;
    //   } else {
    //     setErrorEventExpectedParticipants("");
    //   }
    // } else {
    //   setErrorDescription("");
    // }
    if (check === 0) {
      setEventAp(true);
      return true;
    } else {
      setEventAp(false);
      return false;
    }
  };

  // update event
  const showConfirmUpdate = () => {
    confirm({
      title: "Cảnh báo!!!",
      icon: <ExclamationCircleFilled />,
      content:
        "Sự kiện đã được phê duyệt. Bạn có chắc chắn muốn cập nhật thông tin?",
      onOk() {
        update();
      },
      onCancel() { },
    });
  };

  const handleUpdate = () => {
    if (isRoleHost === false && isRoleCoHost === false) {
      message.error("Bạn không có quyền yêu cập nhật sự kiện khi là Co-host");
    } else {
      if (eventStatus === 4 || eventStatus === 6) {
        showConfirmUpdate();
      } else {
        update();
      }
    }
    //console.log({ eventAp });
  };

  const update = () => {
    let checkAp = 0;
    let check = 0;
    if (!eventName) {
      setErrorEventName("Tên sự kiện không được để trống");
      check += 1;
      checkAp += 1;
    } else if (eventName.trim() === "") {
      setErrorEventName("Tên sự kiện không được để trống");
      check += 1;
      checkAp += 1;
    } else if (eventName.length > 500) {
      setErrorEventName("Tên sự kiện vượt quá 500 ký tự");
      check += 1;
      checkAp += 1;
    } else {
      setErrorEventName("");
    }
    if (!(eventEndDate || eventStartDate)) {
      setErrorDate("Thời gian diễn ra không được bỏ trống");
      check += 1;
      checkAp += 1;
    } else if (eventStartDate < new Date().getTime()) {
      setErrorDate("Thời gian bắt đầu phải là tương lai");
      check += 1;
      checkAp += 1;
    } else if (eventEndDate < new Date().getTime()) {
      setErrorDate("Thời gian kết thúc phải là tương lai");
      check += 1;
      checkAp += 1;
    } else if (eventStartDate > eventEndDate) {
      setErrorDate("Thời gian diễn ra phải sau thời gian kết thúc");
      check += 1;
      checkAp += 1;
    } else {
      setErrorDate("");
    }

    if (
      eventExpectedParticipants === undefined &&
      eventExpectedParticipants.length > 5
    ) {
      setErrorEventExpectedParticipants(
        "Dự kiến người tham gia không được quá giới hạn 5 ký tự"
      );
      check += 1;
      checkAp += 1;
    } else if (
      eventExpectedParticipants === undefined &&
      eventExpectedParticipants.length > 0 &&
      !/^\d+$/.test(eventExpectedParticipants)
    ) {
      setErrorEventExpectedParticipants("Dự kiến người tham gia phải là số");
      check += 1;
      checkAp += 1;
    } else if (
      eventExpectedParticipants === null ||
      eventExpectedParticipants === undefined ||
      eventExpectedParticipants === "" ||
      eventExpectedParticipants === 0
    ) {
      check += 1;
      checkAp += 1;
      setErrorEventExpectedParticipants(
        "Dự kiến người tham gia không được để trống"
      );
    } else if (
      eventExpectedParticipants > 9999
    ) {
      check += 1;
      checkAp += 1;
      setErrorEventExpectedParticipants(
        "Dự kiến người tham gia quá lớn"
      )
    } else {
      setErrorEventExpectedParticipants("");
    }

    if (
      eventCategory === null ||
      eventCategory === undefined ||
      eventCategory === ""
    ) {
      checkAp += 1;
    }
    if (
      eventObject.length === 0 ||
      eventObject.length === undefined ||
      eventObject.length === ""
    ) {
      checkAp += 1;
    }
    if (
      eventMajor.length === 0 ||
      eventMajor.length === undefined ||
      eventMajor.length === ""
    ) {
      checkAp += 1;
    }
    if (eventType === null || eventType === undefined || eventType === "") {
      checkAp += 1;
    }

    if (eventMajor === undefined || eventMajor === null || eventMajor.length === 0) {
      setErrorEventMajor("Chuyên ngành không được để trống");
      check += 1;
      checkAp += 1;
    } else {
      setErrorEventMajor("");
    }

    const eventTextDescription = eventDescription.replace(/(<([^>]+)>)/ig, '');

    if (eventTextDescription === null || eventTextDescription === undefined || eventTextDescription.trim().length === 0) {
      setErrorDescription("Mục tiêu sự kiện không được để trống");
      check += 1;
      checkAp += 1;
    }
    else if (eventTextDescription !== null && eventTextDescription.length > 4000) {
      setErrorDescription("Số lượng ký tự đã vượt qua giới hạn là 4000 ký tự");
      check += 1;
      checkAp += 1;
    } else {
      setErrorDescription("");
    }

    if (checkAp === 0) {
      setEventAp(true);
    } else {
      setEventAp(false);
    }

    if (check === 0) {
      let obj = {
        id: id,
        backgroundPath: background,
        bannerPath: banner,
        standeePath: standee,
        name: eventName,
        idCategory: eventCategory,
        idMajor: eventMajor.map(item => item.majorId),
        idObject: eventObject,
        eventType: eventType,
        startTime: eventStartDate,
        endTime: eventEndDate,
        expectedParticipants: eventExpectedParticipants,
        description: eventDescription,
        isHireLocation: isHireLocation,
        isHireDesignBanner: isHireDesignBanner,
        isHireDesignBg: isHireDesignBg,
        isHireDesignStandee: isHireDesignStandee,
        semesterId: eventSemesterId,
        block: eventBlock,
      };
      OREventDetailApi.updateEvent(obj).then(
        (response) => {
          message.success("Cập nhật thành công");
          eventDetail(id);
        },
        (error) => {
          message.warning(error.response.data.message);
        }
      );
    } else {
      setEventAp(false);
    }
  };
  // end update event

  // Đóng / mở đăng ký
  const toggleRegistration = () => {
    const obj = {
      id: id,
      isOpenRegistration: !isOpenRegistration,
    };
    //  //console.log(obj);
    OREventDetailApi.openOrCloseRegister(obj).then(
      (res) => {
        //  //console.log(res.data.data);
        if (res.data.data) {
          setIsOpenRegistration(!isOpenRegistration);
          message.success("Đã MỞ đăng ký");
        } else {
          message.success("Đã ĐÓNG đăng ký");
          setIsOpenRegistration(!isOpenRegistration);
        }
      },
      (err) => {
        //  //console.log(err.response);
      }
    );
  };

  //Đóng / mở điểm danh
  const toggleAttendance = () => {
    const obj = {
      id: id,
      isAttendance: !isAttendance,
    };
    if (
      eventStatus === 4 &&
      currentTime >= eventStartDate &&
      currentTime <= eventEndDate
    ) {
      OREventDetailApi.openOrCloseAttendance(obj).then(
        (res) => {
          setIsAttendance(!isAttendance);
          if (res.data.data) {
            message.success("Đã mở điểm danh");
          } else {
            message.success("Đã đóng điểm danh");
          }
        },
        (err) => {
          //  //console.log(err.response);
        }
      );
    } else {
      message.error("Chưa đễn thời gian diễn ra sự kiện");
    }
  };

  // Gửi yêu cầu phê duyệt sự kiện hàng kỳ
  const requestApprovePeriodically = () => {
    setIsApprovalPeriodically(1);
    const obj = {
      id: id,
      isWaitApprovalPeriodically: 1,
    };
    if (eventStatus === 5) {
      OREventDetailApi.approvePeriodicEvent(obj).then(
        (res) => {
          message.success("Gửi yêu cầu thành công");
        },
        (err) => {
          //  //console.log(err.response.data.message);
        }
      );
    } else {
      message.error("Chỉ gửi yêu cầu khi sự kiện đã tổ chức!!!");
    }
  };

  // Tái tổ chức sự kiện
  const [isModalReorganization, setIsModalReorganization] = useState(false);
  const showModalReorganization = () => {
    setIsModalReorganization(true);
  };
  const handleCancelReorganization = () => {
    setIsModalReorganization(false);
  };

  // Gửi tài nguyên sau sự kiện
  const sendMailResource = () => {
    if (eventStatus === 5) {
      OREventDetailApi.sendMailResource(id).then(
        (res) => {
          message.success("Gửi tài nguyên sau sự kiện thành công");
        },
        (err) => {
          //  //console.log(err.response.data.message);
        }
      );
    } else {
      message.error("Chỉ gửi khi sự kiện đã tổ chức!!!");
    }
  };

  // Modal gửi yêu cầu quy đổi
  const [isModalSendConverRequest, setIsModalSendConverRequest] =
    useState(false);
  const [numberHoney, setNumberHoney] = useState(1);
  const [honeyCategoryId, setHoneyCategoryId] = useState("");
  const [errNumberHoney, setErrNumberHoney] = useState("");
  const [errHoneyCategoryId, setErrHoneyCategoryId] = useState("");

  const showSendConverRequest = () => {
    // setListHoneyCategory(listData);
    setIsModalSendConverRequest(true);
  };

  // const handleSendConverRequest = () => {
  //   let check = 0;
  //   if (numberHoney.length <= 0) {
  //     setErrNumberHoney("Số mật ong không được để trống");
  //     check += 1;
  //   } else if (!/^\d+$/.test(numberHoney)) {
  //     setErrNumberHoney("Số mật ong phải là số lớn hơn 0");
  //     check += 1;
  //   } else {
  //     setErrNumberHoney("");
  //   }

  //   if (honeyCategoryId.length <= 0) {
  //     setErrHoneyCategoryId("Vui lòng chọn thể loại");
  //     check += 1;
  //   } else {
  //     setErrHoneyCategoryId("");
  //   }

  //   if (check === 0) {
  //     let obj = {
  //       eventId: id,
  //       numberHoney: numberHoney,
  //       honeyCategoryId: honeyCategoryId
  //     }
  //     OREventDetailApi.sendConversionRequest(obj).then(
  //       (res) => {
  //         setIsModalSendConverRequest(false);
  //         setHoneyCategoryId('');
  //         setNumberHoney(1);
  //         setIsConversionHoneyRequest(true);
  //         message.success("Gửi yêu cầu quy đổi mật ong thành công");
  //       },
  //       (err) => {
  //        //  //console.log("Error API get send request" + err);
  //       }
  //     );
  //   }

  // };
  const handleCancelSendConverRequest = () => {
    setIsModalSendConverRequest(false);
  };

  const updateEventStatus = (eventStatus) => {
    setEventStatus(eventStatus);
  };

  //modal từ chối
  const [open, setOpen] = useState(false);
  const [errMessage, setErrMessage] = useState("");
  const [reason, setReason] = useState("");

  const handleCancelOpen = () => {
    setOpen(false);
    setErrMessage("");
  };

  const showModalOpen = () => {
    setOpen(true);
  };

  const handleOkOpen = (id) => {
    setTimeout(() => {
      if (reason === null || reason.trim() === "") {
        setErrMessage("Bạn cần nhập lý do hủy yêu cầu phê duyệt!");
        return;
      }
      if (reason.length >= 1000) {
        setErrMessage("Bạn đã nhập quá giới hạn số lượng từ");
        return;
      }
      setErrMessage("");
      OREventDetailApi.approvalEvent({
        id: id,
        reason: reason,
        status: 2,
      })
        .then((response) => {
          setOpen(false);
          message.success("hủy yêu cầu phê duyệt thành công");
          loadAllData();
        })
        .catch((error) => {
          message.success("hủy yêu cầu phê duyệt thất bại");
          //  //console.log(error);
        });
    }, 100);
  };

  return (
    <>
      <div>
        <Form
          layout="vertical"
          name="basic"
          autoComplete="off"
          style={{ marginLeft: "25px", marginRight: "25px" }}
        >
          {/* ************* Detail ******************** */}
          <div style={{ overflow: "auto" }}>
            <h2 style={{ fontFamily: "sans-serif", color: "#172b4d" }}>
              <FontAwesomeIcon
                icon={faCircleInfo}
                style={{ color: "#172b4d" }}
              />
              <span style={{ marginLeft: "7px" }}>Thông tin sự kiện</span>
            </h2>

            <div
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                marginTop: "15px",
              }}
            >
              <Card
                className="custom-card"
                bordered={false}
                style={{
                  width: "95%",
                  borderRadius: "10px",
                  boxShadow: "0px 1px 3px rgba(0, 0, 0, 0.2)",
                  marginBottom: "25px",
                }}
              >
                <div
                  style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    marginBottom: "10px",
                  }}
                >
                  <h2
                    style={{
                      color:
                        eventStatus === 2
                          ? "red"
                          : eventStatus === 4
                            ? "green"
                            : "#0096cf",
                      display: "inline",
                      textTransform: "uppercase",
                    }}
                  >
                    Sự kiện{" "}
                    {eventStatus === 0
                      ? "Đóng"
                      : eventStatus === 1
                        ? "Dự kiến tổ chức"
                        : eventStatus === 2
                          ? "Cần sửa"
                          : eventStatus === 3
                            ? "Chờ phê duyệt"
                            : eventStatus === 4
                              ? "Đã phê duyệt" 
                              : eventStatus === 5
                                ? "Đã tổ chức"
                                : eventStatus === 6
                                  ? "Đã được bộ môn thông qua" : ""}
                  </h2>
                </div>
                <Row gutter={16}>
                  <Col span={8}>
                    <div style={{ marginLeft: "30px" }}>
                      <Form.Item className="form">
                        <span style={{ display: "inline", marginRight: "8px" }}>
                          Sự kiện cho:
                        </span>
                        <h3
                          className="text-color"
                          style={{
                            display: "inline",
                          }}
                        >
                          {object}
                        </h3>
                      </Form.Item>
                      {(eventStatus === 4 || eventStatus === 5) && (
                        <Form.Item>
                          <span
                            style={{ display: "inline", marginRight: "8px" }}
                          >
                            Phê duyệt:
                          </span>
                          <h3
                            style={{
                              display: "inline",
                            }}
                          >
                            {eventApprover}
                          </h3>
                        </Form.Item>
                      )}
                      {eventReason && (
                        <Form.Item className="form">
                          <span
                            style={{ display: "inline", marginRight: "8px" }}
                          >
                            {eventStatus === 0
                              ? "Lý do hủy: "
                              : "Lý do từ chối/hủy yêu cầu: "}
                          </span>
                          <h4 style={{ color: "#e13309", display: "inline" }}>
                            {" "}
                            {eventReason}
                          </h4>
                        </Form.Item>
                      )}
                    </div>
                  </Col>
                  <Col span={8}>
                    <div style={{ marginLeft: "30px" }}>
                      <Form.Item
                        className="form"
                        style={{ marginLeft: "30px" }}
                      >
                        <span style={{ display: "inline", marginRight: "8px" }}>
                          Số người đăng ký:
                        </span>
                        <h4 style={{ display: "inline" }}>
                          {" "}
                          {countParticipant > 0 ? countParticipant : 0}{" "}
                        </h4>
                      </Form.Item>
                      <Form.Item
                        className="form"
                        style={{ marginLeft: "30px" }}
                      >
                        <span style={{ display: "inline", marginRight: "8px" }}>
                          Số người tham gia:
                        </span>
                        <h4 style={{ display: "inline" }}>
                          {" "}
                          {countNumberParticipant > 0
                            ? countNumberParticipant
                            : 0}{" "}
                        </h4>
                      </Form.Item>
                    </div>
                  </Col>

                  <Col span={8}>
                    <div style={{ marginLeft: "30px" }}>
                      <Form.Item
                        className="form"
                        style={{ marginLeft: "30px" }}
                      >
                        {eventStatus === 5 && (
                          <>
                            <span
                              style={{ display: "inline", marginRight: "8px" }}
                            >
                              Đánh giá chung:
                            </span>
                            <h4 style={{ display: "inline" }}>
                              {" "}
                              {percentage}%{" "}
                            </h4>
                          </>
                        )}
                      </Form.Item>
                      {approvalPeriodicallyReason &&
                        isApprovalPeriodically === 0 && (
                          <Form.Item
                            className="form"
                            style={{ marginLeft: "30px" }}
                          >
                            <span
                              style={{ display: "inline", marginRight: "8px" }}
                            >
                              Lý do từ chối sự kiện hàng kỳ:
                            </span>
                            <h4 style={{ color: "#e13309", display: "inline" }}>
                              {approvalPeriodicallyReason}
                            </h4>
                          </Form.Item>
                        )}
                    </div>
                  </Col>
                </Row>
              </Card>
            </div>

            <div className="form_left">
              <Form.Item
                label="Tên sự kiện"
                name="eventName"
                rules={[
                  {
                    required: true,
                    message: "Please input your username!",
                  },
                ]}
              >
                <Input
                  placeholder="Nhập vào tên sự kiện"
                  value={eventName}
                  onChange={(e) => setEventName(e.target.value)}
                  disabled={isDisabled}
                  maxLength={500}
                />
                <span style={{ fontSize: 15, color: "red" }}>
                  {errorEventName}
                </span>
              </Form.Item>

              <Form.Item
                label="Học kỳ"
                rules={[{ required: true }]}
              >
                <Input
                  value={
                    eventSemester
                      ? eventSemester +
                      " (" +
                      renderDateTime(semesterStart) +
                      " --> " +
                      renderDateTime(semesterEnd) +
                      ")"
                      : ""
                  }
                  disabled={isDisabled}
                  style={{ opacity: 0.9 }}
                  readOnly
                />
              </Form.Item>

              <Form.Item
                label="Block"
                rules={[{ required: true }]}
              >
                <Input
                  value={
                    eventSemester
                      ? eventBlock === true
                        ? "Block 2 (" +
                        renderDateTime(startTimeBlock) +
                        " --> " +
                        renderDateTime(endTimeBlock) +
                        ")"
                        : "Block 1 (" +
                        renderDateTime(startTimeBlock) +
                        " --> " +
                        renderDateTime(endTimeBlock) +
                        ")"
                      : ""
                  }
                  readOnly
                  style={{ opacity: 0.9 }}
                  disabled={isDisabled}
                />
              </Form.Item>

              <Form.Item label="Background:" rules={[{ required: true }]}>
                <div>
                  {!isHireDesignBg ? (
                    <>
                      {isDisabledImg === false ? (
                        <>
                          <Button
                            disabled={isDisabledImg}
                            icon={<UploadOutlined />}
                            onClick={() => showModalImageUpload(background, 0)}
                          >
                            Tải lên
                          </Button>
                          &nbsp;
                          <Button
                            onClick={() => {
                              if (background === "" || background == null) {
                                message.error("Chưa có ảnh");
                              } else {
                                setImageSrc(background);
                                setVisible(true);
                              }
                            }}
                          >
                            Xem ảnh
                          </Button>
                        </>
                      ) : (
                        <Button
                          onClick={() => {
                            if (background === "" || background == null) {
                              message.error("Chưa có ảnh");
                            } else {
                              setImageSrc(background);
                              setVisible(true);
                            }
                          }}
                        >
                          Xem ảnh
                        </Button>
                      )}
                    </>
                  ) : (
                    <>
                      <Button
                        onClick={() => {
                          if (background === "" || background == null) {
                            message.error("Chưa có ảnh");
                          } else {
                            setImageSrc(background);
                            setVisible(true);
                          }
                        }}
                      >
                        Xem ảnh
                      </Button>
                    </>
                  )}
                  <Checkbox
                    disabled={isDisabled}
                    style={{ width: "60%", marginLeft: 20 }}
                    checked={isHireDesignBg}
                    onChange={(e) => {
                      setIsHireDesignBg(e.target.checked);
                    }}
                  >
                    Book design Background
                  </Checkbox>
                </div>
                <span style={{ fontSize: 15, color: "red" }}>
                  {errorBackGround}
                </span>
              </Form.Item>
              <Form.Item label="Banner:" rules={[{ required: true }]}>
                <div>
                  {!isHireDesignBanner ? (
                    <>
                      {isDisabledImg === false ? (
                        <>
                          <Button
                            disabled={isDisabledImg}
                            icon={<UploadOutlined />}
                            onClick={() => showModalImageUpload(banner, 1)}
                          >
                            Tải lên
                          </Button>
                          &nbsp;
                          <Button
                            onClick={() => {
                              if (banner === "" || banner == null) {
                                message.error("Chưa có ảnh");
                              } else {
                                setImageSrc(banner);
                                setVisible(true);
                              }
                            }}
                          >
                            Xem ảnh
                          </Button>
                        </>
                      ) : (
                        <Button
                          onClick={() => {
                            if (banner === "" || banner == null) {
                              message.error("Chưa có ảnh");
                            } else {
                              setImageSrc(banner);
                              setVisible(true);
                            }
                          }}
                        >
                          Xem ảnh
                        </Button>
                      )}
                    </>
                  ) : (
                    <>
                      <Button
                        onClick={() => {
                          if (banner === "" || banner == null) {
                            message.error("Chưa có ảnh");
                          } else {
                            setImageSrc(banner);
                            setVisible(true);
                          }
                        }}
                      >
                        Xem ảnh
                      </Button>
                    </>
                  )}

                  <Checkbox
                    disabled={isDisabled}
                    style={{ width: "60%", marginLeft: 20 }}
                    checked={isHireDesignBanner}
                    onChange={(e) => {
                      setIsHireDesignBanner(e.target.checked);
                    }}
                  >
                    Book design Banner
                  </Checkbox>
                </div>

                <span style={{ fontSize: 15, color: "red" }}>
                  {errorBanner}
                </span>
              </Form.Item>
              <Form.Item label="Standee:" rules={[{ required: true }]}>
                <div>
                  {!isHireDesignStandee ? (
                    <>
                      {isDisabledImg === false ? (
                        <>
                          <Button
                            icon={<UploadOutlined />}
                            disabled={isDisabledImg}
                            onClick={() => showModalImageUpload(standee, 2)}
                          >
                            Tải lên
                          </Button>
                          &nbsp;
                          <Button
                            onClick={() => {
                              if (standee === "" || standee === null) {
                                message.error("Chưa có ảnh");
                                setVisible(false);
                              } else {
                                setImageSrc(standee);
                                setVisible(true);
                              }
                            }}
                          >
                            Xem ảnh
                          </Button>
                        </>
                      ) : (
                        <Button
                          onClick={() => {
                            if (standee === "" || standee === null) {
                              message.error("Chưa có ảnh");
                              setVisible(false);
                            } else {
                              setImageSrc(standee);
                              setVisible(true);
                            }
                          }}
                        >
                          Xem ảnh
                        </Button>
                      )}
                    </>
                  ) : (
                    <>
                      <Button
                        onClick={() => {
                          if (standee === "" || standee === null) {
                            message.error("Chưa có ảnh");
                            setVisible(false);
                          } else {
                            setImageSrc(standee);
                            setVisible(true);
                          }
                        }}
                      >
                        Xem ảnh
                      </Button>
                    </>
                  )}

                  <Checkbox
                    width={"60%"}
                    style={{ width: "60%", marginLeft: 20 }}
                    disabled={isDisabled}
                    checked={isHireDesignStandee}
                    onChange={(e) => {
                      setIsHireDesignStandee(e.target.checked);
                    }}
                  >
                    Book design Standee
                  </Checkbox>
                </div>
              </Form.Item>
            </div>
            <div className="form_right">
              <Form.Item
                name="department"
                label="Bộ môn"
                rules={[
                  {
                    required: true,
                  }
                ]}
                direction="vertical"
              // extra="Thay đổi bộ môn phải chọn chuyên ngành lại từ đầu."
              >
                <Select
                  maxTagCount="responsive"
                  mode="multiple"
                  value={eventDepartment}
                  onChange={(value) => {
                    setEventDepartment(value);
                    if (value.length > 0) {
                      setEventMajor(eventMajor.filter(item => value.includes(item.departmentId)));
                      OREventDetailApi.getMajorCampus(value.join(",")).then(response => {
                        setListMajor(response.data.data);
                      });
                    } else {
                      setEventMajor([]);
                      setListMajor([]);
                    }
                  }}
                  disabled={isDisabled || !isCNBM}
                  options={listDepartment.map((department) => ({
                    value: department.id,
                    label: department.departmentName,
                  }))}
                  showSearch
                  filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                />
                <span style={{ fontSize: 15, color: "red" }}>
                  {errorEventCategory}
                </span>
              </Form.Item>

              <Form.Item
                name="major"
                label="Chuyên ngành"
                rules={[
                  {
                    required: true,
                  }
                ]}
                direction="vertical"
                tooltip={{
                  title: 'Bạn phải chọn bộ môn trước',
                  icon: <InfoCircleOutlined />,
                }}
              >
                <Select
                  mode="multiple"
                  style={{ width: "100%" }}
                  value={eventMajor.map(item => item.majorId + "," + item.departmentId)}
                  options={listMajor.map((major) => ({
                    value: major.id + "," + major.departmentId,
                    label: major.name,
                  }))}
                  onChange={(major) => {
                    setEventMajor(major.map(item => {
                      const [majorId, departmentId] = item.split(",");
                      return { majorId, departmentId };
                    }));
                  }}
                  placeholder="Select Item..."
                  maxTagCount="responsive"
                  disabled={isDisabled}
                  showSearch
                  filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                />
                <span style={{ fontSize: 15, color: "red" }}>
                  {errorEventMajor}
                </span>
              </Form.Item>

              <Form.Item name="eventTime" label="Thời gian diễn ra" rules={[{ required: true }]}>
                <RangePicker
                  inputReadOnly
                  style={{ width: "100%" }}
                  showTime={{
                    format: "HH:mm",
                  }}
                  disabled={isDisabled}
                  disabledDate={disabledDate}
                  format="HH:mm DD-MM-YYYY"
                  value={[
                    eventStartDate ? dayjs(eventStartDate) : null,
                    eventEndDate ? dayjs(eventEndDate) : null,
                  ]}
                  onOk={onOk}
                />
                <span style={{ fontSize: 15, color: "red" }}>{errorDate}</span>
              </Form.Item>
              <Form.Item name="eventObject" label="Đối tượng hướng đến" direction="vertical"
                rules={[
                  {
                    required: true,
                  },
                ]}
              >
                <Select
                  mode="multiple"
                  style={{ width: "100%" }}
                  value={eventObject}
                  options={listObject.map((object) => ({
                    value: object.id,
                    label: object.name,
                  }))}
                  onChange={(object) => {
                    setEventObject(object);
                  }}
                  placeholder="Select Item..."
                  maxTagCount="responsive"
                  disabled={isDisabled}
                  showSearch
                  filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                />
                <span style={{ fontSize: 15, color: "red" }}>
                  {errorEventObject}
                </span>
              </Form.Item>
              <Form.Item name="eventCategory" label="Thể loại sự kiện" rules={[{ required: true }]}>
                <Select
                  value={eventCategory}
                  onChange={(value) => setEventCategory(value)}
                  disabled={isDisabled}
                  options={listCategory.map((category) => ({
                    value: category.id,
                    label: category.name,
                  }))}
                  showSearch
                  filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                />
                <span style={{ fontSize: 15, color: "red" }}>
                  {errorEventCategory}
                </span>
              </Form.Item>
              <Form.Item
                name="eventExpectedParticipants"
                label="Số người tham gia dự kiến"
                rules={[{ required: true }]}
              >
                <InputNumber
                  disabled={isDisabled}
                  style={{ width: "100%" }}
                  placeholder="Nhập vào người dự kiến"
                  value={
                    eventExpectedParticipants ? eventExpectedParticipants : ""
                  }
                  min={1}
                  max={32000}
                  onChange={(value) => setEventExpectedParticipants(value)}
                />
                <span style={{ fontSize: 15, color: "red" }}>
                  {errorEventExpectedParticipants}
                </span>
              </Form.Item>
              <Form.Item
                label="Sự kiện dành cho"
                rules={[
                  {
                    required: true,
                    message: "Chọn kiểu sự kiện",
                  },
                ]}
              >
                <Radio.Group
                  disabled={isDisabled}
                  value={eventType}
                  onChange={(e) => {
                    setEventType(e.target.value);
                  }}
                >
                  <Radio value={1}>Giảng Viên</Radio>
                  <Radio value={0}>Sinh Viên</Radio>
                  <Radio value={2}>Giảng viên và sinh viên</Radio>
                </Radio.Group>
              </Form.Item>

              {/* <Form.Item
                label="Chuyên ngành"
                name="major"
                style={{
                  margin: '0'
                }}
              >
                <Select style={{ width: "100%" }}
                  // onChange={(value) => setIdMajor(value)}
                  defaultValue={eventMajor}
                  mode='multiple'
                >
                  {listMajor.map((item) => (
                    <Select.Option key={item.id} value={item.id}>
                      {item.name}
                    </Select.Option>
                  ))}
                </Select>
              </Form.Item> */}
              {/* <Form.Item
                label="Chuyên ngành"
                name="major"
                style={{
                  margin: "0",
                }}
              >
                <Select
                  style={{ width: "100%" }}
                  // onChange={(value) => setIdMajor(value)}
                >
                  {listMajor.map((item) => (
                    <Select.Option key={item.id} value={item.id}>
                      {item.name}
                    </Select.Option>
                  ))}
                </Select>
              </Form.Item> */}
              <Form.Item>
                <Checkbox
                  disabled={isDisabled}
                  checked={isHireLocation}
                  onChange={(e) => {
                    setIsHireLocation(e.target.checked);
                  }}
                >
                  Book địa điểm
                </Checkbox>
              </Form.Item>
            </div>
          </div>
          <div>
            <Row>
              <Col span={isDisabled ? 24 : 12}>
                <Form.Item
                  name="description"
                  rules={[
                    {
                      required: true,
                      message: "Chọn kiểu sự kiện",
                    },
                  ]}
                  label="Mục tiêu sự kiện"
                >
                  <FormEditor
                    value={eventDescription}
                    onchange={(description) => setEventDescription(description)}
                    isDisable={isDisabled}
                  />
                  <span style={{ fontSize: 15, color: "red" }}>
                    {errorDescription}
                  </span>
                </Form.Item>
              </Col>
              {!isDisabled && (
                <Col span={11} offset={1}>
                  {/* ************ Danh sách Công cụ  ************ */}
                  <OREDInvitationTime
                    startTime={eventStartDate}
                    eventStatus={eventStatus}
                  />
                </Col>
              )}
            </Row>
          </div>
          {/* ************* End Detail ******************** */}

          {/* ************ Danh sách button của các chức năng */}
          <div className="horizontal-layout"
            style={{
              padding: '10px 0',
            }}
          >
            {/* ************ Cập nhật sự kiện *************** */}
            <Popconfirm
              title="Bạn chắc chắn muốn cập nhật?"
              onConfirm={() => {
                handleUpdate();
              }}
              okText="Có"
              cancelText="Không"
            >
              <Button
                type="primary"
                className="btn-form-event"
                style={{
                  display: isDisabled
                    ? "none"
                    : eventStatus === 1
                      ? ""
                      : eventStatus === 2
                        ? ""
                        : eventStatus === 4
                          ? ""
                          : "none",
                }}
                onClick={(e) => e.stopPropagation()}
              >
                <FontAwesomeIcon
                  icon={faFloppyDisk}
                  style={{ color: "#ffffff", marginRight: "7px" }}
                />
                Cập nhật
              </Button>
            </Popconfirm>
            {/* ************ END Cập nhật sự kiện *************** */}

            {/* ************ Yêu câu phê duyệt *************** */}
            <Popconfirm
              title="Bạn chắc chắn muốn yêu cầu phê duyệt?"
              onConfirm={() => {
                handleUpdateStatusEvent();
              }}
              okText="Có"
              cancelText="Không"
            >
              {eventAp && isRoleHost === true ? (
                <Button
                  type="primary"
                  className="btn-form-event"
                  style={{
                    display: isDisabled
                      ? "none"
                      : eventStatus === 1
                        ? ""
                        : eventStatus === 2
                          ? ""
                          : "none",
                  }}
                  onClick={(e) => e.stopPropagation()}
                >
                  <FontAwesomeIcon
                    icon={faCodePullRequest}
                    style={{ color: "#ffffff", marginRight: "7px" }}
                  />
                  Yêu cầu phê duyệt
                </Button>
              ) : (
                <></>
              )}
            </Popconfirm>
            {/* ************ END Yêu câu phê duyệt *************** */}

            {/* ************ Đóng / mở đăng ký *************** */}
            {/* {eventStatus === 4 && currentTime < eventStartDate && !isDisabled && (
              <>
                {!isOpenRegistration ? (
                  <Button
                    type="primary"
                    className="btn-form-event"
                    onClick={toggleRegistration}
                  >
                    <FontAwesomeIcon
                      icon={faUserCheck}
                      style={{ color: "#ffffff", marginRight: "7px" }}
                    />
                    Mở đăng ký
                  </Button>
                ) : (
                  <Button
                    type="primary"
                    danger
                    className="btn-form-event"
                    onClick={toggleRegistration}
                  >
                    <FontAwesomeIcon
                      icon={faUserCheck}
                      style={{ color: "#ffffff", marginRight: "7px" }}
                    />
                    Đóng đăng ký
                  </Button>
                )}
              </>
            )} */}
            {/* ************ END Đóng / mở đăng ký *************** */}

            {/* ************ Đóng / mở điểm danh *************** */}
            {(isRoleHost || isRoleCoHost) && eventStatus === 4 && currentTime > eventStartDate && currentTime < eventEndDate && (
              <>
                {!isAttendance ? (
                  <Button
                    type="primary"
                    className="btn-form-event"
                    onClick={toggleAttendance}
                  >
                    <FontAwesomeIcon
                      icon={faListCheck}
                      style={{ color: "#ffffff", marginRight: "7px" }}
                    />
                    Mở điểm danh
                  </Button>
                ) : (
                  <Button
                    type="primary"
                    danger
                    className="btn-form-event"
                    onClick={toggleAttendance}
                  >
                    <FontAwesomeIcon
                      icon={faListCheck}
                      style={{ color: "#ffffff", marginRight: "7px" }}
                    />
                    Đóng điểm danh
                  </Button>
                )}
              </>
            )}
            {/* ************ END Đóng / mở điểm danh *************** */}

            {/* ************ Dẫn đến Màn Xem ds người tham gia *************** */}
            {(isRoleHost || isRoleCoHost) &&
              (eventStatus === 4 || eventStatus === 5) && (
                <Link to={`/organizer-management/registration-list/${id}`}>
                  <Button
                    type="primary"
                    style={{
                      marginRight: 10,
                      backgroundColor: green[700],
                    }}
                    htmlType="submit"
                    icon={<UserOutlined />}
                  >
                    DS tham gia
                  </Button>
                </Link>
              )}

            {/* ************ Dẫn đến Màn Xem ds điểm danh *************** */}
            {(isRoleHost || isRoleCoHost) &&
              (eventStatus === 4 || eventStatus === 5) && (
                <Link to={`/organizer-management/attendance-list/${id}`}>
                  <Button
                    style={{
                      marginRight: 10,
                      backgroundColor: yellow[900],
                    }}
                    type="primary"
                    htmlType="button"
                    icon={<FileDoneOutlined />}
                  >
                    DS điểm danh
                  </Button>
                </Link>
              )}

            {/* ************ Gửi Phê duyệt sự kiện hàng kỳ *************** */}
            {/* {eventStatus === 5 && isApprovalPeriodically === 0 && (
              <Popconfirm
                title="Bạn có chắc muốn yêu cầu phê duyệt hàng kỳ cho sự kiện này?"
                onConfirm={() => {
                  requestApprovePeriodically();
                }}
                okText="Có"
                cancelText="Không"
              >
                <Button type="primary" className="btn-form-event">
                  <FontAwesomeIcon
                    icon={faCodePullRequest}
                    style={{ color: "#ffffff", marginRight: "7px" }}
                  />
                  Phê duyệt hàng kỳ
                </Button>
              </Popconfirm>
            )} */}
            {/* ************ Hủy yêu cầu phê duyệt *************** */}
            {isRoleHost && (eventStatus === 3 || eventStatus === 6) && (
              <Button
                type="primary"
                className="btn-form-event"
                onClick={() => showModalOpen()}
              >
                <FontAwesomeIcon
                  icon={faCodePullRequest}
                  style={{ color: "#ffffff", marginRight: "7px" }}
                />
                Hủy yêu cầu phê duyệt
              </Button>
            )}
            {/* ************ Tái tổ chức sự kiện đã tổ chức *************** */}

            {/* ************ Tái tổ chức sự kiện đã tổ chức *************** */}
            {/* {isRoleHost && (eventStatus === 5 || eventStatus === 0) && (
              <Button
                type="primary"
                className="btn-form-event"
                onClick={() => showModalReorganization()}
              >
                <FontAwesomeIcon
                  icon={faCodePullRequest}
                  style={{ color: "#ffffff", marginRight: "7px" }}
                />
                Tái tổ chức sự kiện
              </Button>
            )} */}

            {/* ************ Gửi mail tài nguyên sau khi sự kiện đã tổ chức *************** */}
            {eventStatus === 5 && (
              <Popconfirm
                title="Bạn có chắc muốn gửi tài nguyên của sự kiện này cho sinh viên?"
                onConfirm={() => {
                  sendMailResource();
                }}
                okText="Có"
                cancelText="Không"
              >
                <Button
                  type="primary"
                  className="btn-form-event"
                // onClick={() => showModalReorganization()}
                >
                  <FontAwesomeIcon
                    icon={faPaperPlane}
                    style={{ color: "#ffffff", marginRight: "7px" }}
                  />
                  Gửi mail tài nguyên
                </Button>
              </Popconfirm>
            )}

            {/* ************ Gửi mail tài nguyên sau khi sự kiện đã tổ chức *************** */}

            {/* ************ Gửi yêu cầu quy đổi *************** */}
            {/* {eventStatus === 5 && !isConversionHoneyRequest && (
              <Button
                type="primary"
                className="btn-form-event"
                onClick={() => showSendConverRequest()}
                icon={<CloseCircleOutlined />}
              >
                Gửi yêu cầu quy đổi
              </Button>
            )} */}

            {/* ************ Đóng sự kiện *************** */}
            {(systemOption.isAllowCloseEvent &&
              new Date().getTime() <
              eventStartDate - systemOption.minimumCloseTime &&
              !isDisabled &&
              eventStatus !== 0 &&
              isRoleHost === true && (
                <Button
                  type="primary"
                  danger
                  className="btn-form-event"
                  style={{
                    float: "right",
                  }}
                  onClick={() => showModal()}
                  icon={<CloseCircleOutlined />}
                >
                  Đóng sự kiện
                </Button>
              )) || <></>}
            {/* ************ Đóng sự kiện *************** */}
            {/**********Show lịch sử sự kiện*************/}
            <ShowHistoryModal
              displayName={ORGANIZER_EVENT_DETAIL}
              eventId={id}
            />
          </div>
        </Form>
        <>
          <Divider />

          {/* ************ Modal Đóng sự kiện ************ */}
          <ModalCloseEvent
            visible={isModalOpen}
            onCancel={() => handleCancel()}
            detailEvent={(id) => eventDetail(id)}
            isRole={isRoleHost}
          />
          {/* ************ END Modal Đóng sự kiện ************ */}

          {/* ************ Modal Tái tổ chức sự kiện ************ */}
          <ModalEventReorganization
            loadAllData={loadAllData}
            visible={isModalReorganization}
            onCancel={() => handleCancelReorganization()}
            detailEvent={(id) => eventDetail(id)}
            isRole={isRoleHost}
          />
          {/* ************ END Modal Tái tổ chức sự kiện ************ */}

          {/* ************ Evidence sự kiện ************ */}
          {(isRoleHost || isRoleCoHost) &&
            (eventStatus === 4 || eventStatus === 5) &&
            currentTime > eventStartDate && (
              <OREDEvidence
                eventDetail={(id) => eventDetail(id)}
                actualNumberParticipants={numberParticipant}
                percentage={percentage}
                numberRegistrants={countParticipant}
                numberAttendees={countNumberParticipant}
                eventStatus={eventStatus}
                updateEventStatus={updateEventStatus}
              />
            )}

          {/* ************ Danh sách Người tổ chức ************ */}
          <OrganizersList
            isDisable={isDisabled}
            checkRole={() => checkRole()}
            checkRoleCoHost={() => checkRoleCoHost()}
            isRoleCoHost={isRoleCoHost}
            isRoleHost={isRoleHost}
          />

          {/* ************ Danh sách Địa điểm  ************ */}
          <OREDLocation isDisable={isDisabled} />

          {/* ************ Danh sách Tài nguyên  ************ */}
          <OREDResource isDisableResource={isDisabled} status={eventStatus} />

          {/* ************ Modal upload 3 loại ảnh   ************ */}
          <Modal
            title="Tải ảnh lên"
            visible={visibleImage}
            onOk={() => handleSaveImage()}
            onCancel={handleCancelImage}
          >
            <hr />
            <br />
            <Upload
              customRequest={() => { }}
              showUploadList={false}
              onChange={handleImageUpload}
            >
              <Button icon={<UploadOutlined />}>Chọn ảnh</Button>
            </Upload>
            {isExisted && (
              <div>
                <h4>Ảnh đã lưu:</h4>
                <img
                  src={selectedImage}
                  alt="Ảnh đã lưu"
                  style={{ maxWidth: "100%", borderRadius: "8px" }}
                />
              </div>
            )}
            {selectedImageChange && !isExisted && (
              <div>
                <h4>Ảnh đã chọn:</h4>
                <img
                  src={URL.createObjectURL(selectedImageChange)}
                  alt="Selected"
                  style={{ maxWidth: "100%", borderRadius: "8px" }}
                />
              </div>
            )}
          </Modal>
          {/* ************ END Modal upload 3 loại ảnh   ************ */}

          {/* ******** Xem ảnh ********* */}
          <Image
            className="top-element"
            preview={{
              visible: visible,
              src: imageSrc,
              onVisibleChange: (value) => {
                setVisible(value);
              },
            }}
          />

          {/* ********* Modal gửi yêu cầu quy đổi */}
          <Modal
            title={
              <div>
                <FontAwesomeIcon
                  icon={faCircleInfo}
                  style={{ color: "#172b4d" }}
                />{" "}
                Yêu cầu quy đổi
              </div>
            }
            open={isModalSendConverRequest}
            onCancel={handleCancelSendConverRequest}
            footer={null}
          >
            <hr />

            <div>
              <Form labelCol={{ span: 6 }}>
                <Form.Item
                  label="Số mật ong"
                  required
                  style={{ marginBottom: 10, marginTop: 10 }}
                >
                  <Input
                    name="numberHoney"
                    value={numberHoney}
                    onChange={(e) => setNumberHoney(e.target.value)}
                  />
                </Form.Item>
                {errNumberHoney && (
                  <span style={{ color: "red", marginLeft: 20 }}>
                    {errNumberHoney}
                  </span>
                )}
                <Form.Item
                  label="Thể loại"
                  required
                  style={{ marginBottom: 10 }}
                >
                  <Select
                    style={{ width: "100%" }}
                    value={honeyCategoryId}
                    options={listHoneyCategory.map((x) => ({
                      value: x.id,
                      label: x.name,
                    }))}
                    onChange={(categoryId) => {
                      setHoneyCategoryId(categoryId);
                    }}
                    placeholder="Chọn thể loại..."
                    allowClear
                    showSearch
                    filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                  />
                </Form.Item>
                {errHoneyCategoryId && (
                  <span style={{ color: "red", marginLeft: 20 }}>
                    {errHoneyCategoryId}
                  </span>
                )}
              </Form>
            </div>
            <br />
            <div style={{ textAlign: "right" }}>
              <div>
                <Popconfirm
                  title="Bạn có chắc muốn gửi yêu cầu quy đổi không?"
                  onConfirm={() => {
                    // handleSendConverRequest();
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
                    Gửi
                  </Button>
                </Popconfirm>

                <Button
                  style={{
                    backgroundColor: "red",
                    color: "white",
                  }}
                  onClick={handleCancelSendConverRequest}
                >
                  Hủy
                </Button>
              </div>
            </div>
          </Modal>

          {/* ************ Danh sách button của các chức năng */}
          {/*<div className="horizontal-layout">*/}
          {/*  /!* ************ Cập nhật sự kiện *************** *!/*/}
          {/*  <Popconfirm*/}
          {/*      title="Bạn chắc chắn muốn cập nhật?"*/}
          {/*      onConfirm={() => {*/}
          {/*        handleUpdate();*/}
          {/*      }}*/}
          {/*      okText="Có"*/}
          {/*      cancelText="Không"*/}
          {/*  >*/}
          {/*    <Button*/}
          {/*        type="primary"*/}
          {/*        className="btn-form-event"*/}
          {/*        style={{*/}
          {/*          display:*/}
          {/*              isDisabled ? "none" : eventStatus === 1 ? "" : eventStatus === 2 ? "" : eventStatus === 4 ? "" : "none",*/}
          {/*        }}*/}
          {/*        onClick={(e) => e.stopPropagation()}*/}
          {/*    >*/}
          {/*      <FontAwesomeIcon*/}
          {/*          icon={faFloppyDisk}*/}
          {/*          style={{color: "#ffffff", marginRight: "7px"}}*/}
          {/*      />*/}
          {/*      Cập nhật*/}
          {/*    </Button>*/}
          {/*  </Popconfirm>*/}
          {/*  /!* ************ END Cập nhật sự kiện *************** *!/*/}

          {/*  /!* ************ Yêu câu phê duyệt *************** *!/*/}
          {/*  <Popconfirm*/}
          {/*      title="Bạn chắc chắn muốn yêu cầu phê duyệt?"*/}
          {/*      onConfirm={() => {*/}
          {/*        handleUpdateStatusEvent();*/}
          {/*      }}*/}
          {/*      okText="Có"*/}
          {/*      cancelText="Không"*/}
          {/*  >*/}
          {/*    <Button*/}
          {/*        type="primary"*/}
          {/*        className="btn-form-event"*/}
          {/*        style={{*/}
          {/*          display:*/}
          {/*              isDisabled ? "none" : eventStatus === 1 ? "" : eventStatus === 2 ? "" : "none",*/}
          {/*        }}*/}
          {/*        onClick={(e) => e.stopPropagation()}*/}
          {/*    >*/}
          {/*      <FontAwesomeIcon*/}
          {/*          icon={faCodePullRequest}*/}
          {/*          style={{color: "#ffffff", marginRight: "7px"}}*/}
          {/*      />*/}
          {/*      Yêu cầu phê duyệt*/}
          {/*    </Button>*/}
          {/*  </Popconfirm>*/}
          {/*  /!* ************ END Yêu câu phê duyệt *************** *!/*/}

          {/*  /!* ************ Đóng / mở đăng ký *************** *!/*/}
          {/*  {eventStatus === 4 && !isDisabled && (*/}
          {/*      <>*/}
          {/*        {!isOpenRegistration ? (*/}
          {/*            <Button*/}
          {/*                type="primary"*/}
          {/*                className="btn-form-event"*/}
          {/*                onClick={toggleRegistration}*/}
          {/*            >*/}
          {/*              <FontAwesomeIcon*/}
          {/*                  icon={faUserCheck}*/}
          {/*                  style={{color: "#ffffff", marginRight: "7px"}}*/}
          {/*              />*/}
          {/*              Mở đăng ký*/}
          {/*            </Button>*/}
          {/*        ) : (*/}
          {/*            <Button*/}
          {/*                type="primary"*/}
          {/*                danger*/}
          {/*                className="btn-form-event"*/}
          {/*                onClick={toggleRegistration}*/}
          {/*            >*/}
          {/*              <FontAwesomeIcon*/}
          {/*                  icon={faUserCheck}*/}
          {/*                  style={{color: "#ffffff", marginRight: "7px"}}*/}
          {/*              />*/}
          {/*              Đóng đăng ký*/}
          {/*            </Button>*/}
          {/*        )}*/}
          {/*      </>*/}
          {/*  )}*/}
          {/*  /!* ************ END Đóng / mở đăng ký *************** *!/*/}

          {/*  /!* ************ Đóng / mở điểm danh *************** *!/*/}
          {/*  {eventStatus === 4 && !isDisabled && (*/}
          {/*      <>*/}
          {/*        {!isAttendance ? (*/}
          {/*            <Button*/}
          {/*                type="primary"*/}
          {/*                className="btn-form-event"*/}
          {/*                onClick={toggleAttendance}*/}
          {/*            >*/}
          {/*              <FontAwesomeIcon*/}
          {/*                  icon={faListCheck}*/}
          {/*                  style={{color: "#ffffff", marginRight: "7px"}}*/}
          {/*              />*/}
          {/*              Mở điểm danh*/}
          {/*            </Button>*/}
          {/*        ) : (*/}
          {/*            <Button*/}
          {/*                type="primary"*/}
          {/*                danger*/}
          {/*                className="btn-form-event"*/}
          {/*                onClick={toggleAttendance}*/}
          {/*            >*/}
          {/*              <FontAwesomeIcon*/}
          {/*                  icon={faListCheck}*/}
          {/*                  style={{color: "#ffffff", marginRight: "7px"}}*/}
          {/*              />*/}
          {/*              Đóng điểm danh*/}
          {/*            </Button>*/}
          {/*        )}*/}
          {/*      </>*/}
          {/*  )}*/}
          {/*  /!* ************ END Đóng / mở điểm danh *************** *!/*/}

          {/*  /!* ************ Dẫn đến Màn Xem ds người tham gia *************** *!/*/}
          {/*  {(eventStatus === 4 || eventStatus === 5) && (*/}
          {/*      <Link to={`/organizer-management/registration-list/${id}`}>*/}
          {/*        <Button*/}
          {/*            type="primary"*/}
          {/*            style={{*/}
          {/*              marginRight: 10,*/}
          {/*              backgroundColor: green[700],*/}
          {/*            }}*/}
          {/*            htmlType="submit"*/}
          {/*            icon={<UserOutlined/>}*/}
          {/*        >*/}
          {/*          DS tham gia*/}
          {/*        </Button>*/}
          {/*      </Link>*/}
          {/*  )}*/}

          {/*  /!* ************ Dẫn đến Màn Xem ds điểm danh *************** *!/*/}
          {/*  {(eventStatus === 4 || eventStatus === 5) && (*/}
          {/*      <Link to={`/organizer-management/attendance-list/${id}`}>*/}
          {/*        <Button*/}
          {/*            style={{*/}
          {/*              marginRight: 10,*/}
          {/*              backgroundColor: yellow[900],*/}
          {/*            }}*/}
          {/*            type="primary"*/}
          {/*            htmlType="button"*/}
          {/*            icon={<FileDoneOutlined/>}*/}
          {/*        >*/}
          {/*          DS điểm danh*/}
          {/*        </Button>*/}
          {/*      </Link>*/}
          {/*  )}*/}

          {/*  /!* ************ Gửi Phê duyệt sự kiện hàng kỳ *************** *!/*/}
          {/*  {eventStatus === 5 && isApprovalPeriodically === 0 && (*/}
          {/*      <Popconfirm*/}
          {/*          title="Bạn có chắc muốn yêu cầu phê duyệt hàng kỳ cho sự kiện này?"*/}
          {/*          onConfirm={() => {*/}
          {/*            requestApprovePeriodically();*/}
          {/*          }}*/}
          {/*          okText="Có"*/}
          {/*          cancelText="Không"*/}
          {/*      >*/}
          {/*        <Button type="primary" className="btn-form-event">*/}
          {/*          <FontAwesomeIcon*/}
          {/*              icon={faCodePullRequest}*/}
          {/*              style={{color: "#ffffff", marginRight: "7px"}}*/}
          {/*          />*/}
          {/*          Phê duyệt hàng kỳ*/}
          {/*        </Button>*/}
          {/*      </Popconfirm>*/}
          {/*  )}*/}

          {/*  /!* ************ Tái tổ chức sự kiện đã tổ chức *************** *!/*/}
          {/*  {(eventStatus === 5 || eventStatus === 0) && (*/}
          {/*      <Button*/}
          {/*          type="primary"*/}
          {/*          className="btn-form-event"*/}
          {/*          onClick={() => showModalReorganization()}*/}
          {/*      >*/}
          {/*        <FontAwesomeIcon*/}
          {/*            icon={faCodePullRequest}*/}
          {/*            style={{color: "#ffffff", marginRight: "7px"}}*/}
          {/*        />*/}
          {/*        Tái tổ chức sự kiện*/}
          {/*      </Button>*/}
          {/*  )}*/}

          {/*  /!* ************ Gửi mail tài nguyên sau khi sự kiện đã tổ chức *************** *!/*/}
          {/*  {eventStatus === 5 && (*/}
          {/*      <Popconfirm*/}
          {/*          title="Bạn có chắc muốn gửi tài nguyên của sự kiện này cho sinh viên?"*/}
          {/*          onConfirm={() => {*/}
          {/*            sendMailResource();*/}
          {/*          }}*/}
          {/*          okText="Có"*/}
          {/*          cancelText="Không"*/}
          {/*      >*/}
          {/*        <Button*/}
          {/*            type="primary"*/}
          {/*            className="btn-form-event"*/}
          {/*            // onClick={() => showModalReorganization()}*/}
          {/*        >*/}
          {/*          <FontAwesomeIcon*/}
          {/*              icon={faPaperPlane}*/}
          {/*              style={{color: "#ffffff", marginRight: "7px"}}*/}
          {/*          />*/}
          {/*          Gửi mail tài nguyên*/}
          {/*        </Button>*/}
          {/*      </Popconfirm>*/}
          {/*  )}*/}

          {/*  /!* ************ Gửi mail tài nguyên sau khi sự kiện đã tổ chức *************** *!/*/}

          {/*  /!* ************ Gửi yêu cầu quy đổi *************** *!/*/}
          {/*  {eventStatus === 5 && (*/}
          {/*      <Button*/}
          {/*          type="primary"*/}
          {/*          className="btn-form-event"*/}
          {/*          onClick={() => showSendConverRequest()}*/}
          {/*          icon={<CloseCircleOutlined/>}*/}
          {/*      >*/}
          {/*        Gửi yêu cầu quy đổi*/}
          {/*      </Button>*/}
          {/*  )}*/}

          {/*  /!* ************ Đóng sự kiện *************** *!/*/}
          {/*  <Button*/}
          {/*      type="primary"*/}
          {/*      danger*/}
          {/*      className="btn-form-event"*/}
          {/*      style={{*/}
          {/*        display: isDisabled ? "none" : eventStatus === 0 ? "none" : "",*/}
          {/*        float: "right",*/}
          {/*      }}*/}
          {/*      onClick={() => showModal()}*/}
          {/*      icon={<CloseCircleOutlined/>}*/}
          {/*  >*/}
          {/*    Đóng sự kiện*/}
          {/*  </Button>*/}
          {/*  /!* ************ Đóng sự kiện *************** *!/*/}
          {/*  /!**********Show lịch sử sự kiện*************!/*/}
          {/*  <ShowHistoryModal displayName={ORGANIZER_EVENT_DETAIL} eventId={id}/>*/}
          {/*</div>*/}
        </>
      </div>
      {contextHolder}
      <Modal
        title="Xác nhận hủy phê duyệt"
        open={open}
        onOk={() => handleOkOpen(id)}
        onCancel={handleCancelOpen}
      >
        <Form>
          <Form.Item label="Lý do: " required style={{ marginBottom: 10 }}>
            <Input.TextArea
              rows={5}
              name="reason"
              value={reason}
              onChange={(e) => setReason(e.target.value)}
            />
          </Form.Item>
          {errMessage && (
            <span style={{ color: "red", marginLeft: 57 }}>{errMessage}</span>
          )}
        </Form>
      </Modal>
    </>
  );
};
export default FormEvent;
