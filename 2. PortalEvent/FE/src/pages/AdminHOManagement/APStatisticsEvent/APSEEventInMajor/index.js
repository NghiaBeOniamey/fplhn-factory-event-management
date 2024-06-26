import React, {useEffect, useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendarDays} from "@fortawesome/free-solid-svg-icons";
import APStatisticsEventApi from "../APStatisticsEventApi";
import {Bar} from "react-chartjs-2";

const APSEEventInMajor = ({idSemester, idCampus, idDepartment}) => {
    let lable = [];
    let value = [];
    const [listLable, setListLable] = useState([])
    const [listValue, setListValue] = useState([])
    const [listEventInMajor, setListEventInMajor] = useState([]);
    useEffect(() => {
        if (idSemester !== "" && idCampus !== "" && idDepartment !== "") {
            //console.log(idSemester, idCampus, idDepartment);
            APStatisticsEventApi.getEventInMajor(idSemester, idCampus, idDepartment).then(
                (res) => {
                    setListEventInMajor(res.data.data);
                    res.data.data.map((x) => {
                        lable.push(x.code)
                        value.push(x.quantity)
                    })
                    setListLable(lable)
                    setListValue(value)
                },
                (err) => {
                   //  //console.log(err);
                }
            );
        }
    }, [idSemester, idCampus, idDepartment]);

    const data = {
        labels: listLable,
        datasets: [
            {
                data: listValue,
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
        <div style={{textAlign: "center"}}>
            <h3 className="title-total">
                <b>
                    <FontAwesomeIcon icon={faCalendarDays} style={{color: "#4889f4"}}/>
                    <span style={{marginLeft: "7px"}}>Sự kiện đã tổ chức của các bộ môn</span>
                </b>
            </h3>
            <Bar data={data} options={options}/>
        </div>
    );
}
export default APSEEventInMajor;