// The "Fullscreen" class.
/*
12/January/2009.
Written By: Joseph Richard.
This program is a fun game that I've programed.
*/
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
public abstract class Labyrinth_Game implements KeyListener
{



    //graphics globals
    static GraphicsEnvironment myGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment ();
    static GraphicsDevice myGraphicsDevice = myGraphicsEnvironment.getDefaultScreenDevice ();
    static Frame drawingBoard = new Frame ();
    static Graphics frontBuffer = null;
    static int defaultGraphicsWidth = 0;
    static int defaultGraphicsHeight = 0;
    static int graphicsWidth = 0;
    static int graphicsHeight = 0;
    static Image vBackBuffer;
    static Graphics backBuffer = null;
    static BufferStrategy myBufferStrategy = drawingBoard.getBufferStrategy ();
    //Mouse input Globals
    static byte mouseHitCounter[] = new byte [5];

    static MouseListener MyMouseListener = new MouseAdapter ()  //Mouse input
    {
	public void mouseClicked (MouseEvent mouseEvent)
	{
	    mouseHitCounter [mouseEvent.getButton ()]++;
	}
	public void mousePressed (MouseEvent mouseEvent)
	{

	}
	public void mouseReleased (MouseEvent mouseEvent)
	{

	}
	public void mouseEntered (MouseEvent mouseEvent)
	{

	}
	public void mouseExited (MouseEvent mouseEvent)
	{

	}

    }


    ;
    //key input globals
    static byte keyHitCounter[] = new byte [256];
    static boolean isKeyDown[] = new boolean [256];
    //key constants
    static final short key_UpArrow = 38;
    static final short key_LeftArrow = 37;
    static final short key_RightArrow = 39;
    static final short key_DownArrow = 40;
    static final short key_Escape = 27;
    static final short key_Enter = 10;
    static final short key_0 = 48;
    static final short key_1 = 49;
    static final short key_2 = 50;
    static final short key_3 = 51;
    static final short key_4 = 52;
    static final short key_5 = 53;
    static final short key_6 = 54;
    static final short key_7 = 55;
    static final short key_8 = 56;
    static final short key_9 = 57;


    static KeyListener MyKeyListener = new KeyAdapter ()  //Creates the key listener and adapter
    {
	public void keyTyped (KeyEvent keyEvent)
	{

	}
	public void keyPressed (KeyEvent keyEvent)
	{
	    if (isKeyDown [keyEvent.getKeyCode ()] == false)
	    {
		keyHitCounter [keyEvent.getKeyCode ()]++;
	    }
	    isKeyDown [keyEvent.getKeyCode ()] = true;
	}
	public void keyReleased (KeyEvent keyEvent)
	{
	    isKeyDown [keyEvent.getKeyCode ()] = false;
	}
    }


    ;



    //Level Globals
    static boolean levelData[] [] = new boolean [24] [32];
    //player Globals
    static int playerX = 0; //X coordinates
    static int playerY = 0; //Y coordinates
    static byte playerDir = 0; //Direction
    static int playerHealth = 100; //Health
    static int playerRed = 0; //Colour
    static int playerGreen = 0; //Colour
    static int playerBlue = 0; //Colour
    //Enemy Globals
    static int npcRed = 0;
    static int npcGreen = 0;
    static int npcBlue = 0;
    static byte npcDir = 0;
    static int npcX = 31;
    static int npcY = 23;
    static int npcHealth = 100; //Health
    static int waypointDistance[] [] = new int [24] [32]; //Pathfinding
    static String waypointDirection[] [] = new String [24] [32]; //Pathfinding
    //Magic Globals
    static short magicTime = 0;
    //Game Globals
    static byte speedController = 0;
    static long scoreKeeper = 0;
    // Here are all the Function:
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static byte mouseHit (int buttonHit)  //Returns the number of times mouse was hit since last call to MouseHit()
    {
	byte tempMouseHit = mouseHitCounter [buttonHit];
	mouseHitCounter [buttonHit] = 0;
	return tempMouseHit;
    }


    public static boolean keyDown (int buttonHit)  //Returns true if key down, or false otherwise
    {
	return isKeyDown [buttonHit];
    }


    public static byte keyHit (int buttonHit)  //Returns the number of times mouse was hit since last call to KeyHit()
    {
	byte tempKeyHit = keyHitCounter [buttonHit];
	keyHitCounter [buttonHit] = 0;
	return tempKeyHit;
    }


