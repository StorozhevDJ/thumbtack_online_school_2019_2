package net.thumbtack.school.concert.dto.response;

public class ErrorDtoResponse {
    private String error;

    public ErrorDtoResponse() {
    }

    public ErrorDtoResponse(String error) {
        setError(error);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
