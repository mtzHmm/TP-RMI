# TP Hend



## Structure

- `src/main/java/interfaces` : interfaces Remote + objets `Serializable`
- `src/main/java/server` : implémentations serveur et `MainServer`
- `src/main/java/client` : clients de démonstration

## Compiler

```bash

mvn compile
```

## Lancer le serveur

```bash

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
