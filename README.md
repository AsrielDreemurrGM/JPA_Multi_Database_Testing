<h1>JPA Multi-Database Testing Project</h1>
<p>
  Este README também está disponível em <a href="./README.pt-br.md">Português</a>.
</p>
<p>
  This project explores how to configure and interact with multiple databases using <strong>JPA</strong>. It includes PostgreSQL and MySQL setups with a flexible DAO structure and test coverage across three persistence units.
</p>
<h2>📁 Project Overview</h2>
<p>
  The project began with support for two PostgreSQL databases and later extended to include a third MySQL database. It demonstrates how to:
</p>
<ul>
  <li>Structure JPA DAOs to support multiple persistence units;</li>
  <li>Perform CRUD operations across PostgreSQL and MySQL simultaneously;</li>
  <li>Use environment variables for database credentials;</li>
  <li>Write unit tests for each database configuration.</li>
</ul>
<h2>🧪 Technologies Used</h2>
<ul>
  <li>Java 21</li>
  <li>JPA</li>
  <li>JUnit 5</li>
  <li>PostgreSQL</li>
  <li>MySQL</li>
  <li>Spring Tools Suite 4 (STS4)</li>
</ul>
<h2>⚙️ Setup Instructions</h2>
<ol>
  <li>Make sure you have Java 17+ installed (Project tested on Java 21);</li>
  <li>Install both <strong>PostgreSQL</strong> and <strong>MySQL</strong> databases locally or configure remote instances;</li>
  <li>Create and configure the following environment variables:
    <ul>
      <li><code>DB_URL</code> – JDBC URL for your default database (used by DB1);</li>
      <li><code>DB_USERNAME</code> – Your DB username;</li>
      <li><code>DB_PASSWORD</code> – Your DB password.</li>
    </ul>
  </li>
  <li>Edit <code>persistence.xml</code> in the <code>META-INF</code> folder if necessary to adjust connection URLs and driver properties;</li>
  <li>Run the test classes to validate integration across databases.</li>
</ol>
<h2>📜 Commit Highlights</h2>
<ul>
  <li><strong>Project Setup</strong> – Initialized the project based on a previous JDBC migration structure;</li>
  <li><strong>Two DB Support</strong> – Added DAOs and tests for PostgreSQL using two persistence units;</li>
  <li><strong>Three DB Support</strong> – Integrated MySQL as a third database with new DAO and test class;</li>
  <li><strong>Full Documentation</strong> – Added Javadocs to all entity, DAO, and test classes with internal links for easy navigation.</li>
</ul>
<h2>📝 Notes</h2>
<ul>
  <li>Make sure to include the required JAR files for MySQL/PostgreSQL in your classpath;</li>
  <li>All required JAR files are located in the <code>project-files</code> folder;</li>
</ul>
