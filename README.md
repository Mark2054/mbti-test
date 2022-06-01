# mercedes-test
Repository for the test task given during the application process of Mercedes Benz Tech Innovation



# Grundidee

F�r zwei Intervalle [a,b] und [c,d] gilt: Sie �berschneiden sich genau dann, wenn a <= d und b >= c.

Dies setzt nat�rlich voraus, dass bei einem Intervall [x,y] gilt: x <= y.

Mein Plan: Ich w�hle mir beliebig entweder den linken oder rechten Endpunkt und sortiere danach.
Ich habe den linken gew�hlt.

Damit fange ich dann ganz links an und vergleiche die ersten beiden Nachbarn. 

Wichtig an dieser Stelle: F�r alle rechten Nachbarn [c, d] von jedem beliebigen Intervall [a,b] in der geordneten Liste gilt durch die Sortierung nicht nur c >= a, sondern auch d >= a, da ja d laut Voraussetzung >= c sein muss.

Solange ich also nur nach rechts vergleiche, ist IMMER a <= d gegeben. Entsprechend muss nur noch b >= c �berpr�ft werden. �berpr�fe ich das beim direkten, rechten Nachbarn, gibt es zwei M�glichkeiten:

1. Der rechte Nachbar �berschneidet sich (b >= c)
Wenn das der Fall ist, dann kann ich die beiden problemlos verschmelzen und jetzt statt dem alten das neue Gesamtintervall betrachten und mit dem n�chsten rechten Nachbarn vergleichen.

2. Der rechte Nachbar �berschneidet sich nicht (b < c)
Dieser Fall bedeutet, dass kein einziges der �brigen Intervalle mehr eine �berschneidung mit dem aktuell betrachteten haben kann, denn: Die Liste ist ja geordnet nach dem linken Wert (also a und c). c wird nach rechts hin nur immer gr��er. Und wenn jetzt bereits c > b gilt, dann wird sich das auch nie mehr �ndern. Oder anders gesagt: Ich bin fertig damit, mein aktuelles Intervall zu betrachten. Es kann keinen weiteren Verbindungspartner mehr geben. Also kommt das in die Liste der Intervalle, die ich im Endergebnis sehen m�chte. Und dann mache ich ganz einfach an der Stelle weiter, an der ich jetzt bin und schaue mir f�r das Intervall, das gerade noch der rechte Nachbar war, wieder den rechten Nachbarn an, um zu vergleichen.

Das wiederhole ich dann bis zum letzten Element und spare mir damit, jedes Intervall mit jedem anderen Intervall vergleichen zu m�ssen und beim Vergleichen sogar eine der beiden Bedingungen, die durch das Sortieren automatisch immer erf�llt ist.

Die Laufzeit wird haupts�chlich vom gew�hlten Sortieralgorithmus entschieden, da die Laufzeit f�r das Erzeugen der Ergebnisliste nach dem Sortieren linear ist.


# Umsetzung

F�r das Intervall habe ich mir eine eigene Klasse "Interval" geschrieben. Die ist relativ simpel, hat haupts�chlich get und set Methoden, sorgt aber selbst zwingend daf�r, dass f�r jedes Intervall gilt: x <= y. 
Hinzu kommt, dass sie Methoden hat, die �berpr�fen, ob ein String das richtige Pattern hat, um sich in ein Intervall umwandeln zu lassen.
Es ist au�erdem ein Comparable, damit die Liste sich leicht sortieren l�sst. Allerdings w�rde ich sagen, wenn das jetzt nicht nur ein Test w�re, dann h�tte ich wahrscheinlich noch eine alternative Intervalklasse zus�tzlich f�r die Ergebnisintervalle hinzugef�gt und die jetzige umbenannt, weil ich nicht der Ansicht bin, dass die compareTo Methode so, wie sie ist, f�r eine allgemeine Intervallklasse g�ltig sein sollte. Es ist n�tzlich f�r das, was ich damit mache, aber wenn mir jemand erz�hlen w�rde, Intervalle vergleicht man grunds�tzlich immer �ber den linken Endpunkt, dann w�rde ich da vermutlich gegen argumentieren.

Die Hauptklasse, �ber die dann alles ausgef�hrt wird ist mein ApplicationStarter. Die Klasse ist etwas gr��er geworden, als vermutlich n�tig, da ich verschiedene Arten der Intervalleingabe implementiert habe (dazu weiter unten noch mehr). Im Grunde macht sie aber nichts weiter, als die Intervalle einzulesen, dann den IntervalMerger aufzurufen und das Ergebnis auszugeben.

Der gerade erw�hnte IntervalMerger ist die dritte und letzte Klasse. Hier passiert alles, was von mir oben bei der Grundidee beschrieben wurde. Sie hat nur eine einzige, statische Methode merge, die eine Liste mit Intervallen �bergeben bekommt und dann die Ergebnisliste zur�ckgibt.


# Ausf�hrung

Ich habe sowohl ein make file als auch ein start file (es hei�t aber startApplication) in den Oberordner des Projektes hinzugef�gt, die einfach genutzt werden k�nnen (vorausgesetzt java ist installiert und in den Umgebungsvariablen korrekt gesetzt... was ich aber in diesem Fall einmal voraussetzen w�rde).

mit 'make' l�sst sich das Projekt bauen, mit 'startApplication [Parameter]' ausf�hren.
Parameter sind beim Start nicht zwingend n�tig, bestimmten aber, auf welche Art Intervalle eingegeben werden k�nnen.

Alternative 1:
Wird gar kein Parameter angegeben, so wird eine Nutzereingabe angefragt (�ber Scanner/System.in). Hier (wie im Grunde auch in den anderen beiden Alternativen) wird eine Zeile erwartet nach dem folgenden Schema:
[a,b] [c,d] [e,f] [g,h] ...
Die Liste muss nicht geordnet sein, der linke Endpunkt muss auch nicht kleiner sein, als der rechte. Dazu noch ein wenig mehr in der Datei 'Additional Assumptions'.

Alternative 2:
Es ist auch m�glich, die Intervalle direkt als Parameter anzugeben. Das k�nnte dann zum Beispiel so aussehen:
startApplication [1,2] [15,4] [-8,12] [3,6]

Alternative 3:
Zu guter letzt kann auch statt den Intervallen ein Dateipfad angegeben werden. In der Datei m�ssten die Intervalle dann nach dem �blichen Schema in einer Zeile stehen. Das s�he dann so aus:
startApplication "D:\ExampleFile.dat"
