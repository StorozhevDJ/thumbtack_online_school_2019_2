package net.thumbtack.school.concert.model;

import java.util.Objects;

public class Rating {

	private int id;
    private long rating;
    private String songId;
    private String user;

    public Rating() {

    }

    public Rating(String songId, int rating, String user) {
        setSongId(songId);
        setRating(rating);
        setUser(user);
    }

    public long getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songName) {
        this.songId = songName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
		if (!(o instanceof Rating)) return false;
		Rating rating1 = (Rating) o;
		return id == rating1.id &&
				rating == rating1.rating &&
				Objects.equals(songId, rating1.songId) &&
				Objects.equals(user, rating1.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, rating, songId, user);
	}
}
