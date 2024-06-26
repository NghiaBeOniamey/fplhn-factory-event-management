// prop-types is a library for typechecking of props
import PropTypes from "prop-types";

// @mui material components
import Link from "@mui/material/Link";

// Argon Dashboard 2 MUI components
import ArgonBox from "../../components/ArgonBox";
import ArgonTypography from "../../components/ArgonTypography";

// Argon Dashboard 2 MUI base styles
import typography from "../../assets/theme/base/typography";

import logo from "../../assets/img/logo-udpm-dark.png";

function Footer({ company, links }) {
  const { size } = typography;

  const renderLinks = () =>
    links.map((link) => (
      <ArgonBox key={link.name} component="li" px={2} lineHeight={1}>
        <Link href={link.href} target="_blank">
          <ArgonTypography variant="button" fontWeight="regular" color="text">
            {link.name}
          </ArgonTypography>
        </Link>
      </ArgonBox>
    ));

  return (
    <>
      <footer
        style={{
          padding: '40px 0 0 0',
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          zIndex: 1000,
          fontSize: "1.3rem",
          color: "#172b4d !important",
          height: "70px",
          textAlign: "center",
        }}
      >
        <p>
          Powered by <span style={{ fontWeight: "bold", color: "#172b4d !important", }} className="MuiTypography-root MuiTypography-button">FPLHN-UDPM</span>
        </p>
        <img
          src={logo}
          alt="logo"
          style={{
            width: "100%",
            height: "auto",
            maxWidth: "100px",
          }}
        />
      </footer>
    </>
    // <ArgonBox
    //   width="100%"
    //   display="flex"
    //   flexDirection={{ xs: "column", lg: "row" }}
    //   justifyContent="space-between"
    //   alignItems="center"
    //   px={1.5}
    // >
    //   <ArgonBox
    //     display="flex"
    //     justifyContent="center"
    //     alignItems="center"
    //     flexWrap="wrap"
    //     color="text"
    //     fontSize={size.sm}
    //     px={1.5}
    //   >
    //       Copyright &copy; 2023,
    //     <ArgonBox fontSize={size.md} color="text" mb={-0.5} mx={0.25} />
    //     <ArgonTypography variant="button" fontWeight="medium">
    //       &nbsp;{company.name}&nbsp;
    //     </ArgonTypography>
    //   </ArgonBox>
    //   <ArgonBox
    //     component="ul"
    //     sx={({ breakpoints }) => ({
    //       display: "flex",
    //       flexWrap: "wrap",
    //       alignItems: "center",
    //       justifyContent: "center",
    //       listStyle: "none",
    //       mt: 3,
    //       mb: 0,
    //       p: 0,

    //       [breakpoints.up("lg")]: {
    //         mt: 0,
    //       },
    //     })}
    //   >
    //     {renderLinks()}
    //   </ArgonBox>
    // </ArgonBox>
  );
}

// Setting default values for the props of Footer
Footer.defaultProps = {
  company: "BIT Ha Noi",
  links: [
    // { href: "https://www.creative-tim.com/", name: "Facebook" },
    // { href: "https://www.creative-tim.com/presentation", name: "Youtube" },
  ],
};

// Typechecking props for the Footer
Footer.propTypes = {
  company: PropTypes.objectOf(PropTypes.string),
  links: PropTypes.arrayOf(PropTypes.object),
};

export default Footer;