    public static boolean keyPress (int buttonHit)  //Returns true if key down or hit, or false otherwise
    {
	if (keyDown (buttonHit) || keyHit (buttonHit) > 0)
	{
	    return true;
	}
	return false;

    }


    public static void initialise (int setGraphicsWidth, int setGraphicsHeight)  //Call this at the beginning of main method
    {
	graphicsWidth = setGraphicsWidth;
	graphicsHeight = setGraphicsHeight;
	DisplayMode OriginalDisplayMode = myGraphicsDevice.getDisplayMode ();
	defaultGraphicsWidth = OriginalDisplayMode.getWidth ();
	defaultGraphicsHeight = OriginalDisplayMode.getHeight ();
	DisplayMode ModifiedDisplayMode = new DisplayMode (setGraphicsWidth, setGraphicsHeight, 0, 0);
	//graphics
	drawingBoard.setUndecorated (true);
	drawingBoard.setIgnoreRepaint (true);
	myGraphicsDevice.setFullScreenWindow (drawingBoard);
	myGraphicsDevice.setDisplayMode (ModifiedDisplayMode);
	frontBuffer = myBufferStrategy.getDrawGraphics ();
	vBackBuffer = drawingBoard.createImage (graphicsWidth, graphicsHeight); //Create the back buffer
	backBuffer = vBackBuffer.getGraphics ();
	//mouse
	drawingBoard.addMouseListener (MyMouseListener);
	mouseHitCounter [0] = 0;
	mouseHitCounter [1] = 0;
	mouseHitCounter [2] = 0;
	mouseHitCounter [3] = 0;
	mouseHitCounter [4] = 0;
	//keyboard
	drawingBoard.addKeyListener (MyKeyListener);
    } //End initialise ()





    public static void renderGraphics ()  //draws the back buffer to the front buffer
    {

	frontBuffer.drawImage (vBackBuffer, 0, 0, graphicsWidth, graphicsHeight, null);
    }


    public static void colour (int colourRed, int colourGreen, int colourBlue)  //Change the colour
    {
	if (colourRed > 255) //Keep colours Legal
	{
	    colourRed = 255;
	}
	else if (colourRed < 0)
	{
	    colourRed = 0;
	}
	if (colourGreen > 255)
	{
	    colourGreen = 255;
	}
	else if (colourGreen < 0)
	{
	    colourGreen = 0;
	}
	if (colourBlue > 255)
	{
	    colourBlue = 255;
	}
	else if (colourBlue < 0)
	{
	    colourBlue = 0;
	}
	Color myColour = new Color (colourRed, colourGreen, colourBlue);
	backBuffer.setColor (myColour);
    }


    public static void cls ()  //Change the Colour
    {
	backBuffer.fillRect (0, 0, graphicsWidth, graphicsHeight);
	frontBuffer.fillRect (0, 0, graphicsWidth, graphicsHeight);
    }


    public static void generateLevel ()  //Call this function to generate a level.  It will loop until it creates an acceptable level.
    {
	boolean reGenerateLevel = true;
	double intRegenCount = 0;
	String regenDotState = "...";
	String stringRegenCount = "Generating Level";
	while (reGenerateLevel)
	{

	    stringRegenCount = "Generating Level";
	    intRegenCount++;


	    reGenerateLevel = generatechangingLevel ();
	    if ((intRegenCount / 1000) == ((int) (intRegenCount / 1000)))
	    {

		//control the dots
		if (regenDotState == "...")
		{
		    regenDotState = "  ";
		}
		else if (regenDotState == "  ")
		{
		    regenDotState = ".  ";
		}
		else if (regenDotState == ".  ")
		{
		    regenDotState = ".. ";
		}
		else
		{
		    regenDotState = "...";
		}


		stringRegenCount = stringRegenCount + regenDotState; //Add up the string to display while Loading
		colour (0, 0, 0);
		cls ();
		colour (255, 0, 0);
		backBuffer.drawString (stringRegenCount, 467, 384);
		renderGraphics ();
	    }
	}
    }


