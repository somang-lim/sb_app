package com.ll.exam.app10.util;

import org.apache.tika.Tika;
import org.springframework.web.client.RestTemplate;

import javax.management.Query;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Util {
    public static class str {
        public static boolean empty(String str) {
            if (str == null) return true;
            if (str.trim().length() == 0) return true;

            return false;
        }

        public static boolean eq(String str1, String str2) {
            if (str1 == null && str2 == null) return true;
            if (str1 == null) {
                str1 = "";
            }

            str1 = str1.trim();

            if (str2 == null) {
                str2 = "";
            }

            str2 = str2.trim();

            return str1.equals(str2);
        }
    }

    public static class url {
        public static String encode(String str) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return str;
            }
        }
    }

    public static class date {

        public static String getCurrentDateFormatted(String pattern) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(new Date());
        }
    }

    public static class file {

        public static String getExt(String filename) {
            return Optional.ofNullable(filename)
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                    .orElse("");
        }

        public static String downloadImg(String url, String filePath) {
            new File(filePath).getParentFile().mkdirs();

            byte[] imageBytes = new RestTemplate().getForObject(url, byte[].class);
            try {
                Files.write(Paths.get(filePath), imageBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String mimeType = null;
            try {
                mimeType = new Tika().detect(new File(filePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String ext = mimeType.replaceAll("image/", "");
            ext = ext.replaceAll("jpeg", "jpg");

            String newFilePath = filePath + "." + ext;

            new File(filePath).renameTo(new File(newFilePath));

            return newFilePath;
        }

        public static String getFileExtTypeCodeFromFileExt(String ext) {
            switch (ext) {
                case "jpeg":
                case "jpg":
                case "gif":
                case "png":
                    return "img";
                case "mp4":
                case "avi":
                case "mov":
                    return "video";
                case "mp3":
                    return "audio";
            }
            return "etc";
        }

        public static String getFileExtType2CodeFromFileExt(String ext) {
            switch (ext) {
                case "jpeg":
                case "jpg":
                    return "jpg";
                case "gif":
                case "png":
                case "mp4":
                case "mov":
                case "avi":
                case "mp3":
                    return ext;
            }

            return "etc";
        }
    }
}
