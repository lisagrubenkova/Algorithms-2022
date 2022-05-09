package lesson7;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {

    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */

    // Трудоёмкость: O(m*n), m, n - длины строк
    // Ресурсоёмкость: O(m*n)
    public static String longestCommonSubSequence(String first, String second) {
        int n = first.length();
        int m = second.length();
        int[][] dp = new int[n + 1][m + 1];

        for(int i = 1; i < n + 1; i++)
            for(int j = 1; j < m + 1; j++){
                if (first.charAt(i - 1) == second.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }

        ArrayList<String> answer = new ArrayList<String>();
        int i = n;
        int j = m;
        while (i > 0 && j > 0){
            if (first.charAt(i - 1) == second.charAt(j - 1)){
                answer.add(String.valueOf(first.charAt(i - 1)));
                i -= 1;
                j -= 1;
            }
            else if (dp[i - 1][j] == dp[i][j]){  i -= 1;
            }
            else {
                j -= 1;
            }
        }

        Collections.reverse(answer);

        StringBuilder sb = new StringBuilder();
        for (String s : answer){
            sb.append(s);
        }
        return (sb.toString());
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    // Трудоёмкость: O(n^2)
    // Ресурсоёмкость: O(n)
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        List<Integer> answer = new ArrayList<>();
        if (list.isEmpty()) return answer;
        int[] masLen = new int[list.size()];
        int[] masInd = new int[list.size()];
        int position = 0;
        for (int i = 0; i < list.size(); i++) {
            masLen[i] = 1;
            masInd[i] = -1;
            int len = 0;
            int j = 0;
            while (j < i) {
                if (list.get(j) < list.get(i)) {
                    if (1 + masLen[j] > masLen[j] && 1 + masLen[j] > len) {
                        masLen[i] = 1 + masLen[j];
                        len = 1 + masLen[j];
                        masInd[i] = j;
                    }
                }
                j++;
            }
        }

        int ans = masLen[0];
        int k = 0;
        while (k < list.size()){
            if (masLen[k] > ans) {
                ans = masLen[k];
                position = k;
            }
            k++;
        }
        while (position != -1) {
            int i = position;
            while (i > 0) {
                if (masInd[i - 1] == masInd[i] && list.get(i) > list.get(i - 1)) position--;
                else break;
                i--;
            }
            answer.add(list.get(position));
            position = masInd[position];
        }
        Collections.reverse(answer);
        return answer;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }
}
