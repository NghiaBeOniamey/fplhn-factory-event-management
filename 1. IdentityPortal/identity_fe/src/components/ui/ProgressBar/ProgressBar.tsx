import { CSSProperties } from "react";
import "./ProgressBar.css";

interface ProgressBarProps {
  speed?: number;
  className?: string;
  style?: CSSProperties;
}

const ProgressBar = ({ speed = 5, className, style }: ProgressBarProps) => {
  return (
    <div
      id='loading-bar'
      className={className}
      style={{
        animationDuration: `${speed}s`,
        ...style,
      }}
    />
  );
};

export default ProgressBar;
