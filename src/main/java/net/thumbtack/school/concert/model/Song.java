package net.thumbtack.school.concert.model;

import java.util.List;

public class Song {

	private String songName; // название песни
    private List<String> composer; // композитора
    private List<String> author; // автора слов
	private String singer; // исполнителя (фамилия или название группы)
	private int length; // продолжительность песни в секундах
    private String userLogin; // Пользователь добавивший песню

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
    
    
    
}
