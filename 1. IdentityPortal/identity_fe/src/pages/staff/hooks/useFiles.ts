import { PERMISSIONS } from "#constant/index";
import { RootState } from "#context/redux/store";
import {
  downloadDataStaff,
  downloadTemplate as downloadTemplateApi,
  downloadTemplateByAdmin,
  uploadFile as uploadFileApi,
} from "#service/api/staff.api";
import { useState } from "react";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";

export const useFiles = () => {
  const { permissions, userInfo } = useSelector(
    (state: RootState) => state?.auth?.authorization
  );

  const [loadingState, setLoadingState] = useState<boolean>(false);

  const [loadingStateTemplate, setLoadingStateTemplate] =
    useState<boolean>(false);

  const downloadTemplate = async () => {
    try {
      setLoadingStateTemplate(true);
      const response = permissions.includes(PERMISSIONS.ADMIN)
        ? await downloadTemplateByAdmin()
        : await downloadTemplateApi(userInfo?.userId);
      const url = window.URL.createObjectURL(
        new Blob([response.data], { type: "application/vnd.ms-excel" })
      );
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "template.xlsx");
      document.body.appendChild(link);
      link.click();
    } catch (e) {
      toast.error("Không thể tải xuống template");
    } finally {
      setLoadingStateTemplate(false);
    }
  };

  const downloadCurrentStaffData = async (campusCode: string) => {
    try {
      setLoadingState(true);
      const response = await downloadDataStaff(campusCode);
      const url = window.URL.createObjectURL(
        new Blob([response.data], { type: "application/vnd.ms-excel" })
      );
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "danh-sach-nhan-vien.xlsx");
      document.body.appendChild(link);
      link.click();
    } catch (e) {
      toast.error("Không thể tải xuống template");
    } finally {
      setLoadingState(false);
    }
  };

  const uploadFile = async (file: File) => {
    try {
      setLoadingState(true);
      const response = await uploadFileApi(file);
      toast.success(response.data);
    } catch (error: any) {
      toast.error(error.response.data);
    } finally {
      setLoadingState(false);
    }
  };

  return {
    downloadTemplate,
    uploadFile,
    downloadCurrentStaffData,
    loadingState,
    loadingStateTemplate,
  };
};
