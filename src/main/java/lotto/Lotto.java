package lotto;

import java.util.List;


public class Lotto {
    private final List<Integer> numbers;
    public Lotto(List<Integer> numbers) {
        LottoValidator.validate(numbers);
        LottoValidator.checkDuplicate(numbers);
        this.numbers = numbers;
    }
    public List<Integer> getNumbers() {
        return numbers;
    }
}
