import React, { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import APStatisticsEventApi from "../APStatisticsEventApi";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarDays } from "@fortawesome/free-solid-svg-icons";

const APSEParticipantInEvent = ({ idSemester, idMajor }) => {
    const [listLabel, setListLabel] = useState([]); // Tên sự kiện
    const [listExpectedParticipants, setListExpectedParticipants] = useState([]); // Dự kiến số người tham gia
    const [listNumberParticipantsEnrolled, setListNumberParticipantsEnrolled] = useState([]); // Số người đăng kí tham gia
    const [listNumberParticipants, setNumberParticipants] = useState([]); // Số người tham gia thực tế

    useEffect(() => {
        if (idSemester !== "" && idMajor !== "") {
            //console.log(idSemester , idMajor);
            APStatisticsEventApi.getParticipantInEvent(idSemester, idMajor).then(
                (res) => {
                    setListLabel([])
                    setListExpectedParticipants([])
                    setListNumberParticipantsEnrolled([])
                    setNumberParticipants([])
                    res.data.data.map(obj => {
                        setListLabel(prev => [...prev, obj.name]);
                        setListExpectedParticipants(prev => [...prev, obj.expectedParticipants]);
                        setListNumberParticipantsEnrolled(prev => [...prev, obj.numberParticipantsEnrolled]);
                        setNumberParticipants(prev => [...prev, obj.numberParticipants]);
                    })
                },
                (err) => {
                    //  //console.log(err);
                }
            );
        }
    }, [idSemester, idMajor]);

    const data = {
        labels: listLabel,
        datasets: [
            {
                label: "Số người tham gia dự kiến",
                data: listExpectedParticipants,
                backgroundColor: "#fbbc05"
            },
            {
                label: "Số người đăng ký tham gia",
                data: listNumberParticipantsEnrolled,
                backgroundColor: "#34a853"
            },
            {
                label: "Số người tham gia thực tế",
                data: listNumberParticipants,
                backgroundColor: "#ea4335"
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
                display: true,
            },
        },
        responsive: true,
    };

    return (
        <div style={{ textAlign: "center" }}>
            <h3 className="title-total">
                <b>
                    <FontAwesomeIcon icon={faCalendarDays} style={{ color: "#4889f4" }} />
                    <span style={{ marginLeft: "7px" }}>Số lượng sinh viên tham gia của các sự kiện</span>
                </b>
            </h3>
            <Bar data={data} options={options} />
        </div>
    );
};

export default APSEParticipantInEvent;
