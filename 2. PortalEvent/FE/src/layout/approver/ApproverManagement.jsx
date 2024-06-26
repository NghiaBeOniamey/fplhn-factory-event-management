import React, { useEffect, useState } from 'react';
import Sidenav from '../../section/Sidenav';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { setMiniSidenav, setOpenConfigurator, useArgonController } from '../../context';
import { useLocation } from 'react-router-dom';
import theme from "../../assets/theme";
import themeDark from "../../assets/theme-dark";
import SettingsIcon from '@mui/icons-material/Settings';
import ArgonBox from '../../components/ArgonBox';
import Configurator from '../../section/Configurator';
import ApproverRoutes from "./ApproverRoutes";
import "../../assets/css/nucleo-icons.css";
import "../../assets/css/nucleo-svg.css";
import DashboardLayout from '../../section/LayoutContainers/DashboardLayout';
import DashboardNavbar from '../../section/Navbars/DashboardNavbar';
import Footer from '../../section/Footer';
//Logo SideNav
import brand from "../../assets/img/logo-udpm.png";
import brandDark from "../../assets/img/logo-udpm-dark.png";

const ApproverManagement = ({ children, routeName, urlRole }) => {
    const producer = { name: "BIT Ha Noi" };
    const [controller, dispatch] = useArgonController();
    const { miniSidenav, direction, layout, openConfigurator, sidenavColor, darkSidenav, darkMode } =
        controller;
    const [onMouseEnter, setOnMouseEnter] = useState(false);
    const { pathname } = useLocation();

    // Open sidenav when mouse enter on mini sidenav
    const handleOnMouseEnter = () => {
        if (miniSidenav && !onMouseEnter) {
            setMiniSidenav(dispatch, false);
            setOnMouseEnter(true);
        }
    };

    // Close sidenav when mouse leave mini sidenav
    const handleOnMouseLeave = () => {
        if (onMouseEnter) {
            setMiniSidenav(dispatch, true);
            setOnMouseEnter(false);
        }
    };

    // Change the openConfigurator state
    const handleConfiguratorOpen = () => setOpenConfigurator(dispatch, !openConfigurator);

    // Setting the dir attribute for the body element
    useEffect(() => {
        document.body.setAttribute("dir", direction);
    }, [direction]);

    // Setting page scroll to 0 when changing the route
    useEffect(() => {
        document.documentElement.scrollTop = 0;
        document.scrollingElement.scrollTop = 0;
        document.title = routeName + ' | Portal Event'
    }, [pathname]);

    const configsButton = (
        <ArgonBox
            display="flex"
            justifyContent="center"
            alignItems="center"
            width="3.5rem"
            height="3.5rem"
            bgColor="white"
            shadow="sm"
            borderRadius="50%"
            position="fixed"
            right="2rem"
            bottom="5rem"
            zIndex={99}
            color="dark"
            sx={{ cursor: "pointer" }}
            onClick={handleConfiguratorOpen}
        >
            <SettingsIcon fontSize="default" color="inherit">
                settings
            </SettingsIcon>
        </ArgonBox>
    );

    return (
        <ThemeProvider theme={darkMode ? themeDark : theme}>
            <CssBaseline />
            {layout === "dashboard" && (
                <>
                    <Sidenav
                        roleUrl={urlRole}
                        color={sidenavColor}
                        brand={darkSidenav || darkMode ? brand : brandDark}
                        brandName="Portal Event"
                        routes={ApproverRoutes}
                        onMouseEnter={handleOnMouseEnter}
                        onMouseLeave={handleOnMouseLeave}
                    />
                    <Configurator />
                    {configsButton}
                </>
            )}
            {layout === "vr" && <Configurator />}

            <DashboardLayout>
                <DashboardNavbar darkMode={darkMode} urlRole={urlRole} routeName={routeName} />
                <div style={{
                    minHeight: "calc(100vh - 70px - 187px)",
                }}>
                    {children}
                </div>
                <Footer company={producer} />
            </DashboardLayout>
        </ThemeProvider>
    );
}
export default ApproverManagement;