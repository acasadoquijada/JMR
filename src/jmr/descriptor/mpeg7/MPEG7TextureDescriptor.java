package jmr.descriptor.mpeg7;

import jmr.media.JMRExtendedBufferedImage;
import jmr.colorspace.ColorSpaceJMR;
import jmr.colorspace.ColorConvertTools;
import java.awt.color.ColorSpace;
import jmr.descriptor.TextureDescriptor;

/**
 * <p>Title: JMR Project</p>
 * <p>Description: Java Multimedia Retrieval API</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: University of Granada</p>
 * @author Jesus Chamorro Martinez
 * @version 1.0
 */

public abstract class MPEG7TextureDescriptor extends TextureDescriptor {

  /**
   * This boolean let us know if the descriptor can used only image with one intensity channel
   * or image that have 3 channels knowing that the first one represent the intensity.
   * */
  boolean useOneChannel;

  /**
   * The constructor.
   * @param typeMedia type
   */
  protected MPEG7TextureDescriptor(int type, boolean useOneChannel) {
    super(type);
    this.useOneChannel = useOneChannel;
  }

  /**
   * Check the colorSpace of the input image.
   *
   * <p>
   * The color space must be {@link ColorSpace#CS_GRAY} if we need to use only one channel.
   * or it can be for example {@link ColorSpaceJMR#CS_YCbCr} that have intensity value on the first channel.
   * <br/>( {@link ColorSpaceJMR#CS_Lab} & {@link ColorSpaceJMR#CS_Luv} need to be check
   * before their implementing them.
   * </p>
   *
   *
   * @param 	cS 	The ColorSpace used
   * @return		A true value if the input image use a correct color Space.
   */
  protected boolean checkColorSpace(ColorSpace cS) {
    if (useOneChannel) {
      return (cS.getType() == ColorSpaceJMR.CS_GRAY);
    }
    else {
      return (cS.getType() == ColorSpaceJMR.CS_YCbCr);
    }
  }

  /**
   * Convert Image in {@link ColorSpaceJMR#CS_YCbCr} if they are in RGB format.
   *
   * <p>
   * This method can be improve to deal with different input image color space:<ul>
   * <li>HSI : Take only the I (intensity) channel and return a {@link BufferedImage#TYPE_BYTE_GRAY} image.</li>
   * <li>HMMD: Use the D (diff) channel and return {@link BufferedImage#TYPE_BYTE_GRAY} image</li>
   * <li>CIELab,CIELuv: Don't transform them and deal with the L (luminace) channel</li>
   * <li>...</li></ul>
   * </p>
   *
   * @see es.ugr.siar.ip.desc.VisualDescriptor#convertImg(es.ugr.siar.ip.ImageJMR)
   */
  protected JMRExtendedBufferedImage convertImg(JMRExtendedBufferedImage imSrc) {
    JMRExtendedBufferedImage dst = ColorConvertTools.colorConvertOp(imSrc, ColorSpaceJMR.getInstance(ColorSpaceJMR.CS_YCbCr));
    return new JMRExtendedBufferedImage(dst.getLayeredByteImages()[0]);
  }

}
