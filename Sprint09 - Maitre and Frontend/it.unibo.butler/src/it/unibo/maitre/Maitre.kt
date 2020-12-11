/* Generated by AN DISI Unibo */ 
package it.unibo.maitre

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Maitre ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("Maitre | Ready")
					}
					 transition( edgeName="goto",targetState="waitCmdPrepare", cond=doswitch() )
				}	 
				state("waitCmdPrepare") { //this:State
					action { //it:State
					}
					 transition(edgeName="t034",targetState="exposeroomstate",cond=whenEvent("exposeroomstate_button"))
					transition(edgeName="t035",targetState="prepare",cond=whenEvent("prepare_button"))
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t136",targetState="add",cond=whenEvent("add_button"))
					transition(edgeName="t137",targetState="clear",cond=whenEvent("clear_button"))
					transition(edgeName="t138",targetState="stoprobot",cond=whenEvent("stop_button"))
				}	 
				state("exposeroomstate") { //this:State
					action { //it:State
						forward("expose", "expose(Cmd)" ,"robot" ) 
					}
					 transition( edgeName="goto",targetState="waitCmdPrepare", cond=doswitch() )
				}	 
				state("prepare") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("prepare_button(Cmd)"), Term.createTerm("prepare_button(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Maitre | PREPARE")
								updateResourceRep( "prepare"  
								)
								forward("prepare", "prepare(Cmd)" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("add") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("add_button(Foodcode,Quantity)"), Term.createTerm("add_button(Foodcode,Quantity)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Maitre | ADD")
								updateResourceRep( "add"  
								)
								forward("add", "add(${payloadArg(0)},${payloadArg(1)})" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("clear") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("clear_button(Cmd)"), Term.createTerm("clear_button(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("Maitre | CLEAR")
								updateResourceRep( "clear"  
								)
								forward("clear", "clear(Cmd)" ,"robot" ) 
						}
					}
					 transition( edgeName="goto",targetState="waitCmdPrepare", cond=doswitch() )
				}	 
				state("stoprobot") { //this:State
					action { //it:State
						println("Maitre | Requirement: STOP")
						updateResourceRep( "stoprobot"  
						)
						forward("stop", "stop(Action)" ,"robot" ) 
					}
					 transition(edgeName="t139",targetState="reactivaterobot",cond=whenEvent("reactivate_button"))
				}	 
				state("reactivaterobot") { //this:State
					action { //it:State
						println("Maitre | Requirement: REACTIVATE")
						updateResourceRep( "reactivaterobot"  
						)
						forward("reactivate", "reactivate(Action)" ,"robot" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
