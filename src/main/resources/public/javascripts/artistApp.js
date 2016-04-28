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
      ArtistService.createArtist(self.formArtistName, self.formSortName)
        .success(function(response) {
          self.artists.push(response.value);
          var x = self.artists.sort(function(a, b) {
            if (a.sortName < b.sortName)
              return -1;
            if (a.sortName > b.sortName)
              return 1;
            else
              return 0;
            });
          self.artists = x;
          $('#addArtistWindow').modal('hide');
          self.formArtistName = "";
          self.formSortName = "";
          console.log(x);
        })
        .error(function() {
          $log.debug("Post went wrong!");
        }
      );
    };

}]);

myApp.service('ArtistService', ['$http', function($http){

  var self = this;

  self.getArtists = function() {
    return $http.get('/artist/');
  };

  self.createArtist = function(displayName, sortName) {
    var data = {displayName: displayName, sortName: sortName};
    return $http.post('/artist/create', data);
  };

}]);