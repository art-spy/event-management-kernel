package eventmanagement.kernel.api;

import eventmanagement.kernel.api.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * CRUD endpoints for /api/users
 */
@RequestMapping("/api/users")
//@Tag(name = "/api/users")
//@FeignClient(name = "User Endpoint", url = "${kernel.url}")
public interface UserEndpoint {

    @GetMapping
    ResponseEntity<List<UserDto>> getAllUsers();

    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<UserDto> createUser(@RequestBody UserDto dto);

    @PutMapping("/{id}")
    ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<UserDto> deleteUser(@PathVariable Long id);
}
