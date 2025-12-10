# Notepad App

Este projeto é um aplicativo Android de anotações, desenvolvido em Kotlin/Java, utilizando arquitetura moderna e boas práticas. Ele permite criar, editar, excluir e organizar notas de forma simples e intuitiva.

---

##  Funcionalidades

* Criar novas notas
* Editar notas existentes
* Excluir notas
* Listagem dinâmica com RecyclerView
* Armazenamento local
* Múltiplas telas (Activities e Fragments)
* Sistema de autenticação (caso incluído no código)

---

##  Estrutura do Projeto

```
app/
 ├── java/com/gualda/sachetto/notepad
 │    ├── activities      → Telas principais do app
 │    ├── adapter         → Adapters do RecyclerView
 │    ├── fragments       → Telas em formato de fragmento
 │    ├── model           → Modelos de dados
 │    ├── service         → Serviços e lógica de negócio
 │    └── utils           → Utilidades e classes auxiliares
 ├── res/                 → Layouts, ícones, temas e strings
 ├── AndroidManifest.xml  → Configuração do app
```

Observação: O bloco acima não utiliza formatação de código e está apenas como visualização.

---

##  Tecnologias Utilizadas

* **Linguagem:** Java (maior parte)
* **Android SDK**
* **RecyclerView** para exibição de listas
* **Fragments** para navegação interna
* **Material Design Components**
* **Gradle Kotlin DSL**

---


##  Integração com API Externa

Este aplicativo consome uma **API que está em outro repositório**, responsável por gerenciar dados como usuários, notas, autenticação ou outros serviços.

### Como funciona a comunicação

* O app envia requisições HTTP usando serviços dentro de `service/`.]
  
<a href="https://github.com/iCrowleySHR/api_notepad.git"> Clique aqui para acessar o repositório da API </a>

---

##  Como Executar o Projeto

1. Instale o **Android Studio** (recomendado versão mais recente)
2. Faça o clone ou extraia o projeto
3. Abra o Android Studio e selecione **Open Project**
4. Aguarde o Gradle sincronizar
5. Conecte um dispositivo físico ou use um emulador
6. Clique em **Run** para iniciar o app

---

## Estrutura de Build

O projeto utiliza:

* `build.gradle.kts` na raiz
* Configurações separadas por módulo (app)
* Dependências declaradas com Kotlin DSL

---

##  Licença

Este projeto é de uso pessoal do autor. Ajuste conforme necessário caso deseje publicar.

---

##  Autor

Desenvolvido por **Gustavo Gualda**.



