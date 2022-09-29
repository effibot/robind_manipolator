import peasy.*;
import java.io.*;
 private final int NX = 1;
    private final int NY = 1;
    private final int bgColor = color(51,102,102);
    private Robot r;
    private final PeasyCam[] cameras = new PeasyCam[NX * NY];
  private int padding = 10;
void settings() {
        int WIDTH3D = 1366;
        int HEIGHT3D = 768;
        size(WIDTH3D, HEIGHT3D, P3D);
        pixelDensity(1);
        smooth(8);
    }
ArrayList<Float[]> coords;
ArrayList<Float[]> convert(String file){
   // Creating an object of BufferedReader class
   ArrayList<Float[]> list = new ArrayList<>();
        //try(BufferedReader br = new BufferedReader(new FileReader(file))){          
          String[] st = loadStrings(file);
          //while ((st = br.readLine()) != null){
            for(int j = 0; j<st.length;j++){
                 String[] result =st[j].split(",");
                 Float[] bc = new Float[3];
                  for(int i = 0; i< result.length; i++){
                     bc[i] = Float.valueOf(result[i]); 
                   }
                 
                    list.add(bc);
          }
        //} catch(Exception e){
          //e.printStackTrace();
      //}
      return list;
}
void setup(){
    sphereDetail(15);

  //size(1000,1000,P3D);
  String file = "file.txt";
  coords = convert(file);
  rectMode(CENTER);
        r = new Robot();
        //setGradient(0, 0, width, height, c1, c2, Y_AXIS);
        int tilex = floor((width-padding) / NX);
        int tiley = floor((height-padding) / NY);

        // viewport offset ... corrected gap due to floor()
        int offx = (width - (tilex * NX-padding)) / 2;
        int offy = (height - (tiley * NY-padding)) / 2;

        // viewport dimension
        int cw = tilex - padding;
        int ch = tiley - padding;

        // create new viewport for each camera
        for (int y = 0; y < NY; y++) {
            for (int x = 0; x < NX; x++) {
                int id = y * NX + x;
                int cx = offx + x * tilex;
                int cy = offy + y * tiley;
                if(x == 0) cameras[id] = new PeasyCam(this, 300);
                else cameras[id] = new PeasyCam(this, 500);
                cameras[id].setViewport(cx, cy, cw, ch); // this is the key of this whole demo
            }
        }
}
public PeasyCam setupScene(PeasyCam cam,int id) {
        //!! Begin camera setup
        
        int[] viewport = cam.getViewport();
        int w = viewport[2];
        int h = viewport[3];
        int x = viewport[0];
        int y = viewport[1];
        int y_inv = height - y - h; // inverted y-axis
        // scissors-test and viewport transformation
        setGLGraphicsViewport(x, y_inv, w, h);
        // set camera state like spinning a globe
        cam.setYawRotationMode();
        cam.setRightDragHandler(null);
        cam.setCenterDragHandler(null);
        /*switch(id){
            case 0:              
                cam.setMinimumDistance(300);
                cam.setMaximumDistance(1000);
                break;
        }*/
        cam.feed();
        // projection - using camera viewport
        perspective(60 * PI / 180, w / (float) h, 1, 5000);
        // clear background (scissors makes sure we only clear the region we own)
//        background(51, 102, 153);
        background(bgColor);
        // Ruoto il mondo per settare
        // Z > 0 su, X > 0 a destra, Y > 0 in avanti
        scale(1,-1,1);
        rotateX(-PI / 2);
        rotateZ(-PI/2);
        //!! End camera setup
        return cam;
    }
    float th = 0;
void draw(){
  frameRate(100);
   setGLGraphicsViewport(0, 0, width, height);
        background(153,204,153);
        // Aggiungo degli effetti di luce direzionale
        directionalLight(126F, 126F, 126F, (float) -1, (float) 1, (float) -0.7);
        // Aggiungo degli effetti di luce ambientale
        ambientLight(200, 200, 200);
        // setup bg
        for (int i = 0; i < cameras.length; i++) {
            pushStyle();
            pushMatrix();
            switch (i) {
                case 0: draw3Drobot(setupScene(cameras[i],i)); break;
            }
            popMatrix();
            popStyle();
        }
        
}


