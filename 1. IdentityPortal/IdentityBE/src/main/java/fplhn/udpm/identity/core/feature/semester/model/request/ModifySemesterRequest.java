package fplhn.udpm.identity.core.feature.semester.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifySemesterRequest {

    @NotNull(message = "Semester name is required")
    @NotBlank(message = "Semester name is required")
    private String name;

    @NotNull(message = "Start time is required")
    private Long startTime;

    @NotNull(message = "End time is required")
    private Long endTime;

    @NotNull(message = "Start time first block is required")
    private Long startTimeFirstBlock;

    @NotNull(message = "End time first block is required")
    private Long endTimeFirstBlock;

    @NotNull(message = "Start time second block is required")
    private Long startTimeSecondBlock;

    @NotNull(message = "End time second block is required")
    private Long endTimeSecondBlock;

}
