package cn.whaley.member.base.util;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by leiwenbin on 15/9/24.
 * Http Client
 */


public final class HttpHelper {

    private String charset = "utf-8";
    private Integer connectTimeout = null;
    private Integer readTimeout = null;
    private String proxyHost = null;
    private Integer proxyPort = null;

    /**
     * Get Request
     *
     * @return HttpResponse
     * @throws Exception
     */
    public String doGet(String url) throws Exception {

        URL localURL = new URL(url);

        if ("https".equalsIgnoreCase(localURL.getProtocol())) {
            SslHelper.ignoreSsl();
        }

        URLConnection connection = openConnection(localURL);
        renderRequest(connection);
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setRequestProperty("Accept-Charset", charset);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine;

        if (httpURLConnection.getResponseCode() >= 400)
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());

        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null)
                resultBuffer.append(tempLine);

        } finally {
            if (reader != null)
                reader.close();

            if (inputStreamReader != null)
                inputStreamReader.close();

            if (inputStream != null)
                inputStream.close();

        }

        return resultBuffer.toString();
    }

    /**
     * Do POST request
     *
     * @param url
     * @param parameterMap
     * @return
     * @throws Exception
     */
    public String doPost(String url, Map parameterMap) throws Exception {

        /* Translate parameter map to parameter date string */
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key;
            String value;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }

                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }

        LogHelper.getLogger().info("POST parameter:" + parameterBuffer.toString());

        URL localURL = new URL(url);

        if ("https".equalsIgnoreCase(localURL.getProtocol())) {
            SslHelper.ignoreSsl();
        }

        URLConnection connection = openConnection(localURL);
        renderRequest(connection);
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", charset);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine;

        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(parameterBuffer.toString());
            outputStreamWriter.flush();

            if (httpURLConnection.getResponseCode() >= 400)
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());

            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null)
                resultBuffer.append(tempLine);

        } finally {
            if (outputStreamWriter != null)
                outputStreamWriter.close();

            if (outputStream != null)
                outputStream.close();

            if (reader != null)
                reader.close();

            if (inputStreamReader != null)
                inputStreamReader.close();

            if (inputStream != null)
                inputStream.close();

        }

        return resultBuffer.toString();
    }

    private URLConnection openConnection(URL localURL) throws IOException {
        URLConnection connection;
        if (proxyHost != null && proxyPort != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            connection = localURL.openConnection(proxy);
        } else {
            connection = localURL.openConnection();
        }
        return connection;
    }

    /**
     * Render request according setting
     *
     * @param connection
     */
    private void renderRequest(URLConnection connection) {
        if (connectTimeout != null)
            connection.setConnectTimeout(connectTimeout);
        if (readTimeout != null)
            connection.setReadTimeout(readTimeout);
    }

    /*
     * Getter & Setter
     */
    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

}
