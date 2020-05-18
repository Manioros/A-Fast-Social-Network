    /* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*function check_password() {
 if (document.getElementById('password').value == document.getElementById('confirm_password').value) {
 document.getElementById('message').innerHTML = 'password is same with confirm password,ok.';
 } else {
 document.getElementById('message').innerHTML = 'password is not same with confirm password.';
 }
 }*/

function showlogin() {
    var login = document.getElementById("login");
    login.style.display = "block";
    /*var register = document.getElementById("register");
     register.style.display = "none";*/
    hidderegister();

}

function showMenu() {
    var menu = document.getElementById("menu");
    menu.style.display = "block";
}

function hiddelogin() {
    var login = document.getElementById("login");
    login.style.display = "none";
}

function showregister() {
    var register = document.getElementById("register");
    register.style.display = "block";
    /*var login = document.getElementById("login");
     login.style.display = "none";*/
    hiddelogin();
}

function hidderegister() {
    var register = document.getElementById("register");
    register.style.display = "none";
}


function showMainContainer() {
    var MainContainer = document.getElementById("MainContainer");
    MainContainer.style.display = "block";
}

function dologin() {

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            /* document.getElementById("status").innerHTML = this.responseText;*/ /*to error to ekana status*/
            var response = JSON.parse(this.responseText);
            if (response.error) {
                document.getElementById("status").innerHTML = response.message;
            } else {
                document.getElementById("status").innerHTML = response.message;
                hiddelogin();
                //TODO: dixe logout
                showMenu();
                showMainContainer();
                CreateMapButton();
                CreatePostContainer();
                
            }
        }
    };
    xhttp.open("POST", "loginServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    var username_login = document.getElementById("username_login").value;
    var password_login = document.getElementById("password_login").value;
    xhttp.send("user=" + username_login + "&pass=" + password_login);
}

function doregister() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if (response.error) {
                document.getElementById("status").innerHTML = response.message;
            } else {
                document.getElementById("status").innerHTML = response.message;
                showlogin();
            }

        }
    };
    xhttp.open("POST", "registerServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    var username = document.getElementById("username").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    /*Birthday*/
    var day = document.getElementById("Day").value;
    var month = document.getElementById("Month").value;
    var year = document.getElementById("Year").value;
    var birthday = day + "/" + month + "/" + year;
    /*Birthday*/

    var radios = document.getElementsByName('sex');
    var sexValue;

    for (var i = 0, length = radios.length; i < length; i++)
    {
        if (radios[i].checked)
        {
            // do whatever you want with the checked radio
            sexValue = radios[i].value;

            // only one radio can be logically checked, don't check the rest
            break;
        }
    }

    var counrty = document.getElementById("counrty").value;
    var city = document.getElementById("city").value;
    var job = document.getElementById("job").value;
    var interests = document.getElementById("interests").value;
    var general_information = document.getElementById("general_information").value;
    var address = document.getElementById("address").value;

    xhttp.send("user=" + username + "&email=" + email + "&pass=" + password +
            "&firstname=" + firstname + "&lastname=" + lastname +
            "&birthday=" + birthday + "&gender=" + sexValue + "&counrty=" +
            counrty + "&city=" + city + "&address=" + address + "&job=" + job + "&interests=" + interests +
            "&general_information=" + general_information);
}

function checkUsername() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("status_username").innerHTML = this.responseText;
        }
    };
    var username = document.getElementById("username").value;
    xhttp.open("POST", "checkUsernameServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("user=" + username);
}

function checkEmail() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("status_email").innerHTML = this.responseText;
        }
    };
    var email = document.getElementById("email").value;
    xhttp.open("POST", "checkEmailServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("email=" + email);
}

function showUsers() {
    /*kanw ajax klhsh kai pernw json apo to servlet pou tha ftiaxw sth java
     * localhost:8084/lq/DoLoginServlet*/

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
    xhttp.open("POST", "ShowUsersServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send();
}

function showUserInfo() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if (response.error) {
                document.getElementById("status").innerHTML = response.message;
            } else {
                document.getElementById("status").innerHTML = JSON.stringify(
                        response.message);
                //response.message.BirthDate
            }
        }
    };
    xhttp.open("POST", "ShowUserInfoServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send();
}

function updateUserInfo() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if (response.error) {
                document.getElementById("status").innerHTML = response.message;
            } else {
                document.getElementById("status").innerHTML = JSON.stringify(
                        response.message);
                //response.message.BirthDate
                showregister();
                document.getElementById("username").value = response.message.UserName;
                document.getElementById("email").value = response.message.email;
                if (response.message.Gender == "Unknown") //        MALE("Male"), FEMALE("Female"), UNKNOWN("Unknown");

                    document.getElementById("Not_applicable").checked = true;
                if (response.message.Gender == "Male") //        MALE("Male"), FEMALE("Female"), UNKNOWN("Unknown");
                    document.getElementById("male").checked = true;
                if (response.message.Gender == "Female") //        MALE("Male"), FEMALE("Female"), UNKNOWN("Unknown");
                    document.getElementById("female").checked = true;
                document.getElementById("password").value = response.message.password;
                document.getElementById("firstname").value = response.message.FirstName;
                document.getElementById("lastname").value = response.message.LastName;
                document.getElementById("counrty").value = response.message.Country;
                document.getElementById("city").value = response.message.Town;
                document.getElementById("address").value = response.message.Address;
                document.getElementById("job").value = response.message.Occupation;
                document.getElementById("interests").value = response.message.Interests;
                document.getElementById("general_information").value = response.message.Info;

                var d = new Date(response.message.BirthDate);
                document.getElementById("Year").value = d.getFullYear();
                var month = d.getMonth();
                if (month < 10) {
                    month = "0" + month;
                }
                document.getElementById("Month").value = month;
                var day = d.getDate();
                if (day < 10) {
                    day = "0" + day;
                }
                document.getElementById("Day").value = day;


            }
        }
    };



    xhttp.open("POST", "ShowUserInfoServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send();
}

