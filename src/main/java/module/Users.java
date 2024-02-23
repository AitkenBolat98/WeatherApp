package module;

import lombok.*;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false,nullable = false)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String login;
    @Column()
    private String password;
    @OneToOne(mappedBy = "user")
    private Sessions session;

    @OneToOne(mappedBy = "user")
    private Locations location;
}
