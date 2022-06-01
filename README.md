# mercedes-test
Repository for the test task given during the application process of Mercedes Benz Tech Innovation



# Grundidee

Für zwei Intervalle [a,b] und [c,d] gilt: Sie überschneiden sich genau dann, wenn a <= d und b >= c.

Dies setzt natürlich voraus, dass bei einem Intervall [x,y] gilt: x <= y.

Mein Plan: Ich wähle mir beliebig entweder den linken oder rechten Endpunkt und sortiere danach.
Ich habe den linken gewählt.

Damit fange ich dann ganz links an und vergleiche die ersten beiden Nachbarn. 

Wichtig an dieser Stelle: Für alle rechten Nachbarn [c, d] von jedem beliebigen Intervall [a,b] in der geordneten Liste gilt durch die Sortierung nicht nur c >= a, sondern auch d >= a, da ja d laut Voraussetzung >= c sein muss.

Solange ich also nur nach rechts vergleiche, ist IMMER a <= d gegeben. Entsprechend muss nur noch b >= c überprüft werden. Überprüfe ich das beim direkten, rechten Nachbarn, gibt es zwei Möglichkeiten:

1. Der rechte Nachbar überschneidet sich (b >= c)
Wenn das der Fall ist, dann kann ich die beiden problemlos verschmelzen und jetzt statt dem alten das neue Gesamtintervall betrachten und mit dem nächsten rechten Nachbarn vergleichen.

2. Der rechte Nachbar überschneidet sich nicht (b < c)
Dieser Fall bedeutet, dass kein einziges der übrigen Intervalle mehr eine Überschneidung mit dem aktuell betrachteten haben kann, denn: Die Liste ist ja geordnet nach dem linken Wert (also a und c). c wird nach rechts hin nur immer größer. Und wenn jetzt bereits c > b gilt, dann wird sich das auch nie mehr ändern. Oder anders gesagt: Ich bin fertig damit, mein aktuelles Intervall zu betrachten. Es kann keinen weiteren Verbindungspartner mehr geben. Also kommt das in die Liste der Intervalle, die ich im Endergebnis sehen möchte. Und dann mache ich ganz einfach an der Stelle weiter, an der ich jetzt bin und schaue mir für das Intervall, das gerade noch der rechte Nachbar war, wieder den rechten Nachbarn an, um zu vergleichen.

Das wiederhole ich dann bis zum letzten Element und spare mir damit, jedes Intervall mit jedem anderen Intervall vergleichen zu müssen und beim Vergleichen sogar eine der beiden Bedingungen, die durch das Sortieren automatisch immer erfüllt ist.

Die Laufzeit wird hauptsächlich vom gewählten Sortieralgorithmus entschieden, da die Laufzeit für das Erzeugen der Ergebnisliste nach dem Sortieren linear ist.


# Umsetzung

Für das Intervall habe ich mir eine eigene Klasse "Interval" geschrieben. Die ist relativ simpel, hat hauptsächlich get und set Methoden, sorgt aber selbst zwingend dafür, dass für jedes Intervall gilt: x <= y. 
Hinzu kommt, dass sie Methoden hat, die überprüfen, ob ein String das richtige Pattern hat, um sich in ein Intervall umwandeln zu lassen.
Es ist außerdem ein Comparable, damit die Liste sich leicht sortieren lässt. Allerdings würde ich sagen, wenn das jetzt nicht nur ein Test wäre, dann hätte ich wahrscheinlich noch eine alternative Intervalklasse zusätzlich für die Ergebnisintervalle hinzugefügt und die jetzige umbenannt, weil ich nicht der Ansicht bin, dass die compareTo Methode so, wie sie ist, für eine allgemeine Intervallklasse gültig sein sollte. Es ist nützlich für das, was ich damit mache, aber wenn mir jemand erzählen würde, Intervalle vergleicht man grundsätzlich immer über den linken Endpunkt, dann würde ich da vermutlich gegen argumentieren.

Die Hauptklasse, über die dann alles ausgeführt wird ist mein ApplicationStarter. Die Klasse ist etwas größer geworden, als vermutlich nötig, da ich verschiedene Arten der Intervalleingabe implementiert habe (dazu weiter unten noch mehr). Im Grunde macht sie aber nichts weiter, als die Intervalle einzulesen, dann den IntervalMerger aufzurufen und das Ergebnis auszugeben.

Der gerade erwähnte IntervalMerger ist die dritte und letzte Klasse. Hier passiert alles, was von mir oben bei der Grundidee beschrieben wurde. Sie hat nur eine einzige, statische Methode merge, die eine Liste mit Intervallen übergeben bekommt und dann die Ergebnisliste zurückgibt.


# Ausführung

Ich habe sowohl ein make file als auch ein start file (es heißt aber startApplication) in den Oberordner des Projektes hinzugefügt, die einfach genutzt werden können (vorausgesetzt java ist installiert und in den Umgebungsvariablen korrekt gesetzt... was ich aber in diesem Fall einmal voraussetzen würde).

mit 'make' lässt sich das Projekt bauen, mit 'startApplication [Parameter]' ausführen.
Parameter sind beim Start nicht zwingend nötig, bestimmten aber, auf welche Art Intervalle eingegeben werden können.

Alternative 1:
Wird gar kein Parameter angegeben, so wird eine Nutzereingabe angefragt (über Scanner/System.in). Hier (wie im Grunde auch in den anderen beiden Alternativen) wird eine Zeile erwartet nach dem folgenden Schema:
[a,b] [c,d] [e,f] [g,h] ...
Die Liste muss nicht geordnet sein, der linke Endpunkt muss auch nicht kleiner sein, als der rechte. Dazu noch ein wenig mehr in der Datei 'Additional Assumptions'.

Alternative 2:
Es ist auch möglich, die Intervalle direkt als Parameter anzugeben. Das könnte dann zum Beispiel so aussehen:
startApplication [1,2] [15,4] [-8,12] [3,6]

Alternative 3:
Zu guter letzt kann auch statt den Intervallen ein Dateipfad angegeben werden. In der Datei müssten die Intervalle dann nach dem üblichen Schema in einer Zeile stehen. Das sähe dann so aus:
startApplication "D:\ExampleFile.dat"
