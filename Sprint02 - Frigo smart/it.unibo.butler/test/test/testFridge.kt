package test

import it.unibo.kactor.ActorBasic
import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertTrue
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay
import org.junit.After
import org.junit.Before

class testFridge {
	var robot  : ActorBasic? = null
	var fridge : ActorBasic? = null
	var maitre : ActorBasic? = null
	var user : ActorBasic? = null
		
	val initDelayTime   = 2000L   // 

	val addrrobot       = "localhost:8038"
	val contextrobot    = "ctxrobot"
	val destactorrobot  = "robot"
    val uriStrRobot     = "coap://$addrrobot/$contextrobot/$destactorrobot"
	val clientrobot		=  CoapClient()
	
	val addrfridge      = "localhost:8038"
	val contextfridge   = "ctxrobot"
	val destactorfridge = "fridge"
    val uriStrFridge    = "coap://$addrfridge/$contextfridge/$destactorfridge"
	val clientfridge	=  CoapClient()
	
	val addrmaitre      = "localhost:8038"
	val contextmaitre   = "ctxrobot"
	val destactormaitre = "maitre"
    val uriStrMaitre    = "coap://$addrmaitre/$contextmaitre/$destactormaitre"
	val clientmaitre	=  CoapClient()
	
	
	@Before
	fun systemSetUp() {
   		kotlin.concurrent.thread(start = true) {
			it.unibo.ctxrobot.main()
    	} 
	}
	
	@After
	fun terminate() {
		println("testFridge terminated ")
	}
	
	fun checkResource(client: CoapClient, value: String, actor: String){
		val respGet : CoapResponse = client.get( )
		val v = respGet.getResponseText()
		println("	checkResource $actor |  $v value=$value ")
 		assertTrue( v == value)
	}

	
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi	
	@Test
	fun testFridge(){
 		runBlocking{
			while( robot == null || fridge==null || maitre==null ){
				delay(initDelayTime)  //give time to the actor to start
				robot = it.unibo.kactor.sysUtil.getActor(destactorrobot)
				fridge = it.unibo.kactor.sysUtil.getActor(destactorfridge)
				maitre = it.unibo.kactor.sysUtil.getActor(destactormaitre)
				user = it.unibo.kactor.sysUtil.getActor("user")
			}
	 		clientrobot.uri = uriStrRobot
	 		clientfridge.uri = uriStrFridge
	 		clientmaitre.uri = uriStrMaitre
			
			//terminate user
			MsgUtil.sendMsg( "test", "test", "test(0)", user!! ) 
					
			//prepare
			delay(500)
			var event = MsgUtil.buildEvent("test", "prepare_button", "prepare_button(0)")
	 		MsgUtil.sendMsg(event, maitre!!)
	 		delay(500)
			checkResource(clientmaitre, "prepare", "maitre")
			checkResource(clientrobot, "preparestarted", "robot")
			delay(4000)
					
			//add
			event = MsgUtil.buildEvent("test", "add_button", "add_button(pizza,4)")
	 		MsgUtil.sendMsg(event, maitre!!)
			delay(100)
			checkResource(clientmaitre, "add", "maitre")
			checkResource(clientrobot, "addstarted", "robot")
			checkResource(clientfridge, "checkfood", "fridge")
			delay(4000)
			
			//fridge
			var req = MsgUtil.buildRequest( "test", "exposefridgestate", "exposefridgestate(0)", "fridge" )
			MsgUtil.sendMsg(req, fridge!!) 
	 		delay(500)
			checkResource(clientfridge, "exposestate", "fridge")
			delay(4000)
			
			//take
			MsgUtil.sendMsg( "test", "take_food", "take_food(pizza,4)", fridge!! )
			delay(500)
			checkResource(clientfridge, "takefood", "fridge")
			delay(4000)
			
			//fridge
			req = MsgUtil.buildRequest( "test", "exposefridgestate", "exposefridgestate(0)", "fridge" )
			MsgUtil.sendMsg(req, fridge!!)
	 		delay(500)
			checkResource(clientfridge, "exposestate", "fridge")
			delay(4000)
				
		}
	}
}