package com.graphics.snake.core.snake;

public class Generator {
    public int lengthPoints,lengthTypes = 20;
    ////////////////////////////////////////////////////////
    public PointOptimization[] pointsHead;

    public int[] massOfTypesHead = new int[lengthTypes];
    public int powerHead = 8000;
    public int[] generatorHead = new int[lengthTypes];

    public int[] massOfTypesHeadPlus = new int[lengthTypes];
    public int powerHeadPlus = 400;
    public int[] generatorHeadPlus = new int[lengthTypes];
    ////////////////////////////////////////////////////////

    public PointOptimization[] pointsMotors;
    public int[] massOfTypesMotors   = new int[lengthTypes];
    public int powerMotors = 20;
    public int[] generatorMotors   = new int[lengthTypes];

    public Generator(PointOptimization[] pointsHead,PointOptimization[] pointsMotors){
        this.pointsHead = pointsHead;
        this.pointsMotors = pointsMotors;

        this.lengthPoints = pointsMotors.length;
    }

    public void process(){
        // сброс данных
        for(int i = 0;i<lengthTypes;i++){
            massOfTypesHeadPlus[i] = 0;
            generatorHeadPlus[i] = 0;

            massOfTypesHead[i] = 0;
            generatorHead[i] = 0;
            /////////////////////
            massOfTypesMotors[i] = 0;
            generatorMotors[i] = 0;
        }

        // сбор данных
        for(int i = 0;i<lengthPoints;i++){
            if(pointsHead[i].getValue()>100){
                massOfTypesHead[pointsHead[i].getType()] = massOfTypesHead[pointsHead[i].getType()] + 1;
            }

            if(pointsHead[i].getValue()>50&&pointsMotors[i].getValue()==0){
                massOfTypesHeadPlus[pointsHead[i].getType()] = massOfTypesHeadPlus[pointsHead[i].getType()] + 1;
            }

            /////////////////////
            if(pointsHead[i].getValue()>0&&pointsMotors[i].getValue()>0){
                massOfTypesMotors[pointsMotors[i].getType()] += 1;
            }
        }

        // расчёт на основе данных
        for(int i = 0;i<lengthTypes;i++){
            if(massOfTypesHead[i]!=0){
                generatorHead[i] = powerHead/massOfTypesHead[i];
            }
            if(massOfTypesHeadPlus[i]!=0){
                generatorHeadPlus[i] = powerHeadPlus/massOfTypesHeadPlus[i];
            }
            //////////////////////////////////
            if(massOfTypesMotors[i]!=0){
                generatorMotors[i] = powerMotors/massOfTypesMotors[i];
            }
        }

        // применение расчёта
        for(int i = 0;i<lengthPoints;i++){
            if(pointsHead[i].getValue()>100){
                pointsHead[i].addValue(generatorHead[pointsHead[i].getType()] );
            }

            if(pointsHead[i].getValue()>50&&pointsMotors[i].getValue()==0){
                pointsHead[i].addValue(generatorHeadPlus[pointsHead[i].getType()] );
            }

            ///////////
            if(pointsHead[i].getValue()>0&&pointsMotors[i].getValue()>0){
                pointsMotors[i].addValue(generatorMotors[pointsMotors[i].getType()] );
            }

        }

    }
}
