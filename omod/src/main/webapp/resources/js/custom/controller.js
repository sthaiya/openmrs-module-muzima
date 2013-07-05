function ErrorCtrl($scope, $filter, $location, $data) {
    $data.getErrorList().
        then(function(response) {
            $scope.errors = response.data;
        });

    $scope.selectedErrors = function () {
        return $filter('filter')($scope.errors, {checked: true});
    };

    $scope.reQueue = function() {
        $location.path("/errors");
    }
}

function QueueCtrl($scope, $filter, $location, $data) {
    $data.getQueueList().
        then(function(response) {
            $scope.queues = response.data;
        });

    $scope.selectedQueues = function () {
        return $filter('filter')($scope.queues, {checked: true});
    };

    $scope.deleteQueue = function() {
        $location.path("/queues");
    }
}

function EditSourceCtrl($scope, $location, $data) {
}

function CreateSourceCtrl($scope, $location, $data) {
}

function SourcesCtrl($scope, $location, $data) {
}