package common;

public class MathUtils {
	public static int doubleToInt(double value)
	{
		return (int)Math.floor(value);
	}
	
	public static boolean withinBounds(int pointX, int pointY, int startX, int width, int startY, int height)
	{
		if ((pointX >= startX && pointX <= startX + width) && (pointY >= startY && pointY <= startY + height))
		{
			return true;
		}	
		return false;
	}
	
	public static boolean meldOverlaps (int x, int y, int size, Meld m) {
		/*
		return (x < m.getMeldXPosition() + m.getSize() * 25 && x + size * 25
				> m.getMeldXPosition() && y < m.getMeldYPosition() + 40
				&& y + 40 > m.getMeldYPosition());
		*/
		return !(m.getMeldXPosition() > x + size * 25
				|| m.getMeldYPosition() > y + 40
				|| x > m.getMeldXPosition() + m.getSize() * 25
				|| y > m.getMeldYPosition() + 40);
	}
}
