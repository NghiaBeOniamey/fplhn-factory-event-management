import axios from "axios";
import { instanceNoAuth } from "../base/request";

const URL_ENTRY_MODULE = "/entry-module";

const URL_SUPPORT = "/support/mail";

export const getAllEntryModule = async () => {
  const res = await instanceNoAuth({
    url: `${URL_ENTRY_MODULE}/all`,
    method: "GET",
  });

  return res.data;
};

export const sendSupportMail = (data) => {
  const formData = new FormData();
  formData.append("subject", data.subject);
  formData.append("body", data.body);
  if (data.file) formData.append("file", data.file.originFileObj);
  formData.append("moduleAddress", data.moduleAddress);
  return instanceNoAuth({
    url: `${URL_SUPPORT}/send`,
    method: "POST",
    data: formData,
  });
};
