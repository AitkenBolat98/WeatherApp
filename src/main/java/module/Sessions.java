package module;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity(name="sessions")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne()
    @JoinColumn(name="user_id")
    private Users user;
    @Column(name= "expired_at")
    private Date expiredAt;
}
