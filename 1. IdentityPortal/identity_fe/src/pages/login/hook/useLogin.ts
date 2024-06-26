import { PERMISSIONS, URL_BACKEND_OAUTH2, URL_FRONTEND } from "#constant/index";
import { INTERNAL_SERVER_ERROR } from "#constant/message.constant";
import { RootState } from "#context/redux/store";
import { getAllEntryModule } from "#service/no-permission";
import { message } from "antd";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

interface IModule {
  name: string;
  uri: string;
}

export const useLogin = () => {
  const [urlRedirect, setUrlRedirect] = useState<string>("");

  const [module, setModule] = useState<IModule[]>([]);

  const [isLoginProcessing, setIsLoginProcessing] = useState<boolean>(false);

  const authorization = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const navigate = useNavigate();

  const handleLogin = () => {
    if (urlRedirect === "") {
      message.info("Đang kết nối, vui lòng thử lại sau!");
      return;
    }
    setIsLoginProcessing(true);
    window.location.href = URL_BACKEND_OAUTH2 + urlRedirect;
  };

  useEffect(() => {
    if (
      authorization?.userInfo &&
      authorization?.permissions &&
      !authorization?.permissions.includes(PERMISSIONS.SINH_VIEN)
    ) {
      navigate("/module-switch");
    }

    const getAllModule = async () => {
      try {
        const { data } = await getAllEntryModule();
        setModule(data);
        setUrlRedirect(URL_FRONTEND);
      } catch (e) {
        message.error(INTERNAL_SERVER_ERROR);
      }
    };
    getAllModule();
  }, [navigate, authorization?.userInfo, authorization?.permissions]);

  return {
    module,
    urlRedirect,
    setUrlRedirect,
    handleLogin,
    isLoginProcessing,
  };
};
