import React, { Suspense, useEffect } from "react";

// react-router components
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Cookies from "js-cookie";
// Icon Fonts
import AuthGuard from "./guard/AuthGuard";
import NotFound from "./pages/ErrorPage/404";
import NotAuthorized from "./pages/ErrorPage/401";
import OrganizerManagement from "./layout/organizer/OrganizerManagement";
import OrganizerRoutes from "./layout/organizer/OrganizerRoutes";
import ApproverManagement from "./layout/approver/ApproverManagement";
import ApproverRoutes from "./layout/approver/ApproverRoutes";
import HomeComponent from "./pages/Home";
import APEventDetail from "./pages/ApproverManagement/APEventDetail/index";
import ADEventDetail from "./pages/AdminManagement/ADEventDetail/index";
import ADHEventDetail from "./pages/AdminHManagement/AdminHEventDetail/index";
import ADHOEventDetail from "./pages/AdminHOManagement/AdminHOEventDetail/index";
import APAttendanceList from "./pages/ApproverManagement/APAttendanceList/index";
import ADAttendanceList from "./pages/AdminManagement/ADAttendanceList/index";
import OREventDetail from "./pages/OrganizerManagement/OREventDetail/index";
import ORAttendanceList from "./pages/OrganizerManagement/ORAttendanceList/index";
import ORRegistrationList from "./pages/OrganizerManagement/ORRegistrationList/index";
import APRegistrationList from "./pages/ApproverManagement/APRegistrationList";
import ADRegistrationList from "./pages/AdminManagement/ADRegistrationList";
import ADHRegistrationList from "./pages/AdminHManagement/AdminHRegistrationList";
import ADHAttendanceList from "./pages/AdminHManagement/AdminHAttendanceList";
import AdminHOAttendanceList from "./pages/AdminHOManagement/AdminHOAttendanceList";
import AdminHORegistrationList from "./pages/AdminHOManagement/AdminHORegistrationList";
import ORPermission from "./pages/ORPermission/index";
import Forbidden from "./pages/ErrorPage/403";
import NotAceptable from "./pages/ErrorPage/NotAceptable";
import { useAppSelector } from "./app/hook";
import { GetCountRequest, GetLoading, SetLoadingFalse, SetLoadingTrue } from "./app/reducer/Loading.reducer";
import UnknownError from "./pages/ErrorPage/500";
import { dispatch } from "./app/store";
import Loading from "./utils/Loading/Loading";
import AdminManagement from "./layout/admin/AdminManagement";
import AdminRoutes from "./layout/admin/AdminRoutes";
import Footer from "./section/Footer";
import AdminHManagement from "./layout/admin-h/AdminHManagement";
import AdminHRoutes from './layout/admin-h/AdminHRoutes';
import AdminHOManagement from "./layout/admin-ho/AdminHOManagement";
import AdminHORoutes from './layout/admin-ho/AdminHORoutes';
import SuportMailButton from "./components/SuportMail";

