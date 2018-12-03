package cz.muni.fi.pv254.dto;

import java.util.Objects;

public class WordDTO {
    private Long id;
    private GameDTO game;
    private double count;
    private String word;

    public WordDTO() {
    }

    public WordDTO(GameDTO game, double count, String word) {
        this.game = game;
        this.count = count;
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
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
        WordDTO wordDTO = (WordDTO) o;
        return getCount() == wordDTO.getCount() &&
                Objects.equals(getWord(), wordDTO.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCount(), getWord());
    }

    @Override
    public String toString() {
        return "WordDTO{" +
                "id=" + id +
                ", count=" + count +
                ", word='" + word + '\'' +
                '}';
    }
}
