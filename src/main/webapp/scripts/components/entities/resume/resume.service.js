'use strict';

angular.module('shultzspaceApp')
    .factory('Resume', function ($resource) {
        return $resource('api/resumes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
