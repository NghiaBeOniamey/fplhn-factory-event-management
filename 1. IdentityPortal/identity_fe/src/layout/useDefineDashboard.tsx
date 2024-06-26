import { PERMISSIONS } from "#constant/index";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { removeAuthorization } from "#context/redux/slice/AuthSlice";
import { openModalPersonalInfo } from "#context/redux/slice/ModalSlice";
import { RootState } from "#context/redux/store";
import { logout } from "#service/api/auth.api";
import permissionVerify from "#utils/permissionVerify";
import { ApartmentOutlined, ProjectOutlined } from "@ant-design/icons";
import { Button, Modal, Tooltip } from "antd";
import { useCallback, useMemo, useState } from "react";
import { BsFilePersonFill } from "react-icons/bs";
import { FaCity, FaRegUser } from "react-icons/fa";
import { GoArrowSwitch } from "react-icons/go";
import { MdOutlineLogin } from "react-icons/md";
import { PiStudentBold } from "react-icons/pi";
import { RiSoundModuleFill } from "react-icons/ri";
import { SiStudyverse } from "react-icons/si";
import { useDispatch, useSelector } from "react-redux";
import { Link, useLocation, useNavigate } from "react-router-dom";

export const buttonNavigatorStyle = {
  marginRight: "8px",
  marginTop: 7,
  fontSize: 18,
} as React.CSSProperties;

export const textNavigatorStyle = {
  marginLeft: 20,
  marginRight: 22,
  fontSize: 14.5,
  fontWeight: 500,
} as React.CSSProperties;

export interface MenuItem {
  key: string;
  icon: React.ReactNode;
  children?: MenuItem[];
  label: React.ReactNode;
  permissions?: string[];
}

export function getItem(
  icon: React.ReactNode,
  key: string,
  permissions?: string[],
  label?: React.ReactNode,
  children?: MenuItem[]
): MenuItem {
  return { icon, key, children, label, permissions };
}

