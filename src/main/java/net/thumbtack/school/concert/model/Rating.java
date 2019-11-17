package net.thumbtack.school.concert.model;

public class Rating {

    private long rating;
    private String songName;
    private String user;

    public Rating() {

    }

    public Rating(String songName, int rating, String user) {
        setSongName(songName);
        setRating(rating);
        setUser(user);
    }

    public long getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
