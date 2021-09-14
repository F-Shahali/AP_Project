package Game;
import java.io.BufferedReader;
import java.io.FileReader;

public class Players implements Comparable<Players>{
	int bomb = 0, heartt = 0, bnanaNum = 0, powerOfarrow = 0,
			KindOfarrow = 0, level = 0, levelInlevel = 0, Time = 0;
	Integer score, Wave;
	String name;
	int[] InFile;
	public Players(String name) {
		this.name = name;
		initialize();
	}
	public Integer getWave() {
		return Wave;
	}

	public Integer getScore() {
		return score;
	}

	public Integer getTime() {
		return Time;
	}

	private void initialize() {
		try {
			InFile = new int[9];
			String lines;
			int i =0;
			BufferedReader bw = new BufferedReader(new FileReader("file/" + name + ".data"));
			while((lines = bw.readLine()) != null) {
				InFile[i] = Integer.valueOf(lines);
				i++;
			}
			bw.close();
			bomb = InFile[0]; heartt = InFile[1]; bnanaNum = InFile[2]; powerOfarrow = InFile[3];
			KindOfarrow = InFile[4]; score = InFile[5]; level = InFile[6]; levelInlevel = InFile[7]; Time =InFile[8]; 
//			if(level > 1) {
				Wave = (level-1)*5 + levelInlevel-1;
//			}
			
		} catch (Exception e) {}
		
	}
	@Override
    public String toString() {
        return "[wave=" + Wave + "]";
    }
	@Override
	public int compareTo(Players p) {
		if(this.getWave() == p.getWave()) {
			if(this.getScore() == p.getScore())
				return this.getTime().compareTo(p.getTime());
			return this.getScore().compareTo(p.getScore());
			
		}
		return this.getWave().compareTo(p.getWave());
	}

}