    public static boolean generatechangingLevel ()  //assign values to the level array, building paths
    {
	byte changingLevelX = 1;
	byte changingLevelY = 1;
	boolean satisfyStart = false;
	boolean satisfyFinish = false;
	boolean reloop;
	for (byte loop1 = 0 ; loop1 <= 23 ; loop1++) //Set Everything to zero
	{
	    for (byte loop2 = 0 ; loop2 <= 31 ; loop2++)
	    {

		levelData [loop1] [loop2] = true;
	    }
	}





	reloop = true;

	while (reloop == true)
	{
	    levelData [changingLevelY] [changingLevelX] = false;
	    if (changingLevelX == 30 && changingLevelY == 22)
	    {
		reloop = false;
	    }

	    if (changingLevelX == 30)
	    {
		changingLevelY++;
	    }
	    else if (changingLevelY == 22)
	    {
		changingLevelX++;
	    }
	    else if (coinFlip ())
	    {
		if (coinFlip () || changingLevelY == 0)
		{
		    changingLevelY++;
		}
		else
		{
		    changingLevelY--;
		}
	    }
	    else
	    {
		if (coinFlip () || changingLevelX == 0)
		{
		    changingLevelX++;
		}
		else
		{
		    changingLevelX--;
		}
	    }

	}
	for (byte loop = 0 ; loop < 32 ; loop++)
	{
	    levelData [0] [loop] = false;
	    levelData [23] [loop] = false;
	}
	for (byte loop = 0 ; loop < 23 ; loop++)
	{
	    levelData [loop] [0] = false;
	    levelData [loop] [31] = false;
	}
	if (levelData [20] [29] == false ||
		levelData [22] [25] == false ||
		levelData [18] [30] == false ||
		levelData [5] [1] == false ||
		levelData [4] [10] == false ||
		levelData [18] [10] ||
		levelData [10] [26])
	    //Regenerate Level if level sucks
	    {
		return true; //Calls the function again
	    }
	return false; //finishes properly
    }


    public static boolean coinFlip ()
    {
	double rnd = (Math.random ());
	if (rnd > 0.5)
	{
	    return true;
	}
	return false;
    }


    public static void drawLevel ()  // draw the current level
    {
	for (byte loop1 = 0 ; loop1 <= 23 ; loop1++)
	{
	    for (byte loop2 = 0 ; loop2 <= 31 ; loop2++)
	    {
		if (levelData [loop1] [loop2]) //choose colour
		{
		    colour (128, 128, 128);
		}
		else
		{
		    colour (64, 64, 64);
		}
		backBuffer.fillRect (loop2 * 32, loop1 * 32, 32, 32); //draw the rectangle
		if (keyDown (10)) //Draw the Pathfinding information if enter key is being pressed
		{
		    colour (0, 0, 0);
		    backBuffer.drawString (waypointDirection [loop1] [loop2] + waypointDistance [loop1] [loop2], loop2 * 32, (loop1 * 32) + 20);
		}
	    }
	}
    }


    public static void drawCharacter (int characterAngle, int characterX, int characterY, int characterRed, int characterGreen, int characterBlue)   // draw a Character
    {
	int drawCharacterX = (int) (characterX * 32);
	int drawCharacterY = (int) (characterY * 32);
	colour (characterRed, characterGreen, characterBlue);
	backBuffer.fillOval (drawCharacterX, drawCharacterY, 32, 32); //draw the Character
	colour (0, 0, 0);
	backBuffer.fillOval ((drawCharacterX) + 13, (drawCharacterY) + 13, 6, 6); //draw the Character
	switch (characterAngle) //draw the gun
	{
	    case 0:
		backBuffer.drawLine (16 + (drawCharacterX), 16 + (drawCharacterY), 16 + (drawCharacterX), drawCharacterY);
		backBuffer.drawLine (15 + (drawCharacterX), 16 + (drawCharacterY), 15 + (drawCharacterX), drawCharacterY);
		break;
	    case 1:
		backBuffer.drawLine (16 + (drawCharacterX), 16 + (drawCharacterY), 32 + (drawCharacterX), (drawCharacterY) + 16);
		backBuffer.drawLine (16 + (drawCharacterX), 15 + (drawCharacterY), 32 + (drawCharacterX), (drawCharacterY) + 15);
		break;
	    case 2:
		backBuffer.drawLine (16 + (drawCharacterX), 16 + (drawCharacterY), 16 + (drawCharacterX), (drawCharacterY) + 32);
		backBuffer.drawLine (15 + (drawCharacterX), 16 + (drawCharacterY), 15 + (drawCharacterX), (drawCharacterY) + 32);
		break;
	    case 3:
		backBuffer.drawLine (16 + (drawCharacterX), 16 + (drawCharacterY), (drawCharacterX), (drawCharacterY) + 16);
		backBuffer.drawLine (16 + (drawCharacterX), 15 + (drawCharacterY), (drawCharacterX), (drawCharacterY) + 15);
		break;
	}
    }





