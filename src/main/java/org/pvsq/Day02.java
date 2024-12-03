package org.pvsq;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day02 {
	private final String inputPathString;
	private int numSafeReports = 0;
	private Map<int[], Integer> unsafeReportsMap = new HashMap<int[], Integer>();

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
		try (Scanner scanner = new Scanner(Paths.get(inputPathString))) {
			while (scanner.hasNextLine()) {
				int[] levels = Stream.of(scanner.nextLine().split(" "))
						.mapToInt(Integer::parseInt)
						.toArray();

				int unsafeLevelIdx = getUnsafeIndex(levels);
				if (unsafeLevelIdx == -1) {
					numSafeReports += 1;
				} else {
					unsafeReportsMap.put(levels, unsafeLevelIdx);
				}
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}

		return numSafeReports;
	}

	private int partTwo() {
		for (Map.Entry<int[], Integer> reportIdxMap : unsafeReportsMap.entrySet()) {
			int unsafeIdx = reportIdxMap.getValue();
			int[] levels = reportIdxMap.getKey();

			for (int i = unsafeIdx - 1; i <= unsafeIdx + 1; ++i) {
				int currentUnsafeIdx = i;
				int[] unsafeRemovedReport = IntStream.range(0, levels.length)
						.filter(j -> j != currentUnsafeIdx)
						.map(j -> levels[j])
						.toArray();

				if (getUnsafeIndex(unsafeRemovedReport) == -1) {
					numSafeReports += 1;
					break;
				}
			}
		}

		return numSafeReports;
	}

	private int getUnsafeIndex(int[] levels) {
		int deltaSum = 0;

		for (int i = 0; i < levels.length - 1; ++i) {
			if (Math.abs(levels[i] - levels[i + 1]) > 3)
				return i;

			int delta = levels[i + 1] - levels[i];
			if (delta == 0)
				return i;
			if (deltaSum > 0) {
				if ((deltaSum + delta) < deltaSum)
					return i;
			} else if (deltaSum < 0) {
				if ((deltaSum + delta) > deltaSum)
					return i;
			}
			deltaSum += levels[i + 1] - levels[i];
		}

		return -1; // safe
	}
}
