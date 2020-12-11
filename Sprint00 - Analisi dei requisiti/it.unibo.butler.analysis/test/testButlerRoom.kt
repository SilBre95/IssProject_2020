import it.unibo.kactor.ActorBasic
import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapResponse
import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil

class testButlerRoom {
	var robot  : ActorBasic? = null
	var fridge : ActorBasic? = null
	var maitre : ActorBasic? = null
		
	val initDelayTime   = 2000L   // 

	val context     	= "ctxroom"
	val addr       		= "localhost:8038"
	
	val destactorrobot  = "robot"
    val uriStrRobot     = "coap://$addr/$context/$destactorrobot"
	val clientrobot		=  CoapClient()
	
	val destactorfridge = "fridge"
    val uriStrFridge    = "coap://$addr/$context/$destactorfridge"
	val clientfridge	=  CoapClient()
	
	val destactormaitre = "maitre"
    val uriStrMaitre    = "coap://$addr/$context/$destactormaitre"
	val clientmaitre	=  CoapClient()
	
	@Before
	fun systemSetUp() {
   		kotlin.concurrent.thread(start = true) {
			it.unibo.ctxroom.main() 
    	} 
	}
	
	@After
	fun terminate() {
		println("testButlerRoom terminated ")
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
	fun testButlerRoom(){
 		runBlocking{
			while( robot == null || fridge==null || maitre==null ){
				delay(initDelayTime)  //give time to the actor to start
				robot = it.unibo.kactor.sysUtil.getActor(destactorrobot)
				fridge = it.unibo.kactor.sysUtil.getActor(destactorfridge)
				maitre = it.unibo.kactor.sysUtil.getActor(destactormaitre) 
			}
	 		clientrobot.uri = uriStrRobot
	 		clientfridge.uri = uriStrFridge
	 		clientmaitre.uri = uriStrMaitre
			println("testPrepare |  $robot | $maitre")
					
			//prepare
			delay(500)
	 		MsgUtil.sendMsg( "test", "prepare", "prepare(0)", maitre!!)
	 		delay(500)
			checkResource(clientmaitre, "prepare", "maitre")
			checkResource(clientrobot, "preparestarted", "robot")
			
			//stop
			MsgUtil.sendMsg( "test", "stop", "stop(0)", maitre!! ) 
	 		delay(500)
			checkResource(clientmaitre, "stoprobot", "maitre")
			checkResource(clientrobot, "stopped", "robot")
			
			//reactivate
	 		MsgUtil.sendMsg( "test", "reactivate", "reactivate(0)", maitre!! ) 
	 		delay(500)
			checkResource(clientmaitre, "reactivaterobot", "maitre")
			checkResource(clientrobot, "reactivated", "robot")
			delay(2000)
			checkResource(clientrobot, "ended", "robot")
			delay(4000)
			
			//consult
			MsgUtil.sendMsg( "test", "roomstate", "roomstate(0)", maitre!! ) 
	 		delay(500)
			checkResource(clientmaitre, "consultroom", "maitre")
			delay(500)
					
			//add
	 		MsgUtil.sendMsg( "test", "add", "add(0)", maitre!! ) 
	 		delay(500)
			checkResource(clientmaitre, "add", "maitre")
			checkResource(clientrobot, "addstarted", "robot")
			delay(4000)
			
			//fridge
			MsgUtil.sendMsg( "test", "check_food", "check_food(0)", fridge!! ) 
	 		delay(500)
			checkResource(clientfridge, "checkfood", "fridge")
			delay(500)
			MsgUtil.sendMsg( "test", "fridgestate", "fridgestate(0)", fridge!! ) 
	 		delay(500)
			checkResource(clientfridge, "exposestate", "fridge")
			delay(4000)
			
			//clear
	 		MsgUtil.sendMsg( "test", "clear", "clear(0)", maitre!! ) 
	 		delay(500)
			checkResource(clientmaitre, "clear", "maitre")
			checkResource(clientrobot, "clearstarted", "robot")
			delay(2000)
			checkResource(clientrobot, "ended", "robot")	
				
		}
	}
	
}