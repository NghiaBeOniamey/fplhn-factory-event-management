import React from "react";
import styled from "styled-components";
import { Link } from "react-scroll";
// Assets
import LogoImg from "../../../assets/svg/Logo";
import LogoUdpm from "../../../assets/img/logo-udpm-dark.png";
import LogoBIT from "../../../assets/img/BIT.png";
import LogoFPoly from "../../../assets/img/Logo_FPoly.png";
import {Col, Row} from "antd";

export default function Contact() {

  const getCurrentYear = () => {
    return new Date().getFullYear();
  }

  return (
    <Wrapper>
      <Row>
        <InnerWrapper className="flexSpaceCenter" style={{ padding: "30px 0" }}>
          <Col span={16}>
            <Logo src={LogoFPoly} width="15%" alt={"Logo"}></Logo>
            <Logo src={LogoUdpm} width="15%" alt={"Logo"}></Logo>
            <Logo src={LogoBIT} width="6%" alt={"Logo"}></Logo>
          </Col>
          <Col span={8}>
            <h1 className="darkColor" style={{ fontFamily: "nunito", fontSize: "40px", marginLeft: "15px" }}>
              Portal Event
            </h1>
            <StyleP className="darkColor font15">
              © 2023 - <b className="darkColor font13">BIT Hà Nội</b> All Right Reserved
            </StyleP>
          </Col>
        </InnerWrapper>
      </Row>
    </Wrapper>
  );
}

const Wrapper = styled.div`
  width: 100%;
  background-color: #FCE3AD;
  text-align: center;
  max-height: 200px;
`;
const Logo = styled.img`
  margin-left: 60px;
`
const InnerWrapper = styled.div`
  @media (max-width: 550px) {
    flex-direction: column;
  }
`;
const StyleP = styled.p`
  font-family: Nunito,serif;
  @media (max-width: 550px) {
    margin: 20px 0;
  }
`;