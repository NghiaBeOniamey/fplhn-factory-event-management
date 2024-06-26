package fplhn.udpm.identity.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class URLBuilder {

    private final StringBuilder folders;

    private final StringBuilder params;

    private String connType, host;

    public void setConnectionType(String conn) {
        this.connType = conn;
    }

    public URLBuilder() {
        folders = new StringBuilder();
        params = new StringBuilder();
    }

    public URLBuilder(String host) {
        this();
        this.host = host;
    }

    public void addSubfolder(String folder) {
        folders.append("/");
        folders.append(folder);
    }

    public void addParameter(String parameter, String value) {
        if (!params.toString().isEmpty()) {
            params.append("&");
        }
        params.append(parameter);
        params.append("=");
        params.append(value);
    }

    public String getURL() throws URISyntaxException, MalformedURLException {
        URI uri = new URI(connType, host, folders.toString(),
                params.toString(), null);
        return uri.toURL().toString();
    }

    public String getRelativeURL() throws URISyntaxException, MalformedURLException {
        URI uri = new URI(null, null, folders.toString(), params.toString(), null);
        return uri.toString();
    }

}