function doupdate() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if (response.error) {
                document.getElementById("status").innerHTML = response.message;
            } else {
                document.getElementById("status").innerHTML = response.message;
            }
        }
    };
    xhttp.open("POST", "updateServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    //var username = document.getElementById("username").value;
    //var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    /*Birthday*/
    var day = document.getElementById("Day").value;
    var month = document.getElementById("Month").value;
    var year = document.getElementById("Year").value;
    var birthday = day + "/" + month + "/" + year;
    /*Birthday*/

    var radios = document.getElementsByName('sex');
    var sexValue;

    for (var i = 0, length = radios.length; i < length; i++)
    {
        if (radios[i].checked)
        {
            // do whatever you want with the checked radio
            sexValue = radios[i].value;

            // only one radio can be logically checked, don't check the rest
            break;
        }
    }

    var counrty = document.getElementById("counrty").value;
    var city = document.getElementById("city").value;
    var job = document.getElementById("job").value;
    var interests = document.getElementById("interests").value;
    var general_information = document.getElementById("general_information").value;
    var address = document.getElementById("address").value;

    xhttp.send("&pass=" + password +
            "&firstname=" + firstname + "&lastname=" + lastname +
            "&birthday=" + birthday + "&gender=" + sexValue + "&counrty=" +
            counrty + "&city=" + city + "&address=" + address + "&job=" + job + "&interests=" + interests +
            "&general_information=" + general_information);
}

function dologout() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if (response.error) {
                document.getElementById("status").innerHTML = response.message;
            } else {
                document.getElementById("status").innerHTML = response.message;
            }
        }
    };
    xhttp.open("POST", "logoutServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send();
}


function ShowUserProfile() {
    var userName =document.getElementById("searchUserField").value;
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
    xhttp.open("POST", "ShowUserProfileServlet", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("userName=" + userName);
}

function CreatePostContainer(){
    
    $('body').append('<div id="postContainer"></div>');
    $('#postContainer').css({"width":"50em","height":"25em","float":"center","margin-left":"30em"});
    $('#postContainer').append("<div id=addPostDiv> Add new comment </div>");
    $('#addPostDiv').css({"background-color": "light-blue" , "border": "solid" , "border-colour": "black","float":"center","width":"40em","height":"25em","float":"center","margin-left":"3em"});
    $('#addPostDiv').append('<br><label  id=\'commentText\' >Enter text:</label><input  type="text"  id=\'textSection\' required> <button type="button" id=\'Post\' onclick=\'makeReq();\'>Post</button><br>'); 
    $('#textSection').css({"height":"5em"});
    $('#addPostDiv').append('<br><label for="url">Enter URL:</label><input type="url" name="url" id="url"placeholder="https:\//example.com"pattern="https:\//.*" size="30" required>');
    $('#addPostDiv').append('<br><label  >Img Url:  </label> <input id=\'ImgUrl\' name="imgurl" placeholder="Post image URL here" /><br /><img id="imgHolder"src="http:\//webspace.webring.com/people/jv/vladilyich/preview.gif" class="preview" />');
    $('#addPostDiv').append('<input type="file" id="myFile" oninput="putFileInImgHolder()" accept="image/png, image/jpeg">');
    $('#addPostDiv').append('<br><label >Base64:</label> <input id=\'img64\' name="img64" placeholder="Post base64 img " /><br /><img id="base64holder"src="http:\//webspace.webring.com/people/jv/vladilyich/preview.gif" class="preview" />');
    $('#imgHolder').css({"max-width":"9em","max-height":"7em"});
    $('[name="thumbnail"]').on('change', function() {
    $('img.preview').prop('src', this.value); 
    });
    makeRatingBtn();
}

function makeReq(){
    var text=document.getElementById("textSection").value;
    var url=document.getElementById("url").value;
    var imgUrl=document.getElementById("ImgUrl").value;
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
    xhttp.open("POST", "addPostServlet", true); 
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    
    xhttp.send("text=" + text +"&url=" + url+"&imgUrl=" + imgUrl);
}

function CreateMapButton(){
    $('body').append('<button id = "find_location_btn" type="button" onclick="findLocation();">Find location</button>');

}


function makeRateReq(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById('status').innerHTML = xhr.responseText;
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };
    var postid = document.getElementById('postId').innerHTML;
    var rating = document.getElementById('RateSection').innerHTML;
    xhr.open("POST", "DeleteAccountServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("postid=" + postid + "&rating=" + rating);
}

