package com.app.taima.utils;


import com.app.taima.enums.ResponseType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    private String type;
    private String code;
    private String description;
    private T data;

    public GenericResponse(ResponseType type, T data) {
        this.type = type.toString();
        this.data = data;
    }

    public GenericResponse(ResponseType type, String description) {
        this.type = type.toString();
        this.description = description;
    }

    public GenericResponse(ResponseType type) {
        this.type = type.toString();
    }


    public static <T> GenericResponse<T> success(T data) {
        return new GenericResponse<>(ResponseType.SUCCESS, data);
    }

    public static <T> GenericResponse<T> success() {
        return new GenericResponse<>(ResponseType.SUCCESS);
    }

    public static <T> GenericResponse<T> error(String description) {
        return new GenericResponse<>(ResponseType.ERROR, description);
    }

    public static <T> GenericResponse<T> error() {
        return new GenericResponse<>(ResponseType.ERROR);
    }

    public static <T> GenericResponse<T> dbError(String description) {
        return new GenericResponse<>(ResponseType.DB_ERROR, description);
    }

    public GenericResponse<T> description(String description) {
        this.description = description;
        return this;
    }

    public GenericResponse<T> code(String code) {
        this.code = code;
        return this;
    }
}

