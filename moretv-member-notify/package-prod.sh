mvn -Dmaven.test.skip=true -f ../pom.xml $@ clean install
mvn -Dmaven.test.skip=true $@ clean package assembly:assembly