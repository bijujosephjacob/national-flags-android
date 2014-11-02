package jabs.nationalflags;

import android.graphics.Color;

public class Utils {
	/**
	 * Blend two colors.
	 * 
	 * @param color1
	 *            First color to blend.
	 * @param color2
	 *            Second color to blend.
	 * @param ratio
	 *            Blend ratio. 0.5 will give even blend, 1.0 will return color1,
	 *            0.0 will return color2 and so on.
	 * @return Blended color.
	 */
	public static int blendColor(int color1, int color2, double ratio) {
		float r = (float) ratio;
		float ir = (float) 1.0 - r;

		int rgb1[] = new int[] { Color.red(color1), Color.green(color1),
				Color.blue(color1) };
		int rgb2[] = new int[] { Color.red(color2), Color.green(color2),
				Color.blue(color2) };

		int color = Color.rgb((int) (rgb1[0] * r + rgb2[0] * ir),
				(int) (rgb1[1] * r + rgb2[1] * ir),
				(int) (rgb1[2] * r + rgb2[2] * ir));

		return color;
	}
	
	public static long roundToNearestMultiple(double value, int multiple) {
		return multiple * (Math.round(value / multiple));
	}
}
