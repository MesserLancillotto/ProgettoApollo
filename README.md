# ProgettoApollo
## Prerequisiti
Per limiti di nostra capacità progettuale il progetto dovrebbe essere posto in `~/documents/ProgettoApollo/`, principalmente perché è lì che si trova il database. Avremmo potuto fare in modo che il db si trovasse in qualche cartella locale (come in `.local/share`) ma abbiamo preferito non sporcare al di fuori della cartella di questo progetto, poiché anche per il professore andare a ripulire ogni volta tutti i db di tutti gli studenti (sempre ammesso che mettessero tutti i db nella stessa cartella) sarebbe un lavoro quantomeno tedioso.
## Compilazione
Per compilare è necessario aver installato maven. Se si vuole adoperare il makefile basta usare il comando 

```bash
make all
```

altrimenti usando direttamente maven 

```bash
mvn clean compile -U
``` 

## Database
Installare H2 v.2.2.224 che dovrebbe creare la cartella `~/.m2/repository/com/h2database/h2/2.2.224/`, dunque, usando il makefile 

```bash
make h2_setup
``` 

Quindi aprire l'interfaccia grafica di H2 con uno a scelta dei seguenti comandi

```bash
make h2_html_interface
``` 

```bash
java -cp ~/.m2/repository/com/h2database/h2/2.2.224/h2-2.2.224.jar org.h2.tools.Server
``` 

Aprire il database in `~/documents/ProgettoApollo/databases/MAIN_DB` e copiare ed incollare prima il contenuto di `terraform.sql` per avere le tabelle e dati mock. Qualora si volesse stampare a video ogni dato nel db per comodità è stato creato il file `table_select_all`, similmente per `table_drop_all.sql` se si vuole cancellare tutto.
## Lato Server
Per eseguire il server con database usare
```bash
make server_run
```
## Lato Client
Per avviare il lato client usare
```bash
make client_run
```
## Richieste custom (debug)
Per mandare richieste di test usando netcat usare
```bash
LEN=$(wc -c < login_request.json)
printf "$(printf '\\x%02x\\x%02x' $((LEN>>8)) $((LEN&0xff)))" > /tmp/hdr
cat /tmp/hdr login_request.json | nc localhost 8000 | xxd
```
dove `login_request.json` è un file con la stringa formata per la richiesta