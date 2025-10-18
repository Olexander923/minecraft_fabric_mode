package shadrin.dev.entity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid", nullable = false)
    private UUID playerUuid;
    @Column(name = "text", nullable = false,length = 256)
    private String text;

    public MessageEntity(UUID playerUuid, String text) {
        this.playerUuid = playerUuid;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public String getText() {
        return text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayerUuid(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    public void setText(String text) {
        this.text = text;
    }
}
