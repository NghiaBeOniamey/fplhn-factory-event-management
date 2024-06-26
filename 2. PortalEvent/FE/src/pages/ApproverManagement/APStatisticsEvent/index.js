import APSEChartTotalEvents from "./APSEChartTotalEvents";
import APSEListLecturerTop from "./APSEListLecturerTop";
import APSEEventInMajor from "./APSEEventInMajor";
import APSEChartTop from "./APSEChartTop";
import APSEParticipantInEvent from "./APSEParticipantInEvent";
import APStatisticsEventApi from "./APStatisticsEventApi";
import APSELecturerInEvent from "./APSELecturerInEvent";
import APSEParticipantInEventByCategory from "./APSEParticipantInEventByCategory";
import "./index.css";
import { useEffect, useState } from "react";
import { Form, Select } from "antd";
import dayjs from "dayjs";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFilter } from "@fortawesome/free-solid-svg-icons";

const APStatisticsEvent = () => {
    const [idSemester, setIdSemester] = useState("");
    const [listSemester, setListSemester] = useState([]);
    const [sumEvent, setSumevent] = useState();
    const [isLoading, setIsLoading] = useState();

    useEffect(() => {
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
    }, []);

    useEffect(() => {
        if (idSemester !== "") {
            fetchEvent(idSemester)
        }
    }, [idSemester]);

    const fetchEvent = async (idSemester) => {
        try {
            await APStatisticsEventApi.getSumEvent(idSemester).then(
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
                    <div style={{
                        marginBottom: "30px"
                    }}>
                        <h3 className="title-total">
                            <b>
                                <FontAwesomeIcon icon={faFilter} />
                                <span style={{ marginLeft: "7px" }}>Bộ lọc</span>
                            </b>
                        </h3>
                    </div>
                    <Form.Item label="Học kỳ" direction="vertical">
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
                    </Form.Item>
                    {idSemester === "" && <h3>Không có dữ liệu!</h3>}
                    {idSemester !== "" && (

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
                {idSemester !== "" && (
                    <>
                        <div className="div-container-chartTop">
                            <APSEChartTop semesterId={idSemester} />
                        </div>
                    </>
                )}
            </div>
            {idSemester !== "" && (
                <>
                    <div className="div-container-ORSEChartTotalEvents">
                        <APSEChartTotalEvents idSemester={idSemester} />
                    </div>
                    <div className="div-container-ORSEChartTotalEvents">
                        <APSEEventInMajor idSemester={idSemester} />
                    </div>
                    <div className={"div-container-APSEParticipantInEvent"}>
                        <APSEParticipantInEvent idSemester={idSemester} />
                    </div>
                    <div className={"div-container-APSELecturerInEvent"}>
                        <APSELecturerInEvent idSemester={idSemester} />
                    </div>
                    <div className={"div-container-APSEParticipantInEventByCategory"}>
                        <APSEParticipantInEventByCategory idSemester={idSemester} />
                    </div>
                    <div className={"div-container-APSEListLecturerTop"}>
                        <APSEListLecturerTop idSemester={idSemester} />
                    </div>
                </>
            )}
        </div>
    );
};

export default APStatisticsEvent;
