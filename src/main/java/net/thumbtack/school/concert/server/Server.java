package net.thumbtack.school.concert.server;

import java.io.FileNotFoundException;

import com.google.gson.Gson;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.response.ErrorDtoResponse;
import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;
import net.thumbtack.school.concert.service.CommentService;
import net.thumbtack.school.concert.service.RatingService;
import net.thumbtack.school.concert.service.SongService;
import net.thumbtack.school.concert.service.UserService;

public class Server {

    private UserService userService;
    private SongService songService;
    private RatingService ratingService;
    private CommentService commentService;

    /**
     * Производит всю необходимую инициализацию и запускает сервер.
     *
     * @param savedDataFileName - имя файла, в котором было сохранено состояние
     *                          сервера. Если savedDataFileName == null,
     *                          восстановление состояния не производится, сервер
     *                          стартует “с нуля”.
     * @throws ServerException
     */
    public void startServer(String savedDataFileName) throws ServerException {
        if (isServerStarted()) {
            throw new ServerException(ServerErrorCode.SERVER_ALREADY_STARTED);
        }

        if ((savedDataFileName != null) && (!savedDataFileName.isEmpty())) {
            // Start server with settings from config file
            try {
                new DataBase().open(savedDataFileName);
            } catch (Exception e) {
                throw new ServerException(ServerErrorCode.CONFIG_FILE_NOT_READ, e.getMessage());
            }
        } else {
            // Start server with default data
            new DataBase().open();
        }
        userService = new UserService();
        songService = new SongService();
        ratingService = new RatingService();
        commentService = new CommentService();
    }

    /**
     * Останавливает сервер и записывает все его содержимое в файл сохранения с
     * именем savedDataFileName. Если savedDataFileName == null, запись содержимого
     * не производится.
     *
     * @param savedDataFileName - имя файла, в котором было сохранено состояние
     *                          сервера.
     * @throws ServerException
     */
    public void stopServer(String savedDataFileName) throws ServerException {
        if (!isServerStarted()) {
            throw new ServerException(ServerErrorCode.SERVER_NOT_STARTED);
        }
        // If the filename is not empty
        if ((savedDataFileName != null) && (!savedDataFileName.isEmpty())) {
            // Save Date to file
            try {
                new DataBase().close(savedDataFileName);
            } catch (FileNotFoundException e) {
                throw new ServerException(ServerErrorCode.CONFIG_FILE_NOT_WRITED);
            }
        } else {
            // Exit without saving data
            new DataBase().close();
        }
        userService = null;
        songService = null;
        ratingService = null;
        commentService = null;
    }

    /**
     * @return boolean - Server started state
     */
    public boolean isServerStarted() {
        return (userService != null) && (songService != null);
    }

