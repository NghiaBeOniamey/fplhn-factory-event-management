import { MODULE_NOT_AVAILABLE } from "#constant/message.constant";
import { setAuthorization } from "#context/redux/slice/AuthSlice";
import { getPermissionsUser, getUserInformation } from "#utils/token.helper";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, useSearchParams } from "react-router-dom";

const StaffRedirectHandler = () => {
  const [searchParams] = useSearchParams();

  const dispatch = useDispatch();

  const navigate = useNavigate();

  useEffect(() => {
    const state = searchParams.get("state");
    const token = state ? atob(state) : null;
    const authenticToken = token ? JSON.parse(token) : null;
    const error = searchParams.get("error");

    if (authenticToken) {
      const { accessToken, refreshToken } = authenticToken;
      const userInfo = getUserInformation(accessToken);
      const permissions = getPermissionsUser(accessToken);

      if (userInfo.moduleAvailableResponse == null) {
        navigate(`/error?error=${MODULE_NOT_AVAILABLE}`);
        return;
      }

      if (userInfo && permissions) {
        dispatch(
          setAuthorization({
            token: accessToken,
            userInfo,
            permissions,
            refreshToken,
            moduleAvailableResponses: userInfo.moduleAvailableResponse
              ? userInfo.moduleAvailableResponse
              : [],
          })
        );
        navigate("/module-switch");
        return;
      }
    }

    if (error) {
      navigate("/");
      return;
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return null;
};

export default StaffRedirectHandler;
