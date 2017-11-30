import acm.graphics.*;
import acm.program.GraphicsProgram;
import java.awt.event.*;

import java.util.Random;

import javax.swing.*;

public class Snake extends GraphicsProgram{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int m_fieldSizeX = 50;
	int m_fieldSizeY = 20;
	int m_sizePlayer;
	GameObject[] m_next_food;
	GameObject m_player[];
	int m_player_array_start;
	int m_player_array_end;
	int m_tileSize = 10;
	int m_seed = 0;
	Direction m_direction;
	Random m_randomGen;
	boolean m_bodyCountsAsReached;
	int m_next_food_cnt = 1;
	double m_fps = 10;
	GameObject m_field;
	
	public void generateNextFood()
	{
		int newX = m_randomGen.nextInt(m_fieldSizeX) * m_tileSize;
		int newY = m_randomGen.nextInt(m_fieldSizeY) * m_tileSize;
		m_next_food[0] = new Food(newX, newY, m_tileSize, m_tileSize);
		add(m_next_food[0]);
	}
	
	public void init() 
	{
		addKeyListeners();
		addMouseListeners();
		setTitle("Snake");
		m_randomGen = new Random();
		
		setSize(m_fieldSizeY * m_tileSize, m_fieldSizeY * m_tileSize);
		pause(1);
		m_next_food = new GameObject[10];
		
		initGame();
	}
	public void initGame() 
	{
		m_field = new GameObject(m_fieldSizeX * m_tileSize, m_fieldSizeY * m_tileSize);
		add(m_field);
		m_sizePlayer = 3;
		m_fps = 10;
		m_player = new PlayerRect[m_fieldSizeX * m_fieldSizeY];
		m_player_array_start = 0;
		m_player_array_end = m_sizePlayer - 1;
		//m_seed = getDialog().readInt();
		m_randomGen.setSeed(m_seed);
		generateNextFood();
		for(int i = 0; i < m_sizePlayer; i++) {
			m_player[i] = new PlayerRect((5+i) * m_tileSize, 5 * m_tileSize, m_tileSize, m_tileSize);
			add(m_player[i]);
		}
		add(m_next_food[0]);
		m_direction = Direction.EAST;
	}
	
	public void resetGame()
	{
		removeAll();
		initGame();
	}
	
	public int getNextArrayPos()
	{
		return ((m_player_array_end + 1) % m_player.length);
	}
	
	public GPoint getPositionFromDirection(GObject p_currentObj, Direction p_direction) 
	{
		int newX;
		int newY;
		
		switch(m_direction)
		{
		case NORTH:
			newX = (int) p_currentObj.getX();
			newY = (int) p_currentObj.getY() + m_tileSize;
			break;
		case EAST:
			newX = (int) p_currentObj.getX() + m_tileSize;
			newY = (int) p_currentObj.getY();
			break;
		case SOUTH:
			newX = (int) p_currentObj.getX();
			newY = (int) p_currentObj.getY() - m_tileSize;
			break;	
		case WEST:
			newX = (int) p_currentObj.getX() - m_tileSize;
			newY = (int) p_currentObj.getY();
			break;
			default:
				newX = 0;
				newY = 0;
		}
		return new GPoint(newX,newY);
	}
	
	public void updatePlayer(int p_foodReached)
	{
		GRect current_rect = (GRect) m_player[m_player_array_end];
		GPoint new_pos = getPositionFromDirection(current_rect, m_direction);
		
		if(p_foodReached == -1) {
			remove(m_player[m_player_array_start]);
			m_player_array_start = (m_player_array_start + 1) % m_player.length;
		}
		
		m_player[getNextArrayPos()] = new PlayerRect(new_pos.getX(), new_pos.getY(),m_tileSize,m_tileSize);
		add(m_player[getNextArrayPos()]);
		
		m_player_array_end = (m_player_array_end + 1) % m_player.length;
	}
	
	
	public void update() 
	{
	}
	
	public void mousePressed(MouseEvent e) 
	{
		GObject elem = getElementAt(e.getX(), e.getY());
		if(elem == null || elem == m_field)
		{
			System.out.println("Nothing here");
		}
		else 
		{
			System.out.println("Found Someting at: " + e.getX() + " , " + e.getY());
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyChar()) 
		{
		case 's':
			if(m_direction != Direction.SOUTH) 
			{
				m_direction = Direction.NORTH;
			}
			break;
		case 'd':
			if(m_direction != Direction.WEST) 
			{
				m_direction = Direction.EAST;
			}
			break;
		case 'w':
			if(m_direction != Direction.NORTH) 
			{
				m_direction = Direction.SOUTH;
			}
			break;
		case 'a':
			if(m_direction != Direction.EAST) 
			{
				m_direction = Direction.WEST;
			}
			break;
		}
	}
	public boolean checkForSelfCollision()
	{
		GPoint p = getPositionFromDirection(m_player[m_player_array_end],m_direction);
		GameObject gO = (GameObject) getElementAt(p);
		if(gO == null || gO.m_element_type == GameObjectType.PLAYER ) 
		{
			return true;
		}
		return false;
	}
	public boolean checkOutOfBounds(){
		return false;
	}
	
	public int checkFoodReached()
	{
		int result = -1;
		if(m_bodyCountsAsReached) 
		{
			
		}
		
		for(int i = 0; i < m_next_food_cnt; i++) {
			if(m_player[m_player_array_end].getLocation().equals(m_next_food[i].getLocation()))
			{
				result = i;
			}
		}
		return result;
	}
	public int checkCollision()
	{
		boolean outOfBounds = checkOutOfBounds();
		int foodReached = checkFoodReached();
		boolean selfCollision = checkForSelfCollision();
		if(outOfBounds || selfCollision) {
			getDialog().showErrorMessage("YOU LOST");
			resetGame();
			return -1;
		}
		
		if(foodReached != -1) 
		{
			System.out.println(foodReached);
			m_next_food_cnt = 1;
			remove(m_next_food[foodReached]);
			generateNextFood();
		}
		
		return foodReached;
	}
	public void run() 
	{
		boolean programmIsRunning = true;
		double nextFrame = 1000.0 / m_fps;
		double lastFrame = System.currentTimeMillis();
		double loop_end;
		int foodReached = -1;
		while(programmIsRunning) {
			
			if(nextFrame < 0) {			
				foodReached = checkCollision();
			updatePlayer(foodReached);
			if(foodReached != -1) 
			{
				m_fps+=0.75;
			}
				nextFrame = 1000.0 / m_fps;
			}
			
			loop_end = System.currentTimeMillis();
			nextFrame -= loop_end - lastFrame;
			lastFrame = loop_end;
		}
	}

}
