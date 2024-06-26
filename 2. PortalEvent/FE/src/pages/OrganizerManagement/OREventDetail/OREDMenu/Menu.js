import { faList } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Dropdown } from "antd";
import React from "react";

const items = [
    {
        key: "1",
        label: (
            <a target="_blank" rel="noopener noreferrer" href="https://www.antgroup.com">
                Mở điểm danh
            </a>
        ),
    },
    {
        key: "2",
        label: (
            <a target="_blank" rel="noopener noreferrer" href="https://www.aliyun.com">
                Đóng điểm danh
            </a>
        ),
    },
    {
        key: "3",
        label: (
            <a
                target="_blank"
                rel="noopener noreferrer"
                href="https://www.luohanacademy.com"
            >
                Danh sách đăng kí
            </a>
        ),
    },
    {
        key: "4",
        label: (
            <a
                target="_blank"
                rel="noopener noreferrer"
                href="https://www.luohanacademy.com"
            >
                Danh sách điểm danh
            </a>
        ),
    },
    // {
    //     key: "5",
    //     label: (
    //         <a
    //             target="_blank"
    //             rel="noopener noreferrer"
    //             href="https://www.luohanacademy.com"
    //         >
    //             Đóng sự kiện
    //         </a>
    //     ),
    // },
];
const MenuFunction = () => (
    <>
        <br />
        <Dropdown
            menu={{
                items,
            }}
            placement="topLeft"
            arrow
        >
            <Button style={{ backgroundColor: "#0098d1"}} type="primary">
                <span style={{ marginRight: "7px" }}>Chức năng khác</span>
                <FontAwesomeIcon icon={faList} style={{ color: "#ffffff" }} />
            </Button>
        </Dropdown>
    </>
);
export default MenuFunction;
