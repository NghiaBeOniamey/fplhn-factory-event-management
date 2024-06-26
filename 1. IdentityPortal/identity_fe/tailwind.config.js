/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    container: {
      center: true,
    },
  },
  plugins: [],
  corePlugins: {
    preflight: false,
  },
  important: true,
};
