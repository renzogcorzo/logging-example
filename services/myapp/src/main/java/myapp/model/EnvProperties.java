package myapp.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by renzogcorzo on 10/21/2018.
 */

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
