package net.thumbtack.school.base;

public class StringOperations {

    /**
     * Возвращает суммарную длину строк, заданных массивом strings.
     *
     * @param strings
     * @return
     */
    public static int getSummaryLength(String[] strings) {
        int len = 0;
        for (String s : strings) {
            len += s.length();
        }
        return len;
    }

    /**
     * Возвращает двухсимвольную строку, состоящую из начального и конечного
     * символов заданной строки.
     *
     * @param string
     * @return
     */
    public static String getFirstAndLastLetterString(String string) {
        return string.substring(0, 1) + string.substring(string.length() - 1);
    }

    /**
     * Возвращает true, если обе строки в позиции index содержат один и тот же
     * символ, иначе false.
     *
     * @param string1
     * @param string2
     * @param index
     * @return
     */
    public static boolean isSameCharAtPosition(String string1, String string2, int index) {
        return string1.charAt(index) == string2.charAt(index);
    }

    /**
     * Возвращает true, если в обеих строках первый встреченный символ character
     * находится в одной и той же позиции. Просмотр строк ведется от начала.
     *
     * @param string1
     * @param string2
     * @param character
     * @return
     */
    public static boolean isSameFirstCharPosition(String string1, String string2, char character) {
        return string1.indexOf(character) == string2.indexOf(character);
    }

    /**
     * Возвращает true, если в обеих строках первый встреченный символ character
     * находится в одной и той же позиции. Просмотр строк ведется от конца.
     *
     * @param string1
     * @param string2
     * @param character
     * @return
     */
    public static boolean isSameLastCharPosition(String string1, String string2, char character) {
        return string1.lastIndexOf(character) == string2.lastIndexOf(character);
    }

    /**
     * Возвращает true, если в обеих строках первая встреченная подстрока str
     * начинается в одной и той же позиции. Просмотр строк ведется от начала.
     *
     * @param string1
     * @param string2
     * @param str
     * @return
     */
	public static boolean isSameFirstStringPosition(String string1, String string2, String str) {
        return string1.indexOf(str) == string2.indexOf(str);
    }

    /**
     * Возвращает true, если в обеих строках первая встреченная подстрока str
     * начинается в одной и той же позиции. Просмотр строк ведется от конца.
     *
     * @param string1
     * @param string2
     * @param str
	 * @return
	 */
	public static boolean isSameLastStringPosition(String string1, String string2, String str) {
        return string1.lastIndexOf(str) == string2.lastIndexOf(str);
    }

    /**
     * Возвращает true, если строки равны.
     *
     * @param string1
     * @param string2
     * @return
     */
    public static boolean isEqual(String string1, String string2) {
        return string1.equals(string2);
    }

    /**
     * Возвращает true, если строки равны без учета регистра (например, строки “abc”
     * и “aBC” в этом смысле равны).
     *
     * @param string1
     * @param string2
     * @return
     */
    public static boolean isEqualIgnoreCase(String string1, String string2) {
        return string1.equalsIgnoreCase(string2);
    }

    /**
     * Возвращает true, если строка string1 меньше строки string2.
     *
     * @param string1
     * @param string2
     * @return
     */
    public static boolean isLess(String string1, String string2) {
        return string1.compareTo(string2) < 0;
    }

    /**
     *
     * Возвращает true, если строка string1 меньше строки string2 без учета регистра
     * (например, строка “abc” меньше строки “ABCd” в этом смысле).
	 * 
	 * @param string1
	 * @param string2
     * @return
     */
    public static boolean isLessIgnoreCase(String string1, String string2) {
        return string1.compareToIgnoreCase(string2) < 0;
    }

    /**
     * Возвращает строку, полученную путем сцепления двух строк.
     *
     * @param string1
     * @param string2
     * @return
     */
    public static String concat(String string1, String string2) {
        return string1.concat(string2);
    }

    /**
     * Возвращает true, если обе строки string1 и string2 начинаются с одной и той
     * же подстроки prefix.
     *
     * @param string1
     * @param string2
     * @param prefix
     * @return
     */
    public static boolean isSamePrefix(String string1, String string2, String prefix) {
        return (string1.startsWith(prefix) && string2.startsWith(prefix));
    }

