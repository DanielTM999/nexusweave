# NexusWeave HTTP Server

NexusWeave é um servidor HTTP simples e leve para aplicações Java. Ele fornece as funcionalidades necessárias para criar e gerenciar requisições e respostas HTTP, permitindo configurar rapidamente um servidor e gerenciar sessões de clientes.

## Como Usar

### Pré-requisitos

Certifique-se de ter os seguintes itens instalados no seu sistema:

- Java Development Kit (JDK) 8 ou superior
- Apache Maven

### Instalação

Para adicionar o NexusWeave ao seu projeto, inclua a seguinte dependência e repositório no seu arquivo `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>nexusweave.server</groupId>
        <artifactId>nexusweave</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/DanielTM999/nexusweave</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

## Exemplo de Uso

Abaixo está um exemplo de como configurar e iniciar o servidor HTTP NexusWeave:

```java
package com.exemplo;

import nexusweave.server.http.NexusWeaveServer;
import nexusweave.server.http.core.HttpAction;
import nexusweave.server.http.HttpServerRequest;
import nexusweave.server.http.HttpServerResponse;

public class Main {
    public static void main(String[] args) {
        NexusWeaveServer server = new NexusWeaveServer(8080);
        server.start(new HttpAction() {
            @Override
            public void actionCalback(HttpServerRequest request, HttpServerResponse response) {
                response.append("<html><body><h1>Olá, Mundo!</h1></body></html>");
                response.writer();
            }
        });
    }
}
```

Aqui está o texto formatado em Markdown:

markdown

## Exemplo de Uso

Abaixo está um exemplo de como configurar e iniciar o servidor HTTP NexusWeave:

```java
package com.exemplo;

import nexusweave.server.http.NexusWeaveServer;
import nexusweave.server.http.core.HttpAction;
import nexusweave.server.http.HttpServerRequest;
import nexusweave.server.http.HttpServerResponse;

public class Main {
    public static void main(String[] args) {
        NexusWeaveServer server = new NexusWeaveServer(8080);
        server.start(new HttpAction() {
            @Override
            public void actionCalback(HttpServerRequest request, HttpServerResponse response) {
                response.append("<html><body><h1>Olá, Mundo!</h1></body></html>");
                response.writer();
            }
        });
    }
}
```

## Executando o Servidor

Depois de configurar seu projeto, você pode executar o servidor

## Contribuição

Contribuições para melhorar o servidor NexusWeave são bem-vindas. Por favor, faça um fork do repositório e crie um pull request com suas alterações.