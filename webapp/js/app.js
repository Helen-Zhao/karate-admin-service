var selectedText;
var memberListSize = 100;
var paginationCounter = 0;
var endOfListReached = false;
var beginningOfListReached = true;
var size = 10;

function loadMemberList(next, previous) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var xmlDoc = this.responseXML;
            memberListSize = xmlDoc.getElementsByTagName("memberListWrapper").nodeValue;
            populateTable(xmlDoc.getElementsByTagName("memberListWrapper")[0]);
        }
    };

    if (next) {
        loadNextMemberPage(xhttp);
    }

    if (previous) {
        loadPreviousMemberPage(xhttp);
    }
}
function loadPreviousMemberPage(xhttp) {
    size = 10;
    updateNextPrevious(false);
    var url = "http://localhost:8000/service/members?start=" + paginationCounter + "&size=" + size;
    console.log(url);
    paginationCounter =- 10;
    xhttp.open("GET", url, true);
    xhttp.send();
}

function loadNextMemberPage(xhttp) {
    size = 10;
    updateNextPrevious(true);
    if (!endOfListReached) {
        var url = "http://localhost:8000/service/members?start=" + paginationCounter + "&size=" + size;
        console.log(url);
        paginationCounter += 10;
        xhttp.open("GET", url, true);
        xhttp.send();
    }

}

function updateNextPrevious(isNext) {
    if ((paginationCounter + size) > memberListSize) {
        if (isNext) {
            size = memberListSize - paginationCounter - 1;
        }
        console.log("reached end of list")
        endOfListReached = true;
    } else {
        console.log("not end of list")
        endOfListReached = false;
    }
    if ((paginationCounter - size) < 0) {
        if (!isNext) {
            size = paginationCounter - size;
        }
        beginningOfListReached = true;
        console.log("reached beginning of list");
    } else {
        console.log("not beginning of list")
        beginningOfListReached = false;
    }
}

function loadFunctions() {
    loadMemberList(true, false);
}
function notify(message) {
    toastr.success(message);
}

function swapPage() {
    // document.getElementbyId("content").innerHTML =
}

function populateTable(xmlDoc) {
    var table = '<table class="table-striped"><tr><th>Email</th><th>Belt</th><th>Outstanding Fees</th></tr>';
    console.log(xmlDoc);
    var x = xmlDoc.getElementsByTagName("queriedMembers")[0].getElementsByTagName("queriedMember");
    for (i = x.length - 1; i >= 0; i--) {
        table += "<tr><td>" +
            x[i].getElementsByTagName("email")[0].childNodes[0].nodeValue +
            "</td><td>" +
            x[i].getElementsByTagName("belt")[0].childNodes[0].nodeValue +
            "</td><td>" +
            x[i].getElementsByTagName("fees")[0].childNodes[0].childNodes[0].nodeValue +
            "</td></tr>";
    }
    document.getElementById("table").innerHTML = table;
}

function submitForm() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.status == 201) {
            $("#addMemberModal").modal('hide');
            loadMemberList();
            toastr.info("Success", "Member created successfully");
        } else if (this.status < 550 && this.status > 250) {
            toastr.error("Error", "Issue creating member. " + this.responseText);
        }

    };
    var url = "http://localhost:8000/service/members";
    xhttp.open("POST", url, true);
    var info = "email=" + document.getElementById("email").value + "&belt=" + encodeURI(selectedText) + "&fees=" + document.getElementById("fees").value;
    console.log(info);
    xhttp.send(info);
}

$(document).ready(function () {
    $("#addMember").click(function () {
        $("#addMemberModal").modal();
    });
});

function getDateTime() {
    var today = new Date();
    var months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    var date = today.getDate() + " " + months[today.getMonth()] + ", " + today.getFullYear();
    var ampm = "";
    var hour = today.getHours();
    if (hour > 12) {
        ampm = "PM";
        hour = hour - 12;
    } else {
        ampm = "AM";
    }
    if (hour < 10) {
        hour = "0" + hour;
    }
    var minutes = today.getMinutes();
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    var seconds = today.getSeconds();
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    var time = hour + ":" + minutes + ":" + seconds + " " + ampm;

    document.getElementById("date").innerHTML = date;
    document.getElementById("time").innerHTML = time;

    setTimeout(getDateTime, 1000);
}

/*Date/Time initialiser*/
getDateTime();

toastr.options = {
    "closeButton": false,
    "debug": false,
    "newestOnTop": false,
    "progressBar": false,
    "positionClass": "toast-bottom-right",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

window.onload = loadFunctions();

$('#belt').change(function () {
    selectedText = $(this).find("option:selected").text();
});

$("#nextBtn").toggle(!endOfListReached);
$("#previousBtn").toggle(!beginningOfListReached);
