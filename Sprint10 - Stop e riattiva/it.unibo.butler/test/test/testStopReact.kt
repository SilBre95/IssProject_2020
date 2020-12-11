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

class testStopReact{

	var robot : ActorBasic? = null
	var robotmover : ActorBasic? = null

	
	val addr       = "localhost:8038"
	val context    = "ctxrobot"

	
	val destactorrobot  = "robot"
    val uriStrRobot     = "coap://$addr/$context/$destactorrobot"
	val clientrobot	 =  CoapClient()
	
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
		println("teststop terminated ")
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
	fun testmover(){
 		runBlocking{
			while(robot==null || robotmover==null){
				delay(initDelayTime)  //give time to the actor to start

				robotmover= it.unibo.kactor.sysUtil.getActor(destactorrobotmover)
				robot= it.unibo.kactor.sysUtil.getActor(destactorrobot)
			
			}
	 	
	 		clientrobotmover.uri = uriStrRobotmover
			clientrobot.uri = uriStrRobot
			

			delay(1000)
			MsgUtil.sendMsg("test", "prepare", "prepare(0)", robot!!)
			delay(2000)
			
			MsgUtil.sendMsg("test", "stop", "stop(0)", robot!!)
			delay(500)
			checkResource(clientrobot, "stopped", "robot")
			
			MsgUtil.sendMsg("test", "reactivate", "reactivate(0)", robot!!)
			delay(500)
			checkResource(clientrobot, "reactivated", "robot")
								
		}
	}
}