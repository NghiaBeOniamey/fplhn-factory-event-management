import React, {useEffect, useRef} from "react";
import styled from "styled-components";
// Components
import FullButton from "../Buttons/FullButton";
// Assets
import Dots from "../../../assets/svg/Dots";
import Background from '../../../assets/img/background.jpg';
import Typed from 'typed.js';
import {Link} from "react-scroll";

export default function Header({showDetail, event, isNoEvent, isLecturer}) {
    const typedTextRef = useRef(null);

    useEffect(() => {
        let options = {};
        if (isNoEvent) {
            options = {
                strings: ['Cùng đón chờ những sự kiện hấp dẫn, bổ ích sắp diễn ra nhé!!!'],
                typeSpeed: 100,
                loop: true
            }
        } else {
            options = {
                strings: event.listOrganizerAccount,
                typeSpeed: 100,
                loop: true
            };
        }

        const typed = new Typed(typedTextRef.current, options);

        return () => {
            typed.destroy();
        };
    }, []);

    const scrollToCalendar = () => {
        const element = document.getElementById('calendar');
        if (element) {
            element.scrollIntoView({behavior: 'smooth'});
        }
    }

    return (
        <>
            {(!isNoEvent || !isLecturer) &&
                <Wrapper id="header">
                    <WrapperHeader className="flexSpaceCenter event-incoming">
                        <LeftSide className="flexCenter" data-aos="fade-right">
                            <div>
                                <h1 className="extraBold font40 darkColor">{event.name}</h1>
                                <h1 className="font25 redColor">{event.time}</h1>
                                <OrganizerName className="font18 semiBold darkColor" ref={typedTextRef}/>{/*Tên GV tham gia*/}
                                <BtnWrapper>
                                    <FullButton action={() => showDetail(event.id)} bgColor="#fdb800" colors="#ffffff" hoverBgColor="#f6cc75" title="Xem chi tiết"/>
                                </BtnWrapper>
                            </div>
                        </LeftSide>
                        <RightSide data-aos="zoom-in">
                            <ImageWrapper>
                                <Img className="radius8 header-image" src={event.banner ? event.banner : Background} alt="office" style={{zIndex: 8}}/>
                                <DotsWrapper>
                                    <Dots/>
                                </DotsWrapper>
                            </ImageWrapper>
                        </RightSide>
                    </WrapperHeader>
                </Wrapper>
            }
            {(isNoEvent || isLecturer) &&
                <Wrapper id="header">
                    <WrapperHeader className="flexSpaceCenter event-incoming">
                        <LeftSide className="flexCenter" data-aos="fade-right">
                            <div>
                                <h1 className="extraBold font40 darkColor">Cổng thông tin sự kiện </h1>
                                <h1 className="extraBold font40 darkColor">Bộ môn Ứng dụng phần mềm</h1>
                                <OrganizerName className="font18 semiBold darkColor" ref={typedTextRef}/>{/*Tên GV tham gia*/}
                                <BtnWrapper>
                                    <FullButton action={scrollToCalendar} bgColor="#fdb800" colors="#ffffff" hoverBgColor="#f6cc75" title="Lịch sự kiện">
                                    </FullButton>
                                </BtnWrapper>
                            </div>
                        </LeftSide>
                        <RightSide data-aos="zoom-in">
                            <ImageWrapper>
                                <Img className="radius8 header-image" src={Background} alt="office" style={{zIndex: 8}}/>
                                {/* <QuoteWrapper className="flexCenter darkBg radius8">
                        <QuotesWrapper>
                          <QuotesIcon />
                        </QuotesWrapper>
                        <div>
                          <p className="font15 whiteColor">
                            <em>Friends, such as we desire, are dreams and fables. Friendship demands the ability to do without it.</em>
                          </p>
                          <p className="font13 orangeColor textRight" style={{marginTop: '10px'}}>Ralph Waldo Emerson</p>
                        </div>
                      </QuoteWrapper> */}
                                <DotsWrapper>
                                    <Dots/>
                                </DotsWrapper>
                            </ImageWrapper>
                        </RightSide>
                    </WrapperHeader>
                </Wrapper>
            }
        </>
    );
}

const Wrapper = styled.div`
  background-color: #FFF8C9;
`;
const WrapperHeader = styled.section`
  width: 90%;
  //padding-top: 20vh;
  margin-left: auto;
  height: 100vh;
  max-height: 100vh;
  //background-color: #F5F5F5;
  @media (max-width: 960px) {
    flex-direction: column;
  }
`;
const LeftSide = styled.div`
  justify-content: center;
  display: flex;
  width: 40%;
  height: 100%;
  color: white;
  @media (max-width: 960px) {
    width: 100%;
    order: 2;
    margin: 50px 0;
    text-align: center;
  }
  @media (max-width: 560px) {
    margin: 80px 0 50px 0;
  }
`;
const RightSide = styled.div`
  width: 60%;
  padding-top: 40px;
  height: 100%;
  padding-right: 50px;
  justify-content: center;
  display: flex;
  align-items: center;
  @media (max-width: 960px) {
    width: 100%;
    order: 1;
    margin-top: 30px;
  }
`;
const OrganizerName = styled.div`
  max-width: 470px;
  height: 30px;
  line-height: 1.5rem;
  @media (max-width: 960px) {
    padding: 15px 0 50px 0;
    text-align: center;
    max-width: 100%;
  }
`;
const BtnWrapper = styled.div`
  margin-top: 50px;
  max-width: 190px;
  @media (max-width: 960px) {
    margin: 0 auto;
  }
`;
const ImageWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  position: relative;
  right: 10px;
  z-index: 9;
  @media (max-width: 960px) {
    width: 100%;
    justify-content: center;
  }
`;
const Img = styled.img`
  width: 640px;
  height: 360px;
  @media (max-width: 960px) {
    width: 100%;
    height: auto;
  }
`;
const DotsWrapper = styled.div`
  position: absolute;
  right: -80px;
  bottom: 100px;
  z-index: 2;
  @media (max-width: 960px) {
    right: 100px;
  }
  @media (max-width: 560px) {
    display: none;
  }
`;

// const QuoteWrapper = styled.div`
//   position: absolute;
//   left: 0;
//   bottom: 50px;
//   max-width: 330px;
//   padding: 30px;
//   z-index: 99;
//   @media (max-width: 960px) {
//     left: 20px;
//   }
//   @media (max-width: 560px) {
//     bottom: -50px;
//   }
// `;
// const QuotesWrapper = styled.div`
//   position: absolute;
//   left: -20px;
//   top: -10px;
// `;