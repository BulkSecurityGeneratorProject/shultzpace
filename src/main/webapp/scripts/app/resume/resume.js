'use strict';

angular.module('shultzspaceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('resume', {
                parent: 'site',
                url: '/resume',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/resume/resume.html',
                        controller: 'ResumeController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('resume');
                        return $translate.refresh();
                    }]
                }
            });
    });
