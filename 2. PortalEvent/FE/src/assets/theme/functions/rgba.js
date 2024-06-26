// Argon Dashboard 2 MUI helper functions
import hexToRgb from "./hexToRgb";

function rgba(color, opacity) {
  return `rgba(${hexToRgb(color)}, ${opacity})`;
}

export default rgba;
