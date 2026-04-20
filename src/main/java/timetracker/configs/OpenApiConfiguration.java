package timetracker.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Test task");

        Contact myContact = new Contact();
        myContact.setName("Kirill Knyazkov");
        myContact.setEmail("k.knyazkov@g.nsu.ru");

        Info information = new Info()
                .title("Time Tracker API")
                .version("1.0")
                .description("This API exposes endpoints to manage tasks and task records.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
