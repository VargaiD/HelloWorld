package cz.muni.fi.pv254.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Table(name= "words")
@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="games_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Game game;

    @NotNull
    @Column(nullable = false)
    private double count;

    @NotNull
    @Column(nullable = false)
    private String word;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return getCount() == word1.getCount() &&
                Objects.equals(getWord(), word1.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCount(), getWord());
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", count=" + count +
                ", word='" + word + '\'' +
                '}';
    }
}
