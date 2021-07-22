package io.gitee.welkinfast.admin;

/**
 * @Author yuanjg
 * @CreateTime 2020/08/18 14:33
 * @Version 1.0.0
 */
public class TestDemo {

    public static void main(String[] args) {
        int[] a = getNext("ABABAAABABAA");
        int i = kmpSearch("BBC ABCDAB ABCDABCDABDE", "ABCDABD");
        System.out.println("");
    }

    // 不需要回溯 -- 公共串有公共前后缀 -- 模式串前缀移动到后缀的位置
    // next 数组
    public static int kmpSearch(String source, String target) {
        int[] next = getNext(target);
        for (int i = 0, j = 0; i < source.length(); i++) {
            while (j > 0 && source.charAt(i) != target.charAt(j)) {
                j = next[j - 1];
            }
            if (source.charAt(i) == target.charAt(j)) {
                j++;
            }
            if (j == target.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    // A B C D A B D
      // A B C D A B D
      //{0,0,0,0,1,2,0}
    public static int[] getNext(String target) {
        int[] next = new int[target.length()];
        next[0] = 0;
        int i = 0;
        for (int j = 1; j < target.length(); j++) {
            while (i > 0 && target.charAt(i) != target.charAt(j)) {
                i = next[i - 1];
            }
            if (target.charAt(i) == target.charAt(j)) {
                i++;
                next[j] = i;
            } else {
                next[j] = 0;
            }
        }
        return next;
    }
}
