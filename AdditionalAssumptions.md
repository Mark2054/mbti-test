Zu Intervallen:
- Intervalle werden immer in der Form [x,y] angegeben, wobei x und y Ganzzahlen sind (d�rfen auch negativ sein). Keine weiteren, zus�tzlichen Zeichen sind erlaubt 
- Die Intervalle [a,b] und [b,a] bilden das gleiche Intervall ab. Im Laufe der Bearbeitung allerdings wird immer die kleinere Zahl links stehen (und somit auch im Ergebnis).

Zur Intervalleingabe:
- Es soll m�glich sein, Intervalle direkt als Parameter anzugeben, oder im Nachhinein zur Eingabe aufgefordert zu werden. Es ist au�erdem m�glich, aus einer Datei zu lesen, statt sie von Hand anzugeben.
- Werden Intervalle als Parameter oder per Eingabeaufforderung eingegeben, so geschieht dies in der Form: [a,b] [c,d] [e,f] usw. Also immer mit einem Leerzeichen zwischen jedem Intervall.
- Genauso muss es auch in der Datei aussehen. Weitere Zeichen (inklusive Zeilenumbr�che) sind nicht gestattet.

Verhalten im Fehlerfall:
- Sollten fehlerhafte Intervalle (z.b. mit Buchstaben, Kommazahlen, etc.) eingelesen werden, so wird das Programm dem Nutzer dies mitteilen, aber dies nicht als Fehler zur Terminierung ansehen, sondern ohne die fehlerhaften Intervalle fortfahren.
- Sollte kein einziges fehlerfreies Intervall f�r die Bearbeitung zur Verf�gung stehen, wird dem Nutzer dies mitgeteilt. In diesem Fall wird das Programm auch nicht weiter ausgef�hrt.