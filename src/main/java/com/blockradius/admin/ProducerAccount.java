package com.blockradius.admin;

import java.util.ArrayList;
import java.util.List;

public class ProducerAccount {

    private String name;
    private String company;
    private String email;
    private String phone;

    private final List<ProducerProject> projects = new ArrayList<>();
    private final List<ProducerMaster> masters = new ArrayList<>();

    public ProducerAccount(String name, String company, String email, String phone) {
        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<ProducerProject> getProjects() {
        return projects;
    }

    public void addProject(ProducerProject project) {
        projects.add(project);
    }

    public List<ProducerMaster> getMasters() {
        return masters;
    }

    public void addMaster(ProducerMaster master) {
        masters.add(master);
    }
}
