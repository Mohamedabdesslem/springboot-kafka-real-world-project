.\bin\windows\kafka-server-start.bat .\config\server.properties

.\bin\windows\kafka-storage.bat format --config .\config\server.properties --cluster-id 0001


.\bin\windows\kafka-topics.bat --create --topic topic-exemple --bootstrap-server localhost:9092

set KAFKA_LOG4J_OPTS=-Dlog4j.configurationFile=file:///C:\\Users\\souhi\\Downloads\\kafka\\config/log4j2.yaml


.\bin\windows\kafka-console-producer.bat --topic words --bootstrap-server localhost:9092 --property parse.key=true --property key.separator="-"


.\bin\windows\kafka-console-consumer.bat --topic orders --bootstrap-server localhost:9092 --from-beginning

.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list



set LOG4J_CONFIGURATION_FILE=C:\\Users\\souhi\\Downloads\\kafka\\config\\log4j2.yaml

set "KAFKA_LOG4J_OPTS=-Dlog4j2.configurationFile=file:///C:/Users/souhi/Downloads/kafka/config/log4j2.yaml" 



java -jar h2-1.4.200.jar -tcp -tcpPort 9094 -tcpAllowOthers

jdbc:h2:tcp://localhost:9094/~/Delivery


.\bin\windows\kafka-console-consumer.bat --topic words-stream-app-words-store-changelog --bootstrap-server localhost:9092 --from-beginning
.\bin\windows\kafka-console-consumer.bat --topic restaurant_orders --bootstrap-server localhost:9092 --from-beginning



-----------------------------------------------------------

 
1- Définit ce que doit faire Kafka si aucun offset n’est trouvé pour ce groupe de consommateurs.
 
Valeur	    Comportement
earliest	Kafka commence à consommer depuis le début de la partition (offset = 0).
latest	    Kafka commence à consommer à partir des nouveaux messages (au moment de la connexion).
none	    

✅ Résumé
👉 Si aucun offset n’est trouvé Kafka utilise la stratégie définie par auto-offset-reset.
💡 Important : cette stratégie n’est utilisée qu’en cas d’absence totale d’offset
→ Si un offset a déjà été commité, Kafka l’utilise toujours, même si auto-offset-reset=earliest.Kafka lève une erreur si aucun offset n’est trouvé.

2- Précisions importantes :
Kafka répartit les partitions entre les consommateurs d’un même groupe.

Si tu as 2 partitions et 1 consommateur, ce consommateur lit les 2 partitions.

Si tu as 2 partitions et 2 consommateurs dans le même groupe, chacun lit une partition (partitionnement équilibré).

Si tu as 2 partitions et 3 consommateurs dans le même groupe, un consommateur restera inactif, car il n’y a pas assez de partitions à distribuer.

3- 🎁 Conclusion
Ajoute des threads pour lire plus de partitions en parallèle.

Le nombre de threads utiles = nombre de partitions (max).

Les partitions = unité de parallélisme chez Kafka.

Dans Kafka Streams, les tasks sont liées aux partitions et distribuées sur les threads.


4-Résumé :
Code	Nom	Action
0	REPLACE_THREAD	        Redémarre un seul thread Kafka Streams
1	SHUTDOWN_CLIENT	        Arrête uniquement le client Kafka Streams
2	SHUTDOWN_APPLICATION	Arrête toute l’application Java

5- 
Élément	Rôle
KTable	                        Représente un état agrégé par clé (la dernière valeur connue)
Materialized.as("words-store")	Crée un state store local nommé "words-store"
.toStream().print(...)	        Convertit le KTable en KStream pour afficher les mises à jour

5- l'opérateur join n'est pas valable si les recors des différents topics ne partagent pas le meme clé 

