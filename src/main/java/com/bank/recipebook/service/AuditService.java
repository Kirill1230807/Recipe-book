package com.bank.recipebook.service;

import com.bank.recipebook.exception.ResourceNotFoundException;
import com.bank.recipebook.model.AuditLog;
import com.bank.recipebook.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void log(String action, String entityName, Long entityId, String details) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (auth != null && auth.isAuthenticated()) ? auth.getName() : "anonymous";

        AuditLog entry = AuditLog.builder()
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .userEmail(email)
                .details(details)
                .createdAt(LocalDateTime.now())
                .build();

        auditLogRepository.save(entry);
    }

    public List<AuditLog> findLogs(String action, String email) {
        if (action != null && email != null) {
            return auditLogRepository.findByActionIgnoreCaseAndUserEmailIgnoreCase(action, email);
        }
        if (action != null) {
            return auditLogRepository.findByActionIgnoreCase(action);
        }
        if (email != null) {
            return auditLogRepository.findByUserEmailIgnoreCase(email);
        }
        return auditLogRepository.findAll();
    }

    public AuditLog findById(Long id) {
        return auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit log with id " + id + " not found"));
    }
}