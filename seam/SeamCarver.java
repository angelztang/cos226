import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.awt.Color;

public class SeamCarver {
    private Picture pic; // initial picture
    private int width; // picture width
    private int height; // picture height

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        validate(picture);
        pic = new Picture(picture);
        width = picture().width();
        height = picture().height();
    }

    // current picture
    public Picture picture() {
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validate(x, y);
        int left;
        if (x - 1 < 0) left = width - 1;
        else left = x - 1;

        int right;
        if (x + 1 == width) right = 0;
        else right = x + 1;

        int top;
        if (y - 1 < 0) top = height - 1;
        else top = y - 1;

        int bottom;
        if (y + 1 == height) bottom = 0;
        else bottom = y + 1;

        Color leftColor = pic.get(left, y);
        Color rightColor = pic.get(right, y);
        Color topColor = pic.get(x, top);
        Color bottomColor = pic.get(x, bottom);

        double rx = leftColor.getRed() - rightColor.getRed();
        double gx = leftColor.getGreen() - rightColor.getGreen();
        double bx = leftColor.getBlue() - rightColor.getBlue();
        double x2 = Math.pow(rx, 2) + Math.pow(gx, 2) + Math.pow(bx, 2);

        double ry = topColor.getRed() - bottomColor.getRed();
        double gy = topColor.getGreen() - bottomColor.getGreen();
        double by = topColor.getBlue() - bottomColor.getBlue();
        double y2 = Math.pow(ry, 2) + Math.pow(gy, 2) + Math.pow(by, 2);
        return Math.sqrt(x2 + y2);
    }

    // interchanges row and column indices for horizontal seams
    private Picture transpose() {
        Picture newPic = new Picture(height, width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newPic.set(j, i, pic.get(i, j));
            }
        }
        int temp = height;
        height = width;
        width = temp;
        return newPic;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        Picture tmpPic = pic;
        pic = transpose();
        int[] ans = findVerticalSeam();
        pic = tmpPic;
        return ans;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[][] edgeTo = new int[height][width];
        double[][] opt = new double[height][width];
        double e;
        double smallestE = 0;
        int edge;
        int minSeam = 0;
        double minSeamEnergy = Double.POSITIVE_INFINITY;
        int[] ans = new int[height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                e = energy(j, i);
                edge = j;
                if (i != 0) {
                    smallestE = opt[i - 1][j];
                    if (j < width - 1) {
                        if (opt[i - 1][j + 1] < opt[i - 1][j]) {
                            smallestE = opt[i - 1][j + 1];
                            edge = j + 1;
                        }
                    }
                    else if (j == width - 1 && width > 1) {
                        if (opt[i - 1][j] > opt[i - 1][j - 1]) {
                            edge = j - 1;
                            smallestE = opt[i - 1][j - 1];
                        }
                    }
                    if (j > 0 && j < width - 1) {
                        if (smallestE > opt[i - 1][j - 1]) {
                            edge = j - 1;
                            smallestE = opt[i - 1][j - 1];
                        }
                    }
                }
                edgeTo[i][j] = edge;
                opt[i][j] = e + smallestE;
                if (i == height - 1 && opt[i][j] < minSeamEnergy) {
                    minSeamEnergy = opt[i][j];
                    minSeam = j;
                }
            }
        }
        for (int i = 0; i < height; i++) {
            ans[ans.length - 1 - i] = minSeam;
            minSeam = edgeTo[ans.length - 1 - i][minSeam];
        }
        return ans;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        Picture tmpPic = pic;
        pic = transpose();
        removeVerticalSeam(seam);
        pic = tmpPic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validate(seam);
        Picture tmpPic = new Picture(width - 1, height);
        int x = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width - 1; j++) {
                if (seam[i] != j) {
                    tmpPic.set(j, i, pic.get(x, i));
                    x++;
                }
                else {
                    tmpPic.set(j, i, pic.get(x + 1, i));
                    x += 2;
                }
            }
            x = 0;
        }
        pic = tmpPic;
        width--;
    }

    // validates argument of pic for SeamCarver()
    private void validate(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("picture cannot be null");
        }
    }

    // validates arguments for energy()
    private void validate(int x, int y) {
        if (x > width || x < 0 || y > height || y < 0) {
            throw new IllegalArgumentException("pixel is out of bounds");
        }
    }

    // validates RemoveVerticalSeam()
    private void validate(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("seam cannot be null");
        if (seam.length != height || width == 1)
            throw new IllegalArgumentException("dimensions do not match");
        for (int i = 0; i < height; i++) {
            if (seam[i] < 0 || seam[i] > width - 1)
                throw new IllegalArgumentException("entry not valid");
            if (i > 0 && (Math.abs(seam[i - 1] - seam[i]) > 1)) {
                throw new IllegalArgumentException(
                        "adjacent entries differ by more than one");
            }
        }
    }

    //  unit testing (required)
    public static void main(String[] args) {
        Picture picture = new Picture(
                "city8000-by-2000.png");
        SeamCarver test = new SeamCarver(picture);
        System.out.println(test.height());
        System.out.println(test.width());
        System.out.println(test.picture());
        System.out.println(test.energy(1, 1));

        Stopwatch sw = new Stopwatch();

        test.removeHorizontalSeam(test.findHorizontalSeam());
        test.removeVerticalSeam(test.findVerticalSeam());

        // StdOut.printf("new image size is %d columns by %d rows\n", test.width(), test.height());

        StdOut.println("Resizing time: " + sw.elapsedTime() + " seconds.");
        // picture.show();
        // test.picture().show();


    }

}


