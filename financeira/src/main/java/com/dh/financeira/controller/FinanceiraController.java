package com.dh.financeira.controller;

import com.dh.financeira.models.Financeira;
import com.dh.financeira.service.FinanceiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class FinanceiraController {

    private final FinanceiraService service;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public ResponseEntity<List<Financeira>> getAll() {
        return ResponseEntity.ok().body(service.getAllFinanceira());
    }


    @PreAuthorize("hasRole('admin')")
    @GetMapping(path = "/check/admin")
    public String mod(){return "admin";}

    @PreAuthorize("hasRole('provider')")
    @GetMapping(path = "/check/provider")
    public String users(){return "provider";}

    @GetMapping(path = "/anon")
    public String anon(){return "anonon";}


}
