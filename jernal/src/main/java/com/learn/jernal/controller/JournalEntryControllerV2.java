package com.learn.jernal.controller;

import com.learn.jernal.entity.JournalEntry;
import com.learn.jernal.services.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalService journalService;

    @GetMapping
    public List<JournalEntry> getAll() {

        return journalService.getAll();
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalService.saveEntry(journalEntry);

        return true;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {

        return journalService.findById(myId).orElse(null);
    }

    @DeleteMapping("/id/{myId}")
    public Boolean deleteJournalEntryById(@PathVariable ObjectId myId) {
       journalService.deleteById(myId);
       return  true;
    }

    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {

        JournalEntry existingEntry = journalService.findById(myId).orElse(null);
        if (existingEntry != null) {
            existingEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : existingEntry.getContent());
        }
        journalService.saveEntry(existingEntry);

        return existingEntry;
    }
}
