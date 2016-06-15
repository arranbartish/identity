angular.module("WastedApp", ["ngAnimate","ui.bootstrap"])
    .config(["$httpProvider", function($httpProvider){

    }])
    .service("RequestService", function($http){
        this.doRequest = function(request, successCallback) {
            $http(request).success(successCallback);
        }
    })
    .service("WastedTimeService", function(RequestService){

        this.iHaveWastedTime = function(wastedTime, scope) {

            var req = {
                method: 'POST',
                url: '/wasted',
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                data: wastedTime
            };
            RequestService.doRequest(req,
                function(response){
                    scope.submitted = 'DONE';
                }
            );
        };
    })
    .service("FormService", function(){
        this.restForm = function(scope) {
            scope.name = '';
            scope.activity = '';
            scope.duration = '';
            scope.date = new Date();
        }

    })
    .service("AliasService", function(RequestService){

        this.populateTimeWasters = function(scope) {

            var req = {
                method: 'GET',
                url: '/alias',
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            };
            RequestService.doRequest(req,
                function(response){
                    scope.timeWasters = response;
                }
            );
        };
    })
    .service("TimeSliceService", function(RequestService){

        this.populateTimeSlices = function(scope) {
            var req = {
                method: 'GET',
                url: '/time-slices',
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            };
            RequestService.doRequest(req,
                function(response){
                    scope.timeSlices = response;
                }
            );
        };
    })
    .service("ActivitiesService", function(RequestService){

        this.populateActivities = function(scope) {
            var req = {
                method: 'GET',
                url: '/wasted/activities',
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            };
            RequestService.doRequest(req,
                function(response){
                    scope.activities = response;
                }
            );
        };
    })
    .controller("WastedTime", function($scope, $http, $timeout, $log, TimeSliceService, WastedTimeService, AliasService, ActivitiesService, FormService){
        $scope.date = new Date();
        TimeSliceService.populateTimeSlices($scope);
        AliasService.populateTimeWasters($scope);
        ActivitiesService.populateActivities($scope);

        this.submitWastedTime = function() {
            payload = {
                who : $scope.name,
                activity : $scope.activity,
                duration : $scope.duration.slice,
                date : $scope.date.getTime()
            };
            WastedTimeService.iHaveWastedTime(payload, $scope);
            FormService.restForm($scope);
            AliasService.populateTimeWasters($scope);
            ActivitiesService.populateActivities($scope);
        };
    })
    .directive('wasted', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: './templates/wasted.html',
            controllerAs: 'wastedTime',
            controller: 'WastedTime'
        }
    });

