function toggleActiveNavbarItemOn(itemId) {


    var homeMenuItem = document.getElementById('homeMenuItem');
    var artistMenuItem = document.getElementById('artistMenuItem');
    var albumMenuItem = document.getElementById('albumMenuItem');
    
    homeMenuItem.className = "";
    artistMenuItem.className = "";
    albumMenuItem.className = "";

    var activeItem = document.getElementById(itemId);

    activeItem.className = "active";

}
