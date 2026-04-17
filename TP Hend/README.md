# TP Hend (duplicate adaptee de TP-RMI)

Implémentation complète du sujet `tp-rmi-enonce.pdf` avec Java RMI (Java 11, Maven).

## Structure

- `src/main/java/interfaces` : interfaces Remote + objets `Serializable`
- `src/main/java/server` : implémentations serveur et `MainServer`
- `src/main/java/client` : clients de démonstration

## Compiler

```bash
cd <project-root>
mvn compile
```

## Lancer le serveur

```bash
cd <project-root>
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
