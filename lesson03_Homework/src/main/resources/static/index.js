angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $rootScope, $localStorage) {

    const contextPath = 'http://localhost:8189/market/api/v1';

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }


    //---------------------------------  PRODUCTS -----------------------------------
    $scope.fillTable = function () {
        $http.get( contextPath +'/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + '/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $scope.createNewProduct = function () {
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    }

//---------------------------------  CART -----------------------------------
    $scope.fillCart = function () {
        $http.get(contextPath + '/cart')
            .then(function (response) {
                // console.log(response.data)
                $scope.cart = response.data;
            });
    };

    $scope.addToCart = function (id) {
        $http.get(contextPath + '/cart/add/' + id)
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.deleteCart = function () {
        $http.delete(contextPath + '/cart/delete/all')
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.changeQuantity = function (productTitle, delta) {
        $http({
            url: contextPath + '/cart/change_quantity',
            method: 'GET',
            params: {
                productTitle: productTitle,
                delta: delta
            }
        }).then(function (response) {
            $scope.fillCart();
        });
    }

    // ---------------------------------  AUTH -----------------------------------
    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/market/auth', $scope.user)
            .then(function successCallback(response) {
                // console.log(response)
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    console.log($localStorage.springWebUser)
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
                // console.log(response)
            });
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            console.log($scope.springWebUser)
            return true;
        } else {
            return false;
        }
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };


    $scope.showUserInformationAlert = function () {
        $http.get('http://localhost:8189/market/user_profile')
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
                alert('MY EMAIL IS: ' + response.data.email);
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
            });
    }




    $scope.fillTable();
    $scope.fillCart();
});