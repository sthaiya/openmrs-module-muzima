function ErrorCtrl($scope, $filter, $location, $data) {
    $data.getErrors().
        then(function(response) {
            $scope.errors = response.data;
        });

    $scope.selectedErrors = function () {
        return $filter('filter')($scope.errors, {checked: true});
    };

    $scope.queue = function() {
        $location.path("/errors");
    }

    $scope.filterError = function() {
        // use the $scope.search as the filter term
    }
}

function QueueCtrl($scope, $filter, $location, $data) {
    $data.getQueues().
        then(function(response) {
            $scope.queues = response.data;
        });

    $scope.selectedQueues = function () {
        return $filter('filter')($scope.queues, {checked: true});
    };

    $scope.delete = function() {
        $location.path("/queues");
    }

    $scope.filterQueue = function() {
        // use the $scope.search as the filter term
    }
}

function EditSourceCtrl($scope, $location, $data) {
}

function CreateSourceCtrl($scope, $location, $data) {
}

function SourcesCtrl($scope, $data) {
    $data.getSources().
        then(function(response) {
            $scope.sources = response.data;
        });

    $scope.filterSource = function() {
        // use the $scope.search as the filter term
    }
}