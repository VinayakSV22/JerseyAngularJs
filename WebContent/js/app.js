var app = angular.module("app", ['ngRoute','ngMessages','toaster']);

// configure our routes
    app.config(function($routeProvider) {
        $routeProvider

            // route for the home page
            .when('/home', {
                templateUrl : 'views/home.html',
                controller  : 'mainController'
            })

            // route for the about page
            .when('/personlist', {
                templateUrl : 'views/personlist.html',
                controller  : 'personListController'
            })

            .otherwise({
			redirectTo: '/home'
		});
    });


	app.filter('join', function () {
		return function join(array, separator, prop) {
			if (!Array.isArray(array)) {
				return array; // if not array return original - can also throw error
			}

			return (!!prop ? array.map(function (item) {
				return item[prop];
			}) : array).join(separator);
		};
	});

    // create the controller and inject Angular's $scope, $http
    app.controller('mainController', function($scope,$http) {

		$scope.showDomain = false;
        $scope.emailId = "";
        $scope.name = "";
        $scope.comment = "";
        $scope.hobyList = {};

        $scope.hobbiesOptions = [
					    'Singing', 
					    'Dancing', 
					    'Playing', 
					    'Other'
					  ];

		
		//NOTE: To iterate the selected checklist
		/*$scope.checkList = function(){
			angular.forEach($scope.hobyList, function (selected, item) {
		        if (selected) {
		    	       alert(item);
		        }
		    });
			
		}*/

        $scope.getDomain = function(email){
        	//alert($scope.myForm.email.$viewValue);

				if(email != 'undefined' &&  email != null && email != undefined){    	
				var trimValue = email;	
        		trimValue = trimValue.split("@");
        		trimValue = trimValue[1];
        		trimValue = trimValue.split(".");
        		trimValue = trimValue[0];

        		$scope.domainCroped = trimValue;
        		$scope.showDomain = true;
        	}else{
        		$scope.showDomain = false;
        	}
        }

        $scope.save = function(){
        	var listOfHobbies = [];
        	angular.forEach($scope.hobyList, function (selected, item) {
		        if (selected) {
		    	       //alert(item);
		    	       listOfHobbies.push(item);
		        }
		    });

        	var data = {
        		'name':$scope.name,
        		'emailId':$scope.emailId,
        		'hobbies':listOfHobbies,
        		'comment':$scope.comment
        	};

        	console.log(data.hobbies);
        	var url = "http://localhost:8082/simpledemo/rest/add";
        	  
        	$http.post(url, data)
				    .then(function(response) {
				        //First function handles success
				        $scope.result = response.data.notification;
				    }, function(response) {
				        //Second function handles error
				        $scope.result = "Something went wrong";
				    });


        }

    });

    app.controller('personListController', function($scope,$http) {
		$scope.delete_icon = "images/delete.png";

		var url = "http://localhost:8082/simpledemo/rest/allpersons";
		$http.get(url)
				    .then(function(response) {
				        //First function handles success
				        $scope.personList = response.data.personList;
				      
				    }, function(response) {
				        //Second function handles error
				        $scope.personList = "Something went wrong";
				    });
		
		$scope.getAllData = function(){
			
			var url = "http://localhost:8082/simpledemo/rest/allpersons";
			$http.get(url)
					    .then(function(response) {
					        //First function handles success
					        $scope.personList = response.data.personList;
					      
					    }, function(response) {
					        //Second function handles error
					        $scope.personList = "Something went wrong";
					    });
			
		}

		$scope.deleteById = function(id){
			var url = "http://localhost:8082/simpledemo/rest/delete";

			$http.get(url)
				    .then(function(response) {
				        //First function handles success
						if(response.data.error == 999){
							//alert("deleted the row successfully!");
							$scope.success = "deleted the row successfully!";
							
							$scope.callToasters("success");
							
							$scope.getAllData();
						}
						else{
							$scope.error = "Not Deleted!";
							
							$scope.callToasters("error");
						}
				      
				    }, function(response) {
				        //Second function handles error
				        $scope.warning = "Somthing went wrong!";
				        $scope.callToasters("warning");
				    });
		}
		
		$scope.callToasters = function(name){
			var x = document.getElementById(name)
		    x.className = "show";
		    setTimeout(function(){ 
		    	x.className = x.className.replace("show", ""); 
		    }, 3000);
		}

		$scope.deleteAll = function(){
			var id = -1;
			var url = "http://localhost:8082/simpledemo/rest/"+id;

			$http.get(url)
				    .then(function(response) {
				        //First function handles success
						if(response.data.notification === 777)
							alert("deleted all!");
						else
							alert("not deleted!");
				      
				    }, function(response) {
				        //Second function handles error
				        alert("Something went wrong.");
				    });
		}
		
		$scope.pop = function(){
			alert("hi");
	        toaster.pop('success', "success", "text");
	        toaster.pop('error', "error", "text");
	        toaster.pop('warning', "warning", "text");
	        toaster.pop('note', "note", "text");
	    }
    });