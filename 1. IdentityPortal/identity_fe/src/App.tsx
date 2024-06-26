import ButtonSupport from "#components/support/ButtonSupport";
import ModalSendSupport from "#components/support/ModalSendSupport";
import WarningDevToolOpen from "#components/ui/WarningDevToolOpen/WarningDevToolOpen";
import { closeModal } from "#context/redux/slice/ModalSlice";
import { RootState } from "#context/redux/store";
import { usePreventInspect } from "#hooks/usePreventInspect";
import { useDispatch, useSelector } from "react-redux";
import { BrowserRouter } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import { Fragment } from "react/jsx-runtime";
import RouteList from "./route/RouteList";

export default function App() {
  const { isOpen } = useSelector((state: RootState) => state?.modal?.modal);

  const dispatch = useDispatch();

  const onClose = () => dispatch(closeModal());

  const isDevToolOpen = usePreventInspect();

  if (isDevToolOpen) return <WarningDevToolOpen />;

  return (
    <Fragment>
      <BrowserRouter>
        <RouteList />
      </BrowserRouter>
      <ModalSendSupport {...{ isOpen, onClose }} />
      <ButtonSupport />
      <ToastContainer />
    </Fragment>
  );
}
