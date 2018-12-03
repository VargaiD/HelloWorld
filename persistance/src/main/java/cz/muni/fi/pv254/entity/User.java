package cz.muni.fi.pv254.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name= "users")
@Entity
public class User implements Serializable {
    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long steamId;

    private String passwordHash;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable=false,unique=true,length = 64)
//    @Pattern(regexp=".+@.+\\....?")
    private String email;

    @NotNull
    @Column(nullable = false)
    private Boolean isAdmin;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<Recommendation> recommendations = new HashSet<>();

    public User(Long steamId, String passwordHash, String name, String email, Boolean isAdmin) {
        this.steamId = steamId;
        this.passwordHash = passwordHash;
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public User() { }

    public void setId(Long id) { this.id = id; }

    public Long getId() { return id; }

    public Long getSteamId() {
        return steamId;
    }

    public void setSteamId(Long steamId) {
        this.steamId = steamId;
    }

    public String getPasswordHash() { return passwordHash; }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Boolean getIsAdmin() { return isAdmin; }

    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }

    public Set<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return steamId == user.steamId &&
                Objects.equals(passwordHash, user.passwordHash) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(isAdmin, user.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passwordHash, steamId, name, email, isAdmin);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id + "\'" +
                "steamId='" + steamId + "\'" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void addRecommendation(Recommendation recommendation){
        if (recommendations.contains(recommendation))
            return;

        recommendations.add(recommendation);
        recommendation.setAuthor(this);
    }

    public void removeRecommendation(Recommendation recommendation){
        if (!recommendations.contains(recommendation))
            return;

        recommendations.remove(recommendation);
        recommendation.setAuthor(null);
    }
}
