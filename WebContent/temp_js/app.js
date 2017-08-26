var app = angular.module("app", ['ngRoute']);

app.config(["$routeProvider", function(a) {
	a.when('/login', {
			templateUrl: 'views/login.html',
			controller: 'loginController'
		})
		.when('/home', {
			templateUrl: 'home.html',
			controller: 'hellocontroller'
		})
		.when('/viewStudents', {
			templateUrl: 'viewStudents.html',
			controller: 'StudentController'
		})
		.otherwise({
			redirectTo: '/login'
		});
}]);

app.controller("httpCtrl", function($scope, $http){
	var baseUrl = "http://localhost:8082/simpledemo/rest/";
	
	
})