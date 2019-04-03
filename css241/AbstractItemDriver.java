package css241;

public class AbstractItemDriver {

	public static void main(String[] args) {
		String[] test1 = {"1"};
		String[] test2 = {"2"};
		
		
		WrittenItem o1 = new WrittenItem("test1", "test2", 0, test1, 0, "test3");		
		WrittenItem o11 = new WrittenItem("test11", "test22", 0, test2, 0, "test3");
		MediaItem o2 = new MediaItem("test4", "test5", 0, test2, 0, 0, "test6");
		MediaItem o22 = new MediaItem("test4", "test55", 0, test2, 0, 0, "test6");
		
		System.out.println(o1);
		System.out.println(o2);
		
		System.out.println(o1.equals(o2));
		
		
		System.out.println(o1.compareTo(o1));
		System.out.println(o1.compareTo(o11));
		System.out.println(o2.compareTo(o22));
		//System.out.println(o1.compareTo(o2));
		//System.out.println(o2.compareTo(o11));
		//System.out.println(o22.compareTo(o11));
		

	}

}
