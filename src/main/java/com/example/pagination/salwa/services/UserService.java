package com.example.pagination.salwa.services;

import com.example.pagination.salwa.dto.PaginationResponse;
import com.example.pagination.salwa.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private static final String BASE_URL = "https://dummyjson.com/users";

    public PaginationResponse<User> getUsers(int page, int size) {

        if (page <= 0 || size <= 0) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }

        RestTemplate restTemplate = new RestTemplate();
        Map response;

        try {
            response = restTemplate.getForObject(BASE_URL + "?limit=1000", Map.class);
        } catch (Exception e) {
            return new PaginationResponse<>(
                    page, size, 0, 0, List.of()
            );
        }

        if (response == null || !response.containsKey("users")) {
            return new PaginationResponse<>(
                    page, size, 0, 0, List.of()
            );
        }

        List<Map<String, Object>> usersRaw =
                (List<Map<String, Object>>) response.get("users");

        List<User> users = usersRaw.stream().map(u -> {
            User user = new User();
            user.setId((Integer) u.get("id"));
            user.setFirstName((String) u.get("firstName"));
            user.setLastName((String) u.get("lastName"));
            user.setEmail((String) u.get("email"));
            return user;
        }).toList();

        Integer totalFromApi = (Integer) response.get("total");
        int totalItems = totalFromApi != null ? totalFromApi : users.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, users.size());

        if (fromIndex >= users.size()) {
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