void keyPressed(){
  switch(key){
    case 'a': px +=1; break;
    case 'z': px -=1;break;
    case 's': py +=1;break;
    case 'x': py -=1;break;
    case 'd': pz +=1;break;
    case 'c': pz -=1;break;
  }
  float[] status = r.getJoint();
  if (key == '1') {
            r.setJoint(0,elbow*status[0]+0.2f);
        }
        if (key == '2') {
            r.setJoint(1,elbow*status[1]+0.2f);
        }
        if (key == '3') {
            r.setJoint(2,elbow*status[2]+0.2f);
        }
        if (key == '4') {
            r.setJoint(3,elbow*status[3]+0.2f);
        }
        if (key == '5') {
            r.setJoint(4,elbow*status[4]+0.2f);
        }
        if (key == '6') {
            r.setJoint(5,elbow*status[5]+0.2f);
        }
        if (key=='q'){
          roll =roll+0.2;
        }
        
        if (key=='w'){
         pitch =pitch+0.2;
        }
        
        if (key=='e'){
          yaw =yaw+0.2;
        }
        if(key == 'm'){
          dx = dx+1;
          println(dx);
        } 
        if (key == 'n'){
          dx = dx-1;
          println(dx);
        }
        if(key == 'e'){
          elbow = -elbow;
        }  
      System.out.format("%f %f %f\n",px,py,pz);
        
}
int box_size = 10;
public void show(float x, float y, float z, boolean show) {
        int length = 100;
        // reference coordinates
        pushMatrix();
        translate(x, y, z);
        float alpha = show ? 255.0f : 0.0f;
        strokeWeight(3);
        // x-axis
        stroke(255,0,0,alpha);
        line(0,0,0,length,0,0);
        // y-axis
        stroke(0,255,0,alpha);
        line(0,0,0,0,length,0);
        // z-axis
        stroke(0,0,255,alpha);
        line(0, 0, 0, 0, 0,length);
        // show

        if (show) {
            strokeWeight(1);
            stroke(0);
            fill(153,204,204);
            box(box_size);
            noFill();
        }
        noStroke();
        popMatrix();

    }
     float roll = -PI/4;
     float pitch = PI/3;
     float yaw = PI/4;
     //     float roll =0;
     //float pitch = 0;
     //float yaw = 0;
     float px= 0;
float py= 0f;
float pz = 0f;
int dx = 0;
Ellipsoid shape;
int elbow = 1;
public void draw3Drobot(PeasyCam cam){
        //show(0,0,0,true);
        //th = th+0.02;
        //pushMatrix();
        //rotateY(th);
        //show(0,0,0,true);
        //popMatrix();
         
        fill(255);
        stroke(0);
        strokeWeight(3);
        translate(0,0,-100);
        /*pushMatrix();
        
        //show(0,0,96+dx,true);    
        rotateZ(roll);
        rotateY(pitch);
        rotateZ(yaw);
        //show(0,0,0,true);
        //box(5);
        //show(0,0,63,true);
        popMatrix();
        noStroke();                
        //translate(0,0,-32);
        Float[] val = coords.get(dx+67);
          //r.setDhTable(r.inverseKinematics(val[0],val[1],val[2],roll,pitch,yaw,elbow));
          
        pushMatrix();/*
        /*for(int i = 0; i < coords.size(); i++){
          pushMatrix();
          Float[] bc = coords.get(i);
          translate(bc[0],bc[1],bc[2]);
       stroke(255,50);
        strokeWeight(1);
        noFill();
          sphere(12);
          popMatrix();
        }*/
         r.drawLink();
         //show(0,0,0,true);
         
        //popMatrix();
       /*pushMatrix();

        translate(0.0, 0.0, 33);
        //fill(color(20,200,150));
        stroke(0);
        strokeWeight(2);*/
        //sphere(abs(a2 - d4));
        //sphere(50+51);
        //shape = new Ellipsoid(50+51, 50+51, 50+51, 24, 12);
        //shape.fill(color(1,1,1, 0.5));
        //shape.drawMode(S3D.WIRE);
        //shape.draw(getGraphics());
        //popMatrix();
        cam.beginHUD();


        cam.endHUD();
    }
public void setGLGraphicsViewport(int x, int y, int w, int h) {
    PGraphics3D pg = (PGraphics3D) this.g;
    PJOGL pgl = (PJOGL) pg.beginPGL();
    pg.endPGL();
    pgl.enable(PGL.SCISSOR_TEST);
    pgl.scissor(x, y, w, h);
    pgl.viewport(x, y, w, h);
}  

