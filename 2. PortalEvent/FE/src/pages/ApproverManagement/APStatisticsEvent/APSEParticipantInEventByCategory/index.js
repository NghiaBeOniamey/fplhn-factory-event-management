import React, { useEffect, useState } from "react";
import { Bar } from "react-chartjs-2";
import APStatisticsEventApi from "../APStatisticsEventApi";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarDays } from "@fortawesome/free-solid-svg-icons";
import { Form, Select } from "antd";

const APSEParticipantInEventByCategory = ({ idSemester }) => {
    const [listLabel, setListLabel] = useState([]); // Tên sự kiện
    const [listExpectedParticipants, setListExpectedParticipants] = useState([]); // Dự kiến số người tham gia
    const [listNumberParticipantsEnrolled, setListNumberParticipantsEnrolled] = useState([]); // Số người đăng kí tham gia
    const [listNumberParticipants, setNumberParticipants] = useState([]); // Số người tham gia thực tế
    const [listCategory, setListCategory] = useState([]);
    const [category, setCategory] = useState("");

    useEffect(() => {
        APStatisticsEventApi.getAllCategory().then(
            (res) => {
                setListCategory(res.data.data)
                if (res.data.data.length > 0 && category === "") {
                    setCategory(res.data.data[0].id)
                }
            },
            (err) => {
                //  //console.log(err);
            }
        );
    }, []);

    useEffect(() => {
        APStatisticsEventApi.getParticipantInEventByCategory(idSemester, category).then(
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
    }, [idSemester, category]);

    const data = {
        labels: listLabel,
        datasets: [
            {
                label: "Số người tham gia dự kiến",
                data: listExpectedParticipants,
                backgroundColor: "#6228b7"
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
                    <span style={{ marginLeft: "7px" }}>Số lượng sinh viên tham gia của các sự kiện theo thể loại</span>
                </b>
            </h3>
            <Form.Item label="Thể loại sự kiện" rules={[{ required: true }]} style={{
                width: "40%",

            }}>
                <Select
                    value={category}
                    onChange={(value) => setCategory(value)}
                    options={listCategory?.map((category) => ({
                        value: category.id,
                        label: category.name,
                    }))}
                    showSearch
                    filterOption={(input, option) => (option?.label ?? '').toLowerCase().includes(input.toLowerCase())}
                />
            </Form.Item>
            <Bar data={data} options={options} />
        </div>
    );
};

export default APSEParticipantInEventByCategory;
