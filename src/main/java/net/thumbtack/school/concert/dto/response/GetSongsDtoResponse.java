package net.thumbtack.school.concert.dto.response;

public class GetSongsDtoResponse {
    private String songName;   //название песни
    private String[] composer; //композитора
    private String[] author;   //автора слов
    private String singer;     //исполнителя (фамилия или название группы)
    //private int length;        //продолжительность песни в секундах
    private String userName;	//Имя пользователя предложившего песню
    private float rating;		//Средняя оценка песни
    private String[] coments;	//Все комментарии к этому предложению
    
    
	public String getSongName() {
		return songName;
	}
	
	public void setSongName(String songName) {
		this.songName = songName;
	}
	
	public String[] getComposer() {
		return composer;
	}
	
	public void setComposer(String[] composer) {
		this.composer = composer;
	}
	
	public String[] getAuthor() {
		return author;
	}
	
	public void setAuthor(String[] author) {
		this.author = author;
	}
	
	public String getSinger() {
		return singer;
	}
	
	public void setSinger(String singer) {
		this.singer = singer;
	}
	
	/*public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}*/
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public float getRating() {
		return rating;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public String[] getComents() {
		return coments;
	}
	
	public void setComents(String[] coments) {
		this.coments = coments;
	}
}
