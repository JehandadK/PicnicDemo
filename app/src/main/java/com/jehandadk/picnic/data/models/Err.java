package com.jehandadk.picnic.data.models;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class Err {
    private String Message;
    private String RequestId;
    private String HostId;
    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    public String getHostId() {
        return HostId;
    }

    public void setHostId(String HostId) {
        this.HostId = HostId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    @Override
    public String toString() {
        return "ClassPojo [Message = " + Message + ", RequestId = " + RequestId + ", HostId = " + HostId + ", Code = " + Code + "]";
    }
}
