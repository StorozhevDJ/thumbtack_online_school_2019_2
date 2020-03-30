package com.lineate.spring.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

public class RecordingDto {
    @NotNull
    @NotBlank
    private String artist;
    @NotNull
    @NotBlank
    private String name;
    private String albumName;
    private int year;

    private URL urlCover;
    private String genre;
    private int len;
    private URL urlAudio;
    private URL urlVideo;

    public RecordingDto(String artist, String name, String albumName, int year, URL urlCover, String genre, int len, URL urlAudio, URL urlVideo) {
        this.artist = artist;
        this.name = name;
        this.albumName = albumName;
        this.year = year;
        this.urlCover = urlCover;
        this.genre = genre;
        this.len = len;
        this.urlAudio = urlAudio;
        this.urlVideo = urlVideo;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public URL getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(URL urlCover) {
        this.urlCover = urlCover;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public URL getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(URL urlAudio) {
        this.urlAudio = urlAudio;
    }

    public URL getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(URL urlVideo) {
        this.urlVideo = urlVideo;
    }
}
