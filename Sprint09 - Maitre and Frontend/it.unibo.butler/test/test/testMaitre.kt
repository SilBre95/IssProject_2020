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

class testMaitre{

	var maitre : ActorBasic? = null
	var robot : ActorBasic? = null

	
	val addr       = "localhost:8038"
	val context    = "ctxrobot"

	
	val destactormaitre  = "maitre"
    val uriStrMaitre     = "coap://$addr/$context/$destactormaitre"
	val clientmaitre	 =  CoapClient()
	
	val destactorrobot  = "robot"
    val uriStrRobot     = "coap://$addr/$context/$destactorrobot"
	val clientrobot	    =  CoapClient()	

	
	val initDelayTime   = 2000L
	
	
	@Before
	fun systemSetUp() {
   		kotlin.concurrent.thread(start = true) {
			it.unibo.ctxrobot.main()
    	} 
	}
	
	@After
	fun terminate() {
		println("testmaitre terminated ")
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
	fun testmaitre(){
 		runBlocking{
			while(maitre==null || robot == null){
				delay(initDelayTime)  //give time to the actor to start

				maitre= it.unibo.kactor.sysUtil.getActor(destactormaitre)
				robot= it.unibo.kactor.sysUtil.getActor(destactorrobot)
			
			}
	 		clientmaitre.uri = uriStrMaitre
			clientrobot.uri = uriStrRobot

					
			 
			delay(500)
			val ev = MsgUtil.buildEvent("test", "prepare_button", "prepare_button(0)")
			MsgUtil.sendMsg(ev, maitre!!)
			delay(500)
			checkResource(clientmaitre, "prepare", "maitre")
			checkResource(clientrobot, "handlingprepare", "robot")
		
						
		}
	}
}