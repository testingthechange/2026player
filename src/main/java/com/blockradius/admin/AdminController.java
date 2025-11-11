package com.blockradius.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {

    // In-memory catalog of producers (dev-only)
    private final List<ProducerAccount> producers = new ArrayList<>();

    // In-memory magic link registry
    private final MagicLinkRegistry magicLinkRegistry = new MagicLinkRegistry();

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/")
    public String home() {
        // Root URL redirects to Producer page for now
        return "redirect:/producer";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    // Producer catalog + create form
    @GetMapping("/producer")
    public String producer(Model model) {
        model.addAttribute("producers", producers);
        return "producer";
    }

    @PostMapping("/producer/create")
    public String createProducer(@RequestParam String name,
                                 @RequestParam String company,
                                 @RequestParam String email,
                                 @RequestParam String phone) {
        if (name != null && !name.isBlank()) {
            ProducerAccount account = new ProducerAccount(
                    name.trim(),
                    company == null ? "" : company.trim(),
                    email == null ? "" : email.trim(),
                    phone == null ? "" : phone.trim()
            );
            producers.add(account);
        }
        return "redirect:/producer";
    }

    // Producer "log account" page
    @GetMapping("/producer/{id}")
    public String producerAccount(@PathVariable int id, Model model) {
        if (id < 0 || id >= producers.size()) {
            return "redirect:/producer";
        }
        ProducerAccount account = producers.get(id);
        model.addAttribute("producer", account);
        model.addAttribute("producerId", id);
        return "producer-account";
    }

    // Create project under a given producer
    @PostMapping("/producer/{id}/projects/create")
    public String createProject(@PathVariable int id,
                                @RequestParam String projectName,
                                @RequestParam String projectDate) {
        if (id < 0 || id >= producers.size()) {
            return "redirect:/producer";
        }
        if (projectName != null && !projectName.isBlank()) {
            ProducerAccount account = producers.get(id);
            account.addProject(new ProducerProject(
                    projectName.trim(),
                    projectDate == null ? "" : projectDate.trim()
            ));
        }
        return "redirect:/producer/" + id;
    }

    // Send / Resend Magic Link for a specific project
    @PostMapping("/producer/{id}/projects/{projectIndex}/magic-link")
    public String sendMagicLink(@PathVariable int id,
                                @PathVariable int projectIndex) {
        if (id < 0 || id >= producers.size()) {
            return "redirect:/producer";
        }
        ProducerAccount account = producers.get(id);
        if (projectIndex < 0 || projectIndex >= account.getProjects().size()) {
            return "redirect:/producer/" + id;
        }

        ProducerProject project = account.getProjects().get(projectIndex);

        LocalDateTime now = LocalDateTime.now();

        // Decide whether to create a new magicId or reuse existing
        String existingId = project.getMagicId();
        boolean expired = isLinkExpired(project, now);

        String magicId;
        if (existingId == null || existingId.isBlank() || expired) {
            // First send OR expired -> create new link
            magicId = UUID.randomUUID().toString();
            project.setMagicId(magicId);
        } else {
            // Resend: reuse existing magicId
            magicId = existingId;
        }

        // Register (or re-register) mapping: magicId -> (producerId, projectIndex)
        magicLinkRegistry.register(magicId, id, projectIndex);

        // Store timestamps
        String sentAt = now.format(FORMATTER);
        project.setMagicLinkSentAt(sentAt);

        // Expire 7 days from now
        LocalDateTime expires = now.plusDays(7);
        project.setMagicExpiresAt(expires.format(FORMATTER));

        // Later: call Magic API to actually send the email using this magicId.
        return "redirect:/producer/" + id;
    }

    // Admin override: expire a magic link immediately for a specific project
    @PostMapping("/producer/{id}/projects/{projectIndex}/magic-expire")
    public String expireMagicLink(@PathVariable int id,
                                  @PathVariable int projectIndex) {
        if (id < 0 || id >= producers.size()) {
            return "redirect:/producer";
        }
        ProducerAccount account = producers.get(id);
        if (projectIndex < 0 || projectIndex >= account.getProjects().size()) {
            return "redirect:/producer/" + id;
        }

        ProducerProject project = account.getProjects().get(projectIndex);

        // Set the expiration to "now" so any further clicks treat it as expired
        LocalDateTime now = LocalDateTime.now();
        project.setMagicExpiresAt(now.format(FORMATTER));

        return "redirect:/producer/" + id;
    }

    private boolean isLinkExpired(ProducerProject project, LocalDateTime now) {
        String expiresAt = project.getMagicExpiresAt();
        if (expiresAt == null || expiresAt.isBlank()) {
            return false;
        }
        try {
            LocalDateTime expiry = LocalDateTime.parse(expiresAt, FORMATTER);
            return now.isAfter(expiry);
        } catch (Exception e) {
            // If parsing fails, treat as expired for safety
            return true;
        }
    }

    // Master save endpoint: called when album / NFT album / song master is saved
    @PostMapping("/producer/{id}/projects/{projectIndex}/masters/create")
    public String createMaster(@PathVariable int id,
                               @PathVariable int projectIndex,
                               @RequestParam String masterType) {
        if (id < 0 || id >= producers.size()) {
            return "redirect:/producer";
        }
        ProducerAccount account = producers.get(id);
        if (projectIndex < 0 || projectIndex >= account.getProjects().size()) {
            return "redirect:/producer/" + id;
        }

        ProducerProject project = account.getProjects().get(projectIndex);

        LocalDateTime now = LocalDateTime.now();
        String savedAt = now.format(FORMATTER);

        // Create a master record linked to this project
        ProducerMaster master = new ProducerMaster(masterType, project.getName(), savedAt);
        account.addMaster(master);

        // For now, send admin back to producer page; in real player flow this
        // will be called from the Magic Link side and the user stays in the player.
        return "redirect:/producer/" + id;
    }

    // Preview work for a specific project
    @GetMapping("/producer/{id}/projects/{projectIndex}/preview")
    public String previewProject(@PathVariable int id,
                                 @PathVariable int projectIndex,
                                 Model model) {
        if (id < 0 || id >= producers.size()) {
            return "redirect:/producer";
        }
        ProducerAccount account = producers.get(id);
        if (projectIndex < 0 || projectIndex >= account.getProjects().size()) {
            return "redirect:/producer/" + id;
        }

        ProducerProject project = account.getProjects().get(projectIndex);

        model.addAttribute("producer", account);
        model.addAttribute("producerId", id);
        model.addAttribute("project", project);
        model.addAttribute("projectIndex", projectIndex);

        return "project-preview";
    }

    @GetMapping("/pending")
    public String pending() {
        return "pending";
    }

    @GetMapping("/export")
    public String export() {
        return "export";
    }

    @GetMapping("/accounts")
    public String accounts() {
        return "accounts";
    }

    // Expose registry for other controllers (like MagicLinkController)
    public MagicLinkRegistry getMagicLinkRegistry() {
        return magicLinkRegistry;
    }

    // Helper to get producers list from other controllers
    public List<ProducerAccount> getProducers() {
        return producers;
    }
}
