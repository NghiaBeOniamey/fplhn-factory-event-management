package fplhn.udpm.identity.infrastructure.excel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImportRoleExcel {

    private String staffInfo;

    private List<String> roleCodeCheck;

}
