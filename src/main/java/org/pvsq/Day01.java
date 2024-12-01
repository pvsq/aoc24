package org.pvsq;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

class Day01 {
	private List<Integer> leftList = new ArrayList<Integer>();
	private List<Integer> rightList = new ArrayList<Integer>();
	private final String inputPathString;

	public static void answers(String inputPathString) {
		Day01 day01 = new Day01(inputPathString);
		System.out.println("Day 1 Answers");
		System.out.println("===============");
		System.out.println("Part 1 - " + day01.partOne());
		System.out.println("Part 2 - " + day01.partTwo());
		System.out.println("===============");
	}

	private Day01(String inputPathString) {
		this.inputPathString = inputPathString;
	}

	private int partOne() {
		int length = 0;
		int distance = 0;

		try (Scanner scanner = new Scanner(Paths.get(inputPathString))) {
			while (scanner.hasNextLine()) {
				String[] splitIDs = scanner.nextLine().split("   ");
				leftList.add(Integer.parseInt(splitIDs[0]));
				rightList.add(Integer.parseInt(splitIDs[1]));
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}

		leftList.sort(Integer::compare);
		rightList.sort(Integer::compare);

		length = leftList.size();
		for (int i = 0; i < length; ++i) {
			distance += Math.abs(leftList.get(i) - rightList.get(i));
		}

		return distance;
	}

	private int partTwo() {
		Map<Integer, Integer> duplicatesScore = new HashMap<Integer, Integer>();
		int similarity = 0;

		int length = leftList.size();
		for (int i = 0; i < length; ++i) {
			int current = leftList.get(i);
			if (duplicatesScore.containsKey(current)) {
				similarity += duplicatesScore.get(current);
				continue;
			}

			int multiplier = 0;
			for (int j = 0; j < length; ++j) {
				// the lists are sorted at this point
				if (rightList.get(j) > current)
					break;

				if (rightList.get(j) == current)
					multiplier += 1;
			}
			duplicatesScore.put(current, current * multiplier);
			similarity += current * multiplier;
		}

		return similarity;
	}
}
