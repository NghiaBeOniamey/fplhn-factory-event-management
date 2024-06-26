import React from "react";
import styled from "styled-components";
import { Link } from "react-scroll";
import Logo from "../../../assets/img/logo-udpm-dark.png"
// Assets
import CloseIcon from "../../../assets/svg/CloseIcon";
import LogoIcon from "../../../assets/svg/Logo";

export default function Sidebar({ sidebarOpen, toggleSidebar, isDetail }) {
  return (
    <Wrapper className="animate" sidebarOpen={sidebarOpen}>
      <SidebarHeader className="flexSpaceCenter">
        <div className="flexNullCenter">
          <img src={Logo} alt="" width={"50%"}/>
          <h1 className="darkColor font20" style={{ marginLeft: "15px" }}>
            Portal Event
          </h1>
        </div>
        <CloseBtn onClick={() => toggleSidebar(!sidebarOpen)} className="animate pointer">
          <CloseIcon />
        </CloseBtn>
      </SidebarHeader>

      <UlStyle className="flexNullCenter flexColumn">
        <li className="semiBold font15 pointer">
          <Link
            onClick={() => toggleSidebar(!sidebarOpen)}
            activeClass="active"
            className="darkColor"
            style={{ padding: "10px 15px" }}
            to="header"
            spy={true}
            smooth={true}
            offset={-60}
          >
            Home
          </Link>
        </li>
        <li className="semiBold font15 pointer">
          <Link
            onClick={() => toggleSidebar(!sidebarOpen)}
            activeClass="active"
            className="darkColor"
            style={{ padding: "10px 15px" }}
            to="calendar"
            spy={true}
            smooth={true}
            offset={-60}
          >
            Lịch sự kiện
          </Link>
        </li>
        {/* {isDetail && (
            <>
              <li className="semiBold font15 pointer">
                <Link
                  onClick={() => toggleSidebar(!sidebarOpen)}
                  activeClass="active"
                  className="darkColor"
                  style={{ padding: "10px 15px" }}
                  to="resume"
                  spy={true}
                  smooth={true}
                  offset={-60}
                >
                  Chi tiết sự kiện
                </Link>
              </li>
              <li className="semiBold font15 pointer">
                <Link
                    onClick={() => toggleSidebar(!sidebarOpen)}
                    activeClass="active"
                    className="darkColor"
                    style={{ padding: "10px 15px" }}
                    to="comment"
                    spy={true}
                    smooth={true}
                    offset={-60}
                >
                  Comment
                </Link>
              </li>
            </>
        )} */}
      {/*  <li className="semiBold font15 pointer">*/}
      {/*    <Link*/}
      {/*      onClick={() => toggleSidebar(!sidebarOpen)}*/}
      {/*      activeClass="active"*/}
      {/*      className="whiteColor"*/}
      {/*      style={{ padding: "10px 15px" }}*/}
      {/*      to="blog"*/}
      {/*      spy={true}*/}
      {/*      smooth={true}*/}
      {/*      offset={-60}*/}
      {/*    >*/}
      {/*      Blog*/}
      {/*    </Link>*/}
      {/*  </li>*/}
      {/*  <li className="semiBold font15 pointer">*/}
      {/*    <Link*/}
      {/*      onClick={() => toggleSidebar(!sidebarOpen)}*/}
      {/*      activeClass="active"*/}
      {/*      className="whiteColor"*/}
      {/*      style={{ padding: "10px 15px" }}*/}
      {/*      to="pricing"*/}
      {/*      spy={true}*/}
      {/*      smooth={true}*/}
      {/*      offset={-60}*/}
      {/*    >*/}
      {/*      Pricing*/}
      {/*    </Link>*/}
      {/*  </li>*/}
      {/*  <li className="semiBold font15 pointer">*/}
      {/*    <Link*/}
      {/*      onClick={() => toggleSidebar(!sidebarOpen)}*/}
      {/*      activeClass="active"*/}
      {/*      className="whiteColor"*/}
      {/*      style={{ padding: "10px 15px" }}*/}
      {/*      to="contact"*/}
      {/*      spy={true}*/}
      {/*      smooth={true}*/}
      {/*      offset={-60}*/}
      {/*    >*/}
      {/*      Contact*/}
      {/*    </Link>*/}
      {/*  </li>*/}
      {/*</UlStyle>*/}
      {/*<UlStyle className="flexSpaceCenter">*/}
      {/*  <li className="semiBold font15 pointer">*/}
      {/*    <a href="/" style={{ padding: "10px 30px 10px 0" }} className="whiteColor">*/}
      {/*      Log in*/}
      {/*    </a>*/}
      {/*  </li>*/}
      {/*  <li className="semiBold font15 pointer flexCenter">*/}
      {/*    <a href="/" className="radius8 lightBg" style={{ padding: "10px 15px" }}>*/}
      {/*      Get Started*/}
      {/*    </a>*/}
      {/*  </li>*/}
      </UlStyle>
    </Wrapper>
  );
}

const Wrapper = styled.nav`
  background-color: #FCE3AD;
  width: 400px;
  height: 100vh;
  position: fixed;
  top: 0;
  padding: 0 30px;
  right: ${(props) => (props.sidebarOpen ? "0px" : "-500px")};
  z-index: 9999;
  @media (max-width: 400px) {
    width: 100%;
  }
`;
const SidebarHeader = styled.div`
  padding: 20px 0;
`;
const CloseBtn = styled.button`
  border: 0px;
  outline: none;
  background-color: transparent;
  padding: 10px;
`;
const UlStyle = styled.ul`
  padding: 40px;
  li {
    margin: 20px 0;
  }
`;
