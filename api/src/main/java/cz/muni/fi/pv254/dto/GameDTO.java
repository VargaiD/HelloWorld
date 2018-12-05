package cz.muni.fi.pv254.dto;

import cz.muni.fi.pv254.entity.Word;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GameDTO {
    private Long id;
    private String name;
    private Long steamId;
    private Set<GenreDTO> genres = new HashSet<>();
    private String shortDescription;

    public Set<WordDTO> getWords() {
        return words;
    }

    public void setWords(Set<WordDTO> words) {
        this.words = words;
    }

    private Set<WordDTO> words = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSteamId() {
        return steamId;
    }

    public void setSteamId(Long steamId) {
        this.steamId = steamId;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDTO> genres) {
        this.genres = genres;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDTO gameDTO = (GameDTO) o;
        return steamId == gameDTO.steamId &&
                Objects.equals(name, gameDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, steamId);
    }

    @Override
    public String toString() {
        return "GameDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", steamId=" + steamId +
                '}';
    }
}
