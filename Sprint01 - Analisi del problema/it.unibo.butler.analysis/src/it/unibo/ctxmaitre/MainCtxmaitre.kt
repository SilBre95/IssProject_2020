/* Generated by AN DISI Unibo */ 
package it.unibo.ctxmaitre
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "butler.pl", "sysRules.pl"
	)
}

