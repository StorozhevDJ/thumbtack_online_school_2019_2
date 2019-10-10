package net.thumbtack.school.concert.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class AddSongDtoRequest {

	@NotBlank(message = "Название песни не задано. ")
	@Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9 -_\\.?!*]{1,50}$", message = "Название песни может содержать только буквы, цифры, символы и быть длинной от 1 до 50 символов. ")
	private String songName;    //название песни
	//@NotBlank(message = "Композитор не указан. ")
	@Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z -_]{1,50}$", message = "Композитор может содержать только буквы и быть длинной от 1 до 50 символов. ")
    private String[] composer;  //композитора
	//@NotBlank(message = "Автор не указан. ")
	@Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z -_]{1,50}$", message = "Автор может содержать только буквы и быть длинной от 1 до 50 символов. ")
	private String[] author;    //автора слов
	//@NotBlank(message = "Исполнитель песни не задано. ")
	@Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9 -_\\.?!*]{1,50}$", message = "Исполнитель песни может содержать только буквы, цифры, символы и быть длинной от 1 до 50 символов. ")	
    private String singer;      //исполнителя (фамилия или название группы)
	@Positive(message = "Продолжительность песни должна быть положительной. ")
    private int length;         //продолжительность песни в секундах
    
    
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
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}


}
