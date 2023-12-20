package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static java.lang.Math.sqrt;

/**
 * This helper class contains the logic for compressing an image.
 */
public class CompressImage {

  /**
   * Function to compress an image.
   *
   * @param image The image to compress
   * @return the compressed image
   */
  public static ImageNew compress(ImageNew image, int percentage) throws IllegalArgumentException {
    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Percent value should be between 0 and 100.");
    }
    try {
      ImageNew[] rgb_split = image.splitChannel();

      Double[][] r_compressed_haar = compressHelperHaar(rgb_split[0]);
      Double[][] g_compressed_haar = compressHelperHaar(rgb_split[1]);
      Double[][] b_compressed_haar = compressHelperHaar(rgb_split[2]);


      Set<Double> combined_set = new TreeSet<>();
      for (int i = 0; i < image.getHeight(); i++) {
        for (int j = 0; j < image.getWidth(); j++) {
          combined_set.add(Math.abs(r_compressed_haar[i][j]));
          combined_set.add(Math.abs(g_compressed_haar[i][j]));
          combined_set.add(Math.abs(b_compressed_haar[i][j]));
        }
      }

      ArrayList<Double> unique_values = new ArrayList<Double>(combined_set);
      Collections.sort(unique_values);
      Double threshold = unique_values.get(((percentage * unique_values.size()) / 100) - 1);


      Double[][] r_modified_threshold = compressHelperModifyThreshold(r_compressed_haar,
              threshold);
      Double[][] g_modified_threshold = compressHelperModifyThreshold(g_compressed_haar,
              threshold);
      Double[][] b_modified_threshold = compressHelperModifyThreshold(b_compressed_haar,
              threshold);


      Image r_compressed = inverseHaar(
              r_modified_threshold, image.getWidth(), image.getHeight());
      Image g_compressed = inverseHaar(
              g_modified_threshold, image.getWidth(), image.getHeight());
      Image b_compressed = inverseHaar(
              b_modified_threshold, image.getWidth(), image.getHeight());

      return new ColorImageNew().combineChannels(r_compressed, g_compressed, b_compressed);
    } catch (IllegalStateException e) {
      Double[][] compressed_haar = compressHelperHaar(image);
      Double[] combinedValues = Stream.of(compressed_haar)
              .flatMap(Stream::of)
              .toArray(Double[]::new);

      Set<Double> combined_set = new TreeSet<>((Arrays.asList(combinedValues)));
      ArrayList<Double> unique_values = new ArrayList<>(combined_set);
      Collections.sort(unique_values);
      Double threshold = unique_values.get((percentage * unique_values.size()) / 100);
      Double[][] modified_threshold = compressHelperModifyThreshold(compressed_haar, threshold);
      Image compressed = inverseHaar(modified_threshold, image.getWidth(), image.getHeight());
      return new GreyscaleImageNew(compressed);
    } catch (Exception e) {
      System.out.println("Exception occurred during Compressing image");
      return null;
    }
  }

  private static Double[][] compressHelperModifyThreshold(Double[][] image, Double threshold) {
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image.length; j++) {
        if (Math.abs(image[i][j]) <= threshold) {
          image[i][j] = 0D;
        }
      }
    }
    return image;
  }

  private static Double[][] compressHelperHaar(ImageNew image) {
    int img_width = image.getWidth();
    int img_height = image.getHeight();
    int nextPowerOf2_width = (int) Math.pow(2, Math.ceil(Math.log(img_width) / Math.log(2)));
    int nextPowerOf2_height = (int) Math.pow(2, Math.ceil(Math.log(img_height) / Math.log(2)));

    int compressedImageSize = Math.max(nextPowerOf2_height, nextPowerOf2_width);

    Double[][] compressedImageGrid = new Double[compressedImageSize][compressedImageSize];
    for (int i = 0; i < compressedImageSize; i++) {
      for (int j = 0; j < compressedImageSize; j++) {
        compressedImageGrid[i][j] = i < img_height && j < img_width ? image.getPixel(
                i, j).getValue() : 0D;
      }
    }

    return haar(compressedImageGrid, compressedImageSize);

  }

  private static ImageNew inverseHaar(Double[][] compressedImageGrid,
                                      int imageWidth, int imageHeight) {
    int c = 2;
    int compressedImageSize = compressedImageGrid.length;
    while (c <= compressedImageSize) {
      for (int i = 0; i < c; i++) {

        Double[] colArray = new Double[c];
        for (int row = 0; row < c; row++) {
          colArray[row] = compressedImageGrid[row][i];
        }

        colArray = compressionInvert(colArray);

        for (int row = 0; row < c; row++) {
          compressedImageGrid[row][i] = colArray[row];
        }
      }

      for (int i = 0; i < c; i++) {
        compressedImageGrid[i] = compressionInvert(compressedImageGrid[i]);
      }
      c = c * 2;
    }

    AbstractPixel[][] compressedImage = new AbstractPixel[imageHeight][imageWidth];
    for (int i = 0; i < imageHeight; i++) {
      for (int j = 0; j < imageWidth; j++) {
        int value = (int) Math.round(compressedImageGrid[i][j]);
        if (value < 0) {
          value = 0;
        } else {
          value = Math.min(value, 255);
        }
        //System.out.printf("i,j=%d,%d",i,j);
        compressedImage[i][j] = new GreyscalePixel(value);
      }
    }

    return new GreyscaleImageNew(compressedImage);
  }

  private static Double[][] haar(Double[][] compressedImageGrid, int compressedImageSize) {
    int c = compressedImageSize;
    while (c > 1) {
      for (int i = 0; i < c; i++) {
        compressedImageGrid[i] = compressTransform(compressedImageGrid[i]);
      }
      for (int i = 0; i < c; i++) {

        Double[] colArray = new Double[c];
        for (int row = 0; row < c; row++) {
          colArray[row] = compressedImageGrid[row][i];
        }

        colArray = compressTransform(colArray);

        for (int row = 0; row < c; row++) {
          compressedImageGrid[row][i] = colArray[row];
        }
      }
      c = c / 2;
    }
    return compressedImageGrid;
  }

  /**
   * A helper method to perform Averaging and differencing.
   *
   * @param s A list of values to be compressed
   * @return compressed list
   */
  private static Double[] baseAndDifference(Double[] s) {
    List<Double> avg = new ArrayList<Double>();
    List<Double> diff = new ArrayList<Double>();

    for (int i = 0; i < s.length; i = i + 2) {
      double a = s[i];
      double b = s[i + 1];
      double av = (a + b) / sqrt(2);
      double di = (a - b) / sqrt(2);

      avg.add(av);
      diff.add(di);

    }
    avg.addAll(diff);
    return avg.toArray(new Double[0]);
  }


  /**
   * A helper method to perform inverse of Averaging and differencing.
   *
   * @param s A list of values
   * @return original sequence list
   */
  private static List<Double> reverseBaseAndDifference(List<Double> s) {

    List<Double> result = new ArrayList<>();

    for (int i = 0; i < (s.size() / 2); i = i + 1) {
      int j = (s.size() / 2) + i;
      double a = s.get(i);
      double b = s.get(j);

      double av = (a + b) / sqrt(2);
      double de = (a - b) / sqrt(2);

      result.add(av);
      result.add(de);
    }

    return result;
  }

  /**
   * Let us assume that of length is a power of 2 (if not, we pad it with extra zeroes at the end).
   * As a result, we get a sequence of length in which the first term is the "global average"
   * and the rest of the terms are various differences.
   *
   * @param s sequence of numbers
   * @return sequence of original length in which the first term is the "global average"
   *         and the rest of the terms are various differences
   */
  private static Double[] compressTransform(Double[] s) {
    int m = s.length;

    while (m > 1) {
      Double[] newS_avg_diff = baseAndDifference(Arrays.copyOfRange(s, 0, m));
      for (int i = 0; i < m; i++) {
        s[i] = newS_avg_diff[i];
      }
      m = m / 2;
    }

    return s;
  }

  private static Double[] compressionInvert(Double[] s) {
    int l = s.length;
    int m = 2;

    while (m <= l) {
      //System.out.println("\nm = " + m);

      List<Double> tempS = new ArrayList<>();
      for (int i = 0; i < m; i++) {
        tempS.add(s[i]);
        //System.out.println("tempS = " + tempS);
      }
      List<Double> resReverse = reverseBaseAndDifference(tempS);
      for (int i = 0; i < resReverse.size(); i++) {
        s[i] = resReverse.get(i);
      }
      m = m * 2;
    }
    return s;
  }


}