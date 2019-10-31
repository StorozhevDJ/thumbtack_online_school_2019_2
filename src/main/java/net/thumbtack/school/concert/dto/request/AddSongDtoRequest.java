package net.thumbtack.school.concert.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class AddSongDtoRequest {

	public class Song {
		@NotBlank(message = "Название песни не задано. ")
		@Pattern(regexp = "^[а-яА-ЯёЁa-z\\w\\-_\\.?!*]{1,50}$", message = "Название песни может содержать только буквы, цифры, символы и быть длинной от 1 до 50 символов. ")
		private String songName; // название песни
		private List<@NotBlank(message = "Композитор не указан. ") @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z-_]{1,50}$", message = "Композитор может содержать только буквы и быть длинной от 1 до 50 символов. ") String> composer; // композитора
		private List<@NotBlank(message = "Автор не указан. ") @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z-_]{1,50}$", message = "Автор может содержать только буквы и быть длинной от 1 до 50 символов. ") String> author; // автора слов
		@NotBlank(message = "Исполнитель песни не задан. ")
		@Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9 -_\\.?!*]{1,50}$", message = "Исполнитель песни может содержать только буквы, цифры, символы и быть длинной от 1 до 50 символов. ")
		private String singer; // исполнителя (фамилия или название группы)
		@Positive(message = "Продолжительность песни должна быть положительной. ")
		private int length; // продолжительность песни в секундах

		public Song(String songName, List<String> composer, List<String> author, String singer, int length) {
			this.songName = songName;
			this.composer = composer;
			this.author = author;
			this.singer = singer;
			this.length = length;
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

	private List<Song> song;

	@NotBlank(message = "Сессия пользователя отсутствует. ")
	@Pattern(regexp = "^[0-9A-Fa-f]{8}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{12}$", message = "Не корректная сессия пользователя. ")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Song> getSong() {
		return song;
	}

	public void setSong(List<Song> song) {
		this.song = song;
	}
}
