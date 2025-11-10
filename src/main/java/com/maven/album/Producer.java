package com.maven.album;

public class Producer {
    private long id;
    private String name;
    private String email;
    private String status;

    public Producer(String name, String email, String status) {
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
