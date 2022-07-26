<div align="center">

# Desafio Public Sector Net Debt IBM

</div>

<div align="justify">

Esta aplicação tem como objetivo, o consumo de recursos da Dívida Líquida do Setor Público (% PIB) do Banco Central
do Brasil, bem como a disponibilização dos dados e iteração com uma API. Possui EndPoints (recursos) bases em CRUD.
Não é recomendado que seja aplicado em ambiente de produção, pois não foi implantado testes onde contemplam 100% de segurança. Construída em JAVA/Spring Boot (veja abaixo).

</div>

---

### Dados

- Fonte de dados [Dívida Líquida do Setor Público (% PIB) - Total - Banco Central](https://dadosabertos.bcb.gov.br/dataset/4505-divida-liquida-do-setor-publico--pib---total---banco-central)

---

### Configuração

- [x] IDE Intellij
- [x] Java JDK 11
- [x] Spring Boot 2.3.4.RELEASE
- [x] Banco de dados (MySQL)

---

### Ferramentas

- [x] Postman
- [x] Swagger - documentação - 
  - URL: http://localhost:8000/swagger-ui.html

---

### Dependências

groupId |           artifactId           |    version    |  scope  |
:-------: |:------------------------------:|:-------------:|:-------:|
org.springframework.boot |    spring-boot-starter-web     |     2.6.7     |    -    |
org.springframework.boot |      spring-boot-devtools      |     2.6.7     |   test  |
org.springframework.boot |        spring-boot-test        | 2.2.2.RELEASE |    -    |
org.springframework.boot  |  spring-boot-starter-data-jpa  |     2.6.6     |    -    |
mysql    |      mysql-connector-java      |    8.0.28     | runtime |
org.projectlombok |             lombok             |    1.18.22    | provided |
com.h2database |               h2               |    2.1.212    |   test  |
org.springframework.cloud | spring-cloud-starter-openfeign |     3.1.1     |    -    |
junit |             junit              |    4.13.2     |   test  |
net.minidev |           json-smart           |     2.4.8     |    -    |
io.springfox |       springfox-swagger2       |     2.9.2     |    -    |
io.springfox |      springfox-swagger-ui      |     2.9.2     |    -    |
org.junit.vintage |                junit-vintage-engine                |       5.8.2        |    test     |

---

### Plugins

groupId |        artifactId        |    version    |
:-------: |:------------------------:|:-------------:|
org.springframework.boot | spring-boot-maven-plugin | 2.3.4.RELEASE |
org.jacoco |             jacoco-maven-plugin             |     0.8.8     |

---

### Estrutura do projeto

<pre>

public-sector-debt/
├── .idea/..
├── .mvn/..
├── src/                            
│   ├── main/                         
│   │   └── java/                        
│   │       ├── com.ibm.publicsectordebt/
│   │       │   ├── config/
│   │       │   │   └── Runner.java
│   │       │   ├── controllers/
│   │       │   │   ├── exceptions/
│   │       │   │   │   ├── ResourceExceptionHandler.java
│   │       │   │   │   └── StandardError.java
│   │       │   │   ├── BcDataController.java
│   │       │   │   └── RequestClientApiController.java
│   │       │   ├── entities/
│   │       │   │   └── BcData.java
│   │       │   ├── feignclients/
│   │       │   │   └── BcbFeignClient.java
│   │       │   ├── repositories/
│   │       │   │   └── BcDataRepository.java
│   │       │   ├── services/
│   │       │   │   ├── exceptions/
│   │       │   │   │   ├── RequestClientApiException.java
│   │       │   │   │   └── ResourceNotFoundException.java
│   │       │   │   ├── util/
│   │       │   │   │   └── Validation.java
│   │       │   │   ├── BcDataService.java
│   │       │   │   └── RequestClientApiService.java
│   │       │   ├── swagger/
│   │       │   │   └── Swagger.java
│   │       │   └── PublicSectorDebtApplication.java     
│   │       └── resources/                  
│   │           ├── static/..
│   │           ├── templates/..
│   │           ├── application.properties
│   │           └── application-dev.properties             
│   │                           
│   └─── test/
│        └── java/
│            └── com.ibm.publicsectordebt/
│                ├── controllers/
│                │   └── BcDataControllerTest.java
│                ├── services/
│                │   └── BcDataServiceTest.java
│                └── PublicSectorDebtApplicationTests.java 
├── target/..
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── public-sector-debt.iml
└── README.md
</pre>

---

### Descrição dos arquivos e pacotes

<div align="justify">

<br>A aplicação possui um Feign Client para recuperar os dados externos da API do banco central, onde os dados são carregados no start da aplicação, mas também é possível realizar uma solicição POST, o endpoint para o POST se encontra na classe 'RequestClientApiController' tendo uma ligação com a camada de serviço 'RequestClientApiService' verifica se existe dados na base e tenta realizar a inserção utlizando a camada de repositório 'BcDataRepository'.
<br>Para demais processos, utiliza-se o controlador 'BcDataController' para realizar as requisições, solicitando para 'BcDataService' que está na camada de serviço que realize as operações de CRUD, como inserção de dado, atualização, deleção, pesquisas, etc.
<br>Foi criado uma classe 'Validation' no pacote 'util' na camada de serviço, para realizar algumas validações fora da classe 'BcDataService' evitando deixar os métodos extensos.
<br>Na camada de serviços foi criado um pacote de exceptions para ter exception da própria camada de serviço, na camada de controladores foi criado outro pacote de exceptions contendo um modelo 'StandardError' e 'ResourceExceptionHandler' para tratar os erros e lançar a exception, utilizando a notação @ControllerAdvice.

</div>

#### config/Runner.java

- A class Runner é utilizada para dar um start junto com a aplicação, o retorno dos dados da API externa, acionando na camada de serviço a classe 'RequestClientApiService' para iniciar o processo de onboarding.

#### feingClients/BcbFeignClient.java

- Arquivo com interface para consumir serviço externo.

#### controllers/RequestClientApiController

- Arquivo que contém o endpoint para buscar também dados na API externa, porém como o processo de onboarding é iniciado no start da aplicação, esse endpoint irá retornar uma negação porque já contém dados na base, para executar esse endpoint terá que deletar todos os dados para realizar uma nova inserção.

#### entities/BcData.java

- Etidade da aplicação 'BcData'.

#### repositories/BcDataRepository.java

- Repositório da aplicação.

#### services/RequestClientApiService.java

- Classe solicitar a requição em 'BcbFeignClient' e salvar os dados recebidos em 'BcDataRepository'.

#### services/BcDataService.java

- Contém métodos para realizar operações e lógicas.

#### services/util/Validation.java

- Métodos estáticos para realizar validações na camada de serviço.

#### services/exceptions/..

- Exceptions personalizadas da camada de seriço.

#### controllers/exceptions/..

- Contém arquivo arquivo modelo e classe para tratar e lançar exceptions.


#### swagger/Swagger.java

- Documentação da API utilizando swagger. 

---

### EndPoints

Resource | Verb HTTP |                                           URL                                           |
:-------: |:---------:|:---------------------------------------------------------------------------------------:|
ONBOARDING |   POST    |                      http://localhost:8000/request/initializer-api                      |
INSERT |   POST    |                               http://localhost:8000/data                                |
SEARCH ALL |    GET    |                             http://localhost:8000/data/all                              |
SEARCH ALL PAGINATION |    GET    |                   http://localhost:8000/all-pagination?page=0&sort=id                   |
SEARCH ID |    GET    |                              http://localhost:8000/data/1                               |
SEARCH DATE START AND DATE END|    GET    | http://localhost:8000/data/search-date-interval?startDate=21-04-2002&endDate=20-05-2002  |
SEARCH DAY, MONTH OR YEAR |    GET    | http://localhost:8000/data/search-date?field=year&value=2001 |
SEARCH DAY, MONTH OR YEAR |    GET    |  http://localhost:8000/data/search-date?field=month&value=5  |
SEARCH DAY, MONTH OR YEAR |    GET    |   http://localhost:8000/data/search-date?day=day&value=13    |
SEARCH NET DEBT PER YEAR |    GET    |                          http://localhost:8000/data/debt/2018                           |
SEARCH DATA SUMMARY |    GET    |                           http://localhost:8000/data/summary                            |
UPDATE |    PUT    |                              http://localhost:8000/data/1                               |
DELETE DATA ID |  DELETE   |                              http://localhost:8000/data/1                               |
DELETE ALL |  DELETE   |                               http://localhost:8000/data                                |

#### ONBOARDING
> Buscar informações na API externa e tenta realizar a inserção dos dados.

#### INSERT
> Inserir dados.

#### SEARCH ALL
> Retornar todos os dados.

#### SEARCH ALL PAGINATION
> Retornar dados paginados.

#### SEARCH ID
> Retornar dados por ID.

#### SEARCH DATE START AND DATE END
> Retornar dados com intervalos entre datas, passando na URL startDate=21-04-2002 & endDate=21-05-2002. Lembrando que endDate terá que ser posterior a startDate.

#### SEARCH DAY, MONTH OR YEAR
> Retornar dados por dia, mês ou ano. Passando na URL parametros para field e value.
> <br> Busca por ano: search-date?field=year&value=2001
> <br> Busca por mês: search-date?field=month&value=5
> <br> Busca por dia: search-date?field=day&value=15

#### SEARCH NET DEBT PER YEAR
> Retornar dados por ano e soma acumulada pelo período de 12 meses.

#### SEARCH DATA SUMMARY
> Retornar alguns dados em resumo, como maior valor, menos valor, média de todos os valores, etc.

#### UPDATE
> Atualizar.

#### DELETE
> Apagar dado específico.

#### DELETE-ALL
> Apagar todos os dados.

---

### Como rodar a aplicação em ambiente local

- [x] **IDE Intellij - Plugins**
  - [x] Database Navigator
  - [x] JPA Buddy
  - [x] Spring Initializr and Assistent
- [x] **Java JDK 11**
- [x] **Banco de dados (MySQL - version 8.0.28 - Port MySQL: 3306)**
  - [x] mysql-connector-java-8.0.28

#### Caso o plug-in DB Navigator não possua conexão com MySQL

> Acesse no Intellij View: <br>Tool Windows -> DB Browser -> New Connection -> selected MySQL

```mysql-psql
Name: MySQLConnector
Host: localhost
Database: mysql
user: {seu usuario}
password: {senha do seu acesso}
Driver library: C:\Program Files\MySQL\mysql-connector-java-8.0.28\mysql-connector-java-8.0.28.jar
```

#### Aplicação

Iniciar aplicação - recuperar dados externos (Onboarding)
```json
http://localhost:8000/request/initializer-api
```

Demais endpoints da aplicação
```json
http://localhost:8000/data/
```

Documentação Swagger
```json
http://localhost:8000/swagger-ui.html
```