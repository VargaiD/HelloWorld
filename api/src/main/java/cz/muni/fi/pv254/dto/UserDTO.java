package cz.muni.fi.pv254.dto;

import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.Recommendation;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private Long id;
    private String passwordHash;
    private String name;
    private String email;
    private Boolean isAdmin;
    private Long steamId;

    public Long getSteamId() {
        return steamId;
    }

    public void setSteamId(Long steamId) {
        this.steamId = steamId;
    }

    public UserDTO() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPasswordHash() { return passwordHash; }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Boolean getIsAdmin() { return isAdmin; }

    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof UserDTO)) return false;

        UserDTO userDto = (UserDTO) o;

        if (name != null ? !name.equals(userDto.name) : userDto.name != null) return false;
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) return false;
        return email != null ? email.equals(userDto.email) : userDto.email == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", steamId=" + steamId +
//                ", passwordHash='" + passwordHash + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
