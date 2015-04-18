'use strict';

angular.module('shultzspaceApp')
    .controller('ResumeDetailController', function ($scope, $stateParams, Resume) {
        $scope.resume = {};
        $scope.load = function (id) {
            Resume.get({id: id}, function(result) {
              $scope.resume = result;
            });
        };
        $scope.load($stateParams.id);
    });