    /**
     * Возвращает true, если обе строки string1 и string2 заканчиваются одной и той
     * же подстрокой suffix.
     *
     * @param string1
     * @param string2
     * @param suffix
     * @return
     */
    public static boolean isSameSuffix(String string1, String string2, String suffix) {
        return (string1.endsWith(suffix) && string2.endsWith(suffix));
    }

    /**
     * Возвращает самое длинное общее “начало” двух строк. Если у строк нет общего
     * начала, возвращает пустую строку.
     *
     * @param string1
     * @param string2
     * @return
     */
    public static String getCommonPrefix(String string1, String string2) {
        int i = 0;
        for (; (i < string1.length() && i < string2.length()); i++) {
            if (string1.charAt(i) != string2.charAt(i)) {
                break;
            }
        }
        return string1.substring(0, i);
        /*
         * int index = string1.compareTo(string2); return string1.substring(0,
         * Math.abs(index));
         */
    }

    /**
     * Возвращает перевернутую строку.
     *
     * @param string
     * @return
     */
    public static String reverse(String string) {
        return new StringBuilder(string).reverse().toString();
    }

    /**
     * Возвращает true, если строка является палиндромом, то есть читается слева
     * направо так же, как и справа налево.
     *
     * @param string
     * @return
     */
    public static boolean isPalindrome(String string) {
        return string.equals(new StringBuilder(string).reverse().toString());
    }

    /**
     * Возвращает true, если строка является палиндромом, то есть читается слева
     * направо так же, как и справа налево, без учета регистра.
     *
     * @param string
     * @return
     */
    public static boolean isPalindromeIgnoreCase(String string) {
        return string.equalsIgnoreCase(new StringBuilder(string).reverse().toString());
    }

    /**
     * Возвращает самый длинный палиндром (без учета регистра) из массива заданных
     * строк. Если в массиве нет палиндромов, возвращает пустую строку.
     *
     * @param strings
     * @return
     */
    public static String getLongestPalindromeIgnoreCase(String[] strings) {
        String retStr = "";
        int maxlen = 0;
        for (String str : strings) {
            if (isPalindromeIgnoreCase(str)) {
                if (str.length() > maxlen) {
                    maxlen = str.length();
                    retStr = str;
                }
            }
        }
        return retStr;
    }

    /**
     * Возвращает true, если обе строки содержат один и тот же фрагмент длиной
     * length, начиная с позиции index.
     *
     * @param string1
     * @param string2
     * @param index
     * @param length
     * @return
     */
    public static boolean hasSameSubstring(String string1, String string2, int index, int length) {
        if ((string1.length() < index + length) || (string2.length() < index + length)) {
            return false;
        }
        return string1.substring(index, index + length).equals(string2.substring(index, index + length));
    }

    /**
     * Возвращает true, если после замены в string1 всех вхождений replaceInStr1 на
     * replaceByInStr1 и замены в string2 всех вхождений replaceInStr2 на
     * replaceByInStr2 полученные строки равны.
     *
     * @param string1
     * @param replaceInStr1
     * @param replaceByInStr1
     * @param string2
     * @param replaceInStr2
     * @param replaceByInStr2
     * @return
     */
    public static boolean isEqualAfterReplaceCharacters(String string1, char replaceInStr1, char replaceByInStr1,
                                                        String string2, char replaceInStr2, char replaceByInStr2) {
        return string1.replace(replaceInStr1, replaceByInStr1).equals(string2.replace(replaceInStr2, replaceByInStr2));
    }

    /**
     * Возвращает true, если после замены в string1 всех вхождений строки
     * replceInStr1 на replaceByInStr1 и замены в string2 всех вхождений
     * replceInStr2 на replaceByInStr2 полученные строки равны.
     *
     * @param string1
     * @param replaceInStr1
     * @param replaceByInStr1
     * @param string2
     * @param replaceInStr2
     * @param replaceByInStr2
     * @return
     */
    public static boolean isEqualAfterReplaceStrings(String string1, String replaceInStr1, String replaceByInStr1,
                                                     String string2, String replaceInStr2, String replaceByInStr2) {
        return string1.replace(replaceInStr1, replaceByInStr1).equals(string2.replace(replaceInStr2, replaceByInStr2));
    }

