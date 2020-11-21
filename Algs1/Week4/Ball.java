import java.util.Random;
import edu.princeton.cs.algs4.StdDraw;

public class Ball {
	private double rx, ry; // position
	private double vx, vy; // velocity
	private final double radius; // radius
	
	public Ball() {
		radius = 0.01;
		
		Random r = new Random();		
		
		rx = r.nextDouble();
		ry = r.nextDouble();		
		
		double v = 0.01;
		int i;
		
		i = r.nextInt();		
		vx = v * (i % 2 == 0 ? 1 : -1);
		i = r.nextInt();
		vy = v * (i % 2 == 0 ? 1 : -1);
	}
	
	public Ball(double rx, double ry, double vx, double vy, double radius) {
		this.rx = rx;
		this.ry = ry;
		this.vx = vx;
		this.vy = vy;		
		this.radius = radius;
	}
	
	public void move(double dt) {
		if((rx + vx*dt < radius) || (rx + vx*dt > 1.0 - radius)) { vx = -vx; }
		if((ry + vy*dt < radius) || (ry + vy*dt > 1.0 - radius)) { vy = -vy; }
		rx = rx + vx*dt;
		ry = ry + vy*dt;
	}
	
	public void draw() {
		StdDraw.filledCircle(rx, ry, radius);
	}
}
