package org.pvsq;

class AoC24 {
	public static void main(String[] args) {
		parseArgs(args);
	}

	private static void parseArgs(String[] args) {
		if (args.length < 2 || args.length > 2) {
			System.out.println(
					"Usage: java -jar <path-to-jar> <day_number> <input_file>");
			System.exit(1);
		}

		int day = Integer.parseInt(args[0]);
		switch (day) {
			case 1:
				Day01.answers(args[1]);
				break;
			case 2:
				Day02.answers(args[1]);
				break;
			case 3:
				Day03.answers(args[1]);
				break;
			case 4:
				Day04.answers(args[1]);
				break;
			default:
				System.out.println("Day input must be between 1 and 25, inclusive.");
				break;
		}
	}
}
