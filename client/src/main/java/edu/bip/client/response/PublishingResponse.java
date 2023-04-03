package edu.bip.client.response;

import edu.bip.client.entity.PublishingEntity;
import lombok.Data;

import java.util.Optional;

@Data
public class PublishingResponse extends BaseResponse {
    public PublishingResponse(boolean success, String message, PublishingEntity publishing){
        super(success,message);
        this.publishing=publishing;
    }
    public PublishingResponse(PublishingEntity publishing){
        super(true,"Publishing Data");
    }

    public PublishingResponse(Optional<PublishingEntity> publishing1){
        super(true,"Publishing Data");
    }
    private PublishingEntity publishing;
    private Optional<PublishingEntity> publishing1;
}
