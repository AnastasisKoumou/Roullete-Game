public class Player {

    private String name;
    private String code;
    private String category;
    private int score;
    private int highestScore;

    public Player(String name, String code, int score, int highestScore) {
        this.name = name;
        this.code = code;

        //Automatos upologismos kathgorias
        if(score<500) {
            this.category = "None";
        }
        else if(score>=500 && score<4000) {
            this.category = "Bronze";
        }
        else if(score>=4000 && score<7000) {
            this.category="Silver";
        }
        else {
            this.category = "Gold";
        }

        this.score = score;
        this.highestScore = highestScore;
    }

    public void resetStats() {
        this.category = null;
        this.score = 500;
        this.highestScore = 500;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getHighestScore() {
        return highestScore;
    }
    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

}