

function ShowTop10Posts() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('status').innerHTML = xhr.responseText;
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };
    xhr.open("POST", "ShowTop10PostsServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}


function SeeMyPosts() {
    makeDelButton();
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('status').innerHTML = xhr.responseText;
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };
    xhr.open("POST", "ShowMyPostsServlet2", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
    
}
//canvas.toDataURL("image/jpeg");

function makeDelButton(){
    $('body').prepend('<div id="deleteCont">Comment Section</div>');
    $('#deleteCont').css({"float":"right","margin-top":"10em"});
    $('#deleteCont').prepend('<br><label  id=\'CommentID\' >Enter PostID:</label><input  type="text"  id=\'IDSection\'> \n\
        <button type="button" id=\'DelPost\' onclick=\'makeDelReq();\'>DELETE</button><br>' );
    
   
}


function makeDelReq(){
    var PostId=document.getElementById("IDSection").value;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            /* document.getElementById("status").innerHTML = this.responseText;*/ /*to error to ekana status*/
            var response = JSON.parse(this.responseText);
            if (response.error) {
                document.getElementById("status").innerHTML = response.message;
            } else {
                document.getElementById("status").innerHTML = response.message;

            }
        }
    };
    xhttp.open("POST", "DeletePostServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("PostId=" + PostId);
}


function findLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(geoloc_create_map, showError);
    } else {
        $("#response").innerHTML = "Geolocation is not supported by this browser.";
    }
}

function showPopUp() {
    alert("to implement popup!");
}

function DeleteMyAccount() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('status').innerHTML = xhr.responseText;
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };
    xhr.open("POST", "AddRatingServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}
