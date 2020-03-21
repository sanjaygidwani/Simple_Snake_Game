import java.util.*;
import java.lang.*;
import java.io.IOException;

class SnakeGame 
{	
	public static final int width = 30, height = 20; // game boundaries
	public static int x, y, fruitX, fruitY, score; // x-axis and y-axis, fruit and score
	public static boolean gameOver; // gameover flag
	
	public static final int STOP = 0, LEFT = 1, RIGHT = 2, UP = 3, DOWN = 4; // snake movement keys
	public static int dir; 
	
	public static int tailX[] = new int[100]; // x-axis and y-axis tail array
	public static int tailY[] = new int[100];
	public static int nTail; // tail length
	
	public static void main(String argv[]) throws Exception // main method, sleep function exception
	{
		Setup();
		while(!gameOver)
		{
			Thread.sleep(10);
			Draw();
			Input();
			Logic();
		}
		System.out.print("Game Over!");
	}	
   
	public static void Setup() // initial game structure
	{
		gameOver = false;
		dir = STOP;
		x = width / 2;
		y = height / 2;
		fruitX = (int)Math.ceil(Math.random() * 100) % width; // randomly placing fruit
		fruitY = (int)Math.ceil(Math.random() * 100) % height;
		score = 0;
	}
	
	public static void clrscr() // clear screen method
	{
        	try 
		{
            		if (System.getProperty("os.name").contains("Windows"))
               			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            		else
               			Runtime.getRuntime().exec("clear");
         	} 
		catch (IOException | InterruptedException ex) {}
        }
	
	public static void Draw() // drawing boundaries, showing snake, tail and fruit
	{
        	clrscr();	
		for(int i = 0; i < width+2; i++) // upper horizontal boundary
			System.out.print("#");
		System.out.print("\n");
		
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				if(j == 0)
					System.out.print("#"); // left vertical boundary
				
				if(i == y && j == x) // snake's head
					System.out.print("O");
				else if(i == fruitY && j == fruitX) // fruit's position
					System.out.print("F");
				else
				{
					boolean print = false;
					for(int k = 0; k < nTail; k++)
					{
						if(tailX[k] == j && tailY[k] == i) // snake's tail
						{
							System.out.print("o"); 
							print = true;
						}
					}
					if(!print)
						System.out.print(" "); 
				}
				
				if(j == width-1)
					System.out.print("#"); // right vertical boundary
			}
			System.out.print("\n");
		}
		
		for(int i = 0; i < width+2; i++) // lower horizontal boundary
			System.out.print("#");
		System.out.print("\n");
		
		System.out.print("SCORE: "+score+"\n"); // showing updated score
	}    
	
	public static void Input() throws IOException // input from keyboard
	{
		switch((char)System.in.read())
		{
			case 'a': dir = LEFT;
			          break;
			case 'd': dir = RIGHT;
			          break;
			case 'w': dir = UP;
			          break;
			case 's': dir = DOWN;
			          break;
			case 'x': gameOver = true;
			          break;
		        default: break;
		}
	}
	
	public static void Logic()
	{
		int prevX = tailX[0]; // logic for tail increment, follows snake's head
		int prevY = tailY[0];
		int prev2X, prev2Y;
		tailX[0] = x;
		tailY[0] = y;
		for(int i = 1; i < nTail; i++)
		{
			prev2X = tailX[i];
			prev2Y = tailY[i];
			tailX[i] = prevX;
			tailY[i] = prevY;
			prevX = prev2X;
			prevY = prev2Y;
		}
		
		switch(dir)
		{
			case 1: x--;
			           break;
			case 2: x++;
			           break;
			case 3: y--;
			           break;
			case 4: y++;
			           break;
			default: break;
		}
		
	     /* if(x > width || x < 0 || y > height || y < 0) // snake touches wall, gameover 
			gameOver = true; */
	    
		if(x >= width) x = 0; else if(x < 0) x = width-1; // snake touches wall, appears from another side
		if(y >= height) y = 0; else if(y < 0) y = height-1;
		
		for(int i = 0; i < nTail; i++) // snake's head touches tail, gameover
		{
			if(tailX[i] == x && tailY[i] == y)
				gameOver = true;
		}
		
		if(x == fruitX && y == fruitY)
		{
			score += 10;
			fruitX = (int)Math.ceil(Math.random() * 100) % width; // randomly placing fruit again after eating
		        fruitY = (int)Math.ceil(Math.random() * 100) % height;
			nTail ++; // increasing tail size by 1
		}
	}
}
