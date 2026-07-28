package com.example.REVIEW_SERVICE.service;

import com.example.REVIEW_SERVICE.dto.PaperDashboardResponse;

public interface ReviewDashboardService {

    PaperDashboardResponse getDashboard(
            Long paperId
    );

}