/**
 All of the routes for the Soft UI Dashboard React are added here,
 You can add a new route, customize the routes and delete the routes here.
 Once you add a new route on this file it will be visible automatically on
 the Sidenav.
 For adding a new route you can follow the existing routes in the routes array.
 1. The `type` key with the `collapse` value is used for a route.
 2. The `type` key with the `title` value is used for a title inside the Sidenav.
 3. The `type` key with the `divider` value is used for a divider between Sidenav items.
 4. The `name` key is used for the name of the route on the Sidenav.
 5. The `key` key is used for the key of the route (It will help you with the key prop inside a loop).
 6. The `icon` key is used for the icon of the route on the Sidenav, you have to add a node.
 7. The `collapse` key is used for making a collapsible item on the Sidenav that has other routes
 inside (nested routes), you need to pass the nested routes inside an array as a value for the `collapse` key.
 8. The `route` key is used to store the route location which is used for the react router.
 9. The `href` key is used to store the external links location.
 10. The `title` key is only for the item with the type of `title` and its used for the title text on the Sidenav.
 10. The `component` key is used to store the component of its route.
 */

// Argon Dashboard 2 MUI components
import ArgonBox from "../../components/ArgonBox";
// Component
// import EventWaitingApproval from "../../pages/AdminManagement/ADEventWaitingApproval";
// import EventApproved from "../../pages/AdminManagement/ADEventApproved";
// import ADMajorManagerment from "../../pages/AdminManagement/ADMajorManagerment";
// // import PeriodicEvent from "../../pages/ApproverManagement/APPeriodicEvent";
// import APEventClosed from "../../pages/ApproverManagement/APEventClosed";
import React from "react";
import CategoryList from "../../pages/AdminHManagement/AdminHCategoryManagement";
import ObjectList from "../../pages/AdminHManagement/AdminHObjectManagement";
import Semester from "../../pages/AdminHManagement/AdminHSemester";
import APStatisticsEvent from "../../pages/AdminHManagement/APStatisticsEvent";
import AdminHMajorManagerment from "../../pages/AdminHManagement/AdminHMajorManagerment";
import EventWaitingApproval from "../../pages/AdminHManagement/AdminHEventWaitingApproval";


const AdminHRoutes = [
    {
        type: "route",
        name: "Thống kê",
        key: "statistics-event",
        route: "/statistics-event",
        icon: (
            <ArgonBox
                component="i"
                color="primary"
                fontSize="14px"
                className="ni ni-tv-2"
            />
        ),
        component: <APStatisticsEvent />,
    },
    {
        type: "route",
        name: "Quản lý sự kiện",
        key: "event-waiting-approval",
        route: "/event-waiting-approval",
        icon: (
            <ArgonBox
                component="i"
                color="primary"
                fontSize="14px"
                className="ni ni-tv-2"
            />
        ),
        component: <EventWaitingApproval />,
    },
    // {
    //     type: "route",
    //     name: "Sự kiện đã phê duyệt",
    //     key: "event-approved",
    //     route: "/event-approved",
    //     icon: (
    //         <ArgonBox
    //             component="i"
    //             color="primary"
    //             fontSize="14px"
    //             className="ni ni-tv-2"
    //         />
    //     ),
    //     component: <EventApproved />,
    // },
    // {
    //     type: "route",
    //     name: "Sự kiện hằng kỳ",
    //     key: "periodic-event-approved",
    //     route: "/periodic-event-approved",
    //     icon: (
    //         <ArgonBox
    //             component="i"
    //             color="primary"
    //             fontSize="14px"
    //             className="ni ni-tv-2"
    //         />
    //     ),
    //     component: <PeriodicEvent />,
    // },
    // {
    //     type: "route",
    //     name: "Quản lý thể loại",
    //     key: "category-list",
    //     route: "/category-list",
    //     icon: (
    //         <ArgonBox
    //             component="i"
    //             color="primary"
    //             fontSize="14px"
    //             className="ni ni-tv-2"
    //         />
    //     ),
    //     component: <CategoryList />,
    // },
    // {
    //     type: "route",
    //     name: "Quản lý đối tượng",
    //     key: "object-list",
    //     route: "/object-list",
    //     icon: (
    //         <ArgonBox
    //             component="i"
    //             color="primary"
    //             fontSize="14px"
    //             className="ni ni-tv-2"
    //         />
    //     ),
    //     component: <ObjectList />,
    // },
    // {
    //     type: "route",
    //     name: "Quản lý học kỳ",
    //     key: "semester",
    //     route: "/semester",
    //     icon: (
    //         <ArgonBox
    //             component="i"
    //             color="primary"
    //             fontSize="14px"
    //             className="ni ni-tv-2"
    //         />
    //     ),
    //     component: <Semester />,
    // },
    // {
    //     type: "route",
    //     name: "Quản lý bộ môn",
    //     key: "major-management",
    //     route: "/major-management",
    //     icon: (
    //         <ArgonBox
    //             component="i"
    //             color="primary"
    //             fontSize="14px"
    //             className="ni ni-tv-2"
    //         />
    //     ),
    //     component: <AdminHMajorManagerment />,
    // },
    // {
    //     type: "route",
    //     name: "Sự kiện đã đóng",
    //     key: "event-closed",
    //     route: "/event-closed",
    //     icon: (
    //         <ArgonBox
    //             component="i"
    //             color="primary"
    //             fontSize="14px"
    //             className="ni ni-tv-2"
    //         />
    //     ),
    //     component: <APEventClosed/>,
    // },
];

export default AdminHRoutes;
