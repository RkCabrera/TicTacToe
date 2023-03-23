package views;

/**
 * Play TicTacToe the computer that can have different AIs to beat you. 
 * Select the Options menus to begin a new game, switch strategies for 
 * the computer player (BOT or AI), and to switch between the two views.
 * 
 * This class represents an event-driven program with a graphical user 
 * interface as a controller between the view and the model. It has 
 * event handlers to mediate between the view and the model.
 * 
 * This controller employs the Observer design pattern that updates two 
 * views every time the state of the tic tac toe game changes:
 * 
 *    1) whenever you make a move by clicking a button or an area of either view
 *    2) whenever the computer AI makes a move
 *    3) whenever there is a win or a tie
 *    
 * You can also select two different strategies to play against from the menus
 * 
 * @author Rick Mercer
 */


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.IntermediateAI;
import model.OurObserver;
import model.RandomAI;
import model.TicTacToeGame;

/* TicTacToeGUI.java
 * 
 * Author: Ryan Cabrera
 */

public class TicTacToeGUI extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  private TicTacToeGame theGame;

  private MenuBar menuBar;
  private Menu options;
  private Menu strategies;
  private Menu views;
  private MenuItem newGame;
  private MenuItem randomAI;
  private MenuItem intermediateAI;
  private MenuItem buttonMenu;
  private MenuItem textAreaMenu;

  private OurObserver currentView;
  private OurObserver buttonView;
  private OurObserver textAreaView;
//  private OurObserver drawingView;

  private BorderPane window;
  public static final int width = 254;
  public static final int height = 360;

  public void start(Stage stage) {
    stage.setTitle("Tic Tac Toe");
    window = new BorderPane();
    Scene scene = new Scene(window, width, height);
    initializeGameForTheFirstTime();
    createMenus();
    registerHandlers();
    window.setTop(menuBar);
    
    // Set up the views
    buttonView = new ButtonView(theGame);
    textAreaView = new TextAreaView(theGame);
//    drawingView = new DrawingView(theGame);
    theGame.addObserver(buttonView);
    theGame.addObserver(textAreaView);
    theGame.notifyObservers(theGame);
//    theGame.addObserver(drawingView);
    setViewTo(textAreaView);
    //setViewTo(buttonView);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Set the game to the default of an empty board and the random AI.
   */
  public void initializeGameForTheFirstTime() {
    theGame = new TicTacToeGame();
    // This event driven program will always have
    // a computer player who takes the second turn
    theGame.setComputerPlayerStrategy(new RandomAI());
  }

  private void setViewTo(OurObserver newView) {
    window.setCenter(null);
    currentView = newView;
    window.setCenter((Node) currentView);
    //window.setTop(menuBar);
  }
  
  private void createMenus() {
	  // create views menu items
	  buttonMenu = new MenuItem("Button");
	  textAreaMenu = new MenuItem("TextArea");
	  views = new Menu("Views");
	  views.getItems().addAll(buttonMenu, textAreaMenu);
	  
	  // create strategies menu items
	  randomAI = new MenuItem("RandomAI");
	  intermediateAI = new MenuItem("Intermediate");
	  strategies = new Menu("Strategies");
	  strategies.getItems().addAll(randomAI, intermediateAI);
	  
	  // create new game menu item
	  newGame = new MenuItem("New Game");
	  
	  // create options menu. include all menus and items
	  options = new Menu("Options");
	  options.getItems().addAll(newGame, strategies, views);
	  
	  // add to menu bar
	  menuBar = new MenuBar();
	  menuBar.getMenus().addAll(options);
  }
  
  private void registerHandlers() {
	  newGame.setOnAction(new MakeNewGame());
	  randomAI.setOnAction(new SetRandomAI());
	  intermediateAI.setOnAction(new SetIntermediateAI());
	  buttonMenu.setOnAction(new SetButtonView());
	  textAreaMenu.setOnAction(new SetTextAreaView());
  }
  
  private class MakeNewGame implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			theGame.startNewGame();
			theGame.notifyObservers(theGame);
		}
	}
  
  private class SetRandomAI implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			theGame.setComputerPlayerStrategy(new RandomAI());
			theGame.notifyObservers(theGame);
		}
	}
  
  private class SetIntermediateAI implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			theGame.setComputerPlayerStrategy(new IntermediateAI());
			theGame.notifyObservers(theGame);
		}
	}
  
  private class SetButtonView implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			setViewTo(buttonView);
		}
	}
  
  private class SetTextAreaView implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			setViewTo(textAreaView);
		}
	}

}