import React from "react";
import { createRoot } from "react-dom/client";
import App from "./App";
import { Provider } from "react-redux";

// Soft UI Context Provider
import { ArgonControllerProvider } from "./context";

// react-perfect-scrollbar styles
import "react-perfect-scrollbar/dist/css/styles.css";

// index style
import "./index.css";
import { store } from "./app/store";

const container = document.getElementById("root");
const root = createRoot(container);

root.render(
  // <React.StrictMode>
  <Provider store={store}>
    <ArgonControllerProvider>
      <App />
    </ArgonControllerProvider>{" "}
  </Provider>
  // </React.StrictMode>
);
