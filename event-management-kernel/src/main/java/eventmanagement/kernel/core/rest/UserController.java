package eventmanagement.kernel.core.rest;

import eventmanagement.kernel.api.UserEndpoint;
import eventmanagement.kernel.api.model.UserDto;
import eventmanagement.kernel.core.domain.error.GenericEventmanagementException;
import eventmanagement.kernel.core.domain.service.UserService;
import eventmanagement.kernel.core.rest.mapper.BoDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CRUD endpoint controller for /api/users
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements UserEndpoint {

    private final UserService userService;
    private final BoDtoMapper mapper;

    @GetMapping
    @Override
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAllUsers().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);

    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mapper.toDto(userService.findUserById(id)));
        } catch (GenericEventmanagementException e) {
            return generateErrorResponse(e);
        }
    }

    @PostMapping
    @Override
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        try {
            return ResponseEntity.ok(
                    mapper.toDto(userService.createUser(mapper.toBo(dto)))
            );
        } catch (GenericEventmanagementException e) {
            return generateErrorResponse(e);
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        try {
            return ResponseEntity.ok(
                    mapper.toDto(userService.updateUser(id, mapper.toBo(dto)))
            );
        } catch (GenericEventmanagementException e) {
            return generateErrorResponse(e);
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (GenericEventmanagementException e) {
            return generateErrorResponse(e);
        }
    }

    private ResponseEntity<UserDto> generateErrorResponse(GenericEventmanagementException e) {
        UserDto errorDto = new UserDto();
        errorDto.getMessages().put("errorType", e.getErrorType());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

}
