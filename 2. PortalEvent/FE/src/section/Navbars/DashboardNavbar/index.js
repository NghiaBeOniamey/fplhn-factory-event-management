import React, { useEffect, useState } from "react";

// prop-types is a library for typechecking of props.
import PropTypes from "prop-types";

// @mui core components
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import SettingsIcon from "@mui/icons-material/Settings";

// Argon Dashboard 2 MUI components
import ArgonBox from "../../../components/ArgonBox";

import {
    navbar,
    navbarContainer,
    navbarDesktopMenu,
    navbarIconButton,
    navbarMobileMenu,
    navbarRow,
} from "../../Navbars/DashboardNavbar/styles";

// Argon Dashboard 2 MUI context
import { setMiniSidenav, setOpenConfigurator, setTransparentNavbar, useArgonController, } from "../../../context";

// Images
import Breadcrumbs from "../../../section/Breadcrumbs";
import { LogoutOutlined } from "@ant-design/icons";
import {
    Avatar,
    Button,
    Card,
    Checkbox,
    Descriptions,
    Tooltip,
    InputNumber,
    message,
    Modal,
    Popconfirm,
    Popover
} from "antd";
import Meta from "antd/es/card/Meta";
import Cookies from "js-cookie";
import { portEvent, portIdentity, URL_API_SYSTEM } from "../../../pages/ApiUrl";
import { convertRole, getCurrentUser } from "../../../utils/Common";
import { ACTOR_BDT, ACTOR_SV } from "../../../constants/ActorConstant";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDragon, faFloppyDisk, faGear, faKey } from "@fortawesome/free-solid-svg-icons";
import { CheckboxChangeEvent } from "antd/es/checkbox";
import { request } from "../../../helper/Request.helper";
import { RolesAPI } from './../../../helper/RolesAPI';

