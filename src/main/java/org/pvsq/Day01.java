package org.pvsq;

import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

class Day01 {
	public static int PartOne(String inputPathString) {
		List<Integer> leftList = new ArrayList<Integer>();
		List<Integer> rightList = new ArrayList<Integer>();
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
		for (int i = 0; i < length; i++) {
			distance += Math.abs(leftList.get(i) - rightList.get(i));
		}

		return distance;
	}
}
