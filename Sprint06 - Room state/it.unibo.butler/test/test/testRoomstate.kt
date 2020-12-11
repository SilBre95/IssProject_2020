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

class testRoomstate{
	var robot  : ActorBasic? = null
	var robotexecutor : ActorBasic? = null
	var robotmover : ActorBasic? = null
	var roomstate : ActorBasic? = null
	
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
	
	val destactorroomstate  = "roomstate"
    val uriStrroomstate     = "coap://$addr/$context/$destactorroomstate"
	val clientroomstate	 =  CoapClient()
	
	val initDelayTime   = 2000L
	
	
	@Before
	fun systemSetUp() {
   		kotlin.concurrent.thread(start = true) {
			it.unibo.ctxrobot.main()
    	} 
	}
	
	@After
	fun terminate() {
		println("testRoomstate terminated ")
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
	fun teststate(){
 		runBlocking{
			while( robot == null || robotmover==null || robotexecutor==null ){
				delay(initDelayTime)  //give time to the actor to start
				robot = it.unibo.kactor.sysUtil.getActor(destactorrobot)
				robotmover= it.unibo.kactor.sysUtil.getActor(destactorrobotmover)
				robotexecutor = it.unibo.kactor.sysUtil.getActor(destactorrobotexec)
				roomstate = it.unibo.kactor.sysUtil.getActor(destactorroomstate)
				user = it.unibo.kactor.sysUtil.getActor("user")
			}
	 		clientrobot.uri = uriStrRobot
	 		clientrobotexec.uri = uriStrRobotexec
	 		clientrobotmover.uri = uriStrRobotmover
			clientroomstate.uri = uriStrroomstate
			
			//terminate user
			MsgUtil.sendMsg( "test", "test", "test(0)", user!! ) 
					
			//prepare
			delay(500)
			MsgUtil.sendMsg("test", "prepare", "prepare(0)", robot!!)
			delay(1000)
			checkResource(clientroomstate, "updateroomstate", "roomstate")
			delay(10000)
			
			var req = MsgUtil.buildRequest( "test", "exposeroomstate", "exposeroomstate(0)", "roomstate" )
			MsgUtil.sendMsg(req, roomstate!!) 
	 		delay(500)
			checkResource(clientroomstate, "exposeroomstate", "roomstate")
			
						
		}
	}
}