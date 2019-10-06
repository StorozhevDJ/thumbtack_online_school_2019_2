package net.thumbtack.school.concert.exception;

public enum ServerErrorCode {
	CONFIG_FILE_NOT_READ("Ошибка чтения конфигурационного файла \"%s\""),
	CONFIG_FILE_NOT_WRITED("Ошибка записи конфигурационного файла \"%s\""),
	SERVER_ALREADY_STARTED("Сервер уже запущен"),
	SERVER_NOT_STARTED("Сервер еще не запущен"),
	USERNAME_ALREADY_IN_USE("Логин %s уже используется"),
	LOGIN_INCORRECT("Неверное имя пользователя или пароль"),
	OTHER_ERROR("%s");

	private String errorString;

	ServerErrorCode(String text) {
		this.errorString = text;
	}
	
	public String getText() {
		return this.errorString;
	}
}
