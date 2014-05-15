package com.ericliu.billshare.util;

public class ArrayToString {

	public static String arrayToString(int[] array) {
		String result = "[";
		for (int i = 0; i < array.length; i++) {
			result = result + " " + String.valueOf(array[i]);
		}

		result = result + "]";

		return result;

	}

	public static String arrayToString(int[][] array) {
		String result = "[";
		for (int i = 0; i < array.length; i++) {

			for (int j = 0; j < array[i].length; j++) {

				result = result + " " + String.valueOf(array[i][j]);
			}
		}

		result = result + "]";

		return result;

	}

	public static String arrayToString(double[] array) {
		String result = "[";
		for (int i = 0; i < array.length; i++) {
			result = result + " " + String.valueOf(array[i]);
		}

		result = result + "]";

		return result;

	}

	public static String arrayToString(double[][] array) {
		String result = "[";
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {

				result = result + " " + String.valueOf(array[i][j]);
			}
		}

		result = result + "]";

		return result;

	}
	
	public static String arrayToString(String[] array) {
		String result = "[";
		for (int i = 0; i < array.length; i++) {
			result = result + " " + array[i];
		}

		result = result + "]";

		return result;

	}
	
	
	public static String arrayToString(String[][] array) {
		String result = "[";
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {

				result = result + " " + array[i][j];
			}
		}

		result = result + "]";

		return result;

	}

}
