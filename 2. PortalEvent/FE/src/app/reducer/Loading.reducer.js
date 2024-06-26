import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  loading: false,
  countRequest: 0
}

const LoadingSlice = createSlice({
  name: "loading",
  initialState,
  reducers: {
    SetLoadingFalse: (state) => {
      state.loading = false;
      return state;
    },
    SetLoadingTrue: (state) => {
      state.loading = true;
      return state;
    },
    SetCountCong: (state) => {
      state.countRequest = state.countRequest + 1;
      return state;
    },
    SetCountTru: (state) => {
      state.countRequest = state.countRequest - 1;
      return state;
    },
  },
});

export const { SetLoadingFalse, SetLoadingTrue, SetCountCong, SetCountTru } = LoadingSlice.actions;


export const GetLoading = (state) => state.loading.loading;
export const GetCountRequest = (state) => state.loading.countRequest;

export default LoadingSlice.reducer;