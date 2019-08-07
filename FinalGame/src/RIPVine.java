
import processing.core.*;
import g4p_controls.*;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

//change name of class:
public class RIPVine extends PApplet {

    int screen = 0;

    PImage titlescrn, img, goright, goleft, cutsceneimg, spngbobback, mspuff, boat, spngbobcutscene, fightopt,
            roomatesback, roomates, roomatecutscene, lawnback, lawnmower, lawncutscene, chknimg, chkncutscene;

    GImageButton btnplay, btnabout, btncntrl, btnback, btnheal, btnatck, btndfnd;
    //play button img
    String pimage[] = {"play.png"};
    //about btn img
    String aimage[] = {"about.png"};
    //control btn img
    String cimage[] = {"controls.png"};
    //back btn img
    String bimage[] = {"backbtn.png"};

    String foename;

    String text[];

    //char imgs walking right
    PImage charimgright[] = {loadImage("char0.png"), loadImage("char1.png"), loadImage("char2.png")};
    //char images walking left
    PImage charimgleft[] = {loadImage("charleft0.png"), loadImage("charleft1.png"), loadImage("char2.png")};

    PImage cutscenes[] = {loadImage("spngbobani0.png"),
        loadImage("spngbobani1.png"), loadImage("roomatesani0.png"), loadImage("roomatesani1.png"),
        loadImage("lawnani.png"), loadImage("chknani0.png"), loadImage("chknani1.png")};

    //button options during fight
    String atkimg[] = {"atck.png"};
    String dfndimg[] = {"dfnd.png"};
    String healimg[] = {"heal.png"};

    //count to change walking img
    int imgcountright = 0;
    int imgcountleft = 0;
    int imgnum = 0;

    //foes health
    int foehealth;
    //mainchar health
    int charhealth = 75;
    //foes attack strength
    int foeatck = 5;
    //your attack strength
    int myatck = 5;
    //heal amt
    int healamt = 5;

    int msgtime = 0;

    int cutscenetime = 5;

    //booleans to walk, play music, play win music, play lose music, and game status aka if youre fighting or not, turn sees if its players turn during fight
    boolean walking = false;
    boolean music = false;
    boolean ifwin = false;
    boolean iflose = false;
    boolean gamestat = false;
    boolean myturn = true;
    boolean defend = false;
    boolean cutscene = false;
    boolean attack = false;
    boolean heal = false;
    //declare character
    Hitbox mainchar, mspuffhit, roomateshit, lawnhit, chknhit;
    //game sounds
    GameSound title, win, lose, loseintro, walk, fight, chkn, lawn, roommates, spngbob;
    String msg;

    public void setup() {
        size(768, 432, JAVA2D);
        //load game sounds
        title = new GameSound("titleaudio.wav");
        walk = new GameSound("walkaudio.wav");
        fight = new GameSound("fightaudio.wav");
        win = new GameSound("winaudio.wav");
        loseintro = new GameSound("loseintro.wav");
        lose = new GameSound("loseaudio.wav");
        chkn = new GameSound("chickenstrips.wav");
        lawn = new GameSound("lawnmoweraud.wav");
        roommates = new GameSound("roomatesaud.wav");
        spngbob = new GameSound("spngbobaud.wav");
        msg = "";

        //load character                
        mainchar = new Hitbox(this, charimgright[0], 10, 300);
        //load main screen img
        titlescrn = loadImage("titlescrn.png");
        //load game screens
        goright = loadImage("goright.png");
        goleft = loadImage("goleft.png");

        //declare start screen buttons
        btnplay = new GImageButton(this, 5, 25, 224, 224, pimage);
        btnabout = new GImageButton(this, 555, 25, 224, 224, aimage);
        btncntrl = new GImageButton(this, 5, 175, 224, 224, cimage);
        btnback = new GImageButton(this, 0, 0, 96, 96, bimage);
        //set back btn invisible to begin
        btnback.setVisible(false);

        //spongebob level
        spngbobback = loadImage("spngbob.png");
        mspuff = loadImage("mspuff.png");
        boat = loadImage("boat.png");
        mspuffhit = new Hitbox(this, mspuff, 200, 300);

        //roomates level
        roomatesback = loadImage("rommatesback.png");
        roomates = loadImage("roomates.png");
        roomateshit = new Hitbox(this, roomates, 600, 300);

        //lawnmower level
        lawnback = loadImage("lawnback.png");
        lawnmower = loadImage("lawnmower.png");
        lawnhit = new Hitbox(this, lawnmower, 200, 300);

        //chkn level
        chknimg = loadImage("chkndude.png");
        chknhit = new Hitbox(this, chknimg, 600, 300);

        //fight opt buttons 
        btnatck = new GImageButton(this, 555, 290, 32, 32, atkimg);
        btndfnd = new GImageButton(this, 595, 290, 32, 32, dfndimg);
        btnheal = new GImageButton(this, 635, 290, 32, 32, healimg);
        btnatck.setVisible(false);
        btndfnd.setVisible(false);
        btnheal.setVisible(false);
        fightopt = loadImage("fightopt.png");

    }//end of setup

