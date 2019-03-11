package com.example.mando;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Draw extends View {

    private int red, green, blue;

    public Draw(Context context, int red, int green, int blue) {
        super(context);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setARGB(255, this.red, this.green, this.blue);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 100, paint);
    }

}
