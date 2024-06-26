package com.portalevent.core.organizer.eventRegister.model.request;

import com.portalevent.infrastructure.constant.EntityProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author SonPT
 */
@Getter
@Setter
public class OerCreateEventLocationRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = EntityProperties.LENGTH_ID)
    private String idEvent;

    @NotNull
    private Integer formality;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = EntityProperties.LENGTH_NAME)
    private String name;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = EntityProperties.LENGTH_PATH)
    private String path;

}
