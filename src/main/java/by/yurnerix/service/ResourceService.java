package by.yurnerix.service;

import by.yurnerix.dto.CreateResourceRequest;
import by.yurnerix.dto.ResourceResponse;
import by.yurnerix.dto.UpdateResourceRequest;

import java.util.List;

public interface ResourceService {
    ResourceResponse createResource(CreateResourceRequest request);
    ResourceResponse getResourceById(Long resourceId);
    List<ResourceResponse> getAllResources();
    ResourceResponse updateResource(Long resourceId, UpdateResourceRequest request);
    void deleteResource(Long resourceId);
}