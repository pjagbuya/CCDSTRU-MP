package models;

import java.util.ArrayList;
import java.util.List;

public class CombinationFunc {
  
  public static List<List<Integer>> combine(int[] nums, int k) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, k, 0);
    return result;
  }

  private static void backtrack(List<List<Integer>> result, List<Integer> temp, int[] nums, int k, int start) {
    if (temp.size() == k) {
      result.add(new ArrayList<>(temp));
    } else {
      for (int i = start; i < nums.length; i++) {
        temp.add(nums[i]);
        backtrack(result, temp, nums, k, i + 1);
        temp.remove(temp.size() - 1);
      }
    }
  }

  public static void main(String[] args) {
    int[] nums = {1, 2, 3, 4};
    int k = 2;
    List<List<Integer>> result = combine(nums, k);
    System.out.println(result);
  }
}