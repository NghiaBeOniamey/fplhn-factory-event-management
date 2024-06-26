import React, { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import APStatisticsEventApi from "../APStatisticsEventApi";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarDays } from "@fortawesome/free-solid-svg-icons";

const APSEChartTotalEvents = ({ idSemester, idMajor }) => {
    const status = [1, 3, 2, 4, 5, 0];
    let quantity = [];
    const [listQuantity, setListQuantity] = useState([]);
    useEffect(() => {
        if (idSemester !== "" && idMajor !== "") {
            //console.log(idSemester , idMajor);
            APStatisticsEventApi.getEventBySemesterAndOrganizer(idSemester, idMajor).then(
                (res) => {
                    status.map((status) => {
                        const obj = res.data.data.find((data) => status === data.status);
                        if (obj !== undefined) {
                            quantity.push(obj.quantity);
                        } else {
                            quantity.push(0);
                        }
                    });
                    setListQuantity(quantity);
                },
                (err) => {
                    //  //console.log(err);
                }
            );
        }
    }, [idSemester, idMajor]);

    const data = {
        labels: [
            "Dự kiến tổ chức",
            "Chờ phê duyệt",
            "Cần sửa",
            "Đã phê duyệt",
            "Đã tổ chức",
            "Đã đóng",
        ],
        datasets: [
            {
                data: listQuantity,
                backgroundColor: [
                    "#6228b7",
                    "#fbbc05",
                    "#ee5fb7",
                    "#34a853",
                    "#4285f4",
                    "#ea4335",
                ],
            },
        ],
    };

    const options = {
        scales: {
            yAxes: [
                {
                    ticks: {
                        beginAtZero: true,
                    },
                },
            ],
        },
        plugins: {
            legend: {
                display: false,
            },
        },
    };

    return (
        <div style={{ textAlign: "center" }}>
            <h3 className="title-total">
                <b>
                    <FontAwesomeIcon icon={faCalendarDays} style={{ color: "#4889f4" }} />
                    <span style={{ marginLeft: "7px" }}>Tổng sự kiện đã đăng ký</span>
                </b>
            </h3>
            <Bar data={data} options={options} />
        </div>
    );
};

export default APSEChartTotalEvents;
