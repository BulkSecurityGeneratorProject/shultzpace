'use strict';

angular.module('shultzspaceApp')
    .controller('ResumeController', function ($scope, Principal) {
      //$scope.resumes = resolvedResume;

      $scope.create = function () {
          Resume.save($scope.resume,
              function () {
                  $scope.resumes = Resume.query();
                  $('#saveResumeModal').modal('hide');
                  $scope.clear();
              });
      };

      $scope.update = function (id) {
          $scope.resume = Resume.get({id: id});
          $('#saveResumeModal').modal('show');
      };

      $scope.delete = function (id) {
          Resume.delete({id: id},
              function () {
                  $scope.resumes = Resume.query();
              });
      };

      $scope.clear = function () {
          $scope.resume = {id: "", sampleTextAttribute: "", sampleDateAttribute: ""};
      };
    });
