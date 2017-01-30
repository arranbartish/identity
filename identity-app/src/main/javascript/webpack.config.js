(function () {
  'use strict';
  var path = require('path');
  var webpack = require('webpack');
  var ngAnnotatePlugiun = require('ng-annotate-webpack-plugin');

  module.exports = {
    context: path.join(__dirname),
    entry: {
      'identity': './identity-app.js'
    },
    output: {
      path: path.join(__dirname),
      filename: '[name].js'
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
      new ngAnnotatePlugiun({ add: true })
    ],
    externals: {
      angular: true,
      'angular-route': '"ngRoute"',
      'moment': true
    }
  };
})();