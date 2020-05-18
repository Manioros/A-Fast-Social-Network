'use strict';

/*
    Author: Panagiotis Papadakos papadako@csd.uoc.gr

    Try to read this file and understand what the code does...
    Then try to complete the missing functionality
    
    For the needs of the hy359 2018 course
    University of Crete

*/

/*  face recognition that is based on faceplusplus service */
var faceRec = (function () {
    var userName;
    // Object that holds anything related with the facetPlusPlus REST API Service
    var faceAPI = {
        apiKey: 'l2jNgKbk1HXSR4vMzNygHXx2g8c_xT9c',
        apiSecret: '2T6XdZt4EYw-I7OhmZ6g1wtECl81e_Ip',
        app: 'hy359',
        // Detect
        // https://console.faceplusplus.com/documents/5679127
        detect: 'https://api-us.faceplusplus.com/facepp/v3/detect',  // POST
        // Set User ID
        // https://console.faceplusplus.com/documents/6329500
        setuserId: 'https://api-us.faceplusplus.com/facepp/v3/face/setuserid', // POST
        // Get User ID
        // https://console.faceplusplus.com/documents/6329496
        getDetail: 'https://api-us.faceplusplus.com/facepp/v3/face/getdetail', // POST
        // addFace
        // https://console.faceplusplus.com/documents/6329371
        addFace: 'https://api-us.faceplusplus.com/facepp/v3/faceset/addface', // POST
        // Search
        // https://console.faceplusplus.com/documents/5681455
        search: 'https://api-us.faceplusplus.com/facepp/v3/search', // POST
        // Create set of faces
        // https://console.faceplusplus.com/documents/6329329
        create: 'https://api-us.faceplusplus.com/facepp/v3/faceset/create', // POST
        // update
        // https://console.faceplusplus.com/documents/6329383
        update: 'https://api-us.faceplusplus.com/facepp/v3/faceset/update', // POST
        // removeface
        // https://console.faceplusplus.com/documents/6329376
        removeFace: 'https://api-us.faceplusplus.com/facepp/v3/faceset/removeface', // POST
    };

    // Object that holds anything related with the state of our app
    // Currently it only holds if the snap button has been pressed
    var state = {
        photoSnapped: false,
    };

    // function that returns a binary representation of the canvas
    function getImageAsBlobFromCanvas(canvas) {

        // function that converts the dataURL to a binary blob object
        function dataURLtoBlob(dataUrl) {
            // Decode the dataURL
            var binary = atob(dataUrl.split(',')[1]);

            // Create 8-bit unsigned array
            var array = [];
            for (var i = 0; i < binary.length; i++) {
                array.push(binary.charCodeAt(i));
            }

            // Return our Blob object
            return new Blob([new Uint8Array(array)], {
                type: 'image/jpg',
            });
        }

        var fullQuality = canvas.toDataURL('image/jpeg', 1.0);
        return dataURLtoBlob(fullQuality);

    }

    // function that returns a base64 representation of the canvas
    function getImageAsBase64FromCanvas(canvas) {
        // return only the base64 image not the header as reported in issue #2
        return canvas.toDataURL('image/jpeg', 1.0).split(',')[1];
    }

    // Function called when we upload an image
    function uploadImage() {
        if (state.photoSnapped ) {
            var canvas = document.getElementById('canvas');
            var image = getImageAsBlobFromCanvas(canvas);

            // TODO!!! Well this is for you ... YES you!!!
            // Good luck!
            // Create Form Data. Here you should put all data
            // requested by the face plus plus services and
            // pass it to ajaxRequest
            var data = new FormData();
            data.append('api_key', faceAPI.apiKey);
            data.append('api_secret', faceAPI.apiSecret);
            data.append('image_file', image);
            data.append('outer_id',faceAPI.app);
            // add also other query parameters based on the request
            // you have to send

            // You have to implement the ajaxRequest. Here you can
            // see an example of how you should call this
            // First argument: the HTTP method
            // Second argument: the URI where we are sending our request
            // Third argument: the data (the parameters of the request)
            // ajaxRequest function should be general and support all your ajax requests...
            // Think also about the handler

            ajaxRequest('POST', faceAPI.detect, data,setuserIdFunction);

        } else {
            alert('No image has been taken!');
        }
    }



    // Function for initializing things (event handlers, etc...)
    function init() {
        alert("i got called");
        // Put event listeners into place
        // Notice that in this specific case handlers are loaded on the onload event
        window.addEventListener('DOMContentLoaded', function () {
            // Grab elements, create settings, etc.
            var canvas = document.getElementById('canvas');
            var context = canvas.getContext('2d');
            var video = document.getElementById('video');
            var mediaConfig = {
                video: true,
            };
            var errBack = function (e) {
                console.log('An error has occurred!', e);
            };

            // Put video listeners into place
            if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
                navigator.mediaDevices.getUserMedia(mediaConfig).then(function (stream) {
                    video.srcObject = stream;
                    video.onloadedmetadata = function (e) {
                        video.play();
                    };
                });
            }

            // Trigger photo take
            document.getElementById('snap').addEventListener('click', function () {
                context.drawImage(video, 0, 0, 640, 480);
                state.photoSnapped = true; // photo has been taken
            });

            // Trigger when upload button is pressed
            document.getElementById('upload').addEventListener('click', uploadImage);

            //triger when radio button "auto recognition" is pressed
            document.getElementById('radio2').addEventListener('click', face_handle);

        }, false);

        userName=window.localStorage.getItem('objectToPass');
    }

    // !!!!!!!!!!! ================ TODO  ADD YOUR CODE HERE  ====================
    // From here on there is code that should not be given....

    // You have to implement the ajaxRequest function!!!!

    function ajaxRequest(requestMethod, url, data, callback) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var JsonObj=JSON.parse(this.responseText);
                callback(JsonObj,data);
            }
        };
        xhttp.open(requestMethod, url, false);
        xhttp.send(data);
    }

    function setuserIdFunction(detectReqResponce,data){
        console.log(detectReqResponce);
        //console.log(JsonObj.faces[0].face_token);
        data.append('face_token',detectReqResponce.faces[0].face_token);
        data.append('user_id',userName);
        ajaxRequest('POST', faceAPI.setuserId, data,getDetailFunction);

    }

    function getDetailFunction(setUserIdResponse,data){
        console.log(setUserIdResponse);
        ajaxRequest('POST', faceAPI.getDetail, data,addFaceFunction);
    }

    function addFaceFunction(getDetailResponse,data){
        console.log(getDetailResponse);
        //data.append('outer_id',faceAPI.app);
        ajaxRequest('POST', faceAPI.getDetail, data,printFunction);
    }


    function printFunction(response,data){
        console.log(response);
    }


    function face_handle(){
        var canvas = document.getElementById('canvas');
        var image = getImageAsBlobFromCanvas(canvas);
        var data = new FormData();
        data.append('api_key', faceAPI.apiKey);
        data.append('api_secret', faceAPI.apiSecret);
        data.append('image_file', image);
        data.append('outer_id',faceAPI.app);
        ajaxRequest('POST', faceAPI.search, data,getUserName);
    }

    function getUserName(searchResponse,data){
        console.log(searchResponse);
        document.getElementById('userName').value= searchResponse.results[0].user_id;
    }

    // !!!!!!!!!!! =========== END OF TODO  ===============================

    // Public API of function for facet recognition
    // You might need to add here other methods based on your implementation
    return {
        init: init,face_handle
    };

})();

