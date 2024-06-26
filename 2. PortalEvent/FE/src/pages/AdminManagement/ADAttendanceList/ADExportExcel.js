import React from "react";
import {Button} from "antd";
import {faFileCsv} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {ADAttendanceListApi} from "./ADAttendanceListApi";

const ADExportExcel = ({ eventId }) => {
    const handleOkEvent = () => {
        //console.log("eventId", eventId)
        ADAttendanceListApi.handleExportAttendance(eventId);
    };

    // return <button onClick={exportToExcel}>Xuất Excel</button>;
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
                style={{ color: "#ffffff", marginLeft: "7px" }}
            />
        </Button>
    );
};

export default ADExportExcel;
