package lotto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class LottoValidator {
    public static void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
    }
    public static void checkDuplicate(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>();
        numbers.stream()
                .filter(number -> !uniqueNumbers.add(number))
                .findFirst()
                .ifPresent(duplicate -> {
                    throw new IllegalArgumentException("중복된 숫자가 있습니다.");
                });
    }
    public static boolean validateNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
//            throw new IllegalArgumentException("[ERROR] 숫자만 입력해주세요.");
        }
        return true;
    }
    public static void validateLottoNumber(int input) {
        if (input < 1 || input > 45) {
            throw new IllegalArgumentException();
        }
    }
}
