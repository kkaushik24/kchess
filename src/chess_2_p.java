import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;

//import kchess.MyMouseAdapter;
//import kchess.MyWindowAdapter;

public class chess_2_p {
	static Image a;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

    private static void createAndShowGUI() {
       // System.out.println("Created GUI on EDT? "+
      //  SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("kchess");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        try {
	           a = ImageIO.read(new File("icon.jpg"));
	       } catch (IOException e) {
	       }
        kchess b=new kchess();
        b.validate();
        b.repaint();
        f.add(b);
        f.pack();
        f.setVisible(true);
        f.setIconImage(a);
    } 
}


class kchess extends JPanel {


int x[]=new int[120];
int y[]=new int[120];
int b[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
int w[]={48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63};
int mousex,mousey;
boolean chance1=true,chance2=true,urgent1=false,urgent2=false;
//int fill_player1=87;
//boolean drect_flag; 
int flag[]=new int[120];
 int f[]=new int[40];
 int gp1[]=new int[40];
 int gp2[]=new int[40];
 int p1=88,p2=70;
static Image a;

Graphics2D g1;



//int player_move[]=new int[50];
//int player_counter=0;
//int attacked[][]=new int[16][40];



private void dinit()
{
	int u=425,v=45,i,j,k=0,h=90;
	for(i=0;i<8;i++)
	{
		for(j=0;j<8;j++)
		{
		x[k]=u+j*h;
		y[k]=v;
		k++;
		}
		v=v+h;
	}
	v=85;
	for(i=0;i<6;i++)
	{
		x[k]=50;
		y[k]=v+i*h;
		k++;
	}
	u=155;
	v=155;
	for(i=0;i<6;i++)
	{
		for(j=0;j<3;j++)
		{
		x[k]=u+j*h;
		y[k]=v;
		k++;
		}
		v=v+h;
	}
	u=1145;
	v=155;
	for(i=0;i<6;i++)
	{
		for(j=0;j<3;j++)
		{
		x[k]=u+j*h;
		y[k]=v;
		k++;
		}
		v=v+h;
	}
	
		
	for(i=0;i<120;i++)
	{
		if((i>=0&&i<16) || (i>=48&&i<64))
			flag[i]=i;
		else
			flag[i]=0;
		
	}

	//changing the value of rook of com to -1 or -2
	flag[0]=-1;
	flag[7]=-2;
	for(i=0;i<40;i++)
	{
		f[i]=999;
		
	}
	
	//flag[0]=58;
	//flag[44]=58;
}


private int get_pos(int x,int y)
{
	int u=380,h=90,v=0,i,j;
	
	for( i=0;i<8;i++)
	{
		if((x>=u+i*h) && (x<=u+(i+1)*h)&&(x>380))
			{
			
			
			break;
			}
		
		
	}
	for(j=0;j<8;j++)
	{
		if(y>=v+j*h && y<=v+(j+1)*h)
			break;
	}
	return (x>380&&y<720&&x<1100&& y>20?i+8*j:-1);

}

public kchess() {

    //setBorder(BorderFactory.createLineBorder(Color.black));
	 
    try {
           a = ImageIO.read(new File("icon.jpg"));
       } catch (IOException e) {
       }
	//Color c3 = new Color(250, 250, 200,250);
	setBackground(Color.GRAY);
	setForeground(Color.black);
	dinit();
	//addMouseListener(new MyMouseAdapter(this));
	//addWindowListener(new MyWindowAdapter());
	addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
        	mousex=e.getX();
    		mousey=e.getY();
    		
            repaint();
        }
    });
    }
/*class MyMouseAdapter extends MouseAdapter {
	
	kchess appWindow;
	public MyMouseAdapter(kchess appWindow) {
	this.appWindow = appWindow;
	}
	public void mouseClicked(MouseEvent me) {
	mousex=me.getX();
	mousey=me.getY();
	g1.setColor(Color.green);
	draw_king(g1,19);
	repaint();
	
	}
	}*/


       


public Dimension getPreferredSize() {
    return new Dimension(250,200);
}

protected void paintComponent(Graphics g) {
    super.paintComponent(g);       
    int x=380,y=0,u=90,v=90;
	
	for(int i=0;i<8;i++)
	{
	
		x=380;
		
		for(int j=0;j<8;j++)
		{
		
			if(i%2==0 && j%2==0 || (i%2!=0 && j%2!=0))
			g.fillRect(x, y,u,v);
			//g.drawRect(x, y, u, v);
			x=x+90;
			
		}
		y=y+90;
	}
	
	intro(g);
	
	g1=(Graphics2D)g;
	g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
	//g1.getRenderingHint(null);
	g1.setStroke(new BasicStroke(2));
	update(g1);
	
}



