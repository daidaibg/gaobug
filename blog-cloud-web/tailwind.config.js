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
    screens: {
      'sm': '640px',
      md: '768px',
      'lg': '1024px',
      'xl': '1280px',
      '2xl': '1536px',
    },
  },
  plugins: [],

}
