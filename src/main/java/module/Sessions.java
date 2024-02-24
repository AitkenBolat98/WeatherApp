package module;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="sessions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sessions {
    @Id
    @Column(name = "id",nullable = false)
    private UUID id;
    @OneToOne()
    @JoinColumn(name="user_id")
    private Users user;
    @Column(name= "expires_at",nullable = false)
    private LocalDateTime expiredAt;
}
