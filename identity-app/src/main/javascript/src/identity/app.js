(function () {
    'use strict';
    var angular = require('angular');
    var moment = require('moment');
    var ngModule = angular.module('time-logger', [require('angular-route')]);

    ngModule.config(function ($routeProvider) {
        $routeProvider.when('/:date', {
            template: '<time-logger></time-logger>'
        });
        $routeProvider.otherwise('/' + moment().format('YYYY-MM-DD'));
    });

    require('./moment.filter.js')(ngModule);
    require('./timeLogger.service.js')(ngModule);

    require('./time-logger.directive.js')(ngModule);
    require('./time-logger-nav.directive.js')(ngModule);
    require('./time-entries.directive.js')(ngModule);

    require('./time-entry-form.directive.js')(ngModule);

    module.exports = ngModule;
})();