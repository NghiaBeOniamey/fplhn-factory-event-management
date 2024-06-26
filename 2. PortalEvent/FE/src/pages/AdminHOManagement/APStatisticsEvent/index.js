import { faFilter } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Form, Row, Select } from "antd";
import dayjs from "dayjs";
import { useEffect, useState } from "react";
import APSEChartTop from "./APSEChartTop";
import APSEChartTotalEvents from "./APSEChartTotalEvents";
import APSEEventInMajor from "./APSEEventInMajor";
import APSELecturerInEvent from "./APSELecturerInEvent";
import APSEListLecturerTop from "./APSEListLecturerTop";
import APSEParticipantInEvent from "./APSEParticipantInEvent";
import APSEParticipantInEventByCategory from "./APSEParticipantInEventByCategory";
import APStatisticsEventApi from "./APStatisticsEventApi";
import "./index.css";

const APStatisticsEvent = () => {
    const [idSemester, setIdSemester] = useState("");
    const [idCampus, setIdCampus] = useState("");
    const [idDepartment, setIdDepartment] = useState("");
    const [listSemester, setListSemester] = useState([]);
    const [listCampus, setListCampus] = useState([]);
    const [listDepartment, setListDepartment] = useState([]);
    const [sumEvent, setSumevent] = useState();

    const [form] = Form.useForm();

    const getAllSemester = () => {
        APStatisticsEventApi.getAllSemester().then(
            (res) => {
                setListSemester(res.data.data);
                // Lấy ngày hiện tại cho lần chạy đầu tiên
                const now = new Date().getTime();
                const currentSemesters = res.data.data.find(
                    (semester) => semester.startTime <= now && semester.endTime >= now
                );
                if (currentSemesters !== undefined) {
                    setIdSemester(currentSemesters?.id);
                }
            },
            (err) => {
                //  //console.log(err);
            }
        );
    }

    const getAllCampus = () => {
        APStatisticsEventApi.getAllCampus().then(
            (res) => {
                setListCampus(res.data.data);
                setIdCampus(res.data.data[0]?.id);
            }, (err) => {
                //  //console.log(err);
            }
        );
    }


    const getDepartmentByCampusId = (campusId) => {
        APStatisticsEventApi.getDepartmentByCampusId(campusId).then(
            (res) => {
                setListDepartment(res.data.data);
                setIdDepartment(res.data.data[0]?.id);
            },
            (err) => {
                //  //console.log(err);
            }
        );
    }

    useEffect(() => {
        getAllSemester();
    }, []);

    useEffect(() => {
        getAllCampus();
    }, []);

    useEffect(() => {
        if (idCampus !== "") {
            getDepartmentByCampusId(idCampus);
        }
    }, [idCampus]);


    useEffect(() => {
        if (idSemester !== "" && idCampus !== "" && idDepartment !== "") {
            fetchEvent(idSemester, idCampus, idDepartment)
        }
    }, [idSemester, idCampus, idDepartment]);

    const fetchEvent = async (semesterId, campusId, departmentId) => {
        try {
            await APStatisticsEventApi.getSumEvent(semesterId, campusId, departmentId).then(
                (res) => {
                    setSumevent(res.data.data);
                })
        } catch (err) {
            //  //console.log(err);
        }
    }

    return (
        <div className="div-hehe">
            <div className="div-top">
                <div className="div-1">
                    <h3 className="title-total" style={{
                        marginBottom: "50px"
                    }}>
                        <b>
                            <FontAwesomeIcon icon={faFilter} />
                            <span style={{ marginLeft: "7px" }}>Bộ lọc</span>
                        </b>
                    </h3>
                    <Form
                        form={form}
                        name="basic"
                        initialValues={{
                            remember: true,
                        }}
                        autoComplete="off"
                    >
                        <Form.Item
                            label="Học kỳ"
                            direction="vertical"
                            name="semesters"
                            rules={[
                                {
                                    required: true,
                                    message: "",
                                },
                            ]}
                        >
                            <Row style={{
                                minWidth: "100%",
                            }}>
                                <Select
                                    mode="single"
                                    style={{ width: "100%" }}
                                    options={listSemester?.map((semester) => ({
                                        value: semester.id,
                                        label:
                                            semester.name +
                                            " (" +
                                            dayjs(semester.startTime).format("DD/MM/YYYY") +
                                            " - " +
                                            dayjs(semester.endTime).format("DD/MM/YYYY") +
                                            ")",
                                    }))}
                                    value={idSemester}
                                    onChange={(value) => setIdSemester(value)}
                                    placeholder="Select Item..."
                                    maxTagCount="responsive"
                                    showSearch
                                    filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                                />
                            </Row>
                        </Form.Item>
                        <Form.Item label="Cơ sở" direction="vertical"
                            name="campus"
                            rules={[
                                {
                                    required: true,
                                    message: "",
                                },
                            ]}
                        >
                            <Row
                                style={{
                                    width: "100%",
                                }}
                            >
                                <Select
                                    mode="single"
                                    style={{ width: "100%" }}
                                    options={listCampus?.map((campus) => ({
                                        value: campus.id,
                                        label: campus.name
                                    }))}
                                    value={idCampus}
                                    onChange={(value) => {
                                        setIdCampus(value);
                                    }}
                                    placeholder="Select Item..."
                                    maxTagCount="responsive"
                                    showSearch
                                    filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                                />
                            </Row>
                        </Form.Item>
                        <Form.Item label="Bộ môn" direction="vertical"
                            name="departments"
                            rules={[
                                {
                                    required: true,
                                    message: ""
                                },
                            ]}
                        >
                            <Row
                                style={{
                                    width: "100%",
                                }}
                            >
                                <Select
                                    mode="single"
                                    style={{ width: "100%" }}
                                    options={listDepartment?.map((department) => ({
                                        value: department.id,
                                        label: department.name
                                    }))}
                                    value={idDepartment}
                                    onChange={(value) => setIdDepartment(value)}
                                    placeholder="Select Item..."
                                    maxTagCount="responsive"
                                    showSearch
                                    filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                                />
                            </Row>
                        </Form.Item>
                    </Form>
                    {idSemester === "" || idCampus === "" || idDepartment === "" && <h3>Không có dữ liệu!</h3>}
                    {(idSemester !== "" && idCampus !== "" && idDepartment !== "") && (

                        <div className="div-sum-event">
                            <h3>
                                <b>
                                    <p
                                        style={{
                                            marginLeft: "7px",
                                            fontFamily: "sans-serif",
                                            fontSize: "24px",
                                            color: "white",
                                            textAlign: "center",
                                        }}
                                    >
                                        TỔNG SỰ KIỆN
                                    </p>
                                    <p
                                        style={{
                                            marginLeft: "7px",
                                            fontFamily: "sans-serif",
                                            fontSize: "50px",
                                            color: "white",
                                            textAlign: "center",
                                        }}
                                    >
                                        {sumEvent}
                                    </p>
                                </b>
                            </h3>
                        </div>
                    )}
                </div>
                {(idSemester !== "" && idCampus !== "" && idDepartment !== "") && (
                    <>
                        <div className="div-container-chartTop">
                            <APSEChartTop
                                {...{
                                    campusId: idCampus,
                                    departmentId: idDepartment,
                                    semesterId: idSemester
                                }}
                            />
                        </div>
                    </>
                )}
            </div>
            {(idSemester !== "" && idCampus !== "" && idDepartment !== "") && (
                <>
                    <div className="div-container-ORSEChartTotalEvents">
                        <APSEChartTotalEvents
                            {...{
                                idCampus,
                                idDepartment,
                                idSemester
                            }}
                        />
                    </div>
                    <div className="div-container-ORSEChartTotalEvents">
                        <APSEEventInMajor
                            {...{
                                idCampus,
                                idDepartment,
                                idSemester
                            }}
                        />
                    </div>
                    <div className={"div-container-APSEParticipantInEvent"}>
                        <APSEParticipantInEvent
                            {...{
                                idCampus,
                                idDepartment,
                                idSemester
                            }}
                        />
                    </div>
                    <div className={"div-container-APSELecturerInEvent"}>
                        <APSELecturerInEvent
                            {...{
                                idCampus,
                                idDepartment,
                                idSemester
                            }}
                        />
                    </div>
                    <div className={"div-container-APSEParticipantInEventByCategory"}>
                        <APSEParticipantInEventByCategory
                            {...{
                                idCampus,
                                idDepartment,
                                idSemester
                            }}
                        />
                    </div>
                    <div className={"div-container-APSEListLecturerTop"}>
                        <APSEListLecturerTop
                            {...{
                                idCampus,
                                idDepartment,
                                idSemester
                            }}
                        />
                    </div>
                </>
            )}
        </div>
    );
};

export default APStatisticsEvent;
