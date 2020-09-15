# org-company-emp-app
- Import project to the IntelliJ IDEA
- Use spring-boot:run for starting the service `>mvn spring-boot:run`
- Use 'site' to start UI `>mvn site`
- The service starts at port `8081`, root context `/empapp-api`, version `/v1`. A sample GET api being - `http://localhost:8081/empapp-api/v1/employees`
- The UI starts at `localhost:4200`
- Configure the browser for CORS to enable cross-origin resource sharing (in MacOS, set in ~/.bash_profile)
`alias chrome="/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --disable-web-security --user-data-dir=~/.chrome-disable-web-security"`
- Swagger available at `localhost:8081/empapp-api/swagger-ui.html`
