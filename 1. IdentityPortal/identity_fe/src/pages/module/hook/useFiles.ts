import { downloadTemplateRole, uploadFileRole } from "#service/api/module.api";
import { message } from "antd";
import React from "react";
import { toast } from "react-toastify";

export const useFiles = (
  ref: React.RefObject<HTMLInputElement>,
  moduleCode: string
) => {
  const [downloadTemplateLoading, setDownloadTemplateLoading] =
    React.useState<boolean>(false);

  const downloadTemplate = async () => {
    try {
      setDownloadTemplateLoading(true);
      const response = await downloadTemplateRole(moduleCode);
      setDownloadTemplateLoading(false);
      const blob = new Blob([response.data], {
        type: "application/vnd.ms-excel",
      });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", `template-role-${moduleCode}.xlsx`);
      document.body.appendChild(link);
      link.click();
    } catch (e) {
      toast.error("Không thể tải xuống template");
    } finally {
      setDownloadTemplateLoading(false);
    }
  };

  const uploadFile = async (file: File) => {
    try {
      if (!file) {
        message.error("Vui lòng chọn file");
        return;
      }
      const response = await uploadFileRole(file);
      toast.success(response.data);
      if (ref.current) {
        ref.current.value = "";
      }
    } catch (error: any) {
      toast.error(error.response.data);
    }
  };

  return {
    downloadTemplate,
    uploadFile,
    downloadTemplateLoading,
  };
};
