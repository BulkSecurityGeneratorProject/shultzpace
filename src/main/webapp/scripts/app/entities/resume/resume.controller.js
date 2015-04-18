'use strict';

angular.module('shultzspaceApp')
    .controller('ResumeController', function ($scope, Resume) {
        $scope.resumes = [];
        $scope.loadAll = function() {
            Resume.query(function(result) {
               $scope.resumes = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Resume.update($scope.resume,
                function () {
                    $scope.loadAll();
                    $('#saveResumeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Resume.get({id: id}, function(result) {
                $scope.resume = result;
                $('#saveResumeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Resume.get({id: id}, function(result) {
                $scope.resume = result;
                $('#deleteResumeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Resume.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteResumeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.resume = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
