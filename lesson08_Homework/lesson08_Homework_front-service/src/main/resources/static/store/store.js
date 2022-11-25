angular.module('market').controller('storeController', function ($scope, $http, $rootScope, $localStorage, $location) {

    const marketAuthContextPath = 'http://localhost:5555/auth';
    const marketCoreContextPath = 'http://localhost:5555/core/api/v1';
    const marketCartContextPath = 'http://localhost:5555/cart/api/v1';


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

    $scope.addToCart = function (id) {
        $http.get(marketCartContextPath + '/cart/add/' + id)
            .then(function (response) {

            });
    }




    $scope.fillTable();

});