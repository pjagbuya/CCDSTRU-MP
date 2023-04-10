package models;

import colorPrint.Paint;

public enum Winner {
    // Modified to add some color
    PLAYER_A(Paint.paintTextCyan("A wins")),
    PLAYER_B(Paint.paintTextOrange("B wins")),
    NONE("No winner");

    private String response;

    Winner(String response) {
        this.response = response;
    }

    public String getResponse() {

        return response;
    }
}
