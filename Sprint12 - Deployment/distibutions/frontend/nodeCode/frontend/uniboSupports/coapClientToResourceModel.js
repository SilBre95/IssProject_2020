/*
frontend/uniboSupports/coapClientToResourceModel
*/
var app = require('../applCode');  //previously was app;
const handle           = require('./qakeventHandler');  
const coap             = require("node-coap-client").CoapClient; 
//var coapAddr           = "coap://192.168.1.8:5683"	//RESOURCE ON RASPBERRY PI

var coapAddr = "coap://localhost:8038/ctxfridge/fridge"
var coapResourceAddr = coapAddr + "/ctxfridge/fridge"

	exports.setcoapAddr = function(addr){
		coapAddr = "coap://"+ "localhost" + ":8038";
		coapResourceAddr = coapAddr + "/ctxfridge/fridge"
		console.log("coap coapResourceAddr " + coapResourceAddr);
	}



exports.coapGet = function (  ){
	
	coap
	    .request(
	    	coapResourceAddr,
	        "get", 		/* "get" | "post" | "put" | "delete" */
	    	//new Buffer(cmd)
 	        //[payload 	/* Buffer */,
	        //[options 	/* RequestOptions */]]
	    )
	    .then(response => {
			/* handle response */
			console.log("coap get done > " + response.payload);
			//var expose = response.payload.toString().split(":")[1];
			app.getIoSocket().emit('expose', expose);
		})
	    .catch(err => {
			/* handle error */ 
			console.log("coap get error> " + err );
		});
}//coapGet

exports.coapPut = function (cmd ){ 
	coap
	    .request(
	    	coapResourceAddr,     
	        "put" , 		// "get" | "post" | "put" | "delete"   
	        new Buffer("msg(check_food,request,js,fridge,"+cmd+",1)") // payload Buffer 
 	        //[options]]	// RequestOptions 
	    )
	    .then(response => {
			/* handle response */  
			console.log("coap put done > " + cmd);
			console.log("response: " + response.payload);
			if(response.payload.toString().includes("food_available")){
				app.getIoSocket().emit('request', "yes");
			}
			else{
				app.getIoSocket().emit('request', "no");
			}
		})
	    .catch(err => {
			/* handle error */
			console.log("coap put error> " + err + " for cmd=" + cmd);
		});
}//coapPut

const myself   = require('./coapClientToResourceModel');

function test(){
 	//console.log("GET");
  	myself.coapGet();
 	//console.log("PUT");
 	myself.coapPut("r")
 	myself.coapGet();
}

//test()
 

/*
 * ========= EXPORTS =======
 */

//module.exports = coap;
