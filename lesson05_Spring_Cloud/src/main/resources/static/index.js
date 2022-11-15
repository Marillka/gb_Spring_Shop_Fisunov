angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage, $rootScope) {

    if ($localStorage.marchMarketUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marchMarketUser.token;
    }

    $scope.fillTable = function () {
        $http.get('http://localhost:8189/market/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
                // console.log(response);
            });
    };

    $scope.deleteProduct = function (id) {
        $http.delete('http://localhost:8189/market/api/v1/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $scope.createNewProduct = function () {
        // console.log($scope.newProduct);
        $http.post('http://localhost:8189/market/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    }

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/market/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marchMarketUser = {username: $scope.user.username, token: response.data.token}

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {

            });
    }

    $rootScope.tryToLogout = function () {
        $scope.clearUser();
    };

    $scope.clearUser = function () {
        delete $localStorage.marchMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.marchMarketUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.getCurrentUserInfo = function () {
        $http.get('http://localhost:8189/market/auth/about_me')
            .then(function (response) {
                alert(response.data.value)
            });
    };



    $scope.fillTable();
});