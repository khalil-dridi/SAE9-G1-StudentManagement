# Étape 1 : utiliser une image JDK comme base
FROM eclipse-temurin:17-jdk-alpine

# Étape 2 : définir le jar à copier
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Étape 3 : exposer le port de l'application Spring Boot
EXPOSE 8081

# Étape 4 : définir la commande de démarrage
ENTRYPOINT ["java","-jar","/app.jar"]

