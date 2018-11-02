package src;

public class AbstractItemDriver {

	public static void main(String[] args) {
		String[] test1 = {"1"};
		String[] test2 = {"2"};
		
		
		WrittenItem o1 = new WrittenItem("test1", "test2", 0, test1, 0, "test3");
		MediaItem o2 = new MediaItem("test1", "test5", 0, test2, 0, 0, "test6");
		
		System.out.println(o1);
		System.out.println(o2);
		
		System.out.println(o1.equals(o2));

	}

}
