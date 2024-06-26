import React from "react";
import {APAttendanceListApi} from "./APAttendanceListApi";
import {Button} from "antd";
import {faFileCsv} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const APExportExcel = ({eventId}) => {

    const handleOkEvent = () => {
        //console.log("eventId", eventId)
        APAttendanceListApi.handleExportAttendance(eventId);
    };

    return (
        <Button
            type="primary"
            className="btn-form-event"
            onClick={handleOkEvent}
            style={{
                backgroundColor: "#217346",
            }}
        >
            Xuất Excel
            <FontAwesomeIcon
                icon={faFileCsv}
                style={{color: "#ffffff", marginLeft: "7px"}}
            />
        </Button>
    );
};

export default APExportExcel;
