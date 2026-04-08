package by.yurnerix.controller;

import by.yurnerix.dto.CreateResourceRequest;
import by.yurnerix.dto.ResourceResponse;
import by.yurnerix.dto.UpdateResourceRequest;
import by.yurnerix.enums.ResourceType;
import by.yurnerix.service.ResourceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public String getAllResources(Model model) {
        List<ResourceResponse> resources = resourceService.getAllResources();
        model.addAttribute("resources", resources);
        return "resources";
    }

    @GetMapping("/create")
    public String showCreateResourcePage(Model model) {
        CreateResourceRequest request = new CreateResourceRequest();

        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        request.setAvailableFrom(now);
        request.setAvailableTo(now.plusHours(2));
        request.setActive(true);

        model.addAttribute("createResourceRequest", request);
        model.addAttribute("resourceTypes", ResourceType.values());

        return "create-resource";
    }

    @PostMapping("/create")
    public String createResource(@ModelAttribute CreateResourceRequest createResourceRequest) {
        resourceService.createResource(createResourceRequest);
        return "redirect:/resources";
    }

    @GetMapping("/edit/{id}")
    public String showEditResourcePage(@PathVariable Long id, Model model) {
        ResourceResponse resource = resourceService.getResourceById(id);

        UpdateResourceRequest updateResourceRequest = new UpdateResourceRequest();
        updateResourceRequest.setName(resource.getName());
        updateResourceRequest.setDescription(resource.getDescription());
        updateResourceRequest.setType(resource.getType());
        updateResourceRequest.setLocation(resource.getLocation());
        updateResourceRequest.setCapacity(resource.getCapacity());
        updateResourceRequest.setActive(resource.getActive());
        updateResourceRequest.setAvailableFrom(resource.getAvailableFrom());
        updateResourceRequest.setAvailableTo(resource.getAvailableTo());

        model.addAttribute("resourceId", id);
        model.addAttribute("updateResourceRequest", updateResourceRequest);
        model.addAttribute("resourceTypes", ResourceType.values());

        return "edit-resource";
    }

    @PostMapping("/edit/{id}")
    public String updateResource(@PathVariable Long id,
                                 @ModelAttribute UpdateResourceRequest updateResourceRequest) {
        resourceService.updateResource(id, updateResourceRequest);
        return "redirect:/resources";
    }

    @PostMapping("/delete/{id}")
    public String deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return "redirect:/resources";
    }
}