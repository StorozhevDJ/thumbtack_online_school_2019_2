package net.thumbtack.school.concert.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

public class AddRatingDtoRequest {
	
	@NotBlank(message = "Название песни не задано. ")
	@Pattern(regexp = "^[а-яА-ЯёЁa-z\\w\\-_\\.?!*]{1,50}$", message = "Название песни может содержать только буквы, цифры, символы и быть длинной от 1 до 50 символов. ")
	private String songName; // название песни
	@Range(min=1,max=5)
	private int rating; 
    @NotBlank(message = "Сессия пользователя отсутствует. ")
    @Pattern(regexp = "^[0-9A-Fa-f]{8}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{12}$", message = "Не корректная сессия пользователя. ")
    private String token;
    
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    

}
