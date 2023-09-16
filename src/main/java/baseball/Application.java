package baseball;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // 컴퓨터 수 생성
        int comNum = getComputerNumber();
        int[] comDigits = Stream.of(String.valueOf(comNum).split(""))
                .mapToInt(Integer::parseInt)
                .toArray();

        System.out.println("숫자 야구 게임을 시작합니다.");

        playNumBaseballGame(sc, comDigits);
        sc.close();
    }

    private static void playNumBaseballGame(Scanner sc, int[] comDigits) {
        int comNum = 0;
        do {
            // 사용자 수 입력
            System.out.print("숫자를 입력해주세요 : ");
            int userNum = sc.nextInt();

            // 예외 - 사용자가 잘못된 값을 입력할 경우 (미구현)

            int[] userDigits = Stream.of(String.valueOf(userNum).split(""))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            
            int ballCnt = getBallCnt(comDigits, userDigits);
            int strikeCnt = getStrikeCnt(comDigits, userDigits);

            createOutputPrint(ballCnt, strikeCnt);
            
            // 3개의 숫자를 모두 맞혔을 시
            if (strikeCnt == 3) {
                System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");

                int flag = sc.nextInt();
                if (flag == 2) {
                    break;
                }
                comNum = getComputerNumber();
                comDigits = Stream.of(String.valueOf(comNum).split(""))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }
        } while (true);
    }

    // 입력한 수에 대한 결과를 볼, 스트라이크 개수로 표시
    private static void createOutputPrint(int ballCnt, int strikeCnt) {
        if (ballCnt > 0 && strikeCnt > 0) {
            System.out.println(ballCnt +"볼 "+ strikeCnt +"스트라이크");
        } else if (ballCnt > 0 && strikeCnt == 0) {
            System.out.println(ballCnt +"볼");
        } else if (ballCnt == 0 && strikeCnt > 0) {
            System.out.println(strikeCnt +"스트라이크");
        } else {
            System.out.println("낫싱");
        }
    }

    // 사용자가 입력한 값과 컴퓨터 값을 비교해서 볼 확인
    private static int getBallCnt(int[] comDigits, int[] userDigits) {
        int ballCnt = 0;
        int[] numsToCompare = {0, 0, 0, 0, 0, 0};

        numsToCompare[0] = comDigits[1];
        numsToCompare[1] = comDigits[2];
        numsToCompare[2] = comDigits[0];
        numsToCompare[3] = comDigits[2];
        numsToCompare[4] = comDigits[0];
        numsToCompare[5] = comDigits[1];

        for (int i = 0; i < 3; i++) {
            int[] copyCompareArr = new int[2];
            System.arraycopy(numsToCompare, i*2, copyCompareArr, 0, 2);
            int num = userDigits[i];
            if (Arrays.stream(copyCompareArr).anyMatch(x -> x == num)) {
                ballCnt += 1;
            }
        }
        return ballCnt;
    }

    // 사용자가 입력한 값과 컴퓨터 값을 비교해서 스트라이크 확인
    private static int getStrikeCnt(int[] comDigits, int[] userDigits) {
        int strikeCnt = 0;
        for (int i = 0; i < 3; i++) {
            if (comDigits[i] == userDigits[i]) {
                strikeCnt += 1;
            }
        }
        return strikeCnt;
    }

    // 3자리 컴퓨터 랜덤 숫자 생성
    private static int getComputerNumber() {
        Random rd = new Random();
        while (true) {
            // 100 ~ 999 범위의 난수 발생
            int rdNum = rd.nextInt(900) + 100;
            int digitSecond = rdNum / 10;
            System.out.println(rdNum);
            if (!(((rdNum / 100) == (digitSecond % 10)) || ((rdNum / 100) == (rdNum % 10)) || ((digitSecond % 10) == (rdNum % 10)))) {
                if ((digitSecond % 10 != 0) && (rdNum % 10 != 0)) {
                    return rdNum;
                }
            }
        }
    }
}
