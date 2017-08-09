package Model;

/**
 * Created by TOSHIBA on 2017/8/7.
 */

public class Token {

    /**
     * StatusCode : 200
     * Info :
     * Data : {"StaffId":10000,"SignToken":"2801c287-c4c8-449b-a0f5-8dcc05edfef3","ExpireTime":"2017-08-10T19:38:35.0956465+08:00"}
     */

    private int StatusCode;
    private String Info;
    private DataBean Data;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * StaffId : 10000
         * SignToken : 2801c287-c4c8-449b-a0f5-8dcc05edfef3
         * ExpireTime : 2017-08-10T19:38:35.0956465+08:00
         */

        private int StaffId;
        private String SignToken;
        private String ExpireTime;

        public int getStaffId() {
            return StaffId;
        }

        public void setStaffId(int StaffId) {
            this.StaffId = StaffId;
        }

        public String getSignToken() {
            return SignToken;
        }

        public void setSignToken(String SignToken) {
            this.SignToken = SignToken;
        }

        public String getExpireTime() {
            return ExpireTime;
        }

        public void setExpireTime(String ExpireTime) {
            this.ExpireTime = ExpireTime;
        }
    }
}
