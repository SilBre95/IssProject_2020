# ISSproject
Progetto d'esame per il corso di [Ingegneria dei Sistemi Software M](https://www.unibo.it/it/didattica/insegnamenti/insegnamento/2018/385373)

## Team
Silvia Brescia  - silvia.brescia@studio.unibo.it\
Giulia Grundler - giulia.grundler@studio.unibo.it

## Descrizione
L'obiettivo del progetto consiste nella realizzazione di un robot cameriere (capace di prepare e pulire il tavolo di un buffet, sotto la guida di un maitre), dell'applicazione da cui il maitre può inviare comandi al robot e del software che gestisce un frigo smart.\
Per maggiori dettagli: [Sprint00 - Analisi dei requisiti / TFBO19ISS.pdf](https://github.com/giuliagru/ISSproject/blob/master/Sprint00%20-%20Analisi%20dei%20requisiti/TFBO19ISS.pdf)

Il progetto è per la maggior parte realizzato utilizzando il linguaggio QActor, sviluppato dal Prof. Antonio Natali. Le librerie del linguaggio, i componenti basicrobot e virtualrobot e altre risorse sono disponibili qui: [github.com/anatali/iss2020LabBo](https://github.com/anatali/iss2020LabBo)

## Utilizzo
* Scaricare la cartella [Sprint12 - Deployment / distibutions](https://github.com/giuliagru/ISSproject/tree/master/Sprint12%20-%20Deployment/distibutions)
* Eseguire il file `installFrontend.bat` per installare node nel frontend (applicazione del maitre)

**Per utilizzare il robot virtuale:**
* eseguire il file `installRobot.bat` per installare node
* eseguire il file `startAppl.bat` che esegue tutti i componenti e apre due finestre del browser: una per il frontend e una per l'ambiente virtuale del robot,
*oppure* fare riferimento ai `README` contenuti in ogni distribuzione per eseguire le applicazioni singolarmente o modificare alcune configurazioni

**Per utilizzare il robot nano (su raspberry):**
* fare riferimento al progetto https://github.com/anatali/iss2020LabBo/tree/master/it.unibo.raspIntro2020 per la configurazione del raspberry
* eseguire il basicrobot su raspberry
* eseguire il file `startApplNano.bat` che esegue tutti gli altri componenti e apre una finestra del browser con il frontend, *oppure* fare riferimento ai `README` contenuti in ogni distribuzione per eseguire le applicazioni singolarmente o modificare alcune configurazioni

## Demo 
Viene fornita una demo (https://github.com/SilBre95/IssProject_2020/blob/main/DEMO.mp4) che riassume le funzionalità principali

![alt text](https://github.com/SilBre95/IssProject_2020/blob/main/Robot_Image.png)

## Tags
kotlin, dsl, raspberry, node, express, coap, mqtt, tcp, iot, planner, robot, prolog, ia, qak, actor, smart device
