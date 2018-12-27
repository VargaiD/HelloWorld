package cz.muni.fi.pv254.dto;

import cz.muni.fi.pv254.enums.AlgorithmType;

import java.util.Objects;

public class AlgorithmRatingDTO {
    private Long id;
    private UserDTO author;
    private boolean votedUp;
    private AlgorithmType algorithmType;

    public AlgorithmRatingDTO(UserDTO author, boolean votedUp, AlgorithmType algorithmType) {
        this.author = author;
        this.votedUp = votedUp;
        this.algorithmType = algorithmType;
    }

    public AlgorithmRatingDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public boolean isVotedUp() {
        return votedUp;
    }

    public void setVotedUp(boolean votedUp) {
        this.votedUp = votedUp;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlgorithmRatingDTO that = (AlgorithmRatingDTO) o;
        return isVotedUp() == that.isVotedUp() &&
                getAuthor().getId().equals(that.getAuthor().getId()) &&
                getAlgorithmType() == that.getAlgorithmType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor().getId(), isVotedUp(), getAlgorithmType());
    }

    @Override
    public String toString() {
        return "AlgorithmRatingDTO{" +
                "id=" + id +
                ", author=" + author +
                ", votedUp=" + votedUp +
                ", algorithmType=" + algorithmType +
                '}';
    }
}