    /**
     * Возвращает true, если строка после выбрасывания из нее всех пробелов является
     * палиндромом, без учета регистра.
     *
     * @param string
     * @return
     */
    public static boolean isPalindromeAfterRemovingSpacesIgnoreCase(String string) {
        return isPalindromeIgnoreCase(string.replace(" ", ""));
    }

    /**
     * Возвращает true, если две строки равны, если не принимать во внимание все
     * пробелы в начале и конце каждой строки.
     *
     * @param string1
     * @param string2
     * @return
     */
    public static boolean isEqualAfterTrimming(String string1, String string2) {
        return string1.trim().equals(string2.trim());
    }

    /**
     * Для заданного массива целых чисел создает текстовую строку, в которой числа
     * разделены знаком “запятая” (т.н. формат CSV - comma separated values). Для
     * пустого массива возвращается пустая строка.
     *
     * @param array
     * @return
     */
    public static String makeCsvStringFromInts(int[] array) {
        if (array.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int val : array) {
                sb.append(val).append(",");
            }
            return sb.deleteCharAt(sb.length() - 1).toString();
        }
        return "";
    }

    /**
     * Для заданного массива вещественных чисел создает текстовую строку, в которой
     * числа разделены знаком “запятая”, причем каждое число записывается с двумя
     * знаками после точки. Для пустого массива возвращается пустая строка.
     *
     * @param array
     * @return
     */
    public static String makeCsvStringFromDoubles(double[] array) {
        if (array.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (double val : array) {
                sb.append(String.format("%.02f", val)).append(",");
            }
            return sb.deleteCharAt(sb.length() - 1).toString();
        }
        return "";
    }

    /**
     * То же, что и в упражнении 25, но возвращает StringBuilder.
     *
     * @param array
     * @return
     */
    public static StringBuilder makeCsvStringBuilderFromInts(int[] array) {
        StringBuilder sb = new StringBuilder();
        if (array.length > 0) {
            for (int val : array) {
                sb.append(val).append(",");
            }
            return sb.deleteCharAt(sb.length() - 1);
        }
        return sb;
    }

    /**
     * То же, что и в упражнении 26, но возвращает StringBuilder.
     *
     * @param array
     * @return
     */
    public static StringBuilder makeCsvStringBuilderFromDoubles(double[] array) {
        StringBuilder sb = new StringBuilder();
        if (array.length > 0) {
            for (double val : array) {
                sb.append(String.format("%.02f", val)).append(",");
            }
            return sb.deleteCharAt(sb.length() - 1);
        }
        return sb;
    }

    /**
     * Удаляет из строки символы, номера которых заданы в массиве positions.
     * Предполагается, что будут передаваться только допустимые номера,
     * упорядоченные по возрастанию. Номера позиций для удаления указаны для
     * исходной строки. Возвращает полученный в результате StringBuilder.
     *
     * @param string
     * @param positions
     * @return
     */
    public static StringBuilder removeCharacters(String string, int[] positions) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = positions.length; i > 0; i--) {
            sb = sb.deleteCharAt(positions[i - 1]);
        }
        return sb;
    }

    /**
     * Вставляет в строку символы. Массивы positions и characters имеют одинаковую
     * длину. В позицию positions[i] в исходной строке string вставляется символ
     * characters[i]. Если в массиве positions один и тот же номер позиции
     * повторяется несколько раз, это значит, что в указанную позицию вставляется
     * несколько символов, в том порядке, в котором они перечислены в массиве
     * characters. Предполагается, что будут передаваться только допустимые номера,
     * упорядоченные по неубыванию. Возвращает полученный в результате
     * StringBuilder.
     *
     * @param string
     * @param positions
     * @param characters
     * @return
     */
    public static StringBuilder insertCharacters(String string, int[] positions, char[] characters) {
        StringBuilder sb = new StringBuilder(string);
		for (int i = positions.length; i > 0; i--) {
			sb = sb.insert(positions[i - 1], characters[i - 1]);
		}
		return sb;
	}

}
