
package by.yurnerix.service.impl;

import by.yurnerix.dto.CreateResourceRequest;
import by.yurnerix.dto.ResourceResponse;
import by.yurnerix.dto.UpdateResourceRequest;
import by.yurnerix.entity.Resource;
import by.yurnerix.enums.AuditActionType;
import by.yurnerix.exception.ResourceNotFoundException;
import by.yurnerix.mapper.ResourceMapper;
import by.yurnerix.repository.ResourceRepository;
import by.yurnerix.service.AuditLogService;
import by.yurnerix.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final AuditLogService auditLogService;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceMapper resourceMapper, AuditLogService auditLogService) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
        this.auditLogService = auditLogService;
    }

    @Override
    public ResourceResponse createResource(CreateResourceRequest request) {
        if (request.getAvailableFrom() == null || request.getAvailableTo() == null) {
            throw new RuntimeException("Необходимо указать диапазон доступности ресурса");
        }

        if (!request.getAvailableFrom().isBefore(request.getAvailableTo())) {
            throw new RuntimeException("Дата и время начала доступности должны быть раньше окончания");
        }

        Resource resource = new Resource();
        resource.setName(request.getName());
        resource.setDescription(request.getDescription());
        resource.setType(request.getType());
        resource.setLocation(request.getLocation());
        resource.setCapacity(request.getCapacity());
        resource.setActive(request.getActive());
        resource.setAvailableFrom(request.getAvailableFrom());
        resource.setAvailableTo(request.getAvailableTo());

        Resource saved = resourceRepository.save(resource);

        auditLogService.log(
                AuditActionType.RESOURCE_CREATED,
                null,
                null,
                saved.getId(),
                "Создан новый ресурс: " + saved.getName()
        );

        return resourceMapper.toResponse(saved);
    }

    @Override
    public ResourceResponse getResourceById(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ресурс не найден"));
        return resourceMapper.toResponse(resource);
    }

    @Override
    public List<ResourceResponse> getAllResources() {
        return resourceRepository.findAll()
                .stream()
                .map(resourceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ResourceResponse updateResource(Long id, UpdateResourceRequest request) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ресурс не найден"));

        if (request.getAvailableFrom() == null || request.getAvailableTo() == null) {
            throw new RuntimeException("Необходимо указать диапазон доступности ресурса");
        }

        if (!request.getAvailableFrom().isBefore(request.getAvailableTo())) {
            throw new RuntimeException("Дата и время начала доступности должны быть раньше окончания");
        }

        resourceMapper.updateEntity(resource, request);
        Resource saved = resourceRepository.save(resource);

        auditLogService.log(
                AuditActionType.RESOURCE_UPDATED,
                null,
                null,
                saved.getId(),
                "Обновлён ресурс: " + saved.getName()
        );

        return resourceMapper.toResponse(saved);
    }

    @Override
    public void deleteResource(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ресурс не найден"));

        auditLogService.log(
                AuditActionType.RESOURCE_DELETED,
                null,
                null,
                resource.getId(),
                "Удалён ресурс: " + resource.getName()
        );

        resourceRepository.delete(resource);
    }
}