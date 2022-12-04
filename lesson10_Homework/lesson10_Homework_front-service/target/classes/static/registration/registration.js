angular.module('market').controller('registrationController', function ($scope, $http) {

    const marketAuthContextPath = 'http://localhost:5555/auth';
    const marketCoreContextPath = 'http://localhost:5555/core/api/v1';
    const marketCartContextPath = 'http://localhost:5555/cart/api/v1';

    $scope.registration = function () {
        $http.post(marketAuthContextPath + "/register", $scope.newUser)
            .then(function successCallback(response) {
                alert('Регистрация прошла успешно');
                $scope.newUser.password = '';
                $scope.newUser.username = '';
                $scope.newUser.confirmPassword = '';
                $scope.newUser.email = '';
            }, function errorCallback(response) {
                alert(response.data.message);
            });
    };

});