    public static void pathfinder ()
    {

	if (invisibilityTimer == 0)
	{
	    for (byte loop1 = 0 ; loop1 <= 23 ; loop1++)
	    {
		for (byte loop2 = 0 ; loop2 <= 31 ; loop2++)
		{

		    waypointDistance [loop1] [loop2] = -1;
		}
	    }
	    for (byte loop1 = 0 ; loop1 <= 23 ; loop1++) //check everything
	    {
		for (byte loop2 = 0 ; loop2 <= 31 ; loop2++)
		{
		    if (levelData [loop1] [loop2] == false && waypointDistance [loop1] [loop2] == -1)
		    {
			if (fearTimer > 0)
			{
			    pathfindingAssignvalues (31, 23);
			}
			else
			{
			    pathfindingAssignvalues (playerX, playerY);
			}
		    }

		}

	    }
	}
    }





    public static void pathfindingAssignvalues (int PointX, int PointY)
    {
	int lowestValue = 768;
	waypointDistance [PointY] [PointX] = 0;
	waypointDirection [PointY] [PointX] = "*";
	for (byte loop1 = 0 ; loop1 <= 23 ; loop1++)
	{
	    for (byte loop2 = 0 ; loop2 <= 31 ; loop2++)

		{
		    lowestValue = 768;
		    if (levelData [loop1] [loop2] == false && waypointDistance [loop1] [loop2] == -1)
		    {
			//Choose Which way
			if (waypointDistance [loop1] [loop2] == -1)
			{
			    //Works fine up to here

			    if (loop1 > 0)
			    {
				if (waypointDistance [loop1 - 1] [loop2] >= 0 && waypointDistance [loop1 - 1] [loop2] < lowestValue)
				{
				    waypointDistance [loop1] [loop2] = waypointDistance [loop1 - 1] [loop2] + 1;
				    lowestValue = waypointDistance [loop1 - 1] [loop2];
				    waypointDirection [loop1] [loop2] = "U";
				}
			    }
			    if (loop1 < 23)
			    {
				if (waypointDistance [loop1 + 1] [loop2] >= 0 && waypointDistance [loop1 + 1] [loop2] < lowestValue)
				{
				    waypointDistance [loop1] [loop2] = waypointDistance [loop1 + 1] [loop2] + 1;
				    lowestValue = waypointDistance [loop1 + 1] [loop2];
				    waypointDirection [loop1] [loop2] = "D";
				}
			    }
			    if (loop2 > 0)
			    {
				if (waypointDistance [loop1] [loop2 - 1] >= 0 && waypointDistance [loop1] [loop2 - 1] < lowestValue)
				{
				    waypointDistance [loop1] [loop2] = waypointDistance [loop1] [loop2 - 1] + 1;
				    lowestValue = waypointDistance [loop1] [loop2 - 1];
				    waypointDirection [loop1] [loop2] = "L";
				}
			    }
			    if (loop2 < 31)
			    {
				if (waypointDistance [loop1] [loop2 + 1] >= 0 && waypointDistance [loop1] [loop2 + 1] < lowestValue)
				{
				    waypointDistance [loop1] [loop2] = waypointDistance [loop1] [loop2 + 1] + 1;
				    lowestValue = waypointDistance [loop1] [loop2 + 1];
				    waypointDirection [loop1] [loop2] = "R";
				}
			    }
			}




		    }
		    else if (levelData [loop1] [loop2])
		    {
			waypointDirection [loop1] [loop2] = "X";
		    }
		} //End Inner loop
	} //End Outer loop

    }







    public static void npcColour ()
    {
	npcRed = (int) (Math.random () * 255);
	npcGreen = (int) (Math.random () * 255);
	npcBlue = (int) (Math.random () * 255);
    }


    public static void playerColour ()
    {
	int healthLost = 100 - playerHealth;
	playerRed = healthLost * 25;
	playerGreen = (int) (playerHealth * 2.55);
	playerBlue = 0;
    }



