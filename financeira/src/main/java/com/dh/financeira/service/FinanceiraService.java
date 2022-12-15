package com.dh.financeira.service;

import com.dh.financeira.models.Financeira;
import com.dh.financeira.repositories.FinanceiraRepository;
import com.dh.financeira.models.Financeira;
import com.dh.financeira.repositories.FinanceiraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceiraService {

    private final FinanceiraRepository repository;

    public List<Financeira> getAllFinanceira() {
        return repository.findAll();
    }

}
