import java.util.*;

public class ProblemF
{
	public static void main(String[] args)
	{
		Scanner s = new Scanner(System.in);

		// Read our first line of input
		int r = s.nextInt(), c = s.nextInt();
		s.nextLine();

		// Build our virtual keyboard
		char[][] grid = new char[r][c];

		for (int i = 0; i < r; i++) {
			char[] line = s.nextLine().toCharArray();
			for (int j = 0; j < c; j++) grid[i][j] = line[j];
		}

		// Build our transition table since we skip over adjacent characters who are the same as us
		TransitionSet[][] transitionTable = new TransitionSet[r][c];
		for (int i = 0; i < r; i++)
			for (int j = 0; j < c; j++)
				transitionTable[i][j] = calculateTransitionSet(grid, j, i);

		// Grab our last line of input, append '*' character as its the ENTER key
		char[] targetWord = (s.nextLine() + "*").toCharArray();

		// Setup our datastructures
		ArrayDeque<State> queue = new ArrayDeque<>();
		HashSet<String> hs = new HashSet<>();

		// Queue our initial state (top-left of virtual keyboard)
		queue.add(new State(0, 0, 0, 0));

		// BFS search of neighbors
		while (!queue.isEmpty()) {
			State state = queue.pop();

			// If we've already searched this state, lets ignore it
			if (hs.contains(state.toString())) continue;
			else hs.add(state.toString());

			// If we hit our next character, lets select it and update our state
			if (grid[state.j][state.i] == targetWord[state.n]) {
				state.n++;
				state.l++;
			}

			// If we've finished our word, we're finished
			if (state.n == targetWord.length) {
				System.out.println(state.l);
				break;
			}

			// Queue ourself again incase double occurrence of character
			queue.add(state);

			// Grab the states neighbors and queue them
			TransitionSet ts = transitionTable[state.j][state.i];

			if (ts.up != null) queue.add(getNextState(state, ts.up));
			if (ts.down != null) queue.add(getNextState(state, ts.down));
			if (ts.left != null) queue.add(getNextState(state, ts.left));
			if (ts.right != null) queue.add(getNextState(state, ts.right));
		}
	}

	// Helper functions
	public static State getNextState(State state, Transition t)
	{
		return new State(t.x, t.y, state.n, state.l + 1);
	}

	public static TransitionSet calculateTransitionSet(char[][] grid, int x, int y)
	{
		TransitionSet ts = new TransitionSet();

		ts.up = getTransition(grid, x, y, Direction.up);
		ts.down = getTransition(grid, x, y, Direction.down);
		ts.left = getTransition(grid, x, y, Direction.left);
		ts.right = getTransition(grid, x, y, Direction.right);

		return ts;
	}

	public static Transition getTransition(char[][] grid, int x, int y, Direction d)
	{
		Transition t = new Transition();

		Vector cursor = new Vector(x, y);
		Vector offset = null;

		switch(d)
		{
			case up: 	offset = new Vector(0, -1);	break;
			case down: 	offset = new Vector(0, 1);	break;
			case left: 	offset = new Vector(-1, 0);	break;
			case right:	offset = new Vector(1, 0);	break;
		}

		while(true)
		{
			cursor.add(offset);

			if (cursor.x < 0 || cursor.x >= grid[0].length)
				return null;

			if (cursor.y < 0 || cursor.y >= grid.length)
				return null;

			if (grid[cursor.y][cursor.x] != grid[y][x]) {

				t.x = cursor.x;
				t.y = cursor.y;
				return t;
			}
		}
	}

	// Helper objects
	enum Direction { up, down, left, right }

	public static class Transition { public int x, y; }

	public static class TransitionSet { public Transition up, down, left, right; }

	public static class State
	{
		public int i, j, n, l;
		public State(int i, int j, int n, int l) { this.i = i; this.j = j; this.n = n; this.l = l; }
		public String toString() { return this.i + "," + this.j + "," + this.n; }
	}

	public static class Vector
	{
		public int x, y;
		public Vector(int x, int y) { this.x = x; this.y = y; }
		public void add(Vector v2) { this.x += v2.x; this.y += v2.y; }
	}
}