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

    @Column(name = "id_text")
    @ForeignKey(update = ReferentialAction.CASCADE, referencedColumn = "id_text")
    @ManyToOne
    TranslationEntity textId;

    /* Links to other entity */

    @OneToMany(mappedBy = "id_theme")
    MutableResult<BuildbattleEntity> buildbattles;

    @OneToMany(mappedBy = "id_theme")
    MutableResult<CupRoundEntity> cupRounds;
}
