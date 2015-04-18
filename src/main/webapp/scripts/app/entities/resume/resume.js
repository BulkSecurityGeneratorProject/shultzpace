'use strict';

angular.module('shultzspaceApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('resume', {
                parent: 'entity',
                url: '/resume',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'shultzspaceApp.resume.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/resume/resumes.html',
                        controller: 'ResumeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('resume');
                        return $translate.refresh();
                    }]
                }
            })
            .state('resumeDetail', {
                parent: 'entity',
                url: '/resume/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'shultzspaceApp.resume.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/resume/resume-detail.html',
                        controller: 'ResumeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('resume');
                        return $translate.refresh();
                    }]
                }
            });
    });
