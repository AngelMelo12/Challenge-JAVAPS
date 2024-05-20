# TargetCustomer

Todos os endpoints estão disponíveis em uma coleção para teste via Postman. Utilizando o arquivo endpoints-postman.json localizado na pasta raíz do projeto.

## Integrantes
### Angélica Ferreira de Matos Melo - RM550776 - Planejamento, Definição do escopo do projeto, Desenvolvimento, Homologação/Testes, Deploy/Go Live.
### Ricardo Yuri Gonçalves Domingues - RM551808 - Planejamento, Definição do escopo do projeto, Desenvolvimento.
### Eduardo Foncesca Finardi - RM98624 - Planejamento, Definição do escopo do projeto, Documentação.
### Matheus Roberto Aparecido de M.C.P de Souza - RM98581 - Planejamento, Definição do escopo do projeto.
### Lucca Rinaldi Valladão de Freitas - RM98207 - Planejamento, Definição do escopo do projeto.

## Como rodar o projeto
Primeiramente certifique-se que seu computador possui o Java 17 instalado, assim como a versão mais recente do Maven.

Clone este repositório na sua pasta de preferência. Utilizando um terminal, digite o seguinte comando:

    mvn spring-boot:run

Caso esteja usando uma IDE, abra o projeto na seu editor de preferência, localize a classe **TargetcustomerApplication** e execute o projeto através do método main localizado nesta classe.

Utilize o arquivo *endpoints-postman.json* localizado na pasta raíz do projeto para realizar testes locais das chamadas de API persistência de informações no banco de dados.

## Endpoints

### Cadastro

GET /cadastro

Lista todas as cadastros cadastradas no sistema.

*Códigos de status*
200 sucesso

---
GET /cadastro/{id}

Retorna os detalhes de um cadastro com o 'id' informado.

*Códigos de status*
200 sucesso
404 id não encontrado

---
POST /cadastro

Cadastrar uma nova empresa.

| campo       | tipo   | obrigatório | descrição 
|-------------|--------|-----------|-----------
| cnpj        | long   | sim       | um cnpj para identificar a cadastro 
| senha       | string | sim       | senha escolhida pelo usuário 
| razaoSocial | string | sim       | razão social do usuário 

```json
{
  "cnpj":12345,
  "senha":"12345",
  "razaoSocial":"12345"
}
```

*Códigos de status*
201 criado com sucesso
400 validação falhou

---
DELETE /cadastro/{id}

Apaga o cadastro com o 'id' informado.

*Códigos de status*
204 apagado com sucesso
404 id não encontrado

---
PUT /cadastro/{id}

Altera o cadastro com o 'id' informado.

| campo | tipo | obrigatório | descrição 
|-------|------|-------------|-----------
| cnpj        | long   | sim       | um cnpj para identificar a cadastro 
| senha       | string | sim       | senha escolhida pelo usuário 
| razaoSocial | string | sim       | razão social do usuário design

*Códigos de status*
200 sucesso
404 id não encontrado
400 validação falhou

*Scheme

```json
{
  "cnpj":12345,
  "senha":"12345",
  "razaoSocial":"12345"
}
```

### E-mail

GET /email

Lista todos os e-mails cadastrados no sistema.

*Códigos de status*
200 sucesso

---
GET /email/{id}

Retorna os detalhes de um e-mail com o 'id' informado.

*Códigos de status*
200 sucesso
404 id não encontrado

---
POST /email

Cadastrar um novo e-mail.

| campo      | tipo   | obrigatório | descrição 
|------------|--------|-----------|-----------
| idCadastro | long   | sim       | id do cadastro 
| email      | string | sim       | e-mail para ser cadastrado 

```json
{
  "idCadastro":1,
  "email":"meuemail@email.com"
}
```

*Códigos de status*
201 criado com sucesso
400 validação falhou

---
DELETE /email/{id}

Apaga o e-mail com o 'id' informado.

*Códigos de status*
204 apagado com sucesso
404 id não encontrado

---
PUT /email/{id}

Altera a e-mail com o 'id' informado.

| campo | tipo | obrigatório | descrição 
|-------|------|-------------|-----------
| email      | string | sim       | e-mail para ser atualizado 

*Códigos de status*
200 sucesso
404 id não encontrado
400 validação falhou

*Scheme

```json
{
  "email":"meunovoemail@email.com"
}
```

### Endereço

GET /endereco

Lista todos os endereços cadastrados no sistema.

*Códigos de status*
200 sucesso

---
GET /endereco/{id}

Retorna os detalhes de um endereço com o 'id' informado.

*Códigos de status*
200 sucesso
404 id não encontrado

---
POST /endereco

Cadastrar um novo endereço.

