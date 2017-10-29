package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Cache.Entry;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.cookie.DateUtils;

public class BasicNetwork implements Network {
    protected static final boolean DEBUG = VolleyLog.DEBUG;
    private static int DEFAULT_POOL_SIZE = 4096;
    private static int SLOW_REQUEST_THRESHOLD_MS = 3000;
    protected final HttpStack mHttpStack;
    protected final ByteArrayPool mPool;

    public BasicNetwork(HttpStack httpStack) {
        this(httpStack, new ByteArrayPool(DEFAULT_POOL_SIZE));
    }

    public BasicNetwork(HttpStack httpStack, ByteArrayPool pool) {
        this.mHttpStack = httpStack;
        this.mPool = pool;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.android.volley.NetworkResponse performRequest(com.android.volley.Request<?> r21) throws com.android.volley.VolleyError {
        /*
        r20 = this;
        r14 = android.os.SystemClock.elapsedRealtime();
    L_0x0004:
        r10 = 0;
        r7 = 0;
        r13 = new java.util.HashMap;
        r13.<init>();
        r9 = new java.util.HashMap;	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r9.<init>();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r3 = r21.getCacheEntry();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r0 = r20;
        r0.addCacheHeaders(r9, r3);	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r0 = r20;
        r3 = r0.mHttpStack;	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r0 = r21;
        r10 = r3.performRequest(r0, r9);	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r8 = r10.getStatusLine();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r16 = r8.getStatusCode();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r3 = r10.getAllHeaders();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r13 = convertHeaders(r3);	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r3 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        r0 = r16;
        if (r0 != r3) goto L_0x0055;
    L_0x0039:
        r3 = new com.android.volley.NetworkResponse;	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r17 = 304; // 0x130 float:4.26E-43 double:1.5E-321;
        r6 = r21.getCacheEntry();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        if (r6 != 0) goto L_0x004e;
    L_0x0043:
        r6 = 0;
    L_0x0044:
        r18 = 1;
        r0 = r17;
        r1 = r18;
        r3.<init>(r0, r6, r13, r1);	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
    L_0x004d:
        return r3;
    L_0x004e:
        r6 = r21.getCacheEntry();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r6 = r6.data;	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        goto L_0x0044;
    L_0x0055:
        r3 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
        r0 = r16;
        if (r0 == r3) goto L_0x0061;
    L_0x005b:
        r3 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
        r0 = r16;
        if (r0 != r3) goto L_0x006e;
    L_0x0061:
        r3 = "Location";
        r12 = r13.get(r3);	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r12 = (java.lang.String) r12;	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r0 = r21;
        r0.setRedirectUrl(r12);	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
    L_0x006e:
        r3 = r10.getEntity();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        if (r3 == 0) goto L_0x00ac;
    L_0x0074:
        r3 = r10.getEntity();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r0 = r20;
        r7 = r0.entityToBytes(r3);	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
    L_0x007e:
        r18 = android.os.SystemClock.elapsedRealtime();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r4 = r18 - r14;
        r3 = r20;
        r6 = r21;
        r3.logSlowRequests(r4, r6, r7, r8);	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r3 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = r16;
        if (r0 < r3) goto L_0x0097;
    L_0x0091:
        r3 = 299; // 0x12b float:4.19E-43 double:1.477E-321;
        r0 = r16;
        if (r0 <= r3) goto L_0x00b0;
    L_0x0097:
        r3 = new java.io.IOException;	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r3.<init>();	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        throw r3;	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
    L_0x009d:
        r2 = move-exception;
        r3 = "socket";
        r6 = new com.android.volley.TimeoutError;
        r6.<init>();
        r0 = r21;
        attemptRetryOnException(r3, r0, r6);
        goto L_0x0004;
    L_0x00ac:
        r3 = 0;
        r7 = new byte[r3];	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        goto L_0x007e;
    L_0x00b0:
        r3 = new com.android.volley.NetworkResponse;	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        r6 = 0;
        r0 = r16;
        r3.<init>(r0, r7, r13, r6);	 Catch:{ SocketTimeoutException -> 0x009d, ConnectTimeoutException -> 0x00b9, MalformedURLException -> 0x00c8, IOException -> 0x00ea }
        goto L_0x004d;
    L_0x00b9:
        r2 = move-exception;
        r3 = "connection";
        r6 = new com.android.volley.TimeoutError;
        r6.<init>();
        r0 = r21;
        attemptRetryOnException(r3, r0, r6);
        goto L_0x0004;
    L_0x00c8:
        r2 = move-exception;
        r3 = new java.lang.RuntimeException;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r17 = "Bad URL ";
        r0 = r17;
        r6 = r6.append(r0);
        r17 = r21.getUrl();
        r0 = r17;
        r6 = r6.append(r0);
        r6 = r6.toString();
        r3.<init>(r6, r2);
        throw r3;
    L_0x00ea:
        r2 = move-exception;
        r16 = 0;
        r11 = 0;
        if (r10 == 0) goto L_0x0140;
    L_0x00f0:
        r3 = r10.getStatusLine();
        r16 = r3.getStatusCode();
        r3 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
        r0 = r16;
        if (r0 == r3) goto L_0x0104;
    L_0x00fe:
        r3 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
        r0 = r16;
        if (r0 != r3) goto L_0x0146;
    L_0x0104:
        r3 = "Request at %s has been redirected to %s";
        r6 = 2;
        r6 = new java.lang.Object[r6];
        r17 = 0;
        r18 = r21.getOriginUrl();
        r6[r17] = r18;
        r17 = 1;
        r18 = r21.getUrl();
        r6[r17] = r18;
        com.android.volley.VolleyLog.m16e(r3, r6);
    L_0x011c:
        if (r7 == 0) goto L_0x017f;
    L_0x011e:
        r11 = new com.android.volley.NetworkResponse;
        r3 = 0;
        r0 = r16;
        r11.<init>(r0, r7, r13, r3);
        r3 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        r0 = r16;
        if (r0 == r3) goto L_0x0132;
    L_0x012c:
        r3 = 403; // 0x193 float:5.65E-43 double:1.99E-321;
        r0 = r16;
        if (r0 != r3) goto L_0x015f;
    L_0x0132:
        r3 = "auth";
        r6 = new com.android.volley.AuthFailureError;
        r6.<init>(r11);
        r0 = r21;
        attemptRetryOnException(r3, r0, r6);
        goto L_0x0004;
    L_0x0140:
        r3 = new com.android.volley.NoConnectionError;
        r3.<init>(r2);
        throw r3;
    L_0x0146:
        r3 = "Unexpected response code %d for %s";
        r6 = 2;
        r6 = new java.lang.Object[r6];
        r17 = 0;
        r18 = java.lang.Integer.valueOf(r16);
        r6[r17] = r18;
        r17 = 1;
        r18 = r21.getUrl();
        r6[r17] = r18;
        com.android.volley.VolleyLog.m16e(r3, r6);
        goto L_0x011c;
    L_0x015f:
        r3 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
        r0 = r16;
        if (r0 == r3) goto L_0x016b;
    L_0x0165:
        r3 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
        r0 = r16;
        if (r0 != r3) goto L_0x0179;
    L_0x016b:
        r3 = "redirect";
        r6 = new com.android.volley.AuthFailureError;
        r6.<init>(r11);
        r0 = r21;
        attemptRetryOnException(r3, r0, r6);
        goto L_0x0004;
    L_0x0179:
        r3 = new com.android.volley.ServerError;
        r3.<init>(r11);
        throw r3;
    L_0x017f:
        r3 = new com.android.volley.NetworkError;
        r3.<init>(r11);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.BasicNetwork.performRequest(com.android.volley.Request):com.android.volley.NetworkResponse");
    }

    private void logSlowRequests(long requestLifetime, Request<?> request, byte[] responseContents, StatusLine statusLine) {
        if (DEBUG || requestLifetime > ((long) SLOW_REQUEST_THRESHOLD_MS)) {
            String str = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]";
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(requestLifetime);
            objArr[2] = responseContents != null ? Integer.valueOf(responseContents.length) : "null";
            objArr[3] = Integer.valueOf(statusLine.getStatusCode());
            objArr[4] = Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount());
            VolleyLog.m15d(str, objArr);
        }
    }

    private static void attemptRetryOnException(String logPrefix, Request<?> request, VolleyError exception) throws VolleyError {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int oldTimeout = request.getTimeoutMs();
        try {
            retryPolicy.retry(exception);
            request.addMarker(String.format("%s-retry [timeout=%s]", new Object[]{logPrefix, Integer.valueOf(oldTimeout)}));
        } catch (VolleyError e) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{logPrefix, Integer.valueOf(oldTimeout)}));
            throw e;
        }
    }

    private void addCacheHeaders(Map<String, String> headers, Entry entry) {
        if (entry != null) {
            if (entry.etag != null) {
                headers.put("If-None-Match", entry.etag);
            }
            if (entry.serverDate > 0) {
                headers.put("If-Modified-Since", DateUtils.formatDate(new Date(entry.serverDate)));
            }
        }
    }

    protected void logError(String what, String url, long start) {
        long now = SystemClock.elapsedRealtime();
        VolleyLog.m18v("HTTP ERROR(%s) %d ms to fetch %s", what, Long.valueOf(now - start), url);
    }

    private byte[] entityToBytes(HttpEntity entity) throws IOException, ServerError {
        PoolingByteArrayOutputStream bytes = new PoolingByteArrayOutputStream(this.mPool, (int) entity.getContentLength());
        byte[] buffer = null;
        try {
            InputStream in = entity.getContent();
            if (in == null) {
                throw new ServerError();
            }
            buffer = this.mPool.getBuf(1024);
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    break;
                }
                bytes.write(buffer, 0, count);
            }
            byte[] toByteArray = bytes.toByteArray();
            return toByteArray;
        } finally {
            try {
                entity.consumeContent();
            } catch (IOException e) {
                VolleyLog.m18v("Error occured when calling consumingContent", new Object[0]);
            }
            this.mPool.returnBuf(buffer);
            bytes.close();
        }
    }

    private static Map<String, String> convertHeaders(Header[] headers) {
        Map<String, String> result = new HashMap();
        for (int i = 0; i < headers.length; i++) {
            result.put(headers[i].getName(), headers[i].getValue());
        }
        return result;
    }
}
