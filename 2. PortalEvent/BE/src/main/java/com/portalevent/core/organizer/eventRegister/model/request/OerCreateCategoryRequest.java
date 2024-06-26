package com.portalevent.core.organizer.eventRegister.model.request;

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
public class OerCreateCategoryRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = 100)
    private String name;

}
