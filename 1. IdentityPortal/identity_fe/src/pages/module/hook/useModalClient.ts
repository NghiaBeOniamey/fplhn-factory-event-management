import useToggle from "#hooks/useToggle";
import { useGetClientByModuleId } from "#service/action/module.action";
import { useMemo, useState } from "react";

export const useModalClient = () => {
  const { value: show, setValue: setShow } = useToggle();

  const [moduleId, setModuleId] = useState<number | null>(null);

  const onHide = () => setShow(false);

  const onShow = (idModule: number) => {
    setModuleId(idModule);
    setShow(true);
  };

  const { data: dataClientFetch } = useGetClientByModuleId(moduleId);

  const dataClient = useMemo(() => {
    return dataClientFetch?.data;
  }, [dataClientFetch]);

  return {
    show,
    onHide,
    client: dataClient,
    onShow,
  };
};
