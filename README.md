# ids-poc

POC com UUID, ULID, TSID etc.

## Features

- Gerar milhões de IDs em paralelo e checar duplicidade, classes `MainMemory` e `MainFile`
- Integração entre o Hibernate e os diversos tipos de ID
- Integração com o Rest + Jackson
- Endpoint REST para inserir e consultar a entidade `MyEntity` com vários tipos de ID, classe `MyEntityResource`

## Versões

- JDK 21

## Gerar milhões de IDs

 Gerar milhões de IDs em paralelo e checar se houve colisão (duplicidade) entre os IDs gerados. Executar `MainMemory` ou `MainFile`. A documentação está no JavaDoc.

## Endpoint REST

Subir a aplicação

```bash
./mvnw -V quarkus:dev
```

Os endpoints e comandos `curl` estão na classe `MyEntityResource`.

Os dados para acessar o banco de dados H2 que sobe em memória estão no arquivo `application.properties`.
