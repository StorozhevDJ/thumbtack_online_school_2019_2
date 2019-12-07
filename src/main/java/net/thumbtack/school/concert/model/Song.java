package net.thumbtack.school.concert.model;

import java.util.List;

public class Song {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((composer == null) ? 0 : composer.hashCode());
		result = prime * result + length;
		result = prime * result + ((singer == null) ? 0 : singer.hashCode());
		result = prime * result + ((songName == null) ? 0 : songName.hashCode());
		result = prime * result + ((userLogin == null) ? 0 : userLogin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Song other = (Song) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (composer == null) {
			if (other.composer != null)
				return false;
		} else if (!composer.equals(other.composer))
			return false;
		if (length != other.length)
			return false;
		if (singer == null) {
			if (other.singer != null)
				return false;
		} else if (!singer.equals(other.singer))
			return false;
		if (songName == null) {
			if (other.songName != null)
				return false;
		} else if (!songName.equals(other.songName))
			return false;
		if (userLogin == null) {
			if (other.userLogin != null)
				return false;
		} else if (!userLogin.equals(other.userLogin))
			return false;
		return true;
	}

    
}
