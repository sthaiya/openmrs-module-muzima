var muzimaconsultation = angular.module('muzima', ['ui.bootstrap']);

muzimaconsultation.
    config(['$routeProvider', '$compileProvider', function ($routeProvider, $compileProvider) {
        $compileProvider.urlSanitizationWhitelist(/^\s*(https?|ftp|mailto|file):/);
        $routeProvider.when('/queue', {controller: QueueCtrl,
            templateUrl: '../../moduleResources/muzimaconsultation/partials/queue.html'});
        $routeProvider.when('/error', {controller: ErrorCtrl,
            templateUrl: '../../moduleResources/muzimaconsultation/partials/error.html'});
        $routeProvider.when('/source', {controller: SourceCtrl,
            templateUrl: '../../moduleResources/muzimaconsultation/partials/source.html'});
        $routeProvider.otherwise({redirectTo: '/source'});
    }]);

muzimaconsultation.factory('$data', function ($http) {
    var getQueues = function () {
        return $http.get("queues.list");
    };
    var getQueue = function (uuid) {
        return $http.get("queue.form", {"uuid": uuid});
    };
    var deleteQueue = function (uuid) {
        return $http.post("queue.form", {"uuid": uuid});
    };

    var getErrors = function () {
        return $http.get("error.list");
    };
    var getError = function (uuid) {
        return $http.get("error.form", {"uuid": uuid});
    };
    var reQueue = function (uuidList) {
        return $http.post("reQueue.form", {"uuid": uuidList});
    };

    var getSources = function () {
        return $http.get("sources.list");
    };
    var getSource = function (uuid) {
        return $http.get("source.form", {"uuid": uuid});
    };
    var saveSource = function (name, description) {
        return $http.post("source.form", {"name": name, "description": description});
    };
    var deleteSource = function(uuid) {
        return $http.post("source.form", {"uuid": uuid});
    };
    return {
        getQueueList: getQueues,
        getQueue: getQueue,
        deleteQueue: deleteQueue,

        getErrorList: getErrors,
        getError: getError,
        reQueue: reQueue,

        getSources: getSources,
        getSource: getSource,
        saveSource: saveSource,
        deleteSource: deleteSource
    }
});

