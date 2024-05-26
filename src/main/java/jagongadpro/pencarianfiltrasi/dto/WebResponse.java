package jagongadpro.pencarianfiltrasi.dto;

import lombok.Data;

@Data
public class WebResponse<T> {
    private T data;
    private Object errors;
}