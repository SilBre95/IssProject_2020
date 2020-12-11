import it.unibo.kactor.ActorBasic
import org.eclipse.californium.core.CoapClient
import org.junit.Before
import org.junit.After
import org.junit.Assert.assertTrue
import org.eclipse.californium.core.CoapResponse
import org.junit.Test
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import it.unibo.kactor.MsgUtil

class testClear{
	var robot  : ActorBasic? = null
	var robotexecutor : ActorBasic? = null
	var robotmover : ActorBasic? = null
	
	var user : ActorBasic? = null
	
	val addr       = "localhost:8038"
	val context    = "ctxrobot"
	
	val destactorrobot  = "robot"
    val uriStrRobot     = "coap://$addr/$context/$destactorrobot"
	val clientrobot		=  CoapClient()
	
	val destactorrobotexec  = "robotexecutor"
    val uriStrRobotexec     = "coap://$addr/$context/$destactorrobotexec"
	val clientrobotexec		=  CoapClient()
	
	val destactorrobotmover  = "robotmover"
    val uriStrRobotmover     = "coap://$addr/$context/$destactorrobotmover"
	val clientrobotmover	 =  CoapClient()
	
	val initDelayTime   = 2000L
	
	
	@Before
	fun systemSetUp() {
   		kotlin.concurrent.thread(start = true) {
			it.unibo.ctxrobot.main()
    	} 
	}
	
	@After
	fun terminate() {
		println("testClear terminated ")
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
	fun testClear(){
 		runBlocking{
			while( robot == null || robotmover==null || robotexecutor==null ){
				delay(initDelayTime)  //give time to the actor to start
				robot = it.unibo.kactor.sysUtil.getActor(destactorrobot)
				robotmover= it.unibo.kactor.sysUtil.getActor(destactorrobotmover)
				robotexecutor = it.unibo.kactor.sysUtil.getActor(destactorrobotexec)
				user = it.unibo.kactor.sysUtil.getActor("user")
			}
	 		clientrobot.uri = uriStrRobot
	 		clientrobotexec.uri = uriStrRobotexec
	 		clientrobotmover.uri = uriStrRobotmover
			
			//terminate user
			MsgUtil.sendMsg( "test", "test", "test(0)", user!! ) 
					
			//clear
			delay(500)
			MsgUtil.sendMsg("test", "prepare", "prepare(0)", robot!!)
			delay(10000)
		
	 		MsgUtil.sendMsg("test", "clear", "clear(0)", robot!!)
	 		delay(200)
			checkResource(clientrobot, "handlingclear", "robot")
			checkResource(clientrobotexec, "startclear", "robotexecutor")
			checkResource(clientrobotmover, "moving", "robotmover")
			
			delay(1000)
			checkResource(clientrobot, "handlingclear", "robot")
			checkResource(clientrobotexec, "attable", "robotexecutor")
			checkResource(clientrobotmover, "moving", "robotmover")
			
			delay(1000)
			checkResource(clientrobot, "handlingclear", "robot")
			checkResource(clientrobotexec, "atfridge", "robotexecutor")
			checkResource(clientrobotmover, "moving", "robotmover")
			
			delay(1000)
			checkResource(clientrobot, "handlingclear", "robot")
			checkResource(clientrobotexec, "attable", "robotexecutor")
			checkResource(clientrobotmover, "moving", "robotmover")
			
			delay(1000)
			checkResource(clientrobot, "handlingclear", "robot")
			checkResource(clientrobotexec, "atdishwasher", "robotexecutor")
			checkResource(clientrobotmover, "moving", "robotmover")
						
			delay(1000)
			checkResource(clientrobot, "handlingclear", "robot")
			checkResource(clientrobotexec, "athome", "robotexecutor")
			checkResource(clientrobotmover, "notmoving", "robotmover")
		}
	}
}