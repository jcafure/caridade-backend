📌 Projeto de Caridade

📖 Visão Geral

Este projeto tem como objetivo facilitar a organização de campanhas de caridade voltadas para o preparo e a distribuição de refeições em comunidades carentes.

A plataforma permitirá:
✅ Criação e gestão de campanhas por grupos de atendimento
✅ Registro e notificação de doadores sobre itens necessários
✅ Gerenciamento de doações parciais, como quilos de alimentos individuais
✅ Acompanhamento das refeições distribuídas e histórico de campanhas

🚀 Tecnologias Utilizadas

Java 21 - Backend da aplicação

Spring Boot 3.4.3 - Framework para desenvolvimento rápido e robusto

PostgreSQL - Banco de dados principal

Flyway - Controle de versionamento do banco de dados

H2 - Banco de dados em memória para testes

🎯 Como Funciona

🏡 Grupos de Atendimento

Os grupos de atendimento são responsáveis por:
✔ Criar campanhas para distribuição de refeições
✔ Definir os itens necessários para cada campanha (ex: arroz, feijão, óleo)
✔ Acompanhar a arrecadação dos itens e atualizar o status da campanha

📩 Notificações para Doadores

Sempre que uma nova campanha for criada, os doadores cadastrados receberão uma notificação com a lista de itens necessários.

📊 Doações Parciais

Se um doador contribuir com apenas uma parte dos itens (exemplo: doar 5kg de arroz quando a campanha precisa de 20kg), o sistema calculará automaticamente o saldo pendente e continuará notificando outros doadores até que a necessidade seja atendida.

🔄 Fluxo do Sistema:

1️⃣ Criação da Campanha: Um grupo de atendimento cadastra uma nova campanha com os itens necessários.
2️⃣ Notificação dos Doadores: Todos os doadores cadastrados são notificados sobre os itens que precisam ser arrecadados.
3️⃣ Registro de Doações: Os doadores podem contribuir com parte ou com o total de algum item. O sistema calcula o saldo restante.
4️⃣ Distribuição das Refeições: Após arrecadar todos os itens, a campanha entra na fase de distribuição.
5️⃣ Finalização da Campanha: Depois de distribuídas as refeições, a campanha é finalizada e arquivada.

🔧 Configuração do Banco de Dados PostgreSQL

🐳 Como Configurar o Banco de Dados no Docker

Este projeto utiliza o PostgreSQL como banco de dados principal. Para facilitar a configuração, a aplicação pode ser executada em um contêiner Docker. No entanto, caso você tenha dificuldades para criar o banco de dados automaticamente, siga as etapas abaixo:

📌 Passo a Passo:

1️⃣ Executar o PostgreSQL com Docker

Se você ainda não tiver o banco de dados criado, basta executar o contêiner Docker com o seguinte comando:

$ docker-compose up -d

Isso iniciará o contêiner do PostgreSQL com as configurações definidas no docker-compose.yml.

2️⃣ Criar o Banco de Dados Manualmente

Caso o banco de dados não tenha sido criado automaticamente, você pode criá-lo manualmente dentro do contêiner do PostgreSQL:

Acesse o contêiner do PostgreSQL:

$ docker exec -it postgres-caridadems psql -U postgres

Dentro do PostgreSQL, execute o seguinte comando SQL para criar o banco de dados:

CREATE DATABASE caridadedb;

3️⃣ Conectar ao Banco de Dados

Agora você pode acessar o banco de dados utilizando o DBeaver ou qualquer outro cliente PostgreSQL com as seguintes configurações:
Parâmetro   | Valor                                  
------------|---------------------------------------
**Host**    | localhost                            
**Porta**   | 5434 (conforme docker-compose.yml)  
**Usuário** | postgres                             
**Senha**   | admin                                
**Banco**   | caridadedb

4️⃣ Exemplo de URL de Conexão no DBeaver:

jdbc:postgresql://localhost:5434/caridadedb

