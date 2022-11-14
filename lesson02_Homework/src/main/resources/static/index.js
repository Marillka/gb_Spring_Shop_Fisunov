angular.module('market', []).controller('indexController', function ($scope, $http) {

    $scope.fillTable = function () {
        $http.get('http://localhost:8189/market/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.deleteProduct = function (id) {
        $http.delete('http://localhost:8189/market/api/v1/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $scope.createNewProduct = function () {
        $http.post('http://localhost:8189/market/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    }

    // -------------------------------------------------------------------------------------------
    $scope.fillCart = function () {
        $http.get('http://localhost:8189/market/api/v1/cart')
            .then(function (response) {
                console.log(response.data)
                $scope.cart = response.data;
            });
    };

    $scope.addToCart = function (id) {
        $http.get('http://localhost:8189/market/api/v1/cart/add/' + id)
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.deleteCart = function () {
        $http.delete('http://localhost:8189/market/api/v1/cart/delete/all')
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.changeQuantity = function (productTitle, delta) {
        $http({
            url: 'http://localhost:8189/market/api/v1/cart/change_quantity',
            method: 'GET',
            params: {
                productTitle: productTitle,
                delta: delta
            }
        }).then(function (response) {
            $scope.fillCart();
        });
    }

    $scope.fillTable();
    $scope.fillCart();
});