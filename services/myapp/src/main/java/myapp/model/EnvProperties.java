package myapp.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class EnvProperties {

    @Value("${db.userName}")
    private String userName;

    @Value("${db.password}")
    private String password;

    @Value("${db.portNumber}")
    private String portNumber;

    @Value("${db.dbms}")
    private String dbms;

    @Value("${db.serverName}")
    private String serverName;
}
