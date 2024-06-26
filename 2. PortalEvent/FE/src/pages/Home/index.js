import React, { useEffect, useState } from "react";
// AOS animation library
import Aos from 'aos';
import "aos/dist/aos.css";
// Sections
import TopNavbar from "./Nav/TopNavbar";
import Header from "./Sections/Header";
import Footer from "../../section/Footer";
import BackToTopButton from "../../components/BackToTop";
import { EventStatus, EventType } from "../../constants/EventProperties";
// CSS
import "./PAHome.css";
import "../../assets/css/flexboxgrid.min.css";
import { PAHomeApi } from "./PAHomeApi";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { formatDateHome } from "../../utils/Converter"
import {
    faChalkboardUser,
    faCircleInfo,
    faClock,
    faDownload,
    faFile, faFloppyDisk,
    faImage,
    faLocationDot, faPaperPlane,
    faTag
} from "@fortawesome/free-solid-svg-icons";
// Utils
import { dateTimeFromLong } from "../../utils/Converter"
import ModalRegister from "./Component/ModalRegister";
import ModalAttendance from "./Component/ModalAttendance";
import CommentSection from "./Component/Comment";
import { CheckSquareOutlined, DownloadOutlined } from "@ant-design/icons";
import { Button, Carousel, ConfigProvider, Image, message, Row, Col, Spin, Popconfirm, Tag } from "antd";
import LogoUDPM from "../../assets/img/logo-udpm-dark.png";
import LogoFPT from "../../assets/img/Logo_FPoly.png";
import { getCurrentUser } from "../../utils/Common";
import { ACTOR_GV } from "../../constants/ActorConstant";

