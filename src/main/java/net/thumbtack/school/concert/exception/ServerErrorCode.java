package net.thumbtack.school.concert.exception;

public enum ServerErrorCode {
    CONFIG_FILE_NOT_READ("Ошибка чтения конфигурационного файла \"%s\""),
    CONFIG_FILE_NOT_WRITED("Ошибка записи конфигурационного файла \"%s\""),
    SERVER_ALREADY_STARTED("Сервер уже запущен"),
    SERVER_NOT_STARTED("Сервер еще не запущен"),
    USERNAME_ALREADY_IN_USE("Логин %s уже используется"),
    LOGIN_INCORRECT("Неверное имя пользователя или пароль"),
    TOKEN_INCORRECT("Неверный идентификатор пользователя или пользователь отсутствует. "),
    BAD_REQUEST("Не корректный запрос! %s"),
    JSON_SYNTAX_ERROR("JSON syntax error"),
    SONG_NOT_FOUND("Песня не найдена"),
    ADD_RATING_ERROR("Невозможно добавить новую оценку. "),
    CHANGE_RATING_ERROR("Невозможно изменить рейтинг%s. "),
    DELETE_RATING_ERROR("Невозможно удалить рейтинг%s. "),
    ADD_COMMENT_ERROR("Невозможно добавить новый комментарий. "),
    CHANGE_COMMENT_ERROR("Невозможно изменить комментарий%s. "),
    DELETE_COMMENT_ERROR("Невозможно удалить комментарий. "),
    OTHER_ERROR("%s");

    private String errorString;

    ServerErrorCode(String text) {
        this.errorString = text;
    }

    public String getText() {
        return this.errorString;
    }
}
