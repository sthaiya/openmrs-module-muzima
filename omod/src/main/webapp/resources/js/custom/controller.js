function ErrorCtrl($scope, $routeParams, $location, $data) {
    // page parameter
    $scope.uuid = $routeParams.uuid;
    // get the current notification
    $data.getError($scope.uuid).
        then(function (response) {
            $scope.error = response.data;
        });

    $scope.queue = function () {
    };

    $scope.cancel = function () {
        $location.path('/consults/true');
    };
}

function ErrorsCtrl($scope, $filter, $location, $data) {
    $scope.maxSize = 5;
    $scope.pageSize = 5;
    $scope.currentPage = 1;
    $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
        then(function (response) {
            var serverData = response.data;
            $scope.errors = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.selectedErrors = function () {
        return $filter('filter')($scope.errors, {checked: true});
    };

    $scope.queue = function () {
        $location.path("/errors");
    };

    $scope.$watch('currentPage', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.errors = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);

    $scope.$watch('search', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $scope.currentPage = 1;
            $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.errors = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);
}

function QueueCtrl($scope, $routeParams, $location, $data) {
    // page parameter
    $scope.uuid = $routeParams.uuid;
    // get the current notification
    $data.getQueue($scope.uuid).
        then(function (response) {
            $scope.error = response.data;
        });

    $scope.delete = function () {
    };

    $scope.cancel = function () {
        $location.path('/consults/true');
    };
}

function QueuesCtrl($scope, $filter, $location, $data) {
    $scope.maxSize = 5;
    $scope.pageSize = 5;
    $scope.currentPage = 1;
    $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
        then(function (response) {
            var serverData = response.data;
            $scope.queues = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.selectedQueues = function () {
        return $filter('filter')($scope.queues, {checked: true});
    };

    $scope.delete = function () {
        $location.path("/queues");
    };

    $scope.$watch('currentPage', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.queues = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);

    $scope.$watch('search', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $scope.currentPage = 1;
            $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.queues = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);
}

function SourceCtrl($scope, $location, $data) {
}

function SourcesCtrl($scope, $data) {
    $scope.maxSize = 5;
    $scope.pageSize = 5;
    $scope.currentPage = 1;
    $data.getSources($scope.search, $scope.currentPage, $scope.pageSize).
        then(function (response) {
            var serverData = response.data;
            $scope.sources = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.$watch('currentPage', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $data.getSources($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.sources = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);

    $scope.$watch('search', function (newValue, oldValue) {
        if (newValue != oldValue) {
            $scope.currentPage = 1;
            $data.getSources($scope.search, $scope.currentPage, $scope.pageSize).
                then(function (response) {
                    var serverData = response.data;
                    $scope.sources = serverData.objects;
                    $scope.noOfPages = serverData.pages;
                });
        }
    }, true);
}