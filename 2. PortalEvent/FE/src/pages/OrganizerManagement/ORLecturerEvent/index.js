import React from "react";
import "./lecturer-event-style.css";
import {Divider, message} from "antd";
import {faCalendarDays} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import {PAHomeApi} from "../../Home/PAHomeApi";

export default function ORLecturerEvent() {


    return (
        <>
            <div className="div-container">
                <h3>Sự kiện dành cho Giảng viên</h3>
                <Divider/>
                <h4><FontAwesomeIcon icon={faCalendarDays}/> Lịch sự kiện</h4>
                <div className="lecturer-event-calendar">
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
                        eventDisplay='auto'
                        displayEventEnd={true}
                        weekends={true}
                        scrollTime={true}
                        // events={data}
                        headerToolbar={{
                            left: 'prev,next, today',
                            center: 'title',
                            right: 'dayGridMonth,dayGridWeek,dayGridDay'
                        }}
                        // eventClick={(arg) => handleCalendarClick(arg.event.id)}
                        // eventMouseEnter={(arg) => handleMouseEnter(arg)}
                        // eventMouseLeave={(arg) => handleMouseLeave(arg)}
                    />
                </div>
            </div>
        </>
    );
}