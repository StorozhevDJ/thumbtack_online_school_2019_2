package net.thumbtack.school.concert.model;

import java.util.Objects;

public class Comment {

	private int id;
    private String comment;
    private String author;
    private String songId;

    public Comment() {

    }

    public Comment(String songId, String author, String comment) {
        setComment(comment);
        setAuthor(author);
        setSongId(songId);
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

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songName) {
        this.songId = songName;
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
		if (!(o instanceof Comment)) return false;
		Comment comment1 = (Comment) o;
		return id == comment1.id &&
				Objects.equals(comment, comment1.comment) &&
				Objects.equals(author, comment1.author) &&
				Objects.equals(songId, comment1.songId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, comment, author, songId);
	}
}
