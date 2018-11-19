package cz.muni.fi.pv254.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,unique=true,length = 64)
    @NotNull
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "genres")
    private Set<Game> games = new HashSet<>();

    public Genre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        if (games.contains(game))
            return;

        games.add(game);

        game.addGenre(this);
    }

    public void removeGame(Game game){
        if (!games.contains(game))
            return;

        games.add(game);

        game.removeGenre(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
