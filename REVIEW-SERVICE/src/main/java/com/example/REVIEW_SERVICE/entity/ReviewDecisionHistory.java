package com.example.REVIEW_SERVICE.entity;

import com.example.REVIEW_SERVICE.enums.EditorialDecision;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_decision_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReviewDecisionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Review this decision belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "review_id",
            nullable = false
    )
    private Review review;

    /**
     * Previous editorial decision.
     */
    @Enumerated(EnumType.STRING)
    private EditorialDecision previousDecision;

    /**
     * Newly selected decision.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EditorialDecision decision;

    /**
     * Editor's comment.
     */
    @Lob
    private String comment;

    /**
     * Which editor made this decision.
     */
    @Column(nullable = false)
    private Long decidedBy;

    /**
     * Time of decision.
     */
    @CreatedDate
    @Column(nullable =false, updatable = false)
    private LocalDateTime decidedAt;
}