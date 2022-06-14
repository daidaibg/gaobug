module.exports = {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  purge: [ './index.html', './src/**/*.vue', './src/**/*.jsx', ],
  enabled: true,
  darkMode: false, 
  theme: {
    extend: {},
  },
  plugins: [],

}
