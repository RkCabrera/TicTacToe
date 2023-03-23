package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.OurObserver;
import model.TicTacToeGame;

/* ButtonView.java
 * 
 * Author: Ryan Cabrera
 */

public class ButtonView extends BorderPane implements OurObserver {

	private TicTacToeGame theGame;
	private Button[][] buttons = new Button[3][3];
	private Label label = new Label("Click to make a move");
	
	public ButtonView(TicTacToeGame theModel) {
		theGame = theModel;
		buttons[0][0] = new Button(" ");
		buttons[0][1] = new Button("_");
		buttons[0][2] = new Button("_");
		buttons[1][0] = new Button("_");
		buttons[1][1] = new Button("_");
		buttons[1][2] = new Button("_");
		buttons[2][0] = new Button("_");
		buttons[2][1] = new Button("_");
		buttons[2][2] = new Button("_");
		initializePanel();
	}
	
	private void initializePanel() {
		registerHandlers();
		Font labelFont = new Font("Courier New", 18);
		label.setFont(labelFont);
		GridPane labelPane = new GridPane();
		labelPane.setHgap(20);
		labelPane.setVgap(20);
		labelPane.add(label, 1, 1);
		labelPane.add(new Label(""), 0, 2);
		
		Font font = new Font("Courier New", 28);
		buttons[0][0].setFont(font);
		buttons[0][1].setFont(font);
		buttons[0][2].setFont(font);
		buttons[1][0].setFont(font);
		buttons[1][1].setFont(font);
		buttons[1][2].setFont(font);
		buttons[2][0].setFont(font);
		buttons[2][1].setFont(font);
		buttons[2][2].setFont(font);
		
		GridPane outer = new GridPane();
		outer.setHgap(40);
		outer.setVgap(60);
		GridPane pane = new GridPane();
		pane.setHgap(10);
		pane.setVgap(10);
		
		
		pane.add(buttons[0][0], 0, 0);
		pane.add(buttons[0][1], 1, 0);
		pane.add(buttons[0][2], 2, 0);
		pane.add(buttons[1][0], 0, 1);
		pane.add(buttons[1][1], 1, 1);
		pane.add(buttons[1][2], 2, 1);
		pane.add(buttons[2][0], 0, 2);
		pane.add(buttons[2][1], 1, 2);
		pane.add(buttons[2][2], 2, 2);
		
		outer.add(pane, 1, 1);
		
		setCenter(outer);
		setBottom(labelPane);
	}
	
	
	
	@Override
	public void update(Object theObserved) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setText(String.valueOf(theGame.getTicTacToeBoard()[i][j]));
			}
		}
		
		if (!theGame.stillRunning()) {
			if (theGame.didWin('O')) {
	    		label.setText("O wins");
	    	}
	    	if (theGame.didWin('X')) {
	    		label.setText("X wins");
	    	}
	    	else if (theGame.tied()) {
	    		label.setText("Tie");
	    	}
		}
		
	}
	
	private void registerHandlers() {
		buttons[0][0].setOnAction(new ButtonPressed());
		buttons[0][1].setOnAction(new ButtonPressed());
		buttons[0][2].setOnAction(new ButtonPressed());
		buttons[1][0].setOnAction(new ButtonPressed());
		buttons[1][1].setOnAction(new ButtonPressed());
		buttons[1][2].setOnAction(new ButtonPressed());
		buttons[2][0].setOnAction(new ButtonPressed());
		buttons[2][1].setOnAction(new ButtonPressed());
		buttons[2][2].setOnAction(new ButtonPressed());
	}
	
	private class ButtonPressed implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Button buttonClicked = (Button) event.getSource();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (buttons[i][j] == buttonClicked) {
						theGame.humanMove(i, j, false);
						theGame.notifyObservers(theGame);
					}
				}
			}
		}
	}
	
}
