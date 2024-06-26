package fplhn.udpm.identity.core.feature.module.model.response;

import fplhn.udpm.identity.core.common.IsIdentify;

public interface ClientResponse extends IsIdentify {

    String getClientId();

    String getClientSecret();

}
