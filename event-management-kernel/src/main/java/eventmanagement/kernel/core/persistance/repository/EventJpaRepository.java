package eventmanagement.kernel.core.persistance.repository;

import eventmanagement.kernel.core.persistance.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA-Repository for event entity.
 */
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {

    @Query("""
      SELECT eventEntity 
      FROM EventEntity eventEntity 
      JOIN eventEntity.participants partisipants
      WHERE partisipants.id = :userId
        AND eventEntity.startDate < :endDate
        AND eventEntity.endDate > :startDate
    """)
    List<EventEntity> findEventOverlapping(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime start,
            @Param("endDate")   LocalDateTime end
    );

    @Query("""
      SELECT eventEntity 
      FROM EventEntity eventEntity 
      JOIN eventEntity.participants participants
      WHERE participants.id = :userId
    """)
    List<EventEntity> findEventsByParticipantId(@Param("userId") Long userId);

}
