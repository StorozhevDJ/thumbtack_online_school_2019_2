package net.thumbtack.school.concert.model;

public class Comment {

    private String comment;
    private String author;
    private String songName;

    public Comment() {

    }

    public Comment(String songName, String author, String comment) {
        setComment(comment);
        setAuthor(author);
        setSongName(songName);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
