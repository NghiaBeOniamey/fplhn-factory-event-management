import {
    Button,
    Col,
    DatePicker,
    Form,
    Input,
    InputNumber,
    message,
    Row,
    Select,
    Space,
} from "antd";
import React, { useContext, useEffect, useState } from "react";
import { AppContextregister } from "./context";
import OREventRegisterApi from "./OREventRegisterApi";
import { Option } from "rc-select";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBan, faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import dayjs from "dayjs";
import FormJoditEditor from "./Jodit";
import { dateTimeFromLong } from "../../../utils/Converter";
import { DatePickerProps, RangePickerProps } from "antd/es/date-picker";
import { getCurrentUser } from "../../../utils/Common";
import { InfoCircleOutlined } from "@ant-design/icons";

const { RangePicker } = DatePicker;

const FormComponent = (props) => {
    const navigate = useNavigate();

    let [nameSemester, setNameSemester] = useState("");

    const [formRegister] = Form.useForm();

    const [loading, setLoading] = useState(true);

    const user = getCurrentUser();

    const {
        name,
        setName,
        idCategory,
        setIdCategory,
        startTime,
        setStartTime,
        endTime,
        setEndTime,
        idSemester,
        setIdSemester,
        idObject,
        setIdObject,
        idMajor,
        setIdMajor, // id varchar lưu db
        idDepartment,
        setIdDepartment,
        contentEditor,
        setContentEditor,
        listSemester,
        setListSemester,
        listObject,
        setListObject,
        listMajor,
        setListMajor,
        listMajorShow,
        setListMajorShow, // get all và không thay đổi
        listDepartment,
        setListDepartment,
        listDepartmentByCampus,
        setListDepartmentByCampus,
        listMajorCampus,
        setListMajorCampus,
        listCategory,
        setListCategory,
        eventType,
        setEventType,
        blockNumber,
        setBlockNumber,
        expectedParticipants,
        setExpectedParticipants,
        isGV,
        isCNBM,
        subjectCode,
    } = useContext(AppContextregister);
    const [nameErrorMessage, setNameErrorMessage] = useState("");
    const [timeErrorMessage, setTimeErrorMessage] = useState("");
    const [descriptionErrorMessage, setDescriptionErrorMessage] = useState("");
    const [
        expectedParticipantsErrorMessage,
        setExpectedParticipantsErrorMessage,
    ] = useState("");
    const [idCategoryErrorMessage, setIdCategoryErrorMessage] = useState("");
    const [idMajorErrorMessage, setIdMajorErrorMessage] = useState("");
    const [departmentIdLongs, setDepartmentIdLongs] = useState([]);

    const defaultDepartment = () => {
        if (listDepartmentByCampus.length !== 0) {
            for (const department of listDepartmentByCampus) {
                if (department.departmentCode === subjectCode) {
                    setIdDepartment([...idDepartment, department.id]);
                    // danh sachs id department đc chọn trong slect option
                    formRegister.setFieldsValue({ department: department.id });
                }
            }
        }
    };

    const getIdLongDepartment = () => {
        const newDepartmentIdLongs = [];
        for (const department of listDepartmentByCampus) {
            for (const id of idDepartment) {
                if (department.id === id) {
                    newDepartmentIdLongs.push(department.departmentCampusId);
                }
            }
        }
        const uniqueArray = newDepartmentIdLongs.filter(
            (item, index) => newDepartmentIdLongs.indexOf(item) === index
        );
        setDepartmentIdLongs(uniqueArray);
    };

    const fillMajor = () => {
        if (departmentIdLongs.length === 0) {
            setListMajor([]);
            return;
        }
        const listMajorByDepartment = [];
        for (const departmentIdLong of departmentIdLongs) {
            for (const major of listMajorCampus) {
                if (departmentIdLong === major.departmentCampusId) {
                    listMajorByDepartment.push(major);
                }
            }
        }
        let newIdMajor = [];
        for (const id of idMajor) {
            newIdMajor = listMajorByDepartment.filter((major) => major.id !== id);
        }

        // formRegister.resetFields({ 'major': []});
        for (const id of newIdMajor) {
            formRegister.setFieldsValue({ major: id });
        }
        // setIdMajor(newIdMajor);
        setListMajor(listMajorByDepartment);
    };



    const findSemester = () => {
        let startDate = startTime ? new Date(startTime).getTime() : null;
        let endDate = endTime ? new Date(endTime).getTime() : null;

        let foundSemester = null;
        let foundStartBlock = null; // Biến để theo dõi block cho ngày bắt đầu
        let foundEndBlock = null; // Biến để theo dõi block cho ngày kết thúc

        for (const semester of listSemester) {
            if (startDate >= semester.startTime && endDate <= semester.endTime) {
                //61200000 là 17 giờ vì endTimeFirst luôn là 7h sáng
                setIdSemester(semester.id);
                foundSemester = semester.id;
                setNameSemester(semester.name);
                formRegister.setFieldsValue({ nameSemester: semester.name });

                if (
                    startDate >= semester.startTimeFirstBlock &&
                    startDate <= semester.endTimeFirstBlock
                ) {
                    foundStartBlock = false; // Tìm thấy block cho ngày bắt đầu
                } else if (
                    startDate >= semester.startTimeSecondBlock &&
                    startDate <= semester.endTimeSecondBlock
                ) {
                    foundStartBlock = true; // Tìm thấy block cho ngày bắt đầu
                }

                if (
                    endDate >= semester.startTimeFirstBlock &&
                    endDate <= semester.endTimeFirstBlock //61200000 là 17 giờ vì endTimeFirst luôn là 7h sáng
                ) {
                    foundEndBlock = false; // Tìm thấy block cho ngày kết thúc
                } else if (
                    endDate >= semester.startTimeSecondBlock &&
                    endDate <= semester.endTimeSecondBlock //61200000 là 17 giờ vì endTimeFirst luôn là 7h sáng
                ) {
                    foundEndBlock = true; // Tìm thấy block cho ngày kết thúc
                }
                break;
            }
        }

        if (startDate !== null && endDate !== null) {
            if (foundSemester === null) {
                setIdSemester(null);
                setBlockNumber(null);
                message.error("Không tìm thấy học kỳ phù hợp");
            } else {
                setIdSemester(foundSemester);
                if (foundStartBlock !== null && foundEndBlock !== null) {
                    if (foundStartBlock !== foundEndBlock) {
                        message.error("Không tìm thấy block phù hợp");
                        setBlockNumber(null);
                    } else {
                        // Ngày bắt đầu và kết thúc ở cùng một block
                        setBlockNumber(foundStartBlock);
                        formRegister.setFieldsValue({ blockNumber: foundStartBlock ? "Block 2" : "Block 1" });
                    }
                }
            }
        } else {
            setBlockNumber(null);
        }
    };

    useEffect(() => {
        defaultDepartment();
    }, [listDepartmentByCampus]);

    useEffect(() => {
        fillMajor();
    }, [departmentIdLongs]);

    useEffect(() => {
        getIdLongDepartment();
    }, [idDepartment]);

    useEffect(() => {
        findSemester();
    }, [startTime, endTime]);

    function compareTime(startTime, endTime) {
        const startTimeStamp = Math.floor(new Date(startTime).getTime());
        const endTimeStamp = Math.floor(new Date(endTime).getTime());
        return !(
            startTimeStamp > endTimeStamp ||
            dateTimeFromLong(startTimeStamp) === dateTimeFromLong(endTimeStamp)
        );
    }

    function cleanData() {
        //console.log(subjectCode);
        setIdSemester("");
        setIdCategory("");
        setIdObject([]);
        setIdMajor([]);
        // setIdDepartment([]);
        setName("");
        setStartTime("");
        setEndTime("");
        setContentEditor("");
        setEventType(undefined);
        setBlockNumber(null);
        setExpectedParticipants(0);
        setNameSemester("");
    }

    const disabledDate = (current) => {
        const today = dayjs().startOf("day");
        return current < today;
    };

    const onFinish = () => {
        let data = {
            idCategory: idCategory,
            idSemester: idSemester,
            listObject: idObject,
            name: name,
            startTime: startTime,
            endTime: endTime,
            eventType: eventType,
            blockNumber: blockNumber,
            description: contentEditor,
            listMajor: idMajor.map(item => item.majorId),
            listDepartment: idDepartment,
            expectedParticipants: Number(expectedParticipants),
        };

        let check = true;

        if (
            data.name.trim() === "" ||
            data.name.trim() === undefined ||
            data.name.length > 244
        ) {
            setNameErrorMessage("Vui lòng nhập đúng định dạng tên sự kiện");
            check = false;
        } else {
            setNameErrorMessage("");
        }

        if (data.startTime === "" || data.endTime === "") {
            setTimeErrorMessage("Vui lòng chọn thời gian diễn ra sự kiện");
            check = false;
        } else if (!compareTime(data.startTime, data.endTime)) {
            setTimeErrorMessage(
                "Thời gian bắt đầu không thể lớn hơn, hoặc bằng thời gian kết thúc!!!"
            );
            check = false;
        } else {
            setTimeErrorMessage("");
        }

        if (data.expectedParticipants >= 23000 || data.expectedParticipants < 0) {
            setExpectedParticipantsErrorMessage(
                "Vui lòng điền số người dự kiện đúng định dạng"
            );
            check = false;
        } else {
            setExpectedParticipantsErrorMessage("");
        }

        if (data.idCategory === "" || data.idCategory === undefined || data.idCategory === null) {
            setIdCategoryErrorMessage("Vui lòng chọn loại sự kiện");
            check = false;
        } else {
            setIdCategoryErrorMessage("");
        }

        if (data.listMajor === undefined || data.listMajor === null || data.listMajor.length === 0) {
            setIdMajorErrorMessage("Vui lòng chọn chuyên ngành của sự kiện");
            check = false;
        } else {
            setIdMajorErrorMessage("");
        }
        //console.log(data.listMajor);
        if (check) {
            // //console.log("Dữ liệu đăng ký");
            // //console.log({ data });
            // message.success('Đăng ký thành công');
            OREventRegisterApi.register(data)
                .then((response) => {
                    message.success("Đăng ký thành công");
                    navigate(
                        "/organizer-management/event-detail/" + response.data.data.id
                    );
                })
                .catch((error) => {
                    message.warning(error.response.data.message);
                });
        } else {
            message.info("Vui lòng điền các thông tin cần thiết");
        }
        cleanData();
        formRegister.resetFields();
        defaultDepartment();
    };

    const clearFormInformations = () => {
        cleanData();
        formRegister.resetFields();
        defaultDepartment();
    };

    return (
        <div>
            <Form form={formRegister} layout="vertical">
                <Space
                    direction="vertical"
                    size="middle"
                    style={{ display: "flex", marginTop: "20px" }}
                >
                    <Row style={{ justifyContent: "space-between" }}>
                        <Col span={10} offset={-3}>
                            <Form.Item
                                name="name"
                                style={{
                                    margin: "0",
                                }}
                                required
                                label="Tên sự kiện"
                            >
                                <Input
                                    maxLength="500"
                                    placeholder="Nhập tên sự kiện"
                                    onChange={(e) => setName(e.target.value)}
                                />
                            </Form.Item>
                            <span style={{ color: "red" }}>{nameErrorMessage}</span>
                        </Col>

                        <Col Col span={11}>
                            <Form.Item
                                name="category"
                                style={{
                                    margin: "0",
                                }}
                                label="Thể loại"
                                required
                            >
                                <Select
                                    style={{ width: "100%" }}
                                    onChange={(value) => setIdCategory(value)}
                                    showSearch
                                    filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                                    options={listCategory?.map((item) => ({
                                        value: item.id,
                                        label: item.name,
                                    }))}
                                />
                                {/* {listCategory.map((item) => (
                                        <Select.Option key={item.id} value={item.id}>
                                            {item.name}
                                        </Select.Option>
                                    ))}
                                </Select> */}
                                <span style={{ color: "red" }}>{idCategoryErrorMessage}</span>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row style={{ justifyContent: "space-between" }}>
                        <Col span={10} offset={-3}>
                            <Form.Item
                                name="time"
                                style={{
                                    margin: "0",
                                }}
                                label="Thời gian diễn ra"
                                required
                            >
                                <RangePicker
                                    disabledDate={disabledDate}
                                    showTime={{ format: "HH:mm" }}
                                    format="YYYY-MM-DD HH:mm"
                                    popupStyle={{ maxHeight: "300px" }}
                                    value={[
                                        startTime !== "" ? dayjs(new Date(startTime)) : "",
                                        endTime !== "" ? dayjs(new Date(endTime)) : "",
                                    ]}
                                    style={{ width: "100%" }}
                                    onChange={(
                                        value,
                                        dateString
                                    ) => {
                                        setStartTime(
                                            dateString[0] !== ""
                                                ? new Date(dateString[0]).valueOf()
                                                : ""
                                        );
                                        setEndTime(
                                            dateString[1] !== ""
                                                ? new Date(dateString[1]).valueOf()
                                                : ""
                                        );
                                    }}
                                />
                            </Form.Item>
                            <span style={{ color: "red" }}>{timeErrorMessage}</span>
                        </Col>
                        <Col span={11} offset={-3}>
                            <Form.Item
                                name="object"
                                style={{
                                    margin: "0",
                                }}
                                label="Đối tượng"
                            >
                                <Select
                                    style={{ width: "100%" }}
                                    onChange={(value) => setIdObject(value)}
                                    mode="multiple"
                                    showSearch
                                    filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                                    options={listObject?.map((item) => ({
                                        value: item.id,
                                        label: item.name,
                                    }))}
                                />
                                {/* {listObject.map((item) => (
                                        <Select.Option key={item.id} value={item.id}>
                                            {item.name}
                                        </Select.Option>
                                    ))}
                                </Select> */}
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row style={{ justifyContent: "space-between" }}>
                        <Col span={10} offset={-3}>
                            <Form.Item
                                name="blockNumber"
                                style={{
                                    margin: "0",
                                }}
                                label="Block"
                                tooltip={{
                                    title: 'Hệ thống tự động điền',
                                    icon: <InfoCircleOutlined />,
                                }}
                            >
                                <Input
                                    readOnly
                                    value={
                                        blockNumber === null
                                            ? ""
                                            : blockNumber === false
                                                ? "Block 1"
                                                : "Block 2"
                                    }
                                />
                            </Form.Item>
                        </Col>
                        <Col span={11} offset={-3}>
                            <Form.Item
                                name="eventType"
                                style={{
                                    margin: "0",
                                }}
                                label="Sự kiện dành cho"
                            >
                                <Select
                                    style={{ width: "100%" }}
                                    onChange={(value) => setEventType(value)}
                                >
                                    <Option value={0}>Sinh viên</Option>
                                    <Option value={1}>Giảng viên</Option>
                                    <Option value={2}>Tất cả</Option>
                                </Select>
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row style={{ justifyContent: "space-between" }}>
                        <Col span={10} offset={-3}>
                            <Form.Item
                                name="nameSemester"
                                style={{
                                    margin: "0",
                                }}
                                label="Học kỳ"
                                tooltip={{
                                    title: 'Hệ thống tự động điền',
                                    icon: <InfoCircleOutlined />,
                                }}
                                extra="Học kỳ và bộ môn được fill tự động khi chọn thời gian diễn ra."
                            >
                                <Input value={nameSemester} readOnly />
                            </Form.Item>
                        </Col>
                        <Col span={11} offset={-3}>
                            <Form.Item
                                label="Bộ môn"
                                name="department"
                                style={{
                                    margin: "0",
                                }}
                            // extra="Thay đổi bộ môn phải chọn chuyên ngành lại từ đầu."
                            >
                                <Select
                                    style={{ width: "100%" }}
                                    onChange={(value) => {
                                        if (value.length > 0) {
                                            formRegister.setFieldsValue({ major: [] });
                                            setIdDepartment(value);
                                            setIdMajor(idMajor.filter(item => value.includes(item.departmentId)));
                                        } else {
                                            setIdDepartment([]);
                                            setIdMajor([]);
                                        }
                                    }}
                                    mode="multiple"
                                    disabled={!isCNBM}
                                    value={idDepartment}
                                    maxTagCount="responsive"
                                    showSearch
                                    filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                                    options={listDepartmentByCampus?.map((item) => ({
                                        value: item.id,
                                        label: item.departmentName,
                                    }))}
                                />
                                {/* {listDepartmentByCampus.map((item) => (
                                        <Select.Option key={item.id} value={item.id}>
                                            {item.departmentName}
                                        </Select.Option>
                                    ))}
                                </Select> */}
                            </Form.Item>
                        </Col>
                    </Row>
                    <Row style={{ justifyContent: "space-between" }}>
                        <Col span={10} offset={-3}>
                            <Form.Item
                                label="Số người tham gia dự kiến"
                                name="expectedParticipants"
                                style={{
                                    margin: "0",
                                }}
                            >
                                <InputNumber
                                    type="number"
                                    style={{ width: "100%" }}
                                    rootClassName={"mb-5"}
                                    min={0}
                                    max={32760}
                                    value={expectedParticipants}
                                    onChange={(e) => setExpectedParticipants(e)}
                                />
                            </Form.Item>
                            <span style={{ color: "red" }}>
                                {expectedParticipantsErrorMessage}
                            </span>
                        </Col>
                        <Col span={11} offset={-3}>
                            <Form.Item
                                name="major"
                                label="Chuyên ngành"
                                style={{
                                    margin: "0",
                                }}
                                tooltip={{
                                    title: 'Bạn phải chọn bộ môn trước',
                                    icon: <InfoCircleOutlined />,
                                }}
                                required
                            >
                                <Select
                                    style={{ width: "100%" }}
                                    onChange={(value) => {
                                        setIdMajor(value.map(item => {
                                            const [majorId, departmentId] = item.split(",");
                                            return { majorId, departmentId };
                                        }));
                                    }}
                                    value={idMajor.map(item => item.majorId + "," + item.departmentId)}
                                    mode="multiple"
                                    maxTagCount="responsive"
                                    showSearch
                                    filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                                    options={listMajor?.map((item) => ({
                                        value: item.id + "," + item.departmentId,
                                        label: item.name,
                                    }))}
                                />
                                <span style={{ color: "red" }}>{idMajorErrorMessage}</span>
                                {/* {listMajor.map((item) => (
                                        <Select.Option key={item.id} value={item.id}>
                                            {item.name}
                                        </Select.Option>
                                    ))}
                                </Select> */}
                            </Form.Item>
                        </Col>
                    </Row>

                    <Row>
                        <Col span={24}>
                            <Form.Item
                                label="Mục tiêu sự kiện"
                                name="description"
                                style={{
                                    margin: "0",
                                }}
                            >
                                <FormJoditEditor
                                    // maxLength="10"
                                    value={contentEditor}
                                    onChange={(description) => setContentEditor(description)}
                                ></FormJoditEditor>
                            </Form.Item>
                            <span style={{ color: "red" }}>{descriptionErrorMessage}</span>
                        </Col>
                    </Row>

                    <Row justify="center">
                        <Button
                            type="primary"
                            htmlType="button"
                            style={{ marginBottom: "0.8rem", marginRight: "10px" }}
                            onClick={onFinish}
                        >
                            <FontAwesomeIcon
                                icon={faPenToSquare}
                                style={{ marginRight: "0.5rem" }}
                            />
                            Đăng ký sự kiện
                        </Button>
                        <Button
                            type="primary"
                            style={{ backgroundColor: "#dc3545" }}
                            htmlType="reset"
                            onClick={clearFormInformations}
                        >
                            <FontAwesomeIcon icon={faBan} style={{ marginRight: "0.5rem" }} />
                            Clear form
                        </Button>
                    </Row>
                </Space>
            </Form>
        </div>
    );
};

export default FormComponent;
