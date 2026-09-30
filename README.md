# Loja de Guitarras

## Como rodar

**Precisa de:** JDK 25 e Docker.

1. Subir o banco (Postgres na porta **5434**):

   ```shell
   docker run -d --name lojaguitarra-db -e POSTGRES_USER=topicos1 -e POSTGRES_PASSWORD=123456 -e POSTGRES_DB=topicos1db -p 5434:5432 postgres:16
   ```

2. Rodar a aplicação:

   ```shell
   .\mvnw.cmd quarkus:dev
   ```

3. Abrir o Swagger: <http://localhost:8080/q/swagger-ui>

As tabelas e os dados de exemplo são criados automaticamente.

> Se aparecer `release version 25 not supported`, o `JAVA_HOME` está apontando para um JDK mais antigo. Aponte para o JDK 25 antes do passo 2, por exemplo no PowerShell:
> `$env:JAVA_HOME = "C:\Program Files\Java\jdk-25"`
