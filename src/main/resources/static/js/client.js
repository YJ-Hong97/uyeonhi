/**
 * 
 */
 //open a web socket from javascript
const WebSocket = require('ws');
var socket = new WebSocket("ws://localhost:3000");

socket.onopen = function() {

    // Web Socket is connected, send datas to server
    socket.send("Message from user~~~~~하이 ");
    console.log("Message send to server");
 };

 socket.onmessage = function (evt) { 

     // handle messages from server
    var received_msg = evt.data;
    alert("Mesage from server = "+received_msg);
 };

 socket.onclose = function() { 

    // websocket is closed
    console.log("Websocket Connection is closed...");
 };