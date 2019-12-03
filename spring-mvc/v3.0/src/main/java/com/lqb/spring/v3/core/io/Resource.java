package com.lqb.spring.v3.core.io;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class Resource {

    private URI uri;

    private URL url;

    private File file;

    public Resource(URI uri) throws MalformedURLException {
        this.uri = uri;
        this.url = uri.toURL();
    }

    public Resource(URL url) {
        this.url = url;
        this.uri = null;
    }

    public Resource(String path) throws MalformedURLException {
        this.uri = null;
        this.url = new URL(path);
    }

    public Resource(String protocol, String location) throws MalformedURLException {
        this(protocol, location, null);
    }

    public Resource(String protocol, String location, @Nullable String fragment) throws MalformedURLException  {
        try {
            this.uri = new URI(protocol, location, fragment);
            this.url = this.uri.toURL();
        }
        catch (URISyntaxException ex) {
            MalformedURLException exToThrow = new MalformedURLException(ex.getMessage());
            exToThrow.initCause(ex);
            throw exToThrow;
        }
    }

    public Resource(File file) {
        this.file = file;
    }

    public InputStream getInputStream() throws IOException {
        URLConnection con = this.url.openConnection();
        con.setUseCaches(con.getClass().getSimpleName().startsWith("JNLP"));
        try {
            return con.getInputStream();
        }
        catch (IOException ex) {
            // Close the HTTP connection (if applicable).
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
            throw ex;
        }
    }

    public URI getURI() {
        return uri;
    }

    public URL getURL() {
        return url;
    }

    public File getFile() {
        return new File(this.uri.getSchemeSpecificPart());
    }

}
