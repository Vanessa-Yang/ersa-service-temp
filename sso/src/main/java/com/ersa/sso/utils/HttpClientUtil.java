package com.ersa.sso.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @Description 依赖的jar包有：commons-lang-2.6.jar、httpclient-4.3.2.jar、httpcore-4.3.1.jar、commons-io-2.4.jar
 * @Author Vanessa Yang
 * @Data 2021/2/5 0005 10:45
 */

public class HttpClientUtil {

    public static final int connTimeout = 10000;
    public static final int readTimeout = 10000;
    public static final String charset = "UTF-8";
    private static HttpClient client = null;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
        client = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String get(String url) throws Exception {
        return get(url, charset, null, null);
    }

    /**
     * 发送一个GET请求
     *
     * @param url
     * @param charset
     * @param connTimeout 建立连接超时时间，单位：毫秒
     * @param readTimeout 响应超时时间，单位：毫秒
     * @return
     */
    private static String get(String url, String charset, Integer connTimeout, Integer readTimeout) throws IOException {

        HttpClient client = null;
        HttpGet get = new HttpGet(url);
        String result = "";
        try {
            //设置参数
            RequestConfig.Builder custom = RequestConfig.custom();
            if (connTimeout != null) {
                custom.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                custom.setSocketTimeout(readTimeout);
            }
            get.setConfig(custom.build());

            HttpResponse res = null;

            if (url.startsWith("https")) {
                //执行 Https 请求
                client = createSSLInsecureClient();
                res = client.execute(get);
            } else {
                //执行 Http 请求
                client = HttpClientUtil.client;
                res = client.execute(get);
            }

            result = IOUtils.toString(res.getEntity().getContent(), charset);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient)client).close();
            }
        }
        return result;
    }

    /**
     * 创建 SSL 连接
     *
     * @return
     */
    private static CloseableHttpClient createSSLInsecureClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
                @Override
                public void verify(String s, SSLSocket sslSocket) throws IOException {

                }

                @Override
                public void verify(String s, X509Certificate x509Certificate) throws SSLException {

                }

                @Override
                public void verify(String s, String[] strings, String[] strings1) throws SSLException {

                }

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });

            return  HttpClients.custom().setSSLSocketFactory(socketFactory).build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }


}
