import { URL_WEBSOCKET } from "#constant/index";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import React from "react";
import ReactDOM from "react-dom/client";
import { Provider } from "react-redux";
import { StompSessionProvider } from "react-stomp-hooks";
import "react-toastify/dist/ReactToastify.css";
import App from "./App";
import "./assets/style/index.css";
import store from "./context/redux/store";
import reportWebVitals from "./reportWebVitals";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: 0,
      refetchOnWindowFocus: false,
      refetchOnMount: true,
      refetchOnReconnect: false,
      retryOnMount: false,
      staleTime: 10 * 1000,
      cacheTime: 5 * 60 * 1000,
      onError: (e) => console.error(e),
    },
    mutations: {
      retry: 0,
    },
  },
});

root.render(
  <React.StrictMode>
    <Provider store={store}>
      <QueryClientProvider client={queryClient}>
        <StompSessionProvider url={URL_WEBSOCKET}>
          <App />
        </StompSessionProvider>
        <ReactQueryDevtools initialIsOpen={false} />
      </QueryClientProvider>
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
