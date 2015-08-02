/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.portletbridge.portlet;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.portletbridge.ResourceException;

/**
 * Default implementation of the httpclient template. Makes a call
 * to the url specifies and then passes the result to the callback.
 * 
 * @author JMcCrindle
 */
public class DefaultHttpClientTemplate implements HttpClientTemplate {
    
    private HttpClient httpClient = null;

    private final int connectionTimeout;
    private final int readTimeoutMillis;
    private final int retryCount;

    public static final DefaultHttpClientTemplate INSTANCE =
            new DefaultHttpClientTemplate.Builder().connectionTimeout(5000).build();

    public static class Builder {

        private static final int DEFAULT_CONNECTION_TIMEOUT_MILLIS = 5000;
        private static final int DEFAULT_READ_TIMEOUT_MILLIS = 5000;
        private static final int DEFAULT_RETRY_COUNT = 3;

        private int connectionTimeout;
        private int readTimeoutMillis;
        private int retryCount;

        public Builder() {
            connectionTimeout = DEFAULT_CONNECTION_TIMEOUT_MILLIS;
            readTimeoutMillis = DEFAULT_READ_TIMEOUT_MILLIS;
            retryCount = DEFAULT_RETRY_COUNT;
        }



        public Builder connectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }


        public Builder readTimeoutMillis(int readTimeoutMillis) {
            this.readTimeoutMillis = readTimeoutMillis;
            return this;
        }


        public Builder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }


        public DefaultHttpClientTemplate build() {
            return new DefaultHttpClientTemplate(this);
        }
        
    };


    public DefaultHttpClientTemplate() {
        this(new Builder());
    }


    /**
     * 
     */
    private DefaultHttpClientTemplate(Builder b) {
        retryCount = b.retryCount;
        connectionTimeout = b.connectionTimeout;
        readTimeoutMillis = b.readTimeoutMillis;

        httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        httpClient.getHttpConnectionManager()
                .getParams()
                .setConnectionTimeout(connectionTimeout);
        httpClient.getHttpConnectionManager()
                .getParams()
                .setSoTimeout(readTimeoutMillis);
        HttpMethodRetryHandler retryhandler = new HttpMethodRetryHandler() {
            public boolean retryMethod(
                final HttpMethod method,
                final IOException exception,
                int executionCount) {
                if (executionCount >= retryCount) {
                    // Do not retry if over max retry count
                    return false;
    }
                if (exception instanceof NoHttpResponseException) {
                    // Retry if the server dropped connection on us
                    return true;
                }
                if (exception instanceof SocketException) {
                    // Retry if the server reset connection on us
                    return true;
                }
                if (exception instanceof SocketTimeoutException) {
                    // Retry if the read timed out
                    return true;
                }
                if (!method.isRequestSent()) {
                    // Retry if the request has not been sent fully or
                    // if it's OK to retry methods that have been sent
                    return true;
                }
                // otherwise do not retry
                return false;
            }
        };
    
        httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                retryhandler);
    }
    
    public Object service(HttpMethodBase method, HttpClientState state, HttpClientCallback callback) throws ResourceException {
        try {
            HostConfiguration hostConfiguration = new HostConfiguration();
            
            if(state.getProxyHost() != null && state.getProxyHost().trim().length() > 0) {
                hostConfiguration.setProxy(state.getProxyHost(), state.getProxyPort());
            }
            hostConfiguration.setHost(method.getURI());
            int statusCode = httpClient.executeMethod(hostConfiguration, method, state.getHttpState());
            return callback.doInHttpClient(statusCode, method);
        } catch (ResourceException e) {
            throw e;
        } catch (Throwable e) {
            throw new ResourceException("error.httpclient", e.getMessage(), e);
        } finally {
            method.releaseConnection();
        }
    }

}
