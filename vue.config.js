const path = require('path');
module.exports = {
  pages: {
    desktop: {
      entry: 'src/main.js',
      template: 'public/index.html',
      filename: 'index.html'
    },
    mobile: {
      entry: 'src/main-mobile.js',
      template: 'public/index.html',
      filename: 'mobile.html'
  }
},
}