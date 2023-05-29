package lotto;
import camp.nextstep.edu.missionutils.Console;
public class LottoIOHandler {
    public static final String INPUT_AMOUNT = "구입금액을 입력해 주세요.";
    public static final String INPUT_LOTTO_NUMBERS = "당첨 번호를 입력해 주세요.";
    public static final String INPUT_BONUS_NUMBER = "보너스 번호를 입력해 주세요.";
    // 로또 구입 금액 입/출력
    public static String input() {
        return Console.readLine();
    }
    public static void output(String s) {
        System.out.println(s);
    }
}
