# Estágio 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia apenas os arquivos de configuração do Maven primeiro (melhor cache)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Baixa as dependências (esse layer será cacheado se o pom.xml não mudar)
RUN mvn dependency:go-offline -B

# Copia o código fonte
COPY src ./src

# Compila a aplicação e gera o JAR
RUN mvn clean package -DskipTests -B

# Estágio 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Define o diretório de trabalho
WORKDIR /app

# Cria um usuário não-root para executar a aplicação (segurança)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia o JAR compilado do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Variáveis de ambiente para otimização da JVM
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Comando para executar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]