function DashboardNavbar({ absolute, light, isMini, routeName, urlRole, darkMode }) {
    // const [openMenu, setOpenMenu] = useState(false);
    const [navbarType, setNavbarType] = useState();
    const [controller, dispatch] = useArgonController();
    const { miniSidenav, transparentNavbar, fixedNavbar, openConfigurator } = controller;
    const [currentUser, setCurrentUser] = useState({});
    const [arrRole, setArrRole] = useState([]);
    const [isEnableUpdateSystemConfig, setIsEnableUpdateSystemConfig] = useState(false);
    const [isModalSystemOptionOpen, setIsModalSystemOptionOpen] = useState(false);
    const [isDisableSaveOption, setIsDisableSaveOption] = useState(true);
    const [systeamOption, setSystemOption] = useState({
        id: 1,
        mandatoryApprovalDays: 1,
        isAllowNotEnoughTimeApproval: false,
        isAllowCloseEvent: false,
        minimumCloseTime: 0
    });
    const [newSysteamOption, setNewSystemOption] = useState({
        id: 1,
        mandatoryApprovalDays: 1,
        isAllowNotEnoughTimeApproval: true,
        isAllowCloseEvent: true,
        minimumCloseTime: 0
    });
    const token = Cookies.get("token");

    const [actionArr, setActionArr] = useState([
        <Button type="link" danger style={{ fontSize: "large" }} onClick={() => {
            window.location.href = portIdentity + "module-switch";
        }}>
            <Tooltip placement="bottom" title="Module Switch" color={'volcano'} >
                <FontAwesomeIcon icon={faDragon} />&nbsp; Module
            </Tooltip>
        </Button>,
        <Button type="link" danger style={{ fontSize: "large" }} onClick={() => {
            window.location.href = portEvent + "permission-event";
        }}>
            <Tooltip placement="bottom" title="Role Switch" color={'volcano'} >
                <FontAwesomeIcon icon={faKey} />&nbsp; Role
            </Tooltip>
        </Button>,
        <Button type="link" danger style={{ fontSize: "large" }} onClick={() => {
            let user = getCurrentUser();
            let check = checkUser(user.role);
            //  //console.log(check);
            const data = {
                userId: user.id,
                userType: check ? "CAN_BO" : "SINH_VIEN",
            }
            RolesAPI.callApiIdentityNavbar(data)
                .then((res) => {
                    //  //console.log(res);
                    Cookies.remove("token");
                    window.location.href = portIdentity;
                }).catch((err) => {
                    //  //console.log(err);
                });
        }}><LogoutOutlined /> Đăng xuất</Button>,
    ]);

    const checkUser = (role) => {
        for (let i = 0; i < role.length; i++) {
            let element = role[RolesAPI];
            //  //console.log(element);
            if (element === ACTOR_SV) {
                return false;
            } else {
                return true;
            }
        }
    };

    useEffect(() => {
        //console.log("run");
        // Setting the navbar type
        if (fixedNavbar) {
            setNavbarType("sticky");
        } else {
            setNavbarType("static");
        }

        // A function that sets the transparent state of the navbar.
        function handleTransparentNavbar() {
            setTransparentNavbar(
                dispatch,
                (fixedNavbar && window.scrollY === 0) || !fixedNavbar
            );
        }

        /**
         The event listener that's calling the handleTransparentNavbar function when
         scrolling the window.
         */
        window.addEventListener("scroll", handleTransparentNavbar);

        // Call the handleTransparentNavbar function to set the state with the initial value.
        handleTransparentNavbar();
        //Check role to display
        const user = getCurrentUser();
        if (user) {
            setCurrentUser(user);
            if (typeof user.role === 'object') {
                let arr = [];
                let check = false;
                user.role.forEach(item => {
                    if (item === ACTOR_BDT) {
                        check = true;
                    }
                    arr.push(<em><h4>{convertRole(item)}</h4></em>);
                })
                setArrRole(arr);
                if (check) {
                    setIsEnableUpdateSystemConfig(true);
                }
            } else if (typeof user.role === 'string') {
                setArrRole(<em><h4>{convertRole(user.role)}</h4></em>);
                if (user.role === ACTOR_BDT) {
                    setIsEnableUpdateSystemConfig(true);
                } else {
                    setIsEnableUpdateSystemConfig(false);
                }
            }
        }
        //get System Option
        if ((typeof user.role === 'string') && user.role === ACTOR_BDT || (typeof user.role === 'object') && user.role.includes(ACTOR_BDT)) {
            request({
                method: "GET",
                url: URL_API_SYSTEM + '/get-system-option',
            }).then((res) => {
                setSystemOption(res.data.data);
                setNewSystemOption(res.data.data);
            }).catch((err) => {
                //  //console.log(err.response);
            });
        }

        // Remove event listener on cleanup
        return () => window.removeEventListener("scroll", handleTransparentNavbar);
    }, [dispatch, fixedNavbar]);

    useEffect(() => {
        if (isEnableUpdateSystemConfig) {
            if (actionArr.length === 3) {
                setActionArr([<Button type="link" danger style={{ fontSize: "large" }} onClick={() => {
                    setIsModalSystemOptionOpen(true);
                }}>
                    <FontAwesomeIcon icon={faGear} />&nbsp;Option</Button>, ...actionArr]); // lỗi
            }
        } else {
            if (actionArr.length > 3) {
                const copyActionArr = actionArr; // Tạo một bản sao của mảng actionArr
                copyActionArr.shift(); // Xóa phần tử đầu tiên khỏi bản sao mảng
                setActionArr(copyActionArr);
            }
        }
    }, [actionArr, isEnableUpdateSystemConfig]);

    useEffect(() => {
        if (newSysteamOption.isAllowNotEnoughTimeApproval !== systeamOption.isAllowNotEnoughTimeApproval
            || newSysteamOption.mandatoryApprovalDays !== systeamOption.mandatoryApprovalDays
            || newSysteamOption.isAllowCloseEvent !== systeamOption.isAllowCloseEvent
            || newSysteamOption.minimumCloseTime !== systeamOption.minimumCloseTime) {
            setIsDisableSaveOption(false);
        } else {
            setIsDisableSaveOption(true);
        }
    }, [newSysteamOption, systeamOption.isAllowNotEnoughTimeApproval, systeamOption.mandatoryApprovalDays]);

    const handleMiniSidenav = () => setMiniSidenav(dispatch, !miniSidenav);
    const handleConfiguratorOpen = () =>
        setOpenConfigurator(dispatch, !openConfigurator);

    // Render the notifications menu
    // const handleOpenMenu = (event) => setOpenMenu(event.currentTarget);
    // const handleCloseMenu = () => setOpenMenu(false);
    // const renderMenu = () => (
    //   <Menu
    //     anchorEl={openMenu}
    //     anchorReference={null}
    //     anchorOrigin={{
    //       vertical: "bottom",
    //       horizontal: "left",
    //     }}
    //     open={Boolean(openMenu)}
    //     onClose={handleCloseMenu}
    //     sx={{ mt: 2 }}
    //   >
    //   </Menu>
    // );

    const content = (
        <Card
            style={{ marginTop: 16, padding: "10px" }}
            actions={actionArr}
        >
            <Meta
                avatar={<Avatar size={80} src={currentUser.avatar} />}
                title={currentUser.name}
                description={(
                    <>
                        <h4>{currentUser.email}</h4>
                        {arrRole}
                    </>
                )}
            />
        </Card>
    );

    const handleSaveConfig = () => {
        request({
            method: "PUT",
            url: URL_API_SYSTEM + '/update-system-option',
            data: newSysteamOption,
        }).then((res) => {
            message.success('Lưu thành công');
            setSystemOption(res.data.data);
            setNewSystemOption(res.data.data);
        }).catch((err) => {
            //  //console.log(err.response);
            message.error("Lưu thất bại!");
        });
    }

    return (
        <>
            <AppBar
                position={absolute ? "absolute" : navbarType}
                color="inherit"
                sx={(theme) => navbar(theme, { transparentNavbar, absolute, light })}
            >
                <Toolbar sx={(theme) => navbarContainer(theme, { navbarType })}>
                    <ArgonBox
                        color={"white"}
                        mb={{ xs: 1, md: 0 }}
                        sx={(theme) => navbarRow(theme, { isMini })}
                    >
                        <Breadcrumbs
                            icon="home"
                            urlRole={urlRole}
                            title={routeName}
                            light={true}
                        />
                        <MenuIcon
                            fontSize="medium"
                            sx={navbarDesktopMenu}
                            onClick={handleMiniSidenav}
                        >
                            {miniSidenav ? "menu_open" : "menu"}
                        </MenuIcon>
                    </ArgonBox>
                    {isMini ? null : (
                        <ArgonBox sx={(theme) => navbarRow(theme, { isMini })}>
                            <Popover content={content}>
                                <ArgonBox pr={1} style={{ cursor: "pointer" }}>
                                    <div style={{ display: "flex" }}>
                                        <Avatar size={40} src={currentUser.avatar} />
                                        <ArgonBox pr={2} pl={1} pt={1} color={darkMode ? "white" : "dark"}><h5>Xin
                                            chào{currentUser.name ? ', ' + currentUser.name : ''}</h5>
                                        </ArgonBox>
                                    </div>
                                    {/*/>*/}
                                </ArgonBox>
                            </Popover>
                            <ArgonBox color={light ? "white" : "inherit"}>
                                <IconButton
                                    size="small"
                                    color={light && transparentNavbar ? "white" : "dark"}
                                    sx={navbarMobileMenu}
                                    onClick={handleMiniSidenav}
                                >
                                    <MenuIcon>{miniSidenav ? "menu_open" : "menu"}</MenuIcon>
                                </IconButton>
                                <IconButton
                                    size="small"
                                    color={"white"}
                                    sx={navbarIconButton}
                                    onClick={handleConfiguratorOpen}
                                >
                                    <SettingsIcon>settings</SettingsIcon>
                                </IconButton>

                                {/****** SEARCH BUTTON & NOTIFICATION BUTTON*******/}
                                {/*<Link to="/authentication/sign-in/basic">*/}
                                {/*  <IconButton sx={navbarIconButton} size="small">*/}
                                {/*    <AccountCircleIcon*/}
                                {/*      sx={({ palette: { dark, white } }) => ({*/}
                                {/*        color:*/}
                                {/*          light && transparentNavbar ? white.main : dark.main,*/}
                                {/*      })}*/}
                                {/*    >*/}
                                {/*      account_circle*/}
                                {/*    </AccountCircleIcon>*/}
                                {/*    <ArgonTypography*/}
                                {/*      variant="button"*/}
                                {/*      fontWeight="medium"*/}
                                {/*      color={light && transparentNavbar ? "white" : "dark"}*/}
                                {/*    >*/}
                                {/*      Sign in*/}
                                {/*    </ArgonTypography>*/}
                                {/*  </IconButton>*/}
                                {/*</Link>*/}
                                {/*<IconButton*/}
                                {/*  size="small"*/}
                                {/*  color={light && transparentNavbar ? "white" : "dark"}*/}
                                {/*  sx={navbarIconButton}*/}
                                {/*  aria-controls="notification-menu"*/}
                                {/*  aria-haspopup="true"*/}
                                {/*  variant="contained"*/}
                                {/*  onClick={handleOpenMenu}*/}
                                {/*>*/}
                                {/*  <NotificationsIcon>notifications</NotificationsIcon>*/}
                                {/*</IconButton>*/}
                                {/*<NotificationItem*/}
                                {/*  image={<img src={logoSpotify} alt="person" />}*/}
                                {/*  title={["New message", "from Laur"]}*/}
                                {/*  date="13 minutes ago"*/}
                                {/*  onClick={handleCloseMenu}*/}
                                {/*/>*/}
                                {/*<NotificationItem*/}
                                {/*  image={<img src={logoSpotify} alt="person" />}*/}
                                {/*  title={["New album", "by Travis Scott"]}*/}
                                {/*  date="1 day"*/}
                                {/*  onClick={handleCloseMenu}*/}
                                {/*/>*/}
                                {/*{renderMenu()}*/}
                            </ArgonBox>
                        </ArgonBox>
                    )}
                </Toolbar>
            </AppBar>
            <Modal title={<><FontAwesomeIcon icon={faGear} />&nbsp;<span>System Option</span></>}
                open={isModalSystemOptionOpen}
                width={700}
                onCancel={() => {
                    setIsModalSystemOptionOpen(false);
                }}
                footer={[
                    <Button key="cancelModel" onClick={() => {
                        setIsModalSystemOptionOpen(false);
                    }}>
                        Cancel
                    </Button>,
                    <Popconfirm
                        title="Bạn chắc chắn muốn thay đổi tùy chọn hệ thống"
                        onConfirm={handleSaveConfig}
                        okText="Yes"
                        cancelText="No"
                        disabled={isDisableSaveOption}
                    >
                        <Button disabled={isDisableSaveOption} style={isDisableSaveOption ? { marginLeft: "5px" } : {}} key="saveSystemConfig" type="primary">
                            <FontAwesomeIcon icon={faFloppyDisk} />&nbsp;Lưu
                        </Button>
                    </Popconfirm>,
                ]}>
                <hr style={{ opacity: 0.2, marginBottom: "10px" }} />
                <Descriptions bordered column={1}>
                    <Descriptions.Item span={1}
                        contentStyle={{ width: "25%" }}
                        label="Số ngày cần gửi yêu cầu phê duyệt trước thời gian diễn ra sự kiện:">
                        <InputNumber min={1} max={100} defaultValue={1}
                            style={{ marginLeft: "5px", width: "100%" }}
                            value={newSysteamOption.mandatoryApprovalDays}
                            size={"large"}
                            addonAfter={"Ngày"}
                            readOnly={!newSysteamOption.isAllowNotEnoughTimeApproval}
                            onChange={(value) => {
                                setNewSystemOption(prevState => ({
                                    ...prevState,
                                    mandatoryApprovalDays: value
                                }))
                            }} />
                    </Descriptions.Item>
                    <Descriptions.Item
                        className="text-center"
                        span={1}
                        label="Cho phép gửi yêu cầu phê duyệt sự kiện không đủ thời gian phê duyệt:">
                        <Checkbox checked={newSysteamOption.isAllowNotEnoughTimeApproval}
                            onChange={(e: CheckboxChangeEvent) => {
                                setNewSystemOption(prevState => ({
                                    ...prevState,
                                    isAllowNotEnoughTimeApproval: e.target.checked
                                }))
                            }} />
                    </Descriptions.Item>
                    <Descriptions.Item
                        className="text-center"
                        span={1}
                        label="Cho phép đóng sự kiện khi đã được phê duyệt:">
                        <Checkbox checked={newSysteamOption.isAllowCloseEvent}
                            onChange={(e: CheckboxChangeEvent) => {
                                setNewSystemOption(prevState => ({
                                    ...prevState,
                                    isAllowCloseEvent: e.target.checked
                                }))
                            }} />
                    </Descriptions.Item>
                    <Descriptions.Item span={1}
                        contentStyle={{ width: "25%" }}
                        label="Thời gian tối thiểu có thể đóng sự kiện trước khi diễn ra:">
                        <InputNumber min={1} max={100} defaultValue={1}
                            style={{ marginLeft: "5px", width: "100%" }}
                            readOnly={!newSysteamOption.isAllowCloseEvent}
                            value={newSysteamOption.minimumCloseTime ? newSysteamOption.minimumCloseTime / 3600000 : ''}
                            size={"large"}
                            addonAfter={"Giờ"}
                            onChange={(value) => {
                                setNewSystemOption(prevState => ({
                                    ...prevState,
                                    minimumCloseTime: value * 3600000
                                }))
                            }} />
                    </Descriptions.Item>
                </Descriptions>
                {/*</Form.Item>*/}
            </Modal>
        </>
    );
}

// Setting default values for the props of DashboardNavbar
DashboardNavbar.defaultProps = {
    absolute: false,
    light: true,
    isMini: false,
};

// Typechecking props for the DashboardNavbar
DashboardNavbar.propTypes = {
    absolute: PropTypes.bool,
    light: PropTypes.bool,
    isMini: PropTypes.bool,
};

export default DashboardNavbar;
