function toggleActiveNavbarItemOn(itemId) {

    console.log("I was called with " + itemId);

    var artistMenuItem = document.getElementById('artistMenuItem');
    var albumMenuItem = document.getElementById('albumMenuItem');
    
    artistMenuItem.className = "";
    albumMenuItem.className = "";

    var activeItem = document.getElementById(itemId);

    console.log("activeItem is " + activeItem);

    activeItem.className = "active";

};

