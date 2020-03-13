package net.thumbtack.school.concert.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class GetSongsDtoRequest {

    private List<@Pattern(regexp = "^[а-яА-ЯёЁ\\w\\s\\-\\.]{0,50}$", message = "Композитор может содержать только буквы, цифры и быть длинной до 50 символов. ") String> composer; // композитора
    private List<@Pattern(regexp = "^[а-яА-ЯёЁ\\w\\s\\-\\.]{0,50}$", message = "Автор может содержать только буквы, цифры и быть длинной до 50 символов. ") String> author; // автора слов
    @Pattern(regexp = "^[а-яА-ЯёЁ\\w\\s\\-\\.\\?@!\\*\"]{0,50}$", message = "Исполнитель песни может содержать только буквы, цифры, символы и быть длинной от 1 до 50 символов. ")
    private String singer; // исполнителя (фамилия или название группы)
    @NotBlank(message = "Сессия пользователя отсутствует. ")
    @Pattern(regexp = "^[0-9A-Fa-f]{8}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{12}$", message = "Не корректная сессия пользователя. ")
    private String token;

    public GetSongsDtoRequest() {
    }

    public GetSongsDtoRequest(List<String> composer, List<String> author, String singer, String token) {
        setComposer(composer);
        setAuthor(author);
        setSinger(singer);
        setToken(token);
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
