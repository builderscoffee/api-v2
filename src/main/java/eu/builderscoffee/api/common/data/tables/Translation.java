package eu.builderscoffee.api.common.data.tables;

import io.requery.*;
import io.requery.query.MutableResult;
import lombok.ToString;

import java.sql.Timestamp;

/***
 * {@link Translation} est l'objet utilisé pour stocker les joueurs bannis.
 */
@Entity
@Table(name = "translation")
@ToString
public abstract class Translation {

    /* Columns */

    @Key
    @Generated
    int id;

    @Column(name = "id_text")
    @Generated
    int textId;

    @Column
    Profil.Languages language;

    @Column
    String text;

    /* Links to other entity */

    @OneToMany(mappedBy = "id_text")
    MutableResult<BuildbattleThemeEntity> buildbattleThemes;

    @OneToMany(mappedBy = "id_text")
    MutableResult<CupEntity> cups;

    @OneToMany(mappedBy = "id_text")
    MutableResult<CosmetiqueEntity> cosmetics;
}
