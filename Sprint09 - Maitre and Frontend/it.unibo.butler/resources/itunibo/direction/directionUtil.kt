package itunibo.direction

object directionUtil {
	suspend fun changeDirection( fromDir: String , toDir : String): String{
 		when( toDir ){
			"upDir" ->  when( fromDir ) {
							"leftDir"  -> return "r"
							"rightDir" -> return "l"
					        "downDir"  -> return "180"
					         else -> println("changeDirection NEVER HERE (upDir)")
 						} 
			"downDir" ->  when( fromDir ) {
							"leftDir"  -> return "l"
							"rightDir" -> return "r"
					        "upDir"    -> return "180"
					        else -> println("changeDirection NEVER HERE (downDir)")
						} 
			"rightDir" ->  when( fromDir ) {
							"downDir"  -> return "l" //forwardToResourcemodel(actor,"l")
							"upDir"    -> return "r"
					        "leftDir"   -> return "180"
					        else -> println("changeDirection NEVER HERE (rightDir)")
						} 
			"leftDir" ->  when( fromDir ) {
							"downDir"  -> return "r"
							"upDir"    -> return "l"
					        "rightDir" -> return "180"
					        else -> println("changeDirection NEVER HERE (leftDir)")
						} 
			 else -> println("changeDirection DIRECTION UNKNOWN")
		}
 		return ""
	
	}
}	