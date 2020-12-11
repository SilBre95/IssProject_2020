package itunibo.utils


import org.json.JSONObject
import it.unibo.kactor.ActorBasicFsm

fun prepareToSend(msg: String) : Map<String, String> {
	val data = """{"f000" :"pasta","f001":"pizza","f002":"fanta","f003":"chips","f004":"cola","f005":"birra","f006":"snack","f007":"popcorn","f008":"burger","f009":"pesce","f010":"riso","f011":"pane","g008":"errore"}';'"""
	val jsonObject = JSONObject(data)
	val new = msg.replace(",,","").replace("[","").replace("]","")	
	var linewsts = new.split(")(")
	//val retString: String = ""
	//val retString = ""
	val listP = mutableListOf<String>()
	val listR = mutableListOf<String>()
	val listT = mutableListOf<String>()
	val listD = mutableListOf<String>()
	val listF = mutableListOf<String>()
	val map = mutableMapOf<String, String>()
	
	linewsts.forEach {
		var x =it.replace(",(","").replace("))","").replace(")","").replace("(","")		
		if(x.contains("pantrydishes")){
			var n= x.replace("pantrydishes","")
			n=n+"-"
		listP.add(n)
		}
		else if(x.contains("robotdishes")){
			var n= x.replace("robotdishes","")
			n=n+"-"
		listR.add(n)
		}
		else if(x.contains("robotfood")){
			var n= x.replace("robotfood","")
			var subFood = n.split(",")
			var one = subFood.elementAtOrNull(0)
			var rest = jsonObject.get(one.toString())
			n=rest.toString() +","+ subFood.elementAtOrNull(1)+"-"
		listR.add(n)
		}
		
		else if(x.contains("tabledishes")){
			var n= x.replace("tabledishes","")
			n=n+"-"
		listT.add(n)
		}
		else if(x.contains("tablefood")){
			var n= x.replace("tablefood","")
			var subFood = n.split(",")
			var one = subFood.elementAtOrNull(0)
			var rest = jsonObject.get(one)
			n=rest.toString() +","+ subFood.elementAtOrNull(1)+"-"			
		listT.add(n)
		}
		else if(x.contains("dishwasherdishes")){
			var n= x.replace("dishwasherdishes","")
			n=n+"-"
		listD.add(n)
		}
		if(x.contains("fridgefood")){
			var n= x.replace("fridgefood","")
			var subFood = n.split(",")
			var one = subFood.elementAtOrNull(0)
			var rest = jsonObject.get(one)
			n=rest.toString() +","+ subFood.elementAtOrNull(1)+"-"			
		listF.add(n)
		}
	}
	var c = listP.joinToString()
	var t = "pantry(*"+c+")"
	
	var r = listR.joinToString()
	var rob = "robot(*"+r+")"
	
	var ta = listT.joinToString()
	var tav = "table(*"+ta+")"
	
	var d = listD.joinToString()
	var dw = "dishwasher(*"+d+")"
	
	var f = listF.joinToString()
	var fri = "fridge(*"+f+")"
	
	map["pantry"] = t
	map["robot"] = rob
	map["table"] = tav
	map["dishwasher"] = dw
	map["fridge"] = fri
	
	return map
	}

	fun formatPosition() : String {
		return "(" + itunibo.planner.plannerUtil.getPosX() + ", " +
				itunibo.planner.plannerUtil.getPosY() + " - " +
				itunibo.planner.plannerUtil.getDir() + ")"
	}