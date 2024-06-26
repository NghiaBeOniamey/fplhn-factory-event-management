const path = require("path");
module.exports = {
  webpack: {
    alias: {
      "#pages": path.resolve(__dirname, "src/pages"),
      "#components": path.resolve(__dirname, "src/components"),
      "#styles": path.resolve(__dirname, "src/styles"),
      "#utils": path.resolve(__dirname, "src/utils"),
      "#assets": path.resolve(__dirname, "src/assets"),
      "#hooks": path.resolve(__dirname, "src/hooks"),
      "#context": path.resolve(__dirname, "src/context"),
      "#service": path.resolve(__dirname, "src/service"),
      "#constant": path.resolve(__dirname, "src/constant"),
      "#type": path.resolve(__dirname, "src/type"),
      "#layout": path.resolve(__dirname, "src/layout"),
    },
  },
};
