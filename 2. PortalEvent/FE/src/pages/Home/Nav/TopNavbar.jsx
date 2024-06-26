import React, {useEffect, useState} from "react";
import {Link} from "react-scroll";
import {Link as LinkTo} from "react-router-dom";
// Components
import Sidebar from "../Nav/Sidebar";
import Backdrop from "../Elements/Backdrop";
import Cookies from "js-cookie";
import {portIdentity} from "../../ApiUrl";
// Assets
import BurgerIcon from "../../../assets/svg/BurgerIcon";
// ICON
import HomeIcon from '@mui/icons-material/Home';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import InfoIcon from '@mui/icons-material/Info';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faComments, faList} from "@fortawesome/free-solid-svg-icons";
import {Avatar, Button, Card, Popover} from "antd";
import {LogoutOutlined} from "@ant-design/icons";
import Meta from "antd/es/card/Meta";
import {convertRole, getCurrentUser} from "../../../utils/Common";
import {ACTOR_SV} from "../../../constants/ActorConstant";

const TopNavbar = ({isDetail}) => {
    const [y, setY] = useState(window.scrollY);
    const [sidebarOpen, toggleSidebar] = useState(false);
    const [arrRole, setArrRole] = useState([]);
    const [currentUser, setCurrentUser] = useState({});
    const [isParticipant, setIsparticipant] = useState(false);

    useEffect(() => {
        window.addEventListener("scroll", () => setY(window.scrollY));
        return () => {
            window.removeEventListener("scroll", () => setY(window.scrollY));
        };
    }, [y]);

    useEffect(() => {
        const user = getCurrentUser();
        if (user) {
            setCurrentUser(user);
            if (typeof user.role === 'object') {
                let arr = [];
                user.role.forEach(item => {
                    arr.push(<em><h4>{convertRole(item)}</h4></em>);
                })
                setArrRole(arr);
            } else if (typeof user.role === 'string') {
                setArrRole(<em><h4>{convertRole(user.role)}</h4></em>);
                if (user.role === ACTOR_SV) {
                    setIsparticipant(true);
                }
            }
        }
    }, []);

    const handleLogout = () => {
        Cookies.remove("token");
        window.location.href = portIdentity;
    }
    const content = (
        <Card
            style={{marginTop: 16}}
            actions={[
                <Button type="link" danger style={{fontSize: "large"}} onClick={handleLogout}><LogoutOutlined/> Đăng
                    xuất</Button>
            ]}>
            <Meta
                style={{fontFamily: 'nunito'}}
                avatar={<Avatar size={80} src={currentUser.avatar}/>}
                title={currentUser.name}
                extra={<a href="#">More</a>}
                description={(
                    <>
                        <h4>{currentUser.email}</h4>
                        {arrRole}
                    </>
                )}
            />
        </Card>
    );

    return (
        <>
            <Sidebar sidebarOpen={sidebarOpen} toggleSidebar={toggleSidebar} isDetail={isDetail}/>
            {sidebarOpen && <Backdrop toggleSidebar={toggleSidebar}/>}
            <button className="mobile-nav-toggle" onClick={() => toggleSidebar(!sidebarOpen)}>
                <BurgerIcon/>
            </button>
            <div id="navbar" className="d-flex flex-column justify-content-center">
                <nav className="navbar nav-menu">
                    <ul>
                        <li>
                            <Link activeClass="active" className="nav-link scrollto"
                                  style={{padding: "10px 15px"}}
                                  to="header" spy={true} smooth={true} offset={-80}>
                                <HomeIcon></HomeIcon>
                                <span>Home</span>
                            </Link>
                        </li>
                        <li>
                            <Link activeClass="active" className="nav-link scrollto"
                                  style={{padding: "10px 15px"}}
                                  to="calendar" spy={true} smooth={true} offset={-80}>
                                <CalendarMonthIcon></CalendarMonthIcon>
                                <span>Lịch sự kiện</span>
                            </Link>
                        </li>
                        {/* {isDetail && (
                            <>
                                <li>
                                    <Link activeClass="active" className="nav-link scrollto"
                                          style={{padding: "10px 15px"}}
                                          to="resume" spy={true} smooth={true} offset={-80}>
                                        <InfoIcon></InfoIcon>
                                        <span>Chi tiết sự kiện</span>
                                    </Link>
                                </li>
                                <li>
                                    <Link activeClass="active" className="nav-link scrollto"
                                          style={{padding: "10px 15px"}}
                                          to="comment" spy={true} smooth={true} offset={-80}>
                                        <FontAwesomeIcon icon={faComments} size="xl"/>
                                        <span>Comment</span>
                                    </Link>
                                </li>
                            </>
                        )} */}
                        {!isParticipant &&
                            <li>
                                <LinkTo activeClass="active" className="nav-link scrollto"
                                        style={{padding: "10px 15px"}}
                                        to="/" spy={true} smooth={true} offset={-80}>
                                    <FontAwesomeIcon icon={faList} size="xl"/>
                                    <span>Menu quản lý</span>
                                </LinkTo>
                            </li>
                        }
                    </ul>
                    <div>
                        <Popover className="profile" content={content} placement="right">
                            <Avatar
                                style={{
                                    zIndex: "9999",
                                    cursor: "pointer"
                                }}
                                size={56} src={currentUser.avatar}/>
                        </Popover>
                    </div>
                </nav>
            </div>
        </>
    )
}
export default TopNavbar;