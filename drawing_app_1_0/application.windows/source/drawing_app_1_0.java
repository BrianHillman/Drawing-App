import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class drawing_app_1_0 extends PApplet {

//Created by Brian Hillman.
//Tested with processint 1.5.1
//Questions or comments? feel free to email me: BrianJHillman@gmail.com

boolean SHAPE_POINTS_AS_NUMBERS= true;
ShapeInterface[] dataPoints = new ShapeInterface[300];
boolean pointSelected = false;
final int RECT_SIZE = 10;
final int BUTTON_SPACING = 2;
int  pointIndex = -1;
boolean showSquares = true;
ArrayList<MPoint> tempMpoints = new ArrayList<MPoint>();
boolean recPoints = false;
int pointIndexSelected = 0;
boolean isOpen;
public void setup() {
	ellipseMode(RADIUS);
	stroke(50,255);
	noLoop();
	smooth();
	size(600, 400);
	fill(0);
	textSize(16);
	textAlign(LEFT);
	rectMode(CORNERS);
}
//=========================================
// Events
//=========================================
public void mousePressed() {
	for (int x = 0; x < dataPoints.length;x++){
		if(dataPoints[x] !=null  &&  dataPoints[x].isSelected() != -1){
			pointIndexSelected = dataPoints[x].isSelected();
			pointIndex = x;
			pointSelected = true;
			
		}
	}
}public void mouseDragged() {
	if(pointSelected && pointIndex >= 0){
		dataPoints[pointIndex].selected(pointIndexSelected);
	}
}public void mouseReleased() {
	pointSelected = false;
}public void mouseClicked() {
	redraw();
	drawTempShape();
	if(recPoints && mouseX > width-60 && mouseY > height - 25 ){
		for (int xx = 0;xx < dataPoints.length;xx++) {
			if (dataPoints[xx] == null) {
				isOpen = false;
				dataPoints[xx] = new mshape();
				tempMpoints = new ArrayList();
				recPoints = false;
				//  println("4");
				return;
			}
		}
	}
	if(recPoints && mouseX > width-60 && mouseY > height - 55 ){
		for (int xx = 0;xx < dataPoints.length;xx++) {
			if (dataPoints[xx] == null) {
				isOpen = true;

				dataPoints[xx] = new mshape();
				tempMpoints = new ArrayList();
				recPoints = false;
				//  println("4");
				return;
			}
		}
	}
	if(recPoints){
		tempMpoints.add(new MPoint());
		println("Vertex # " + tempMpoints.size() + "  added");
	}else{
		interact();
	}
}public void draw() {
	background(204);
	redrawText();
	drawTempShape();
	for(int x = 0; x < dataPoints.length;x++){
		if(dataPoints[x] == null){
			return;
		}else{
			dataPoints[x].re_draw();
		}
	}
}public void redrawText(){
	fill(50, 25); //grey, alpha
	//  rectMode(CORNERS);
	fill(50, 25);
	rect(1, 20 * 0 + BUTTON_SPACING, 55, 20 * 1 + BUTTON_SPACING);
	rect(1, 20 * 1 + BUTTON_SPACING, 55, 20 * 2 + BUTTON_SPACING);
	rect(1, 20 * 2 + BUTTON_SPACING, 55, 20 * 3 + BUTTON_SPACING);
	rect(1, 20 * 3 + BUTTON_SPACING, 55, 20 * 4 + BUTTON_SPACING);
	rect(1, 20 * 4 + BUTTON_SPACING, 55, 20 * 5 + BUTTON_SPACING);
	rect(1, 20 * 6 + BUTTON_SPACING, 55, 20 * 9 + BUTTON_SPACING);
	rect(1, 20 * 9 + BUTTON_SPACING, 55, 20 * 10 + BUTTON_SPACING);
	rect(1, 20 * 10 + BUTTON_SPACING, 55, 20 * 11 + BUTTON_SPACING);
	rect(1, 20 * 5 + BUTTON_SPACING, 55, 20 * 6 + BUTTON_SPACING);
	if(recPoints)    rect(width-60, height- 25,width-5, height - 5);
	if(recPoints)    rect(width-60, height- 55,width-5, height - 30);

	stroke(50,255);
	fill(50, 255);
	stroke(50,255);
	text("Curve", 5, 20);
	text("Arc", 5, 40);
	text("Bezier", 5, 60);
	text("Line", 5, 80);
	text("Circle", 5, 100);
	text("Guide on/off", 2, 20 * 6 + BUTTON_SPACING, 54, 20 * 9 + BUTTON_SPACING);
	text("Toggle", 1, 20 * 9 + BUTTON_SPACING, 55, 20 * 10 + BUTTON_SPACING);
	text("Shape",1, 20 * 5 + BUTTON_SPACING, 55, 20 * 6 + BUTTON_SPACING);
	text("Print",5, 20 * 10 + BUTTON_SPACING, 55, 20 * 11 + BUTTON_SPACING);
	if(recPoints)    text("Close",width-55, height- 25,width-5, height - 5);
	if(recPoints)    text("Open",width-55, height- 55,width-5, height - 30);

	noFill();
}public void interact() {
	if(dataPoints[dataPoints.length-1] != null){
		println("maximum number of objects has been reached. ");
	}
	// button presses
	if (mouseX > 1 && mouseX < 55 ) {
		if ( false) {
		}else if (mouseY < 20 * 1 + BUTTON_SPACING) {
			for (int xx = 0;xx < dataPoints.length;xx++) {
				if (dataPoints[xx] == null) {
					
					dataPoints[xx] =new Curve();
					dataPoints[xx].drawSquares();
					println("Curve created, a total of "+(xx+1)+" objects have been created");
					return;
				}
			}
		}else if (mouseY < (20) * 2+ BUTTON_SPACING) {
			for (int xx = 0;xx < dataPoints.length;xx++) {
				if (dataPoints[xx] == null) {
					dataPoints[xx] =new Arc();
					dataPoints[xx].drawSquares();
					println("Arc created, a total of "+(xx+1)+" objects have been created");
					return;
				}
			}
		}else if (mouseY < (20) * 3 + BUTTON_SPACING){
			//println("bezier");
			for (int xx = 0;xx < dataPoints.length;xx++) {
				if (dataPoints[xx] == null) {
					dataPoints[xx] =new bezier();
					dataPoints[xx].drawSquares();
					println("bezier created, a total of "+(xx+1)+" objects have been created");
					return;
				}
			}
		}else if (mouseY < (20) * 4 + BUTTON_SPACING){
			for (int xx = 0;xx < dataPoints.length;xx++) {
				if (dataPoints[xx] == null) {
					dataPoints[xx] =new Line();
					dataPoints[xx].drawSquares();
					println("Line created, a total of "+(xx+1)+" objects have been created" );
					return;
				}
			}
			//println("line");
		}else if (mouseY < (20) * 5+ BUTTON_SPACING ){
			//println("Circle");
			for (int xx = 0;xx < dataPoints.length;xx++) {
				if (dataPoints[xx] == null) {
					dataPoints[xx] =new Circle();
					dataPoints[xx].drawSquares();
					println("Line created, a total of "+(xx+1)+" objects have been created" );
					return;
				}
			}
		}else if (mouseY < (20) *6 + BUTTON_SPACING){
			println("Creating a shape, hold the 'c' key while creating a vertex to make it curved\nFor each curved segment(s) the first and last points are used as guide points.\nIf you have fewer than 4 vertices in a curved line segment it will not be visible.\nClick 'Done' to exit the shape creator.");
			recPoints = true;
		}else if (mouseY < (20) * 9+ BUTTON_SPACING ){
			showSquares = !showSquares;
			println("guides = "+ showSquares);
		}else if (mouseY < (20) * 10+ BUTTON_SPACING ){
			if(pointIndex >= 0){
				dataPoints[pointIndex].toggleHelperPoints();
			}
		}else if (mouseY < (20) *11 + BUTTON_SPACING){
			//println("print");
			
			println("rectMode(CORNERS);");
			println("ellipseMode(RADIUS);");
			println("smooth();");
			println("noFill();");
			println("size("+width+", "+height+");");
			
			for (int xx = 0;xx < dataPoints.length;xx++) {
				if (dataPoints[xx] != null) {
					dataPoints[xx].mPrint();
				}else{
					break;
				}
			}
		}
		
	}
	return;
}// a the shape constructors and creation of shapes isnt called until the user has placed all the points
public void drawTempShape(){
	MPoint temp = new MPoint();
	MPoint temp2;
	MPoint temp3;
	if(tempMpoints.size() > 0){
		temp = (MPoint) tempMpoints.get(0);
	}
	redraw();
	beginShape();
	for(int xx = 0;xx < tempMpoints.size(); xx++){
		if(tempMpoints.get(xx).curved){
			curveVertex(tempMpoints.get(xx).x, tempMpoints.get(xx).y);
		}else{
			vertex(tempMpoints.get(xx).x, tempMpoints.get(xx).y);
		}
	}
	endShape();
	//draw helper lines for MShape
	stroke(150,70);
	if(tempMpoints.size() > 1){
		
		for(int x = 0; x < tempMpoints.size() ; x++){
			if(!(tempMpoints.get(x).curved) && tempMpoints.get(min(x+1,tempMpoints.size()-1)).curved){
				line(tempMpoints.get(x).x,tempMpoints.get(x).y,tempMpoints.get(min(x+1,tempMpoints.size()-1)).x,tempMpoints.get(min(x+1,tempMpoints.size()-1)).y);
			}else if(tempMpoints.get(x).curved && !(tempMpoints.get(min(x+1,tempMpoints.size()-1)).curved)){
				line(tempMpoints.get(x).x,tempMpoints.get(x).y,tempMpoints.get(max(x-1,0)).x,tempMpoints.get(max(x-1,0)).y);
			}
		}
		if(tempMpoints.get(0).curved){
			line(tempMpoints.get(0).x,tempMpoints.get(0).y,tempMpoints.get(1).x,tempMpoints.get(1).y);
		}
		if(tempMpoints.get(tempMpoints.size()-1).curved){
			line(tempMpoints.get(tempMpoints.size()-1).x,tempMpoints.get(tempMpoints.size()-1).y,tempMpoints.get(tempMpoints.size()-2).x,tempMpoints.get(tempMpoints.size()-2).y);
		}
	}		
	stroke(50,255);
	//end helper lines
	//redraw();
}///// interface //////
public interface ShapeInterface{
	public int isSelected();
	public void re_draw();
	public void drawSquares();
	public void drawSquareLines();
	public void selected(int pointIndexSelected);
	public void toggleHelperPoints();
	public void mPrint();
}//////// Classes ///////////
public class Line implements ShapeInterface{
	float[] data = { width/3, height/2, (width/3)*2, ( height/2)};
	boolean helperPts = true;
	public Line() {
		line(data[0], data[1], data[2], data[3]);
		loop();
	}
	public int isSelected() {
		if((abs(mouseX-data[0]) < RECT_SIZE && abs(mouseY-data[1]) < RECT_SIZE)){
			return 0;
			
		}else if((abs(mouseX-data[2]) < RECT_SIZE && abs(mouseY-data[3]) < RECT_SIZE)){
			return 1;
			
		}else{
			return -1;
			
		}
		
	}
	public void selected(int index) {
		if (index == 0) {
			data[0] = mouseX;
			data[1] = mouseY;
		}
		else if (index == 1) {
			data[2] = mouseX;
			data[3] = mouseY;
		}
		redraw();
		line(data[0], data[1], data[2], data[3]);
	}
	public void toggleHelperPoints(){
		helperPts = !helperPts;
		
	}
	public void re_draw(){
		line(data[0], data[1], data[2], data[3]);
		if(showSquares && helperPts){
			this.drawSquares();
			this.drawSquareLines();
		}
	}
	public void drawSquares(){
		rectMode(CORNERS);
		for(int x = 0; x < 2; x++){
			rect(data[x*2]-RECT_SIZE/2,data[1+(x*2)]-RECT_SIZE/2,data[x*2]+RECT_SIZE/2,data[1+(x*2)]+RECT_SIZE/2);
		}
	}
	public void drawSquareLines(){
		stroke(50,255);
	}
	public void mPrint(){
		println( "line("+data[0]+", "+data[1]+", "+data[2]+", "+data[3]+");");
	}
}
public class Arc implements ShapeInterface{
	float[] data = { width/2, height/2, width/3, height/3,0,PI/2};
	float[] pointer1 = {height/3,width/3};
	float[] pointer2 = {height/3*2,width/3*2};
	boolean helperPts = true;
	public Arc() {
		arc(data[0], data[1], data[2], data[3], data[4], data[5]);
		redraw();
		//this.selected();  p212
	}
	public int isSelected() {
		if((abs(mouseX-data[0]) < RECT_SIZE && abs(mouseY-data[1]) < RECT_SIZE)){
			return 0;
		}else if((abs(mouseX-data[0]) < RECT_SIZE && abs(mouseY-data[1]-data[3]) < RECT_SIZE)){
			return 1;
		}else if((abs(mouseX-data[0]-data[2])) < RECT_SIZE  && abs(mouseY-data[1]) < RECT_SIZE){
			return 2;
		}else if((abs(mouseX-pointer1[0])< RECT_SIZE &&(mouseY-pointer1[1])< RECT_SIZE)){
			return 3;
		}else if((abs(mouseX-pointer2[0])< RECT_SIZE &&(mouseY-pointer2[1])< RECT_SIZE)){
			return 4;
		}else{
			return -1;
		}
	}
	public void selected(int index) {
		if (index == 0){  //gg
			data[0] = mouseX;
			data[1] = mouseY;
		}else if (index == 3)  {  //gg
			pointer1[0] = mouseX;
			pointer1[1] = mouseY;
		}else if (index == 4)  { //gg
			pointer2[0] = mouseX;
			pointer2[1] = mouseY;
		}else if (index == 1) {
			data[3] = abs(mouseY-data[1]);
		}else if (index == 2 ) {
			data[2] = abs(mouseX-data[0]);
		}
		//recalc angles ( data[3,5])
		//avoids div by 0
		if(pointer1[1] == 0){
			pointer1[1] = .000001f;
		}
		if(pointer2[1] == 0){
			pointer2[1] = .000001f;
		}
		float ratio = data[2]/data[3];
		data[4] = atan( ratio*(pointer1[1]-data[1])/(pointer1[0]-data[0]));
		data[5] = atan( ratio*(pointer2[1]-data[1])/(pointer2[0]-data[0]));
		
		if ((pointer1[0] - data[0]) < 0.0f)
		data[4] += PI;
		if ((pointer2[0] - data[0]) < 0.0f)
		data[5] += PI;
		if (data[5] < data[4])
		data[5] += TWO_PI;
		
		
		
		redraw();
		arc(  data[0], data[1], data[2], data[3], data[4], data[5]  );
	}
	public void re_draw(){
		arc(data[0], data[1], data[2], data[3], data[4], data[5] );
		if(showSquares && helperPts){
			this.drawSquares();
			this.drawSquareLines();
		}
	}
	public void toggleHelperPoints(){
		helperPts = !helperPts;
	}
	public void drawSquares(){
		
		rect(data[0]-RECT_SIZE/2,data[1]-RECT_SIZE/2,data[0]+RECT_SIZE/2,data[1]+RECT_SIZE/2);  //center
		rect(pointer1[0]-RECT_SIZE/2,pointer1[1]-RECT_SIZE/2,pointer1[0]+RECT_SIZE/2,pointer1[1]+RECT_SIZE/2); 	//p1
		rect(pointer2[0]-RECT_SIZE/2,pointer2[1]-RECT_SIZE/2,pointer2[0]+RECT_SIZE/2,pointer2[1]+RECT_SIZE/2);	//p2
		rect((data[0]+data[2])-RECT_SIZE/2,data[1]-RECT_SIZE/2,(data[0]+data[2])+RECT_SIZE/2,data[1]+RECT_SIZE/2);  //width
		rect((data[0])-RECT_SIZE/2,(data[1]+data[3])-RECT_SIZE/2,(data[0])+RECT_SIZE/2,(data[1]+data[3])+RECT_SIZE/2);	//height
		
	}
	public void drawSquareLines(){
		stroke(150,70);
		line(pointer1[0],pointer1[1],data[0],data[1]);
		line(pointer2[0],pointer2[1],data[0],data[1]);
		ellipse(  data[0], data[1], data[2], data[3]  );
		
		stroke(50,255);
	}
	public void mPrint(){
		println( "arc("+data[0]+", "+data[1]+", "+data[2]+", "+data[3]+", "+data[4]+", "+data[5]+" );");
	}
}
public class Curve implements ShapeInterface{
	float[] data = { width/3, height/3, width/3, ( height/3)*2, (width/3)*2, ( height/3)*2, (width/3)*2, ( height/3)};
	boolean helperPts = true;
	public Curve() {
		curve(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
		loop();
	}
	public int isSelected() {
		if((abs(mouseX-data[0]) < RECT_SIZE && abs(mouseY-data[1]) < RECT_SIZE)){
			
			return 0;
		}else if((abs(mouseX-data[2]) < RECT_SIZE && abs(mouseY-data[3]) < RECT_SIZE)){
			return 1;
		}else if((abs(mouseX-data[4]) < RECT_SIZE && abs(mouseY-data[5]) < RECT_SIZE)){
			return 2;
		}else if((abs(mouseX-data[6]) < RECT_SIZE && abs(mouseY-data[7]) < RECT_SIZE)){
			return 3;
		}else{
			return -1;
		}
		
	}
	public void toggleHelperPoints(){
		helperPts = !helperPts;
		
	}
	public void selected(int index) {
		if (index == 0) {
			data[0] = mouseX;
			data[1] = mouseY;
		}
		else if (index == 1) {
			data[2] = mouseX;
			data[3] = mouseY;
		}
		else if (index == 2) {
			data[4] = mouseX;
			data[5] = mouseY;
		}
		else if (index == 3) {
			data[6] = mouseX;
			data[7] = mouseY;
		}
		redraw();
		curve(  data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]  );
	}
	public void re_draw(){
		curve(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]  );
		if(showSquares && helperPts){
			this.drawSquares();
			this.drawSquareLines();
		}
	}
	public void drawSquares(){
		for(int x = 0; x < 4 ; x++){
			rectMode(CORNERS);
			rect(data[x*2]-RECT_SIZE/2,data[1+(x*2)]-RECT_SIZE/2,data[x*2]+RECT_SIZE/2,data[1+(x*2)]+RECT_SIZE/2);
		}
	}
	public void drawSquareLines(){
		stroke(150,70);
		line(data[0],data[1],data[2],data[3]);
		line(data[4],data[5],data[6],data[7]);
		stroke(50,255);
		
	}
	public void mPrint(){
		println("curve("+data[0]+", "+data[1]+", "+data[2]+", "+data[3]+", "+data[4]+", "+data[5]+", "+data[6]+", "+data[7]+"  );");
	}
}
public class bezier implements ShapeInterface{
	boolean helperPts = true;
	float[] data = { width/3, height/3, width/3, ( height/3)*2, (width/3)*2, ( height/3)*2, (width/3)*2, ( height/3)};
	public bezier() {
		bezier(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
		loop();
	}
	public void toggleHelperPoints(){
		helperPts = !helperPts;
		
	}
	public int isSelected() {
		if((abs(mouseX-data[0]) < RECT_SIZE && abs(mouseY-data[1]) < RECT_SIZE)){
			return 0;
		}else if((abs(mouseX-data[2]) < RECT_SIZE && abs(mouseY-data[3]) < RECT_SIZE)){
			return 1;
		}else if((abs(mouseX-data[4]) < RECT_SIZE && abs(mouseY-data[5]) < RECT_SIZE)){
			return 2;
		}else if((abs(mouseX-data[6]) < RECT_SIZE && abs(mouseY-data[7]) < RECT_SIZE)){
			return 3;
		}else{
			return -1;
		}
	}
	public void selected(int index) {
		if (index == 0) {
			data[0] = mouseX;
			data[1] = mouseY;
		}
		else if (index == 1) {
			data[2] = mouseX;
			data[3] = mouseY;
		}
		else if (index == 2) {
			data[4] = mouseX;
			data[5] = mouseY;
		}
		else if (index == 3) {
			data[6] = mouseX;
			data[7] = mouseY;
		}
		redraw();
		bezier(  data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]  );
	}
	public void re_draw(){
		bezier(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]  );
		if(showSquares && helperPts){
			this.drawSquares();
			this.drawSquareLines();
		}
	}
	public void drawSquares(){
		for(int x = 0; x < 4 ; x++){
			rectMode(CORNERS);
			rect(data[x*2]-RECT_SIZE/2,data[1+(x*2)]-RECT_SIZE/2,data[x*2]+RECT_SIZE/2,data[1+(x*2)]+RECT_SIZE/2);
		}
	}
	public void drawSquareLines(){
		stroke(150,70);
		line(data[0],data[1],data[2],data[3]);
		line(data[4],data[5],data[6],data[7]);
		
		
		stroke(50,255);
		
	}
	public void mPrint(){
		println( "bezier("+data[0]+", "+data[1]+", "+data[2]+", "+data[3]+", "+data[4]+", "+data[5]+", "+data[6]+", "+data[7]+"  );");
		
	}
}
public class Circle implements ShapeInterface{
	float[] data = { width/3, height/3, width/3};
	boolean helperPts = true;
	public Circle() {
		ellipse(data[0], data[1], data[2], data[2]);
		loop();
	}
	public int isSelected() {
		if((abs(mouseX-data[0]) < RECT_SIZE && abs(mouseY-data[1]) < RECT_SIZE)){
			return 0;
		}else if(( abs(mouseX-(data[0]+data[2])) < RECT_SIZE && abs(mouseY-data[1]) < RECT_SIZE )){
			return 1;
			
		}else{
			return -1;
		}
	}
	public void selected(int index) {
		if (index == 0) {
			data[0] = mouseX;
			data[1] = mouseY;
		}else if (index == 1) {
			data[2] = abs(data[0] - mouseX);
		}
		redraw();
		ellipse(data[0], data[1], data[2], data[2]);
	}
	public void re_draw(){
		ellipse(data[0], data[1], data[2], data[2]);
		if(showSquares && helperPts){
			this.drawSquares();
			this.drawSquareLines();
		}
	}
	public void toggleHelperPoints(){
		helperPts = !helperPts;
		
	}
	public void drawSquares(){
		rect(data[0]-RECT_SIZE/2,data[1]-RECT_SIZE/2,data[0]+RECT_SIZE/2,data[1]+RECT_SIZE/2);  //center
		rect((data[2]+data[0])-RECT_SIZE/2,data[1]-RECT_SIZE/2,(data[2]+data[0])+RECT_SIZE/2,data[1]+RECT_SIZE/2);  		
	}
	public void drawSquareLines(){
	}
	public void mPrint(){
		println("ellipse("+data[0]+", "+data[1]+", "+data[2]+", "+data[2]+");");
	}
}
public class mshape implements ShapeInterface {
	ArrayList<MPoint> MPoints;
	boolean helperPts = true;
	boolean isOpen1;
	public mshape(){
		MPoints = tempMpoints;
		isOpen1 = isOpen;
		//remove unnecessary points
		int run = 0;
		boolean done = false;
		for(int i = 0; i < MPoints.size();i++){
			if(MPoints.get(i).curved){
				run++;
			}
			if((!MPoints.get(i).curved || MPoints.size()-1 == i  ) && run > 0 && run < 4){ //removes runs of less than 4 curved points
				MPoints.subList(i-run,i).clear();
				i =0;
				println(run);
				run = 0;
			}else if(run > 4 && (!MPoints.get(i).curved || MPoints.size()-1 == i  ))
			run = 0;
			
		}
	}
	
