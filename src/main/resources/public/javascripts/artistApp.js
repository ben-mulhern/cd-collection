var myApp = angular.module('artistApp', []);

myApp.controller('ArtistController', ['$log', 'ArtistService', function($log, ArtistService) {

  var self = this;

  self.artists;
  self.dataError = false;
  self.formArtistName;
  self.formSortName;
  self.artistSearchTerm;

  ArtistService.getArtists().then(function(response) {
       self.artists = response.data;
    },
    function() {
      self.dataError = true;
      $log.debug("It went wrong!");
    });

    self.createArtist = function() {
      ArtistService.createArtist(self.formArtistName, self.formSortName);
      console.log("This was passed into controller: " + self.formArtistName + ", " + self.formSortName)
    };

}]);

myApp.service('ArtistService', ['$http', function($http){

  var self = this;

  self.getArtists = function() {
    return $http.get('/artist/')
  };

  self.createArtist = function(displayName, sortName) {
    var data = {displayName: displayName, sortName: sortName}
    $http.post('/artist/create', data)
      .success(function(response) {
        console.log(response);
       });
  };

}]);