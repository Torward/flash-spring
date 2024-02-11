const colors = require('tailwindcss/colors');
/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/**/*.{html,js}"],
    theme: {
        screens: {
            sm: '480px',
            md: '768px',
            lg: '976px',
            xl: '1440px',
            },
        extend: {
            colors: {
                'primary': 'rgb(0, 30, 60)',
                'secondary': 'rgb(30, 73, 118)',
                'info': '#17a2b8',
                'light': '#f8f9fa',
                'dark': 'rgb(0, 30, 60)',
                'text': 'rgb(128, 191, 255)',
                'success': '#28a745',
                'danger': '#dc3545',
                'warning': '#ffc107',
            },
            fontFamily: {
                sans: ['"Montserrat"', 'sans-serif'],
                serif: ['"Montserrat"', 'serif'],
            },
        },

    },
    plugins: [],
}
