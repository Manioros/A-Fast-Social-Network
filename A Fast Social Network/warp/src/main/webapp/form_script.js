"use strict";

function checkPasswords() {
	var passw=document.getElementById('passw').value;
	var passwcon=document.getElementById('passwcon').value;
    if (passw != passwcon) {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'passwords not matching';
    } else {
        document.getElementById('message').style.color = 'green';
        document.getElementById('message').innerHTML = 'passwords matching';
    }
}


var location_list; //text returned by the GET requestem to nomatim
var query;
var location_object;
var map;
var longitude;
var latitude;

//makes a query of country+city+address
var make_query=function(){
    var country_menu = document.getElementById('country_menu');
    var country= country_menu.options[country_menu.selectedIndex].text;
    var city = document.getElementById('City').value;
    var address = document.getElementById('Address').value;

    //replace spaces with +
	address = address.replace(/\s+/g, '+');
	query = country.concat('+',city,'+',address);
};


/* event handler :AJAX request to nomatim ,
js object creation from json ,
lat-lon retrieval from object*/

var search_location=function(){

    make_query();

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {

        if (this.readyState == 4 && this.status == 200) {

            location_list = this.responseText;
            if(location_list==="[]") {
                document.getElementById('status').innerHTML = "location not found ";
            }else {
                document.getElementById('status').innerHTML = "location found ";
                $('#status').append(create_map_btn());
                location_object=JSON.parse(location_list);
                get_objects_lon_lat();
            }

        }

    };

    var request_string= 'https://nominatim.openstreetmap.org/search?q=';
    request_string = request_string.concat(query,'&format=json&polygon=1&addressdetails=1');

    xhttp.open('GET',request_string, true);
    xhttp.send();

};


function create_map_btn() {
    return $('<button/>', {text: 'see on map', id: 'map_button', click: create_map });

}

function get_objects_lon_lat(){
    //select the first location : location_object[0]
    longitude=location_object[0].lon;
    latitude=location_object[0].lat;
}


function findLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(geoloc_create_map, showError);
    } else {
        $("#status").innerHTML = "Geolocation is not supported by this browser.";
    }
}

function geoloc_create_map(position) {

    latitude=position.coords.latitude;
    longitude=position.coords.longitude;
    find_address();


}

function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            $("#status").innerHTML = "User denied the request for Geolocation.";
            break;
        case error.POSITION_UNAVAILABLE:
            $("#status").innerHTML = "Location information is unavailable.";
            break;
        case error.TIMEOUT:
            $("#status").innerHTML = "The request to get user location timed out.";
            break;
        case error.UNKNOWN_ERROR:
            $("#status").innerHTML = "An unknown error occurred.";
            break;
    }
}


function create_map() {

    $("#status").html("");
    $('<div/>', {id: 'map',  title: 'osm map',style:"height:30em; width:40em;"}).appendTo('#status');
    map = new OpenLayers.Map("map");
    map.addLayer(new OpenLayers.Layer.OSM());
    // WGS 1984 ==>Spherical Mercator Projection
    var lonLat = new OpenLayers.LonLat( longitude ,latitude ).transform(
        new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject());

    var zoom=16;

    var markers = new OpenLayers.Layer.Markers( "Markers" );
    map.addLayer(markers);

    markers.addMarker(new OpenLayers.Marker(lonLat));

    map.setCenter (lonLat, zoom);

}
function create_map_withLatLonParams(lat,lon) {

    $("#status").html("");
    $('<div/>', {id: 'map',  title: 'osm map',style:"height:15em; width:20em;"}).appendTo('#status');
    map = new OpenLayers.Map("map");
    map.addLayer(new OpenLayers.Layer.OSM());
    // WGS 1984 ==>Spherical Mercator Projection
    var lonLat = new OpenLayers.LonLat( lon ,lat ).transform(
        new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject());

    var zoom=16;

    var markers = new OpenLayers.Layer.Markers( "Markers" );
    map.addLayer(markers);

    markers.addMarker(new OpenLayers.Marker(lonLat));

    map.setCenter (lonLat, zoom);

}
var object_from_coords;

function find_address(){

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {

        if (this.readyState == 4 && this.status == 200) {

            if(location_list==="[]") {
                document.getElementById('status').innerHTML = "location not found ";

            }else {
                document.getElementById('status').innerHTML = "location found ";
                object_from_coords=JSON.parse(this.responseText);
                fill_form_fields();
                create_map();
            }
        }
    };
    var request_string= 'https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat='+latitude+'&lon='+longitude;
    xhttp.open('GET',request_string, true);
    xhttp.send();

}

function fill_form_fields(){
    console.log(object_from_coords);
    var country_menu=document.getElementById('counrty');
    country_menu.options[country_menu.selectedIndex].text=object_from_coords.address.country;
    document.getElementById('city').value=object_from_coords.address.city;
    document.getElementById('address').value=object_from_coords.address.road+object_from_coords.address.residential;

}



function handle_radiobtn_event() {
    window.location='/lq/regist_form_fcrec.html';
}



function getUserName(){
    var myData=document.getElementById('userName').value;
    window.localStorage.setItem( 'objectToPass', myData );

}


