package eventmanagement.kernel.core.domain.service;

import eventmanagement.kernel.core.domain.model.UserBO;
import eventmanagement.kernel.core.persistance.mapper.EntityBoMapper;
import eventmanagement.kernel.core.persistance.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final EntityBoMapper mapper;

    @Override
    public List<UserBO> findAll() {
        return userJpaRepository.findAll().stream()
                .map(mapper::toBO)
                .collect(Collectors.toList());
    }

    @Override
    public UserBO findById(Long id) {
        return userJpaRepository.findById(id)
                .map(mapper::toBO)
                .orElseThrow(() -> new EntityNotFoundException("User nicht gefunden: " + id));
    }

    @Override
    public UserBO create(UserBO user) {
        userJpaRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    try {
                        throw new ValidationException("E-Mail bereits vergeben.");
                    } catch (ValidationException e) {
                        throw new RuntimeException(e);
                    }
                });
        return mapper.toBO(userJpaRepository.save(mapper.toEntity(user)));
    }

    @Override
    public UserBO update(Long id, UserBO user) {
        var existing = userJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User nicht gefunden: " + id));
        // validate eMail change
        if (!existing.getEmail().equals(user.getEmail())) {
            userJpaRepository.findByEmail(user.getEmail())
                    .ifPresent(u -> {
                        try {
                            throw new ValidationException("E-Mail bereits vergeben.");
                        } catch (ValidationException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        user.setId(id);
        return mapper.toBO(userJpaRepository.save(mapper.toEntity(user)));
    }

    @Override
    public void delete(Long id) {
        if (!userJpaRepository.existsById(id)) {
            throw new EntityNotFoundException("User nicht gefunden: " + id);
        }
        userJpaRepository.deleteById(id);
    }
}
