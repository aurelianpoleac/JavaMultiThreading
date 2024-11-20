package G.CountDownLatch.c.pr;

import java.util.Random;

public class TeamName {

	public static String getName() {
		Random random = new Random();
		int count = 0;
		StringBuilder teamName = new StringBuilder();
		while (count < 3) {
			int i = random.nextInt(99);
			if (i > 64 && i < 90) {
				teamName.append((char) i);
				count++;
			}
		}
		return teamName.toString();
	}
}
