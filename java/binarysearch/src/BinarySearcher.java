
public class BinarySearcher {
	
	public static void main(String[] args) {
		int[] arrayToSearch = {1,2,5,6,7,8};
		BinarySearcher searcher = new BinarySearcher(arrayToSearch);
		for( int i=0 ; i<8 ; i++ ) {
			System.out.println(searcher.search(i));
		}
	}
	
	private int[] arrayToSearch;
	
	public BinarySearcher(int[] arrayToSearch) {
		this.arrayToSearch = arrayToSearch;
	}
	
	public int search(int n) {
		int[] range = {0, arrayToSearch.length};
		while (range[0] < range[1]) {
			int index = (range[0]+range[1])/2;
			if (n < arrayToSearch[index]) {
				range[1] = index;
			}else if (n > arrayToSearch[index]) {
				range[0] = index+1;
			}else {
				return index;
			}
		}
		return -1;
	}

}
