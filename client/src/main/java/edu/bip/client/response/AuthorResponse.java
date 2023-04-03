package edu.bip.client.response;

import edu.bip.client.entity.AuthorEntity;
import lombok.Data;

@Data
public class AuthorResponse extends BaseResponse {
    public AuthorResponse(boolean success, String message, AuthorEntity author){
        super(success,message);
        this.author=author;
    }
    public AuthorResponse(AuthorEntity author) {
        super(true,"Author Data");
    }
    private AuthorEntity author;
}
