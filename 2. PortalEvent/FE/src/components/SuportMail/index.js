import React, { useState } from "react";
import styled from "styled-components";
import { WechatOutlined } from '@ant-design/icons';
import ModalSendSuport from "./ModalSendSuport";

const SuportMailButton = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  }

  const closeModal = () => {
    setIsModalOpen(false);
  }

  return (
    <>
      <SuportMailButtonStyled onClick={openModal}>
        <WechatOutlined />
        Hỗ trợ kỹ thuật
      </SuportMailButtonStyled>
      {isModalOpen && (
        <ModalSendSuport  isOpen={isModalOpen} onClose={closeModal} />
      )}
    </>
  );
};

const SuportMailButtonStyled = styled.button`
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 10px 15px;
  background-color: #95b6d4;
  color: #fff;
  border: none;
  border-radius: 2px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  border-radius: 20px;
  z-index: 9999999999999999999999999999;
  
  span {
    font-size: 24px;
    padding-right: 8px;
  }

  &:hover {
    background: #0678e3;
    color: #fff;
  }
`;

export default SuportMailButton;
