package cz.muni.fi.pv254.dto;

import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.User;

import java.util.Objects;

public class RecommendationDTO {
    private Long id;
    private Long steamId;
    private UserDTO author;
    private GameDTO game;
    private boolean votedUp;
    private Long votesUp;
    private double weightedVoteScore;
    private boolean earlyAccess;

    public RecommendationDTO(Long steamId, UserDTO author, GameDTO game, boolean votedUp){
        this.setGame(game);
        this.setAuthor(author);
        this.setSteamId(steamId);
        this.setVotedUp(votedUp);
    }
    public RecommendationDTO(){}

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

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    public boolean isVotedUp() {
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

    public void setWeightedVoteScore(double weightedVoteScore) {
        this.weightedVoteScore = weightedVoteScore;
    }

    public boolean isEarlyAccess() {
        return earlyAccess;
    }

    public void setEarlyAccess(boolean earlyAccess) {
        this.earlyAccess = earlyAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationDTO that = (RecommendationDTO) o;
        return steamId == that.steamId &&
                votedUp == that.votedUp &&
                Double.compare(that.weightedVoteScore, weightedVoteScore) == 0 &&
                earlyAccess == that.earlyAccess &&
                ((author == null && that.author == null) || Objects.equals(author.getId(), that.author.getId())) &&
                ((game == null && that.game == null) || Objects.equals(game.getId(), that.game.getId())) &&
                Objects.equals(votesUp, that.votesUp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(steamId, votedUp, votesUp, weightedVoteScore, earlyAccess);
    }

    @Override
    public String toString() {
        return "RecommendationDTO{" +
                "id=" + id +
                ", steamId=" + steamId +
                ", author=" + author +
                ", game=" + game +
                ", votedUp=" + votedUp +
                ", votesUp=" + votesUp +
                ", weightedVoteScore=" + weightedVoteScore +
                ", earlyAccess=" + earlyAccess +
                '}';
    }
}
