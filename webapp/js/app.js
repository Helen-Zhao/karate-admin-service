var selectedText;
var urlNext;
var urlPrev;
var size = 10;
var xmlDoc;

function loadInitialMemberList() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            xmlDoc = this.responseXML;
            console.log(xmlDoc);
            populateTable(xmlDoc.getElementsByTagName("memberListWrapper")[0]);
            setNextPrev(xmlDoc);

        }
    };
    var url = "http://localhost:8000/service/members?start=0&size=10";
    xhttp.open("GET", url, true);
    xhttp.send();
}
function loadMemberList(next, previous) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var xmlDoc = this.responseXML;
            console.log(xmlDoc);
            populateTable(xmlDoc.getElementsByTagName("memberListWrapper")[0]);
            setNextPrev(xmlDoc);
        }
    }

    if (next) {
        xhttp.open("GET", urlNext, true);
        xhttp.send();
    }

    if (previous) {
        xhttp.open("GET", urlPrev, true);
        xhttp.send();
    }
}

function setNextPrev(xmlDoc) {
    if (typeof xmlDoc.getElementsByTagName("memberListWrapper")[0].getElementsByTagName("urlNext")[0].childNodes[0] == 'undefined') {
        urlNext = null;
    } else {
        urlNext = xmlDoc.getElementsByTagName("memberListWrapper")[0].getElementsByTagName("urlNext")[0].childNodes[0].nodeValue;
    }

    if(typeof xmlDoc.getElementsByTagName("memberListWrapper")[0].getElementsByTagName("urlPrev")[0].childNodes[0] == 'undefined') {
        urlPrev = null;
    } else {
        urlPrev = xmlDoc.getElementsByTagName("memberListWrapper")[0].getElementsByTagName("urlPrev")[0].childNodes[0].nodeValue;
    }
    $('#nextBtn').toggle(urlNext != null);
    $('#previousBtn').toggle(urlPrev != null);
}

function loadFunctions() {
    loadInitialMemberList();
}
function notify(message) {
    toastr.success(message);
}

function swapPage() {
    // document.getElementbyId("content").innerHTML =
}

function populateTable(xmlDoc) {
    $('#loading').hide();
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
            loadInitialMemberList();
            toastr.info("Success", "Member created successfully");
        } else if (this.status < 550 && this.status >= 300) {
            toastr.error("Error", "Issue creating member. " + this.responseText);
        }

    };
    var query = "?email=" + document.getElementById("email").value + "&belt=" + encodeURI(selectedText) + "&fees=" + document.getElementById("fees").value;
    var url = "http://localhost:8000/service/members" + query;
    xhttp.open("POST", url, true);
    console.log(url);
    xhttp.send();
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

$('#loading').show(typeof xmlDoc == 'undefined');

$('#previousBtn').hide();
$('#nextBtn').hide();


