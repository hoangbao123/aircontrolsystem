package TileMap;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import javax.imageio.ImageIO;

import game.GamePanel;

public class AirCraft {

	public final static int FLY_STRAIGHT = 4;
	public final static int FLY_CIRCLE = 5;
	public final static int FLY_LAND=3;
    public final static int FLY_GOGATE=2;
    public final static int FLY_STOP=1;
    protected final double ep=1,r0x=480,r0y=190,r1x=500,r1y=375;
	protected BufferedImage image;
	public static boolean runway1=false,runway2=false;
	public  double x0;
	protected double x;
	public double y0;
	protected double y;
	protected double dx;
	protected double dy;
	protected double omega;
	protected double radius;
	protected double angle;
	protected double angle0;
	public ToaDo toado;
	double m=0,p=0,r=0;
	protected boolean runwayNum=false,goToGate=false, choose=true;
	protected int mode,numGate,i=1;
	protected int gate;
    protected static int[] Gate={0, 0, 0, 0, 0, 0, 0, 0},
            X={680, 660, 640, 620, 461, 456, 450, 445},
            Y={398, 413, 429, 445, 340, 315, 290, 265} ;
	long t1=0;
	public AirCraft(String s)
	{
		try
		{
		    image = ImageIO.read(getClass().getResourceAsStream(s));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		omega = 0.01;
		radius = 0;
		angle = 0;
		toado=new ToaDo(x,y,angle);
	}
	
	public void update()
	{
		 	toado.setToado(x, y, angle);
		 	t1++;
		 	if(t1>50){
		 		if(toado.isInArea1()){
		 			if(getGate()==-1||runway1==false&&runway2==false||!( 85<Math.toDegrees(angle)&&Math.toDegrees(angle)<180)){
		 					flyCircle();
		 					System.out.println("stage 1 angle : "+Math.toDegrees(angle));
		 				}
		 			else{
		 				System.out.println("stage 3:angle "+angle);
		 				setInitAngle(Math.PI-Math.atan((490-x)/(180-y)));
		 				flyStraight();
		 			}
		 		}
		 		if(toado.isInArea3()){
		 			if(getGate()==-1||runway1==false&&runway2==false||!( 120<Math.toDegrees(angle)&&Math.toDegrees(angle)<180)){
	 					flyCircle();
	 					System.out.println("stage 5 angle : "+Math.toDegrees(angle));
	 				}
	 			else{
	 				System.out.println("stage 4:angle "+angle);
	 				setInitAngle(Math.atan((501-x)/(y-380)));
	 				flyStraight();
	 			}
		 		}
		 		
		 		
			}
		 	System.out.println(t1);
		 	flyStraight();
 		 	
			//if(mode==FLY_CIRCLE) flyCircle();
			//else flyStraight();
			
	}
	
	public void flyStraight()
	{
		x= x+dx*Math.sin(angle);
		y=y+ dy*Math.cos(angle)*-1;
		
		System.out.println("flystraight ");
		x0=x;
		y0=y;
		angle0=angle;
		m=0;
		
		p=0;
		r=0;
		//if(x>800) mode=FLY_CIRCLE;
		//if(t1>300&&x<200) mode=FLY_CIRCLE;
		
	}
	
	public void flyCircle()
	{
		if(m==0)x0=x;
		m++;
		if(p==0)y0=y;
		p++;
		if(r==0)angle0=angle;
		r++;
		double centerX,centerY;
	
		centerX=x0-radius*Math.sin(angle0-Math.PI/2);
		centerY=y0+radius*Math.cos(angle0-Math.PI/2);
		
		x =centerX+radius*Math.sin(angle-Math.PI/2);
		y = centerY -radius * Math.cos(angle-Math.PI/2);
		if(angle>Math.PI*2){
			angle=angle-Math.PI*2;
			
		}
		angle += omega;	
		System.out.println("flycircle");
		//if(x>700&&angle>Math.toRadians(360-80)) mode=FLY_STRAIGHT;
		//if(x<200&&angle>Math.toRadians(80)) mode=FLY_STRAIGHT;
	}
	public void flyCircleLeft(){
		
	}
	public void land(){
		toado.setX(x);
		toado.setY(y);
		if(toado.isInArea1()){
			angle=Math.PI/2;
			
		}
	}
	
	
	/*public int getGate(){
		int kq=-1;
        if(choose){
            for(int i=0 ; i<4 ; i++){
                if(Gate[i]==0){
                   choose=false;
                   Gate[i]=1;
                   kq=i;
                   break;
                }
            }
            if(kq==-1){
                choose=false;
                try{
                kq=getGate();
                }catch(Exception e){
                	System.out.println("bi loi 1: "+e.getMessage());
                }
            }
        }
        else{
            for(int i=4; i<8; i++){
                if(Gate[i]==0){
                    choose=true;
                    Gate[i]=1;
                    kq=i;
                    break;
                }
            }
            if(kq==-1){
                choose=true;
                try{
                kq=getGate();//chua xet truong hop cac o deu kin het
                }catch(Exception e){
                	System.out.println("bi loi"+e.getMessage() );
                }
                
            }
        }
        return 1;
	}*/

	public int getGate(){
		if(t1>300) {
			
			runway1=true;
			runway2=true;
			return 1;
		}
		return -1;
	}
	public void landing(){
        if(runwayNum){
            setVelocity(0.5,(double)13/45);
            x+=dx;
            y+=dy;
            if(abs(x-720)<ep&&abs(y-328)<ep){
                goToGate=true;
                numGate=getGate();
                setInitAngle(4*Math.PI/3);
                setMode(FLY_GOGATE);
            }
        }
        else {
            setVelocity(0.5,-12.0/41);
            x+=dx;
            y+=dy;
            if(abs(x-720)<ep&&abs(y-246)<ep){
                goToGate=false;
                numGate=getGate();
                setInitAngle((double)(Math.PI/3));
                setMode(FLY_GOGATE);
            }
        }
    }
	public void goToGate(int numGate){
        if(0<=numGate&&numGate<4){
            if(!runwayNum){
                setInitAngle(Math.PI);
                if(y-246>=-ep&&328-y>=0){
                    setVelocity(0,0.5);
                    x+=dx;
                    y+=dy;
                }else{
                    setInitAngle(-2*Math.PI/3);
                    x=x+0.5;
                    y=y;
                    runwayNum=true;
                }
        }else{
        switch(i){
        case 1:   if(x-658.8+(numGate)*21.4>=ep){
                            setVelocity(-0.5,29.0/98);
                            x+=dx;
                            y+=dy;
                }
                else{i=i+1;}
                break;
        case 2:{
            setInitAngle(5*Math.PI/6);
            setVelocity(0.1,49.0/290);
            x+=dx;
            y+=dy;
            if(abs(x-X[numGate])<=ep&&abs(y-Y[numGate])<=ep){
                setMode(FLY_STOP);
            }
            break;
            }
        }
        }
        }else{
            if(!runwayNum){
                setInitAngle(Math.PI);
                if(y-246>=-ep&&328-y>=0){
                    setVelocity(0,0.5);
                    x+=dx;
                    y+=dy;
                }else{
                    setInitAngle(-2*Math.PI/3);
                    x=x+0.5;
                    y=y;
                    runwayNum=true;
            }
        }else{
            switch(i){
                case 1:   if(x-572>=ep){
                            setVelocity(-0.5,29.0/98);
                            x+=dx;
                            y+=dy;
                    }
                    else{i=i+1;}
                    break;
                case 2:{
                    setInitAngle(5*Math.PI/3);
                    setVelocity(-0.1,-10.0/230);
                    x+=dx;
                    y+=dy;
                    if(x-504<ep){
                        i=i+1;
                    }
                break;
                }
                case 3:{
                    setInitAngle(47*Math.PI/24);
                    if(y-334.4+(numGate-4)*25.2>=ep){
                    setVelocity(-0.1,-15.5/24);
                    x+=dx;
                    y+=dy;
                    }else i=i+1;
                    break;
                }
                case 4:{
                    setInitAngle(35*Math.PI/24);
                    setVelocity(-0.1,2.4/155);
                    x+=dx;
                    y+=dy;
                                            System.out.println("x" +x+ "y"+y);
                    if(abs(x-X[numGate])<=ep&&abs(y-Y[numGate])<=ep){
                        setMode(FLY_STOP);
                    }
                    
                }
                
            }
                
            }
        }
    }
    
	public void setInitPosition(double x, double y)
	{
		this.x0 = x;
		this.y0 = y;
		this.x = x;
		this.y = y;
	}
	public void setVelocity(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
	}
	
	public void setInitAngle(double angle)
	{
	    this.angle = angle;	
	}
	public double getAngle(){
		return angle;
	}
	public void draw(Graphics2D g)
	{
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.rotate(angle,image.getWidth()/2,image.getHeight());
		
		g.drawImage(image, at, null);
		g.drawLine(0,200 , 500, 200);
	}
	public void bayVongCung(){
		if(x>970||y>523) 
			if(mode==FLY_STRAIGHT){
				mode=FLY_CIRCLE;
				
			}
	}
}
