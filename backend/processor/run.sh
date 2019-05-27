mvn clean package assembly:single -DskipTests
mv target/AttackTopology-jar-with-dependencies.jar target/AttackTopology.jar
/home/ma/exp/storm/bin/storm jar target/AttackTopology.jar com.mobileai.luncert.Application 