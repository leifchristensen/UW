package css241;

public class MoneyTypeDriver {

	public static void main(String[] args) {
		MoneyType m1 = new MoneyType(1, 35, "USD");
		MoneyType m2 = new MoneyType(3, 75, "USD");
		MoneyType m3 = m1.add(m2);
		
		System.out.println(m1);
		System.out.println(m2);
		System.out.println(m3);

	}

}
