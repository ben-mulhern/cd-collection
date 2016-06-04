var myApp = angular.module('albumApp', []);

myApp.controller('AlbumController', ['$log', 'AlbumService', function($log, AlbumService) {

  var self = this;

  self.albums;
  self.dataError = false;
  self.formAlbumName;
  self.formSortName;
  self.albumSearchTerm;

  self.genericWindowTitle;
  self.genericWindowMessage;
  self.genericWindowAction;
  self.genericWindowFunction;
  self.genericNotificationTitle;
  self.genericNotificationMessage;
  self.manageWindowTitle;
  self.manageWindowAction;
  self.manageWindowMode;

  self.albumModel;

  String.prototype.paddingLeft = function (paddingValue) {
    return String(paddingValue + this).slice(-paddingValue.length);
  };

  self.formatDate = function(date) {
    if (date) {
      return date.day.toString().paddingLeft("00") + "/" + 
             date.month.toString().paddingLeft("00") + "/" + 
             date.year.toString().substring(2, 3).paddingLeft("00");
    } else { return "" };
  };

  AlbumService.getAlbums().then(function(response) {
       self.albums = response.data;
    },
    function() {
      self.dataError = true;
      $log.debug("It went wrong!");
    });

    self.createAlbum = function() {
      AlbumService.createAlbum(self.formAlbumName, self.formSortName)
        .success(function(response) {
          self.albums.push(response.value);
          self.albums = self.sortAlbumList(self.albums);
          $('#manageAlbumWindow').modal('hide');
          self.showGenericNotification("Album created", "Album " + self.formAlbumName + " was created successfully.")
          self.albumSearchTerm = self.formAlbumName;
          self.formAlbumName = "";
          self.formSortName = "";
        })
        .error(function() {
          $log.debug("Post went wrong!");
        }
      );
    };

    self.updateAlbum = function() {
      AlbumService.updateAlbum(self.albumModel)
        .success(function(response) {

          // Find old version in array and remove it before adding in new version and sorting
          var checkUpdate = function(a) { return a.id == self.albumModel.id };
          var x = self.albums.findIndex(checkUpdate);
          self.albums.splice(x, 1);
          self.albums.push(response.value);
          self.albums = self.sortAlbumList(self.albums);

          $('#manageAlbumWindow').modal('hide');
          self.showGenericNotification("Album updated", "Album " + self.formAlbumName + " was updated successfully.")
          self.albumSearchTerm = self.formAlbumName;
          self.formAlbumName = "";
          self.formSortName = "";
        })
        .error(function() {
          $log.debug("Put went wrong!");
        }
      );
    };

    self.sortAlbumList = function(albumList) {
      return albumList.sort(function(a, b) {
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

    self.setAlbum = function() {
      if (self.manageWindowMode)
        self.createAlbum();
      else {
        self.albumModel.displayName = self.formAlbumName;
        self.albumModel.sortName = self.formSortName;
        self.updateAlbum();
      };
    }

    self.showGenericNotification = function(title, message) {
      self.genericNotificationTitle = title;
      self.genericNotificationMessage = message;
      $('#genericNotificationWindow').modal('show');
    };

    self.deleteAlbum = function(album) {
      self.showGenericChoice("Delete album", 
                             "Are you sure you want to delete album " + album.displayName + "?",
                             "Delete", function(){self.executeAlbumDelete(album)});
    }

    self.executeAlbumDelete = function(album) {
      AlbumService.deleteAlbum(album)
        .success(function(response) {
          var checkDelete = function(a) { return a.id == album.id };
          var x = self.albums.findIndex(checkDelete);
          self.albums.splice(x, 1);
          $('#genericChoiceWindow').modal('hide');
          self.showGenericNotification("Album deleted", "Album " + album.displayName + " was deleted successfully.");
        })
        .error(function() {
          $log.debug("Delete Post went wrong!");
        }
      );      
    };

    self.showGenericChoice = function(title, message, action, submitFunction) {
      self.genericWindowTitle = title;
      self.genericWindowMessage = message;
      self.genericWindowAction = action;
      self.genericWindowFunction = submitFunction;
      $('#genericChoiceWindow').modal('show');
    }

    self.manageAlbum = function(createMode, album) {
      self.manageWindowMode = createMode;
      if (createMode) {
        self.formAlbumName = "";
        self.formSortName = "";      
        self.manageWindowTitle = "Add album";
        self.manageWindowAction = "Add new album";
      } else { 
        self.albumModel = album;
        self.formAlbumName = album.displayName;
        self.formSortName = album.sortName; 
        self.manageWindowTitle = "Update album";
        self.manageWindowAction = "Update album";
      };  
      $('#manageAlbumWindow').modal('show');        
    };

}]);

myApp.service('AlbumService', ['$http', function($http){

  var self = this;

  self.getAlbums = function() {
    console.log("Requesting list of all albums - GET /albums");
    return $http.get('/albums');
  };

  self.createAlbum = function(displayName, sortName) {
    var data = {displayName: displayName, sortName: sortName};
    console.log("Creating album - POST /albums, with body " + data);
    return $http.post('/albums', data);
  };

  self.updateAlbum = function(album) {
    var url = "/albums/" + album.id;
    console.log("Updating album - PUT " + url + ", with body " + album);
    return $http.put(url, album);
  };

  self.deleteAlbum = function(album) {
    var url = "/albums/" + album.id;
    console.log("DELETING album - DELETE " + url);
    return $http.delete(url);
  };

}]);