package lotto;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;

public class LottoManager {
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;
    private static final int NUMBER_COUNT = 6;
    private final List<List<Integer>> lottoTickets;
    private double lotteryPurchaseAmount;
    private int bonusNumber;
    private final int[] lottoResult;
    private int bonusMatchCount;
    public LottoManager() { // 로또 추첨 할 때마다 필드 변수 초기화
        this.lottoTickets = new ArrayList<>();
        this.lotteryPurchaseAmount = 0;
        this.lottoResult = new int[4];
        this.bonusNumber = 0;
        this.bonusMatchCount = 0;
    }
    public void run() {
        LottoIOHandler.output(LottoIOHandler.INPUT_AMOUNT);
        String input = LottoIOHandler.input();
        if (!LottoValidator.validateNumeric(input)) {
            System.out.println("[ERROR]");
            return;
        }
        this.lotteryPurchaseAmount = Integer.parseInt(input);
        int lottoCount = getLottoCount(lotteryPurchaseAmount);
        LottoIOHandler.output("\n" + lottoCount + "개를 구매했습니다.");
        saveRandomLottoNumbers(lottoCount);
        LottoIOHandler.output(LottoIOHandler.INPUT_LOTTO_NUMBERS);
        String lottoNumbers = LottoIOHandler.input();
        Lotto lotto = new Lotto(splitLottoNumbersByComma(lottoNumbers));
        countWinningTickets(lotto);
        LottoIOHandler.output(LottoIOHandler.INPUT_BONUS_NUMBER);
        String bonus = LottoIOHandler.input();
        if (!LottoValidator.validateNumeric(bonus)) {
            System.out.println("[ERROR]");
            return;
        }
        this.bonusNumber = Integer.parseInt(bonus);
        printResult();
    }

    private List<Integer> generateLottoNumbers() {
        return Randoms.pickUniqueNumbersInRange(MIN_NUMBER, MAX_NUMBER, NUMBER_COUNT);
    }
    private int getLottoCount(double lottoAmount) {
        if (lottoAmount % 1000 != 0) {
            throw new IllegalArgumentException();
        }
        return (int) (lottoAmount / 1000);
    }
    private void saveRandomLottoNumbers(int lottoCount) {
        for (int i = 0; i < lottoCount; i++) {
            List<Integer> number = generateLottoNumbers();
            List<Integer> numbers = new ArrayList<>(number); // 수정 가능한 컬렉션으로 변환
            Collections.sort(numbers);
            lottoTickets.add(numbers);
            printLottoTicket(numbers);
        }
    }
    private void printLottoTicket(List<Integer> numbers) {
        String result = "";
        result += "[";
        for (int i = 0; i < numbers.size(); i++) {
            result += numbers.get(i);
            if (i < numbers.size() - 1) {
                result += ", ";
            }
        }
        result += "]";
        LottoIOHandler.output(result);
    }
    private List<Integer> splitLottoNumbersByComma(String lottoNumbers) {
        StringTokenizer st = new StringTokenizer(lottoNumbers, ",");
        List<Integer> splitNumbers = new ArrayList<>();
        while(st.hasMoreTokens()) {
            splitNumbers.add(Integer.parseInt(st.nextToken()));
        }
        return splitNumbers;
    }
    private void countWinningTickets(Lotto lotto) {
        List<Integer> winningNumber = lotto.getNumbers();
        for (List<Integer> ticket : lottoTickets) {
            int count = matchLottoNumbers(winningNumber, ticket);
            boolean isBonusWin = matchBonusNumber(ticket);
            setResultNumbers(count, isBonusWin);
        }
    }
    private int matchLottoNumbers(List<Integer> winningNumbers, List<Integer> ticket) {
        int numberCount = 0;
        for (Integer winningNumber : winningNumbers) {
            if (ticket.contains(winningNumber)) { // 당첨 번호가 티켓 번호 안에 존재하면 count 에 1을 더함
                numberCount++;
            }
        }
        return numberCount;
    }

    private boolean matchBonusNumber(List<Integer> ticket) {
        return ticket.contains(bonusNumber);
    }
    private void setResultNumbers(int count, boolean isBonusWin) {
        if (count >= 3 && !isBonusWin) { // 3개 이상 일치하면 3 ~ 6에 해당하는 배열 인덱스에 count
            lottoResult[count - 3] += 1;
            return;
        }
        if (count == 5) { // 5개 번호 + 보너스 번호 일치
            bonusMatchCount += 1;
        }
    }
    private double calculateProfitRate(int[] lottoResult, int bonusMatchCount) {
        double profit = 0;
        profit += lottoResult[0] * 5000;
        profit += lottoResult[1] * 50000;
        profit += lottoResult[2] * 1500000;
        profit += bonusMatchCount * 30000000;
        profit += lottoResult[3] * 2000000000;
        return 100 * (Math.round(profit / lotteryPurchaseAmount * 1000)) / 1000.0;
    }

    private void printResult() {
        LottoIOHandler.output("당첨 통계\n" +
                "---\n" +
                "3개 일치 (5,000원) - " + lottoResult[0] + "개\n" +
                "4개 일치 (50,000원) - " + lottoResult[1] + "개\n" +
                "5개 일치 (1,500,000원) - " + lottoResult[2] + "개\n" +
                "5개 일치, 보너스 볼 일치 (30,000,000원) - " + bonusMatchCount + "개\n" +
                "6개 일치 (2,000,000,000원) - " + lottoResult[3] + "개\n" +
                "총 수익률은 " + calculateProfitRate(lottoResult, bonusMatchCount) + "%입니다.");
    }
}
