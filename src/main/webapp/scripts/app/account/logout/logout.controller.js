'use strict';

angular.module('shultzspaceApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
