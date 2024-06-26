import React, { useEffect, useState } from "react";
import { Doughnut } from "react-chartjs-2";
import ORStatisticEventApi from "../ORStatisticsEventApi";
import "./index.css";

const DoughnutChart = ({ semesterId }) => {
    const [totalRole, setTotalRole] = useState();

    useEffect(() => {
        ORStatisticEventApi.getTotalRole(semesterId).then(
            (res) => {
                setTotalRole(res.data.data);
               //  //console.log(res.data.data);
            },
            (err) => {
               //  //console.log(err);
            }
        );
    }, [semesterId]);

    const data = {
        labels: ["Vai trò host", "Vai trò co-host"],
        datasets: [
            {
                data: [totalRole?.numberOfCoHost ?? 0, totalRole?.numberOfHost ?? 0],
                backgroundColor: ["#f5365c", "#11cdef "],
            },
        ],
    };

    const options = {
        plugins: {
            datalabels: {
                color: "white",
                formatter: (value, ctx) => {
                    let sum = 0;
                    let dataArr = ctx.chart.data.datasets[0].data;
                    dataArr.map((data) => {
                        sum += data;
                    });
                    let percentage = ((value * 100) / sum).toFixed(2) + "%";
                    return percentage;
                },
                font: {
                    weight: "bold",
                },
            },
        },
    };
    return (
        <div style={{ textAlign: "center" }}>
            <h3 className="title-total">
                <b>
                    <span style={{ marginLeft: "7px" }}>
                        Tổng sự kiện tham gia: {totalRole?.totalEvents ?? 0}
                    </span>
                </b>
            </h3>
            <Doughnut data={data} options={options} />
        </div>
    );
};

export default DoughnutChart;
