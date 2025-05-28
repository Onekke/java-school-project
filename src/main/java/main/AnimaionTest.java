package main;

import entity.SpriteAnimation;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimaionTest extends Application {

    private static final Image IMAGE = new Image(AnimaionTest.class.getResource("/game_assets/sprites/character/chicken2_left.png").toExternalForm());

//  butterfly3.png
//    private static final int COLUMNS  =  12;
//    private static final int COUNT    =  34;
//    private static final int OFFSET_X =  10;
//    private static final int OFFSET_Y =  20;
//    private static final int WIDTH    = 70;
//    private static final int HEIGHT   = 60;

//    cat2_left.png
//    private static final int COLUMNS  =  1;
//    private static final int COUNT    =  12;
//    private static final int OFFSET_X =  10;
//    private static final int OFFSET_Y =  0;
//    private static final int WIDTH    = 400;
//    private static final int HEIGHT   = 200;

//    cat_fat.png
//    private static final int COLUMNS  =  3;
//    private static final int COUNT    =  6;
//    private static final int OFFSET_X =  0;
//    private static final int OFFSET_Y =  15;
//    private static final int WIDTH    = 200;
//    private static final int HEIGHT   = 120;

//    cat3.png
//    private static final int COLUMNS  =  2;
//    private static final int COUNT    =  8;
//    private static final int OFFSET_X =  15;
//    private static final int OFFSET_Y =  55;
//    private static final int WIDTH    = 400;
//    private static final int HEIGHT   = 220;

//    cat1.png
//    private static final int COLUMNS  =  4;
//    private static final int COUNT    =  8;
//    private static final int OFFSET_X =  40;
//    private static final int OFFSET_Y =  30;
//    private static final int WIDTH    = 450;
//    private static final int HEIGHT   = 300;

//    chicken1-6.png
    private static final int COLUMNS  =   6;       // 6
    private static final int COUNT    =  47;       // 24, 47, 40, 67, 86, 32
    private static final int OFFSET_X =  45;
    private static final int OFFSET_Y =  5;       // 30, 5, 30
    private static final int WIDTH    = 148;
    private static final int HEIGHT   = 110;

//    butterfly2.png
//    private static final int COLUMNS  =   14;
//    private static final int COUNT    =  84;
//    private static final int OFFSET_X =  10;
//    private static final int OFFSET_Y =  20;
//    private static final int WIDTH    = 70;
//    private static final int HEIGHT   = 65;

//    butterfly.png
//    private static final int COLUMNS  =   5;
//    private static final int COUNT    =  10;
//    private static final int OFFSET_X =  -40;
//    private static final int OFFSET_Y =  -20;
//    private static final int WIDTH    = 220;
//    private static final int HEIGHT   = 200;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Horse in Motion");

        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1600),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        primaryStage.setScene(new Scene(new Group(imageView)));
        primaryStage.show();
    }
}
