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

class testmover{

	var robotmover : ActorBasic? = null

	
	var user : ActorBasic? = null
	
	val addr       = "localhost:8038"
	val context    = "ctxrobot"

	
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
		println("testmover terminated ")
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
	fun testm(){
			
 		runBlocking{
			
			while(robotmover==null  ){
				delay(initDelayTime)  //give time to the actor to start

				robotmover= it.unibo.kactor.sysUtil.getActor(destactorrobotmover)
				user = it.unibo.kactor.sysUtil.getActor("user")
				
			}
	 	
	 		clientrobotmover.uri = uriStrRobotmover
			
			
			//terminate user
			MsgUtil.sendMsg( "test", "test", "test(0)", user!! ) 
			
			//goto 
			delay(500)
			var req = MsgUtil.buildRequest("test", "goto", "goto(fridge)", "robotmover")
			MsgUtil.sendMsg(req, robotmover!!)
			delay(10000)
			assertTrue( itunibo.planner.plannerUtil.getPosX() == 5)
			assertTrue( itunibo.planner.plannerUtil.getPosY() == 0)
			checkResource(clientrobotmover, "notmoving", "robotmover")
			
			req = MsgUtil.buildRequest("test", "goto", "goto(casamia)", "robotmover")
			MsgUtil.sendMsg(req, robotmover!!)
			checkResource(clientrobotmover, "notmoving", "robotmover")
		
						
		}
	}
}