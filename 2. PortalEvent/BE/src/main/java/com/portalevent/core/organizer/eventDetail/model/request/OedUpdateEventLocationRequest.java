package com.portalevent.core.organizer.eventDetail.model.request;

import com.portalevent.entity.base.IsIdentified;
import com.portalevent.infrastructure.constant.Formality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author HoangDV
 */
@Getter
@Setter
@ToString
public class OedUpdateEventLocationRequest {

    @NotBlank
    private String id;

    @NotNull
    private Formality formality;

    @NotBlank
    private String name;

    @NotBlank
    private String path;

}
