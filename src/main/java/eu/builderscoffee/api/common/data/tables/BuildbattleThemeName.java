package eu.builderscoffee.api.common.data.tables;

import io.requery.*;
import lombok.ToString;

/***
 * {@link BuildbattleThemeName} est l'objet utilisé pour stocker thèmes de Buildbattles.
 */
@Entity
@Table(name = "buildbattles_themes_names")
@ToString
public abstract class BuildbattleThemeName {

    /* Columns */

    @Key @Generated
    int id;

    @Column(name = "id_theme", nullable = false)
    @ForeignKey(update = ReferentialAction.CASCADE, referencedColumn = "id")
    @ManyToOne
    BuildbattleThemeEntity theme;

    @Column
    Profil.Languages language;

    @Column
    String name;
}