public void update(Graphics g) {
	Color c3 = new Color(100, 100, 255,100);
	Color  c4 = new Color(255, 255, 255,150);
	int k=get_pos(mousex,mousey);
	if(!urgent1&&!urgent2)
	{
	if(!comp_r(k))
	clear_f();
	else
	{
		if(flag[k]==0)
		{
		flag[k]=flag[f[0]];
		flag[f[0]]=0;
		if(flag[k]<16)
		{
			chance2=true;
			chance1=false;
			
			
			//compare_f_gp2();
				
			
		}
		if( flag[k]>47 &&flag[k]<64)
		{
			chance2=false;
			chance1=true;
			
			
				
		}
		}
		else 
		{
			if(flag[k]<16 && flag[f[0]]>47 &&flag[f[0]]<64)
			{
				//compare_f_gp1();
				if(flag[k]==4)
				{
						urgent2=true;
				}
				else
				{flag[p1++]=flag[k];
				
				flag[k]=flag[f[0]];
				flag[f[0]]=0;}
				chance2=false;
				chance1=true;
				
				
			}
			else if(flag[k]>47 && flag[f[0]]<16)
			{
				//compare_f_gp2();
				
					
				chance2=true;
				chance1=false;
				if(flag[k]==60)
				{
						urgent1=true;
				}
				else
				{
					flag[p2++]=flag[k];
					flag[k]=flag[f[0]];
					flag[f[0]]=0;
				}
			}
		}
		
		
		
		clear_f();
		
		
	}
	}
	
	if(urgent1)
	{
		Font f = new Font("SansSerif", Font.BOLD, 60);
		g.setFont(f);
		g.setColor(c4);
		g.drawString("CHECK MATE", 550, 200);
		g.drawString("PLAYER2 WINS", 550, 400);
	
	}
	if(urgent2)
	{
		Font f = new Font("SansSerif", Font.BOLD, 60);
		g.setFont(f);
		g.setColor(c4);
		g.drawString("CHECK MATE", 550, 200);
		g.drawString("PLAYER1 WINS", 550, 400);
	
	}
	g.setColor(c3);
	if(k!=-1&&!urgent1&&!urgent2)
	set_property(g,k);
	draw_rect(g);
	set1(g);
	set2(g);	
	
}
private void print(Graphics2D g)
{
	
	
	
		
}
private void set1(Graphics g)
{
	g.setColor(Color.RED);
	for(int i=0;i<120;i++)
	{
		if(flag[i]>=8&&flag[i]<16)
		{
			draw_pawn(g,i);
		}
		else
		{
		switch(flag[i])
		{
		case -1:draw_rook(g,i);
				break;
		case 1:draw_knight(g,i);
				break;
		case 2:draw_bishop(g,i);
				break;
		case 3:draw_queen(g,i);
				break;
		case 4:draw_king(g,i);
				break;
		case 5:draw_bishop(g,i);
				break;
		case 6:draw_knight(g,i);
				break;
		case -2:draw_rook(g,i);
				break;
		default:break;
		}
		}
	}

}
private void set2(Graphics g)
{
	
	g.setColor(Color.CYAN);
	/*draw_rook(g,56);
	draw_knight(g,57);
	draw_bishop(g,58);
	
	draw_queen(g,59);
	draw_king(g,60);
	draw_bishop(g,61);
	draw_knight(g,62);
	draw_rook(g,63);
	for(int k=48;k<56;k++)
	{
		draw_pawn(g,k);
	}*/
	for(int i=0;i<120;i++)
	{
		if(flag[i]>=48&&flag[i]<56)
		{
			draw_pawn(g,i);
		}
		else
		{
		switch(flag[i])
		{
		case 56:draw_rook(g,i);
				break;
		case 57:draw_knight(g,i);
				break;
		case 58:draw_bishop(g,i);
				break;
		case 59:draw_queen(g,i);
				break;
		case 60:draw_king(g,i);
				break;
		case 61:draw_bishop(g,i);
				break;
		case 62:draw_knight(g,i);
				break;
		case 63:draw_rook(g,i);
				break;
		default:break;
				

				
		}

		}		
	}
		
}


