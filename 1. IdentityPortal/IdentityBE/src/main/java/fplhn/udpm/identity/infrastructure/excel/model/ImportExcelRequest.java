package fplhn.udpm.identity.infrastructure.excel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImportExcelRequest {

    private String maGiangVien;

    private String hoTen;

    private String emailFpt;

    private String emailFe;

    private String tenBoMonTheoCoSo;

}
