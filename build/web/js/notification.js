//function showNotification(text, id) {
//    if (text !== "") {
//        setTimeout(function () {
//            closeNotification(id);
//        }, 2000);
//    }
//}
//
//function closeNotification(id) {
//    document.getElementById(id).style.display = "none";
//}

function showNotification(text, id) {
    if (text !== "") {
        document.getElementById(id).style.height = "20%";
        setTimeout(function () {
            closeNotification(id);
        }, 1500);
    }
}
function closeNotification(id) {
    document.getElementById(id).style.height = "0%";
}


