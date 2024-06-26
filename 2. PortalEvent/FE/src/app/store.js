import { configureStore } from "@reduxjs/toolkit";
import LoadingReducer from "./reducer/Loading.reducer";

export const store = configureStore({
  reducer: {
    loading: LoadingReducer,
  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;