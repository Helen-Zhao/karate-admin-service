function loadMemberList() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      populateTable(this);
    }
  };
  var url = "http://localhost:8000/service/members";
  xhttp.open("GET", url, true);
  xhttp.send();
}

function loadFunctions() {
  loadMemberList();
}
function notify(message){
  toastr.success(message);
}

function swapPage() {
  // document.getElementbyId("content").innerHTML =
}

function populateTable(xml) {
  var xmlDoc = xml.responseXML;
  var table = '<table class="table-striped"><tr><th>Email</th><th>Belt</th><th>Outstanding Fees</th></tr>';
  console.log(xmlDoc);
  var x = xmlDoc.getElementsByTagName("member");
  for (i = 0; i <x.length; i++) {
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
  xhttp.onreadystatechange = function() {
    if (this.status == 201) {
      $("#addMemberModal").hide();
      toastr.info("Success", "Member created successfully");
    } else {
      toastr.error("Error", "Issue creating member. " + this.responseText);
    }

  };
  var url = "http://localhost:8000/service/members";
  xhttp.open("POST", url, true);
  var info = "email=" + document.getElementById("email").value + "&belt=" + document.getElementById("belt").value+ "&fees=" + document.getElementById("fees").value;
  console.log(info);
  xhttp.send(info);
}

$(document).ready(function(){
  $("#addMember").click(function(){
    $("#addMemberModal").modal();
  });
});

function getDateTime() {
  var today = new Date();
  var months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
  var date = today.getDate() + " " + months[today.getMonth()] + ", " + today.getFullYear();
  var ampm = "";
  var hour = today.getHours();
  if (hour > 12){
    ampm = "PM";
    hour = hour - 12;
  } else {
    ampm = "AM";
  }
  if(hour < 10){
    hour = "0" + hour;
  }
  var minutes = today.getMinutes();
  if(minutes < 10){
    minutes = "0" + minutes;
  }
  var seconds = today.getSeconds();
  if(seconds < 10){
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