private void clear_f()
{
	
	
	for(int i=0;i<40;i++)
	{
		f[i]=999;
	}
}
private boolean comp_r(int k)
{
	int i=1;
	boolean b=false;
	while(f[i]!=999)
	{	if(k==f[i])
		b=true;
		i++;
		
	}
	return b;
	
}
private void intro(Graphics g) {
	// TODO Auto-generated method stub
	
	final Font f = new Font("SansSerif", Font.BOLD, 18);
	g.setFont(f);
	g.setColor(Color.CYAN);
	g.drawString("PLAYER 1", 220, 50);
	g.setColor(Color.RED);
	g.drawString("PLAYER 2", 1160,50);
	//g.fillRect(130, 110, 270, 540);
	//g.fillRect(1120, 110, 270, 540);
	g.setColor(Color.BLACK);
	draw_king(g,64);
	//king end
	g.setFont(f);
	g.setColor(Color.black);
	g.drawString("KING", 20, 50);
	//queen start
	/*g.drawOval(10, 100, 60, 60);
	g.drawOval(25, 115, 30, 30);
	g.fillOval(25, 115, 30, 30);
	*/
	draw_queen(g,65);
	//queen end
	g.setFont(f);
	g.setColor(Color.black);
	g.drawString("QUEEN", 20, 140);
	//rook start
	
	draw_rook(g,66);
	//rook end
	g.setFont(f);
	g.setColor(Color.black);
	g.drawString("ROOK", 20, 230);
	//bishop start
	/*g.drawOval(10, 280, 60, 60);
	g.drawOval(20, 290, 40, 40);
	g.drawOval(30, 300, 20, 20);
	*/
	draw_bishop(g,67);
	//bishop end
	g.setFont(f);
	g.setColor(Color.black);
	g.drawString("BISHOP", 20, 320);
	//knight start
	/*g.drawOval(10, 370, 60, 60);
	g.drawLine(40, 370, 40, 430);
	*/
	draw_knight(g,68);
	//knight end
	g.setFont(f);
	g.setColor(Color.black);
	g.drawString("KNIGHT", 20, 410);
	//pawn start
	/*g.drawOval(20, 460, 40, 40);
	g.drawOval(30, 470, 20, 20);
	*///pawn end
	draw_pawn(g,69);
	g.setFont(f);
	g.setColor(Color.black);
	g.drawString("PAWN", 20, 500);
	

	
}
private void draw_knight(Graphics g,int k)
{
	int h,l;
	h=x[k]-30;
	l=y[k]-30;
	
	g.drawOval(h, l, 60, 60);
	g.drawLine(h+30, l, h+30, l+60);
	
}
private void draw_pawn(Graphics g,int k)
{
	int h,l;
	h=x[k]-20;
	l=y[k]-20;
	g.drawOval(h, l, 40, 40);
	g.drawOval(h+10, l+10, 20, 20);
	
	
}

