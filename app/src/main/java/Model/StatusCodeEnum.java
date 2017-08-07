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
         Num=i;
    }
    public String tostring()
    {
        return  super.toString()+Num;
    }
    private int Num;
}

