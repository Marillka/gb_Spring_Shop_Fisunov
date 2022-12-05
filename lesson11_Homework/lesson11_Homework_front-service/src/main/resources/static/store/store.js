angular.module('market').controller('storeController', function ($scope, $http, $rootScope, $localStorage, $location) {

    const marketAuthContextPath = 'http://localhost:5555/auth';
    const marketCoreContextPath = 'http://localhost:5555/core/api/v1';
    const marketCartContextPath = 'http://localhost:5555/cart/api/v1';

    $scope.loadProducts = function (page = 1) {
        $http({
            url: marketCoreContextPath + '/products',
            method: 'GET',
            params: {
                p: page,
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            console.log($scope.productsPage);
            $scope.generatePagesList($scope.productsPage.totalPages)
        });

    };

    $scope.generatePagesList = function (totalPages) {
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
    }

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