export default function HomeComponent() {
    const [isModalRegister, setIsModalRegister] = useState(false);
    const [isModalAttendance, setIsModalAttendance] = useState(false);
    const [eventsComingUp, setEventComingUp] = useState([]);
    const [isNoEventIncomming, setIsNoEventIncomming] = useState(false)
    const [isLecturer, setIsLecturer] = useState(false);

    useEffect(() => {
        Aos.init({
            duration: 2000,
            easing: 'ease-in-out',
            once: true,
            mirror: false
        });
        PAHomeApi.getEventComingUp().then((res) => {
            setEventComingUp(res.data.data);
            if (res.data.data.length === 0) {
                setIsNoEventIncomming(true)
            }
            let listEvent = [];
            for (let i = 0; i < res.data.data.length; i++) {
                let event = {
                    id: res.data.data[i].id,
                    title: res.data.data[i].name,
                    start: formatDateHome(res.data.data[i].startTime),
                    end: formatDateHome(res.data.data[i].endTime)
                }
                listEvent.push(event);
            }
            setData(listEvent);
        }).catch((err) => {
            message.error("Vui lòng refresh lại trang");
        })
        const user = getCurrentUser();
        if (user.role.includes(ACTOR_GV)) {
            setIsLecturer(true);
        } else {
            setIsLecturer(false);
        }
    }, []);

    // ***************** CALENDAR ****************
    const [data, setData] = useState([]);
    const [eventDetail, setEventDetail] = useState({});
    const [listOrganizerDetail, setListOrganizerDetail] = useState([]);
    const [listLocationDetail, setListLocationDetail] = useState([]);
    const [listObjectDetail, setListObjectDetail] = useState([]);
    const [listResourceDetail, setListResourceDetail] = useState([]);

    //loading
    const [loading, setLoading] = useState(false);
    // điểm danh và đăng ký
    const [isRegisterToParticipate, setRegisterToParticipate] = useState(false);
    const [isRollCall, setRollCall] = useState(false);

    const dangKyThamGia = () => {
        setLoading(true);
        if (new Date().getTime() < eventDetail.startTime) {
            PAHomeApi.registerEvent(eventDetail.id).then((res) => {
                if (res.data.success === true) {
                    message.success('Đăng ký tham gia thành công!');
                    setRegisterToParticipate(true);
                } else {
                    message.error('Bạn đã đăng ký tham gia!');
                }
            }).catch((err) => {
                //console.log(err);
            })
        } else {
            message.error("Hết thời gian đăng ký tham gia sự kiện!");
        }
        setLoading(false);
    };
    //console.log(eventDetail)
    const diemDanh = () => {
        PAHomeApi.rollCallEvent(eventDetail.id).then((res) => {
            if (res.data.data === true) {
                message.success('Điểm danh thành công!');
                setRollCall(true);
            } else {
                window.location.reload(true);
            }
        }).catch((err) => {
            //console.log(err)
        })
    }
    useEffect(() => {
        if (eventDetail.id) {
            const element = document.getElementById('resume');
            if (element) {
                element.scrollIntoView({ behavior: 'smooth' });
            }
        }
    }, [eventDetail, listObjectDetail, listResourceDetail, listOrganizerDetail, listLocationDetail]);

    // const handleMouseEnter = (info) => {
    //     const popup = document.createElement('div');
    //     popup.innerHTML = `<p className="popupTitle">${info.event.title}</p>
    // 						${info.event.start.toLocaleTimeString()} - ${info.event.end.toLocaleTimeString()}`;
    //     popup.className = 'event-popup';
    //     info.el.appendChild(popup);
    //     popup.classList.add('right');
    // };

    const handleMouseLeave = (info) => {
        const popup = info.el.querySelector('.event-popup');
        if (popup) {
            info.el.removeChild(popup);
        }
    };

    const handleCalendarClick = (id) => {
        PAHomeApi.detail(id).then((res) => {
            if (res.data.data.length > 0) {
                let eventDetailReponse = {
                    id: res.data.data[0].id,
                    name: res.data.data[0].name,
                    startTime: res.data.data[0].startTime,
                    isOpenAttendance: res.data.data[0].isOpenAttendance,
                    isOpenRegistration: res.data.data[0].isOpenRegistration,
                    isRegisted: res.data.data[0].isRegisted,
                    isAttended: res.data.data[0].isAttended,
                    endTime: res.data.data[0].endTime,
                    category: res.data.data[0].category,
                    eventType: res.data.data[0].eventType,
                    backgroundPath: res.data.data[0].backgroundPath,
                    bannerPath: res.data.data[0].bannerPath,
                    description: res.data.data[0].description,
                    status: res.data.data[0].status
                };
                let locations = listLocationDetail;
                let objects = listObjectDetail;
                let resources = listResourceDetail;
                let organizers = listOrganizerDetail;
                res.data.data.forEach(item => {
                    //listOrganizerDetail
                    //
                    if (item.listOrganizer) {
                        item.listOrganizer.forEach(item => {
                            if (!organizers.find((organizer) => organizer === item)) {
                                organizers.push(item);
                            }
                        });
                    }
                    if (!locations.find((location) => location.id === item.locationId) && typeof item.locationId !== 'undefined' && item.locationId !== null) {
                        locations.push({
                            id: item.locationId,
                            name: item.loccationName,
                            formality: item.formality,
                            path: item.locationPath
                        })
                    }
                    if (!objects.find((object) => object.id === item.objectId) && typeof item.objectId !== 'undefined' && item.objectId !== null) {
                        objects.push({
                            id: item.objectId,
                            name: item.objectName
                        })
                    }
                    if (!resources.find((resource) => resource.id === item.resourceId) && typeof item.resourceId !== 'undefined' && item.resourceId !== null) {
                        resources.push({
                            id: item.resourceId,
                            name: item.resourceName,
                            path: item.resourcePath
                        })
                    }
                })

                setEventDetail(eventDetailReponse);
                setListLocationDetail(locations);
                //  //console.log(locations)
                setListResourceDetail(resources);
                setListObjectDetail(objects);
                setListOrganizerDetail(organizers);
            }
        }).catch((err) => {
            //  //console.log(err);
            message.error('Vui lòng liên hệ hỗ trợ');
        });

        //check xem đã đăng ký tham gia sk chưa
        PAHomeApi.checkRegisterToParticipate(id).then((res) => {
            if (res.data.data === true) {
                setRegisterToParticipate(true)
            }else{
                setRegisterToParticipate(false)
            }
        }).catch((err) => {
            //console.log(err)
        })
        //check xem đã điểm danh sự kiện chưa
        PAHomeApi.checkRollCallEvent(id).then((res) => {
            if (res.data.data === true) {
                setRollCall(true)
            }else{
                setRollCall(false)
            }
        }).catch((err) => {
            //console.log(err)
        })
    }

    // ************ END CALENDAR ************

    // ************* EVENT DETAIL**************
    const [isPreviewBanner, setIsVisibleBanner] = useState(false);
    const [isPreviewBackground, setIsPreviewBackground] = useState(false);
    const downloadImage = async () => {
        if (eventDetail.bannerPath && isPreviewBanner) {
            try {
                const response = await fetch(eventDetail.bannerPath);
                const blob = await response.blob();
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = eventDetail.name + '_banner'; // Tên file khi tải xuống
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            } catch (error) {
                console.error('Error downloading image:', error);
            }
        } else if (eventDetail.backgroundPath && isPreviewBackground) {
            try {
                const response = await fetch(eventDetail.backgroundPath);
                const blob = await response.blob();
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = eventDetail.name + '_background'; // Tên file khi tải xuống
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            } catch (error) {
                console.error('Error downloading image:', error);
            }
        }
    }
    // ************* END EVENT DETAIL**************

    //Modal register
    const showModalRegister = () => {
        setIsModalRegister(true);
    };
    const handleCancelRegister = () => {
        setIsModalRegister(false);
    };

    //Modal register
    const showModalAttendance = () => {
        setIsModalAttendance(true);
    };
    const handleCancelAttendance = () => {
        setIsModalAttendance(false);
    };

    const updateIsAttended = () => {
        setEventDetail(prevState => ({
            ...prevState,
            isAttended: true
        }));
    };

    const updateIsRegisted = () => {
        setEventDetail(prevState => ({
            ...prevState,
            isRegisted: true
        }));
    };
    return (
        <>
            <LogoUDPMWrapper src={LogoUDPM} alt={"Logo"}></LogoUDPMWrapper>
            <LogoFPTWrapper src={LogoFPT} alt={"LogoFPT"}></LogoFPTWrapper>
            <TopNavbar isDetail={eventDetail.id} />
            {(isNoEventIncomming || isLecturer) &&
                <Header showDetail={handleCalendarClick} event={undefined} isNoEvent={true} isLecturer={true} />
            }
            {(!isNoEventIncomming && !isLecturer) &&
                <ConfigProvider
                    theme={{
                        components: {
                            Carousel: {
                                dotWidth: "40px",
                                dotActiveWidth: "60px",
                                dotHeight: "6px",
                                borderRadius: "5px",
                            },
                        },
                    }}
                >
                    <Carousel autoplay autoplaySpeed={5000} pauseOnHover={false}>
                        {eventsComingUp.map((item, index) => (
                            <Header key={index} showDetail={handleCalendarClick} event={item} isNoEvent={false}
                                isLecturer={false} />
                        ))}
                    </Carousel>
                </ConfigProvider>
            }
            {/*<Services/>*/}
            {/************ CALENDER **************/}
            <div id='calendar' data-aos="fade-up">
                <div className="section-title">
                    <h2>Lịch sự kiện</h2>
                </div>
                <div>
                    <FullCalendar
                        plugins={[dayGridPlugin]}
                        buttonText={{
                            today: 'Hôm nay',
                            month: 'Tháng',
                            week: 'Tuần',
                            day: 'Ngày',
                            list: 'Agenda'
                        }}
                        locale='vi'
                        initialDate={new Date()}
                        navLinks={true}
                        selectMirror={true}
                        dayMaxEvents={true}
                        initialView="dayGridMonth"
                        dayHeaderFormat={{
                            weekday: 'long'
                        }}
                        eventTimeFormat={{
                            hour: '2-digit',
                            minute: '2-digit',
                            hour12: false
                        }}
                        eventDisplay='block'
                        displayEventEnd={true}
                        weekends={true}
                        scrollTime={true}
                        events={data}
                        headerToolbar={{
                            left: 'prev,next, today',
                            center: 'title',
                            right: 'dayGridMonth,dayGridWeek,dayGridDay'
                        }}
                        eventClick={(arg) => handleCalendarClick(arg.event.id)}
                        // eventMouseEnter={(arg) => handleMouseEnter(arg)}
                        eventMouseLeave={(arg) => handleMouseLeave(arg)}
                    />
                </div>
            </div>
            {/****************END CALENDAR*************/}

            {/****************** EVENT DETAIL ******************/}
            {eventDetail.id && (
                <>
                    <Spin spinning={loading}>
                        <section id="resume" className="resume">
                            <div data-aos="fade-up">

                                <div className="section-title">
                                    {/*<h2>Thông tin sự kiện</h2>*/}
                                    <h2>{eventDetail.category} - {eventDetail.name}</h2>
                                </div>

                                <div className="row">
                                    {eventDetail.bannerPath &&
                                        <div className="banner">
                                            <Image
                                                width={800}
                                                style={{ borderRadius: "8px" }}
                                                src={eventDetail.bannerPath}
                                                preview={{
                                                    visible: isPreviewBanner,
                                                    src: eventDetail.bannerPath,
                                                    onVisibleChange: (value) => {
                                                        setIsVisibleBanner(value);
                                                    },
                                                }}
                                            />
                                        </div>
                                    }
                                    <div className="register">
                                        {eventDetail.status === EventStatus.APPROVED && eventDetail.isOpenRegistration && !eventDetail.isRegisted &&
                                            <button className="register-button" onClick={() => showModalRegister()}>Đăng
                                                ký</button>}
                                    </div>
                                    <div className="col-lg-6 resume-content">
                                        <div className="resume-item">
                                            <h4><FontAwesomeIcon icon={faClock} /> Thời gian</h4>
                                            <h5>Thời gian diễn
                                                ra: {dateTimeFromLong(eventDetail.startTime)} - {dateTimeFromLong(eventDetail.endTime)}</h5>
                                        </div>
                                        {listLocationDetail.length > 0 &&
                                            <div className="resume-item">
                                                <>
                                                    <h4><FontAwesomeIcon icon={faLocationDot} /> Địa điểm</h4>
                                                    <ul>
                                                        {listLocationDetail.map((item, index
                                                        ) => (
                                                            <li>
                                                                <h5>{item.formality}</h5>
                                                                {item.name}: {item.formality === 'OFFLINE' ?
                                                                    <span><b>{item.path}</b></span> :
                                                                    <a key={'location' + index} href={item.path}>Tại
                                                                        đây</a>}
                                                            </li>
                                                        ))}
                                                    </ul>
                                                </>
                                            </div>
                                        }
                                        <div className="resume-item">
                                            <h4><FontAwesomeIcon icon={faChalkboardUser} /> Người tổ
                                                chức {eventDetail.status}</h4>
                                            <ul>
                                                {listOrganizerDetail.map((item, index) => (
                                                    <li key={"organizer" + index}>
                                                        <strong>{item}</strong>
                                                    </li>
                                                ))}
                                            </ul>
                                        </div>
                                        {eventDetail.status === EventStatus.APPROVED
                                            && (eventDetail.status === EventStatus.ORGANIZED && eventDetail.isAttended)
                                            && listResourceDetail.length > 0 &&
                                            <div className="resume-item">
                                                <h4><FontAwesomeIcon icon={faFile} /> Tài liệu</h4>
                                                <ul>
                                                    {listResourceDetail.map((item, index) => (
                                                        <li key={'resource' + index}>{item.name}: <a href={item.path}>Tại
                                                            đây</a></li>
                                                    ))}

                                                </ul>
                                            </div>
                                        }
                                    </div>
                                    <div className="col-lg-6 resume-content">
                                        <div className="resume-item">
                                            <h4><FontAwesomeIcon icon={faTag} />
                                                {eventDetail.eventType === EventType.STUDENT_EVENT ?
                                                    ' Sự kiện dành cho sinh viên' :
                                                    eventDetail.eventType === EventType.COMBINED_EVENT ?
                                                        ' Sự kiện dành cho sinh viên và giảng viên' :
                                                        eventDetail.eventType === EventType.LECTURER_EVENT ? 'Sự kiện dành cho Giảng viên' : ''}
                                            </h4>
                                        </div>
                                        <div className="resume-item">
                                            <h4><FontAwesomeIcon icon={faCircleInfo} /> Mục tiêu sự kiện:</h4>
                                            {/*<p>{eventDetail.description}</p>*/}
                                            <p dangerouslySetInnerHTML={{ __html: eventDetail.description }}></p>
                                        </div>
                                        {eventDetail.backgroundPath &&
                                            <div className="resume-item">
                                                <h4><FontAwesomeIcon icon={faImage} /> Background</h4>
                                                <button className="subscribe" id="a"
                                                    onClick={() => setIsPreviewBackground(true)}>
                                                    <FontAwesomeIcon icon={faDownload} /> Background
                                                </button>
                                                <Image
                                                    style={{ display: 'none' }}
                                                    preview={{
                                                        visible: isPreviewBackground,
                                                        src: eventDetail.backgroundPath,
                                                        onVisibleChange: (value) => {
                                                            setIsPreviewBackground(value);
                                                        },
                                                    }}
                                                />
                                            </div>
                                        }
                                    </div>
                                </div>
                                <Row style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                                    {isRegisterToParticipate === true ?
                                        <Tag color="green">Bạn đã đăng ký tham gia sự kiện này!</Tag>
                                        : new Date().getTime() < eventDetail.startTime ?
                                            <Popconfirm
                                                title="Xác nhận đăng ký tham gia sự kiện?"
                                                onConfirm={() => {
                                                    dangKyThamGia();
                                                }}
                                                okText="Có"
                                                cancelText="Không"
                                            >
                                                <Button
                                                    className="btn-post-comment"
                                                    type="primary"
                                                >
                                                    <CheckSquareOutlined style={{ fontSize: '16px' }} /> Đăng ký tham
                                                    gia
                                                </Button>
                                            </Popconfirm> :
                                            <Tag color="red">Đã hết thời gian đăng ký tham gia sự kiện!</Tag>
                                    }
                                </Row>
                                <Row style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', padding: '20px' }}>
                                    {isRollCall === true ?
                                        <Tag color="green">Bạn đã điểm danh sự kiện này!</Tag> :
                                        <p>
                                            {eventDetail.isOpenAttendance ? <Popconfirm
                                                title="Xác nhận điểm danh sự kiện?"
                                                onConfirm={() => {
                                                    diemDanh();
                                                }}
                                                okText="Có"
                                                cancelText="Không"
                                            >
                                                <Button
                                                    className="btn-post-comment"
                                                    type="primary"
                                                >
                                                    <CheckSquareOutlined style={{ fontSize: '16px' }} /> Điểm danh sự
                                                    kiện
                                                </Button>
                                            </Popconfirm> : null}
                                        </p>
                                    }
                                </Row>
                            </div>
                        </section>
                    </Spin>
                </>
            )}
            <ModalRegister
                visible={isModalRegister}
                isLecturer={isLecturer}
                updateIsRegisted={updateIsRegisted}
                onCancel={() => handleCancelRegister()}
                eventId={eventDetail.id}
            />
            <ModalAttendance
                visible={isModalAttendance}
                updateIsAttended={updateIsAttended}
                onCancel={() => handleCancelAttendance()}
                eventId={eventDetail.id}
            />
            {/***************** END EVENT DETAIL **************/}
            {/*<Contact/>*/}
            <div style={{ paddingBottom: '40px' }}>
                <Footer />
            </div>
            <BackToTopButton />
            {(isPreviewBackground || isPreviewBanner) &&
                <button id="imageDownloadButton">
                    <DownloadOutlined onClick={() => downloadImage()} />
                </button>
            }
        </>
    );
}

const LogoUDPMWrapper = styled.img`
    width: 13%;
    z-index: 9999;
    position: absolute;
    top: 5%;
    left: 14%;
    @media (max-width: 576px) {
        width: 80%;
        height: auto;
    }
`;
const LogoFPTWrapper = styled.img`
    width: 10%;
    z-index: 9999;
    position: absolute;
    top: 6%;
    left: 5%;
    @media (max-width: 576px) {
        width: 80%;
        height: auto;
    }
`;