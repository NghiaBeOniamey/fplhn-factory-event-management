import ModalPersonalInfo from "#components/ModalPersonalInfo/ModalPersonalInfor";
import IdentityFooter from "#components/ui/IdentityFooter";
import MagnifyingGlassLoading from "#components/ui/MagnifyingGlassLoading";
import { closeModalPersonalInfo } from "#context/redux/slice/ModalSlice";
import { RootState } from "#context/redux/store";
import { useListenCurrentUserChange } from "#hooks/useListenCurrentUserChange";
import { useDefineDashboard } from "#layout/useDefineDashboard";
import { MenuFoldOutlined, MenuUnfoldOutlined } from "@ant-design/icons";
import { Button, Layout, Menu, Popover, theme } from "antd";
import { Footer } from "antd/es/layout/layout";
import { memo } from "react";
import { GrMoreVertical } from "react-icons/gr";
import { useDispatch, useSelector } from "react-redux";
import logoUDPM from "../assets/image/logo-udpm.png";

const { Header, Sider, Content } = Layout;

const Dashboard = ({ children }: { children: React.ReactNode }) => {
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  const {
    collapsed,
    handleToggle,
    userInfo,
    loading,
    accessibleItems,
    location,
    handleNavigate,
    content,
  } = useDefineDashboard();

  const dispatch = useDispatch();

  const { isOpen } = useSelector(
    (state: RootState) => state?.modal?.modalPersonalInfo
  );

  const onClosePersonalInfo = () => dispatch(closeModalPersonalInfo());

  useListenCurrentUserChange();

  return (
    <>
      {loading && <MagnifyingGlassLoading />}
      {isOpen && <ModalPersonalInfo {...{ isOpen, onClosePersonalInfo }} />}
      <Layout className='min-h-[100vh]'>
        <Sider
          trigger={null}
          collapsible
          collapsed={collapsed}
          className='overflow-auto h-[100vh] fixed left-0 top-0 bottom-0 items-center justify-center'
          width={210}
        >
          <div
            className={`w-full flex flex-col items-center justify-center ${
              collapsed ? "p-3" : "p-0"
            }`}
          >
            {!collapsed && (
              <div
                className='flex flex-col justify-center items-center p-1 mt-3 mb-3 cursor-pointer'
                onClick={() => handleNavigate("/management/welcome-dashboard")}
              >
                <img
                  src={logoUDPM}
                  alt='logo-udpm'
                  className='my-4 p-[10px] w-[80%] bg-transparent'
                />
                <h1 className='text-2xl font-medium text-center text-white'>
                  Portal Identity
                </h1>
              </div>
            )}
          </div>
          <Menu
            selectedKeys={accessibleItems.map((item) =>
              location.pathname.startsWith(item.key) ? item.key : ""
            )}
            mode='inline'
            theme='dark'
            items={accessibleItems}
          />
        </Sider>
        <Layout
          className={`${
            collapsed ? "transition-all duration-300 ml-[80px]" : "ml-[200px]"
          }`}
        >
          <Header
            style={{ background: colorBgContainer }}
            className='p-0 flex justify-between'
          >
            <Button
              type='text'
              icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
              onClick={() => handleToggle(!collapsed)}
              className='w-[64px] h-[64px] font-[16px]'
            />
            <div className='flex items-center pr-[20px]'>
              <div className='h-12 shadow-sm bg-slate-200 rounded-lg flex items-center px-4'>
                <img
                  src={userInfo?.pictureUrl}
                  alt='avatar'
                  className='rounded-[50%] w-[35px] h-[35px] object-cover mr-2'
                />
                <div>Xin Chào, {userInfo?.fullName}</div>
                <Popover
                  placement='bottomLeft'
                  autoAdjustOverflow
                  zIndex={1000}
                  title={() => (
                    <p className='text-base text-center'>Thông Tin Đăng Nhập</p>
                  )}
                  trigger={["click"]}
                  content={content()}
                >
                  <Button
                    type='text'
                    icon={
                      <GrMoreVertical
                        className='font-[20px] text-black'
                        size={20}
                      />
                    }
                    className='ml-2'
                  />
                </Popover>
              </div>
            </div>
          </Header>
          <Content
            style={{
              background: colorBgContainer,
              borderRadius: borderRadiusLG,
            }}
            className='min-h-100vh mx-[18px] my-[24px] p-[28px]'
          >
            <SmoothScroll>{children}</SmoothScroll>
          </Content>
          <Footer className='p-1'>
            <IdentityFooter />
          </Footer>
        </Layout>
      </Layout>
    </>
  );
};

export default memo(Dashboard, (prevProps, nextProps) => {
  return prevProps.children === nextProps.children;
});

const SmoothScroll = ({ children }: React.PropsWithChildren<{}>) => {
  return (
    <div className='scroll-smooth md:scroll-auto font-sans'>{children}</div>
  );
};
