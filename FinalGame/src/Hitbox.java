import processing.core.PApplet;
import processing.core.PImage;

public class Hitbox {

    public int x, y, w, h;
    PApplet host;
    PImage image;
    String img[];
    protected boolean ld = false, rd = false, ud = false, dd = false;
    protected int xdir = 0, ydir = 0;
    boolean canGoVertical = false;
    
   
    
    public Hitbox(PApplet pa, int xp, int yp, int wp, int hp) {
        host = pa;
        x = xp;
        y = yp;
        w = wp;
        h = hp;
        image = null;
    }

    public Hitbox(PApplet pa, PImage img, int xp, int yp) {
        host = pa;
        image = img;
        x = xp;
        y = yp;
        w = img.width;
        h = img.height;
    }
    
    

    public void setDiagonalMove(boolean b) {
        canGoVertical = b;
    }
    
    public void grow(double amt){
        h *= amt;
        w *= amt;
    }

    public void move(int xp, int yp) {
        x = xp;
        y = yp;
        
    }

    public void stop(char keyCode) {

        if (keyCode == 'a') {
            ld = false;
        } else if (keyCode == 'd') {
            rd = false;
        } else if (keyCode == 'w') {
            ud = false;
        } else if (keyCode == 's') {
            dd = false;
        }
        
    }

    protected void updateDir(boolean a, boolean b, boolean c, boolean d) {
        if (canGoVertical) {
            return;
        }
        ld = a;
        rd = b;
        ud = c;
        dd = d;
    }

    public void move() {
        if (host.keyPressed) {
            if (host.key == 'a') {
                ld = true;
                updateDir(true, false, false, false);
            } else if (host.key == 'd') {
                rd = true;
                updateDir(false, true, false, false);
            } else if (host.key == 'w') {
                ud = true;
                updateDir(false, false, true, false);
            } else if (host.key == 's') {
                dd = true;
                updateDir(false, false, false, true);
            }
        }
        if (rd) {
            xdir = 2;
        } else if (ld) {
            xdir = -2;
        } else {
            xdir = 0;
        }

        if (ud) {
            ydir = -5;
        } else if (dd) {
            ydir = 5;
        } else {
            ydir = 0;
        }
        moveLR(xdir);
        //moveUD(ydir);

        /*if (hitWall() != 'x' && xdir != 0) {
            moveLR(-xdir);
            xdir = 0;
        }

        if (hitWall() != 'x' && ydir != 0) {
            moveUD(-ydir);
            ydir = 0;
        }
*/
        /*if(x <=-100 ) x = host.width + 10;
        if(x > host.width+10) x= -10;
        if(y <=-10) y = host.height + 10;
        if(y > host.height + 10) y = -10; */
        
    }

    public void moveLR(int xp) {
        x += xp;
    }

    public void moveUD(int yp) {
        y += yp;
    }

    public boolean collidesWith(Hitbox other) {
        if (x + w > other.x && x < other.x + other.w && y + h > other.y && y < other.y + other.h) {
            return true;
        } else {
            return false;
        }
    }

    public boolean collidesWith(int xp, int yp) {
        if (xp > x && xp < x + w && yp > y && yp < y + h) {
            return true;
        } else {
            return false;
        }
    }

    public char hitWall() {
        if (x < 0) {
            return 'l';
        } else if (x + w > host.width) {
            return 'r';
        } else if (y < 0) {
            return 't';
        } else if (y + h > host.height) {
            return 'b';
        } else {
            return 'x';
        }
    }

    public void drawBox() {
        host.rect(x, y, w, h);
    }

    public void draw() {
        if (image != null) {
            host.image(image, x, y);
        }
    }

}
