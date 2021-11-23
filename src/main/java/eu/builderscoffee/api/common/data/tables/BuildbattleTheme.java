package eu.builderscoffee.api.common.data.tables;

import io.requery.*;
import io.requery.query.MutableResult;
import lombok.ToString;

/***
 * {@link BuildbattleTheme} est l'objet utilisé pour stocker thèmes de Buildbattles.
 */
@Entity
@Table(name = "buildbattles_themes")
@ToString
public abstract class BuildbattleTheme {

    /* Columns */

    @Key @Generated
    int id;


    /* Links to other entity */

    @OneToMany(mappedBy = "id_theme")
    MutableResult<BuildbattleThemeNameEntity> names;

    @OneToMany(mappedBy = "id_theme")
    MutableResult<BuildbattleEntity> buildbattles;

    @OneToMany(mappedBy = "id_theme")
    MutableResult<CupRoundEntity> cupRounds;
}
