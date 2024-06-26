package fplhn.udpm.identity.core.viewer.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {

    @NotNull
    private String subject;

    @NotNull
    private String body;

    @NotNull
    private String moduleAddress;

    private MultipartFile file;

}
