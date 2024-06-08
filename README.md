# FotoAnalyzer-Payara-Micro

* der "Starter" auf der Payara-Seite erzeugt für Gradle ein falsches und unvollständiges `build.gradle`.
* Payara verwendet zum Testen Arquillian, was scheinbar immer noch nicht mit JUnit 5 umgehen kann (es existiert keine `ArquillianExtension`).
* ein möglicher Ausweg ist die Nutzung von `Testcontainers` (siehe https://blog.payara.fish/jakarta-ee-integration-testing-with-testcontainers). Das ist als Nächstes zu untersuchen.

Das Programm beruht auf dem Blog-Artikel https://blog.payara.fish/jakarta-ee-integration-testing-with-testcontainers.

In Parayra ist _Constructor Injection_ nicht zulässig, weil beim Deployment Proxys angelegt werden, die einen _No Args Contructor_ erfordern. Die Felder müssen direkt injiziert werden, weswegen sie nicht _final_ sein können. 

## Probleme mit dem Starter-Projekt
* die Datei ./gradlew ist nicht ausführbar.
* die Datei ./gradlew ist DOS-Syle (CRLF) und muss erst nach Unix (LF) gewandelt werden.
* Payara versucht, im Cluster Mode zu starten, was unter Ubuntu 20.04 nicht geht (_Network interface not configured for IPv4_). Das lässt sich mit der Option `--nocluster` verhindern. Es ist nicht klar, warum Payara im Cluster-Mode laufen soll, wenn doch genau eine Instanz pro Microservice bereitgestellt werden soll. 
* bei der Gradle-Version fehlen die Abhängigkeiten für den Test-Scope.
* in allen Versionen fehlen diverse js-Dateien.
* minimale mit dem Starter für Maven und Gradle erzeugte Projekte haben unterschiedlichen _Content Root_, weswegen bei Gradle der Aufruf http://localhost:8080 nicht die Datei `index.html`lädt.
* ebenfalls fehlt der Pfad `/openapi`bei Gradle.

Es ist rätselhaft, warum diese Probleme seit Ubuntu 20.04 nicht gelöst wurden. Immerhin ist Payara aus Glassfish hervorgegangen, der von Sun, also aus der Unix-Ecke kam. Allgemein sind die Informationen im Internet sehr spärlich. Allzu viele Leute scheinen Payara nicht einzusetzen. Dazu passt auch, dass die Beispielanwendung mit Arquillian läuft, was immer noch auf JUnit 4 setzt, also schon lange _legacy_ ist.

## Debuggen des Payara Gradle Plugin
In IntelliJ IDEA werden geladene Gradle-Plugins nicht unter "External Libraries" angezeigt. Obwohl man die einzelnen Gradle-Tasks im entsprechenden Tool Window auch im Debug-Modus starten kann ist es mangels Quellcode nicht möglich, Breakpoints zu setzen.

Stattdessen ist folgendermaßen vorzugehen:
* Beschaffen des Quellcodes, im vorliegenden Fall von GitHub unter der URL `git@github.com:payara/ecosystem-gradle.git`. Klonen des Repositorys und öffnen mit IDEA.
* Importieren dieses Projekts als Modul im zu untersuchenden Projekt (also FotoAnalyzer-Payara-Micro) mittels "File|New|Modules from Existing Sources..."
* ggf. sicherstellen, dass Projekt und Modul dieselben Gradle- und Java-Versionen benutzen.

Jetzt können im Quellcode des Moduls Breakpoint gesetzt werden, die vom Debugger angesprungen werden.
