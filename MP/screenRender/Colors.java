package screenRender;

public enum Colors {
    CYAN("\033[1;36m"),
    ORANGE("\033[1;38;5;202m"),
    RED("\033[1;31m"),
    YELLOW("\033[1;33m"),
    GREEN("\033[1;32m"),
    OFF_COLOR("\033[0m");

    private String color;
    Colors(String color) {
      this.color = color;
    };

    public String getColor() {
        return color;
    }
}
