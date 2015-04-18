'use strict';

angular.module('shultzspaceApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


