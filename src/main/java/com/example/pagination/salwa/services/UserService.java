package com.example.pagination.salwa.services;

import com.example.pagination.salwa.dto.PaginationResponse;
import com.example.pagination.salwa.models.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private static final String BASE_URL = "https://dummyjson.com/users";

    @Cacheable("users")
    public List<User> fetchAllUsers() {
        RestTemplate restTemplate = new RestTemplate();
        Map response;

        try {
            response = restTemplate.getForObject(BASE_URL + "?limit=1000", Map.class);
        } catch (Exception e) {
            throw new RuntimeException("External API unreachable");
        }

        if (response == null || !response.containsKey("users")) {
            throw new RuntimeException("Invalid response from external API");
        }

        List<Map<String, Object>> usersRaw =
                (List<Map<String, Object>>) response.get("users");

        return usersRaw.stream().map(u -> {
            User user = new User();
            user.setId((Integer) u.get("id"));
            user.setFirstName((String) u.get("firstName"));
            user.setLastName((String) u.get("lastName"));
            user.setEmail((String) u.get("email"));
            return user;
        }).toList();
    }

    public PaginationResponse<User> getUsers(int page, int size, String name) {

        if (page <= 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }

        List<User> users = fetchAllUsers();

        if (name != null && !name.isBlank()) {
            String keyword = name.toLowerCase();
            users = users.stream()
                    .filter(u ->
                            u.getFirstName().toLowerCase().contains(keyword) ||
                                    u.getLastName().toLowerCase().contains(keyword)
                    )
                    .toList();
        }

        int totalItems = users.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, totalItems);

        if (fromIndex >= totalItems) {
            return new PaginationResponse<>(
                    page, size, totalItems, totalPages, List.of()
            );
        }

        return new PaginationResponse<>(
                page,
                size,
                totalItems,
                totalPages,
                users.subList(fromIndex, toIndex)
        );
    }

}
