function toggleActiveNavbarItemOn(itemId) {

    var artistMenuItem = document.getElementById('artistMenuItem');
    var albumMenuItem = document.getElementById('albumMenuItem');
    
    artistMenuItem.className = "";
    albumMenuItem.className = "";

    var activeItem = document.getElementById(itemId);

    activeItem.className = "active";

}

function formatDate(date) {

    return date.day + "/" + date.month + "/" + date.year;

}