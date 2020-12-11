package utils.utilsFrontend

import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasicFsm
import it.unibo.kactor.ActorBasic


	fun updateFrontend( actor: ActorBasicFsm, content: String ){
		var outS = ""
		var contentEmit = content
		when(content){
			"systemStarted" -> outS = "content(taskCompleted(systemStarted))" 
			//"endPrepare"  	-> outS = "content(taskCompleted(endPrepare))"
			//"foodAdded"   	-> outS = "content(taskCompleted(foodAdded))"
			"warning"     	-> outS = "content(taskCompleted(warning))"
			"food_notavailable" -> outS = "content(taskCompleted(warning))"
			//"endClear"    	-> outS = "content(taskCompleted(endClear))"
			"endTask"    	-> outS = "content(taskCompleted(endAction))"
			 else 			-> {
				outS = "content(" + content + ")"
				contentEmit = "consultKb"
			 }
		}
			//contentEmit = "consultKb"
		println("frontendSupport updateFrontend content=$content outS=$outS contentEmit=$contentEmit")
		actor.scope.launch{
			actor.emit(contentEmit, outS) //NOTA: il frontend riceve 6 eventi uguali
		}	
	}

	fun updateGoalToFrontend( actor: ActorBasicFsm, content: String ){
		println("frontendSupport updateGoalToFrontend content = $content")
		actor.scope.launch{
			actor.emit("goal", content)
		}	
	}

	fun updateTaskFrontend( actor: ActorBasicFsm, content: String ){
//		val Cont = content.replace("(","").replace(")","")
		val Cont = ":" + content
		println("frontendSupport updateTaskFrontend content = $Cont")
		actor.scope.launch{
			actor.emit("task", Cont)
		}	
	}

	fun updatePositionFrontend( actor: ActorBasicFsm, content: String ){
		val Cont = ":" + content
		println("frontendSupport updatePositionFrontend content = $content")
		actor.scope.launch{
			actor.emit("currentPosition", Cont)
		}	
	}

	/*fun updateEndTaskToFrontend( actor: ActorBasicFsm, content: String ){
		println("frontendSupport updateCurrentTaskToFrontend content = $content")
		var contentEmit = "consultKb"
		actor.scope.launch{
			actor.emit(contentEmit, "content(" + content + ")")
		}	
	}*/
