package com.ruoyi.web.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author 周建
 * @title: FileUtil
 * @projectName springboot_wechat_hotel
 * @description: TODO
 * @date 2020-02-2611:32
 */
@Slf4j
public class FileUtil {


    public static File multipartFileToFile(MultipartFile file){
        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            try {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] getFileBytes(String path){
        byte[] content = null;
        try {
            File filename = new File(path);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            int size = 0;
            while((size = in.read(temp)) != -1){
                out.write(temp, 0, size);
            }
            in.close();
            content = out.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return content;
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void deleteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
    /**
     * @Author 周建
     * @Description 将base64解码成字节数据
     * @Date 15:30 2020-02-27
     * @Param [imgStr]
     * @return byte[]
     */
    public static byte[] generateBytes(String imgStr) {
        // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {
            // 图像数据为空
            return null;
        }
        byte[] bytes = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            bytes = decoder.decodeBuffer(imgStr);
        } catch (Exception e) {
            bytes = null;
        }
        return bytes;
    }
    /**
     * 删除文件
     *
     * @param pathname
     * @return
     * @throws IOException
     */
    public static boolean deleteFile(String pathname) throws IOException {
        boolean result = false;
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
            result = true;
        }
        return result;
    }

    public static Workbook getWorkBook(MultipartFile file) {
        Workbook workbook = null;
        try {
            String fileName = file.getOriginalFilename();
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith("xlsx")){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        }catch (IOException e){
            log.error("excel文件解析失败",e);
        }catch (Exception e){
            log.error("excel文件解析失败",e);
        }
        return workbook;
    }

    public static boolean isAllRowEmpty(Row row, Row head) {
        int count = 0;
        //单元格数量
        int rowCount = head.getLastCellNum() - head.getFirstCellNum();
        //判断多少个单元格为空
        for (int c = 0; c < rowCount; c++) {
            Cell cell = row.getCell(c);
            if (cell == null || cell.getCellTypeEnum() == CellType.BLANK || StringUtils.isEmpty((cell+"").trim())){
                count += 1;
            }
        }

        if (count == rowCount) {
            return true;
        }

        return  false;
    }

    public static InputStream getInputStream(String relativePath, String fileName) throws IOException{
        String filePath = relativePath +System.getProperty("file.separator")+fileName;
        log.info("文件路径filePath ="+filePath);
        ClassPathResource cpr = new ClassPathResource(filePath);
        InputStream inputStream = cpr.getInputStream();
        return inputStream;
    }


    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static byte[] getHttpBytes(String url) {
        return sendRequest(url, "GET", null);
    }

//    private static byte[] sendRequest(String urlStr, String method, String params) {
//        byte[] result = null;
//        InputStream in = null;
//        try {
//            log.info("发送请求:" + urlStr + ",请求方式:" + method + ",请求参数:" + params);
//            URL realUrl = new URL(urlStr);
//            if (urlStr.startsWith("https:")) {
//                SSLContext sc = SSLContext.getInstance("SSL");
//                sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
//                // 打开和URL之间的连接
//                HttpsURLConnection httpsConnection = (HttpsURLConnection) realUrl.openConnection();
//                // 设置https相关属性
//                httpsConnection.setConnectTimeout(300000);
//                httpsConnection.setReadTimeout(300000);
//                httpsConnection.setRequestMethod(method);
//                httpsConnection.setUseCaches(false);
//                httpsConnection.setSSLSocketFactory(sc.getSocketFactory());
//                httpsConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
//                httpsConnection.setDoOutput(true);
//                httpsConnection.setDoInput(true);
//                // 设置通用的请求属性
//                httpsConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//                httpsConnection.setRequestProperty("accept", "*/*");
//                httpsConnection.setRequestProperty("connection", "Keep-Alive");
//                httpsConnection.setRequestProperty("user-agent",
//                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//                if (params != null) {
//                    httpsConnection.getOutputStream().write(params.getBytes("utf-8"));
//                    httpsConnection.getOutputStream().flush();
//                    httpsConnection.getOutputStream().close();
//                }
//                httpsConnection.connect();
//                in = httpsConnection.getInputStream();
//                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
//                byte[] buff = new byte[100];
//                int rc = 0;
//                while ((rc = in.read(buff, 0, 100)) > 0) {
//                    swapStream.write(buff, 0, rc);
//                }
//                result = swapStream.toByteArray();
//            } else if (urlStr.startsWith("http:")) {
//                HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
//                // 设置https相关属性
//                connection.setConnectTimeout(300000);
//                connection.setReadTimeout(300000);
//                connection.setDoOutput(true);
//                connection.setDoInput(true);
//                connection.setUseCaches(false);
//                connection.setRequestMethod(method);
//                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//                // 设置通用的请求属性
//                connection.setRequestProperty("accept", "*/*");
//                connection.setRequestProperty("connection", "Keep-Alive");
//                connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//                if (params != null) {
//                    connection.getOutputStream().write(params.getBytes("utf-8"));
//                    connection.getOutputStream().flush();
//                    connection.getOutputStream().close();
//                }
//                // 建立实际的连接
//                connection.connect();
//                in = connection.getInputStream();
//                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
//                byte[] buff = new byte[100];
//                int rc = 0;
//                while ((rc = in.read(buff, 0, 100)) > 0) {
//                    swapStream.write(buff, 0, rc);
//                }
//                result = swapStream.toByteArray();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("发送请求出现异常！",e);
//        }
//        // 使用finally块来关闭输入流
//        finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception e2) {
//                log.error("关闭数据流出现异:" + e2);
//            }
//        }
//        return result;
//    }

    public static byte[] sendRequest(String request , String requestMethod , String output ){
        byte[] result = null;
        try {
            log.info("获取文件资源,请求地址：{}，请求方法：{}，参数：{}",request,requestMethod,output);
            //建立连接
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(requestMethod);
            //设置请求内容编码格式
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            if(output!=null){
                OutputStream out = connection.getOutputStream();
                out.write(output.getBytes("UTF-8"));
                out.close();
            }
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = input.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            result = swapStream.toByteArray();
            //关闭连接、释放资源
            input.close();
            input = null;
            connection.disconnect();
        } catch (Exception e) {
            log.error("获取文件失败：",e);
            e.printStackTrace();
        }
        return result;
    }
}