    public void handleButtonEvents(GImageButton button, GEvent event) {
        //code for gimagebuttons goes here

        //btncntrl, btnabout, and btnplay are on title screen
        if (button == btnabout) {
            screen = 1;
            img = loadImage("aboutback.png");

            //set all btns, except back, invisible
            btnplay.setVisible(false);
            btnabout.setVisible(false);
            btncntrl.setVisible(false);
            //set back visible
            btnback.setVisible(true);

        }
        if (button == btncntrl) {
            screen = 2;
            img = loadImage("cntrlsback.png");
            btnplay.setVisible(false);
            btnabout.setVisible(false);
            btncntrl.setVisible(false);
            btnback.setVisible(true);
        }
        if (button == btnback) {
            screen = 0;
            btnplay.setVisible(true);
            btnabout.setVisible(true);
            btncntrl.setVisible(true);
            btnback.setVisible(false);
        }

        if (button == btnplay) {
            screen = 3;
            btnplay.setVisible(false);
            btnabout.setVisible(false);
            btncntrl.setVisible(false);
            btnback.setVisible(false);
            title.stopTrack();
            walk.playTrack();
        }

        //fight option buttons
        if (button == btnatck && myturn) {
            attack = true;
            foehealth -= myatck;
            msg = ("You attacked " + foename + " for " + myatck + " damage!");
            myturn = false;
            msgtime = 120;
            System.out.println(foehealth + " mspuff");
        }
        if (button == btndfnd) {
            msg = ("You blocked " + foename + "'s attack!");
        }
        if (button == btnheal) {
            heal = true;
            charhealth += healamt;
            msg = ("You healed " + healamt + " hearts!");
        }

    }//end of button events

    public void foeAtck() {
        if (myturn == false) {
            if (random(3) < 2 && myturn == false) {
                charhealth -= foeatck;
                msg = ("" + foename + " attacked you for " + foeatck + " damage!");

                System.out.println(charhealth);
            } else {
                msg = ("" + foename + " missed!");
            }
        }
        myturn = true;
        if (foehealth == 0 && charhealth > 0) {
            msg = ("You have defeated " + foename + "!");
            charhealth = 75;
            cutscene = true;
            music = false;
            if (cutscene == true) {
                background(cutsceneimg);
                if (screen == 4 && cutscene == true) {
                    cutscenetime = 320;
                }
                if (screen == 6 && cutscene == true) {
                    cutscenetime = 320;
                }
                if (screen == 8 && cutscene == true) {
                    cutscenetime = 320;
                }
                if (screen == 10 && cutscene == true) {
                    cutscenetime = 320;
                }
            }

        }
        if (charhealth == 0) {
            msg = ("You have been defeated!");
            background(loadImage("theend.png"));
            if (music == false) {
                loseintro.playSound();
            }
            music = true;
            if (music == false) {
                lose.playTrack();
            }
            music = true;
        }

    }//end of foeAtck

    public void displayStatus() {
        fill(0);
        rect(0, 0, 768, 20);
        fill(255);
        text("Your health: " + charhealth, 50, 15);
        text(foename + " health: " + foehealth, 550, 15);
    }//end of displayStatus

