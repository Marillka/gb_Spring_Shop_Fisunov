angular.module('market').controller('cartController', function ($scope, $http, $localStorage) {

    const marketAuthContextPath = 'http://localhost:5555/auth';
    const marketCoreContextPath = 'http://localhost:5555/core/api/v1';
    const marketCartContextPath = 'http://localhost:5555/cart/api/v1';

    $scope.createOrder = function () {
        $http.post(marketCoreContextPath + '/orders')
            .then(function successCallback(response) {
                console.log(response)
                $scope.fillCart();
                alert('Заказ создан успешно');
            }, function errorCallback(response) {
                $scope.fillCart();
                alert('Не удалось создать заказ');
            });
    }

    $scope.fillCart = function () {
        $http.get(marketCartContextPath + '/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.clearCart = function () {
        $http.delete(marketCartContextPath + '/cart/clear')
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

    $scope.fillCart();

});