private void draw_king(Graphics g,int k)
{
	int h,l;
	h=x[k]-30;
	l=y[k]-30;
	//g.setColor(Color.white);
	g.drawOval(h, l, 60, 60);
	g.drawLine(h+15, l+30, h+45, l+30);
	g.drawLine(h+30,l+15 , h+30, l+45);
	
	
}
private void draw_queen(Graphics g,int k)
{
	int h,l;
	h=x[k]-30;
	l=y[k]-30;
	g.drawOval(h, l, 60, 60);
	g.drawOval(h+15, l+15, 30, 30);
	g.fillOval(h+15, l+15, 30, 30);
	
}
private void draw_rook(Graphics g,int k)
{
	int h,l;
	h=x[k]-30;
	l=y[k]-30;
	
	g.drawOval(h, l, 60, 60);
	g.drawOval(h+10, l+10, 40, 40);
	
}
private void draw_bishop(Graphics g,int k)
{
	int h,l;
	h=x[k]-30;
	l=y[k]-30;
	
	g.drawOval(h, l, 60, 60);
	g.drawOval(h+10, l+10, 40, 40);
	g.drawOval(h+20, l+20, 20, 20);
	
}
private void set_property(Graphics g,int k)
{
	if(chance2)
	{
		//chance2=false;
		//chance1=true;
		
		if(flag[k]>=48 && flag[k]<56)
			set_pawn(k);
	
	
	else
	{
	switch(flag[k])
	{
	case 56:set_rook(k);
			break;
	case 57:set_knight(k);
			break;
	case 58:set_bishop(k);
			break;
	case 59:set_queen(k);
			break;
	case 60:set_king(k);
			break;
	case 61:set_bishop(k);
			break;
	case 62:set_knight(k);
			break;
	case 63:set_rook(k);
			break;
	
	default:break;
	}
		
	}
	}
	else if(chance1)
	{
		
		
		//chance2=true;
		//chance1=false;
		
		if(flag[k]>=8 && flag[k]<16)
			set_com_pawn(k);
		else
		{
		switch(flag[k])
		{
		case -1:set_com_rook(k);
		break;
		case 1:set_com_knight(k);
		break;
		case 2:set_com_bishop(k);
		break;
		case 3:set_com_queen(k);
		break;
		case 4:set_com_king(k);
		break;
		case 5:set_com_bishop(k);
		break;
		case 6:set_com_knight(k);
		break;
		case -2:set_com_rook(k);
		break;
		default:break;
		}
		}
	}
}
private void set_pawn(int k)
{
	int i=0;
	//draw_rect(g,k);
	f[i++]=k;
	if(k/8==6)
	{
		if(flag[k-8]==0)
		{
		//	draw_rect(g,k-8);
			f[i++]=k-8;
			if(flag[k-16]==0)
			{
			//	draw_rect(g,k-16);
				f[i++]=k-16;
			}
			if(k-9>=0)
				if(flag[k-9]!=0 && flag[k-9]<16 && (k-9)/8==k/8-1)
				{
					//draw_rect(g,k-9);
					f[i++]=k-9;

				}
				if(k-7>=0)
				if(flag[k-7]!=0 && flag[k-7]<16 && (k-7)/8==k/8-1)
				{
					//draw_rect(g,k-7);
					f[i++]=k-7;

				}
		}
		
	}
	else
	{
		if(k-8>=0)
		if(flag[k-8]==0)
		{
			//draw_rect(g,k-8);
			f[i++]=k-8;
		}
		if(k-9>=0)
		if(flag[k-9]!=0 && flag[k-9]<16 && (k-9)/8==k/8-1)
		{
			//draw_rect(g,k-9);
			f[i++]=k-9;

		}
		if(k-7>=0)
		if(flag[k-7]!=0 && flag[k-7]<16 &&(k-7)/8==k/8-1)
		{
			//draw_rect(g,k-7);
			f[i++]=k-7;

		}

		
	}
	if(k/8==0)
	{
		flag[k]=59;
	}
}
private void set_rook(int k)
{
	int m=k+8,i=0;
	int j=k-8,l=k+1;
	//draw_rect(g,k);
	f[i++]=k;
	if(m<64)
	{
		while(m<64 && flag[m]==0)
	{
		//draw_rect(g,m);
		
		f[i++]=m;
		m=m+8;
	}
	}
	if(m<64)
	{
	if( flag[m]!=0 && flag[m]<16)
	{
		//draw_rect(g,m);
		f[i++]=m;
	}
	}
	
	while(j>=0 && flag[j]==0)
	{
		//draw_rect(g,j);
		f[i++]=j;
		j=j-8;
	}
	
	
	if(j>=0&& flag[j]!=0 && flag[j]<16 && j<64)
	{
		//draw_rect(g,j);
		f[i++]=j;
	}
	
	if(l<64)
	{
	while( l<64 &&l/8==k/8 && flag[l]==0 )
	{
		//draw_rect(g,l);
		f[i++]=l;
		l++;
	}
	}
	if(l<64)
	{
	if(l<64 &&flag[l]!=0 && flag[l]<16 && l/8==k/8)
	{
		//draw_rect(g,l);
		f[i++]=l;
	}
	}
	l=k-1;
	if(l>=0)
	{
	while( l>=0 && l/8==k/8 && flag[l]==0 )
	{
		//draw_rect(g,l);
		f[i++]=l;
		l--;
	}
	}
	
	if(l>=0  && flag[l]<16 && l/8==k/8)
	{
		//draw_rect(g,l);
		f[i++]=l;
	}
		
}	

private void set_king(int k)
{
	int i=0,j=k-9;
	//draw_rect(g,k);
	gp1[i]=k;
	f[i++]=k;
	
	if(k-1>=0)
	{
		if((flag[k-1]<16 || flag[k-1]==0)&& (k-1)/8==(k/8))
		{
			//draw_rect(g,k-1);
			f[i++]=k-1;
				
		}
	}
	if(k+1<64)
	{
		if((flag[k+1]<16 || flag[k+1]==0)&& (k+1)/8==(k/8))
		{
			//draw_rect(g,k+1);
			f[i++]=k+1;
				
		}
	}
	
	
		for(int m=0;m<3;m++)
		{
			if( j+m>=0 && (flag[j+m]<16 || flag[j+m]==0)  && (j+m)/8==(k/8-1))
			{
				
				//draw_rect(g,j+m);
				f[i++]=j+m;
				
			}
		}
		
	
	j=k+9;
	
		for(int m=0;m<3;m++)
		{
			if(j-m<64 && (flag[j-m]<16 || flag[j-m]==0) &&  (j-m)/8==(k/8+1))
			{
				
				//draw_rect(g,j-m);
				f[i++]=j-m;

			}
		}
	}
			


