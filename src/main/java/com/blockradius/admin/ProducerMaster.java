package com.blockradius.admin;

public class ProducerMaster {

    private String type;        // e.g. "Album Master", "NFT Mix Album", "Song Master"
    private String projectName; // which project this belongs to
    private String savedAt;     // when the master was saved

    public ProducerMaster(String type, String projectName, String savedAt) {
        this.type = type;
        this.projectName = projectName;
        this.savedAt = savedAt;
    }

    public String getType() {
        return type;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getSavedAt() {
        return savedAt;
    }
}
