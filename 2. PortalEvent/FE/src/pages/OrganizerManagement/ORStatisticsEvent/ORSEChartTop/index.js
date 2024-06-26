import React, { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import { CategoryScale, Chart } from "chart.js/auto";
import { Tooltip } from "chart.js";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrophy } from "@fortawesome/free-solid-svg-icons";
import ORStatisticEventApi from "../ORStatisticsEventApi";

Chart.register(CategoryScale, Tooltip);

const ORSEChartTop = ({ semesterId }) => {
    const [listTopEvent, setListTopEvent] = useState([]);

    useEffect(() => {
        ORStatisticEventApi.getTopEvent(semesterId).then(
            (res) => {
                setListTopEvent(res.data.data);
            },
            (err) => {
               //  //console.log(err);
            }
        );
    }, [semesterId]);

    // Xác định số lượng cột dựa vào độ dài mảng listTopEvent
    const numColumns = listTopEvent ? listTopEvent.length : 0;

    // Tạo mảng labels tương ứng với số cột
    const labels = [];
    for (let i = 1; i <= numColumns; i++) {
        labels.push(`Top ${i}`);
    }

    // Tạo mảng datasets với dữ liệu participant
    const datasets = [
        {
            data: listTopEvent ? listTopEvent.map((item) => item.numberParticipant) : [],
            backgroundColor: [
                "rgba(255, 99, 132, 0.2)",
                "rgba(54, 162, 235, 0.2)",
                "rgba(255, 206, 86, 0.2)",
                "rgba(75, 192, 192, 0.2)",
                "rgba(153, 102, 255, 0.2)",
            ],
            borderColor: [
                "rgba(255,99,132,1)",
                "rgba(54, 162, 235, 1)",
                "rgba(255, 206, 86, 1)",
                "rgba(75, 192, 192, 1)",
                "rgba(153, 102, 255, 1)",
            ],
            borderWidth: 1,
        },
    ];

    const data = {
        labels: labels,
        datasets: datasets,
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
        responsive: true,
        plugins: {
            legend: {
                display: false,
            },
            tooltip: {
                callbacks: {
                    label: function (context) {
                        const index = context.dataIndex;
                        const event = listTopEvent[index];

                        return `Tên: ${event.name}`;
                    },
                },
            },
        },
    };

    return (
        <div style={{ textAlign: "center" }}>
            <h3 className="title-total">
                <b>
                    <FontAwesomeIcon icon={faTrophy} style={{ color: "#ffca28" }} />
                    <span style={{ marginLeft: "7px" }}>
                        Top {numColumns} sự kiện nổi bật
                    </span>
                </b>
            </h3>
            <Bar data={data} options={options} style={{ marginTop: "50px" }} />
        </div>
    );
};

export default ORSEChartTop;
