/*
* =====================================
* frontend/uniboSupports/mqttUtils.js
* =====================================
*/
const mqtt   = require ('mqtt');  //npm install --save mqtt
const topic  = "unibo/qak/events";

var mqttAddr = 'mqtt://localhost'
//var mqttAddr = 'mqtt://192.168.43.229'
//var mqttAddr = 'mqtt://iot.eclipse.org'

var client   = mqtt.connect(mqttAddr);
var io  ; 	//Upgrade for socketIo;
var robotModel    = "none";
var sonarModel    = "none";
var roomMapModel  = "none";

console.log("mqtt client= " + client );

exports.setIoSocket = function ( iosock ) {
 	io    = iosock;
	console.log("mqtt SETIOSOCKET io=" + io);
}


client.on('connect', function () {
	  client.subscribe( topic );
	  console.log('client has connected successfully with ' + mqttAddr);
});

//The message usually arrives as buffer, so I had to convert it to string data type;
client.on('message', function (topic, message){

var msgStr = message.toString();

var msg = "";

if(msgStr.indexOf("consultKb") >= 0){
	var arg = msgStr.split("content(");
	var content = arg[1].split("))", 1);
	var consultKb = content[0].split("(")[1];

/*	if(arg[1].indexOf("fridge") >= 0){ 
		coap.coapGet("expose");
	}*/

	var socketChannel = arg[1].indexOf("pantry") >= 0 ? 'pantrySocket'
			: arg[1].indexOf("dishwasher") >= 0 ? 'dishwasherSocket'
			: arg[1].indexOf("robot") >= 0 ? 'robotSocket'
			: arg[1].indexOf("table") >= 0 ? 'tableSocket'
			: arg[1].indexOf("fridge") >= 0 ? 'fridgeSocket'
			: "NON IDENTIFICATO";

	console.log("mqtt send on io.sockets| socketChannel = " + socketChannel + " consultKb = " + consultKb);  
	io.sockets.emit(socketChannel, consultKb);
}
else if(msgStr.indexOf("content") >= 0){
	var arg = msgStr.indexOf("taskCompleted");
	var msgStr = msgStr.substr(arg);
	var sp2 = msgStr.indexOf("))");	

	var content = message.toString().substr(arg,sp2+1);//taskCompleted(systemStarted)
	
	//taskCompleted(systemStarted)
	if(arg > 0) {
		msg = content === "taskCompleted(systemStarted)" ? "systemStarted: "
			: content === "taskCompleted(endPrepare)" ? "endPrepare: "
			: content === "taskCompleted(foodAdded)" ? "foodAdded: "
			: content === "taskCompleted(warning)" ? "warning: "
			: content === "taskCompleted(not_warning)" ? "not_warning: "
			: content === "taskCompleted(endClear)" ? "endClear: "
			: content === "taskCompleted(endAction)" ? "endAction: "
			: "TASK NON IDENTIFICATO: ";
	} 

	//systemStarted: taskCompleted(systemStarted) | TASK NON IDENTIFICATO: taskCompleted(x)
	msg = msg + content;

	console.log("mqtt send on io.sockets| msg = "+ msg  + " content = " + content);  
	io.sockets.send(msg);
}
else if(msgStr.indexOf("goal") >= 0){	//goal:pantry(0,5)

	var arg = msgStr.indexOf(":");
	var msgStr = msgStr.substr(arg+1);
	msg = "goal: " + msgStr;
	var sp = msg.split("'");
	var x = sp[0].substring(0, sp[0].length - 1);
	console.log("mqtt send on io.sockets| msg = " +  x);  
	io.sockets.send(x);
}
else if(msgStr.indexOf("task") >= 0){	//task:PREPARE	
	var arg = msgStr.indexOf(":");
	var newString = msgStr.substr(arg+1);
	msg = "task: " + newString;
	var sp = msg.split("'");
	console.log("mqtt send on io.sockets| msg = " +  sp[0]);  
	io.sockets.send(sp[0]);
}
else if(msgStr.indexOf("currentPosition") >= 0){
	var arg = msgStr.indexOf(":");
	var newString = msgStr.substr(arg+1);
	var sp = newString.split(")");
	msg = "robotPosition: " + sp[0];

	console.log("mqtt send on io.sockets| msg = " +  sp[0]);  
	io.sockets.send(msg);
}
});


exports.publish = function( msg, topic ){
	//console.log('mqtt publish ' + client);
	client.publish(topic, msg);
}

exports.getrobotmodel = function(){
	return robotModel;
}
exports.getsonarrobotmodel = function(){
	return sonarModel;
}