package com.application.bookstore.service;

import com.application.bookstore.domain.entity.Publisher;
import com.application.bookstore.domain.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher createPublisher(Publisher publisher) {
        publisherRepository.findByName(publisher.getName());

        return publisherRepository.save(publisher);
    }

    public List<Publisher> getPublishers() {
        return publisherRepository.findAll();
    }
}
