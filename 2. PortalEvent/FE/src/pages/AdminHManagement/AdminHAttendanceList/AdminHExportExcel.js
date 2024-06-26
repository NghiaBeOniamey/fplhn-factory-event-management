import React from "react";
// import { ADAttendanceListApi } from "./AdminHAttendanceListApi";
// import { useParams } from "react-router-dom";
import {Button} from "antd";
import {faFileCsv} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {AdminHAttendanceListApi} from "./AdminHAttendanceListApi";

const AdminHExportExcel = ({ eventId }) => {

    const handleOkEvent = () => {
        //console.log("eventId", eventId)
        AdminHAttendanceListApi.handleExportAttendance(eventId);
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
                style={{ color: "#ffffff", marginLeft: "7px" }}
            />
        </Button>
    );
};

export default AdminHExportExcel;
