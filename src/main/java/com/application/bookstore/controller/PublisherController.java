package com.application.bookstore.controller;

import com.application.bookstore.domain.entity.Publisher;
import com.application.bookstore.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        Publisher saved = publisherService.createPublisher(publisher);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getPublishers() {
        return ResponseEntity.ok(publisherService.getPublishers());
    }
}
