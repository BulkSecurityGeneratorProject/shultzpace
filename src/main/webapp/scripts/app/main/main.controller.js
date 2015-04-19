'use strict';

angular.module('shultzspaceApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
            $scope.gallery = [
            { path: "../assets/images/2500px-NYC_subway-4D_lowres.jpg" },
            { path: "../assets/images/bike.jpg" },
            { path: "../assets/images/downtown.jpg" },
            { path: "../assets/images/horses.jpg" },
            { path: "../assets/images/lady_liberty.jpg" },
            { path: "../assets/images/limelight_100.jpg" },
            { path: "../assets/images/snowshoe.jpg" }
            ];
            for (var i = 0; i < $scope.gallery.length; i++) {
              $scope.gallery[i].d = randomN(true)+"deg";
              $scope.gallery[i].x = randomN(false);
              $scope.gallery[i].y = randomN(false);
              $scope.gallery[i].desktopStyle="animation-duration: "+i+"s; \
              left:"+$scope.gallery[i].x+"px; \
              top:"+$scope.gallery[i].y+"px; \
              -ms-transform: rotate("+$scope.gallery[i].d+"); \
              -webkit-transform: rotate("+$scope.gallery[i].d+"); \
              transform: rotate("+$scope.gallery[i].d+");";
            }
            setTimeout(function(){
              $( ".photo " ).draggable({
                start: function() {
                  $(".photo").click();
                },
                drag: function() {
                  $(".photo").click();
                },
                stop: function() {
                  $(".photo").click();
                }
              });
              $(".photo").click(function(){
                $(".photo").removeClass("active");
                $(this).addClass("active");
              });
              $scope.$apply(function () {
                $scope.portrait=true;
              });
            }, 1000);

            function randomN(negValues) {
              var n = Math.floor((Math.random() * 100) + 1);
              if (n > 50 && negValues) {
                n = n * -1;
              }
              n = (n / 3);
              return n;
            }
        });
    });
