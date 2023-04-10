package screenRender;

import java.util.Arrays;

public class ScreenRenderer {

    private int width, height;
    private int borderWidth, borderHeight;

    private int trueWidth, trueHeight;
    private Pixel[][] pixels;

    public ScreenRenderer(int width, int height, int borderWidth, int borderHeight, Colors color) {
        this.width = width;
        this.height = height;
        this.borderWidth = borderWidth;
        this.borderHeight = borderHeight;
        trueWidth = width + borderWidth * 2;
        trueHeight = height + borderHeight * 2;
        initScreen(color);
    }

    public void initScreen(Colors color) {
        pixels = new Pixel[trueHeight][trueWidth];
        for (int i = 0; i < trueHeight; i++) {
            for (int j = 0; j < trueWidth; j++) {
                pixels[i][j] = new Pixel(' ', Colors.OFF_COLOR);
            }
        }
        fillBorder(color);
    }

    private void fillBorder(Colors color) {
        for (int i = 0; i < trueHeight; i++) {
            for (int j = 0; j < trueWidth; j++) {
                //Outer border
                if((i == 0 || i == trueHeight - 1) && (j != 0 && j != trueWidth - 1)) {
                    pixels[i][j] = new Pixel('═', color);
                } else if((j == 0 || j == trueWidth - 1) && (i != 0 && i != trueHeight - 1)) {
                    pixels[i][j] = new Pixel('║', color);
                }
                // Inner border
                else if((i == borderHeight -1 || i == trueHeight - borderHeight) &&
                        (j > borderWidth - 1 && j < trueWidth - borderWidth)) {
                    pixels[i][j] = new Pixel('═', color);
                } else if((j == borderWidth -1 || j == trueWidth - borderWidth) &&
                        (i > borderHeight - 1 && i < trueHeight - borderHeight)) {
                    pixels[i][j] = new Pixel('║', color);
                }
                // In-between
                else if (i < borderHeight - 1 || i > trueHeight - borderHeight ||
                        j < borderWidth - 1 || j > trueWidth - borderWidth) {
                    pixels[i][j] = new Pixel('░', color);
                }
            }
        }

        //Outer Edges
        pixels[0][0] = new Pixel('╔', color);
        pixels[0][trueWidth - 1] = new Pixel('╗', color);
        pixels[trueHeight - 1][0] = new Pixel('╚', color);
        pixels[trueHeight - 1][trueWidth - 1] = new Pixel('╝', color);

        //Inner Edges
        pixels[borderHeight - 1][borderWidth - 1] = new Pixel('╔', color);
        pixels[borderHeight - 1][trueWidth - borderWidth] = new Pixel('╗', color);
        pixels[trueHeight - borderHeight][borderWidth - 1] = new Pixel('╚', color);
        pixels[trueHeight - borderHeight][trueWidth - borderWidth] = new Pixel('╝', color);
    }

    public void setPixels(int posX, int posY, char pixel, Colors color) {
        if(insideBorder(posX, posY)) {
            pixels[borderHeight + posY][borderWidth + posX] = new Pixel(pixel, color) ;
        }
    }

    public void setPixels(int posX, int posY, int numPixels, Colors color) {
        char[] nums = ("" + numPixels).toCharArray();
        for (int i = 0; i < nums.length; i++) {
            if(insideBorder(posX + i, posY)) {
                pixels[borderHeight + posY][borderWidth + posX + i] = new Pixel(nums[i], color);
            }
        }
    }

    public void setPixels(int posX, int posY, String stringPixels, Colors color) {
        String[] lines = stringPixels.split("\n");
        for (int i = 0; i < lines.length; i++) {
            char[] chars = lines[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if(insideBorder(posX + j, posY + i)) {
                    pixels[borderHeight + posY + i][borderWidth + posX + j] = new Pixel(chars[j], color);
                }
            }
        }
    }

    public void setPixels(int posX, int posY, char[][] stringPixels, Colors color) {
        for (int i = 0; i < stringPixels.length; i++) {
            for (int j = 0; j < stringPixels[i].length; j++) {
                if(insideBorder(posX + j, posY + i)) {
                    pixels[borderHeight + posY + i][borderWidth + posX + j] = new Pixel(stringPixels[i][j], color);
                }
            }
        }
    }

    public void createHorizontalLine(int posY, int lineSize, Colors color) {
        for (int i = 0; i < lineSize; i++) {
            for (int j = 0; j < width; j++) {
                if(insideBorder(j, posY + i)) {
                    if(i == 0 || i == lineSize - 1) {
                        pixels[borderHeight + i + posY][borderWidth + j] = new Pixel('═', color);
                    } else {
                        pixels[borderHeight + i + posY][borderWidth + j] = new Pixel('░', color);
                    }
                }
            }
        }
    }

    public void createHorizontalLine(int posX, int posY, int lineSize, int lineHeight, Colors color) {
        for (int i = 0; i < lineSize; i++) {
            for (int j = 0; j < lineHeight; j++) {
                if(insideBorder(j, posY + i)) {
                    if(i == 0 || i == lineSize - 1) {
                        pixels[borderHeight + i + posY][borderWidth + j + posX] = new Pixel('═', color);
                    } else {
                        pixels[borderHeight + i + posY][borderWidth + j + posX] = new Pixel('░', color);
                    }
                }
            }
        }
    }

    public void createVerticalLine(int posX, int lineSize, Colors color) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < lineSize; j++) {
                if(insideBorder(posX + j, i)) {
                    if(j == 0 || j == lineSize - 1) {
                        pixels[borderHeight + i][borderWidth + j + posX] = new Pixel('║', color);
                    } else {
                        pixels[borderHeight + i][borderWidth + j +posX] = new Pixel('░', color);
                    }
                }
            }
        }
    }

    public void createVerticalLine(int posX, int posY, int lineSize, int lineHeight, Colors color) {
        for (int i = 0; i < lineHeight; i++) {
            for (int j = 0; j < lineSize; j++) {
                if(insideBorder(posX + j, i)) {
                    if(j == 0 || j == lineSize - 1) {
                        pixels[borderHeight + i + posY][borderWidth + j + posX] = new Pixel('║', color);
                    } else {
                        pixels[borderHeight + i + posY][borderWidth + j +posX] = new Pixel('░', color);
                    }
                }
            }
        }
    }

    public void setTextCursor(int posX, int posY) {
        //Set to beginning
        System.out.print("\033[0;0H"); // move cursor to beginning of text
        //Go down
        System.out.print("\033[" +(borderHeight + posY) + "B");
        //GO forward
        System.out.print("\033[" + (borderWidth + posX) + "C");
    }

    public void renderScreen() {
        for (int i = 0; i < trueHeight; i++) {
            for (int j = 0; j < trueWidth; j++) {

                System.out.print(pixels[i][j].color.getColor() + "" + pixels[i][j].node + "");
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

    public void setColorOfPixel(int posX, int posY, Colors color) {
        pixels[borderHeight + posY][borderWidth + posX].setColor(color);
    }
}
