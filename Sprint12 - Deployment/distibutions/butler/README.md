## Butler
L'applicazione contiene diversi attori che rappresentano le varie componenti del robot:
* il robot è l'interfaccia di alto livello che riceve i comandi del maitre
* il robotexecutor è l'esecutore dei comandi e i suoi stati rappresentano le diverse fasi di ogni task
* il robotmover si occupa di gestire il movimento verso le diverse risorse, inviando comandi al basicrobot, con l'aiuto di un planner sviluppato dal Prof. Natali
* il roomstate si occupa di mantenere lo stato delle risorse non smart della stanza

Tramite il file `bin / preparerequirements.pl` è possibile configurare la lista di stoviglie e cibi che il robot deve portare sul tavolo all'inizio del buffet.\
Tramite il file `bin / roomstate.pl` è possibile configurare lo stato iniziale delle risorse non smart, ovvero tavolo, dispensa e lavastoviglie, con predicati prolog del tipo `on(Location, Type, Object, Quantity)`\
Tramite il file `bin / roommap.bin` è possibile modificare la mappa della stanza.\
Tramite il file `bin / roomcoordinates.pl` è possibile configurare la posizione delle risorse nella stanza, con predicati prolog del tipo `position(Resource, XCordinate, YCordinate, RobotDirection)`\
Tramite il file `bin / stepconfig.pl` è possibile configurare la durata dello step da richiedere al basicrobot e la durata della pausa tra uno step e l'altro, con predicati prolog del tipo `step(StepTime, PauseTime)`

Tramite il file `bin / robot.pl` è possibile configurare il contesto di esecuzione.\
Il file `bin / it.unibo.robot.bat` esegue l'applicazione.
