package com.errandGo.backend.service;

import com.errandGo.backend.entities.Account;
import com.errandGo.backend.entities.History;
import com.errandGo.backend.repositories.AccountRepository;
import com.errandGo.backend.repositories.HistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final AccountRepository accountRepository;

    // ✅ Get all history records
    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    // ✅ Get one history record by ID
    public History getHistoryById(Long id) {
        return historyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("History record not found"));
    }

    // ✅ Log a new action
    public History logAction(Long accountId, String action) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        History history = History.builder()
                .account(account)
                .action(action)
                .build();

        return historyRepository.save(history);
    }

    // ✅ Update a history record
    @Transactional
    public History updateHistory(Long id, String newAction) {
        History history = getHistoryById(id);
        history.setAction(newAction);
        return historyRepository.save(history);
    }

    // ✅ Delete a history record
    public void deleteHistory(Long id) {
        if (!historyRepository.existsById(id)) {
            throw new IllegalArgumentException("History record not found");
        }
        historyRepository.deleteById(id);
    }
}
