package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.EditorDashboardResponse;

public interface ReviewDashboardService {

    EditorDashboardResponse getDashboard(
            Long paperId
    );

}