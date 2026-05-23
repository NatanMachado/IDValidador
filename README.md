# IDValidator
- Validador de documentos nacionais brasileiros:
  - **CPF:** Cadastro de Pessoa Física
  - **CNPJ:**. Cadastro Nacional de Pessoa Jurídica

##### Swagger
- **/swagger-ui.html** (http://localhost:8080/swagger-ui.html)

## Running with Docker

### Build the image

```powershell
docker build -t idvalidator:local .
```

### Run the container

```powershell
docker run --rm -p 8080:8080 idvalidator:local
```

Then open:

```text
http://localhost:8080/swagger-ui.html
```

If port `8080` is already in use on your machine, map the container to another host port:

```powershell
docker run --rm -p 8081:8080 idvalidator:local
```

Then open:

```text
http://localhost:8081/swagger-ui.html
```

### Run in the background

```powershell
docker run --rm -d --name idvalidator -p 8080:8080 idvalidator:local
```

Stop the background container:

```powershell
docker stop idvalidator
```
