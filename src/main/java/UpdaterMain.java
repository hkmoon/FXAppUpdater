import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

/**
 * UpdaterMain provides GUI for waiting...
 */
public class UpdaterMain extends Application
{

	@Override public void start( Stage primaryStage ) throws Exception
	{
		VBox loading = new VBox(20);
		loading.setMaxWidth( Region.USE_PREF_SIZE );
		loading.setMaxHeight( Region.USE_PREF_SIZE );
		loading.getChildren().add(new ProgressBar());
		loading.getChildren().add(new Label("Please wait ..."));

		BorderPane root = new BorderPane(loading);
		Scene scene = new Scene(root);

		primaryStage.getIcons().add( new Image( this.getClass().getResourceAsStream( "icon.png" ) ) );
		primaryStage.setTitle( "Updater" );
		primaryStage.setScene( scene );
		primaryStage.setWidth( 300 );
		primaryStage.setHeight( 150 );
		primaryStage.show();

		// test the look and feel with both Caspian and Modena
		Application.setUserAgentStylesheet( Application.STYLESHEET_MODENA );

        Task task = new Task<Void>() {
            @Override public Void call() {
                final File config = new File( System.getProperty("user.dir") + "/" + Updater.configFile );

                if( config.exists() )
                {
                    new Updater( config ).parseAndUpdate();
                }
                else
                {
                    throw new RuntimeException( "No config file is provided." );
                }
                return null;
            }
        };

        new Thread(task).start();
	}

    public static void main( String[] args )
    {
        launch( args );
    }
}
