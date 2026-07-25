package com.example.REVIEW_SERVICE.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerSummaryResponse {

    private Long id;
    private String fullName;
    private String email;
    private String role;
    private boolean enabled;
    private boolean emailVerified;
    private boolean accountNonLocked;
    private String institution;
    private String faculty;
    private String department;

}