private void set_bishop(int k)
{
	int m=k-7,n=1,o=1,p=k+7,i=0,l=7;
	//draw_rect(g,k);
	gp2[i]=k;
	f[i++]=k;
	while(m>=0 && flag[m]==0 && m%8>k%8)
	{
		//draw_rect(g,m);
		f[i++]=m;
		
		m=m-l;
		
	}

	if(m>=0)
	{
	if(flag[m]!=0 && flag[m]<16 && m<64 && m%8>k%8 )
		{
			//draw_rect(g,m);
			f[i++]=m;
		}
	}
	
	while(p<64&& flag[p]==0 && p%8<k%8)
	{
		//draw_rect(g,p);
		f[i++]=p;
		
		p=p+l;
		
	}

	if(p<64)
	{
	if(flag[p]!=0 && flag[p]<16 && p<64 && p%8<k%8 )
		{
			//draw_rect(g,p);
			f[i++]=p;
		}
	}

	
	p=k+9;l=9;
	while(p<64 && flag[p]==0 && p%8>k%8)
	{
		//draw_rect(g,p);
		f[i++]=p;
		
		p=p+l;
		o=0;
	}
	if(p<64)
	{
		if( flag[p]!=0 && flag[p]<16 && p<64 && p%8>k%8)
	
		{
			//draw_rect(g,p);
			f[i++]=p;
		}
	}
	

	m=k-9;l=9;
	while(m>=0 && flag[m]==0 && m%8<k%8)
	{
		//draw_rect(g,m);
		f[i++]=m;
		
		m=m-l;
		o=0;
	}
	if(m>=0)
	{
		if( flag[m]!=0 && flag[m]<16 && m<64 && m%8<k%8)
	
		{
			//draw_rect(g,m);
			f[i++]=m;
		}
	}

}

