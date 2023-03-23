package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.OurObserver;
import model.TicTacToeGame;

/* TextAreaView.java
 * 
 * Author: Ryan Cabrera
 */

public class TextAreaView extends BorderPane implements OurObserver {

  private TicTacToeGame theGame;
  private TextField rowTF = new TextField();
  private TextField colTF = new TextField();
  private Button moveButton = new Button("Make Move");
  private TextArea message = new TextArea();
  
 
  public TextAreaView(TicTacToeGame theModel) {
    theGame = theModel;
    initializePanel();
  }

  private void initializePanel() {
    registerHandlers();
    //message.setStyle("-fx-text-alignment: right; border-width: 10;");
    
    rowTF.setMaxWidth(60);
    colTF.setMaxWidth(60);
    
    
    Font font = new Font("Courier New", 28);
    message.setFont(font);
    message.setText(theGame.toString());
    message.setMaxHeight(180);
    message.setMouseTransparent(true);
    message.setEditable(false);
    this.setBottom(message);
    
    GridPane topPane = new GridPane();
    GridPane inner = new GridPane();
    inner.add(moveButton, 0, 0);
    
    topPane.setHgap(10);
    topPane.setVgap(10);
    topPane.add(rowTF, 6, 4);
    topPane.add(new Label("row"), 7, 4);
    
    topPane.add(colTF, 6, 5);
    topPane.add(new Label("col"), 7, 5);
    topPane.add(inner, 6, 6);
    
    
    setCenter(topPane);
    //this.setBottom(new Button("Bottom"));
  }
 
  // This method is called by Observable's notifyObservers()
  @Override
  public void update(Object observable) {
	//System.out.println("yo here");
    moveButton.setText("Make Move");
    message.setText(theGame.toString());
    
    if (!theGame.stillRunning()) {
    	if (theGame.didWin('O')) {
    		moveButton.setText("O wins");
    	}
    	if (theGame.didWin('X')) {
    		moveButton.setText("X wins");
    	}
    	else if (theGame.tied()) {
    		moveButton.setText("Tie");
    	}
    }
  }
  
  private void registerHandlers() {
	  moveButton.setOnAction(new DoMove());
  }
  
  private class DoMove implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		//System.out.println("handled");
		int row = Integer.parseInt(rowTF.getText().trim());
		int col = Integer.parseInt(colTF.getText().trim());
		if (col > 2 || col < 0 || row > 2 || row < 0 || !theGame.available(row, col)) {
			moveButton.setText("Invalid Choice");
		}
		else {
			theGame.humanMove(row, col, false);
			theGame.notifyObservers(theGame);
		}
		
	}
	  
  }
}