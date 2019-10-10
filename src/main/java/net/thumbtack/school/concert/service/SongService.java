package net.thumbtack.school.concert.service;

import net.thumbtack.school.concert.exception.ServerException;

public class SongService {

	/**
	 * Радиослушатель добавляет новую песню на сервер.
	 * 
	 * @param requestJsonString содержит описание песни и token, полученный как
	 *                          результат выполнения команды регистрации
	 *                          радиослушателя.
	 * @return Метод при успешном выполнении возвращает пустой json.
	 * @throws ServerException Если же команду почему-то выполнить нельзя,
	 *                         возвращает json с элементом “error”
	 */
	public String addSong(String requestJsonString) throws ServerException {
		return requestJsonString;

	}

	/**
	 * Радиослушатель получает список песен.
	 * 
	 * @param requestJsonString содержит параметры для отбора песен и token,
	 *                          полученный как результат выполнения команды
	 *                          регистрации радиослушателя.
	 * @return Метод при успешном выполнении возвращает json с описанием всех песен.
	 * @throws ServerException Если же команду почему-то выполнить нельзя,
	 *                         возвращает json с элементом “error”
	 */
	public String getSongs(String requestJsonString) throws ServerException {
		return requestJsonString;

	}

}
