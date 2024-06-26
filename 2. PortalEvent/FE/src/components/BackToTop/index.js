import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { ArrowUpOutlined } from '@ant-design/icons';
const BackToTopButton = () => {
    const [isVisible, setIsVisible] = useState(false);

    // Hiển thị nút khi người dùng cuộn xuống dưới 100px
    const toggleVisibility = () => {
        if (window.scrollY > 100) {
            setIsVisible(true);
        } else {
            setIsVisible(false);
        }
    };

    // Sự kiện cuộn trang
    useEffect(() => {
        window.addEventListener("scroll", toggleVisibility);
        return () => {
            window.removeEventListener("scroll", toggleVisibility);
        };
    }, []);

    // Cuộn về đầu trang
    const scrollToTop = () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth",
        });
    };

    return (
        <>
            {isVisible && (
                <BackToTopButtonStyled onClick={scrollToTop}>
                    <ArrowUpOutlined />
                </BackToTopButtonStyled>
            )}
        </>
    );
};

const BackToTopButtonStyled = styled.button`
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 10px 15px;
  background-color: #95b6d4;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  width: 40px;
  height: 40px;
  border-radius: 50px;
  
  i {
    /* Thêm icon cho nút "Back to Top" (có thể sử dụng FontAwesome hoặc các icon library khác) */
    font-size: 20px;
  }

  &:hover {
    background: #0678e3;
    color: #fff;
  }
`;

export default BackToTopButton;