    /**
     * Register new user
     * Радиослушатели, желающие принять участие в составлении программы концерта,
     * должны зарегистрироваться на сервере. При регистрации они указывают:
     * фамилию
     * имя
     * логин и пароль для входа на сервер.
     * Введенные при регистрации данные изменению в дальнейшем не подлежат.
     * Радиослушатели не могут регистрироваться два или более раза на сервере.
     *
     * @param jsonRequest - JSON string with Username and password for new user
     */
    public String registerUser(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return userService.registerUser(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Sign in user
     * Вышедший с сервера радиослушатель может войти на сервер снова.
     * При этом ему достаточно ввести свои логин и пароль.
     *
     * @param jsonRequest - JSON string with Username and password for login user
     */
    public String loginUser(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return userService.loginUser(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Logout user
     * Зарегистрированный на сервере радиослушатель может выйти с сервера.
     *
     * @param jsonRequest - JSON string with user UUID for logout
     */
    public String logoutUser(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return userService.logoutUser(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Delete User from DataBase (with deleting rating and songs)
     * Зарегистрированный на сервере радиослушатель может покинуть сервер,
     * в этом случае вся информация о нем удаляется
     *
     * @param jsonRequest
     * @return
     */
    public String deleteUser(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return userService.deleteUser(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Add songs into DataBase
     * Любой радиослушатель может предложить любое количество песен в программу концерта.
     * При этом он не обязан предложить все песни сразу,
     * а может добавлять их по одной или сразу несколько.
     * Для каждой песни он должен указать:
     * название песни
     * композитора
     * автора слов
     * исполнителя (фамилия или название группы)
     * продолжительность песни в секундах
     * Некоторые песни могут иметь более одного композитора или автора слов.
     * в этом случае слушатель вправе указать всех или только некоторых.
     * Исполнитель у песни всегда один.
     * Радиослушатель, предложивший песню в состав концерта, считается автором этого предложения.
     *
     * @param jsonRequest
     * @return
     */
    public String addSongs(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return songService.addSong(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * В любой момент любой радиослушатель может получить следующие списки:
     * Все заявленные в концерт песни.
     * Все заявленные в концерт песни указанного композитора или композиторов.
     * Все заявленные в концерт песни указанного автора слов или авторов слов.
     * Все заявленные в концерт песни указанного исполнителя.
     * <p>
     * В любой момент любой радиослушатель может получить текущую пробную программу концерта.
     * Пробная программа - это концерт из песен, набравших наибольшие суммы оценок
     * при условии, что суммарная продолжительность концерта не превышает 60 минут
     * с учетом того, что между каждыми двумя песнями делается пауза продолжительностью в 10 секунд.
     * В случае, если очередная песня из списка наиболее популярных не может быть добавлена в концерт,
     * потому что при этом будет превышено время концерта, эта песня пропускается,
     * и делается попытка добавить следующую по популярности песню и т.д.
     * В концерт должно включаться максимально возможное количество песен.
     * <p>
     * В пробную программу концерта для каждой песни включаются:
     * Название песни, композитор(ы), автор(ы) слов, исполнитель
     * Средняя оценка песни.
     * Все комментарии к этому предложению.
     *
     * @param jsonRequest
     * @return
     */
    public String getSongs(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return songService.getSongs(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }
    
    public String getAllSongs(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return songService.getAllSongs(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Delete Song with Song Name
     * Радиослушатели, сделавшие свое предложение, могут отменить его.
     * Если на момент отмены предложение не получило никаких оценок от других радиослушателей,
     * оно удаляется. Если же к этому моменту имеются другие оценки этого предложения,
     * то удаляется лишь оценка этого предложения, сделанная автором предложения
     * (то есть его оценка 5), а само предложение не удаляется, все остальные оценки сохраняются,
     * а автором предложения считается сообщество радиослушателей.
     *
     * @param jsonRequest
     * @return
     */
    public String deleteSong(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return songService.deleteSong(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Add Rating for the Song
     * Радиослушатели могут ставить свои оценки предлагаемым в программу песням по шкале 1..5
     *
     * @param jsonRequest
     * @return
     */
    public String addRating(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return ratingService.addRating(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Change Song Rating
     * Радиослушатели вправе изменить свою оценку
     *
     * @param jsonRequest
     * @return
     */
    public String changeRating(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return ratingService.changeRating(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Delete Rating for the Song
     * или вообще удалить ее в любое время
     *
     * @param jsonRequest
     * @return
     */
    public String deleteRating(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return ratingService.deleteRating(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Add Comment to the Song
     * Комментарий представляет собой одну текстовую строку.
     * Радиослушатель, сделавший комментарий, считается его автором. Радиослушатели
     * могут присоединяться к комментариям, сделанным ранее другими
     * радиослушателями.
     *
     * @param jsonRequest - with Song Name and Comment
     * @return
     */
    public String addComment(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return commentService.addComment(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Change Comment
     * Автор комментария вправе изменить его в любой момент. Если на
     * момент изменения к этому комментарию еще никто не присоединился, старый текст
     * комментария просто заменяется на новый. Если же к этому комментарию кто-то
     * успел к моменту его изменения автором комментария присоединиться, старый
     * вариант комментария остается без изменений, новый вариант добавляется к
     * списку комментариев для этой песни, а автором старого комментария считается
     * сообщество радиослушателей.
     *
     * @param jsonRequest
     * @return
     */
    public String changeComment(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return commentService.changeComment(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Радиослушатели, присоединившиеся к комментарию, вправе отказаться
     * от своего присоединения, но не могут изменять текст комментария.
     *
     * @param jsonRequest
     * @return
     */
    public String disclaimComment(String jsonRequest) {
        if (!isServerStarted()) {
            return jsonError(new ServerException(ServerErrorCode.SERVER_NOT_STARTED));
        }
        try {
            return commentService.disclaimComment(jsonRequest);
        } catch (ServerException e) {
            return jsonError(e);
        }
    }

    /**
     * Convert ServerException error to JSON string
     */
    private String jsonError(ServerException error) {
        return new Gson().toJson(new ErrorDtoResponse(error.getServerErrorText()));
    }

}
