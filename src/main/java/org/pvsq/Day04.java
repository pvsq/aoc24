package org.pvsq;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day04 {
	private final int DIM = 140;
	private final int MAXLEN = DIM * DIM;
	private final String XMAS = "XMAS";
	private final String SAMX = "SAMX";
	private String xmasMatrix;
	private int xmasCount = 0;
	private int crossMasCount = 0;

	public static void answers(String inputPathString) {
		Day04 day04 = new Day04(inputPathString);
		System.out.println("Day 4 Answers");
		System.out.println("===============");
		System.out.println("Part 1 - " + day04.partOne());
		System.out.println("Part 2 - " + day04.partTwo());
		System.out.println("===============");
	}

	private Day04(String inputPathString) {
		try (Scanner scanner = new Scanner(Paths.get(inputPathString))) {
			StringBuilder xmasBuilder = new StringBuilder(MAXLEN);
			while (scanner.hasNext()) {
				xmasBuilder.append(scanner.nextLine());
			}
			xmasMatrix = xmasBuilder.toString();
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
	}

	private int partOne() {
		int i;
		for (i = 0; i < MAXLEN; i += DIM) { // horizontal count
			xmasCount += countOfXmas(xmasMatrix.substring(i, i + DIM));
		}

		for (i = 0; i < DIM; ++i) { // vertical count
			int iLoc = i;
			xmasCount += countOfXmas(
					IntStream.range(0, DIM)
							.mapToObj(j -> String.valueOf(xmasMatrix.charAt(iLoc + j * DIM)))
							.collect(Collectors.joining()));
		}

		for (i = DIM - 1; i >= 0; --i) { // diagonal r2l count
			xmasCount += countOfXmas(
					IntStream.iterate(i, n -> n + DIM + 1)
							.limit(DIM - i % DIM)
							.mapToObj(j -> String.valueOf(xmasMatrix.charAt(j)))
							.collect(Collectors.joining()));

			if (i == 0)
				break;
			xmasCount += countOfXmas(
					IntStream.iterate(DIM * i, m -> m + DIM + 1)
							.limit(DIM - i % DIM)
							.mapToObj(k -> String.valueOf(xmasMatrix.charAt(k)))
							.collect(Collectors.joining()));
		}

		for (i = 0; i < DIM; ++i) { // diagonal l2r count
			xmasCount += countOfXmas(
					IntStream.iterate(i, n -> n + DIM - 1)
							.limit(i + 1)
							.mapToObj(j -> String.valueOf(xmasMatrix.charAt(j)))
							.collect(Collectors.joining()));

			if (i == DIM - 1)
				break;
			xmasCount += countOfXmas(
					IntStream.iterate(MAXLEN - (i * DIM) - 1, m -> m + DIM - 1)
							.limit(i + 1)
							.mapToObj(k -> String.valueOf(xmasMatrix.charAt(k)))
							.collect(Collectors.joining()));
		}

		return xmasCount;
	}

	private int partTwo() {
		// skip some elements at the end of the matrix so that extracting a
		// 'cross-MAS' is still possible without overflow
		for (int i = 0; i < (MAXLEN - 2 * DIM - 3); ++i) {
			int startLeftIdx = i;
			int midIdx = i + DIM + 1;
			int startRightIdx = i + 2;
			// mid index in a 6x6 matrix can be up to the second-last element
			// in a row in xmasMatrix
			if (midIdx % DIM <= DIM - 2) {
				if (xmasMatrix.charAt(midIdx) == 'A') {
					String crossMasL2R = IntStream
							.iterate(startLeftIdx, n -> n + DIM + 1).limit(3)
							.mapToObj(k -> String.valueOf(xmasMatrix.charAt(k)))
							.collect(Collectors.joining());
					String crossMasR2L = IntStream
							.iterate(startRightIdx, m -> m + DIM - 1).limit(3)
							.mapToObj(l -> String.valueOf(xmasMatrix.charAt(l)))
							.collect(Collectors.joining());

					crossMasCount += crossMasL2R.equalsIgnoreCase("MAS")
							|| crossMasL2R.equalsIgnoreCase("SAM")
									? (crossMasR2L.equalsIgnoreCase("MAS")
											|| crossMasR2L.equalsIgnoreCase("SAM")
													? 1
													: 0)
									: 0;
				}
			}
			if ((i + 2) % DIM == DIM - 1) {
				i += 2;
			}
		}
		return crossMasCount;
	}

	private int countOfXmas(String line) {
		int count = 0;
		int len = line.length();

		for (int i = 0; i < len - 3; ++i) {
			String xmasSub = line.substring(i, i + 4);
			if (xmasSub.equalsIgnoreCase(XMAS))
				count += 1;
			else if (xmasSub.equalsIgnoreCase(SAMX))
				count += 1;
		}

		return count;
	}
}
