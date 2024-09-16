package com.graphics.snake.wrapper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.graphics.snake.core.Map;

public class Animation {
    // 1 //
    public Map map = new Map();
    public int length = map.length;
    public int mult,brightBubbles,brightSnake,greenBubbles,redSnake;
    public int width,height;
    // 2 //
    com.graphics.snake.core.bubbles.Point[] pointBubbles = map.bubbles.points;
    com.graphics.snake.core.snake.PointOptimization[] pointHead = map.snake.pointsHead;
    com.graphics.snake.core.snake.PointOptimization[] pointMotors = map.snake.pointsMotors;
    com.graphics.snake.core.snake.PointOptimization[] pointBody = map.snake.pointsBody;
    com.graphics.snake.core.border.Point[] pointBorder = map.border.points;
    // 3 //
    com.graphics.snake.core.bubbles.Generator generatorBubbles = map.bubbles.generator;

    public Animation(){ reset(); }

    public void reset(){
        map.reset();
        mult = 4;
        width  = Map.width*mult;
        height = Map.height*mult;
        brightBubbles = 2;
        brightSnake   = 15;
        greenBubbles = 0;
        redSnake = 0;
    }

    public int border(int value){
        if(value>255){ value = 255; }
        return value;
    }

    public void process(Canvas canvas, Paint paint,Panel panel){
        map.process(); //
        ////////////////
        Bitmap bitmap = Bitmap.createBitmap(Map.width,Map.height, Bitmap.Config.RGB_565); //
        ///////////////////////////////////////////////////////////////////////////////////

        if(map.bubbles.death()){ greenBubbles = 5; }
        if(greenBubbles>0){ greenBubbles --; }
        if(map.snake.death()){ redSnake = 5; }

        for(int i = 0;i<length;i++){

            int bubbles = pointBubbles[i].getValue();
            int head = pointHead[i].getValue();
            int motors = pointMotors[i].getValue();
            int body = pointBody[i].getValue();
            boolean border = pointBorder[i].border;

            int x = pointBubbles[i].x;
            int y = pointBubbles[i].y;
            ////////////////////////////ОПРЕДЕЛЕНИЕ ЦВЕТА//////////////////////////
            bubbles = border(bubbles * brightBubbles);
            head = border(head * brightSnake);
            motors = border(motors * brightSnake);
            body = border(body * brightSnake);

            if(border){
                bitmap.setPixel(x,y,Color.argb(255,255,0,0));
            }else
            if(head>0||motors>0||body>0){
                if(redSnake >0){
                    bitmap.setPixel(x,y,Color.argb(255,100,0,0));
                }else{
                    bitmap.setPixel(x,y,Color.argb(255,head,motors,body));
                }

            }else
            if(bubbles>0) {
                int mass = border(generatorBubbles.getMassOfType(pointBubbles[i]));
                if (greenBubbles > 0) {
                    bitmap.setPixel(x, y, Color.argb(255, 0, 255, 0));
                } else if (greenBubbles == 0) {
                    bitmap.setPixel(x, y, Color.argb(255, bubbles, mass, mass));
                }
            }else{
                bitmap.setPixel(x, y, Color.argb(255,0,0,0));
            }

            //g.setColor(new Color(head,0,0));
            //g.setColor(new Color(motors,0,0));
            //g.setColor(new Color(body,0,0));

            ///////////////////////////////////////////////////////////////////////
        }

        Bitmap bitmapMult = Bitmap.createScaledBitmap(bitmap,panel.getWidth(),panel.getWidth(), false);
        canvas.drawBitmap(bitmapMult,0,0,paint);

    }
}
