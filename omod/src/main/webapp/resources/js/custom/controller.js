function ErrorCtrl($scope, $filter, $location, $data) {
    $scope.maxSize = 5;
    $scope.pageSize = 5;
    $scope.currentPage = 1;
    $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
        then(function(response) {
            var serverData = response.data;
            $scope.errors = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.selectedErrors = function () {
        return $filter('filter')($scope.errors, {checked: true});
    };

    $scope.queue = function() {
        $location.path("/errors");
    };

    $scope.$watch('currentPage', function() {
        $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
            then(function(response) {
                var serverData = response.data;
                $scope.errors = serverData.objects;
                $scope.noOfPages = serverData.pages;
            });
    }, true);

    $scope.$watch('search', function() {
        $scope.currentPage = 1;
        $data.getErrors($scope.search, $scope.currentPage, $scope.pageSize).
            then(function(response) {
                var serverData = response.data;
                $scope.errors = serverData.objects;
                $scope.noOfPages = serverData.pages;
            });
    }, true);
}

function QueueCtrl($scope, $filter, $location, $data) {
    $scope.maxSize = 5;
    $scope.pageSize = 5;
    $scope.currentPage = 1;
    $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
        then(function(response) {
            var serverData = response.data;
            $scope.queues = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.selectedQueues = function () {
        return $filter('filter')($scope.queues, {checked: true});
    };

    $scope.delete = function() {
        $location.path("/queues");
    };

    $scope.$watch('currentPage', function() {
        $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
            then(function(response) {
                var serverData = response.data;
                $scope.queues = serverData.objects;
                $scope.noOfPages = serverData.pages;
            });
    }, true);

    $scope.$watch('search', function() {
        $scope.currentPage = 1;
        $data.getQueues($scope.search, $scope.currentPage, $scope.pageSize).
            then(function(response) {
                var serverData = response.data;
                $scope.queues = serverData.objects;
                $scope.noOfPages = serverData.pages;
            });
    }, true);
}

function EditSourceCtrl($scope, $location, $data) {
}

function CreateSourceCtrl($scope, $location, $data) {
}

function SourcesCtrl($scope, $data) {
    $scope.maxSize = 5;
    $scope.pageSize = 5;
    $scope.currentPage = 1;
    $data.getSources($scope.search, $scope.currentPage, $scope.pageSize).
        then(function(response) {
            var serverData = response.data;
            $scope.sources = serverData.objects;
            $scope.noOfPages = serverData.pages;
        });

    $scope.$watch('currentPage', function() {
        $data.getSources($scope.search, $scope.currentPage, $scope.pageSize).
            then(function(response) {
                var serverData = response.data;
                $scope.sources = serverData.objects;
                $scope.noOfPages = serverData.pages;
            });
    }, true);

    $scope.$watch('search', function() {
        $scope.currentPage = 1;
        $data.getSources($scope.search, $scope.currentPage, $scope.pageSize).
            then(function(response) {
                var serverData = response.data;
                $scope.sources = serverData.objects;
                $scope.noOfPages = serverData.pages;
            });
    }, true);
}