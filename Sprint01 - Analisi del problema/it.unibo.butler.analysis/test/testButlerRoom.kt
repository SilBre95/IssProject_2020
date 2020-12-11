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
	var user : ActorBasic? = null
		
	val initDelayTime   = 2000L   // 

	val addrrobot       = "localhost:8038"
	val contextrobot    = "ctxrobot"
	val destactorrobot  = "robot"
    val uriStrRobot     = "coap://$addrrobot/$contextrobot/$destactorrobot"
	val clientrobot		=  CoapClient()
	
	val addrfridge      = "localhost:8092"
	val contextfridge   = "ctxfridge"
	val destactorfridge = "fridge"
    val uriStrFridge    = "coap://$addrfridge/$contextfridge/$destactorfridge"
	val clientfridge	=  CoapClient()
	
	val addrmaitre       = "localhost:8094"
	val contextmaitre   = "ctxmaitre"
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
			
			//stop
			event = MsgUtil.buildEvent("test", "stop_button", "stop_button(0)")
	 		MsgUtil.sendMsg(event, maitre!!)
	 		delay(500)
			checkResource(clientmaitre, "stoprobot", "maitre")
			checkResource(clientrobot, "stopped", "robot")
			
			//reactivate
			event = MsgUtil.buildEvent("test", "reactivate_button", "reactivate_button(0)")
	 		MsgUtil.sendMsg(event, maitre!!)
	 		delay(500)
			checkResource(clientmaitre, "reactivaterobot", "maitre")
			checkResource(clientrobot, "reactivated", "robot")
			delay(2000)
			checkResource(clientrobot, "ended", "robot")
			delay(4000)
			
			//consult
			event = MsgUtil.buildEvent("test", "roomstate", "roomstate(0)")
	 		MsgUtil.sendMsg(event, maitre!!)
	 		delay(500)
			checkResource(clientmaitre, "consultroom", "maitre")
			delay(500)
					
			//add
			event = MsgUtil.buildEvent("test", "add_button", "add_button(pizza)")
	 		MsgUtil.sendMsg(event, maitre!!)
			delay(100)
			checkResource(clientmaitre, "add", "maitre")
			checkResource(clientrobot, "addstarted", "robot")
			checkResource(clientfridge, "checkfood", "fridge")
			delay(4000)
			
			//fridge
			MsgUtil.sendMsg( "test", "fridgestate", "fridgestate(0)", fridge!! ) 
	 		delay(500)
			checkResource(clientfridge, "exposestate", "fridge")
			delay(4000)
			
			//clear
			event = MsgUtil.buildEvent("test", "clear_button", "clear_button(0)")
	 		MsgUtil.sendMsg(event, maitre!!) 
	 		delay(500)
			checkResource(clientmaitre, "clear", "maitre")
			checkResource(clientrobot, "clearstarted", "robot")
			delay(2000)
			checkResource(clientrobot, "ended", "robot")	
				
		}
	}
	
}