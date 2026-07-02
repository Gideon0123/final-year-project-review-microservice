package com.example.REVIEW_SERVICE.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "review")
@Getter
@Setter
public class ReviewProperties {

    /**
     * Number of days given to a reviewer
     * to complete a review.
     */
    private int deadlineDays = 14;

}