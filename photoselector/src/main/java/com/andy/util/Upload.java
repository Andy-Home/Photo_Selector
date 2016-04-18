package com.andy.util;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class Upload {
    private static final String BOUNDARY = java.util.UUID.randomUUID()
            .toString();
    private static final String PREFIX = "--", LINEND = "\r\n";
    private static final String MULTIPART_FROM_DATA = "multipart/form-data";
    private static final String CHARSET = "UTF-8";

    /**
     * @param spec 链接地址
     * @param map  文本参数
     * @param list 图片路径
     * @return
     * @throws IOException
     */
    public static String uploadPicAndTxt(String spec, Map<String, String> map,
                                         List<String> list) throws IOException {
        HttpURLConnection conn = null;
        DataOutputStream out = null;
        try {
            URL url = new URL(spec);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(500 * 1000);
            conn.setReadTimeout(5 * 1000);
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false);
            conn.setRequestMethod("POST"); // Post方式
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charsert", CHARSET);
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                    + ";boundary=" + BOUNDARY);

            // 设置文本类型的参数
            StringBuilder builder = new StringBuilder();
            for (String key : map.keySet()) {
                builder.append(PREFIX)
                        .append(BOUNDARY)
                        .append(LINEND)
                        .append("Content-Disposition: form-data; name=\"" + key
                                + "\"" + LINEND)
                        .append("Content-Type: text/plain; charset=" + CHARSET
                                + LINEND)
                        .append("Content-Transfer-Encoding: 8bit" + LINEND)
                        .append(LINEND).append(map.get(key)).append(LINEND);
            }
            // 发送文本类型的数据
            out = new DataOutputStream(conn.getOutputStream());
            //L.e(builder.toString());
            out.write(builder.toString().getBytes());
            builder.delete(0, builder.length());

            // 发送文件数据
            if (list != null && !list.isEmpty()) {
                int j = 0;
                for (String path : list) {
                    ++j;
                    if (path.equals("")) {
                        continue;
                    }
                    File file = new File(path);
                    if (file != null && file.exists()) {
                        //L.e("file exists");
                    }
                    builder.append(PREFIX)
                            .append(BOUNDARY)
                            .append(LINEND)
                            .append("Content-Disposition: form-data; name=\"file"
                                    + "\"; filename=\""
                                    + file.getName()
                                    + "\"" + LINEND)
                            .append("Content-Type: multipart/form-data; charset="
                                    + CHARSET + LINEND).append(LINEND);
                    out.write(builder.toString().getBytes());
                    //L.e(builder.toString());
                    InputStream in = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int i;
                    while ((i = in.read(buffer)) != -1) {
                        out.write(buffer, 0, i);
                    }
                    in.close();
                    out.write(LINEND.getBytes());
                    builder.delete(0, builder.length());
                }
            }
            // 请求结束标志
            builder.append(PREFIX).append(BOUNDARY).append(PREFIX)
                    .append(LINEND);
            out.write(builder.toString().getBytes());
            //L.e(builder.toString());
            out.flush();
            builder.delete(0, builder.length());
            // 读取服务器端返回内容
            int resultCode = conn.getResponseCode();
            if (resultCode == 200) {
                InputStreamReader in = new InputStreamReader(
                        conn.getInputStream());
                BufferedReader reader = new BufferedReader(in);
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
            return builder.toString();
        } catch (MalformedURLException e) {
            // e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            freeResource(out, null);
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 上传图片
     *
     * @param spec 链接地址
     * @param list 图片路径
     * @return
     * @throws IOException
     */
    public static String uploadPic(String spec, List<String> list) throws IOException {
        HttpURLConnection conn = null;
        DataOutputStream out = null;
        try {
            URL url = new URL(spec);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(500 * 1000);
            conn.setReadTimeout(50 * 1000);
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false);
            conn.setRequestMethod("POST"); // Post方式
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charsert", CHARSET);
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                    + ";boundary=" + BOUNDARY);
            out = new DataOutputStream(conn.getOutputStream());
            // 设置文本类型的参数
            StringBuilder builder = new StringBuilder();

            // 发送文件数据
            if (list != null && !list.isEmpty()) {
                int j = 0;
                for (String path : list) {
                    ++j;
                    if (path.equals("")) {
                        continue;
                    }
                    File file = new File(path);

                    builder.append(PREFIX)
                            .append(BOUNDARY)
                            .append(LINEND)
                            .append("Content-Disposition: form-data; name=\"file"
                                    + "\"; filename=\""
                                    + file.getName()
                                    + "\"" + LINEND)
                            .append("Content-Type: multipart/form-data; charset="
                                    + CHARSET + LINEND).append(LINEND);
                    out.write(builder.toString().getBytes());
                    //L.e(builder.toString());
                    InputStream in = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int i;
                    while ((i = in.read(buffer)) != -1) {
                        out.write(buffer, 0, i);
                    }
                    in.close();
                    out.write(LINEND.getBytes());
                    builder.delete(0, builder.length());
                }
            }
            // 请求结束标志
            builder.append(PREFIX).append(BOUNDARY).append(PREFIX)
                    .append(LINEND);
            out.write(builder.toString().getBytes());
            //L.e(builder.toString());
            out.flush();
            builder.delete(0, builder.length());
            // 读取服务器端返回内容
            int resultCode = conn.getResponseCode();
            if (resultCode == 200) {
                InputStreamReader in = new InputStreamReader(
                        conn.getInputStream());
                BufferedReader reader = new BufferedReader(in);
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
            return builder.toString();
        } catch (MalformedURLException e) {
            // e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            freeResource(out, null);
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 释放字节流资源
     *
     * @param out
     * @param in
     */
    public static void freeResource(OutputStream out, InputStream in) {
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
