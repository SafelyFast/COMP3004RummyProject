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
}
