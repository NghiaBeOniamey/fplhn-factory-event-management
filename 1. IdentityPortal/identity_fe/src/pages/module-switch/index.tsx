import logoFPT from "#assets/image/Logo_FPT.png";
import logoUDPM from "#assets/image/logo-udpm-dark.png";
import moduleImg from "#assets/image/moduleImg.png";
import HashLoading from "#components/ui/HashLoading";
import IdentityFooter from "#components/ui/IdentityFooter";
import { Button, Col, Row } from "antd";
import { IoLogOut } from "react-icons/io5";
import { useSwitchModule } from "./hook/useSwitchModule";
import "./index.css";

export default function ModuleSwitch() {
  const {
    loading,
    handleLogout,
    handleModuleSwitch,
    moduleAvailableResponses,
    isRedirect,
  } = useSwitchModule();

  return (
    <>
      {(loading || isRedirect) && <HashLoading />}
      <div className='background-permission'>
        <div
          style={{
            display: "flex",
            height: "calc(100vh - 64px)",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
            padding: "20px",
          }}
        >
          <Row>
            <Col>
              <img src={logoFPT} width={200} alt='logo Fpt Polytechnic' />
            </Col>
            <Col>
              <img src={logoUDPM} width={200} alt='logo xưởng thực hành' />
            </Col>
          </Row>
          <div
            style={{
              position: "fixed",
              right: "20px",
              top: "20px",
            }}
          >
            <Button
              type='primary'
              size='large'
              onClick={handleLogout}
              style={{
                display: "flex",
                flexDirection: "row",
                alignItems: "center",
                justifyContent: "center",
                fontWeight: "bold",
                color: "white",
                backgroundColor: "#f5222d",
              }}
            >
              <IoLogOut />
              <span style={{ marginLeft: "10px" }}>Đăng Xuất</span>
            </Button>
          </div>
          {moduleAvailableResponses?.length > 0 && (
            <Row style={{ marginTop: "60px" }}>
              <h1
                style={{
                  fontFamily: "Nunito",
                  fontWeight: "bolder",
                  fontSize: "30px",
                }}
              >
                Thay Đổi Module Truy Cập
              </h1>
            </Row>
          )}
          <Row style={{ marginTop: "30px" }}>
            <Col>
              <div
                style={{
                  display: "flex",
                  flexDirection: "row",
                  margin: "20px",
                  alignItems: "center",
                  justifyContent: "center",
                }}
              >
                {moduleAvailableResponses?.length > 0 ? (
                  moduleAvailableResponses.map((module: any) => (
                    <div
                      key={module.moduleCode}
                      style={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        justifyContent: "center",
                        margin: "60px",
                      }}
                    >
                      <img
                        src={moduleImg}
                        width={200}
                        alt='module'
                        className='mb-4 module-image'
                        onClick={() => {
                          handleModuleSwitch(module.moduleCode);
                        }}
                      />
                      <Button
                        style={{
                          fontWeight: "bold",
                        }}
                        type='primary'
                        size='large'
                        onClick={() => {
                          handleModuleSwitch(module.moduleCode);
                        }}
                      >
                        {module.moduleName}
                      </Button>
                    </div>
                  ))
                ) : (
                  <div
                    style={{
                      display: "flex",
                      flexDirection: "column",
                      alignItems: "center",
                      justifyContent: "center",
                      margin: "40px",
                      marginBottom: "100px",
                      backgroundColor: "#f8f9fa",
                      padding: "20px",
                      borderRadius: "10px",
                      boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.1)",
                      transition: "all 0.3s ease",
                    }}
                  >
                    <h3 style={{ textAlign: "center", color: "#333" }}>
                      Bạn Không Có Quyền Truy Cập Module Nào !
                    </h3>
                  </div>
                )}
              </div>
            </Col>
          </Row>
        </div>
      </div>
      <IdentityFooter />
    </>
  );
}
