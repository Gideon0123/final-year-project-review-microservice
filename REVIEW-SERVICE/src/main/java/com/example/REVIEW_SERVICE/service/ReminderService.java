package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.entity.Review;

public interface ReminderService {

    void sendDeadlineReminder(Review review);

}