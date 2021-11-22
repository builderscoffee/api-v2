package eu.builderscoffee.api.common.data.tables;

import io.requery.*;
import io.requery.query.MutableResult;
import lombok.ToString;

/***
 * {@link BuildbattleThemeNames} est l'objet utilisé pour stocker thèmes de Buildbattles.
 */
@Entity
@Table(name = "buildbattles_themes_names")
@ToString
public abstract class BuildbattleThemeNames {

    /* Columns */

    @Key @Generated
    int id;

    @Column(name = "id_theme", nullable = false)
    @ForeignKey(update = ReferentialAction.CASCADE, referencedColumn = "id")
    @ManyToOne
    BuildbattleThemeEntity cup;

    @Column
    Profil.Languages language;

    @Column
    String name;
}