| campo      | tipo   | obrigatório | descrição 
|------------|--------|-----------|-----------
| idCadastro | long   | sim       | id do cadastro 
| logradouro | long   | sim       | número de lougradouro 
| cep        | long   | sim       | número de cep
| descricaoPontoDeReferencia | string | sim       | descrição do ponto de referência do endereço

```json
{
  "idCadastro":1,
  "logradouro":123,
  "cep":123,
  "descricaoPontoDeReferencia":"Descricao"
}
```

*Códigos de status*
201 criado com sucesso
400 validação falhou

---
DELETE /endereco/{id}

Apaga o endereço com o 'id' informado.

*Códigos de status*
204 apagado com sucesso
404 id não encontrado

---
PUT /endereco/{id}

Altera o endereço com o 'id' informado.

| campo | tipo | obrigatório | descrição 
|-------|------|-------------|-----------
| logradouro | long   | sim       | número de lougradouro 
| cep        | long   | sim       | número de cep
| descricaoPontoDeReferencia | string | sim       | descrição do ponto de referência do endereço

*Códigos de status*
200 sucesso
404 id não encontrado
400 validação falhou

*Scheme

```json
{
  "logradouro":123,
  "cep":123,
  "descricaoPontoDeReferencia":"Descricao"
}
```

### Telefone

GET /telefone

Lista todos os telefones cadastrados no sistema.

*Códigos de status*
200 sucesso

---
GET /telefone/{id}

Retorna os detalhes de um telefone com o 'id' informado.

*Códigos de status*
200 sucesso
404 id não encontrado

---
POST /telefone

Cadastrar um novo endereço.

| campo        | tipo   | obrigatório | descrição 
|--------------|--------|-----------|-----------
| idCadastro   | long   | sim       | id do cadastro 
| ddi          | long   | sim       | ddi do número de telefone 
| ddd          | long   | sim       | ddd do número de telefone
| telefone     | long   | sim       | número de telefone completo
| tipoTelefone | string | sim       | origem do telefone (Celular, Fixo, etc.)

```json
{
  "idCadastro":1,
  "ddi":55,
  "ddd":11,
  "telefone":123456789,
  "tipoTelefone":"CELULAR"
}
```

*Códigos de status*
201 criado com sucesso
400 validação falhou

---
DELETE /telefone/{id}

Apaga o telefone com o 'id' informado.

*Códigos de status*
204 apagado com sucesso
404 id não encontrado

---
PUT /telefone/{id}

Altera o telefone com o 'id' informado.

| campo | tipo | obrigatório | descrição 
|-------|------|-------------|-----------
| ddi          | long   | sim       | ddi do número de telefone 
| ddd          | long   | sim       | ddd do número de telefone
| telefone     | long   | sim       | número de telefone completo
| tipoTelefone | string | sim       | origem do telefone (Celular, Fixo, etc.)

*Códigos de status*
200 sucesso
404 id não encontrado
400 validação falhou

```json
{
  "ddi":55,
  "ddd":11,
  "telefone":123456789,
  "tipoTelefone":"CELULAR"
}
```

### Consulta

GET /consulta

Lista todos as consultas cadastrados no sistema.

*Códigos de status*
200 sucesso

---
GET /consulta/{id}

Retorna os detalhes de uma consulta com o 'id' informado.

*Códigos de status*
200 sucesso
404 id não encontrado

---
POST /consulta

Cadastrar uma nova consulta.

| campo        | tipo   | obrigatório | descrição 
|--------------|--------|-----------|-----------
| idCadastro   | long   | sim       | id do cadastro 
| descricaoConsulta          | String | sim       | breve descrição da consulta 

```json
{
  "idCadastro":1,
  "descricaoConsulta":"Consulta do produto X"
}
```

*Códigos de status*
201 criado com sucesso
400 validação falhou

---
DELETE /consulta/{id}

Apaga o consulta com o 'id' informado.

*Códigos de status*
204 apagado com sucesso
404 id não encontrado

---
PUT /consulta/{id}

Altera o consulta com o 'id' informado.

| campo        | tipo   | obrigatório | descrição 
|--------------|--------|-----------|-----------
| descricaoConsulta          | String | sim       | breve descrição da consulta 

```json
{
  "descricaoConsulta":"Consulta do produto X"
}
```
*Códigos de status*
200 sucesso
404 id não encontrado
400 validação falhou


## Link do vídeo de apresentação na nossa Proposta tecnológica: 
https://youtu.be/TIfaJ7jkS3M?feature=shared

##  Diagrama de Classe de entidades e Diagrama de relacionamento:

Imagens estão na pasta Documentação.

## Link do Cronograma no formato Trello:

https://trello.com/b/SHiFbs5O/challenge-targetcustomer-sprint-1-15-04


