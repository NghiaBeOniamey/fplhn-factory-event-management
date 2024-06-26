import { LOGIN_DOMAIN } from "#constant/index";
import { API_REQUEST_LOGOUT } from "#service/base/api.constant";
import { instanceNoAuth } from "#service/base/request";

export type SwitchModuleRequest = {
  userId: number;
  userType: string;
  moduleCode: string;
  identityToken: string;
};

export const switchModule = async (params: SwitchModuleRequest) => {
  try {
    const queryParams = new URLSearchParams({
      moduleCode: params.moduleCode,
      userId: String(params.userId),
      userType: params.userType,
      identityToken: params.identityToken,
    });

    const url = `${LOGIN_DOMAIN}/api/module-switch/switch?${queryParams}`;

    window.location.href = url;
  } catch (error) {
    console.error("Error switching module:", error);
  }
};

export const logout = async ({
  userId,
  userType,
}: {
  userId: number | undefined;
  userType: string | undefined;
}) => {
  return await instanceNoAuth.post(API_REQUEST_LOGOUT, {
    userId,
    userType,
  });
};
