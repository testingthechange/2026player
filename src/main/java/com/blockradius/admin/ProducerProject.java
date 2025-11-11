package com.blockradius.admin;

public class ProducerProject {
    private String name;
    private String date;

    // Magic link metadata
    private String magicId;           // unique id for this project's magic link
    private String magicLinkSentAt;   // when magic link was sent
    private String magicExpiresAt;    // when magic link expires

    public ProducerProject(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getMagicId() {
        return magicId;
    }

    public void setMagicId(String magicId) {
        this.magicId = magicId;
    }

    public String getMagicLinkSentAt() {
        return magicLinkSentAt;
    }

    public void setMagicLinkSentAt(String magicLinkSentAt) {
        this.magicLinkSentAt = magicLinkSentAt;
    }

    public String getMagicExpiresAt() {
        return magicExpiresAt;
    }

    public void setMagicExpiresAt(String magicExpiresAt) {
        this.magicExpiresAt = magicExpiresAt;
    }
}
