package fplhn.udpm.identity.infrastructure.constant;

import lombok.Getter;

import java.util.List;

public class RoleConstants {

    public static final String ACTOR_ADMIN = "ADMIN";

    public static final String ACTOR_CNBM = "CHU_NHIEM_BO_MON";

    public static final String ACTOR_TM = "TRUONG_MON_CHUYEN_NGANH";

    public static final String ACTOR_GV = "GIAO_VIEN";

    public static final String ACTOR_SV = "SINH_VIEN";

    public static final String ACTOR_TBDT_CS = "TRUONG_BAN_DAO_TAO_CO_SO";

    public static final String ACTOR_BDT_HO = "BAN_DAO_TAO_HO";

    public static final String ACTOR_QLDT = "QUAN_LY_DAO_TAO";

    @Getter
    public static List<String> defaultRoles = List.of(
            ACTOR_ADMIN,
            ACTOR_CNBM,
            ACTOR_TM,
            ACTOR_GV,
            ACTOR_SV,
            ACTOR_TBDT_CS,
            ACTOR_BDT_HO,
            ACTOR_QLDT
    );

    @Getter
    public static List<String> defaultRolesForStaff = List.of(
            ACTOR_ADMIN,
            ACTOR_CNBM,
            ACTOR_TM,
            ACTOR_GV,
            ACTOR_TBDT_CS,
            ACTOR_BDT_HO,
            ACTOR_QLDT
    );

}
