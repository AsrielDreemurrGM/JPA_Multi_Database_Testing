<h1>Projeto de Testes JPA com Múltiplos Bancos de Dados</h1>
<p>
  This README is also available in <a href="./README.md">English</a>.
</p>
<p>
  Este projeto explora como configurar e interagir com múltiplos bancos de dados usando <strong>JPA</strong>. Inclui configurações para PostgreSQL e MySQL, com uma estrutura flexível de DAO e cobertura de testes para três unidades de persistência.
</p>
<h2>📁 Visão Geral do Projeto</h2>
<p>
  O projeto começou com suporte para dois bancos PostgreSQL e posteriormente foi estendido para incluir um terceiro banco MySQL. Demonstra como:
</p>
<ul>
  <li>Estruturar DAOs JPA para suportar múltiplas unidades de persistência;</li>
  <li>Realizar operações CRUD simultâneas em PostgreSQL e MySQL;</li>
  <li>Utilizar variáveis de ambiente para credenciais dos bancos;</li>
  <li>Escrever testes unitários para cada configuração de banco de dados.</li>
</ul>
<h2>🧪 Tecnologias Utilizadas</h2>
<ul>
  <li>Java 21</li>
  <li>JPA</li>
  <li>JUnit 5</li>
  <li>PostgreSQL</li>
  <li>MySQL</li>
  <li>Spring Tools Suite 4 (STS4)</li>
</ul>
<h2>⚙️ Instruções de Configuração</h2>
<ol>
  <li>Certifique-se de ter o Java 17+ instalado (Projeto testado no Java 21);</li>
  <li>Instale os bancos <strong>PostgreSQL</strong> e <strong>MySQL</strong> localmente ou configure instâncias remotas;</li>
  <li>Crie e configure as seguintes variáveis de ambiente:
    <ul>
      <li><code>DB_URL</code> – URL JDBC para seu banco padrão (usado pelo DB1);</li>
      <li><code>DB_USERNAME</code> – Seu usuário do banco;</li>
      <li><code>DB_PASSWORD</code> – Sua senha do banco.</li>
    </ul>
  </li>
  <li>Edite o arquivo <code>persistence.xml</code> na pasta <code>META-INF</code>, se necessário, para ajustar URLs de conexão e propriedades do driver;</li>
  <li>Execute as classes de teste para validar a integração entre os bancos.</li>
</ol>
<h2>📜 Destaques dos Commits</h2>
<ul>
  <li><strong>Configuração do Projeto</strong> – Projeto iniciado com base na estrutura da migração JDBC anterior;</li>
  <li><strong>Suporte a Dois Bancos</strong> – Adicionados DAOs e testes para PostgreSQL com duas unidades de persistência;</li>
  <li><strong>Suporte a Três Bancos</strong> – Integrado MySQL como terceiro banco com nova DAO e classe de testes;</li>
  <li><strong>Documentação Completa</strong> – Adicionados Javadocs para todas as entidades, DAOs e classes de teste com links internos para navegação fácil.</li>
</ul>
<h2>📝 Observações</h2>
<ul>
  <li>Certifique-se de incluir os arquivos JAR necessários para MySQL/PostgreSQL no classpath;</li>
  <li>Todos os JARs necessários estão na pasta <code>project-files</code>;</li>
</ul>
