function ErrorCtrl($scope, $routeParams, $location, $data) {
    // page parameter
    $scope.uuid = $routeParams.uuid;
    // get the current notification
    $data.getError($scope.uuid).
        then(function (response) {
            $scope.error = response.data;
        });

    $scope.queue = function () {
        var uuidList = [$scope.uuid];
        $data.reQueue(uuidList).
            then(function() {
                $location.path("/errors");
            })
    };

    $scope.cancel = function () {
        $location.path('/errors');
    };
}

function ErrorsCtrl($scope, $location, $data) {
    // initialize selected error data for re-queueing
    $scope.selected = {};
    // initialize the paging structure
    $scope.maxSize = 5;
    $scope.pageSize = 5;
    $scope.currentPage = 1;
    $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
        then(function (response) {
            var serverData = response.data;
            $scope.errors = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.queue = function () {
        var uuidList = [];
        angular.forEach($scope.selected, function(value, key) {
            if (value) {
                uuidList.push(key);
            }
        });
        $data.reQueue(uuidList).
            then(function() {
                $location.path("/errors");
            })
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
            $scope.queue = response.data;
        });

    $scope.delete = function () {
        var uuidList = [$scope.uuid];
        $data.deleteQueue(uuidList).
            then(function() {
                $location.path("/queues");
            })
    };

    $scope.cancel = function () {
        $location.path('/queues');
    };
}

function QueuesCtrl($scope, $location, $data) {
    // initialize selected error data for re-queueing
    $scope.selected = {};
    // initialize the paging structure
    $scope.maxSize = 5;
    $scope.pageSize = 5;
    $scope.currentPage = 1;
    $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
        then(function (response) {
            var serverData = response.data;
            $scope.queues = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.delete = function () {
        var uuidList = [];
        angular.forEach($scope.selected, function(value, key) {
            if (value) {
                uuidList.push(key);
            }
        });
        $data.deleteQueue(uuidList).
            then(function() {
                $location.path("/queues");
            })
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

function SourceCtrl($scope, $routeParams, $location, $data) {
    // initialize the source object
    $scope.source = {};
    // initialize the view to be read only
    $scope.mode = "view";
    $scope.uuid = $routeParams.uuid;
    if ($scope.uuid === undefined) {
        $scope.mode = "edit";
    } else {
        $data.getSource($scope.uuid).
            then(function(response) {
                $scope.source = response.data;
            });
    }

    $scope.edit = function() {
        $scope.mode = "edit";
    };

    $scope.cancel = function() {
        if ($scope.mode == "edit") {
            if ($scope.uuid === undefined) {
                $location.path("/sources");
            } else {
                $scope.mode = "view"
            }
        } else {
            $location.path("/sources");
        }
    };

    $scope.save = function(source) {
        $data.saveSource(source.uuid, source.name, source.description).
            then(function() {
                $location.path("/sources");
            })
    };

    $scope.delete = function() {
        $data.deleteSource($scope.uuid).
            then(function() {
                $location.path("/sources");
            })
    };
}

function SourcesCtrl($scope, $data) {
    // initialize the paging structure
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