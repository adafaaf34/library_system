Library System - Full runnable project (backend + frontend + DB scripts)

Instructions:
1. Import DB scripts in DB/ folder into MySQL (ddl.sql, dml.sql, procedures.sql).
2. Edit backend/src/main/resources/application.yml and set correct DB credentials and jwt.secret.
3. Run backend: go to backend/ and run `mvn clean package` then `mvn spring-boot:run` (needs Maven).
4. Run frontend: go to frontend/ and run `npm install` then `npm run dev` (needs Node.js & npm).
