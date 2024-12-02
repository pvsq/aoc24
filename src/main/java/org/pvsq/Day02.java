package org.pvsq;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day02 {
	private final String inputPathString;

	public static void answers(String inputPathString) {
		Day02 day02 = new Day02(inputPathString);
		System.out.println("Day 2 Answers");
		System.out.println("===============");
		System.out.println("Part 1 - " + day02.partOne());
		System.out.println("Part 2 - " + day02.partTwo());
		System.out.println("===============");
	}

	private Day02(String inputPathString) {
		this.inputPathString = inputPathString;
	}

	private int partOne() {
		int numSafeReports = 0;

		try (Scanner scanner = new Scanner(Paths.get(inputPathString))) {
			while (scanner.hasNextLine()) {
				int[] levels = Stream.of(scanner.nextLine().split(" "))
						.mapToInt(Integer::parseInt)
						.toArray();

				boolean isSafe = true;
				int deltaSum = 0;
				for (int i = 0; i < levels.length - 1; ++i) {
					if (!isSafe)
						break;

					if (Math.abs(levels[i] - levels[i + 1]) > 3)
						isSafe = false;

					if (i == 0) { // first comparison
						deltaSum = levels[i + 1] - levels[i];
						if (deltaSum == 0)
							isSafe = false;
						continue;
					}

					int delta = levels[i + 1] - levels[i];
					if (delta == 0)
						isSafe = false;
					if (deltaSum > 0) { // previously increasing
						if ((deltaSum + delta) < deltaSum) // decreasing
							isSafe = false;
					} else { // previously decreasing
						if ((deltaSum + delta) > deltaSum) // increasing
							isSafe = false;
					}
					deltaSum += delta;
				}

				// System.out.print(isSafe ? "Safe: " : "Unsafe: ");
				// System.out.println(Arrays.stream(levels)
				// .mapToObj(String::valueOf)
				// .collect(Collectors.joining(" ")));

				if (isSafe) {
					numSafeReports += 1;
				}
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		return numSafeReports;
	}

	private int partTwo() {
		return 0;
	}
}
