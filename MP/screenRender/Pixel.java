package screenRender;

public class Pixel {
    char node;
    Colors color;

    public Pixel(char node, Colors color) {
        this.node = node;
        this.color = color;
    }

    public char getNode() {
        return node;
    }

    public void setNode(char node) {
        this.node = node;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }
}
