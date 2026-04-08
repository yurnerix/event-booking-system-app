package by.yurnerix.mapper;

import by.yurnerix.dto.ResourceResponse;
import by.yurnerix.dto.UpdateResourceRequest;
import by.yurnerix.entity.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {

    public ResourceResponse toResponse(Resource resource) {
        if (resource == null) {
            return null;
        }

        return new ResourceResponse(
                resource.getId(),
                resource.getName(),
                resource.getDescription(),
                resource.getType(),
                resource.getLocation(),
                resource.getCapacity(),
                resource.getActive(),
                resource.getAvailableFrom(),
                resource.getAvailableTo()
        );
    }

    public void updateEntity(Resource resource, UpdateResourceRequest request) {
        resource.setName(request.getName());
        resource.setDescription(request.getDescription());
        resource.setType(request.getType());
        resource.setLocation(request.getLocation());
        resource.setCapacity(request.getCapacity());
        resource.setActive(request.getActive());
        resource.setAvailableFrom(request.getAvailableFrom());
        resource.setAvailableTo(request.getAvailableTo());
    }
}