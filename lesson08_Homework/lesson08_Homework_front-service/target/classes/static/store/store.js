angular.module('market').controller('storeController', function ($scope, $http, $rootScope, $localStorage, $location) {

    const marketAuthContextPath = 'http://localhost:5555/auth';
    const marketCoreContextPath = 'http://localhost:5555/core/api/v1';
    const marketCartContextPath = 'http://localhost:5555/cart/api/v1';


    $scope.loadProducts = function () {
        $http.get(marketCoreContextPath + '/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.addToCart = function (id) {
        $http.get(marketCartContextPath + '/cart/' + $localStorage.marketGuestCartId + '/add/' + id)
            .then(function (response) {

            });
    }

    $scope.deleteProduct = function (id) {
        $http.delete(marketCoreContextPath + '/products/' + id)
            .then(function (response) {
                $scope.loadProducts();
            });
    }


    $scope.loadProducts();

});