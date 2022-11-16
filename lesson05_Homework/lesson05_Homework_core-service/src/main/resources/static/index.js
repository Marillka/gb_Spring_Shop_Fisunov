angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $rootScope, $localStorage) {

    //---------------------------------  CONST -----------------------------------

    const marketCoreContextPath = 'http://localhost:8189/market-core/api/v1';
    const marketCartContextPath = 'http://localhost:8190/market-cart/api/v1';

    //---------------------------------  PREPARATION -----------------------------------

    try {
        let jwt = $localStorage.springWebUser.token;
        let payload = JSON.parse(atob(jwt.split('.')[1]));
        let currentTime = parseInt(new Date().getTime() / 1000);
        if (currentTime > payload.exp) {
            console.log("Token is expired!!!")
            delete $localStorage.springWebUser;
            $http.defaults.headers.common.Authorization = '';
        }
        if ($localStorage.springWebUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        }
    } catch (e) {

    }

    //---------------------------------  PRODUCTS -----------------------------------
    $scope.fillTable = function () {
        $http.get(marketCoreContextPath + '/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.deleteProduct = function (id) {
        $http.delete(marketCoreContextPath + '/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }
    //
    // $scope.createNewProduct = function () {
    //     $http.post(marketCoreContextPath + '/products', $scope.newProduct)
    //         .then(function (response) {
    //             $scope.newProduct = null;
    //             $scope.fillTable();
    //         });
    // }

//---------------------------------  CART -----------------------------------
    $scope.fillCart = function () {
        $http.get(marketCartContextPath + '/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };


    $scope.addToCart = function (id) {
        $http.get(marketCartContextPath + '/cart/add/' + id)
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.deleteCart = function () {
        $http.delete(marketCartContextPath + '/cart/delete/all')
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.changeQuantity = function (productTitle, delta) {
        $http({
            url: marketCartContextPath + '/cart/change_quantity',
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
        $http.post('http://localhost:8189/market-core/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };


    $scope.tryToLogout = function () {
        $scope.clearUser();
    };


    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.showUserInformationAlert = function () {
        $http.get('http://localhost:8189/market-core/user_profile')
            .then(function successCallback(response) {
                alert('MY NAME IS: ' + response.data.username);
                alert('MY EMAIL IS: ' + response.data.email);
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
            });
    }

    // --------------------------------- ORDER -----------------------------------

    $scope.createOrder = function () {

    }

    // --------------------------------- OTHER -----------------------------------
    $scope.fillTable();
    $scope.fillCart();
});







