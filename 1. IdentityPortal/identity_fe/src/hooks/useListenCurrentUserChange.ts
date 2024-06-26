import {
  CHANGE_ROLE,
  CHANGE_STATUS,
  IMPORT_ROLE,
  IMPORT_STAFF,
} from "#constant/websocket.constant";
import { removeAuthorization } from "#context/redux/slice/AuthSlice";
import { RootState } from "#context/redux/store";
import useConnectWebSocket from "#hooks/useConnectWebSocker";
import { Modal } from "antd";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

export const useListenCurrentUserChange = () => {
  const { lastMessage } = useConnectWebSocket();

  const userInfo = useSelector(
    (state: RootState) => state?.auth?.authorization?.userInfo
  );

  const navigate = useNavigate();

  const dispatch = useDispatch();

  useEffect(() => {
    if (
      userInfo &&
      lastMessage &&
      (lastMessage === CHANGE_STATUS(userInfo?.userId) ||
        lastMessage === CHANGE_ROLE(userInfo?.userId))
    ) {
      Modal.confirm({
        title: "Thông báo",
        content:
          "Có vẻ như quyền của bạn đã bị thay đổi, vui lòng đăng nhập lại!",
        onOk: () => {
          dispatch(removeAuthorization());
          navigate("/");
        },
        centered: true,
        cancelButtonProps: { style: { display: "none" } },
        okButtonProps: { className: "bg-[#001529]" },
      });
    }

    if (
      userInfo &&
      lastMessage &&
      (lastMessage === IMPORT_ROLE || lastMessage === IMPORT_STAFF)
    ) {
      Modal.confirm({
        title: "Thông báo",
        content:
          lastMessage === IMPORT_ROLE
            ? "Dữ liệu về quyền đã được nhập dữ liệu mới"
            : "Dữ liệu về nhân viên đã được nhập dữ liệu mới",
        centered: true,
        cancelButtonProps: { style: { display: "none" } },
        okButtonProps: { className: "bg-[#001529]" },
      });
    }
  }, [dispatch, lastMessage, navigate, userInfo]);
};
