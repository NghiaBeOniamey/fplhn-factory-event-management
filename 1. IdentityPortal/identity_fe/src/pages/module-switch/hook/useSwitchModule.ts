import { PERMISSIONS } from "#constant/index";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { removeAuthorization } from "#context/redux/slice/AuthSlice";
import { RootState } from "#context/redux/store";
import { logout, switchModule } from "#service/api/auth.api";
import { isTokenExpired } from "#utils/token.helper";
import { Modal } from "antd";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

export const useSwitchModule = () => {
  const { userInfo, moduleAvailableResponses, token, permissions } =
    useSelector((state: RootState) => state?.auth?.authorization);

  const navigate = useNavigate();

  const dispatch = useDispatch();

  const [loading, setLoading] = useState<boolean>(false);

  const [isLogout, setIsLogout] = useState<boolean>(false);

  const [isRedirect, setIsRedirect] = useState<boolean>(false);

  const handleLogout = async () => {
    setLoading(true);
    setIsLogout(true);
    try {
      const response = await logout({
        userId: userInfo?.userId,
        userType: userInfo?.userType,
      });
      if (response) {
        dispatch(removeAuthorization());
        navigate("/");
      }
    } catch (e) {
      Modal.error({
        title: INTERNAL_SERVER_ERROR,
        content: "Bạn có muốn bắt buộc đăng xuất không?",
        onOk: () => {
          dispatch(removeAuthorization());
          navigate("/");
        },
      });
    } finally {
      setLoading(false);
      setIsLogout(false);
    }
  };

  const handleModuleSwitch = (moduleCode: string) => {
    if (moduleCode === "QLPQ" || moduleCode === "QLPQBMUDPM") {
      navigate("/management/welcome-dashboard");
    } else {
      setIsRedirect(true);
      switchModule({
        userId: userInfo?.userId,
        userType: userInfo?.userType,
        moduleCode,
        identityToken: token,
      });
    }
  };

  useEffect(() => {
    if (!isLogout) {
      if (
        userInfo?.userType === "SINH_VIEN" ||
        permissions?.includes(PERMISSIONS.SINH_VIEN)
      ) {
        navigate("/student-register");
      }

      if (!token || isTokenExpired(token)) {
        dispatch(removeAuthorization());
        navigate("/");
      }
    }
  }, [dispatch, isLogout, navigate, permissions, token, userInfo]);

  return {
    loading,
    handleLogout,
    handleModuleSwitch,
    moduleAvailableResponses,
    isRedirect,
  };
};
