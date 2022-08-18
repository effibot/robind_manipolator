import peasy.*;

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
void setup(){
  //size(1000,1000,P3D);
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
        switch(id){
            case 0:              
                cam.setMinimumDistance(300);
                cam.setMaximumDistance(1000);
                break;
        }
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
void draw(){
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

float px= 30;
float py= -50;
float pz = 30;
int dx = 0;
void keyPressed(){
  switch(key){
    case 'a': px +=1; break;
    case 'z': px -=1;break;
    case 's': py +=1;break;
    case 'x': py -=1;break;
    case 'd': pz +=1;break;
    case 'c': pz -=1;break;
  }
  if (key == '1') {
            r.setJoint(0,0.2f);
        }
        if (key == '2') {
            r.setJoint(1,0.2f);
        }
        if (key == '3') {
            r.setJoint(2,0.2f);
        }
        if (key == '4') {
            r.setJoint(3,0.2f);
        }
        if (key == '5') {
            r.setJoint(4,0.2f);
        }
        if (key == '6') {
            r.setJoint(5,0.2f);
        }
        if (key=='q'){
          roll+=roll+0.2;
        }
        
        if (key=='w'){
          pitch+=pitch+0.2;
        }
        
        if (key=='e'){
          yaw+=yaw+0.2;
        }
        if(key == 'm'){
          dx = dx+1;
          println(dx);
        } 
        if (key == 'n'){
          dx = dx-1;
          println(dx);
        }
  //r.setDhTable(r.inverseKinematics(px,py,pz,roll,pitch,yaw,1));
  
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
     float yaw = 0;
public void draw3Drobot(PeasyCam cam){
        //show(0,0,0,true);
        pushMatrix();
         r.drawLink();
        popMatrix();
        int nPoints = 300;
        fill(255);
        stroke(0);
        strokeWeight(3);
        pushMatrix();
        //show(0,0,96+dx,true);
        translate(px,-py,pz);
        rotateZ(roll);
        rotateY(pitch);
        rotateX(yaw);
        //box(5);.
        //show(0,0,63,true);
        popMatrix();
        noStroke();
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