    public void updateImg() {
        if (gamestat == false) {

        } else if (key == 'd' && gamestat == true) {
            imgcountright++;
            //walking right
            if (imgcountright == 10) {
                imgnum++;
                imgcountright = 0;
                if (imgnum == 2) {
                    imgnum = 0;
                }
                mainchar.image = charimgright[imgnum];
            }
        } //walking left
        else if (key == 'a' && gamestat == true) {
            imgcountleft++;
            if (imgcountleft == 10) {
                imgnum++;
                imgcountleft = 0;
                if (imgnum == 2) {
                    imgnum = 0;
                }
                mainchar.image = charimgleft[imgnum];
            }
        }
    }//end of updateImg

    public void draw() {
        if (gamestat == true) {
            if (walking == true) {
                mainchar.move();
                updateImg();
            }
        }

        //main screen = 0
        if (screen == 0) {
            background(titlescrn);
            if (music == false) {
                title.playTrack();
            }
            //stops music from repeating before finishing loop
            music = true;
        }

        //about screen = 1
        if (screen == 1) {
            image(img, 0, 0);
        }
        //control screen =2
        if (screen == 2) {
            image(img, 0, 0);
        }
        //walk right = 3
        if (screen == 3) {
            background(goright);
        }

        //tells game status to be true (walking around not fighting)
        if (screen == 3 || screen == 5 || screen == 7 || screen == 9) {
            gamestat = true;
        }
        //sees if player loc is off screen & the screen before was walk right, sets music to false so that fight music will loop without repeating before done
        if (screen == 3 && mainchar.x > 710) {
            screen = 4;
            music = false;
            //sets health for spngbob lvl
            if (screen == 4 && cutscene == false) {
                foehealth = 50;
                charhealth = 75;
            }
            //spngbob cutscene
            if (screen == 4 && cutscene == true) {
                cutscenetime = 320;
            }
        }

        //screen 4 = spongebob level
        if (screen == 4 && cutscene == false) {
            background(spngbobback);
            image(boat, 350, 300);
            walk.stopTrack();
            foename = "Ms. Puff";
            displayStatus();
            image(fightopt, 500, 215);
            if (music == false) {
                fight.playTrack();
            }
            music = true;
            mainchar.image = charimgright[2];
            //draws ms puff and stops character from being able to walk
            mspuffhit.draw();
            mainchar.stop(key);
            //draws character where i want for fight scene
            mainchar.x = 25;
            gamestat = false;

            //fight opts become available
            btnatck.setVisible(true);
            btndfnd.setVisible(true);
            btnheal.setVisible(true);

            if (myturn == true) {
                btnatck.setEnabled(true);
                btndfnd.setEnabled(true);
                btnheal.setEnabled(true);
            } else if (myturn == false) {
                btnatck.setEnabled(false);
                btndfnd.setEnabled(false);
                btnheal.setEnabled(false);
                if (msgtime > 0) {
                    msgtime--;

                } else if (attack || heal) {
                    foeAtck();
                }

            }

        }
        if (screen == 4) {
            if (cutscenetime > 0 && cutscene == true) {
                cutscenetime--;
                System.out.println(cutscenetime);
                background(cutsceneimg);
            }
            if (cutscenetime > 120) {
                cutsceneimg = cutscenes[0];
            } else if (cutscenetime < 120) {
                cutsceneimg = cutscenes[1];
            }
            if (cutscenetime <= 0 && cutscene == true) {
                screen = 20;
                ifwin = true;
                background(loadImage("theend.png"));                
            }
            if (cutscene == true && music == false) {
                spngbob.playSound();
            }
            music = true;
            if(screen == 4 && cutscene == true && cutscenetime == 0){
                music = false;
            }
            if (ifwin) {
                    win.playTrack();
                }
                music = true;
        }

        //this code does not work, didnt want to delete it because im going to work on it over the summer
        /* if (screen == 20 && mainchar.x == 0) {
                screen = 5;
                music = false;

            }
        }

        
        //walk left screen after ms puff fight
        if (screen == 5) {
            cutscene = false;
            background(goleft);
            mainchar.x = 766;
            mainchar.image = charimgleft[0];
            walking = true;
            if (music == false) {
                walk.playTrack();
            }
            music = true;
        }

        if (screen == 5 && mainchar.x == -10) {
            screen = 6;
            music = false;
            //roomates lvl
            if (screen == 6 && cutscene == false) {
                foehealth = 60;
                charhealth = 75;
            }
            //roomates cutscene
            if (screen == 6 && cutscene == true) {
                cutscenetime = 320;
            }
        }

        //roomates level
        if (screen == 6 && cutscene == false) {
            background(roomatesback);
            walk.stopTrack();
            foename = "And they were Roommates!";
            displayStatus();
            image(fightopt, 100, 215);
            if (music == false) {
                fight.playTrack();
            }
            music = true;
            mainchar.x = 766;
            mainchar.stop(key);
            mainchar.image = charimgleft[2];
            roomateshit.draw();
            gamestat = false;

            //fight opts become available
            btnatck.setVisible(true);
            btndfnd.setVisible(true);
            btnheal.setVisible(true);

            if (myturn == true) {
                btnatck.setEnabled(true);
                btndfnd.setEnabled(true);
                btnheal.setEnabled(true);
            } else if (myturn == false) {
                btnatck.setEnabled(false);
                btndfnd.setEnabled(false);
                btnheal.setEnabled(false);
                if (msgtime > 0) {
                    msgtime--;

                } else {
                    foeAtck();
                }
            }

        }
        if (screen == 6) {
            if (cutscenetime > 0 && cutscene == true) {
                cutscenetime--;
                background(cutsceneimg);
            }
            if (cutscenetime > 30) {
                cutsceneimg = cutscenes[2];
            } else if (cutscenetime < 120) {
                cutsceneimg = cutscenes[3];
            }
            if (cutscenetime <= 0 && cutscene == true) {
                screen = 21;
                cutsceneimg = null;
                background(roomatesback);
                mainchar.draw();
                gamestat = true;
                walking = true;
                mainchar.move();
                btnatck.setVisible(false);
                btndfnd.setVisible(false);
                btnheal.setVisible(false);
                music = true;
            }
            if (cutscene == true && music == false) {
                roommates.playSound();
            }
            music = true;
        }

        if (screen == 21 && mainchar.x == 772) {
            screen = 7;
            mainchar.x = 5;
            music = false;
        }

        //walk right after roommates fight
        if (screen == 7) {
            background(goright);
            mainchar.x = 5;
            mainchar.image = charimgright[0];
            walking = true;
            if (music == false) {
                walk.playTrack();
            }
            music = true;
        }

        if (screen == 7 && mainchar.x == 772) {
            screen = 8;
            mainchar.x = 5;
            music = false;
            //lawnmower lvl
            if (screen == 8 && cutscene == false) {
                foehealth = 70;
                charhealth = 75;
            }
            //lawnmower cutscene
            if (screen == 8 && cutscene == true) {
                cutscenetime = 320;
            }
        }

        //lawnmower level
        if (screen == 8 && cutscene == false) {
            background(lawnback);
            walk.stopTrack();
            foename = "Lawnmower";
            displayStatus();
            image(fightopt, 500, 215);
            if (music == false) {
                fight.playTrack();
            }
            music = true;
            mainchar.image = charimgright[2];
            //draws lawnmower and stops character from being able to walk
            lawnhit.draw();
            mainchar.stop(key);
            //draws character where i want for fight scene
            mainchar.x = 25;
            gamestat = false;

            //fight opts become available
            btnatck.setVisible(true);
            btndfnd.setVisible(true);
            btnheal.setVisible(true);

            if (myturn == true) {
                btnatck.setEnabled(true);
                btndfnd.setEnabled(true);
                btnheal.setEnabled(true);
            } else if (myturn == false) {
                btnatck.setEnabled(false);
                btndfnd.setEnabled(false);
                btnheal.setEnabled(false);
                if (msgtime > 0) {
                    msgtime--;

                } else {
                    foeAtck();
                }
            }

        }
        if (screen == 8) {
            if (cutscenetime > 0 && cutscene == true) {
                cutscenetime--;
                background(cutsceneimg);
            }
            if (cutscenetime > 30) {
                cutsceneimg = cutscenes[4];
            }
            if (cutscenetime <= 0 && cutscene == true) {
                screen = 22;
                cutsceneimg = null;
                background(lawnback);
                mainchar.draw();
                gamestat = true;
                walking = true;
                mainchar.move();
                btnatck.setVisible(false);
                btndfnd.setVisible(false);
                btnheal.setVisible(false);
                music = true;
            }
            if (cutscene == true && music == false) {
                lawn.playSound();
            }
            music = true;
        }
        if (screen == 22 && mainchar.x == -10 && gamestat == true) {
            screen = 9;
            btnatck.setVisible(false);
            btndfnd.setVisible(false);
            btnheal.setVisible(false);
            music = false;
        }
        //walk left screen after lawnmower fight
        if (screen == 9) {
            background(goleft);
            mainchar.x = 766;
            mainchar.image = charimgleft[0];
            walking = true;
            if (music == false) {
                walk.playTrack();
            }
            music = true;
        }

        if (screen == 9 && mainchar.x == -10) {
            screen = 10;
            music = false;
            //chkn lvl
            if (screen == 10 && cutscene == false) {
                foehealth = 100;
                charhealth = 75;
            }
            //chkn cutscene
            if (screen == 10 && cutscene == true) {
                cutscenetime = 320;
            }
        }

        //chkn level
        if (screen == 10 && cutscene == false) {
            while (screen == 10) {
                for (int i = 0; i < 6; i++) {
                    background(loadImage("back" + (i) + ".png"));
                }
            }
            walk.stopTrack();
            foename = "Chicken Strips";
            displayStatus();
            image(fightopt, 100, 215);
            if (music == false) {
                fight.playTrack();
            }
            music = true;
            mainchar.x = 766;
            mainchar.stop(key);
            mainchar.image = charimgleft[2];
            chknhit.draw();
            gamestat = false;

            //fight opts become available
            btnatck.setVisible(true);
            btndfnd.setVisible(true);
            btnheal.setVisible(true);

            if (myturn == true) {
                btnatck.setEnabled(true);
                btndfnd.setEnabled(true);
                btnheal.setEnabled(true);
            } else if (myturn == false) {
                btnatck.setEnabled(false);
                btndfnd.setEnabled(false);
                btnheal.setEnabled(false);
                if (msgtime > 0) {
                    msgtime--;

                } else {
                    foeAtck();
                }
            }

        }
        if (screen == 10) {
            if (cutscenetime > 0 && cutscene == true) {
                cutscenetime--;
                background(cutsceneimg);
            }
            if (cutscenetime > 30) {
                cutsceneimg = cutscenes[5];
            } else if (cutscenetime < 120) {
                cutsceneimg = cutscenes[6];
            }
            if (cutscenetime <= 0 && cutscene == true) {
                screen = 23;
                cutsceneimg = null;
                background(loadImage("theend.png"));
                btnatck.setVisible(false);
                btndfnd.setVisible(false);
                btnheal.setVisible(false);
                if (music == false) {
                    win.playTrack();
                }
                music = true;
            }
            if (cutscene == true && music == false) {
                chkn.playSound();
            }
            music = true;
        }
         */
        //draws character after main screen
        if (screen > 2 && cutscene == false) {
            mainchar.draw();
        }
        //shows msg
        if (cutscene == false) {
            text(msg, 100, 100);
        }

        if (cutscene == true) {
            btnatck.setVisible(false);
            btndfnd.setVisible(false);
            btnheal.setVisible(false);
            fight.stopTrack();
        }
    }//end of draw

    public void keyReleased() {
        mainchar.stop(key);
        //walking right stop
        if (key == 'd' && gamestat == true) {
            walking = false;

            mainchar.image = charimgright[0];
        }
        //walking left stop
        if (key == 'a' && gamestat == true) {
            walking = false;

            mainchar.image = charimgleft[0];
        }
    }

    public void keyPressed() {
        //walking right go
        if (key == 'd') {
            walking = true;

        }
        //walking left go
        if (key == 'a') {
            walking = true;
        }
    }

    //overide to get through levels faster, just click on enemy
   /* public void mousePressed() {
        if (mspuffhit.collidesWith(mouseX, mouseY) || lawnhit.collidesWith(mouseX, mouseY) || roomateshit.collidesWith(mouseX, mouseY) || chknhit.collidesWith(mouseX, mouseY)) {
            foehealth = 5;
        }
    } */

    //this is needed to run the program
    public static void main(String ags[]) {
        PApplet.main(new String[]{RIPVine.class.getName()});
    }

}
