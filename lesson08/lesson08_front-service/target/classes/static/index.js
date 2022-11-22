angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage, $rootScope) {

    if ($localStorage.marchMarketUser) {
        try {
            let jwt = $localStorage.marchMarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!!!")
                delete $localStorage.marchMarketUser;
                $http.defaults.headers.common.Authorization = '';
            } else {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marchMarketUser.token;
            }
        } catch (e) {

        }
    };

    $scope.loadProducts = function () {
        $http.get('http://localhost:5555/core/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
                // console.log(response);
            });
    };

    $scope.loadCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    // $scope.deleteProduct = function (id) {
    //     $http.delete('http://localhost:8189/market-core/api/v1/products/' + id)
    //         .then(function (response) {
    //             $scope.loadProducts();
    //         });
    // }

    $scope.addToCart = function (id) {
        $http.get('http://localhost:5555/cart/api/v1/cart/add/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    // $scope.createNewProduct = function () {
    //     // console.log($scope.newProduct);
    //     $http.post('http://localhost:8189/core/api/v1/products', $scope.newProduct)
    //         .then(function (response) {
    //             $scope.newProduct = null;
    //             $scope.loadProducts();
    //         });
    // }

    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/authenticate', $scope.user)
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

    // $scope.getCurrentUserInfo = function () {
    //     $http.get('http://localhost:5555/auth/about_me')
    //         .then(function (response) {
    //             alert(response.data.value)
    //         });
    // };

    $scope.createOrder = function () {
        //...
    }


    $scope.loadProducts();
    $scope.loadCart();
});