package com.dot.ai.service;

import com.dot.ai.dtos.request.EmailDetails;

public interface EmailService {
    void sendEmailAlerts(EmailDetails emailDetails);
}
