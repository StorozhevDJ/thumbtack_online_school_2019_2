package net.thumbtack.school.concert.model;

import java.util.List;

public class Song {

	private String songName; // название песни
    private List<String> composer; // композитора
    private List<String> author; // автора слов
	private String singer; // исполнителя (фамилия или название группы)
	private int length; // продолжительность песни в секундах

    public Song(String songName, List<String> composer, List<String> author, String singer, int length) {
        setSongName(songName);
        setComposer(composer);
        setAuthor(author);
        setSinger(singer);
        setLength(length);
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
}
