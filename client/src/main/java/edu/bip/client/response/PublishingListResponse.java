package edu.bip.client.response;

import edu.bip.client.entity.PublishingEntity;
import lombok.Data;

import java.util.List;

@Data
public class PublishingListResponse extends BaseResponse {
    public PublishingListResponse(List<PublishingEntity> data){
        super(true,"Publishers");
        this.data=data;
    }

    private List<PublishingEntity> data;
}
