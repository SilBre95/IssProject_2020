/*
frontend/uniboSupports/applCode
*/
const express     	= require('express');
const path         	= require('path');
//const favicon     = require('serve-favicon');
const logger       	= require('morgan');	//see 10.1 of nodeExpressWeb.pdf;
//const cookieParser= require('cookie-parser');
const bodyParser   	= require('body-parser');
const fs           	= require('fs');
const index         = require('./appServer/routes/index');				 
var io              ; 	//Upgrade for socketIo;

//for delegate
const mqttUtils     = require('./uniboSupports/mqttUtils');  
const coap          = require('./uniboSupports/coapClientToResourceModel');  
//require("node-coap-client").CoapClient; 

var app              = express();
var patternFoodCode = new RegExp("f[0-9]{3}$");

// view engine setup;
app.set('views', path.join(__dirname, 'appServer', 'views'));	 
app.set('view engine', 'ejs');

//create a write stream (in append mode) ;
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'morganLog.log'), {flags: 'a'})
app.use(logger("short", {stream: accessLogStream}));

//Creates a default route. Overloads app.use('/', index);
//app.get("/", function(req,res){ res.send("Welcome to frontend Server"); } );

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));				//shows commands, e.g. GET /pi 304 23.123 ms - -;
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
//app.use(cookieParser());

app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'jsCode'))); //(***)

//DEFINE THE ROUTES ;
//app.use('/', index);		 

//Creates a default route for /pi;
app.get('/info', function (req, res) {
  res.send('This is the frontend created by Silvia and Giulia!')
});

app.get('/', function(req, res) {
	res.render("start");
});	


/*
 * ====================== COMMANDS ================
 */

app.post("/addfood", function (req, res, next) {
    console.log("\nPremuto il pulsante ADD");
   
    r = req.body.foodcode;
    if(!patternFoodCode.test(r)){
        console.log("Foodcode has to start with 'f' and contain 3 numbers");       
        }
    else{
    rest = req.body.foodcode +",1";
    publishEmitEvent("add_button", rest);
    }
    res.status(204).send(); //per rimanere nella stessa pagina dopo un click
});

app.post("/prepare", function (req, res, next) {
    console.log("\nPremuto il pulsante PREPARE");
    publishEmitEvent("prepare_button","X");    
    res.status(204).send(); //per rimanere nella stessa pagina dopo un click
});
app.post("/clear", function (req, res, next) {
    console.log("\nPremuto il pulsante CLEAR");
    publishEmitEvent("clear_button","X"); 
    res.status(204).send(); //per rimanere nella stessa pagina dopo un click
});
app.post("/expose", function (req, res, next) {
    console.log("\nPremuto il pulsante EXPOSE");
    res.status(204).send(); //per rimanere nella stessa pagina dopo un click
});
app.post("/start", function (req, res, next) {
	publishEmitEvent("reactivate_button","X");
    console.log("\nPremuto il pulsante START");
    res.status(204).send(); //per rimanere nella stessa pagina dopo un click
});
app.post("/stop", function (req, res, next) {
	publishEmitEvent("stop_button","X");
    console.log("\nPremuto il pulsante STOP");
    res.status(204).send(); //per rimanere nella stessa pagina dopo un click
});

app.post("/startAppl", function (req, res, next) {
    console.log("\nPremuto il pulsante STARTAPPL");	
	setTimeout(function(){
		publishEmitEvent("exposeroomstate_button","X"); 
	}, 1000);	
	res.render("index");
	next();
});

app.post("/request", function (req, res, next) {
    console.log("\nPremuto il pulsante REQUEST");
    r = req.body.foodcode;
    console.log(r);
    if(!patternFoodCode.test(r)){
        console.log("Foodcode has to start with 'f' and contain 3 numbers");       
        }
    else{
		coapToFridge("check_food("+ r +",1)");
	}
	res.status(204).send(); //per rimanere nella stessa pagina dopo un click
    });

//=================== UTILITIES =========================


var result = "";

app.setIoSocket = function( iosock ){
 	io    = iosock;
 	mqttUtils.setIoSocket(iosock);
	console.log("app SETIOSOCKET io=" + io);
}

app.getIoSocket = function(){
	return io;
}



function delegate( cmd, newState, req, res ){
 	publishMsgToRobotmind(cmd);                  //interaction with the robotmind 
	publishEmitUserCmd(cmd);                     //interaction with the basicrobot
	//publishMsgToResourceModel("robot",cmd);	    //for hexagonal mind
	changeResourceModelCoap(cmd);		            //for hexagonal mind RESTful m2m
 } 
function delegateForAppl( cmd, req, res  ){
     console.log("app delegateForAppl cmd=" + cmd); 
     result = "Web server delegateForAppl: " + cmd;
 	 publishMsgToRobotapplication( cmd );		     
} 



/*
 * ============ TO THE BUSINESS LOGIC =======
 */

var coapToFridge = function(cmd){
	console.log("coap PUT > "+ cmd);
	coap.coapPut(cmd);	
}

var publishEmitEvent = function(ev, evContent){
	var eventstr = evContent !== ""
		? "msg("+ev+",event,js,none,"+ev+"("+evContent+"),1)"
		: "msg("+ev+",event,js,none,"+ev+",1)";

    console.log("publishEmitEvent emits > "+ eventstr);
	mqttUtils.publish(eventstr, "unibo/qak/events");	 
}

/*
* ====================== REPRESENTATION ================
*/
app.use( function(req,res){
	console.info("SENDING THE ANSWER " + result + " json:" + req.accepts('josn') );
	try{
	    console.log("answer> "+ result  );
	}catch(e){console.info("SORRY ..." + e);}
	} 
);

//app.use(converter());

/*
 * ============ ERROR HANDLING =======
 */

// catch 404 and forward to error handler;
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler;
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page;
  res.status(err.status || 500);
  res.render('error');
});

/*
 * ========= EXPORTS =======
 */

module.exports = app;