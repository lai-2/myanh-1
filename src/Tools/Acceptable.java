/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

/**
 *
 * @author myanh
 */
public interface Acceptable {

    public final String CUSTOMER_VALID = "^[CcGgKk]\\d{4}$";
    public final String NAME_VALID = "^[A-Za-z\\s]{2,25}$";
    //public final String PHONE_VALID = "^(0)\\d{9}$";
    public final String PHONE_VALID = "^(03|05|07|08|09)\\d{8}$";
    public final String EMAIL_VALID = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public final String DOUBLE_VALID = "^\\d+(\\.\\d+)?$";
    public final String INTEGER_VALID = "\\d+";

    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
}
