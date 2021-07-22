package io.gitee.welkinfast.gif;

/**
 * @Author yuanjg
 * @CreateTime 2021/05/19 16:36
 * @Version 1.0.0
 */
public class TestDemo {

    public static void main(String[] args) {
        rightBinaryTest();
    }

    public static void binaryTest() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int target = 6;
        int binary = binary(nums, target);
        System.out.println(binary);
    }

    private static int binary(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        // left <= regiht?
        // 因为初始化 right 的赋值是 nums.length - 1，即最后一个元素的索引，而不是 nums.length
        while (left <= right) {
            int mid = (right + left) / 2;
            if (nums[mid] > target) {
                //mid 已经搜索过，从搜索区间中去除
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }


    public static void leftBinaryTest() {
        int[] nums = {1, 2, 4, 4, 4, 4, 7, 8, 9};
        int target = 4;
        int binary = leftBinary(nums, target);
        System.out.println(binary);
    }

    private static int leftBinary(int[] nums, int target) {
        int left = 0;
        int right = nums.length;
        while (left < right) {
            int mid = (right + left) / 2;
            if (nums[mid] > target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid; //继续往左查找
            }
        }
        return left;
    }


    public static void rightBinaryTest() {
        int[] nums = {1, 2, 4, 4, 4, 4, 7, 8, 9};
        int target = 4;
        int binary = rightBinary(nums, target);
        System.out.println(binary);
    }

    private static int rightBinary(int[] nums, int target) {
        int left = 0;
        int right = nums.length;
        while (left < right) {
            int mid = (right + left) / 2;
            if (nums[mid] > target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                left = mid + 1; //继续往左查找
            }
        }
        return left - 1;
    }
}
