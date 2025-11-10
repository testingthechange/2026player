package com.maven.album;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProducerService {

    private final List<Producer> producers = new ArrayList<>();
    private long counter = 1;

    public List<Producer> getAll() {
        return producers;
    }

    public void addProducer(String name, String email) {
        Producer p = new Producer(name, email, "active");
        p.setId(counter++);
        producers.add(p);
    }

    public Producer getById(Long id) {
        for (Producer p : producers) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
