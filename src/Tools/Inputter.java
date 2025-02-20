
package Tools;

import java.util.Scanner;

/**
 *
 * @author myanh
 */
public class Inputter {

    private Scanner ndl;

    public Inputter() {
        this.ndl = new Scanner(System.in);
    }

    public String getString(String mess) {
        System.out.println(mess);
        return ndl.nextLine();
    }

    public int getInt(String mess) {
        int result = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.INTEGER_VALID)) {
            result = Integer.parseInt(temp);
        }
        return result;
    }

    public double getDouble(String mess) {
        double result = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID)) {
            result = Double.parseDouble(temp);
        }
        return result;
    }

    public String inputAndLoop(String mess, String pattern) {
        String result = "";
        boolean more = true;
        do {
            result = getString(mess);
            more = !Acceptable.isValid(result, pattern);
            if (more) {
                System.out.println("Data is invalid !. Re-enter ...");
            }
        } while (more);
        return result.trim();
    }
}
