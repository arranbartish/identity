(function () {
  'use strict';
  var path = require('path');
  var webpack = require('webpack');
  var ngAnnotatePlugiun = require('ng-annotate-webpack-plugin');
  var StyleLintPlugin = require('stylelint-webpack-plugin');
  var distLocation = path.join(__dirname, 'dist');
  console.log('distlocation: ' + distLocation);

  module.exports = {
    context: distLocation,
    entry: [
        './src/identity-app.js',
        'webpack/hot/dev-server',
        'webpack-dev-server/client?http://localhost:8080'
    ],
    output: {
        path: distLocation,
        publicPath: '/dist/',
        filename: 'identity.js'
    },
    devtool: 'source-map',
    module: {
      preLoaders: [
        { test: /\.js$/, exclude: [/node_modules/], loader: 'jshint-loader' }
      ],
      loaders: [
          {test: /\.js$/, loader: 'ng-annotate'},
          {test: /\.css$/, loaders: ['style', 'css']},
          {test: /\.scss$/, loaders: ['style', 'css', 'sass-loader']},
          {test: /\.html$/, loader: 'raw'}
      ]
    },
    plugins: [
        new webpack.optimize.CommonsChunkPlugin('common.js'),
        new ngAnnotatePlugiun({ add: true }),
        new StyleLintPlugin({ configFile: 'stylelint.config.js', syntax: 'scss' }),
        new webpack.HotModuleReplacementPlugin()
    ],
    externals: {
      angular: true,
      'angular-route': '"ngRoute"',
      'moment': true
    }
  };
})();