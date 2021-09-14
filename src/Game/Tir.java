package Game;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tir {
	int Speed, Num = 1, PowerUp, powerOfarrow, degree, power, type, MaximumHeat;
	double ActingPower ;
	BufferedImage tir;
	int x, y, Distance = 0, XNow, YNow;
	double IncreasingHeat;
	boolean drawing = true;
	int[] InFile;
	static Tir ti;
	
	public Tir(int type, int ActingPower){
		this.type = type;
		ti = this;
		initialize();
		UpdatePower(ActingPower);
		
	}
	public void initialize() {
		
		if(type == 1) {
			this.Speed = 200;
			this.power = 1;
			this.IncreasingHeat = 5;
			this.MaximumHeat = 100;
		}
		if(type == 2) {
			this.Speed = 150;
			this.power = 2;
			this.IncreasingHeat = 6;
			this.MaximumHeat = 100;
		}
		if(type == 3) {
			this.Speed = 100;
			this.power = 3;
			this.IncreasingHeat = 4;
			this.MaximumHeat = 100;
		}
	}
	public void UpdatePower(int ActingPower) {
		if(ActingPower == 0 || ActingPower == 1 || ActingPower == 2 || ActingPower == 3) {
			Game.game.NumberOfArrow = ActingPower+1;
			this.ActingPower = power;
		}
		if(ActingPower > 3) {
			Game.game.NumberOfArrow = 4;
			this.ActingPower = this.power + (ActingPower-3)*((double)(power)/4)*this.power;
		}
	}
	public Tir() {
		
	}

}
