package top.meethigher.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Resp<T> {

    private int code;
    private String desc;
    private T data;


    public static <T> Resp<T> getSuccessResp(T data) {
        return new Resp<>(0,
                "成功",
                data);
    }

    public static Resp<Void> getFailureResp(String desc) {
        return new Resp<>(1,
                "失败",
                null);
    }


    public static Resp<Void> getErrorResp() {
        return new Resp<>(500,
                "错误",
                null);
    }
}
