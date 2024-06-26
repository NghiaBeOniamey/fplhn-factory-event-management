package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class OedCreateCategoryRequest {

    @NotBlank
    @Length(max = 10)
    private String code;

    @NotBlank
    @Length(max = 100)
    private String name;
}
