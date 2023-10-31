package com.dot.ai.repositories;

import com.dot.ai.dtos.request.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailDetails, Long> {

}
