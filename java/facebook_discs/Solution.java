import java.io.*;
import java.util.*;

public class Solution {
    
    public static void main(String args[]) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] line = br.readLine().split(" ");
        int N = Integer.parseInt(line[0]);
        int K = Integer.parseInt(line[1]);
        
        Peg[] initialSetup = new Peg[K];
        Peg[] finalSetup = new Peg[K];
        
        for(int i = 0; i < K; i++) {
        	initialSetup[i] = new Peg();
        	finalSetup[i] = new Peg();
        }
	        
	
        line = br.readLine().split(" ");
        for(int i = line.length-1; i > -1; i--) {
        	initialSetup[Integer.parseInt(line[i])-1].placeDisc(i);
        }
	        
        line = br.readLine().split(" ");
        for(int i = line.length-1; i > -1; i--) {
        	finalSetup[Integer.parseInt(line[i])-1].placeDisc(i);
        }
	        
        PegSolver solver = new PegSolver(N, K, initialSetup, finalSetup);
	        
        for(String output : solver.solution()) {
        	System.out.println(output);
        }
	         
    }
}

class Board implements DeepCloneable{
	
	private Peg[] pegs;
	public DeepCloneableList<Move> moveHistory;
	
	public Board(Peg[] pegs) {
		this.pegs = pegs;
		moveHistory = new DeepCloneableList<Move>();
	}
	
	public Board applyMove(Move move) throws InvalidOperation{
		pegs[move.to].placeDisc(pegs[move.from].takeDisc());
		moveHistory.add(move);
		return this;
	}
	
	public DeepCloneableList<Move> possibleMoves() {
		DeepCloneableList<Move> moves = new DeepCloneableList<Move>();
		for (int i = 0; i < pegs.length; i++) {
			if(pegs[i].top() != null) {
				for(int j = 0; j < pegs.length; j++) {
					if(i != j && (pegs[j].top() == null || pegs[i].top() < pegs[j].top())) {
						Move nextMove = new Move(i,j);
						if(moveHistory.isEmpty() || !nextMove.equals(moveHistory.peekLast())) {
							moves.add(new Move(i,j));
						}
					}
				}
			}
		}
		return moves;
	}
	
	public boolean equals(Board otherBoard) {
		return this.toString().equals(otherBoard.toString());
	}
	
	public Board deepClone() {
		Peg[] clonedPegs = new Peg[this.pegs.length];
		for (int i = 0; i < this.pegs.length; i++) {
			clonedPegs[i] = this.pegs[i].deepClone();
		}
		Board clonedBoard = new Board(clonedPegs);
		clonedBoard.moveHistory = this.moveHistory.deepClone();
		return clonedBoard;
	}
	
	public String toString() {
		String output = "[ ";
		for(Peg peg : pegs) {
			output += peg.toString() + " , ";
		}
		output = output.substring(0, output.length() - 3);
		output += "]";
		return output;
	}
	
}

class Peg implements DeepCloneable{
    
    private LinkedList<Integer> discs;
    
    public Peg() {
        discs = new DeepCloneableList<Integer>();
    }
    
    public Integer top() {
    	return discs.peekFirst();
    }
    
    public Integer takeDisc() {
    	return discs.pop();
    }
    
    public void placeDisc(Integer disc) {
    	discs.push(disc);
    }
    
    public boolean equals(Peg otherPeg) {
    	return this.discs.equals(otherPeg.discs);
    }
    
    public Peg deepClone() {
    	Peg clonedPeg = new Peg();
    	for(Integer disc : this.discs) {
    		clonedPeg.discs.add(disc);
    	}
    	return clonedPeg;
    }
    
    public String toString() {
    	String output = "";
    	for (Integer disc : this.discs) {
    		output += disc + " ";
    	}
    	return output;
    }
 
}

class PegSolver {
    
    private int N;
    private int K;
        
    private Board end;
    
    private Queue<Board> queuedBoards;
    
    public PegSolver(int N, int K, Peg[] start, Peg[] end) {
        this.N = N;
        this.K = K;
        queuedBoards = new DeepCloneableList<Board>();
        queuedBoards.add(new Board(start));
        this.end = new Board(end);
    }
    
    public String[] solution() throws InvalidOperation{    	
        Board solved = this.solve();
        
    	String[] outputSolution = new String[solved.moveHistory.size()+1];
    	
    	outputSolution[0] = Integer.toString(solved.moveHistory.size());
    	for(int i = 1; i < outputSolution.length; i++) {
    		outputSolution[i] = solved.moveHistory.pollFirst().toString();
    	}
    	
        return outputSolution;
    }
    
    private Board solve() throws InvalidOperation{
    	while (queuedBoards.peek() != null) {
    		Board nextBoard = queuedBoards.poll();
    		for(Move move : nextBoard.possibleMoves()) {
    			Board possibleBoard = nextBoard.deepClone().applyMove(move);
        		if (end.equals(possibleBoard)) {
        			return possibleBoard;
        		}
    			queuedBoards.add(possibleBoard);
    		}
    	}
    	return null;
    }
}

class Move implements DeepCloneable{
	
	public int from;
	public int to;
	
	public Move(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	public boolean equals(Move otherMove) {
		return from == otherMove.from && to == otherMove.to;
	}
	
	public Move deepClone() {
		return new Move(this.from, this.to);
	}
	
	public String toString() {
		return (from+1) + " " + (to+1);
	}
}

class DeepCloneableList<T> extends LinkedList<T> implements DeepCloneable, Queue<T>{
	
	public DeepCloneableList<T> deepClone() {
		DeepCloneableList<T> deepClonedList = new DeepCloneableList<T>();
		for (T elem : this) {
			deepClonedList.add((T)((DeepCloneable)elem).deepClone());
		}
		return deepClonedList;
	}
}

interface DeepCloneable {
	public DeepCloneable deepClone();
}

class InvalidOperation extends Exception {
	protected InvalidOperation() {
		super();
	}
		
	protected InvalidOperation(String s) {
		super(s);
	}
}	