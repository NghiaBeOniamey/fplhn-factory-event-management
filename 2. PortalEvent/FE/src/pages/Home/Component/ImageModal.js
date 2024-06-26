// ModalImage.jsx
import React, {useEffect} from 'react';
import styled from "styled-components"; // (1)

const ImageModal = ({ imageUrl, onClose }) => {


    return (
        <ImageModalWrapper>
            <ImageModalContent>
                <ImageModalCloseButton onClick={onClose}></ImageModalCloseButton>
                <img src={imageUrl} alt="Modal" />
            </ImageModalContent>
        </ImageModalWrapper>
    );
}

export default ImageModal;

/* ModalImage.css */
const ImageModalWrapper = styled.section`
    display: none;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgb(0,0,0);
    background-color: rgba(0,0,0,0.9);
    padding-top: 60px;
`

const ImageModalContent = styled.div`
    margin: auto;
    display: block;
    max-width: 700px;
    max-height: 500px;
    overflow: auto;
    background-color: #fefefe;
    border-radius: 8px;
    padding: 20px;
    position: relative;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
`

const ImageModalCloseButton = styled.span`
    color: #aaa;
    position: absolute;
    top: 10px;
    right: 15px;
    font-size: 30px;
    cursor: pointer;
  
  	&:hover, &:focus {
      color: black;
      text-decoration: none;
      cursor: pointer;
    }
`