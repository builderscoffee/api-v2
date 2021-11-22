package eu.builderscoffee.api.common.data.tables;

import io.requery.*;
import io.requery.query.MutableResult;
import lombok.ToString;

import java.sql.Timestamp;

/***
 * {@link CupRound} est l'objet utilisé pour stocker des cups.
 */
@Entity
@Table(name = "cups")
@ToString
public abstract class Cup {

    /* Columns */

    @Key
    @Generated
    int id;

    @Column(name = "id_text")
    @ForeignKey(update = ReferentialAction.CASCADE, referencedColumn = "id_text")
    @ManyToOne
    TranslationEntity textId;

    @Column(name = "begin_date", nullable = false)
    Timestamp beginDate;

    @Column(name = "end_date", nullable = false)
    Timestamp endDate;

    @Column(name = "total_rounds")
    int totalRounds;

    /* Links to other entity */

    @OneToMany(mappedBy = "id_cup")
    MutableResult<CupRoundEntity> cupRounds;
}
