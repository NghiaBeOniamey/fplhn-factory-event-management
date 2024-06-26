import { ResponseObject } from "#type/index.i";
import { AxiosPromise, AxiosResponse } from "axios";
import { instanceNoAuth } from "../base/request";

const URL_ENTRY_MODULE = "/entry-module";

const URL_SUPPORT = "/support/mail";

export const getAllEntryModule = async () => {
  const res = (await instanceNoAuth({
    url: `${URL_ENTRY_MODULE}/all`,
    method: "GET",
  })) as AxiosResponse<ResponseObject<any>>;

  return res.data;
};

export const sendSupportMail = ({
  subject,
  body,
  file,
  moduleAddress,
}: {
  subject: string;
  body: string;
  file?: any;
  moduleAddress: string;
}) => {
  const formData = new FormData();
  formData.append("subject", subject);
  formData.append("body", body);
  if (file) formData.append("file", file.originFileObj as File);
  formData.append("moduleAddress", moduleAddress);
  return instanceNoAuth({
    url: `${URL_SUPPORT}/send`,
    method: "POST",
    data: formData,
  }) as AxiosPromise;
};
