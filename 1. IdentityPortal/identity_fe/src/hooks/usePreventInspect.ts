import devtoolsDetect from "devtools-detect";
import { useEffect, useState } from "react";

const ctrlShiftKey = (e: KeyboardEvent, key: string) => {
  return e.ctrlKey && e.shiftKey && e.key.toUpperCase() === key.toUpperCase();
};

export const usePreventInspect = () => {
  const nodeEnv = process.env.REACT_APP_NODE_ENV || "development";

  const [devToolsOpen, setDevToolsOpen] = useState(
    nodeEnv === "development" ? false : devtoolsDetect.isOpen
  );

  useEffect(() => {
    if (nodeEnv === "development") return;
    const contextMenuListener = (e: MouseEvent) => {
      e.preventDefault();
      setDevToolsOpen(true);
    };

    const handleChange = (event: CustomEvent) => {
      setDevToolsOpen(event.detail.isOpen);
    };

    const keyDownListener = (e: KeyboardEvent) => {
      if (
        e.key === "F12" ||
        ctrlShiftKey(e, "I") ||
        ctrlShiftKey(e, "J") ||
        ctrlShiftKey(e, "C") ||
        (e.ctrlKey && e.key.toUpperCase() === "U")
      ) {
        e.preventDefault();
        setDevToolsOpen(true);
      }
      return false;
    };

    document.addEventListener("contextmenu", contextMenuListener);
    document.addEventListener("keydown", keyDownListener);
    window.addEventListener("devtoolschange", handleChange);

    return () => {
      document.removeEventListener("contextmenu", contextMenuListener);
      document.removeEventListener("keydown", keyDownListener);
      window.removeEventListener("devtoolschange", handleChange);
    };
  }, [nodeEnv]);

  return nodeEnv === "development" ? false : devToolsOpen;
};
