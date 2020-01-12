package net.thumbtack.school.concert.mappers;

import net.thumbtack.school.concert.model.Rating;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface RatingMapper {

    void add(Rating rating);

    void add(List<Rating> ratings);

    void update(Rating rating);

    List<Rating> getRatingList(String songId);

    List<Rating> getRatingList(String songId, String user);

    List<Rating> getRatingList(List<String> songId);

    List<Rating> get(List<String> songsIdList);

    void delete(String songId, String user);

    @Delete("DELETE FROM ratings")
    void deleteAll();
}