    public static void playerMovement ()  // draw the current level
    {
	if (keyPress (key_UpArrow) && playerY > 0)
	{
	    if (levelData [playerY - 1] [playerX] == false)
	    {
		movePlayer (0);
	    }
	}
	else if (keyPress (key_RightArrow) && playerX < 31)
	{
	    if (levelData [playerY] [playerX + 1] == false)
	    {
		movePlayer (1);
	    }
	}
	else if (keyPress (key_DownArrow) && playerY < 23)
	{
	    if (levelData [playerY + 1] [playerX] == false)
	    {
		movePlayer (2);
	    }
	}
	else if (keyPress (key_LeftArrow) && playerX > 0)
	{
	    if (levelData [playerY] [playerX - 1] == false)
	    {
		movePlayer (3);
	    }
	}

    }



    public static void movePlayer (int moveplayerAngle)  //Move player 1 square
    {
	playerDir = (byte) moveplayerAngle;
	switch (moveplayerAngle) //This determines which way to move the player.0 = Up. 1 = Right. 2 = Down. 3 = Left.
	{
	    case 0:
		playerY--;
		break;
	    case 1:
		playerX++;
		break;
	    case 2:
		playerY++;
		break;
	    case 3:
		playerX--;
		break;
	}
    }


    public static void npcAI ()  //Move Enemy 1 square
    {
	pathfinder (); //Call Pathfining function
	//This determines which way to move the enemy.I0 = Up. R = Right. D = Down. L = Left.
	if (waypointDirection [npcY] [npcX] == "U")
	{
	    npcY--;
	    npcDir = 0;
	}
	else if (waypointDirection [npcY] [npcX] == "R")
	{
	    npcX++;
	    npcDir = 1;
	}
	else if (waypointDirection [npcY] [npcX] == "D")
	{
	    npcY++;
	    npcDir = 2;
	}
	else if (waypointDirection [npcY] [npcX] == "L")
	{
	    npcX--;
	    npcDir = 3;
	}
	else if (waypointDirection [npcY] [npcX] == "X") //If Enemy is in wall
	{
	    System.out.print ("Collision error.  Please restart program.");
	}

	if (playerY == npcY && playerX == npcX)
	{
	    if (protectTimer == 0)
	    {
		playerHealth -= 10;
	    }
	    else
	    {
		playerHealth--;
	    }
	}

    }



    public static void spellCaster ()  //Cast magic
    {
	if (magicTime < 1000 && keyHit (key_4) > 0)
	{
	    blink ();
	    magicTime += 1000;
	}
	if (magicTime == 0) //slow spellcasting
	{
	    if (keyHit (key_1) > 0)
	    {
		stun ();
		magicTime = 1000;
	    }
	    if (keyHit (key_2) > 0)
	    {
		reMap ();
		magicTime = 10000;
	    }
	    if (keyHit (key_3) > 0)
	    {
		heal ();
		magicTime = 2500;
	    }
	    if (keyHit (key_5) > 0)
	    {
		invisibility ();
		magicTime = 1000;
	    }
	    if (keyHit (key_6) > 0)
	    {
		blast ();
		magicTime = 500;
	    }
	    if (keyHit (key_7) > 0)
	    {
		speed ();
		magicTime = 1000;
	    }
	    if (keyHit (key_8) > 0)
	    {
		protect ();
		magicTime = 500;
	    }
	    if (keyHit (key_9) > 0)
	    {
		fear ();
		magicTime = 1000;
	    }
	    if (keyHit (key_0) > 0)
	    {
		slow ();
		magicTime = 500;
	    }
	}
	else
	{
	    magicTime--;
	}
	spellEffects ();
    }


    public static void spellEffects ()  //Cast magic
    {
	if (stunTimer > 0)
	{
	    stunTimer--;
	}


	if (invisibilityTimer > 0)
	{
	    invisibilityTimer--;
	}


	if (protectTimer > 0)
	{
	    protectTimer--;
	}


	if (speedTimer > 0)
	{
	    speedTimer--;
	    playerMovement ();
	}


	if (slowTimer > 0)
	{
	    slowTimer--;

	}


	if (fearTimer > 0)
	{
	    fearTimer--;

	}
    }




    static int stunTimer = 0;
    public static void stun ()  //Spell Stun
    {
	stunTimer = 250;
    }


    public static void heal ()  //Spell Heal
    {
	playerHealth += 10;
	if (playerHealth > 100)
	{
	    playerHealth = 100;
	}
    }


