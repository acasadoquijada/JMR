/**
 *
 */
package jmr.colorspace;


/**
 * L*,a*,b* or CIELAB Color Space is an approximate perceptually uniform space.
 *
 * <p> This space is recommended for application in reflective light (Wyszecki & Stiles 1982) such in
 * the printing industry.<br/>
 * 	L* stands for lightness value and take into consideration the non-linear relation between
 * the lightness and the perceptual brightness or relative luminance of a point. The component
 * a* denotes relative redness-greenness and b* yellowness-blueness.
 * This space is obtained from CIEXYZ 1931 and it use a Reference White also called {@link WhitePoint}
 * to normalized these XYZ value.</p>
 *
 * <p>
 * <img src="http://www.couleur.org/spaces/Labspace.jpg"/>
 * <img src="http://www.couleur.org/equations/lab.gif"/>
 * </p>
 *
 *
 * @author  RAT Benoit <br/>
 * (<a href="http://ivrg.epfl.ch" target="about_blank">IVRG-LCAV-EPFL</a> &
 *  <a href="http://decsai.ugr.es/vip" target="about_blank">VIP-DECSAI-UGR</a>)
 * @version 1.0
 * @since 29 nov. 07
 * @see <a href="http://www.couleur.org/index.php?page=transformations">www.couleur.org</a>
 */
public class ColorSpaceLab extends ColorSpaceJMR {


	/**     */
	private static final long serialVersionUID = 8551992302672161548L;


	private WhitePoint wP;
	private float epsi = 216.f/24389.f;
	private float kappa = 24389.f/(116.f*27.f);
	private float frac16116 = 16.f/116.f;

	 /**
     * Constructs an instance of this class with <code>type</code>
     * {@link ColorSpaceJMR#CS_Lab}, 3 components, and used as reference white
     * the {@link WhitePoint#WP_TYPE_D65} (Daylight)
     */
	protected ColorSpaceLab() {
		super(ColorSpaceJMR.CS_Lab,3);
		this.wP = WhitePoint.getInstance(WhitePoint.WP_TYPE_D65);
	}


	 /**
     * Constructs an instance of this class with <code>type</code>
     * {@link ColorSpaceJMR#CS_Lab}, 3 components, giving a {@link WhitePoint}
     * in parameters.
     *
     * @param 	wp	 The white point to normalize XYZ value
     */
	public ColorSpaceLab(WhitePoint wp) {
		super(ColorSpaceJMR.CS_Lab,3);
		this.wP=wp;
	}


	/**
	 * In the case we need to change the white point for this color Space
	 * @param 	type 	the white point you can find in {@link WhitePoint}
	 */
	public void setWhitePoint(int type) {
		wP = WhitePoint.getInstance(type);
	}

	/** Transform a RGB pixel in XYZ pixel then in CIELAB.
	 *
	 * @param 	rgbVec	a vector (length=3) with rgb values normalized R,G,B=[0,1]
	 * @return 			a vector (length=3) with CIELAB values L*=[0,100],a*=[-500,500],b*=[-200,200]
	 * @see <i>The Color Image Processing Book, Sangwine 1998</i>
	 */
	public float[] fromRGB(float[] rgbVec) {
//		my.Debug.printCount("fromRGB (RGB > XYZ > CIELAB):");
//		my.Debug.printCount(" - RGB input :  rgb=["+rgbVec[0]+","+rgbVec[1]+","+rgbVec[2]+"];");
		float[] xyzVec = new float[3];
		RGB2XYZ(rgbVec,xyzVec);
//		my.Debug.printCount(" - XYZ output: 	xyz=["+xyzVec[0]+","+xyzVec[1]+","+xyzVec[2]+"];");
		return fromCIEXYZ(xyzVec);
		//return xyzVec;
	}


	/**
	 * Transform a CIELAB pixel in XYZ then in RGB pixel.
	 *
	 * @param 	labVec	a vector (length=3) with lab values L*=[0,100],a*=[-500,500],b*=[-200,200]
	 * @return 			a vector (length=3) with rgb values normalized R,G,B=[0,1]
	 * @see <i>The Color Image Processing Book, Sangwine 1998</i>
	 */
	public float[] toRGB(float[] labVec) {
		float[] xyzVec = toCIEXYZ(labVec);
		float [] rgbVec =  new float[3];

		XYZ2RGB(xyzVec,rgbVec);

		return rgbVec;

	}

	/**
	 * Transform a XYZ pixel in Lab pixel.
	 *
	 * @param 	xyzVec 	a vector (length=3) with xyz values normalized X,Y,Z=[0,1]
	 * @return 			a vector (length=3) with lab values L*=[0,100],a*=[-500,500],b*=[-200,200]
	 * @see <i>The Color Image Processing Book, Sangwine 1998</i>
	 * @see <a href="http://www.brucelindbloom.com">BruceLindbloom's website for Kappa and Espilon values</a>
	 */
	public float[] fromCIEXYZ(float[] xyzVec) {
//		my.Debug.printCount("fromRGB (RGB > XYZ > CIELAB):");
//		my.Debug.printCount(" - XYZ input: 	xyz=["+xyzVec[0]+","+xyzVec[1]+","+xyzVec[2]+"];");

		float[] labVec = new float[3];
		float xr, yr, zr;

		wP.normVal(xyzVec); //XYZ Normalized by the WhitePoint
		xr = xyzVec[0];
		yr = xyzVec[1];
		zr = xyzVec[2];

//		my.Debug.printCount(" - XYZ WP: 	xyz=["+xr+","+yr+","+zr+"];");

		float fy = func(yr);

		labVec[0] = 116f * fy - 16f;
		labVec[1] = 500f*(func(xr)-fy);
		labVec[2] = 200f*(fy-func(zr));

//		my.Debug.printCount(" - LAB output: 	lab=["+labVec[0]+","+labVec[1]+","+labVec[2]+"];");

		return labVec ;
	}


	private float func(float x) {
		if ( x > epsi ) return (float)Math.pow(x, 1.f/3.f);
		else  return kappa * x + frac16116;
	}

	/**
	 * Transform a Lab pixel in XYZ pixel.
	 *
	 * <p style="color:red">This function is not implemented and return a <b>zero vector</b></p>
	 *
	 * @param 	labVec 	a vector (length=3) with lab values  L*=[0,100],a*=[-500,500],b*=[-200,200]
	 * @return 			a vector (length=3) with xyz values normalized X,Y,Z=[0,1]
	 * @deprecated Return a zero vector.
	 */
	public float[] toCIEXYZ(float[] labVec) {
		float[] xyzVec = {0f,0f,0f}; //TODO Transformation not implemented

		return xyzVec;
	}



	public float getMaxValue(int cmp) {
		switch(cmp) {
		case 0:
			return 100.f;
		case 1:
			return 500.f;
		case 2:
			return 200.f;
		default:
			return super.getMaxValue(cmp);
		}
	}

	public float getMinValue(int cmp) {
		switch(cmp) {
		case 1:
			return -500.f;
		case 2:
			return -200.f;
		default:
			return super.getMinValue(cmp);
		}
	}

	public String getName(int cmp) {
		switch(cmp) {
		case 0:
			return "L*";
		case 1:
			return "a*";
		case 2:
			return "b*";
		default:
			return super.getName(cmp);
		}
	}

	public String toString() {
		String str=super.toString();
		str+="/ espilon="+epsi+"; kappa="+kappa;
		return str;
	}
}