	public int  isSelected(){
		MPoint temp;
		for(int xx = 0;xx < MPoints.size(); xx++){
			temp = (MPoint) MPoints.get(xx);
			if(abs(temp.y-mouseY) < RECT_SIZE && abs(temp.x-mouseX) < RECT_SIZE){
				return xx;
				
			}
		}
		return -1;
	}
	public void re_draw(){
		MPoint temp = new MPoint();
		noFill();
		MPoint temp2;
		if(tempMpoints.size() > 0){
			temp = (MPoint) tempMpoints.get(0);
		}
		beginShape();
		for(int xx = 0;xx < MPoints.size(); xx++){
			temp2 = temp;
			temp = (MPoint) MPoints.get(xx);
			if(temp.curved){
				if((temp2.curved && !temp.curved) || (!temp2.curved && temp.curved) || ( xx == tempMpoints.size()-1 && temp.curved)){
					
					
					stroke(150,70);
					
					// line(temp2.x,temp2.y,temp.x,temp.y);
					stroke(50,255);
				}
				curveVertex(temp.x, temp.y);
			}else{
				
				vertex(temp.x, temp.y);
			}
		}
		if(isOpen1){
			endShape();

		}else{
			endShape(CLOSE);

		}
		endShape(CLOSE);
		if(helperPts && showSquares){
			this.drawSquares();
		}
	}
	public void drawSquares(){
		rectMode(CORNERS);
		MPoint temp;
		for(int xx = 0;xx < MPoints.size(); xx++){
			if(SHAPE_POINTS_AS_NUMBERS){
				text(Integer.toString(xx),MPoints.get(xx).x-RECT_SIZE/2, MPoints.get(xx).y-RECT_SIZE,MPoints.get(xx).x+RECT_SIZE*2, MPoints.get(xx).y+RECT_SIZE*2);
			}else{
				rect(MPoints.get(xx).x-RECT_SIZE/2, MPoints.get(xx).y-RECT_SIZE/2,MPoints.get(xx).x+RECT_SIZE/2, MPoints.get(xx).y+RECT_SIZE/2);
			}
		}
		drawSquareLines();
		return;
	}
	public void drawSquareLines(){
		stroke(150,70);
		if(MPoints.size() > 1){
			
			for(int x = 0; x < MPoints.size() ; x++){
				if(!(MPoints.get(x).curved) && MPoints.get(min(x+1,MPoints.size()-1)).curved){
					line(MPoints.get(x).x,MPoints.get(x).y,MPoints.get(min(x+1,MPoints.size()-1)).x,MPoints.get(min(x+1,MPoints.size()-1)).y);
				}else if(MPoints.get(x).curved && !(MPoints.get(min(x+1,MPoints.size()-1)).curved)){
					line(MPoints.get(x).x,MPoints.get(x).y,MPoints.get(max(x-1,0)).x,MPoints.get(max(x-1,0)).y);
				}
			}
			if(MPoints.get(0).curved){
				line(MPoints.get(0).x,MPoints.get(0).y,MPoints.get(1).x,MPoints.get(1).y);
			}
			if(MPoints.get(MPoints.size()-1).curved){
				line(MPoints.get(MPoints.size()-1).x,MPoints.get(MPoints.size()-1).y,MPoints.get(MPoints.size()-2).x,MPoints.get(MPoints.size()-2).y);
			}
		}
		stroke(50,250);
		
		return;
	}
	public void selected(int index){
		MPoint temp;
		for(int xx = 0;xx < MPoints.size(); xx++){
			temp = (MPoint) MPoints.get(xx);
			if(xx == index){
				MPoints.set(xx,new MPoint(temp.curved));
				redraw();
				return;
			}
		}
		redraw();
	}
	public void toggleHelperPoints(){
		helperPts = !helperPts;
	}
	public void mPrint(){
		MPoint temp;
		println("beginShape();");
		for(int xx = 0;xx < MPoints.size(); xx++){
			temp = (MPoint) MPoints.get(xx);
			if(temp.curved){
				println("curveVertex("+temp.x+", "+temp.y+");");
			}else{
				println("vertex("+temp.x+", "+temp.y+");");
			}
		}
		if(isOpen1){
			println("endShape();");

		}else{
			println("endShape(CLOSE);");
		}
		
	}
}public class MPoint{
	boolean curved;
	float x,y;
	public MPoint(){
		x = mouseX;
		y = mouseY;
		if(key == 'c' && keyPressed ){
			curved = true;
		}else{
			curved = false;
		}
		
	}
	public MPoint(boolean t){
		x = mouseX;
		y = mouseY;
		if(key == 'c' && keyPressed || t){
			curved = true;
		}else{
			curved = false;
		}
	}
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#F0F0F0", "drawing_app_1_0" });
  }
}
