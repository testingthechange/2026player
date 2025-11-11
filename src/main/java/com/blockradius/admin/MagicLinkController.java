package com.blockradius.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MagicLinkController {

    private final AdminController adminController;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MagicLinkController(AdminController adminController) {
        this.adminController = adminController;
    }

    // Magic / email will land here with a magicId that ties back to a project.
    @GetMapping("/magic/callback")
    public String magicCallback(@RequestParam(required = false, name = "magicId") String magicId,
                                Model model) {
        if (magicId != null && !magicId.isBlank()) {
            MagicLinkRegistry.MagicContext ctx =
                    adminController.getMagicLinkRegistry().resolve(magicId);

            if (ctx != null) {
                int producerId = ctx.getProducerId();
                int projectIndex = ctx.getProjectIndex();

                List<ProducerAccount> producers = adminController.getProducers();
                if (producerId >= 0 && producerId < producers.size()) {
                    ProducerAccount producer = producers.get(producerId);
                    if (projectIndex >= 0 && projectIndex < producer.getProjects().size()) {
                        ProducerProject project = producer.getProjects().get(projectIndex);

                        // Check expiry
                        String expiresAt = project.getMagicExpiresAt();
                        if (expiresAt != null && !expiresAt.isBlank()) {
                            try {
                                LocalDateTime expiry = LocalDateTime.parse(expiresAt, FORMATTER);
                                if (LocalDateTime.now().isAfter(expiry)) {
                                    // Link is expired -> show expired page
                                    model.addAttribute("producer", producer);
                                    model.addAttribute("project", project);
                                    model.addAttribute("expiresAt", expiresAt);
                                    return "magic-expired";
                                }
                            } catch (Exception e) {
                                // If parsing fails, treat as expired
                                model.addAttribute("producer", producer);
                                model.addAttribute("project", project);
                                model.addAttribute("expiresAt", expiresAt);
                                return "magic-expired";
                            }
                        }

                        // Not expired -> redirect to player with context
                        return "redirect:/player?producerId=" + producerId
                                + "&projectIndex=" + projectIndex;
                    }
                }
            }
        }

        // Fallback: no magicId or not found -> generic player home
        return "redirect:/player";
    }
}