    public static void reMap ()  //Spell Heal
    {
	playerX = 0;
	playerY = 0;
	npcX = 31;
	npcY = 23;
	generateLevel ();
    }


    public static void blink ()  //Spell Heal
    {
	short newX = 0;
	short newY = 0;
	do
	{
	    newX = (short) (Math.random () * 31);
	    newY = (short) (Math.random () * 23);
	}


	while (levelData [newY] [newX]);
	playerX = newX;
	playerY = newY;

    }


    static int invisibilityTimer = 0;
    public static void invisibility ()  //Spell invisibility
    {

	invisibilityTimer = 750;
    }






    static int protectTimer = 0;
    public static void protect ()  //Spell protect
    {
	protectTimer = 1000;
    }


    static int speedTimer = 0;
    public static void speed ()  //Spell speed
    {
	speedTimer = 100;
    }


    public static void blast ()  //Blast a circle around player
    {
	if ((playerX - 1) > 0 && (playerY - 1) > 0)
	{
	    levelData [playerY - 1] [playerX - 1] = false;
	}


	if ((playerX - 1) > 0)
	{
	    levelData [playerY] [playerX - 1] = false;
	}


	if ((playerX - 1) > 0 && (playerY + 1) < 24)
	{
	    levelData [playerY + 1] [playerX - 1] = false;
	}


	if ((playerY - 1) > 0)
	{
	    levelData [playerY - 1] [playerX] = false;
	}


	if ((playerY + 1) < 24)
	{
	    levelData [playerY + 1] [playerX] = false;
	}


	if ((playerX + 1) < 32 && (playerY - 1) > 0)
	{
	    levelData [playerY - 1] [playerX + 1] = false;
	}


	if ((playerX + 1) < 32)
	{
	    levelData [playerY] [playerX + 1] = false;
	}


	if ((playerX + 1) < 32 && (playerY + 1) < 24)
	{
	    levelData [playerY + 1] [playerX + 1] = false;
	}


	colour (255, 255, 128);
	backBuffer.fillOval ((playerX * 32) - 32, (playerY * 32) - 32, 96, 96);
    }


    static int slowTimer = 0;
    public static void slow ()  //Spell slow down time
    {
	slowTimer = 2500;
    }


    static int fearTimer = 0;
    public static void fear ()  //Spell fear make enemy retreat to starting point
    {
	fearTimer = 250;
    }


    //end spells






    public static void gameOver ()
    {

	while (keyHit (key_Escape) == 0)
	{
	    colour (0, 0, 0);
	    cls ();
	    colour (255, 128, 0);
	    backBuffer.drawString ("Game Over", 0, 10);
	    backBuffer.drawString ("Your Score: " + scoreKeeper, 0, 20);
	    renderGraphics ();
	    try
	    {
		Thread.sleep (100);
	    }
	    catch (InterruptedException exception)
	    {

	    }
	}


	System.exit (0);
    }


    public static void pause ()  //Pause the game when player clicks
    {

	if (mouseHit (1) > 0)
	{
	    while (mouseHit (1) == 0)
	    {
	    }
	}
    }


    public static void updateGame ()
    {

	pause ();
	speedController++;
	if (speedController == 1)
	{
	    scoreKeeper++;
	    playerMovement ();
	    if (stunTimer == 0) //Make sure enemy isn't stunned before moving it
	    {
		npcAI ();
	    }

	}
	else if (speedController == 16 + (int) (slowTimer / 10))
	{
	    speedController = 0;
	}
	playerColour ();
	if (playerHealth <= 0) //Game over when player dead
	{
	    gameOver ();
	}
    }




    // Here is the main method:
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main (String args[])
	throws java.io.IOException
    {

	initialise (1024, 768);//Your monitor has to be able to handle this resolution
	generateLevel ();
	npcColour ();

	while (true)
	{
	    updateGame ();
	    drawLevel ();
	    spellCaster ();
	    drawCharacter (npcDir, npcX, npcY, npcRed, npcGreen, npcBlue);
	    drawCharacter (playerDir, playerX, playerY, playerRed, playerGreen, playerBlue);
	    renderGraphics ();

	    if (keyPress (key_Escape)) //exit if escape key pressed
	    {
		gameOver ();
	    }
	}
    }
} // Labyrinth_Game class


