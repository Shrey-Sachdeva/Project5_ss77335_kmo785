package assignment5;
	
import javafx.scene.control.Button;
import javafx.application.Application;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Main extends Application {
	static AnchorPane mainPane = new AnchorPane();
	static GridPane grid = new GridPane();
	@Override
	public void start(Stage primaryStage) {
		try {			
			primaryStage.setTitle("Critter Time :)");
			
			Group group = new Group();
			Button button = new Button("please");
			
			Scene scene = new Scene(mainPane,900,600);
			
			initGrid(grid);
			grid.setManaged(true);
			
			//group.getChildren().add(mainPane);
			AnchorPane.setLeftAnchor(grid, 300.0);
			AnchorPane.setRightAnchor(grid, 0.0);
			AnchorPane.setBottomAnchor(grid, 0.0);
			AnchorPane.setTopAnchor(grid, 0.0);
			mainPane.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			mainPane.getChildren().add(grid);
			//grid.setLayoutX(300);
			//grid.setLayoutY(0);
			
			//group.getChildren().add(grid);
			
			
			//scene.
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			
			
			
			primaryStage.show();
			
			
			
			//Scene myScene = new Scene(,300,300,Color.BLUE);
			//primaryStage.setScene(myScene);
			
		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		System.out.println("launching critters");
		launch(args);
	}
	
	
	public static void initGrid(GridPane grid) {
		for(int c = 0; c < Params.world_width; c++) {
			for(int r = 0; r < Params.world_height; r++) {
				Canvas canvas = new Canvas();
				canvas.setWidth(600.0/(Params.world_width));
				canvas.setHeight(600.0/(Params.world_height));
				GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.setFill(Color.BLACK);
				//gc.fillRect(0, 0, 200/Params.world_width, 200/Params.world_height);
				gc.setFont(new Font("TimesRoman", 1));
				gc.fillText(""+c, 0, 0);
				grid.add(canvas, c, r);
			}
		}
	}
}
