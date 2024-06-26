import logo from "#assets/image/logo-udpm-dark.png";
import { memo } from "react";

export default memo(function IdentityFooter({ style }: { style?: any }) {
  return (
    <footer
      style={{
        ...style,
        bottom: 0,
        width: "100%",
        height: "auto",
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
        flexWrap: "wrap",
        zIndex: 1000,
        padding: "10px 0",
        fontSize: "1.3rem",
        color: "#172b4d !important",
      }}
    >
      <div>
        Powered by <strong>FPLHN-UDPM</strong>
      </div>
      <img
        src={logo}
        alt='logo'
        style={{
          width: "100%",
          height: "auto",
          maxWidth: "100px",
        }}
      />
    </footer>
  );
});
