package com.learn.jernal.controller;

import com.learn.jernal.entity.JournalEntry;
import com.learn.jernal.services.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalService journalService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<JournalEntry> allRecords = journalService.getAll();
        if(allRecords!= null && !allRecords.isEmpty()){
            return new ResponseEntity<>(allRecords, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalService.saveEntry(journalEntry);
            return new ResponseEntity<>( journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalService.findById(myId);
        //here we can also use lambda expression
        if(journalEntry.isPresent())
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
       journalService.deleteById(myId);
       return  new ResponseEntity<>(true,HttpStatus.OK);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {

        JournalEntry existingEntry = journalService.findById(myId).orElse(null);
        if (existingEntry != null) {
            existingEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : existingEntry.getContent());
            journalService.saveEntry(existingEntry);
            return new ResponseEntity<>(existingEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
