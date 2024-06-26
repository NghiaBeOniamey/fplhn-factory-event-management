// CSS
import "./ORMultipleRegisterStyle.css";
//Component
import React, {useEffect, useState} from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import ORMultipleRegisterApi from "./ORMultipleRegisterApi";
import {
    Button,
    Col,
    DatePicker,
    Descriptions,
    Divider,
    Input,
    InputNumber,
    message,
    Modal,
    Popconfirm,
    Row,
    Select,
    Space
} from "antd";
import type {DatePickerProps, RangePickerProps} from 'antd/es/date-picker';
import dayjs from "dayjs";
import {dateFromLong, formatDate, parseDateFromString} from "../../../utils/Converter";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendarPlus} from "@fortawesome/free-solid-svg-icons";
import {Tag} from "antd/lib";
import {Link} from "react-router-dom";
import {ExclamationCircleFilled} from "@ant-design/icons";

export default function ORMultipleRegister() {
    const [data, setData] = useState([]);
    const {confirm} = Modal;
    // Data modal register
    const [newEventName, setNewEventName] = useState("");
    const [newEventNameError, setNewEventNameError] = useState("");
    const [newEventStartTime, setNewEventStartTime] = useState(0);
    const [newEventSemesterId, setNewEventSemesterId] = useState(undefined)
    const [newEventBlockNumber, setNewEventBlockNumber] = useState(undefined);
    const [newEventEndTime, setNewEventEndTime] = useState(0);
    const [newEventCategoryId, setNewEventCategoryId] = useState(undefined);
    const [newEventExpectedParticipants, setNewEventExpectedParticipants] = useState(undefined);
    const [newEventType, setNewEventType] = useState('0');
    const [newEventObjectId, setNewEventObjectId] = useState(undefined);
    const [newEventMajorId, setNewEventMajorId] = useState(undefined);
    const [newEventSemesterName, setNewEventSemesterName] = useState('');
    const [newEventBlockname, setNewEventBlockname] = useState('');
    const [listSemester, setListSemester] = useState([]);
    const [listCategory, setListCategory] = useState([]);
    const [listObject, setListObject] = useState([]);
    const [listMajor, setListMajor] = useState([]);

    const { RangePicker } = DatePicker;
    const [modalClickTitile, setModalClickTitile] = useState('');
    const [listNewEvent, setListNewEvent] = useState([]);
    const [newEventId, setNewEventId] = useState(undefined);
    const [isModalOpenDetail, setIsModalOpenDetail] = useState(false);
    // const [eventDetail, setEventDetail] = useState({});
    const [eventDetail, setEventDetail] = useState({});
    const [isValidDetail, setIsValidDetail] = useState(false); // Có thể xem chi tiết ở màn chi tiết sk
    const [isShowMore, setIsShowMore] = useState(false);

    //Thời gian quá khứ
    const disabledDate = (current) => {
        const today = dayjs().startOf("day");
        return current < today;
    };

    //Load toàn bộ sự kiện
    useEffect(() => {
        ORMultipleRegisterApi.getAllForCalendar().then((res) => {
            convertResponse(res);
        }).catch((err) => {
           //  //console.log(err)
            message.error('Vui lòng refresh lại trang');
        })
        ORMultipleRegisterApi.getAllInfo().then((res) => {
            setListCategory(res.data.data.listCategory);
            setListObject(res.data.data.listObject);
            setListMajor(res.data.data.listMajor);
            setListSemester(res.data.data.listSemester);
        }).catch((err) => {
            message.error('Vui lòng refresh lại trang');
        })
    }, []);

    // Modal
    const [isOpenModalRegister, setIsOpenModalRegister] = useState(false);

    const showModalRegister = (arg, type) => {
        setIsShowMore(false);
        let start = new Date(arg?.start);
        let end = new Date(arg?.end);

        if(start === undefined || end === undefined){
            message.info("Vui lòng lưu trước khi bấm vào thẻ");
            return;
        }

        if (start.getTime() < new Date().getTime() || end.getTime() < new Date().getTime()) {
            message.error('Không thể đăng ký kiện trong quá khứ');
        } else if (type === 0 && arg) {
            setNewEventId(undefined);
            setModalClickTitile('Thêm mới sự kiện');
            setNewEventNameError('');
            setNewEventName('');
            setNewEventStartTime(new Date(arg.start));
            setNewEventEndTime(new Date(arg.end));
            setNewEventCategoryId(undefined);
            setNewEventExpectedParticipants(undefined);
            setNewEventType(undefined);
            setNewEventObjectId(undefined);
            setNewEventMajorId(undefined);
            setNewEventSemesterId(undefined);
            setNewEventSemesterName('');
            setNewEventBlockNumber(undefined)
            setIsOpenModalRegister(true);
            getSemester();
        } else {
            setIsOpenModalRegister(true);
        }
    };

    const handleEventClick = (event) => {
		if (event.groupId == 1) {
            listNewEvent.forEach(item => {
                if (item.id == event.id) {
                    setNewEventId(item.id);
                    setNewEventName(item.name);
                    setNewEventCategoryId(item.categoryId);
                    setNewEventStartTime(new Date(item.startTime));
                    setNewEventEndTime(new Date(item.endTime));
                    setNewEventExpectedParticipants(item.expectedParticipants);
                    setNewEventType(item.eventType);
                    setNewEventObjectId(item.objectId);
                    setNewEventMajorId(item.majorId);
                    showModalRegister(undefined, 1);
                    return;
                }
            })
        } else {
            if (event.groupId == 2) {
                setIsValidDetail(true);
            } else {
                setIsValidDetail(false);
            }
			ORMultipleRegisterApi.getDetailEvent(event.id).then((res) => {
                if (res.data.data) {
                    //console.log(res.data.data)
                    setEventDetail(res.data.data);
                    setIsModalOpenDetail(true);
                } else {
                    message.error('Không tìm thấy sự kiện');
                }
            }).catch((err) => {
               //  //console.log(err);
                message.error('Vui lòng tải lại trang');
            })
        }
    }

    const handleAddEvent = () => {
        if (newEventName.trim().length === 0) {
			setNewEventNameError('Vui lòng nhập tên sự kiện');
            return;
        } else {
            setNewEventNameError( '');
        }
        let id = Math.floor(Math.random() * (100000 + 1));
        while (listNewEvent.findIndex(item => item.id === id) !== -1) {
            id = Math.floor(Math.random() * (100000 + 1));
        }
        setData([...data, {
            id: id,
            title: newEventName,
            start: newEventStartTime,
            end: newEventEndTime,
            color: "#03C988",
            groupId: 1
        }])
        setListNewEvent([...listNewEvent, {
            name: newEventName,
            startTime: typeof newEventStartTime != 'undefined' ? newEventStartTime.getTime() : undefined,
            endTime: typeof newEventEndTime != 'undefined' ? newEventEndTime.getTime() : undefined,
            categoryId: newEventCategoryId,
            expectedParticipants: newEventExpectedParticipants,
            eventType: newEventType,
            objectId: newEventObjectId,
            majorId: newEventMajorId,
            semesterId: newEventSemesterId,
            blockNumber: newEventBlockNumber,
            id: id
        }])
        setIsOpenModalRegister(false);
    };

    const handleEditEvent = () => {
        if (typeof newEventId !== 'undefined') {
            let newListNewEvent = listNewEvent.filter(item => item.id !== newEventId);
            newListNewEvent.push({
                name: newEventName,
                startTime: typeof newEventStartTime != 'undefined' ? newEventStartTime.getTime() : undefined,
                endTime: typeof newEventEndTime != 'undefined' ? newEventEndTime.getTime() : undefined,
                categoryId: newEventCategoryId,
                expectedParticipants: newEventExpectedParticipants,
                eventType: newEventType,
                objectId: newEventObjectId,
                majorId: newEventMajorId,
                semesterId: newEventSemesterId,
                blockNumber: newEventBlockNumber,
                id: newEventId
            })
            setListNewEvent(newListNewEvent);
            let listNewData = data.filter(item => item.id !== newEventId);
            listNewData.push({
                id: newEventId,
                title: newEventName,
                start: new Date(newEventStartTime),
                end: new Date(newEventEndTime),
                color: "#03C988",
                groupId: 1
            })
            setData(listNewData);
            setIsOpenModalRegister(false);
        } else {
            setIsOpenModalRegister(false);
            message.error('Sửa thất bại')
        }
    }

    const handleCancelModalRegister = () => {
        setNewEventId(undefined);
        setIsOpenModalRegister(false);
    };

    function convertResponse(res) {
        let listEvent = [];
        for (let i = 0; i < res.data.data.length; i++) {

            let color = "";
            if (res.data.data[i].isOwnEvent === -1) {
                color = "#F7D060";
            } else if (res.data.data[i].isOwnEvent === 0) {
                color = "#15133C";
            } else if (res.data.data[i].isOwnEvent === 1) {
                color = "#95CD41";
            } else if (res.data.data[i].isOwnEvent === 2) {
                color = "#FF8400";
            } else if (res.data.data[i].isOwnEvent === 3) {
                color = "#C70039";
            } else if (res.data.data[i].isOwnEvent === 4) {
                color = "#9dfa19";
            } else {
                color = "#1677ff";
            }
            let event = {
                id: res.data.data[i].id,
                title: res.data.data[i].name,
                start: new Date(res.data.data[i].startTime),
                end: new Date(res.data.data[i].endTime),
                color: color,
                groupId: res.data.data[i].isOwnEvent == -1 ? 0 : 2
            }
            if (listEvent.find(item => item.id === res.data.data[i].id) === undefined) {
                listEvent.push(event);
            }
        }
        setData(listEvent);
    }

    const handleSave = () => {
        if (listNewEvent.length > 0) {
            ORMultipleRegisterApi.multipleRegisters(listNewEvent).then((res) => {
                setListNewEvent([]);
                convertResponse(res);
                message.success('Lưu thành công');
            }).catch((err) => {
                message.error('Fail')
            });
        } else {
            message.warning('Vui lòng thêm sự kiện mới');
        }
    }

    const customButon = {
        mybutton: {
            text: 'Lưu',
            click: function () {
                confirm({
                    title: "Xác nhận",
                    icon: <ExclamationCircleFilled/>,
                    content:
                        <p>Lưu danh sách {listNewEvent.length} sự kiện dự kiến tổ chức?</p>,
                    onOk() {
                        handleSave();
                    },
                    onCancel() {
                    },
                });

            }
        }
    }
	const getSemester = () => {
        if (typeof newEventStartTime !== 'undefined' && typeof newEventEndTime !== 'undefined') {
            listSemester.forEach(item => {
                if (item.startTime < newEventStartTime && item.endTime > newEventEndTime
                    && newEventStartTime < item.endTime && newEventEndTime > item.startTime) {
                    setNewEventSemesterName(item.name + " (" + dateFromLong(item.startTime) + ' - ' + dateFromLong(item.endTime) + ')');
                    setNewEventSemesterId(item.id);
                    if (newEventStartTime > item.startTimeBlock1 && newEventStartTime < item.endTimeBlock1
                        && newEventEndTime < item.endTimeBlock1 && newEventEndTime > item.startTimeBlock1) {
                        setNewEventBlockname('Block 1 (' + dateFromLong(item.startTimeBlock1) + ' - ' + dateFromLong(item.endTimeBlock1) + ')');
                        setNewEventBlockNumber(false);
                    } else if (newEventStartTime > item.startTimeBlock2 && newEventStartTime < item.endTimeBlock2
                        && newEventEndTime < item.endTimeBlock2 && newEventEndTime > item.startTimeBlock2) {
                        setNewEventBlockname('Block 2 (' + dateFromLong(item.startTimeBlock2) + ' - ' + dateFromLong(item.endTimeBlock2) + ')')
                        setNewEventBlockNumber(true);
                    }
                }
            })
        }
    }

    const handleDeleteNewEvent = (id) => {
        let newData = data.filter(item => item.id !== id);
        setData(newData);
        let newListNewEvent = listNewEvent.filter(item => item.id !== id);
        setListNewEvent(newListNewEvent);
        setIsOpenModalRegister(false);
    }

    const handleCancelModalDetail = () => {
        setIsModalOpenDetail(false);
    };

    const handleMouseLeave = (info) => {
        const popup = info.el.querySelector('.event-popup');
        if (popup) {
            info.el.removeChild(popup);
        }
    };

    const handleMouseEnter = (info) => {
        const popup = document.createElement('div');
        popup.innerHTML = `<p className="popupTitle">${info.event.title}</p>
							${info.event.start.toLocaleTimeString()} - ${info.event.end.toLocaleTimeString()}`;
        popup.className = 'event-popup';
        info.el.appendChild(popup);
        popup.classList.add('right');
    };

    return (
        <>
            <div className={"wrapper"}>
                <h2><FontAwesomeIcon icon={faCalendarPlus} size="lg" color="#3085C3"/> Đăng ký hàng loạt</h2>
                <hr/>
                <Row justify="end">

                </Row>
                <Divider orientation="left">Mô tả</Divider>
                <Row justify="start">
                    <Col span={22}>
                        <Space size={[0, 8]} wrap>
                            <Tag color="#F7D060" style={{color: "black"}}>Sự kiện của NTC khác</Tag>
                            <Tag color="#15133C">Hủy</Tag>
                            <Tag color="#95CD41">Dự kiến tổ chức</Tag>
                            <Tag color="#FF8400">Chờ phê duyệt</Tag>
                            <Tag color="#C70039">Cần sửa</Tag>
                            <Tag color="#9dfa19" style={{color: "black"}}>Đã phê duyệt</Tag>
                            <Tag color="#1677ff">Sự kiện đã tổ chức</Tag>
                            <Tag color="#03C988">Mới thêm (Chưa lưu)</Tag>
                        </Space>
                    </Col>
                </Row>
                <Divider/>
                <FullCalendar
                    plugins={[dayGridPlugin, interactionPlugin, timeGridPlugin]}
                    buttonText={{
                        today: 'Hôm nay',
                        month: 'Tháng',
                        week: 'Tuần',
                        day: 'Ngày',
                        list: 'Agenda'
                    }}
                    customButtons= {customButon}
                    locale='vi'
                    initialDate={new Date()}
                    navLinks={true}
                    selectMirror={true}
                    dayMaxEvents={true}
                    initialView="timeGridWeek"
                    dayHeaderFormat={{
                        weekday: 'long'
                    }}
                    eventTimeFormat={{
                        hour: '2-digit',
                        minute: '2-digit',
                        hour12: false
                    }}
                    selectable={true}
                    eventDisplay='block'
                    displayEventEnd={true}
                    nowIndicator={true}
                    slotLabelFormat={{
                        hour: 'numeric',
                        minute: '2-digit',
                        hour12: false
                    }}
                    now={new Date().toISOString()}
                    allDaySlot={false}
                    weekends={true}
                    scrollTime={true}
                    events={data}
                    headerToolbar={{
                        left: 'prev,next, today',
                        center: 'title',
                        right: 'dayGridMonth,timeGridWeek,timeGridDay, mybutton'
                    }}
                    select={(arg) => showModalRegister(arg, 0)}
                    eventClick={(arg) => handleEventClick(arg.event)}
                    eventMouseEnter={(arg) => handleMouseEnter(arg)}
                    eventMouseLeave={(arg) => handleMouseLeave(arg)}
                />

                {/******** MODAL Đăng ký hàng loạt ********/}
                <Modal title={modalClickTitile}
                       okText={"Thêm"}
                       width={isShowMore ? "50%" : "30%"}
                       cancelButtonProps={{
                    	danger: true
                       }}
                       footer={typeof newEventId !== 'undefined' ? [
                           <Button key="back" onClick={handleCancelModalRegister}>
                               Cancel
                           </Button>,
                           <Popconfirm
                               title="Delete the task"
                               description="Are you sure to delete this task?"
                               onConfirm={() => handleDeleteNewEvent(newEventId)}
                               okText="Yes"
                               cancelText="No"
                           >
                               <Button danger type="primary">
                                   Xóa sự kiện
                               </Button>
                           </Popconfirm>,
                           <Button key="submit" type="primary" onClick={handleEditEvent}>
                               Sửa sự kiện
                           </Button>,
                       ] : [
                           <Button key="back" onClick={handleCancelModalRegister}>
                               Cancel
                           </Button>,
                           <Button key="submit" type="primary" onClick={handleAddEvent}>
                               Thêm sự kiện
                           </Button>,
                       ]}
                       open={isOpenModalRegister}
                       onCancel={handleCancelModalRegister}
                >
                    <hr/>
                    <Space direction="vertical" size="middle" style={{ display: 'flex', marginTop: '20px'}}>
                        <Row>
                            <Col span={!isShowMore ? 24 : 12}>
                                <label>Tên sự kiện</label>
                                <Input style={{width: "95%"}} size={"large"} rootClassName={"mb-5"} type="text" value={newEventName} onChange={(e) => setNewEventName(e.target.value)}/>
                                <span style={{color: "red"}}>{newEventNameError}</span><br/>
                                <label>Thời gian diễn ra</label>
                                <RangePicker
                                    size={"large"}
                                    style={{width: "95%"}}
                                    disabledDate={disabledDate}
                                    value={[typeof newEventStartTime != 'undefined' ? dayjs(newEventStartTime) : '', typeof newEventStartTime != 'undefined' ? dayjs(newEventEndTime) : '']}
                                    showTime={{ format: 'HH:mm' }}
                                    format="HH:mm DD-MM-YYYY"
                                    onChange={(value: DatePickerProps['value'] | RangePickerProps['value'], dateString: [string, string] | string
                                    ) => {
                                       setNewEventStartTime(dateString[0] !== '' ? new Date(dateString[0]).getTime() : undefined);
                                       setNewEventEndTime(dateString[1] !== '' ? new Date(dateString[1]).getTime() : undefined);
                                       getSemester();
                                    }}
                                /><br/>
                                {isShowMore &&
                                    <>
                                        <label>Thể loại</label>
                                        <Select
                                            size={"large"}
                                            style={{ width: "95%" }}
                                            value={newEventCategoryId}
                                            onChange={(e) => setNewEventCategoryId(e)}
                                        >
                                            {listCategory.map((item) => (
                                                <Select.Option key={item.id} value={item.id}>
                                                    {item.name}
                                                </Select.Option>
                                            ))}
                                        </Select><br/>
                                        <label>Học kỳ</label>
                                        <Input size={"large"} style={{width: "95%"}} type="text" readOnly value={newEventSemesterName}/><br/>
                                        <label>Số người tham gia dự kiến</label>
                                        <InputNumber size={"large"} style={{width: "95%"}} rootClassName={"mb-5"} min={0} max={32760} value={newEventExpectedParticipants} onChange={(e) => setNewEventExpectedParticipants(e)}/>
                                    </>
                            	}
                            </Col>
                            {isShowMore &&
                                <Col span={12}>

                                    <label>Sự kiện dành cho</label>
                                    <Select
                                        size={"large"}
                                        value={newEventType}
                                        style={{ width: "95%" }}
                                        onChange={(e) => setNewEventType(e)}
                                        options={[
                                            { value: '0', label: 'Sinh viên' },
                                            { value: '1', label: 'Giảng viên' },
                                            { value: '2', label: 'Sinh viên và Giảng viên' },
                                        ]}
                                    /><br/>
                                    <label>Đối tượng</label>
                                    <Select
                                        size={"large"}
                                        style={{ width: "95%" }}
                                        value={newEventObjectId}
                                        mode="multiple"
                                        onChange={(e) => setNewEventObjectId(e)}
                                    >
                                        {listObject.map((item) => (
                                            <Select.Option key={item.id} value={item.id}>
                                                {item.name}
                                            </Select.Option>
                                        ))}
                                    </Select><br/>
                                    <label>Bộ môn</label>
                                    <Select
                                        size={"large"}
                                        style={{ width: "95%" }}
                                        value={newEventMajorId}
                                        mode="multiple"
                                        onChange={(e) => setNewEventMajorId(e)}
                                    >
                                        {listMajor.map((item) => (
                                            <Select.Option key={item.id} value={item.id}>
                                                {item.code} - {item.name}
                                            </Select.Option>
                                        ))}
                                    </Select><br/>
                                    <label>Block</label>
                                    <Input style={{ width: "95%" }} size={"large"} readOnly value={newEventBlockname}/><br/>
                                </Col>
                            }
                        </Row>
                        <Row>
                            {!isShowMore &&
                            	<Button onClick={() => setIsShowMore(true)}>More</Button>
                            }
                        </Row>
                    </Space>
                </Modal>

            	{/************ MODAL Chi tiết sự kiện *************/}
                <Modal title="Chi tiết sự kiện"
                       width={"50%"} open={isModalOpenDetail}
                       footer={isValidDetail ? [
                           <Button key="back" onClick={handleCancelModalDetail}>
                               Cancel
                           </Button>,
                           <Button key="detail" type="primary" >
                               <Link to={'/organizer-management/event-detail/' + eventDetail.id}>Xem chi tiết</Link>
                           </Button>
                       ] : [
                           <Button key="back" onClick={handleCancelModalDetail}>
                               Cancel
                           </Button>,
                       ]}
                       onCancel={handleCancelModalDetail}>
                    <hr/>
                    <Row>
                        <Col span={24}>
                            <Descriptions bordered column={1}>
                                <Descriptions.Item span={1} label="Tên sự kiện">{eventDetail.name}</Descriptions.Item>
                                <Descriptions.Item span={1} label="Thời gian diễn ra">{eventDetail.time}</Descriptions.Item>
                                <Descriptions.Item span={1} label="Thể loại">{eventDetail.categoryName}</Descriptions.Item>
                                <Descriptions.Item span={1} label="Người tổ chức">{eventDetail.organizers}</Descriptions.Item>
                                <Descriptions.Item span={1} label="Kiểu sự kiện">{eventDetail.eventType}</Descriptions.Item>
                                <Descriptions.Item span={1} label="Học kỳ">{eventDetail.semesterName}</Descriptions.Item>
                                <Descriptions.Item span={1} label="Block">{eventDetail.blockName}</Descriptions.Item>
                                <Descriptions.Item span={1} label="Số người tham gia dự kiến">{eventDetail.expectedParticipants}</Descriptions.Item>
                                <Descriptions.Item span={1} label="Bộ môn">{eventDetail.majors}</Descriptions.Item>
                                <Descriptions.Item span={1} label="Đối tượng">{eventDetail.objects}</Descriptions.Item>
                                <Descriptions.Item span={1} label="Trạng thái">{eventDetail.status}</Descriptions.Item>
                            </Descriptions>
                        </Col>
                    </Row>
                </Modal>
            </div>
        </>
    );
}