import java.awt.Color;
import java.awt.image.BufferedImage;

import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.space.Object2DGrid;

import java.awt.Color;



/**
 * Class that implements the simulation agent for the rabbits grass simulation.

 * @author
 */

public class RabbitsGrassSimulationAgent implements Drawable {

	private int x;
	private int y;
	private int vX;
	private int vY;
	private int energy;
	private boolean fertility = true;
	private static int IDNumber = 0;
	private int ID;
	private RabbitsGrassSimulationSpace rgSpace;
	private static BufferedImage rabbitIcon;


	public RabbitsGrassSimulationAgent(int energyInit){
		x = -1;
		y = -1;
		energy = energyInit;

		setVxVy();

		IDNumber++;
		ID = IDNumber;

	}

	private void setVxVy(){
		vX = 0;
		vY = 0;

		vX = (int)Math.floor(Math.random() * 3) - 1;

		if(vX == 0){
			while(vY == 0){
				vY = (int)Math.floor(Math.random() * 3) - 1;
			}
		}
		else{
			vY = 0;
		}
	}


	public void setRabbitGrassSpace(RabbitsGrassSimulationSpace cds){
		rgSpace = cds;
	}

	public void setXY(int newX, int newY){
		x = newX;
		y = newY;
	}

	public String getID(){
		return "A-" + ID;
	}

	public int getEnergy(){
		return energy;
	}

	public void setEnergy(int e){
		energy = e;
	}

	public void report(){
		System.out.println(getID() +
				" at (" +
				x + "," + y +
				") has " +
				getEnergy() + " energy");
	}

	public void draw(SimGraphics G) {
		if (energy > 0) {
			if (rabbitIcon != null) {
				G.drawImageToFit(rabbitIcon);
			} else {
				G.drawFastRoundRect(Color.blue);
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void step(int stepEnergy) {
		setVxVy();

		int newX = x + vX;
		int newY = y + vY;

		Object2DGrid grid = rgSpace.getCurrentAgentSpace();
		newX = (newX + grid.getSizeX()) % grid.getSizeX();
		newY = (newY + grid.getSizeY()) % grid.getSizeY();

		int count = 0;

		energy -= stepEnergy;
		while (!tryMove(newX, newY) && count++ < 10) {
			setVxVy();

			newX = x + vX;
			newY = y + vY;

			newX = (newX + grid.getSizeX()) % grid.getSizeX();
			newY = (newY + grid.getSizeY()) % grid.getSizeY();



		}
		energy += rgSpace.eatGrassAt(x, y);
	}



	private boolean tryMove(int newX, int newY){
		return rgSpace.moveAgentAt(x, y, newX, newY);
	}

	public static void setRabbitIcon(BufferedImage icon) {
		rabbitIcon = icon;
	}

	public void setFertility(boolean val){
		fertility = val;
	}

	public boolean getFertility(){
		return fertility;
	}
}
