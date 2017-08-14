package Model;

/**
 * Created by TOSHIBA on 2017/8/7.
 */

public enum  StatusCodeEnum {

    Success(200),
    Error(500),
    Unauthorized(401),
    ParameterError(400),
    TokenInvalid(403),
    HttpMehtodError(405),
    HttpRequestError(406),
    URLExpireError(407);
    private  StatusCodeEnum(int i)
    {
       this.Num=i;
    }
    public int tostring()
    {
        return  Num;
    }

    private int Num;
}

