package module;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name="sessions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;
    @OneToOne()
    @JoinColumn(name="user_id")
    private Users user;
    @Column(name= "expired_at",nullable = false)
    private LocalDateTime expiredAt;
}
