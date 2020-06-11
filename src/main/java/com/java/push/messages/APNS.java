package com.java.push.messages;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@ToString
public class APNS {
    

    public String p8file;
    public String teamId;
    public String fileKey;
    public String bundleId;
    public String token;
    public String title;
    public String message;

    public APNS(String p8file, String teamId, String fileKey, String bundleId, String token,String title, String message) {
        this.p8file = p8file;
        this.teamId = teamId;
        this.fileKey = fileKey;
        this.bundleId = bundleId;
        this.token = token;
        this.title = title;
        this.message = message;
    }
    public APNS(){}
}