import React, { useEffect, useState } from "react";
import { Button, Col, Row, Tooltip } from "antd";
import PersonIcon from "@mui/icons-material/Person";
import ApproverIcon from "../../assets/img/imagesRole/2.png";
import OrganizerIcon from "../../assets/img/imagesRole/7.png";
import AdminIcon from "../../assets/img/imagesRole/5.png";
import AdminHOIcon from "../../assets/img/imagesRole/1.png";
import logoUDPM from "../../assets/img/logo-udpm-dark.png";
import logoFPT from "../../assets/img/Logo_FPoly.png";
import "./style.css";
import { getCurrentUser } from "../../utils/Common";
import {
    // ACTOR_ADMINISTRATIVE,
    ACTOR_CNBM,
    ACTOR_TM,
    ACTOR_GV,
    ACTOR_SV,
    ACTOR_BDT_CS,
    ACTOR_TBDT_CS,
    ACTOR_BDT
} from "../../constants/ActorConstant";
import { portIdentity } from "../ApiUrl";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDragon } from "@fortawesome/free-solid-svg-icons";

export default function ORPermission() {

    const [isOrganizer, setIsOrganizer] = useState(false);
    const [isApprover, setIsApprover] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);
    const [isAdminH, setIsAdminH] = useState(false);
    const [isAdminHO, setIsAdminHO] = useState(false);
    const [isTMANDCNBM, setIsTMANDCNBM] = useState(0);
    // const [isTeacher, setIsTeacher] = useState(false);

    const enableRole = () => {
        const user = getCurrentUser();
        if (typeof user.role === 'object') {
            if (user.role.includes(ACTOR_GV)) {
                setIsOrganizer(true);
            }
            if (user.role.includes(ACTOR_TM) || user.role.includes(ACTOR_CNBM)) {
                setIsApprover(true);
                setIsOrganizer(true);
            }
            if (user.role.includes(ACTOR_BDT_CS)) {
                setIsAdmin(true);
            }
            if (user.role.includes(ACTOR_TBDT_CS)) {
                setIsAdminH(true);
            }
            if (user.role.includes(ACTOR_BDT)) {
                setIsAdminHO(true);
            }
        } else if (typeof user.role === 'string') {
            if (user.role === ACTOR_SV) {
                window.location.href="/home";
            }
            if (user.role === ACTOR_GV) {
                setIsOrganizer(true);
            }
            if (user.role === ACTOR_TM || user.role === ACTOR_CNBM) {
                setIsApprover(true);
                setIsOrganizer(true);
            }
            if (user.role === ACTOR_BDT_CS) {
                setIsAdmin(true);
            }
            if (user.role.includes(ACTOR_TBDT_CS)) {
                setIsAdminH(true);
            }
            if (user.role === ACTOR_BDT) {
                setIsAdminHO(true);
            }
        }
    }

    const naviagateWindow = () => {
        const user = getCurrentUser();
        if (typeof user.role === 'string') {
            if (user.role === ACTOR_GV) {
                window.location.href = "/organizer-management/event-register";
            } else if (user.role === ACTOR_SV) {
                window.location.href = "/home";
            } else if (user.role === ACTOR_TM || user.role === ACTOR_CNBM) {
                return;
            }
            else if (user.role === ACTOR_TBDT_CS) {
                window.location.href = "/admin-h-management/statistics-event";
            }
            else if (user.role === ACTOR_BDT_CS) {
                window.location.href = "/admin-management/statistics-event"
                return;
            } else if (user.role === ACTOR_BDT) {
                window.location.href = "/admin-ho-management/statistics-event"
            }
            else {
                window.location.href = "/unknown-error";
            }
        } else if (typeof user.role === 'object') {
            if (user.role.includes(ACTOR_CNBM) && user.role.includes(ACTOR_TM) && !user.role.includes(ACTOR_BDT_CS) && !user.role.includes(ACTOR_GV) && !user.role.includes(ACTOR_TBDT_CS)) {
                window.location.href = "/approver-management/event-waiting-approval";
            }
            if (user.role.includes(ACTOR_CNBM) && user.role.includes(ACTOR_TM)) {
                setIsTMANDCNBM(1);
            } else if (user.role.includes(ACTOR_CNBM)) {
                setIsTMANDCNBM(2);
            } else if (user.role.includes(ACTOR_TM)) {
                setIsTMANDCNBM(3);
            }
            // if (!((APPROVER && user.role.includes(ACTOR_GV) || user.role.includes(ACTOR_BDT_CS)) || (user.role.includes(APPROVER && user.role.includes(ACTOR_ADMINISTRATIVE))))) {
            //     if (user.role.includes(ACTOR_GV)) {
            //         window.location.href = "/organizer-management/statistics-event";
            //     } else if (user.role.includes(ACTOR_ADMINISTRATIVE)) {
            //         window.location.href = "/organizer-management/statistics-event";
            //     } else if (user.role.includes(ACTOR_CNBM) || user.role.includes(ACTOR_TM)) {
            //         window.location.href = "/approver-management/event-waiting-approval";
            //     } else if (user.role.includes(ACTOR_BDT_CS)) {
            //         window.location.href = "/admin-management/category-list";
            //     }
            //     else if (user.role.includes(ACTOR_SV)) {
            //         window.location.href = "/home";
            //     }
            // }
        }
    }

    useState(() => {
        enableRole();
        naviagateWindow();
    }, []);

    const redirectToOrganizer = () => {
        window.location.href = "/organizer-management/event-register";
    };

    const redirectToApprover = () => {
        window.location.href = "/approver-management/statistics-event";
    };

    const redirectToAdmin = () => {
        window.location.href = "/admin-management/statistics-event";
    };

    const redirectToAdminH = () => {
        window.location.href = "/admin-h-management/statistics-event";
    };

    const redirectToAdminHO = () => {
        window.location.href = "/admin-ho-management/statistics-event";
    };

    const styledEvents = {
        cursor: "pointer",
    };

    useEffect(() => {
    }, []);

    return (
        <>
            {/* Thẻ cha bao bọc các thẻ con chứa nó, dùng để làm background */}
            <div className="background-permission">
                <div
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        justifyContent: "center",
                        minHeight: "calc(100vh - 120px)",
                        padding: "10px 10px 40px 10px"
                    }}
                >
                    {/* Phần logo của màn hình role */}
                    <Row>
                        <Col>
                            <img
                                src={logoFPT}
                                width={150}
                                alt="logo Fpt Polytechnic"
                            />
                        </Col>
                        <Col>
                            <img src={logoUDPM} width={150} alt="logo xưởng thực hành" />
                        </Col>
                    </Row>
                    {/* **********************END********************** */}

                    {/* Title của màn hình */}
                    <Row style={{ marginTop: "1.5rem" }}>
                        <h1
                            style={{
                                fontFamily: "Nunito",
                                fontWeight: "bolder",
                                fontSize: "30px",
                            }}
                        >
                            Thay đổi vai trò quản lý sự kiện
                        </h1>
                    </Row>
                    {/* **********************END********************** */}

                    {/* Images và button chuyển role */}
                    <Row
                        style={{
                            marginTop: "4.3rem",
                            alignItems: "center",
                            gap: "70px",
                            justifyContent: "space-between", // Đảm bảo các phần tử sẽ nằm cách xa nhau ngang bằng nhau
                        }}
                    >
                        {/* /**
                        Role tổ chức 
                        */}
                        {
                            isOrganizer &&
                            <div
                                className="image-permission"
                                onClick={() => redirectToOrganizer()}
                                style={styledEvents}
                            >
                                <Row
                                    style={{
                                        display: "flex",
                                        alignItems: "center",
                                        flexDirection: "column",
                                    }}
                                >
                                    <img
                                        src={OrganizerIcon}
                                        alt="Người tổ chức"
                                        style={{
                                            width: "200px",
                                            height: "auto",
                                            transition: "transform 0.3s", // Hiệu ứng hover
                                        }}
                                        onMouseOver={(e) => (e.target.style.transform = "scale(1.1)")}
                                        onMouseOut={(e) => (e.target.style.transform = "scale(1)")}
                                    />
                                    <Button
                                        className="button-role"
                                        style={{
                                            display: "flex",
                                            alignItems: "center",
                                            fontSize: "20px",
                                            marginTop: "15px",
                                            width: "auto",
                                            height: "40px",
                                            color: "white",
                                            background: "#0099FF",
                                        }}
                                    >
                                        <PersonIcon />
                                        Giảng viên
                                    </Button>
                                </Row>
                            </div>
                        }
                        {/* /**
                        Role phê duyệt
                        */}
                        {
                            isApprover &&
                            <div
                                className="image-permission"
                                onClick={() => redirectToApprover()}
                                style={{ styledEvents }}
                            >
                                <Row
                                    style={{
                                        display: "flex",
                                        alignItems: "center",
                                        flexDirection: "column",
                                        cursor: "pointer",
                                    }}
                                >
                                    <img
                                        src={ApproverIcon}
                                        alt="Người phê duyệt"
                                        style={{
                                            width: "200px",
                                            height: "auto",
                                            transition: "transform 0.3s", // Hiệu ứng hover
                                        }}
                                        onMouseOver={(e) => (e.target.style.transform = "scale(1.1)")}
                                        onMouseOut={(e) => (e.target.style.transform = "scale(1)")}
                                    />
                                    <Button
                                        style={{
                                            display: "flex",
                                            alignItems: "center",
                                            fontSize: "20px",
                                            marginTop: "15px",
                                            width: "auto",
                                            height: "40px",
                                            color: "white",
                                            background: "#0099FF",
                                        }}
                                    >
                                        <PersonIcon />
                                        {isTMANDCNBM == 1 && <>Trưởng môn/Chủ nhiệm bộ môn</> || isTMANDCNBM == 2 && <>Chủ nhiệm bộ môn</> || isTMANDCNBM == 3 && <>Trưởng môn</> || <>Chủ nhiệm bộ môn</>}
                                    </Button>
                                </Row>
                            </div>
                        }
                        {/* /**
                        Role ban đào tạo
                        **/ }
                        {
                            isAdmin &&
                            <div
                                className="image-permission"
                                onClick={() => redirectToAdmin()}
                                style={styledEvents}
                            >
                                <Row
                                    style={{
                                        display: "flex",
                                        alignItems: "center",
                                        flexDirection: "column",
                                    }}
                                >
                                    <img
                                        src={AdminIcon}
                                        alt="Quản lý đào tạo"
                                        style={{
                                            width: "200px",
                                            height: "auto",
                                            transition: "transform 0.3s", // Hiệu ứng hover
                                        }}
                                        onMouseOver={(e) => (e.target.style.transform = "scale(1.1)")}
                                        onMouseOut={(e) => (e.target.style.transform = "scale(1)")}
                                    />
                                    <Button
                                        style={{
                                            display: "flex",
                                            alignItems: "center",
                                            fontSize: "20px",
                                            marginTop: "15px",
                                            width: "auto",
                                            height: "40px",
                                            color: "white",
                                            background: "#0099FF",
                                        }}
                                    >
                                        <PersonIcon />
                                        Quản lý đào tạo
                                    </Button>
                                </Row>
                            </div>
                        }

                        {/* /** */}
                        {/* Role T Quản lý đào tạo  */}
                        {/* /** */}
                        {
                            isAdminH &&
                            <div
                                className="image-permission"
                                onClick={() => redirectToAdminH()}
                                style={styledEvents}
                            >
                                <Row
                                    style={{
                                        display: "flex",
                                        alignItems: "center",
                                        flexDirection: "column",
                                    }}
                                >
                                    <img
                                        src={AdminIcon}
                                        alt="Quản lý đào tạo"
                                        style={{
                                            width: "200px",
                                            height: "auto",
                                            transition: "transform 0.3s", // Hiệu ứng hover
                                        }}
                                        onMouseOver={(e) => (e.target.style.transform = "scale(1.1)")}
                                        onMouseOut={(e) => (e.target.style.transform = "scale(1)")}
                                    />
                                    <Button
                                        style={{
                                            display: "flex",
                                            alignItems: "center",
                                            fontSize: "20px",
                                            marginTop: "15px",
                                            width: "auto",
                                            height: "40px",
                                            color: "white",
                                            background: "#0099FF",
                                        }}
                                    >
                                        <PersonIcon />
                                        Trưởng ban đao tạo cơ sở
                                    </Button>
                                </Row>
                            </div>
                        }
                        {/* /** */}
                        {/* Role ban đào tạo HO  */}
                        {/* /** */}
                        {
                            isAdminHO &&
                            <div
                                className="image-permission"
                                onClick={() => redirectToAdminHO()}
                                style={styledEvents}
                            >
                                <Row
                                    style={{
                                        display: "flex",
                                        alignItems: "center",
                                        flexDirection: "column",
                                    }}
                                >
                                    <img
                                        src={AdminHOIcon}
                                        alt="Quản lý đào tạo"
                                        style={{
                                            width: "200px",
                                            height: "auto",
                                            transition: "transform 0.3s", // Hiệu ứng hover
                                        }}
                                        onMouseOver={(e) => (e.target.style.transform = "scale(1.1)")}
                                        onMouseOut={(e) => (e.target.style.transform = "scale(1)")}
                                    />
                                    <Button
                                        style={{
                                            display: "flex",
                                            alignItems: "center",
                                            fontSize: "20px",
                                            marginTop: "15px",
                                            width: "auto",
                                            height: "40px",
                                            color: "white",
                                            background: "#0099FF",
                                        }}
                                    >
                                        <PersonIcon />
                                        Ban đào tạo
                                    </Button>
                                </Row>
                            </div>
                        }
                    </Row>
                    {
                        <div style={{
                            position: "fixed",
                            top: "10px",
                            right: "10px",
                            zIndex: 1000,
                            backgroundColor: "rgba(255, 255, 255, 0.8)",
                            borderTop: "1px solid #e8e8e8",
                            borderBottom: "1px solid #e8e8e8",
                            padding: "10px",
                            cursor: "pointer",
                            transition: "all 0.3s ease-in-out",
                            "&:hover": {
                                backgroundColor: "rgba(255, 255, 255, 0.9)",
                            },
                        }}>
                            <Button type="link" danger style={{ fontSize: "large" }} onClick={() => {
                                window.location.href = portIdentity + "module-switch";
                            }}>
                                <Tooltip placement="bottom" title="Module Switch" color={'volcano'} >
                                    <FontAwesomeIcon icon={faDragon} />&nbsp; Module
                                    {/* <FontAwesomeIcon icon="fa-solid fa-dragon" style={{color: "#b41818",}} />
                                    <FontAwesomeIcon icon="fa-solid fa-dragon" /> */}
                                </Tooltip>
                            </Button>
                        </div>
                    }
                    {/* **********************END********************** */}
                </div>
                {/* Footer màn hình */}
                <div className="footer">
                    <div style={{
                        minHeight: "calc(100vh - (100vh - 120px))",
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        zIndex: 1000,
                        fontSize: "1.3rem",
                        color: "#172b4d !important"
                    }}>
                        <p>
                            Powered by <span style={{ fontWeight: "bold", color: "#172b4d !important", fontSize: "1.5rem", }} className="MuiTypography-root MuiTypography-button">FPLHN-UDPM</span>
                        </p>


                        <img
                            src={logoUDPM}
                            alt="logo"
                            style={{
                                width: "100%",
                                height: "auto",
                                maxWidth: "100px",
                            }}
                        />
                    </div>
                </div>
                {/* **********************END********************** */}
            </div>
        </>
    );
}
