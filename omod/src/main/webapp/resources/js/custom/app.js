var muzima = angular.module('muzima', ['ui.bootstrap']);

muzima.
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

muzima.factory('$data', function ($http) {
    var getQueues = function (search, pageNumber, pageSize) {
        if (search === undefined) {
            // replace undefined search term with empty string
            search = '';
        }
        return $http.get("queues.json?search=" + search + "&pageNumber=" + pageNumber + "&pageSize=" +pageSize);
    };
    var getQueue = function (uuid) {
        return $http.get("queue.json?uuid=" + uuid);
    };
    var deleteQueue = function (uuidList) {
        return $http.post("queue.json", {"uuidList": uuidList});
    };

    var getErrors = function (search, pageNumber, pageSize) {
        if (search === undefined) {
            // replace undefined search term with empty string
            search = '';
        }
        return $http.get("errors.json?search=" + search + "&pageNumber=" + pageNumber + "&pageSize=" +pageSize);
    };
    var getError = function (uuid) {
        return $http.get("error.json?uuid=" + uuid);
    };
    var reQueue = function (uuidList) {
        return $http.post("error.json", {"uuidList": uuidList});
    };

    var getSources = function (search, pageNumber, pageSize) {
        if (search === undefined) {
            // replace undefined search term with empty string
            search = '';
        }
        return $http.get("sources.json?search=" + search + "&pageNumber=" + pageNumber + "&pageSize=" +pageSize);
    };
    var getSource = function (uuid) {
        return $http.get("source.json?uuid=" + uuid);
    };
    var saveSource = function (name, description) {
        return $http.post("source.json", {"name": name, "description": description});
    };
    var deleteSource = function (uuid) {
        return $http.post("source.json", {"uuid": uuid});
    };
    return {
        getQueues: getQueues,
        getQueue: getQueue,
        deleteQueue: deleteQueue,

        getErrors: getErrors,
        getError: getError,
        reQueue: reQueue,

        getSources: getSources,
        getSource: getSource,
        saveSource: saveSource,
        deleteSource: deleteSource
    }
});

