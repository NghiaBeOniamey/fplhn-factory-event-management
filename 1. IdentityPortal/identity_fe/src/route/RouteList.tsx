import HashLoading from "#components/ui/HashLoading";
import { ALL_PERMISSIONS, PERMISSIONS } from "#constant/index";
import Dashboard from "#layout/index";
import NotAuthenticated from "#pages/401";
import NotAuthorized from "#pages/403";
import NotFound from "#pages/404";
import Error from "#pages/error";
import Landing from "#pages/login";
import ModuleSwitch from "#pages/module-switch";
import React, { Suspense } from "react";
import { Navigate, useRoutes } from "react-router-dom";
import ProtectedRoute from "./guard/ProtectedRoute";
import StaffRedirectHandler from "./guard/StaffRedirectHandler";
import StudentRedirectHandler from "./guard/StudentRedirectHandler";

const ManageCampus = React.lazy(() => import("#pages/campus"));
const Welcome = React.lazy(() => import("#pages/welcome"));
const ManageStudent = React.lazy(() => import("#pages/student"));
const Staff = React.lazy(() => import("#pages/staff"));
const ModifyStaff = React.lazy(() => import("#pages/staff/layout/ModifyStaff"));
const ManageDepartment = React.lazy(() => import("#pages/department"));
const ManageDepartmentCampus = React.lazy(
  () => import("#pages/departmentcampus")
);
const ManageModule = React.lazy(() => import("#pages/module"));
const Decentralization = React.lazy(
  () => import("#pages/module/layout/Decentralization")
);
const ManageRole = React.lazy(() => import("#pages/role"));
const Semester = React.lazy(() => import("#pages/semester"));
const StudentLanding = React.lazy(() => import("#pages/student-landing"));

type RouteType = {
  path: string;
  element: React.ReactNode;
  children?: RouteType[];
};

const withDashboard = (Component: React.ReactNode) => {
  return (
    <Dashboard>
      <Suspense fallback={<HashLoading />}>{Component}</Suspense>
    </Dashboard>
  );
};

const RouteList = () => {
  const routes: RouteType[] = [
    { path: "*", element: <NotFound /> },
    { path: "/not-authentication", element: <NotAuthenticated /> },
    { path: "/not-authorization", element: <NotAuthorized /> },
    { path: "/forbidden", element: <NotAuthorized /> },
    { path: "/error", element: <Error /> },
    { path: "/", element: <Landing /> },
    { path: "/redirect", element: <StaffRedirectHandler /> },
    { path: "/student", element: <StudentRedirectHandler /> },
    {
      path: "/student-register",
      element: (
        <ProtectedRoute requiredPermissions={[PERMISSIONS.SINH_VIEN]}>
          <StudentLanding />
        </ProtectedRoute>
      ),
    },
    {
      path: "/module-switch",
      element: (
        <ProtectedRoute requiredPermissions={ALL_PERMISSIONS}>
          <ModuleSwitch />
        </ProtectedRoute>
      ),
    },
    {
      path: "/management",
      element: <Navigate to='/management/welcome-dashboard' />,
    },
    {
      path: "/management/welcome-dashboard",
      element: (
        <ProtectedRoute requiredPermissions={ALL_PERMISSIONS}>
          {withDashboard(<Welcome />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-campus",
      element: (
        <ProtectedRoute requiredPermissions={[PERMISSIONS.BAN_DAO_TAO_HO]}>
          {withDashboard(<ManageCampus />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-student",
      element: (
        <ProtectedRoute
          requiredPermissions={[
            PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO,
            PERMISSIONS.QUAN_LY_DAO_TAO,
          ]}
        >
          {withDashboard(<ManageStudent />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-staff",
      element: (
        <ProtectedRoute
          requiredPermissions={[
            PERMISSIONS.CHU_NHIEM_BO_MON,
            PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO,
            PERMISSIONS.QUAN_LY_DAO_TAO,
          ]}
        >
          {withDashboard(<Staff />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-staff/add-staff",
      element: (
        <ProtectedRoute
          requiredPermissions={[
            PERMISSIONS.CHU_NHIEM_BO_MON,
            PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO,
            PERMISSIONS.QUAN_LY_DAO_TAO,
          ]}
        >
          {withDashboard(<ModifyStaff />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-staff/update-staff/:id",
      element: (
        <ProtectedRoute
          requiredPermissions={[
            PERMISSIONS.CHU_NHIEM_BO_MON,
            PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO,
            PERMISSIONS.QUAN_LY_DAO_TAO,
          ]}
        >
          {withDashboard(<ModifyStaff />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-department",
      element: (
        <ProtectedRoute
          requiredPermissions={[
            PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO,
            PERMISSIONS.BAN_DAO_TAO_HO,
          ]}
        >
          {withDashboard(<ManageDepartment />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-department/:departmentId",
      element: (
        <ProtectedRoute
          requiredPermissions={[
            PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO,
            PERMISSIONS.BAN_DAO_TAO_HO,
          ]}
        >
          {withDashboard(<ManageDepartment />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-department-campus/:departmentId",
      element: (
        <ProtectedRoute
          requiredPermissions={[
            PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO,
            PERMISSIONS.BAN_DAO_TAO_HO,
          ]}
        >
          {withDashboard(<ManageDepartmentCampus />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-module",
      element: (
        <ProtectedRoute
          requiredPermissions={[
            PERMISSIONS.ADMIN,
            PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO,
          ]}
        >
          {withDashboard(<ManageModule />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-module/decentralization-staff",
      element: (
        <ProtectedRoute
          requiredPermissions={[
            PERMISSIONS.ADMIN,
            PERMISSIONS.TRUONG_BAN_DAO_TAO_CO_SO,
          ]}
        >
          {withDashboard(<Decentralization />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-role",
      element: (
        <ProtectedRoute requiredPermissions={[PERMISSIONS.ADMIN]}>
          {withDashboard(<ManageRole />)}
        </ProtectedRoute>
      ),
    },
    {
      path: "/management/manage-semester",
      element: (
        <ProtectedRoute requiredPermissions={[PERMISSIONS.BAN_DAO_TAO_HO]}>
          {withDashboard(<Semester />)}
        </ProtectedRoute>
      ),
    },
  ];

  return useRoutes(routes);
};

export default RouteList;
