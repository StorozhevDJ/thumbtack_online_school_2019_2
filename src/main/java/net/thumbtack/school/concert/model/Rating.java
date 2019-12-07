package net.thumbtack.school.concert.model;

public class Rating {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (rating ^ (rating >>> 32));
		result = prime * result + ((songId == null) ? 0 : songId.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Rating other = (Rating) obj;
		if (rating != other.rating)
			return false;
		if (songId == null) {
			if (other.songId != null)
				return false;
		} else if (!songId.equals(other.songId))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
    
    
}