private void set_queen(int k)
{
	int m=k+8,i=0;
	int j=k-8,l=k+1;
	//draw_rect(g,k);
	f[i++]=k;

	if(m<64)
	{
		while(m<64 && flag[m]==0)
	{
		//draw_rect(g,m);
		
		f[i++]=m;
		m=m+8;
	}
	}
	if(m<64)
	{
	if( m<64 && flag[m]!=0 && flag[m]<16)
	{
		//draw_rect(g,m);
		f[i++]=m;
	}
	}
	if(j>=0)
	{
	while(j>=0 && flag[j]==0)
	{
		//draw_rect(g,j);
		f[i++]=j;
		j=j-8;
	}
	}
	if(j>=0)
	{
	if(flag[j]!=0 && flag[j]<16 && j<64)
	{
		//draw_rect(g,j);
		f[i++]=j;
	}
	}
	if(l<64)
	{
	while( l>=0 && l/8==k/8 && flag[l]==0 )
	{
		//draw_rect(g,l);
		f[i++]=l;
		l++;
	}
	}
	if(l<64)
	{
	if(l<64 &&flag[l]!=0 && flag[l]<16 && l/8==k/8)
	{
		//draw_rect(g,l);
		f[i++]=l;
	}
	}
	l=k-1;
	if(l>=0)
	{
	while( l>=0 &&l/8==k/8 && flag[l]==0 )
	{
		//draw_rect(g,l);
		f[i++]=l;
		l--;
	}
	}
	if(l>=0)
	{
		if(l>=0&& flag[l]!=0 && flag[l]<16 && l/8==k/8)
	
	{
		//draw_rect(g,l);
		f[i++]=l;
	}
	}
	
	 m=k-7;l=7;
	 
	 
	//draw_rect(g,k);
	f[i++]=k;
	while(m>=0 && flag[m]==0 && m%8>k%8)
	{
		//draw_rect(g,m);
		f[i++]=m;
		
		m=m-l;
		
		
	}
	if(m>=0)
	{
		if(flag[m]!=0 && flag[m]<16 && m<64 && m%8>k%8 && m>=0)
	
		{
		//draw_rect(g,m);
		f[i++]=m;
	}
	}
	m=k-9;l=9;
	while(m>=0 && flag[m]==0 && m%8<k%8)
	{
		//draw_rect(g,m);
		f[i++]=m;
		
		m=m-l;
		
	}
	if(m>=0)
	{
		if( flag[m]!=0 && flag[m]<16 && m<64 && m%8<k%8)
	
	{
		//draw_rect(g,m);
		f[i++]=m;
	}
	}
	
	int p=k+7;
	l=7;
	
	while(p<64	&& flag[p]==0 && p%8<k%8)
	{
		//draw_rect(g,p);
		f[i++]=p;
		
		p=p+l;
		
	}

	if(p<64)
	{
	if(flag[p]!=0 && flag[p]<16 && p<64 && p%8<k%8 )
		{
			//draw_rect(g,p);
			f[i++]=p;
		}
	}
	p=k+9;l=9;
	while(p<64 && flag[p]==0 && p%8>k%8)
	{
		//draw_rect(g,p);
		f[i++]=p;
		
		p=p+l;
		
	}
	if(p<64)
	{
		if( flag[p]!=0 && flag[p]<16 && p<64 && p%8>k%8)
	
	{
		//draw_rect(g,p);
		f[i++]=p;
	}
	}


	

}
private void set_knight(int k)
{
	int i=0;
	//draw_rect(g,k);
	f[i++]=k;

	
	if(k-17>=0 && (flag[k-17]==0 || flag[k-17]<16) && (k-17)/8==k/8-2  )
	{
		//draw_rect(g,k-17);
		f[i++]=k-17;
		
	}
	if(k-15>=0 && (flag[k-15]==0 || flag[k-15]<16 )&& (k-15)/8==k/8-2 )
	{
		//draw_rect(g,k-15);
		f[i++]=k-15;
		
	}
	if(k+17<64 && (flag[k+17]==0 || flag[k+17]<16)  && (k+17)/8==k/8+2)
	{
		//draw_rect(g,k+17);
		f[i++]=k+17;
		
	}
	if(k+15<64 && (flag[k+15]==0 || flag[k+15]<16 ) &&(k+15)/8==k/8+2)
	{
		//draw_rect(g,k+15);
		f[i++]=k+15;
		
	}
	
	if(k-10>=0 && (flag[k-10]==0 || flag[k-10]<16)  &&(k-10)/8==k/8-1 )
	{
		//draw_rect(g,k-10);
		f[i++]=k-10;
		
	}
	if(k-6>=0 && (flag[k-6]==0 || flag[k-6]<16 )  &&(k-6)/8==k/8-1)
	{
		//draw_rect(g,k-6);
		f[i++]=k-6;
		
	}
	if(k+10<64 && (flag[k+10]==0 || flag[k+10]<16) && (k+10)/8==k/8+1  )
	{
		//draw_rect(g,k+10);
		f[i++]=k+10;
		
	}
	if(k+6<64 && (flag[k+6]==0 || flag[k+6]<16 ) && ( k+6)/8==k/8+1)
	{
		//draw_rect(g,k+6);
		f[i++]=k+6;
		
	}
	
}
private void draw_rect(Graphics g)
{
	int h,l;
	int i=0;
	while(f[i]!=999)
	{
	
		h=x[f[i]]-45;
		l=y[f[i]]-45;
		
		g.fillRect(h, l, 90, 90);
	
		//g.drawRect(h, l, 90, 90);
	i++;
	}
}



private void set_com_pawn(int k)
{
	int i=0;
	
	f[i++]=k;
	if(k/8==1)
	{
		if(flag[k+8]==0)
		{
			
			f[i++]=k+8;
			if(flag[k+16]==0)
			{
				
				f[i++]=k+16;
			}
		}
		if(k+9<64)
			if(flag[k+9]>=48 && (k+9)/8==k/8+1)
			{
				
				f[i++]=k+9;

			}
			if(k+7<64)
			if(flag[k+7]!=0 && flag[k+7]>=48 && (k+7)/8==k/8+1)
			{
				
				f[i++]=k+7;

			}

		
	}
	else
	{
		
		if(k+8<64 &&flag[k+8]==0)
		{
		
			f[i++]=k+8;
		}
		
		
		if(k+9<64)
		if(flag[k+9]>=48 && (k+9)/8==k/8+1)
		{
			
			f[i++]=k+9;

		}
		if(k+7<64)
		if(flag[k+7]!=0 && flag[k+7]>=48 &&(k+7)/8==k/8+1)
		{
			
			f[i++]=k+7;

		}
	}
	if(k/8==7)
		flag[k]=3;

		
	
}
private void set_com_rook(int k)
{
	int m=k+8,i=0;
	int j=k-8,l=k+1;
	
	f[i++]=k;
	if(m<64)
	{
		while(m<64 && flag[m]==0)
	{
		
		
		f[i++]=m;
		m=m+8;
	}
	}
	if(m<64)
	{
	if( flag[m]!=0 && flag[m]>47)
	{
		
		f[i++]=m;
	}
	}
	if(j>=0)
	{
	while(j>=0 && flag[j]==0)
	{
		
		f[i++]=j;
		j=j-8;
	}
	}
	if(j>=0)
	{
	if(flag[j]!=0 && flag[j]>47 )
	{
		
		f[i++]=j;
	}
	}
	if(l<64)
	{
	while( l<64 &&l/8==k/8 && flag[l]==0 )
	{
		
		f[i++]=l;
		l++;
	}
	}
	if(l<64)
	{
	if(l<64 &&flag[l]!=0 && flag[l]>47 && l/8==k/8)
	{
		
		f[i++]=l;
	}
	}
	l=k-1;
	if(l>=0)
	{
	while( l>=0&&l/8==k/8 && flag[l]==0 )
	{
		//draw_rect(g,l);
		f[i++]=l;
		l--;
	}
	}
	if(l>=0)
	{
		if(l>=0&& flag[l]!=0 && flag[l]>47 && l/8==k/8)
	
	{
		//draw_rect(g,l);
		f[i++]=l;
	}
	}	
}	

