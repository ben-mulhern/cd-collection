var myApp = angular.module('artistApp', []);

myApp.controller('ArtistController', ['$log', 'ArtistService', function($log, ArtistService) {

  var self = this;

  this.artists;
  this.dataError = false;

  ArtistService.getArtists().then(function(response) {
       self.artists = response.data;
    },
    function() {
      self.dataError = true;
      $log.debug("It went wrong!");
    });

  //ArtistService.createArtist("Ben", "Ben");

}]);

myApp.service('ArtistService', ['$http', function($http){

  this.getArtists = function() {
    return $http.get('/artist/')
  };

  this.createArtist = function(displayName, sortName) {
    var data = {displayName: displayName, sortName: sortName}
    $http.post('/createArtist/', data)
      .success(function(response) {
        console.log(response);
       });
  };

}]);