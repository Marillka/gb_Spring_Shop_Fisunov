angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage, $rootScope) {

    if ($localStorage.marchMarketUser) {
        // Перед тем как прицепить токен к каждому нашему запросу нужно проверить актуален ли токен.
        // Потому что вы зайдет под своей учеткой например на слудующий день, а вас не пускает, потому что время действия токена истекло.
        try {
            // достаем токен из хранилища (он состоиет из 3 частей: header, payload, signature
            // и в payload есть время жизни токена
            let jwt = $localStorage.marchMarketUser.token;
            // сплитуем токен и забираем payload
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            // Смотрим на текущее время. Если текущее время превышает время жизни токена, то логируем что время жизни вышло и очищаем пользователяю из локального хралинида и хедера в http запросе.
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!!!")
                delete $localStorage.marchMarketUser;
                $http.defaults.headers.common.Authorization = '';
            } else {
                // если время жизни не истекло до прописываем в header к http запросу.
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marchMarketUser.token;
            }
        } catch (e) {

        }
    };

    $scope.loadProducts = function () {
        $http.get('http://localhost:8189/market-core/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
                // console.log(response);
            });
    };

    $scope.loadCart = function () {
        $http.get('http://localhost:8190/market-cart/api/v1/cart')
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
        $http.get('http://localhost:8190/market-cart/api/v1/cart/add/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.createNewProduct = function () {
        // console.log($scope.newProduct);
        $http.post('http://localhost:8189/market-core/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.loadProducts();
            });
    }

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/market-core/auth', $scope.user)
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

    $scope.getCurrentUserInfo = function () {
        $http.get('http://localhost:8189/market-core/auth/about_me')
            .then(function (response) {
                alert(response.data.value)
            });
    };


    $scope.loadProducts();
    $scope.loadCart();
});