package net.thumbtack.school.concert.model;

import java.util.List;
import java.util.Objects;

public class Song {

    private int id;
    private String songName; // название песни
    private List<String> composer; // композитора
    private List<String> author; // автора слов
    private String singer; // исполнителя (фамилия или название группы)
    private int length; // продолжительность песни в секундах
    private String userLogin; // Пользователь добавивший песню

    public Song() {
    }

    public Song(String songName, List<String> composer, List<String> author, String singer, int length,
                String userLogin) {
        setSongName(songName);
        setComposer(composer);
        setAuthor(author);
        setSinger(singer);
        setLength(length);
        setUserLogin(userLogin);
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public List<String> getComposer() {
        return composer;
    }

    public void setComposer(List<String> composer) {
        this.composer = composer;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return id == song.id &&
                length == song.length &&
                Objects.equals(songName, song.songName) &&
                Objects.equals(composer, song.composer) &&
                Objects.equals(author, song.author) &&
                Objects.equals(singer, song.singer) &&
                Objects.equals(userLogin, song.userLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, songName, composer, author, singer, length, userLogin);
    }
}
