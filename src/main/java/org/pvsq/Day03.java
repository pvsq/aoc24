package org.pvsq;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Day03 {
	private String inputData;
	private final Pattern mulPattern = Pattern.compile(
			"mul\\(([0-9]+),([0-9]+)\\)|do\\(\\)|don't\\(\\)");
	private Matcher mulMatcher;

	public static void answers(String inputPathString) {
		Day03 day03 = new Day03(inputPathString);
		System.out.println("Day 3 Answers");
		System.out.println("===============");
		System.out.println("Part 1 - " + day03.partOne());
		System.out.println("Part 2 - " + day03.partTwo());
		System.out.println("===============");
	}

	private Day03(String inputPathString) {
		try {
			inputData = new String(
					Files.readAllBytes(Paths.get(inputPathString)));
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}

	private int partOne() {
		int mulSum = 0;
		mulMatcher = mulPattern.matcher(inputData);
		while (mulMatcher.find()) {
			// System.out.println(mulMatcher.group(0));
			String firstOp = mulMatcher.group(1);
			String secondOp = mulMatcher.group(2);
			if ((firstOp != null) && (secondOp != null))
				mulSum += Integer.parseInt(firstOp) * Integer.parseInt(secondOp);
		}
		return mulSum;
	}

	private int partTwo() {
		int mulSum = 0;
		boolean disabled = false;
		mulMatcher = mulMatcher.reset();

		while (mulMatcher.find()) {
			String instruction = mulMatcher.group(0);

			if (instruction != null && !instruction.startsWith("mul")) {
				if (instruction.equalsIgnoreCase("don't()") && !disabled)
					disabled = true;
				if (instruction.equalsIgnoreCase("do()") && disabled)
					disabled = false;
				continue;
			}

			if (!disabled) {
				int firstOp = Integer.parseInt(mulMatcher.group(1));
				int secondOp = Integer.parseInt(mulMatcher.group(2));
				mulSum += firstOp * secondOp;
			}
		}

		return mulSum;
	}
}
