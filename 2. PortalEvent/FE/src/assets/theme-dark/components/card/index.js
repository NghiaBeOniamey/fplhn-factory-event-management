// Argon Dashboard 2 MUI Base Styles
import colors from "../../base/colors";
import borders from "../../base/borders";
import boxShadows from "../../base/boxShadows";

// Argon Dashboard 2 MUI Helper Function
import rgba from "../../functions/rgba";

const { black, background } = colors;
const { borderWidth, borderRadius } = borders;
const { cardBoxShadow } = boxShadows;

const card = {
  styleOverrides: {
    root: {
      display: "flex",
      flexDirection: "column",
      position: "relative",
      minWidth: 0,
      wordWrap: "break-word",
      backgroundColor: background.dark,
      backgroundClip: "border-box",
      border: `${borderWidth[0]} solid ${rgba(black.main, 0.125)}`,
      borderRadius: borderRadius.xl,
      boxShadow: cardBoxShadow,
    },
  },
};

export default card;
