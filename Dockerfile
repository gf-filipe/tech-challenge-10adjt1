# Usa uma imagem base do OpenJDK para rodar a aplicação Java
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR da sua aplicação para o container.
# "target/*.jar" assume que você está usando Maven e o arquivo JAR
# compilado está na pasta target.
COPY target/*.jar app.jar

# Expõe a porta em que a aplicação será executada (padrão do Spring Boot)
EXPOSE 8080

# Comando para rodar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]