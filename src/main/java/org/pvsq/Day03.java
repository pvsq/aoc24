package org.pvsq;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day03 {
	private final String inputPathString;

	public static void answers(String inputPathString) {
		Day03 day03 = new Day03(inputPathString);
		System.out.println("Day 3 Answers");
		System.out.println("===============");
		System.out.println("Part 1 - " + day03.partOne());
		System.out.println("Part 2 - " + day03.partTwo());
		System.out.println("===============");
	}

	private Day03(String inputPathString) {
		this.inputPathString = inputPathString;
	}

	private int partOne() {
		int mulSum = 0;
		try {
			String input = new String(
					Files.readAllBytes(Paths.get(inputPathString)));
			Pattern mulPattern = Pattern.compile("mul\\(([0-9]+),([0-9]+)\\)");
			Matcher mulMatcher = mulPattern.matcher(input);
			while (mulMatcher.find()) {
				mulSum += Integer.parseInt(mulMatcher.group(1))
						* Integer.parseInt(mulMatcher.group(2));
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		return mulSum;
	}

	private int partTwo() {
		return 0;
	}
}
