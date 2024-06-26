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
    const [listSemester, setListSemester] = useState([]);
    const [idDepartment, setIdDepartment] = useState("");
    const [listDepartment, setListDepartment] = useState([]);
    const [sumEvent, setSumevent] = useState();

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
                    setIdSemester(currentSemesters.id);
                }
            },
            (err) => {
                //  //console.log(err);
            }
        );
    }

    const getAllDepartment = () => {
        APStatisticsEventApi.getAllDepartment().then(
            (res) => {
                setListDepartment(res.data.data);
                const majorFist = res.data.data.find(
                    (major) => major.id
                )
                if (majorFist !== undefined) {
                    setIdDepartment(majorFist.id);
                }
            },
            (err) => {
                //  //console.log(err);
            }
        );
    }

    useEffect(() => {
        getAllDepartment();
    }, []);

    useEffect(() => {
        getAllSemester();
    }, []);



    useEffect(() => {
        if (idSemester !== "" && idDepartment !== "") {
            fetchEvent(idSemester, idDepartment)
        }
    }, [idSemester, idDepartment]);

    const fetchEvent = async (idSemester, idDepartment) => {
        try {
            await APStatisticsEventApi.getSumEvent(idSemester, idDepartment).then(
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
                    <Form.Item label="Học kỳ" direction="vertical">
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
                    <Form.Item label="Bộ môn" direction="vertical">
                        <Row
                            style={{
                                width: "100%",
                            }}
                        >
                            <Select
                                mode="single"
                                style={{ width: "100%" }}
                                options={listDepartment?.map((major) => ({
                                    value: major.id,
                                    label: major.name
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
                    {idSemester === "" || idDepartment === "" && <h3>Không có dữ liệu!</h3>}
                    {(idSemester !== "" && idDepartment !== "") && (

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
                {(idSemester !== "" && idDepartment !== "") && (
                    <>
                        <div className="div-container-chartTop">
                            <APSEChartTop semesterId={idSemester} majorId={idDepartment} />
                        </div>
                    </>
                )}
            </div>
            {(idSemester !== "" && idDepartment !== "") && (
                <>
                    <div className="div-container-ORSEChartTotalEvents">
                        <APSEChartTotalEvents idSemester={idSemester} idMajor={idDepartment} />
                    </div>
                    <div className="div-container-ORSEChartTotalEvents">
                        <APSEEventInMajor idSemester={idSemester} idMajor={idDepartment} />
                    </div>
                    <div className={"div-container-APSEParticipantInEvent"}>
                        <APSEParticipantInEvent idSemester={idSemester} idMajor={idDepartment} />
                    </div>
                    <div className={"div-container-APSELecturerInEvent"}>
                        <APSELecturerInEvent idSemester={idSemester} idMajor={idDepartment} />
                    </div>
                    <div className={"div-container-APSEParticipantInEventByCategory"}>
                        <APSEParticipantInEventByCategory idSemester={idSemester} idMajor={idDepartment} />
                    </div>
                    <div className={"div-container-APSEListLecturerTop"}>
                        <APSEListLecturerTop idSemester={idSemester} idMajor={idDepartment} />
                    </div>
                </>
            )}
        </div>
    );
};

export default APStatisticsEvent;
