package com.maven.album;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    // ✅ Producer profile page
    @GetMapping("/producer/{id}")
    public String producerProfile(@PathVariable int id, Model model) {
        Producer producer = producerService.getProducerById(id);
        if (producer == null) {
            return "redirect:/admin/producers";
        }
        model.addAttribute("producer", producer);
        return "producer/profile";
    }

    // ✅ Add a project to a producer
    @PostMapping("/producer/{id}/projects/add")
    public String addProject(@PathVariable int id,
                             @RequestParam String projectName,
                             @RequestParam String startDate) {
        Producer producer = producerService.getProducerById(id);
        if (producer != null) {
            Project project = new Project(projectName, startDate);
            producer.getProjects().add(project);
        }
        return "redirect:/producer/" + id;
    }

    // ✅ Send magic link for a project (dummy for now)
    @PostMapping("/producer/{id}/projects/{index}/send-link")
    public String sendMagicLink(@PathVariable int id,
                                @PathVariable int index,
                                @RequestParam String email,
                                Model model) {
        Producer producer = producerService.getProducerById(id);
        if (producer != null && index < producer.getProjects().size()) {
            Project project = producer.getProjects().get(index);
            if (!project.isMagicLinkSent()) {
                project.setMagicLinkSent(true);
                project.setDateSent(java.time.LocalDate.now().toString());

                String link = "/worksheet/" + id + "/" + index + "/home";
                model.addAttribute("message",
                        "Magic link sent to " + email + ": " + link);
            }
        }
        return "redirect:/producer/" + id;
    }
}
