var muzimaconsultation = angular.module('muzima', ['ui.bootstrap']);

muzimaconsultation.
    config(['$routeProvider', '$compileProvider', function ($routeProvider, $compileProvider) {
        $compileProvider.urlSanitizationWhitelist(/^\s*(https?|ftp|mailto|file):/);
        $routeProvider.when('/queue', {controller: QueueCtrl,
            templateUrl: '../../moduleResources/muzima/partials/queues.html'});
        $routeProvider.when('/error', {controller: ErrorCtrl,
            templateUrl: '../../moduleResources/muzima/partials/errors.html'});
        $routeProvider.when('/source/:uuid', {controller: EditSourceCtrl,
            templateUrl: '../../moduleResources/muzima/partials/source.html'});
        $routeProvider.when('/newSource/', {controller: CreateSourceCtrl,
            templateUrl: '../../moduleResources/muzima/partials/source.html'});
        $routeProvider.when('/sources', {controller: SourcesCtrl,
            templateUrl: '../../moduleResources/muzima/partials/sources.html'});
        $routeProvider.otherwise({redirectTo: '/sources'});
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

