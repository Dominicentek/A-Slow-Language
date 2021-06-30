import java.io.*;
import java.util.*;

public class ASlowLanguageInterpreter {
	public static void main(String[] args) throws Exception {
		boolean doDelay = !args[0].equals("--nodelay");
		String programText = readText(new File(combine(args, " ")));
		String[] splittedText = programText.split("\n");
		char[][] characters = new char[splittedText.length][0];
		for (int i = 0; i < splittedText.length; i++) {
			char[] row = new char[splittedText[i].length()];
			for (int j = 0; j < splittedText[i].length(); j++) {
				row[j] = splittedText[i].charAt(j);
			}
			characters[i] = row;
		}
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		for (int i = 0; i < characters.length; i++) {
			lengths.add(characters[i].length);
		}
		Collections.sort(lengths);
		int width = lengths.get(lengths.size() - 1);
		int height = characters.length;
		for (int i = 0; i < characters.length; i++) {
			if (characters[i].length < width) {
				char[] orig = characters[i];
				characters[i] = new char[width];
				for (int j = 0; j < orig.length; j++) {
					characters[i][j] = orig[j];
				}
				for (int j = orig.length; j < width; j++) {
					characters[i][j] = ' ';
				}
			}
		}
		int x = 0;
		int y = 0;
		int dir = 3; // 0 - Up, 1 - Down, 2 - Left, 3 - Right
		ArrayList<Integer> stack = new ArrayList<Integer>();
		Integer[][] values = new Integer[height][width];
		boolean stackPushingMode = false;
		while (x >= 0 && y >= 0 && x < width && y < height) {
			int delay = (int)Math.floor(Math.random() * 3) + 1;
			if (doDelay) Thread.sleep(delay * 1000);
			char character = characters[y][x];
			// System.out.println("EXECUTE: " + x + " " + y + " " + character);
			if (stackPushingMode) {
				if (character == '"') stackPushingMode = false;
				else stack.add((int)character);
				if (dir == 0) y--;
				if (dir == 1) y++;
				if (dir == 2) x--;
				if (dir == 3) x++;
				continue;
			}
			if (character == 'r') dir = 3;
			if (character == 'l') dir = 2;
			if (character == 'u') dir = 0;
			if (character == 'd') dir = 1;
			if (character == '=') {
				if (stack.size() >= 2 && stack.get(stack.size() - 1) == stack.get(stack.size() - 2)) dir = 3;
				else dir = 2;
				if (stack.size() >= 2) stack.remove(stack.size() - 1);
				if (stack.size() >= 1) stack.remove(stack.size() - 1);
			}
			if (character == '|') {
				if (stack.size() >= 2 && stack.get(stack.size() - 1) == stack.get(stack.size() - 2)) dir = 0;
				else dir = 1;
				if (stack.size() >= 2) stack.remove(stack.size() - 1);
				if (stack.size() >= 1) stack.remove(stack.size() - 1);
			}
			if (character == '_') {
				if (stack.size() == 0) dir = 2;
				else dir = 3;
			}
			if (character == '!') {
				if (stack.size() == 0) dir = 1;
				else dir = 0;
			}
			if (character == '?') {
				dir = (int)Math.floor(Math.random() * 4);
			}
			if (character == '"') stackPushingMode = true;
			if (character == '0') stack.add(0);
			if (character == '1') stack.add(1);
			if (character == '2') stack.add(2);
			if (character == '3') stack.add(3);
			if (character == '4') stack.add(4);
			if (character == '5') stack.add(5);
			if (character == '6') stack.add(6);
			if (character == '7') stack.add(7);
			if (character == '8') stack.add(8);
			if (character == '9') stack.add(9);
			if (character == 'A') stack.add(10);
			if (character == 'B') stack.add(11);
			if (character == 'C') stack.add(12);
			if (character == 'D') stack.add(13);
			if (character == 'E') stack.add(14);
			if (character == 'F') stack.add(15);
			if (character == 'v' && stack.size() != 0) stack.remove(stack.size() - 1);
			if (character == '^' && stack.size() != 0) stack.add(stack.get(stack.size() - 1));
			if (character == '~') Collections.reverse(stack);
			if (character == 'O' && stack.size() != 0) {
				System.out.print((char)((int)stack.get(stack.size() - 1)));
				stack.remove(stack.size() - 1);
			}
			if (character == 'o' && stack.size() != 0) {
				System.out.print(stack.get(stack.size() - 1));
				stack.remove(stack.size() - 1);
			}
			if (character == 'I') {
				String input = new Scanner(System.in).nextLine();
				for (int i = 0; i < input.length(); i++) {
					stack.add((int)input.charAt(i));
				}
			}
			if (character == 'i') {
				String input = new Scanner(System.in).nextLine();
				int value = 0;
				try {
					value = Integer.parseInt(input);
				}
				catch (NumberFormatException e) {}
				stack.add(value);
			}
			if (character == '+' && stack.size() >= 2) {
				int result = stack.get(stack.size() - 2) + stack.get(stack.size() - 1);
				stack.remove(stack.size() - 1);
				stack.remove(stack.size() - 1);
				stack.add(result);
			}
			if (character == '-' && stack.size() >= 2) {
				int result = stack.get(stack.size() - 2) - stack.get(stack.size() - 1);
				stack.remove(stack.size() - 1);
				stack.remove(stack.size() - 1);
				stack.add(result);
			}
			if (character == '*' && stack.size() >= 2) {
				int result = stack.get(stack.size() - 2) * stack.get(stack.size() - 1);
				stack.remove(stack.size() - 1);
				stack.remove(stack.size() - 1);
				stack.add(result);
			}
			if (character == '/' && stack.size() >= 2) {
				int result = stack.get(stack.size() - 2) / stack.get(stack.size() - 1);
				stack.remove(stack.size() - 1);
				stack.remove(stack.size() - 1);
				stack.add(result);
			}
			if (character == '%' && stack.size() >= 2) {
				int result = stack.get(stack.size() - 2) % stack.get(stack.size() - 1);
				stack.remove(stack.size() - 1);
				stack.remove(stack.size() - 1);
				stack.add(result);
			}
			if (character == 'x') System.exit(0);
			if (character == 's') System.out.println(Arrays.toString(stack.toArray()));
			if (dir == 0) y--;
			if (dir == 1) y++;
			if (dir == 2) x--;
			if (dir == 3) x++;
		}
	}
	public static String combine(String[] array, String separator) {
		String str = "";
		for (int i = 0; i < array.length; i++) {
			if (array[0].equals("--nodelay") && i == 0) continue;
			str += array[i];
			if (i + 1 < array.length) str += separator;
		}
		return str;
	}
	public static String readText(File file) throws Exception {
		String str = "";
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			str += "\n" + line;
		}
		scan.close();
		return str.replaceFirst("\n", "");
	}
}