package com.bank.recipebook.controller;

import com.bank.recipebook.model.AuditLog;
import com.bank.recipebook.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<AuditLog> getAuditLogs(
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String email
    ) {
        return auditService.findLogs(action, email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public AuditLog getAuditLogById(@PathVariable Long id) {
        return auditService.findById(id);
    }
}