private void set_com_king(int k)
{
	int i=0,j=k-9;
	//draw_rect(g,k);
	
	f[i++]=k;
	if(k-1>=0)
	{
		if((flag[k-1]>47 || flag[k-1]==0)&& (k-1)/8==(k/8) )
		{
			//draw_rect(g,k-1);
			f[i++]=k-1;
				
		}
	}
	if(k+1<64)
	{
		if((flag[k+1]>47 || flag[k+1]==0)&& (k+1)/8==(k/8))
		{
			//draw_rect(g,k+1);
			f[i++]=k+1;
				
		}
	}
	
	
		for(int m=0;m<3;m++)
		{
			if(j+m>=0&& (flag[j+m]>47 || flag[j+m]==0) && j<64 && (j+m)/8==(k/8-1))
			{
				
				//draw_rect(g,j+m);
				f[i++]=j+m;
				
			}
		}
		
	
	j=k+9;
	
		for(int m=0;m<3;m++)
		{
			if(j-m<64 &&(flag[j-m]>47 || flag[j-m]==0)  && (j-m)/8==(k/8+1))
			{
				
				//draw_rect(g,j-m);
				f[i++]=j-m;

			}
		}
	}
			


private void set_com_bishop(int k)
{
	int m=k-7,n=1,o=1,p=k+7,i=0,l=7;
	//draw_rect(g,k);
	f[i++]=k;
	while(m>=0 && flag[m]==0 && m%8>k%8)
	{
		//draw_rect(g,m);
		f[i++]=m;
		
		m=m-l;
		
	}

	if(m>=0)
	{
	if(flag[m]!=0 && flag[m]>47 && m<64 && m%8>k%8 )
		{
			//draw_rect(g,m);
			f[i++]=m;
		}
	}
	
	while(p<64&& flag[p]==0 && p%8<k%8)
	{
		//draw_rect(g,p);
		f[i++]=p;
		
		p=p+l;
		
	}

	if(p<64)
	{
	if(flag[p]!=0 && flag[p]>47 && p<64 && p%8<k%8 )
		{
			//draw_rect(g,p);
			f[i++]=p;
		}
	}

	
	p=k+9;l=9;
	while(p<64 && flag[p]==0 && p%8>k%8)
	{
		//draw_rect(g,p);
		f[i++]=p;
		
		p=p+l;
		o=0;
	}
	if(p<64)
	{
		if( flag[p]!=0 && flag[p]>47 && p<64 && p%8>k%8)
	
		{
			//draw_rect(g,p);
			f[i++]=p;
		}
	}
	

	m=k-9;l=9;
	while(m>=0 && flag[m]==0 && m%8<k%8)
	{
		//draw_rect(g,m);
		f[i++]=m;
		
		m=m-l;
		o=0;
	}
	if(m>=0)
	{
		if( flag[m]!=0 && flag[m]>47 && m<64 && m%8<k%8)
	
		{
			//draw_rect(g,m);
			f[i++]=m;
		}
	}

}

