package com.example.REVIEW_SERVICE.entity;

import com.example.REVIEW_SERVICE.enums.EditorialDecision;
import com.example.REVIEW_SERVICE.enums.ReviewRecommendation;
import com.example.REVIEW_SERVICE.enums.ReviewScore;
import com.example.REVIEW_SERVICE.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_review_assignment",
                        columnNames = {
                                "paper_id",
                                "reviewer_id",
                                "revision_number"
                        }
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Research paper ID from Research Service.
     */
    @Column(
            name = "paper_id",
            nullable = false
    )
    private Long paperId;

    /**
     * Reviewer user ID.
     */
    @Column(
            name = "reviewer_id",
            nullable = false
    )
    private Long reviewerId;

    /**
     * Editor/Admin that assigned this review.
     */
    @Column(
            name = "assigned_by",
            nullable = false
    )
    private Long assignedBy;

    /**
     * Revision number being reviewed.
     */
    @Column(
            name = "revision_number",
            nullable = false
    )
    private Integer revisionNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status;

    @Enumerated(EnumType.STRING)
    private ReviewRecommendation recommendation;

    @Enumerated(EnumType.STRING)
    private ReviewScore overallScore;

    @Enumerated(EnumType.STRING)
    private EditorialDecision decision;

    /**
     * Visible to the author.
     */
    @Lob
    @Column(name = "comments_for_author")
    private String commentsForAuthor;

    /**
     * Visible only to editors.
     */
    @Lob
    @Column(name = "comments_for_editor")
    private String commentsForEditor;

    /**
     * Optional review attachment.
     */
    @Column(name = "attachment_url")
    private String attachmentUrl;

    private String declineReason;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private Integer reviewRound;

    private LocalDateTime invitationSentAt;

    private LocalDateTime acceptedAt;

    private LocalDateTime assignedAt;

    private LocalDateTime declinedAt;

    private LocalDateTime submittedAt;

    private LocalDateTime decisionAt;

    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}