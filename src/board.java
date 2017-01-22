import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Ishan Khatri
//Connect 4
public class board
{
	//Board 2D Array
	private int[][]board=new int[6][7];
	//Constant for the number in a row needed to win
	private static final int WIN=4;
	
	/**
	 * Printing Method
	 * @throws IOException
	 */
	public void print() throws IOException
	{
		for(int r=0; r<board.length; r++)
		{
			for(int c=0; c<board[0].length; c++)
			{
				System.out.print(board[r][c]+"\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * Integer Input Method
	 * @param player - the number of the player whose turn it is, used in the prompt
	 * @return num1 - the input integer
	 * @throws IOException
	 */
	public int intTrap(int player) throws IOException
	{
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader input = new BufferedReader(reader);
		int num1=0;
		do
		{
			System.out.println("Player " + player + " please enter the column in which you would like to add your piece");
			String temp=input.readLine();
			//Error trapping for empty strings & non-integer characters
			if(!temp.isEmpty() && Character.isDigit((temp.charAt(0))))
				num1 = Integer.parseInt(temp);
		}
		while(num1 < 1 || num1 > 7);
		return num1;
	}
	
	/**
	 * Method to drop a token into a specified column
	 * @param input - the user's input for which column they would like to drop their token into
	 * @param player - used to determine which token to insert
	 * @throws IOException 
	 */
	public void add(int input, int player) throws IOException
	{
		int r=0;
		//Check if column is already full
		if(board[r][input-1]!=0)
		{
			System.out.println("This column is full, please choose another");
			add(intTrap(player), player);
			return;
		}
		//Gravity - makes game pieces "fall" down
		while(board[r][input-1]==0 && r<board.length-1)
		{
			r++;
		}
		//Check if the bottom most row is filled
		if(board[r][input-1]!=0)
		{
			//Different tokens for player 1 and player 2
			if(player==1)
				board[r-1][input-1]=1;
			else
				board[r-1][input-1]=2;
		}
		else
		{
			//Different tokens for player 1 and player 2
			if(player==1)
				board[r][input-1]=1;
			else
				board[r][input-1]=2;
		}
	}
	
	/**
	 * Horizontal check for win
	 * @param player
	 * @return true if player wins, false otherwise
	 */
	public boolean horizontalCheck(int player)
	{
		//Horizontal Check
		for(int r=0; r<=board.length-1; r++)
		{
			//Counter to check how many in a row
			int count=0;
			for(int c=0; c<=board[0].length-1; c++)
			{
				if(board[r][c]==player)
				{
					count++;
					if(count>=WIN)
					{
						return true;
					}
				}
				else
					count=0;
			}
			if(count>=WIN)
			{
				System.out.println(count);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Vertical Check for Win
	 * @param player
	 * @return true if player wins, false otherwise
	 */
	public boolean verticalCheck(int player)
	{
		//Vertical Check
		for(int c=0; c<=board[0].length-1; c++)
		{
			//Counter to check how many in a row
			int count=0;
			for(int r=0; r<=board.length-1; r++)
			{
				if(board[r][c]==player)
					count++;
				else
					count=0;
			}
			if(count>=WIN)
				return true;
		}
		return false;
	}
	
	/**
	 * Checks down and to the right.
	 * @param player - player to check
	 * @param row - starting row
	 * @param col - starting column
	 * @return The number of matching tokens on the diagonal 
	 */
	public int diagonalCheck1(int player, int row, int col)
	{
		if(row<0 || col<0 || col>board[0].length-1 || row>board.length-1 ||board[row][col]!=player)
			return 0;
		return 1+diagonalCheck1(player, row+1, col+1);
	}
	
	/**
	 * Checks up and to the left.
	 * @param player - player to check
	 * @param row - starting row
	 * @param col - starting column
	 * @return The number of matching tokens on the diagonal 
	 */
	public int diagonalCheck2(int player, int row, int col)
	{
		if(row<0 || col<0 || col>board[0].length-1 || row>board.length-1 ||board[row][col]!=player)
			return 0;
		return 1+diagonalCheck2(player, row-1, col-1);
	}
	
	/**
	 * Checks up and to the right.
	 * @param player - player to check
	 * @param row - starting row
	 * @param col - starting column
	 * @return The number of matching tokens on the diagonal 
	 */
	public int diagonalCheck3(int player, int row, int col)
	{
		if(row<0 || col<0 || col>board[0].length-1 || row>board.length-1 ||board[row][col]!=player)
			return 0;
		return 1+diagonalCheck3(player, row-1, col+1);
	}
	
	/**
	 * Checks down and to the left.
	 * @param player - player to check
	 * @param row - starting row
	 * @param col - starting column
	 * @return The number of matching tokens on the diagonal 
	 */
	public int diagonalCheck4(int player, int row, int col)
	{
		if(row<0 || col<0 || col>board[0].length-1 || row>board.length-1 ||board[row][col]!=player)
			return 0;
		return 1+diagonalCheck2(player, row+1, col-1);
	}
	
	/**
	 * @param player - which player is being checked
	 * @return true if the player wins, false if not
	 */
	public boolean winner(int player)
	{		
		if(this.horizontalCheck(player) || this.verticalCheck(player))
		{
			System.out.println("Player " + player + " wins!");
			return true;
		}
		for(int c=0; c<board[0].length; c++)
		{
			for(int r=0; r<board.length; r++)
			{
				int count=diagonalCheck1(player, r,c);
				count=count+diagonalCheck2(player, r,c);
				int count2=diagonalCheck3(player, r,c);
				count2=count2+diagonalCheck4(player, r,c);
				if(count>=5 || count2>=5)
				{
					System.out.println("Player " + player + " wins!");
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean tie() throws IOException
	{
		for (int c=0; c<board[0].length; c++)
		{
			if(board[0][c]==0)
			{
				return false;
			}
		}
		System.out.println("The game is a tie");
		return true;
	}
	
	/**
	 * UI Method to increment the player turns, and add tokens to the board as long as there is no winner
	 * @throws IOException
	 */
	public void ui() throws IOException
	{
		int player;
		int x=3;
		do
		{
			if(x%2==1)
				player=1;
			else
				player=2;
			
			add(intTrap(player),player);
			print();
			x++;
		}while(!(winner(player)) && !tie());
	}
	
	/**
	 * Main
	 * @param args
	 * @throws IOException
	 */
	public static void main (String[]args) throws IOException
	{
		board square = new board();
		square.print();
		square.ui();
	}
}
