package cz.muni.fi.pv254.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

/**
 *
 * @author Šimon Baláž
 */
@Table(name="recommendations")
@Entity
public class Recommendation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long steamId;

    //    @NotNull
//    @JoinColumn(name="users_id", nullable = false)
    @JoinColumn(name="users_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private User author;

    //    @NotNull
//    @JoinColumn(name="games_id", nullable = false)
    @JoinColumn(name="games_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Game game;

    @NotNull
    @Column(nullable = false)
    private boolean votedUp;

//    @NotNull
//    @Column(nullable = false)
    private Long votesUp;

//    @NotNull
//    @Column(nullable = false)
    private double weightedVoteScore;

    @Column
    private boolean earlyAccess;

    public Recommendation(@NotNull Long steamId, @NotNull User author,
                          @NotNull Game game, @NotNull boolean votedUp,
                          Long votesUp, double weightedVoteScore, boolean earlyAccess) {
        this.steamId = steamId;
        this.author = author;
        this.game = game;
        this.votedUp = votedUp;
        this.votesUp = votesUp;
        this.weightedVoteScore = weightedVoteScore;
        this.earlyAccess = earlyAccess;
    }

    public Recommendation() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSteamId() {
        return steamId;
    }

    public void setSteamId(Long steamId) {
        this.steamId = steamId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean getVotedUp() {
        return votedUp;
    }

    public void setVotedUp(boolean votedUp) {
        this.votedUp = votedUp;
    }

    public Long getVotesUp() {
        return votesUp;
    }

    public void setVotesUp(Long votesUp) {
        this.votesUp = votesUp;
    }

    public double getWeightedVoteScore() {
        return weightedVoteScore;
    }

    public void setWeightedVoteScore(Long weightedVoteScore) {
        this.weightedVoteScore = weightedVoteScore;
    }

    public boolean getEarlyAccess() {
        return earlyAccess;
    }

    public void setEarlyAccess(boolean earlyAccess) {
        this.earlyAccess = earlyAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return steamId == that.steamId &&
                votedUp == that.votedUp &&
                Double.compare(that.weightedVoteScore, weightedVoteScore) == 0 &&
                earlyAccess == that.earlyAccess &&
                Objects.equals(votesUp, that.votesUp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(steamId, votedUp, votesUp, weightedVoteScore, earlyAccess);
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", steamId=" + steamId +
                ", votedUp=" + votedUp +
                ", votesUp=" + votesUp +
                ", weightedVoteScore=" + weightedVoteScore +
                ", earlyAccess=" + earlyAccess +
                '}';
    }
}
