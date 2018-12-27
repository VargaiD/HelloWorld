package cz.muni.fi.pv254.entity;

import cz.muni.fi.pv254.enums.AlgorithmType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="algorithmRatings")
public class AlgorithmRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="users_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @NotNull
    @Column(nullable = false)
    private boolean votedUp;

    @Column(nullable = false)
    private AlgorithmType algorithmType;

    public AlgorithmRating(User author, @NotNull boolean votedUp, AlgorithmType algorithmType) {
        this.author = author;
        this.votedUp = votedUp;
        this.algorithmType = algorithmType;
    }

    public AlgorithmRating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
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
        AlgorithmRating that = (AlgorithmRating) o;
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
        return "AlgorithmRating{" +
                "id=" + id +
                ", author=" + author.getId() +
                ", votedUp=" + votedUp +
                ", algorithmType=" + algorithmType +
                '}';
    }
}
