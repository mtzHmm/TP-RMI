# TP Hend

Variante du TP-RMI (Java 11, Maven) avec légères modifications.

## Différences par rapport au projet original

- Port RMI : **1100** (au lieu de 1099)
- Nouvelles formes : **Hexagone** (en plus de circle, rectangle, triangle, pentagon)
- Noms de variables et messages en français dans les implémentations serveur
- États des tâches : WAITING / RUNNING / COMPLETED (au lieu de PENDING / IN_PROGRESS / DONE)
- Clients de démonstration enrichis (plus de formes, plus de joueurs, plus de tâches)

## Structure

- `src/main/java/interfaces` : interfaces Remote + objets `Serializable`
- `src/main/java/server` : implémentations serveur et `MainServer`
- `src/main/java/client` : clients de démonstration

## Compiler

```bash
cd "TP Hend"
mvn compile
```

## Lancer le serveur

```bash
cd "TP Hend"
mvn -q exec:java -Dexec.mainClass="server.MainServer"
```

## Lancer les clients (dans d'autres terminaux)

```bash
mvn -q exec:java -Dexec.mainClass="client.MainClient"
mvn -q exec:java -Dexec.mainClass="client.GameSimulationClient"
mvn -q exec:java -Dexec.mainClass="client.VectorClient"
mvn -q exec:java -Dexec.mainClass="client.CounterClient"
mvn -q exec:java -Dexec.mainClass="client.TaskManagerClient"
```
