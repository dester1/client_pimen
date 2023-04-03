package edu.bip.client.response;

import edu.bip.client.entity.AuthorEntity;
import lombok.Data;

import java.util.List;

@Data
public class AuthorListResponse extends BaseResponse {
    public AuthorListResponse(List<AuthorEntity> data){
        super(true,"Authors");
        this.data=data;
    }
    private List<AuthorEntity> data;
}
