# TargetCustomer

## Endpoints

### Cadastro

GET /cadastros

Lista todas as cadastros cadastradas no sistema.

*Códigos de status*
200 sucesso

---
GET /cadastro/{id}

Retorna os detalhes de uma cadastro com o 'id' informado.

*Códigos de status*
200 sucesso
404 id não encontrado

---
POST/cadastro

Cadastrar uma nova cadastro.

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

Apaga a cadastro com o 'id' informado.

*Códigos de status*
204 apagado com sucesso
404 id não encontrado

---
PUT /cadastro/{id}

Altera a cadastro com o 'id' informado.

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

