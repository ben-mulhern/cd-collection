var myApp = angular.module('artistApp', []);

myApp.controller('ArtistController', ['$log', 'ArtistService', function($log, ArtistService) {

  var self = this;

  self.artists;
  self.dataError = false;
  self.formArtistName;
  self.formSortName;
  self.artistSearchTerm;

  self.genericWindowTitle;
  self.genericWindowMessage;
  self.genericWindowAction;
  self.genericNotificationTitle;
  self.genericNotificationMessage;
  self.manageWindowTitle;
  self.manageWindowAction;
  self.manageWindowMode;

  self.artistModel;

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
          self.artists = self.sortArtistList(self.artists);
          $('#manageArtistWindow').modal('hide');
          self.showGenericNotification("Artist created", "Artist " + self.formArtistName + " was created successfully.")
          self.artistSearchTerm = self.formArtistName;
          self.formArtistName = "";
          self.formSortName = "";
        })
        .error(function() {
          $log.debug("Post went wrong!");
        }
      );
    };

    self.updateArtist = function() {
      ArtistService.updateArtist(self.artistModel)
        .success(function(response) {

          // Find old version in array and remove it before adding in new version and sorting
          var checkUpdate = function(a) { return a.id == self.artistModel.id };
          var x = self.artists.findIndex(checkUpdate);
          self.artists.splice(x, 1);
          self.artists.push(response.value);
          self.artists = self.sortArtistList(self.artists);

          $('#manageArtistWindow').modal('hide');
          self.showGenericNotification("Artist updated", "Artist " + self.formArtistName + " was updated successfully.")
          self.artistSearchTerm = self.formArtistName;
          self.formArtistName = "";
          self.formSortName = "";
        })
        .error(function() {
          $log.debug("Put went wrong!");
        }
      );
    };

    self.sortArtistList = function(artistList) {
      return artistList.sort(function(a, b) {
        var aCaps = a.sortName.toUpperCase();
        var bCaps = b.sortName.toUpperCase();
        if (aCaps < bCaps)
          return -1;
        if (aCaps > bCaps)
          return 1;
        else
          return 0;
        });
    };

    self.setArtist = function() {
      if (self.manageWindowMode)
        self.createArtist();
      else
        self.artistModel.displayName = self.formArtistName;
        self.artistModel.sortName = self.formSortName;
        self.updateArtist();
    }

    self.showGenericNotification = function(title, message) {
      self.genericNotificationTitle = title;
      self.genericNotificationMessage = message;
      $('#genericNotificationWindow').modal('show');
    };

    self.deleteArtist = function(artist) {
      self.showGenericChoice("Delete artist", 
                             "Are you sure you want to delete artist " + artist.displayName + "?",
                             "Delete");
    }

    self.showGenericChoice = function(title, message, action) {
      self.genericWindowTitle = title;
      self.genericWindowMessage = message;
      self.genericWindowAction = action;
      $('#genericChoiceWindow').modal('show');
    }

    self.manageArtist = function(createMode, artist) {
      self.manageWindowMode = createMode;
      if (createMode) {
        self.formArtistName = "";
        self.formSortName = "";      
        self.manageWindowTitle = "Add artist";
        self.manageWindowAction = "Add new artist";
      } else { 
        self.artistModel = artist;
        self.formArtistName = artist.displayName;
        self.formSortName = artist.sortName; 
        self.manageWindowTitle = "Update artist";
        self.manageWindowAction = "Update artist";
      };  
      $('#manageArtistWindow').modal('show');        
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

  self.updateArtist = function(artist) {
    console.log("Artist sort name is " + artist.sortName);
    return $http.put('/artist/update', artist);
  };

}]);