export const useDefineDashboard = () => {
  const [collapsed, setCollapsed] = useState<boolean>(false);

  const { userInfo, permissions } = useSelector(
    (state: RootState) => state.auth.authorization
  );

  const [loading, setLoading] = useState<boolean>(false);

  const dispatch = useDispatch();

  const navigate = useNavigate();

  const location = useLocation();

  const handleLogout = useCallback(async () => {
    Modal.confirm({
      title: "Đăng xuất",
      content: "Bạn có chắc chắn muốn đăng xuất?",
      okText: "Đăng xuất",
      cancelText: "Hủy",
      onOk: async () => {
        setLoading(true);
        try {
          const response = await logout({
            userId: userInfo?.userId,
            userType: userInfo?.userType,
          });
          if (response?.status === 200) {
            dispatch(removeAuthorization());
            navigate("/");
          }
        } catch (e) {
          Modal.error({
            title: INTERNAL_SERVER_ERROR,
            content: "Bạn có muốn bắt buộc đăng xuất không?",
            onOk: () => {
              dispatch(removeAuthorization());
              navigate("/");
            },
          });
        } finally {
          setLoading(false);
        }
      },
      centered: true,
      okButtonProps: {
        className: "bg-[#052C65] border-none",
      },
      cancelButtonProps: {
        type: "default",
        className: "border-[#052C65] hover:text-[#052C65] text-[#052C65]",
      },
      maskClosable: true,
      zIndex: 9999,
    });
  }, [dispatch, navigate, setLoading, userInfo]);

  const content = useMemo(() => {
    return () => {
      return (
        <div className='container mx-auto my-5 max-w-lg'>
          <ul className='list-none bg-gray-100 rounded-md shadow-md p-4'>
            <li className='my-2'>
              <span className='font-semibold'>Họ và tên:</span>{" "}
              {userInfo?.fullName}
            </li>
            <li className='my-2'>
              <span className='font-semibold'>Email:</span> {userInfo?.email}
            </li>
            <li className='my-2'>
              <span className='font-semibold'>Mã nhân viên:</span>{" "}
              {userInfo?.userCode}
            </li>
            {userInfo?.campusName && (
              <li className='my-2'>
                <span className='font-semibold'>Cơ sở:</span>{" "}
                {userInfo?.campusName}
              </li>
            )}
            <li className='my-2'>
              <span className='font-semibold'>Vai trò:</span>
              <div className='grid grid-cols-2 gap-2 mt-2'>
                {Array.isArray(permissions) ? (
                  permissions.map((permission, index) => (
                    <Tooltip
                      key={index}
                      title={permission}
                      color='#052C65'
                      placement='top'
                    >
                      <span
                        key={index}
                        className='bg-[#052C65] text-white py-1 px-3 rounded-lg text-sm overflow-hidden overflow-ellipsis text-center'
                      >
                        {permission}
                      </span>
                    </Tooltip>
                  ))
                ) : (
                  <span className='bg-[#052C65] text-white py-1 px-3 rounded-lg text-sm overflow-hidden overflow-ellipsis'>
                    {permissions}
                  </span>
                )}
              </div>
            </li>
          </ul>
          <div className='flex justify-center mt-5 gap-2'>
            <Button
              type='primary'
              className='bg-gradient-to-l from-[#052C65] to-[#065A9D] hover:from-[#052C65] hover:to-[#065A9D] text-white flex items-center'
              onClick={handleLogout}
            >
              <MdOutlineLogin size={20} />
              <span className='ml-2'>Đăng Xuất</span>
            </Button>
            <Button
              type='primary'
              className='bg-gradient-to-r from-[#052C65] to-[#065A9D] hover:from-[#052C65] hover:to-[#065A9D] text-white flex items-center'
              onClick={() => {
                navigate("/module-switch");
              }}
            >
              <GoArrowSwitch size={20} />
              <span className='ml-2'>Chuyển Mô-Đun</span>
            </Button>
            <Button
              type='primary'
              className='bg-gradient-to-r from-[#052C65] to-[#065A9D] hover:from-[#052C65] hover:to-[#065A9D] text-white flex items-center'
              onClick={() => dispatch(openModalPersonalInfo())}
            >
              <BsFilePersonFill size={20} />
              <span className='ml-2'>Thông tin tài khoản</span>
            </Button>
          </div>
        </div>
      );
    };
  }, [
    dispatch,
    handleLogout,
    navigate,
    permissions,
    userInfo?.campusName,
    userInfo?.email,
    userInfo?.fullName,
    userInfo?.userCode,
  ]);

  const accessibleItems = useMemo(() => {
    const items = [
      getItem(
        <Link to='/management/manage-campus'>
          <FaCity style={buttonNavigatorStyle} />
          <span style={textNavigatorStyle}>Quản lý cơ sở</span>
        </Link>,
        "/management/manage-campus",
        [PERMISSIONS.BAN_DAO_TAO_HO],
        "Quản lý cơ sở"
      ),
      getItem(
        <Link to='/management/manage-semester'>
          <SiStudyverse style={buttonNavigatorStyle} />
          <span style={textNavigatorStyle}>Quản lý học kỳ</span>
        </Link>,
        "/management/manage-semester",
        [PERMISSIONS.BAN_DAO_TAO_HO],
        "Quản lý học kỳ"
      ),
      getItem(
        <Link to='/management/manage-department'>
          <ApartmentOutlined style={buttonNavigatorStyle} />
          <span style={textNavigatorStyle}>Quản lý bộ môn</span>
        </Link>,
        "/management/manage-department",
        [PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO, PERMISSIONS.BAN_DAO_TAO_HO],
        "Quản lý bộ môn"
      ),
      getItem(
        <Link to='/management/manage-staff'>
          <FaRegUser style={buttonNavigatorStyle} />
          <span style={textNavigatorStyle}>Quản lý nhân viên</span>
        </Link>,
        "/management/manage-staff",
        [
          PERMISSIONS.CHU_NHIEM_BO_MON,
          PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO,
          PERMISSIONS.QUAN_LY_DAO_TAO,
        ],
        "Quản lý nhân viên"
      ),
      getItem(
        <Link to='/management/manage-student'>
          <PiStudentBold style={buttonNavigatorStyle} />
          <span style={textNavigatorStyle}>Quản lý sinh viên</span>
        </Link>,
        "/management/manage-student",
        [PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO, PERMISSIONS.QUAN_LY_DAO_TAO],
        "Quản lý sinh viên"
      ),
      getItem(
        <Link to='/management/manage-module'>
          <RiSoundModuleFill style={buttonNavigatorStyle} />
          <span style={textNavigatorStyle}>Quản lý mô-đun</span>
        </Link>,
        "/management/manage-module",
        [PERMISSIONS.ADMIN, PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO],
        "Quản lý mô-đun"
      ),
      getItem(
        <Link to='/management/manage-role'>
          <ProjectOutlined style={buttonNavigatorStyle} />
          <span style={textNavigatorStyle}>Quản lý vai trò</span>
        </Link>,
        "/management/manage-role",
        [PERMISSIONS.ADMIN],
        "Quản lý vai trò"
      ),
    ];

    return items.filter((item) => permissionVerify(item.permissions || []));
  }, []);

  const handleToggle = useCallback((collapsedStatus: boolean) => {
    setCollapsed(collapsedStatus);
  }, []);

  const handleNavigate = useCallback(
    (path: string) => {
      navigate(path);
    },
    [navigate]
  );

  return {
    collapsed,
    loading,
    setLoading,
    userInfo,
    permissions,
    dispatch,
    navigate,
    location,
    handleLogout,
    content,
    accessibleItems,
    handleToggle,
    handleNavigate,
  };
};
