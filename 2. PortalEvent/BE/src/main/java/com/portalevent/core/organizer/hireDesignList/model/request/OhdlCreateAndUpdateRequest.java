package com.portalevent.core.organizer.hireDesignList.model.request;


import com.portalevent.infrastructure.constant.Formality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Hứng data từ FE để add hoặc update địa điểm
 */

@Getter
@Setter
@ToString
public class OhdlCreateAndUpdateRequest {

    @NotBlank
    private String idEvent;

    @NotNull
    private Formality formality;

    @NotBlank
    private String name;

    @NotBlank
    private String path;

}
