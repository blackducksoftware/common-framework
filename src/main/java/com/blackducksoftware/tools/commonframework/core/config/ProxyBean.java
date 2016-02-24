/**
 * 
 */
package com.blackducksoftware.tools.commonframework.core.config;

/**
 * Simple bean holding Proxy details
 * 
 * @author akamen
 * 
 */
public class ProxyBean {

    /** The proxy server. */
    private String proxyServer;

    public String getProxyServer() {
        return proxyServer;
    }

    public void setProxyServer(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyServerHttps() {
        return proxyServerHttps;
    }

    public void setProxyServerHttps(String proxyServerHttps) {
        this.proxyServerHttps = proxyServerHttps;
    }

    public String getProxyPortHttps() {
        return proxyPortHttps;
    }

    public void setProxyPortHttps(String proxyPortHttps) {
        this.proxyPortHttps = proxyPortHttps;
    }

    /** The proxy port. */
    private String proxyPort;

    /** The proxy server https. */
    protected String proxyServerHttps;

    /** The proxy port https. */
    protected String proxyPortHttps;

}
