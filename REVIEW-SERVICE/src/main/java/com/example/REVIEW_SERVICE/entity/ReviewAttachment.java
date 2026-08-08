package com.example.REVIEW_SERVICE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "review_attachments",
        indexes = {
                @Index(
                        name = "idx_review_attachment_review_id",
                        columnList = "review_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "review_id",
            nullable = false
    )
    private Review review;

    /**
     * Original filename supplied by the user.
     *
     * Example:
     * reviewer-comments.pdf
     */
    @Column(
            name = "original_filename",
            nullable = false,
            length = 255
    )
    private String originalFilename;

    /**
     * The actual object name used inside MinIO.
     *
     * Example:
     * reviews/5d8.../8a7...-reviewer-comments.pdf
     */
    @Column(
            name = "object_key",
            nullable = false,
            unique = true,
            length = 500
    )
    private String objectKey;

    /**
     * MIME type.
     *
     * Example:
     * application/pdf
     */
    @Column(
            name = "content_type",
            nullable = false,
            length = 150
    )
    private String contentType;

    /**
     * Size of uploaded file in bytes.
     */
    @Column(
            name = "file_size",
            nullable = false
    )
    private Long fileSize;

    /**
     * MinIO bucket containing the object.
     */
    @Column(
            name = "bucket_name",
            nullable = false,
            length = 100
    )
    private String bucketName;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();

    }
}