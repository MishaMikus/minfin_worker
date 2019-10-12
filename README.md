# minfin_worker
start webapp

mvn exec:java -Dexec.mainClass="ui_automation.okko.OkkoWorker"

mvn exec:java -Dexec.mainClass="server.SpringBootApplication"

mvn clean install exec:java@okko_worker
mvn clean install exec:java@bolt_worker
mvn clean install exec:java@uber_worker
mvn clean install exec:java@minfin_worker