export default function App() {

    const previousURL = window.location.search;
    const previousURLParams = new URLSearchParams(previousURL);
    // const tokenFromPreviousURL = previousURLParams.get("Token");
    const tokenFromPreviousURL = previousURLParams.get("state");
    if (tokenFromPreviousURL) {
        const decodeToken = atob(tokenFromPreviousURL);
        const authenToken = JSON.parse(decodeToken);
        ////  //console.log(authenToken);
        const { accessToken, refreshToken } = authenToken;
        Cookies.set("token", accessToken, { expires: 365 });
    }


    const ORGANIZER_MANAGEMENT_URL = '/organizer-management';
    const APPROVER_MANAGEMENT_URL = '/approver-management';
    const ADMIN_MANAGEMENT_URL = '/admin-management';
    const ADMIN_H_MANAGEMENT_URL = '/admin-h-management';
    const ADMIN_HO_MANAGEMENT_URL = '/admin-ho-management';
    // const PARTICIPANT_URL = '/';

    const getOrganizerRoutes = (allRoutes) =>
        allRoutes.map((route) => {
            if (route.collapse) {
                return getOrganizerRoutes(route.collapse);
            }
            if (route.route && route.type !== "redirect") {
                return (
                    <Route
                        exact
                        path={ORGANIZER_MANAGEMENT_URL + route.route}
                        element={
                            <AuthGuard>
                                <OrganizerManagement urlRole={ORGANIZER_MANAGEMENT_URL}
                                    routeName={route.name}>{route.component}</OrganizerManagement>
                            </AuthGuard>
                        }
                        key={route.key}
                    />
                );
            }

            return null;
        });

    const getApproverRoutes = (allRoutes) =>
        allRoutes.map((route) => {
            if (route.collapse) {
                return getApproverRoutes(route.collapse);
            }
            if (route.route) {
                return (
                    <Route
                        exact
                        path={APPROVER_MANAGEMENT_URL + route.route}
                        element={
                            <AuthGuard>
                                <ApproverManagement routeName={route.name}
                                    urlRole={APPROVER_MANAGEMENT_URL}>{route.component}</ApproverManagement>
                            </AuthGuard>
                        }
                        key={route.key}
                    />
                );
            }

            return null;
        });

    const getAdminRoutes = (allRoutes) =>
        allRoutes.map((route) => {
            if (route.collapse) {
                return getAdminRoutes(route.collapse);
            }
            if (route.route) {
                return (
                    <Route
                        exact
                        path={ADMIN_MANAGEMENT_URL + route.route}
                        element={
                            <AuthGuard>
                                <AdminManagement routeName={route.name}
                                    urlRole={ADMIN_MANAGEMENT_URL}>{route.component}</AdminManagement>
                            </AuthGuard>
                        }
                        key={route.key}
                    />
                );
            }

            return null;
        });

    const getAdminHRoutes = (allRoutes) =>
        allRoutes.map((route) => {
            if (route.collapse) {
                return getAdminHRoutes(route.collapse);
            }
            if (route.route) {
                return (
                    <Route
                        exact
                        path={ADMIN_H_MANAGEMENT_URL + route.route}
                        element={
                            <AuthGuard>
                                <AdminHManagement routeName={route.name}
                                    urlRole={ADMIN_H_MANAGEMENT_URL}>{route.component}</AdminHManagement>
                            </AuthGuard>
                        }
                        key={route.key}
                    />
                );
            }

            return null;
        });

    const getAdminHORoutes = (allRoutes) =>
        allRoutes.map((route) => {
            if (route.collapse) {
                return getAdminHORoutes(route.collapse);
            }
            if (route.route) {
                return (
                    <Route
                        exact
                        path={ADMIN_HO_MANAGEMENT_URL + route.route}
                        element={
                            <AuthGuard>
                                <AdminHOManagement routeName={route.name}
                                    urlRole={ADMIN_HO_MANAGEMENT_URL}>{route.component}</AdminHOManagement>
                            </AuthGuard>
                        }
                        key={route.key}
                    />
                );
            }

            return null;
        });

    const loadingOverlay = useAppSelector(GetLoading);
    const countRequest = useAppSelector(GetCountRequest);
    useEffect(() => {
        // //console.log("aaaaaaaaaaaa " + countRequest)
        if (countRequest > 0) {
            dispatch(SetLoadingTrue());
        }
        if (countRequest === 0) {
            dispatch(SetLoadingFalse());
        }
    }, [countRequest]);

    return (
        <div className="App scroll-smooth md:scroll-auto font-sans">
            {loadingOverlay && <Loading />}
            <BrowserRouter>
                <Suspense>
                    <SuportMailButton/>
                    <Routes>
                        <Route path="*" element={<NotFound />} />
                        <Route path="/not-authorization" key="not-authorization" element={<NotAuthorized />} />
                        <Route path="/forbidden" key="forbidden" element={<Forbidden />} />
                        <Route path="/not-aceptable/*" key="not-aceptable" element={<NotAceptable />} />
                        <Route path="/unknown-error" key="unknown-error" element={<UnknownError />} />
                        {getApproverRoutes(ApproverRoutes)}
                        {getOrganizerRoutes(OrganizerRoutes)}
                        {getAdminRoutes(AdminRoutes)}
                        {getAdminHRoutes(AdminHRoutes)}
                        {getAdminHORoutes(AdminHORoutes)}
                        {/* Trang mặc định */}
                        <Route path="/" element={<Navigate replace to="/permission-event" />} />
                        <Route
                            path="/home"
                            name="home"
                            element={
                                <AuthGuard>
                                    <HomeComponent />
                                    {/*<Footer />*/}
                                </AuthGuard>
                            }
                        />
                        {/* Những màn hình không hiển thị nút bấm trên NAV hoặc menu thì viết như dưới đây */}
                        <Route path={APPROVER_MANAGEMENT_URL + "/event-detail/:id"} key={'event-detail'}
                            element={
                                <AuthGuard>
                                    <ApproverManagement routeName={'Chi tiết sự kiện'}
                                        urlRole={APPROVER_MANAGEMENT_URL}>
                                        <APEventDetail />
                                    </ApproverManagement>
                                </AuthGuard>
                            } />
                        <Route path={APPROVER_MANAGEMENT_URL + "/event-attendance/:id"} key={'attendance-list'}
                            element={
                                <AuthGuard>
                                    <ApproverManagement routeName={'Danh sách điểm danh'}
                                        urlRole={APPROVER_MANAGEMENT_URL}>
                                        <APAttendanceList />
                                    </ApproverManagement>
                                </AuthGuard>
                            } />
                        <Route path={APPROVER_MANAGEMENT_URL + "/registration-list/:id"} key={'register-list'}
                            element={
                                <AuthGuard>
                                    <ApproverManagement routeName={'Danh sách đăng ký'}
                                        urlRole={APPROVER_MANAGEMENT_URL}>
                                        <APRegistrationList />
                                    </ApproverManagement>
                                </AuthGuard>
                            } />
                        <Route
                            path={ORGANIZER_MANAGEMENT_URL + "/event-detail/:id"}
                            key={"event-detail"}
                            element={
                                <AuthGuard>
                                    <OrganizerManagement routeName={'Chi tiết sự kiện'}
                                        urlRole={ORGANIZER_MANAGEMENT_URL}>
                                        <OREventDetail />
                                    </OrganizerManagement>
                                </AuthGuard>
                            }
                        />
                        <Route
                            path={ORGANIZER_MANAGEMENT_URL + "/attendance-list/:id"}
                            key={"or-attendance-list"}
                            element={
                                <AuthGuard>
                                    <OrganizerManagement routeName={'Danh sách điểm danh'}
                                        urlRole={ORGANIZER_MANAGEMENT_URL}>
                                        <ORAttendanceList />
                                    </OrganizerManagement>
                                </AuthGuard>
                            }
                        />
                        <Route
                            path={ORGANIZER_MANAGEMENT_URL + "/registration-list/:id"}
                            key={"or-registration-list"}
                            element={<AuthGuard>
                                <OrganizerManagement routeName={'Danh sách đăng ký'} urlRole={ORGANIZER_MANAGEMENT_URL}>
                                    <ORRegistrationList />
                                </OrganizerManagement>
                            </AuthGuard>}
                        />
                        {/*  */}
                        <Route path={ADMIN_H_MANAGEMENT_URL + "/event-detail/:id"} key={'event-detail'}
                            element={
                                <AuthGuard>
                                    <AdminHManagement routeName={'Chi tiết sự kiện'}
                                        urlRole={ADMIN_H_MANAGEMENT_URL}>
                                        <ADHEventDetail />
                                    </AdminHManagement>
                                </AuthGuard>
                            } />
                        <Route path={ADMIN_H_MANAGEMENT_URL + "/event-attendance/:id"} key={'attendance-list'}
                               element={
                                   <AuthGuard>
                                       <AdminHManagement routeName={'Danh sách điểm danh'}
                                                        urlRole={ADMIN_H_MANAGEMENT_URL}>
                                           <ADHAttendanceList/>
                                       </AdminHManagement>
                                   </AuthGuard>
                               } />
                        <Route path={ADMIN_H_MANAGEMENT_URL + "/registration-list/:id"} key={'register-list'}
                               element={
                                   <AuthGuard>
                                       <AdminHManagement routeName={'Danh sách đăng ký'}
                                                        urlRole={ADMIN_H_MANAGEMENT_URL}>
                                            <  ADHRegistrationList/>
                                       </AdminHManagement>
                                   </AuthGuard>
                               } />
                        <Route path={ADMIN_MANAGEMENT_URL + "/event-detail/:id"} key={'event-detail'}
                            element={
                                <AuthGuard>
                                    <AdminManagement routeName={'Chi tiết sự kiện'}
                                        urlRole={ADMIN_MANAGEMENT_URL}>
                                        <ADEventDetail />
                                    </AdminManagement>
                                </AuthGuard>
                            } />
                        <Route path={ADMIN_MANAGEMENT_URL + "/event-attendance/:id"} key={'attendance-list'}
                            element={
                                <AuthGuard>
                                    <AdminManagement routeName={'Danh sách điểm danh'}
                                        urlRole={ADMIN_MANAGEMENT_URL}>
                                        <ADAttendanceList />
                                    </AdminManagement>
                                </AuthGuard>
                            } />
                        <Route path={ADMIN_MANAGEMENT_URL + "/registration-list/:id"} key={'register-list'}
                            element={
                                <AuthGuard>
                                    <AdminManagement routeName={'Danh sách đăng ký'}
                                        urlRole={ADMIN_MANAGEMENT_URL}>
                                        <ADRegistrationList />
                                    </AdminManagement>
                                </AuthGuard>
                            } />
                        <Route path={ADMIN_HO_MANAGEMENT_URL + "/event-detail/:id"} key={'event-detail'}
                            element={
                                <AuthGuard>
                                    <AdminHOManagement routeName={'Chi tiết sự kiện'}
                                        urlRole={ADMIN_HO_MANAGEMENT_URL}>
                                        <ADHOEventDetail />
                                    </AdminHOManagement>
                                </AuthGuard>
                            } />
                        <Route path={ADMIN_HO_MANAGEMENT_URL + "/event-attendance/:id"} key={'attendance-list'}
                            element={
                                <AuthGuard>
                                    <AdminHOManagement routeName={'Danh sách điểm danh'}
                                        urlRole={ADMIN_HO_MANAGEMENT_URL}>
                                        <AdminHOAttendanceList />
                                    </AdminHOManagement>
                                </AuthGuard>
                            } />
                        <Route path={ADMIN_HO_MANAGEMENT_URL + "/registration-list/:id"} key={'register-list'}
                            element={
                                <AuthGuard>
                                    <AdminHOManagement routeName={'Danh sách đăng ký'}
                                        urlRole={ADMIN_HO_MANAGEMENT_URL}>
                                        <AdminHORegistrationList />
                                    </AdminHOManagement>
                                </AuthGuard>
                            } />
                        <Route
                            path={"/permission-event"}
                            key={"permission-event"}
                            element={<ORPermission />}
                        />
                    </Routes>
                </Suspense>
            </BrowserRouter>
        </div>
    );
}
