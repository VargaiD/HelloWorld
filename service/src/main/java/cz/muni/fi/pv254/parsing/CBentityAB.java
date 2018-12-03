package cz.muni.fi.pv254.parsing;

import cz.muni.fi.pv254.dto.GameDTO;

public class CBentityAB {
        private GameDTO gameA;
        private GameDTO gameB;
        private double scoreAB;


        public GameDTO getGameA() {
            return gameA;
        }

        public void setGameA(GameDTO gameA) {
            this.gameA = gameA;
        }

        public GameDTO getGameB() {
            return gameB;
        }

        public void setGameB(GameDTO gameB) {
            this.gameB = gameB;
        }

        public double getScoreAB() {
            return scoreAB;
        }

        public void setScoreAB(double scoreAB) {
            this.scoreAB = scoreAB;
        }


}
