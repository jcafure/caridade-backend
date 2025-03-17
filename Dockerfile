# Usar a imagem oficial do OpenJDK 22
FROM openjdk:22-jdk-slim AS build

# Instalar Maven
RUN apt-get update && apt-get install -y maven && apt-get clean

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo pom.xml e baixar as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar o restante do código fonte
COPY src ./src

# Empacotar a aplicação
RUN mvn clean package -DskipTests

# Fase de runtime
FROM openjdk:22-jdk-slim

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado da fase de build
COPY --from=build /app/target/*.jar app.jar

# Expor a porta da aplicação (altere conforme necessário)
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]