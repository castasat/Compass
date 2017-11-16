package com.openyogaland.denis.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Compass extends AppCompatActivity implements SensorEventListener
{
  // Картинка для компаса
  private ImageView HeaderImage;
  // Угол попворота компаса
  private float rotateDegree = 0f;
  // Менеджер работы с сенсором
  private SensorManager mySensorManager;
  // Текст с отображением отклонения
  private TextView Orientation;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compass);
  
    // Связываем картинку для компаса с изображением
    HeaderImage = (ImageView) findViewById(R.id.CompassView);
    // Связываем текст с отображением отклонения
    Orientation = (TextView) findViewById(R.id.Header);
    
    // инициализация работы с сенсором
    mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    // устанавливаем слушателя ориентации сенсора
    mySensorManager.registerListener(this, mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
  }
  
  @Override
  protected void onPause()
  {
    super.onPause();
    // останавливаем слушателя ориентации сервера
    mySensorManager.unregisterListener(this);
  }
  
  @Override
  public void onSensorChanged(SensorEvent event)
  {
    // получаем отклонение в градусах от севера
    float degree = Math.round(event.values[0]);
    Orientation.setText("Отклонение от севера: " + Float.toString(degree));
    
    // создаём анимацию вращения
    RotateAnimation rotateAnimation = new RotateAnimation(rotateDegree, -degree,
                                                          Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    // продолжительность анимации в миллисекундах
    rotateAnimation.setDuration(200);
    rotateAnimation.setFillAfter(true);
    
    // запускаем анимацию
    HeaderImage.startAnimation(rotateAnimation);
    rotateDegree = -degree;
  }
  
  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy)
  {
    // не используется
  }
}
