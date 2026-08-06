package com.confettiweather.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * One lyric block (verse, chorus, bridge, etc.) belonging to a {@link Song}.
 *
 * <p>The lyrics page renders blocks in {@code displayOrder} sequence, using
 * {@code sectionType} to apply different visual styles (chorus = gold accent,
 * bridge = sage accent, outro = italic, etc.).
 */
@Entity
@Table(name = "lyrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lyric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The song this lyric block belongs to. */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    /**
     * Human-readable section label displayed above the block.
     * e.g. "Verse 1", "Pre-Chorus", "Chorus", "Bridge", "Final Chorus", "Outro"
     */
    @Column(name = "section_label", nullable = false)
    private String sectionLabel;

    /**
     * Section type drives the visual style applied in Angular.
     * Values: VERSE | PRE_CHORUS | CHORUS | BRIDGE | OUTRO
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "section_type", nullable = false)
    private SectionType sectionType;

    /**
     * The actual lyric text. Newlines within the block are preserved
     * and rendered as {@code <br>} in the Angular template.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * Controls the order blocks appear on the lyrics page.
     * Blocks are fetched ORDER BY displayOrder ASC.
     */
    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    /** Lyric section type enum. */
    public enum SectionType {
        VERSE, PRE_CHORUS, CHORUS, BRIDGE, OUTRO
    }
}