private void set_com_queen(int k)
{
	int m=k+8,i=0;
	int j=k-8,l=k+1;
	//draw_rect(g,k);
	f[i++]=k;

	if(m<64)
	{
		while(m<64 && flag[m]==0)
	{
		//draw_rect(g,m);
		
		f[i++]=m;
		m=m+8;
	}
	}
	if(m<64)
	{
	if( m<64&&flag[m]!=0 && flag[m]>47)
	{
		//draw_rect(g,m);
		f[i++]=m;
	}
	}
	if(j>=0)
	{
	while(j>=0 && flag[j]==0)
	{
		//draw_rect(g,j);
		f[i++]=j;
		j=j-8;
	}
	}
	if(j>=0)
	{
	if(flag[j]!=0 && flag[j]>47 && l<64)
	{
		//draw_rect(g,j);
		f[i++]=j;
	}
	}
	if(l<64)
	{
	while( l>=0&&l/8==k/8 && flag[l]==0 )
	{
		//draw_rect(g,l);
		f[i++]=l;
		l++;
	}
	}
	if(l<64)
	{
	if(l<64 &&flag[l]!=0 && flag[l]>47 && l/8==k/8)
	{
		//draw_rect(g,l);
		f[i++]=l;
	}
	}
	l=k-1;
	if(l>=0)
	{
	while( l>=0&&l/8==k/8 && flag[l]==0 )
	{
		//draw_rect(g,l);
		f[i++]=l;
		l--;
	}
	}
	if(l>=0)
	{
		if(l>=0&& flag[l]!=0 && flag[l]>47 && l/8==k/8)
	
	{
		//draw_rect(g,l);
		f[i++]=l;
	}
	}
	
	 m=k-7;l=7;
	 
	 
	//draw_rect(g,k);
	f[i++]=k;
	while(m>=0 && flag[m]==0 && m%8>k%8)
	{
		//draw_rect(g,m);
		f[i++]=m;
		
		m=m-l;
		
		
	}
	if(m>=0)
	{
		if(flag[m]!=0 && flag[m]>47 && m<64 && m%8>k%8 && m>=0)
	
		{
		//draw_rect(g,m);
		f[i++]=m;
	}
	}
	m=k-9;l=9;
	while(m>=0 && flag[m]==0 && m%8<k%8)
	{
		//draw_rect(g,m);
		f[i++]=m;
		
		m=m-l;
		
	}
	if(m>=0)
	{
		if( flag[m]!=0 && flag[m]>47 && m<64 && m%8<k%8)
	
	{
		//draw_rect(g,m);
		f[i++]=m;
	}
	}
	
	int p=k+7;
	l=7;
	
	while(p<64&& flag[p]==0 && p%8<k%8)
	{
		//draw_rect(g,p);
		f[i++]=p;
		
		p=p+l;
		
	}

	if(p<64)
	{
	if(flag[p]!=0 && flag[p]>47 && p<64 && p%8<k%8 )
		{
			//draw_rect(g,p);
			f[i++]=p;
		}
	}
	p=k+9;l=9;
	while(p<64 && flag[p]==0 && p%8>k%8)
	{
		//draw_rect(g,p);
		f[i++]=p;
		
		p=p+l;
		
	}
	if(p<64)
	{
		if( flag[p]!=0 && flag[p]>47 && p<64 && p%8>k%8)
	
	{
		//draw_rect(g,p);
		f[i++]=p;
	}
	}


	

}
private void set_com_knight(int k)
{
	int i=0;
	//draw_rect(g,k);
	f[i++]=k;

	
	if(k-17>=0 && (flag[k-17]==0 || flag[k-17]>47) && (k-17)/8==k/8-2  )
	{
		
		f[i++]=k-17;
		
	}
	if(k-15>=0 && (flag[k-15]==0 || flag[k-15]>47 )&& (k-15)/8==k/8-2 )
	{
		//draw_rect(g,k-15);
		f[i++]=k-15;
		
	}
	if(k+17<64 && (flag[k+17]==0 || flag[k+17]>47)  && (k+17)/8==k/8+2)
	{
		//draw_rect(g,k+17);
		f[i++]=k+17;
		
	}
	if(k+15<64 && (flag[k+15]==0 || flag[k+15]>47 ) &&(k+15)/8==k/8+2)
	{
		//draw_rect(g,k+15);
		f[i++]=k+15;
		
	}
	
	if(k-10>=0 && (flag[k-10]==0 || flag[k-10]>47)  &&(k-10)/8==k/8-1 )
	{
		//draw_rect(g,k-10);
		f[i++]=k-10;
		
	}
	if(k-6>=0 && (flag[k-6]==0 || flag[k-6]>47 )  &&(k-6)/8==k/8-1)
	{
		//draw_rect(g,k-6);
		f[i++]=k-6;
		
	}
	if(k+10<64 && (flag[k+10]==0 || flag[k+10]>47) && (k+10)/8==k/8+1  )
	{
		//draw_rect(g,k+10);
		f[i++]=k+10;
		
	}
	if(k+6<64 && (flag[k+6]==0 || flag[k+6]>47 ) && ( k+6)/8==k/8+1)
	{
		//draw_rect(g,k+6);
		f[i++]=k+6;
		
	}
	
}


}