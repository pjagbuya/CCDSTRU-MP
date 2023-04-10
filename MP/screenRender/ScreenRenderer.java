import java.util.Arrays;

public class ScreenRenderer {

    private int width, height;
    private int borderWidth, borderHeight;

    private int trueWidth, trueHeight;
    private char[][] pixels;

    public ScreenRenderer(int width, int height, int borderWidth, int borderHeight) {
        this.width = width;
        this.height = height;
        this.borderWidth = borderWidth;
        this.borderHeight = borderHeight;
        trueWidth = width + borderWidth * 2;
        trueHeight = height + borderHeight * 2;
        initScreen();
    }

    public void initScreen() {
        pixels = new char[trueHeight][trueWidth];
        for (int i = 0; i < trueHeight; i++) {
            Arrays.fill(pixels[i], ' ');
        }
        fillBorder();
    }

    private void fillBorder() {
        for (int i = 0; i < trueHeight; i++) {
            for (int j = 0; j < trueWidth; j++) {
                //Outer border
                if((i == 0 || i == trueHeight - 1) && (j != 0 && j != trueWidth - 1)) {
                    pixels[i][j] = '═';
                } else if((j == 0 || j == trueWidth - 1) && (i != 0 && i != trueHeight - 1)) {
                    pixels[i][j] = '║';
                }
                // Inner border
                else if((i == borderHeight -1 || i == trueHeight - borderHeight) &&
                        (j > borderWidth - 1 && j < trueWidth - borderWidth)) {
                    pixels[i][j] = '═';
                } else if((j == borderWidth -1 || j == trueWidth - borderWidth) &&
                        (i > borderHeight - 1 && i < trueHeight - borderHeight)) {
                    pixels[i][j] = '║';
                }
                // In-between
                else if (i < borderHeight - 1 || i > trueHeight - borderHeight ||
                        j < borderWidth - 1 || j > trueWidth - borderWidth) {
                    pixels[i][j] = '░';
                }
            }
        }

        //Outer Edges
        pixels[0][0] = '╔';
        pixels[0][trueWidth - 1] = '╗';
        pixels[trueHeight - 1][0] = '╚';
        pixels[trueHeight - 1][trueWidth - 1] = '╝';

        //Inner Edges
        pixels[borderHeight - 1][borderWidth - 1] = '╔';
        pixels[borderHeight - 1][trueWidth - borderWidth] = '╗';
        pixels[trueHeight - borderHeight][borderWidth - 1] = '╚';
        pixels[trueHeight - borderHeight][trueWidth - borderWidth] = '╝';
    }

    public void setPixels(int posX, int posY, char pixel) {
        if(insideBorder(posX, posY)) {
            pixels[borderHeight + posY][borderWidth + posX] = pixel;
        }
    }

    public void setPixels(int posX, int posY, int numPixels) {
        char[] nums = ("" + numPixels).toCharArray();
        for (int i = 0; i < nums.length; i++) {
            if(insideBorder(posX + i, posY)) {
                pixels[borderHeight + posY][borderWidth + posX + i] = nums[i];
            }
        }
    }

    public void setPixels(int posX, int posY, String stringPixels) {
        String[] lines = stringPixels.split("\n");
        for (int i = 0; i < lines.length; i++) {
            char[] chars = lines[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if(insideBorder(posX + j, posY + i)) {
                    pixels[borderHeight + posY + i][borderWidth + posX + j] = chars[j];
                }
            }
        }
    }

    public void createHorizontalLine(int posY, int lineSize) {
        for (int i = 0; i < lineSize; i++) {
            for (int j = 0; j < width; j++) {
                if(insideBorder(j, posY + i)) {
                    if(i == 0 || i == lineSize - 1) {
                        pixels[borderHeight + i + posY][borderWidth + j] = '═';
                    } else {
                        pixels[borderHeight + i + posY][borderWidth + j] = '░';
                    }
                }
            }
        }
    }

    public void createHorizontalLine(int posY, int lineSize, int lineHeight) {
        for (int i = 0; i < lineSize; i++) {
            for (int j = 0; j < lineHeight; j++) {
                if(insideBorder(j, posY + i)) {
                    if(i == 0 || i == lineSize - 1) {
                        pixels[borderHeight + i + posY][borderWidth + j] = '═';
                    } else {
                        pixels[borderHeight + i + posY][borderWidth + j] = '░';
                    }
                }
            }
        }
    }

    public void createVerticalLine(int posX, int lineSize) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < lineSize; j++) {
                if(insideBorder(posX + j, i)) {
                    if(j == 0 || j == lineSize - 1) {
                        pixels[borderHeight + i][borderWidth + j + posX] = '║';
                    } else {
                        pixels[borderHeight + i][borderWidth + j +posX] = '░';
                    }
                }
            }
        }
    }

    public void createVerticalLine(int posX, int lineSize, int lineHeight) {
        for (int i = 0; i < lineHeight; i++) {
            for (int j = 0; j < lineSize; j++) {
                if(insideBorder(posX + j, i)) {
                    if(j == 0 || j == lineSize - 1) {
                        pixels[borderHeight + i][borderWidth + j + posX] = '║';
                    } else {
                        pixels[borderHeight + i][borderWidth + j +posX] = '░';
                    }
                }
            }
        }
    }

    public void renderScreen() {
        for (int i = 0; i < trueHeight; i++) {
            for (int j = 0; j < trueWidth; j++) {
                System.out.print(pixels[i][j]);
            }
            System.out.println();
        }
    }

    private boolean insideBorder(int x, int y) {
        int relativeX = borderWidth + x;
        int relativeY = borderHeight + y;
        return relativeX > borderWidth - 1 && relativeX < trueWidth - borderWidth &&
                relativeY > borderHeight - 1 && relativeY < trueHeight - borderHeight;
    }
}
