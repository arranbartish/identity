# !!Generated file!! - update HerokuProcfile.template\
# java -Dserver.port=$PORT -jar identity-app/target/identity.jar
web: java -Dspring.jpa.hibernate.ddl-auto=create-drop -Dspring.datasource.url=$JDBC_DATABASE_URL -Dspring.datasource.driverClassName=org.postgresql.Driver -Dserver.port=$PORT -jar identity-app/target/identity.jar