import ORSEChartTotalEvents from "./ORSEChartTotalEvents";
import DoughnutChart from "./ORSEChartDoughnut";
import ORSEChartTop from "./ORSEChartTop";
import ORStatisticEventApi from "./ORStatisticsEventApi";
import "./index.css";
import { useEffect, useState } from "react";
import { Form, Select } from "antd";
import dayjs from "dayjs";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFilter } from "@fortawesome/free-solid-svg-icons";

const ORStatisticsEvent = () => {
    const [idSemester, setIdSemester] = useState("");
    const [listSemester, setListSemester] = useState([]);
    const [sumEvent, setSumevent] = useState();
    useEffect(() => {
        ORStatisticEventApi.getAllSemester().then(
            (res) => {
                setListSemester(res.data.data);
            },
            (err) => {
                //  //console.log(err);
            }
        );
    }, []);

    // Lấy ngày hiện tại cho lần chạy đầu tiên
    useEffect(() => {
        const now = new Date().getTime();
        const currentSemesters = listSemester.find(
            (semester) => semester.startTime <= now && semester.endTime >= now
        );
        if (currentSemesters !== undefined) {
            setIdSemester(currentSemesters.id);
        }
    }, [listSemester]);

    useEffect(() => {
       if (idSemester !== "") {
            fetchEvent(idSemester)
        }   
    }, [idSemester]);

    const fetchEvent = async (idSemester) => {
        ORStatisticEventApi.getSumEvent(idSemester).then(
            (res) => {
                setSumevent(res.data.data);
            },
            (err) => {
                //  //console.log(err);
            }
        );
    }

    return (
        <div className="div-hehe">
            <div className="div-top">
                <div className="div-1">
                    <h3 className="title-total">
                        <b>
                            <FontAwesomeIcon icon={faFilter} />
                            <span style={{ marginLeft: "7px" }}>Bộ lọc</span>
                        </b>
                    </h3>

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
                        <div className="div-container-doughnutChart">
                            <DoughnutChart semesterId={idSemester} />
                        </div>
                        <div className="div-container-chartTop-n">
                            <ORSEChartTop semesterId={idSemester} />
                        </div>
                    </>
                )}
            </div>
            {idSemester !== "" && (
                <div className="div-container-ORSEChartTotalEvents">
                    <ORSEChartTotalEvents idSemester={idSemester} />
                </div>
            )}
        </div>
    );
};

export default ORStatisticsEvent;
