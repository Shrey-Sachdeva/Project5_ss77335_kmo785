package assignment5;
	
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import assignment5.Critter;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.scene.control.ToggleButton;
public class Main extends Application {
	static AnchorPane mainPane = new AnchorPane();
	static GridPane grid;
	static Canvas[][] canvases;
	static ChoiceBox makeCritterChoiceBox; 
	static TextField stepTextField;
	static TextField seedTextField;
	static TextField makeTextField;
	static TextField animateTextField;
	static TextField makeCritterChoiceTextField;
	static TextArea statsTextArea;
	static Button makeButton;
	static Button stepButton;
	static Button seedButton;
	static Button statsButton;
	static ToggleButton animateButton;
	static Timer timer;
	static boolean animating = false;
	static TimerTask task;
	
	@Override
	public void start(Stage primaryStage) {
		try {			
			
			initLayout(primaryStage);
			initButtonActions();
			
			primaryStage.show();
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		System.out.println("launching critters");
		launch(args);
	}
	
	
	public static int getMakeNumber() {
		String text = makeTextField.getText();
		try {
			int number = Integer.parseInt(text);
			return number;
		}catch(NumberFormatException e) {
			return -1;
		}
		
	}
	public static int getStepNumber() {
		String text = stepTextField.getText();
		try {
			int number = Integer.parseInt(text);
			return number;
		}catch(NumberFormatException e) {
			return -1;
		}
		
		
	}
	public static int getAnimationSpeed() {
		String text = animateTextField.getText();
		double scale = 1000;
		try {
			int number = Integer.parseInt(text);
			return (int) (scale/number);
		}catch(NumberFormatException e) {
			return (int) scale;
		}
	}
	public static int getSeedFromText() {
		String text = seedTextField.getText();
		try {
			int number = Integer.parseInt(text);
			return number;
		}catch(NumberFormatException e) {
			return -1;
		}
		
	}
	public static void initButtonActions() {
		makeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if(animating) {
					return;
				}
				String choice = makeCritterChoiceTextField.getText();
				choice = "assignment5."+choice;
				if(isValidClass(choice)) {
					try {
						/**TODO Handle making multiple critters from one button press!**/
						int number = getMakeNumber();
	        			if(number > 0) {//if there is a specified number of critters to make
	        				while(number > 0) {//make that number of critters
	        					Critter.makeCritter(choice);
	        					number--;
	        				}
	        			}else {
	        				Critter.makeCritter(choice);
	        			}
						Critter.displayWorld();
						Critter.displayWorld(grid);
					} catch (InvalidCritterException e) {
						
						statsTextArea.setText("Invalid critter exception!");
					}
				}else {
					statsTextArea.setText("Invalid critter choice!");
				}
				
			}
		});
		stepButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(animating) {
					return;
				}
				/**TODO Handle MANY world time steps at a time!**/
				int number = getStepNumber();
    			if(number > 0) {//if there is a specified number of critters to make
    				while(number > 0) {//make that number of critters
    					Critter.worldTimeStep();
    					number--;
    				}
    			}else {
    				Critter.worldTimeStep();
    			}
				Critter.displayWorld();
				Critter.displayWorld(grid);
				
			}
		});
		animateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(animateButton.getText().equals("Animate")) {
					animateButton.setText("Stop");
					int speed = getAnimationSpeed();
					timer = new Timer();
					//Critter.displayWorld();
					task = new TimerTask() {
						
						private boolean ready = true;
						public void run() {
							if(ready) {
								ready = false;
								Critter.worldTimeStep();
								try {
									Critter.displayWorld(grid);
								}catch(NullPointerException e){
									System.out.println("Caught null pointer in display world");
									timer.cancel();
									statsTextArea.setText("Animation was too fast, buffer overflow caused loss of canvas pointer");
								}
								ready = true;
							}
						}
					};
					setDisableInputs(true);
					timer.schedule(task,200, speed);
					
					
					animating = true;
				}else {
					
					animateButton.setText("Animate");
					animateButton.disarm();
					timer.cancel();
					animating = false;
					setDisableInputs(false);
				}
				
			}
		});
		
		seedButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				int seed = getSeedFromText();
				if(seed >= 0) {
					Critter.setSeed(seed);
				}
			}
		});
	}
	
	
	/**
	 * Used during animation to disable inputs
	 */
	public static void setDisableInputs(boolean value) {
		makeButton.setDisable(value);
		stepButton.setDisable(value);
		seedButton.setDisable(value);
		statsButton.setDisable(value);
		makeTextField.setDisable(value);
		stepTextField.setDisable(value);
		animateTextField.setDisable(value);
		makeCritterChoiceTextField.setDisable(value);
		seedTextField.setDisable(value);
	}
	
	
	public static void initLayout(Stage primaryStage) {
		primaryStage.setTitle("Critter Time :)");
		Scene scene = new Scene(mainPane,900,600);
		


		//GRID INITIALIZATION
		grid = new GridPane();
		initGrid(grid);
		grid.setManaged(true);
		AnchorPane.setLeftAnchor(grid, 300.0);
		AnchorPane.setRightAnchor(grid, 0.0);
		AnchorPane.setBottomAnchor(grid, 0.0);
		AnchorPane.setTopAnchor(grid, 0.0);
		mainPane.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		mainPane.getChildren().add(grid);
		grid.setGridLinesVisible(true);
		
		//BUTTONS INITIALIZATION
		makeButton = new Button("Make");
		stepButton = new Button("Step");
		seedButton = new Button("Seed");
		statsButton = new Button("Stats");
		animateButton = new ToggleButton("Animate");
		
		//Make:
		setAnchor(makeButton,10,10,60,20);
		mainPane.getChildren().add(makeButton);
		//Step:
		setAnchor(stepButton,10,50,60,20);
		mainPane.getChildren().add(stepButton);
		//Seed:
		setAnchor(seedButton,10,130,60,20);
		mainPane.getChildren().add(seedButton);
		//Run Stats:
		setAnchor(statsButton,10,170,60,20);
		mainPane.getChildren().add(statsButton);
		//Animate:
		setAnchor(animateButton,10, 90, 60, 20);
		mainPane.getChildren().add(animateButton);
		
		
		//DROP DOWN MENUS:
		/*makeCritterChoiceBox = new ChoiceBox((ObservableList) getPossibleCrittersToMake());
		setAnchor(makeCritterChoiceBox, 90, 10, 100, 20);
		mainPane.getChildren().add(makeCritterChoiceBox);*/
		makeCritterChoiceTextField = new TextField();
		setAnchor(makeCritterChoiceTextField, 90, 10, 100, 20);
		mainPane.getChildren().add(makeCritterChoiceTextField);
		
		//Step Text Field:
		stepTextField = new TextField();
		setAnchor(stepTextField, 90, 50, 100, 20);
		mainPane.getChildren().add(stepTextField);
		stepTextField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            stepTextField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		stepTextField.setPromptText("How many steps?");
		//Seed text field
		seedTextField = new TextField();
		setAnchor(seedTextField, 90, 130, 100, 20);
		seedTextField.setPromptText("Random seed");
		mainPane.getChildren().add(seedTextField);
		seedTextField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            seedTextField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		//Make text field:
		makeTextField = new TextField();
		makeTextField.setPromptText("How many?");
		setAnchor(makeTextField, 210, 10, 70, 20);
		mainPane.getChildren().add(makeTextField);
		makeTextField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            makeTextField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		//Animate text field:
		animateTextField = new TextField();
		animateTextField.setPromptText("Animation speed");
		setAnchor(animateTextField, 90, 90, 100, 20);
		mainPane.getChildren().add(animateTextField);
		animateTextField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	animateTextField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		//Stats text area
		statsTextArea = new TextArea();
		setAnchor(statsTextArea, 5, 200, 280, 290);
		mainPane.getChildren().add(statsTextArea);
		statsTextArea.setEditable(false);
		
		
		//MISC STAGE INITIALIZATION
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		
	}
	
	public static void setAnchor(Node child, double x, double y, double width, double height) {
		AnchorPane.setLeftAnchor(child, x);
		AnchorPane.setRightAnchor(child,900-x-width);
		AnchorPane.setBottomAnchor(child, 600-y-height);
		AnchorPane.setTopAnchor(child, y);
	}
	
	public static ObservableList getPossibleCrittersToMake(){
		//FXCollections.ObservableList<String> choices;
		//choices.add("Blupe");
		//choices.add("Shrey");
		//choices.add("Kylar");
		return FXCollections.observableArrayList("First", "Second", "Third");
	}
	
	public static void initGrid(GridPane grid) {
		canvases = new Canvas[Params.world_height][Params.world_width];
		for(int c = 0; c < Params.world_width; c++) {
			for(int r = 0; r < Params.world_height; r++) {
				Canvas canvas = new Canvas();
				canvas.setWidth(600.0/(Params.world_width));
				canvas.setHeight(600.0/(Params.world_height));
				grid.add(canvas, c, r);
				canvases[r][c] = canvas;
				GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.setFill(Color.WHITE);
				gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			}
		}
	}
	
	/**
     * Checks to see if a class name is a valid class
     * @param className
     * @return true if the class name is valid
     */
    private static boolean isValidClass(String className) {
    	Class c;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
    }
}
