package ir.alirezaalijani.product.manager.application.ui.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static LocalDateTime getDate(String s){
        if (s!=null)
        return LocalDateTime.parse(s, formatter);
        return null;
    }
}
