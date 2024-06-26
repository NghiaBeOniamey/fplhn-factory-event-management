import React from "react";
import styled from "styled-components";

export default function FullButton({ title, action, bgColor, colors, hoverBgColor, hoverColors}) {
  return (
    <Wrapper
      className="animate pointer radius8"
      onClick={action ? () => action() : null}
      bgColor={bgColor}
      colors={colors}
      hoverBgColor={hoverBgColor}
      hoverColors={hoverColors}
    >
      {title}
    </Wrapper>
  );
}

const Wrapper = styled.button`
  border: 1px solid ${(props) => (props.bgColor ? props.bgColor : "#0563bb")};
  background-color: ${(props) => (props.bgColor ? props.bgColor : "#0563bb")};
  width: 100%;
  padding: 15px;
  outline: none;
  font-family: Nunito,serif;
  font-size: large;
  font-weight: bold;
  color: ${(props) => (props.colors ? props.colors : "#fff")};
  :hover {
    background-color: ${(props) => (props.hoverBgColor ? props.hoverBgColor : "#0563bb")};
    border: 1px solid ${(props) => (props.hoverBgColor ? props.hoverBgColor : "#0563bb")};
    color: ${(props) => (props.hoverColors ? props.hoverColors : "#fff")};
  }
`;

