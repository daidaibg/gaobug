module.exports = {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  // purge: [ './index.html', './src/**/*.vue', './src/**/*.jsx', ],
  // enabled: true,
  // darkMode: false, 
  theme: {
    extend: {
      spacing: {
        '16': '16px',
      },
      borderRadius: {
        '6': '6px',
      }
    },
  
  },
  plugins: [],

}
