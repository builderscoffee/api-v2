package eu.builderscoffee.api.common.data.tables;

import io.requery.*;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/***
 * {@link ServerActivity} est l'objet utilisé pour stocker une activité d'un serveur
 */
@Entity
@Table(name = "servers_activities")
@ToString
public abstract class ServerActivity {

    /* Columns */

    @Key
    @Generated
    int id;

    @Column
    String serverName;

    @Column
    String message;

    @Column
    String managerName;

    @Column(value = "CURRENT_TIMESTAMP")
    Timestamp date;
}
