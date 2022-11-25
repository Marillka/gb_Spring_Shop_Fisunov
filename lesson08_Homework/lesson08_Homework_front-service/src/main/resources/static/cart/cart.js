angular.module('market').controller('cartController', function ($scope, $http, $localStorage) {

    const marketCoreContextPath = 'http://localhost:5555/core/api/v1';
    const marketCartContextPath = 'http://localhost:5555/cart/api/v1';

    $scope.loadCart = function () {
        $http.get(marketCartContextPath + '/cart/')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.createOrder = function () {
        $http.post(marketCoreContextPath + '/orders')
            .then(function successCallback(response) {
                console.log(response)
                $scope.loadCart();
                alert('Заказ создан успешно');
            }, function errorCallback(response) {
                $scope.loadCart();
                alert('Не удалось создать заказ');
            });
    }


    $scope.clearCart = function () {
        $http.delete(marketCartContextPath + '/cart/clear')
            .then(function (response) {
                $scope.loadCart();
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
            $scope.loadCart();
        });
    }

    $scope.guestCreateOrder = function () {
        alert('Для оформления заказа необходимо войти в учетную запись')
    };


    $scope.loadCart();

});




