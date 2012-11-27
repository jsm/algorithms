import java.util.Arrays;

public class MergeSorter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] numsToSort = {3,7,8,4,1,9,6,6,12,1,1,4};
		System.out.println(Arrays.toString(MergeSorter.sort(numsToSort)));

	}
	
	public static int[] sort(int[] input) {
		if (input.length < 2) {
			return input;
		}
		int[] split1 = sort(Arrays.copyOf(input, input.length/2));
		int[] split2 = sort(Arrays.copyOfRange(input, input.length/2, input.length));
		return merge(split1, split2);
	}
	
	private static int[] merge(int[] split1, int[] split2) {
		if (split1.length < 1) return split2;
		if (split2.length < 1) return split1;
		
		int[] output = new int[split1.length + split2.length];
		
		int counter1, counter2;
		counter1 = counter2 = 0;
		
		while (counter1+counter2 < split1.length+split2.length) {
			if (counter2 >= split2.length || (counter1 < split1.length && split1[counter1] < split2[counter2])) {
				output[counter1+counter2] = split1[counter1];
				counter1++;
			}else {
				output[counter1+counter2] = split2[counter2];
				counter2++;
			}
		}
		return output;
	}

}
