package com.bank.recipebook.repository;

import com.bank.recipebook.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByActionIgnoreCase(String action);

    List<AuditLog> findByUserEmailIgnoreCase(String userEmail);

    List<AuditLog> findByActionIgnoreCaseAndUserEmailIgnoreCase(String action, String userEmail);
}