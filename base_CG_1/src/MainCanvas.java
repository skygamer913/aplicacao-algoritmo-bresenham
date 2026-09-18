import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Code2D.Linha2D;
import Code2D.Matrix2D;
import Code2D.Ponto2D;

public class MainCanvas extends JPanel implements Runnable{
	int W = 640;
	int H = 480;
	
	Thread runner;
	boolean ativo = true;
	int paintcounter = 0;
	
	BufferedImage imageBuffer;
	byte bufferDeVideo[];
	
	Random rand = new Random();
	
	byte memoriaPlacaVideo[];
	short paleta[][];
	
	int framecount = 0;
	int fps = 0;
	
	Font f = new Font("", Font.PLAIN, 30);
	
	int clickX = 0;
	int clickY = 0;
	int mouseX = 0;
	int mouseY = 0;
	
	int pixelSize = 0;
	int Largura = 0;
	int Altura = 0;
	
	BufferedImage imgtmp = null;
	
	float posx = 00;
	float posy = 00;
	
	boolean LEFT = false;
	boolean RIGHT = false;
	boolean UP = false;
	boolean DOWN = false;
	
	float filtroR = 1;
	float filtroG = 1;
	float filtroB = 1;
	
	Linha2D[] wBorders = {
		new Linha2D(0, 0, 640, 0),
		new Linha2D(0, 0, 0, 480),
		new Linha2D(640, 0, 640, 480),
		new Linha2D(0, 480, 640, 480)
	};
	
	Linha2D linhaPreview = null;

	Matrix2D transform2D = new Matrix2D();
	
	Ponto2D pC = new Ponto2D(320, 240);
	Ponto2D pM = new Ponto2D(mouseX, mouseY);
	Ponto2D p0 = null;
	Ponto2D p1 = null;
	
	ArrayList<Linha2D> linhas = new ArrayList<>();
	
	public MainCanvas() {
		
		File f = new File("CG_1/imgbmp.bmp");
		try (java.io.FileInputStream fin = new java.io.FileInputStream(f)) {

			byte todosodbytes[] = new byte[64000];
			int byteslidos = fin.read(todosodbytes);
			System.out.println("Bytes Lidos "+byteslidos);
			for(int i = 0; i < byteslidos;i++) {
				System.out.println(i+": "+todosodbytes[i]);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		linhas.add(new Linha2D(200, 150, 350, 250));
		// linhas.add(new Linha2D(250, 350, 100, 50));
		// linhas.add(new Linha2D(75, 200, 250, 100));
		// linhas.add(new Linha2D(600, 50, 300, 400));
		
		setSize(640,480);
		setFocusable(true);
		
		Largura = 640;
		Altura = 480;
		
		pixelSize = 640*480;
		
		imgtmp = loadImage("CG_1/imgbmp.bmp");
		
		imageBuffer = new BufferedImage(640,480, BufferedImage.TYPE_4BYTE_ABGR);
		//imageBuffer.getGraphics().drawImage(imgtmp, 0, 0, null);
		
		
		bufferDeVideo = ((DataBufferByte)imageBuffer.getRaster().getDataBuffer()).getData();
		
		System.out.println("Buffer SIZE "+bufferDeVideo.length );
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_W) {
					UP = false;
				}
				if(key == KeyEvent.VK_S) {
					DOWN = false;
				}
				if(key == KeyEvent.VK_A) {
					LEFT = false;
				}
				if(key == KeyEvent.VK_D) {
					RIGHT = false;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				//System.out.println("CLICO "+key);
				if(key == KeyEvent.VK_W) {
					UP = true;
					for(Linha2D l : linhas) {
						l.translate(0, -10);
					}
				}
				if(key == KeyEvent.VK_S) {
					DOWN = true;
					for(Linha2D l : linhas) {
						l.translate(0, 10);
					}
				}
				if(key == KeyEvent.VK_A) {
					LEFT = true;
					for(Linha2D l : linhas) {
						l.translate(-10, 0);
					}
				}
				if(key == KeyEvent.VK_D) {
					RIGHT = true;
					for(Linha2D l : linhas) {
						l.translate(10, 0);
					}
				}
				if(key == KeyEvent.VK_Q) {
					for(Linha2D l : linhas) {
						l.translate(-pC.getpX(), -pC.getpY());
						l.rotate(-15);
						l.translate(pC.getpX(), pC.getpY());
					}
				}
				if(key == KeyEvent.VK_E) {
					for(Linha2D l : linhas) {
						l.translate(-pC.getpX(), -pC.getpY());
						l.rotate(15);
						l.translate(pC.getpX(), pC.getpY());
					}
				}
				if(key == KeyEvent.VK_M) {
					for(Linha2D l : linhas) {
						l.translate(-pC.getpX(), -pC.getpY());
						l.scale(1.5f, 1.5f);
						l.translate(pC.getpX(), pC.getpY());
					}
				}
				if(key == KeyEvent.VK_N) {
					for(Linha2D l : linhas) {
						l.translate(-pC.getpX(), -pC.getpY());
						l.scale(0.5f, 0.5f);
						l.translate(pC.getpX(), pC.getpY());
					}
				}
			}
		});		
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				clickX = e.getX();
				clickY = e.getY();
				
				if(e.getButton() == 1) {
					// Função para criar novas linhas
					if(p0 == null) {
						p0 = new Ponto2D(clickX, clickY);
					} else {
						p1 = new Ponto2D(clickX, clickY);
						linhas.add(new Linha2D(p0, p1));
						p0 = null;
					}
				}
				if (e.getButton() == 3) {
					pC = new Ponto2D(clickX, clickY);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mouseX = arg0.getX();
				mouseY = arg0.getY();
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		

		
	}
	private void drawImageToBuffer(BufferedImage image,int x,int y, float fr, float fg, float fb) {
		byte[] imgBuffer = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
		
		
		int iw = image.getWidth();
		int ih = image.getHeight();
		
		for(int yi = 0; yi < ih; yi++) {
			for(int xi = 0; xi < iw; xi++) {
				int pixi = yi*iw*4 + xi*4;
				int pixb = (yi+y)*W*4 + (xi+x)*4;
				bufferDeVideo[pixb] = imgBuffer[pixi];
				
				//BW
//				int soma = (imgBuffer[pixi+1]&0xff) + (imgBuffer[pixi+2]&0xff) + (imgBuffer[pixi+3]&0xff);
//				int res = (int)(soma/3);
//				
//				bufferDeVideo[pixb+1] = (byte)(res&0xff);
//				bufferDeVideo[pixb+2] = (byte)(res&0xff);
//				bufferDeVideo[pixb+3] = (byte)(res&0xff);
				
				
				int b = (imgBuffer[pixi+1]&0xff);
				int g =	(imgBuffer[pixi+2]&0xff);
				int r = (imgBuffer[pixi+3]&0xff);
				
				b = (int)(b*fb);
				g = (int)(g*fg);
				r = (int)(r*fr);
				
				b = Math.min(255, b);
				g = Math.min(255, g);
				r = Math.min(255, r);
				
				bufferDeVideo[pixb+1] = (byte)(b&0xff);
				bufferDeVideo[pixb+2] = (byte)(g&0xff);
				bufferDeVideo[pixb+3] = (byte)(r&0xff);
			}
		}
	}
	@Override
	public void paint(Graphics g) {
		
		for(int i = 0; i < bufferDeVideo.length; i++) {
			bufferDeVideo[i] = 0;
		}
		
		//drawImageToBuffer(imgtmp,(int)posx,(int)posy,filtroR,filtroG,filtroB);
		for(Linha2D linha : linhas) {
			desenhaLinha(linha, 255, 0, 0);
		}
		
		if(p0 != null) {
			pM.updatePoint(mouseX, mouseY);
			linhaPreview = new Linha2D(p0, pM);
			desenhaLinha(linhaPreview, 0, 0, 255);
		}
		
		if(pC != null) {
			desenhaPixel(pC.getpX(), pC.getpY(), 255, 0, 0);
		}
		
		g.setFont(f);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 640, 480);
		g.drawImage(imageBuffer, 0, 0, null);

		g.setColor(Color.black);
		g.drawString("FPS "+fps+" mouse: "+mouseX+","+mouseY, 10, 25);
	}
	
	// Procedimento de Bresenham para desenhar uma linha entre os pontos (x1, y1) e (x2, y2)
	private void bresenhamAlgorithmPos(int x1, int y1, int x2, int y2) {
		int dirX = 1;

		if(x1 > x2) {  // Se o x1 > x2, a linha vai avançar para a esquerda
			dirX = -1; 
		}

		int pospix = y1*(W*4)+x1*4;
		int m = 2 * (y2-y1);
		int slope_error = m - (x2-x1);

		for(int x = x1; x != x2; x+=dirX) {
			// Desenhando o pixel
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)255;
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)0;
			
			pospix += 4 * dirX;  // Avançando o pixel horizontalmente

			slope_error += m;  // Atualizando o erro da inclinação
			
			// Se o erro da inclinação for maior ou igual a 0, avançamos o pixel verticalmente
			if(slope_error >= 0) {
				pospix += W * 4; // Avançando o pixel verticalmente
				slope_error -= 2 * (x2-x1);  // Resetando o erro da inclinação
			}
		}
	}
	
	// Procedimento de Bresenham para desenhar uma linha entre os pontos (x1, y1) e (x2, y2)
	private void bresenhamAlgorithmNeg(int x1, int y1, int x2, int y2, int r, int g, int b) {
		int dirX = 1;

		if(x1 > x2) {  // Se o x1 > x2, a linha vai avançar para a esquerda
			dirX = -1; 
		}

		int pospix = y1*(W*4)+x1*4;
		int m = 2 * (y2-y1);
		int slope_error = m - (x2-x1);

		for(int x = x1; x != x2; x+=dirX) {
			// Desenhando o pixel
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)(b&0xff);
			bufferDeVideo[pospix+2] = (byte)(g&0xff);
			bufferDeVideo[pospix+3] = (byte)(r&0xff);
			
			pospix += 4 * dirX;  // Avançando o pixel horizontalmente

			slope_error += m;  // Atualizando o erro da inclinação
			
			// Se o erro da inclinação for maior ou igual a 0, avançamos o pixel verticalmente
			if(slope_error <= 0) {
				pospix -= W * 4; // Avançando o pixel verticalmente
				slope_error -= 2 * (x2-x1);  // Resetando o erro da inclinação
			}
		}
	}

	public void desenhaLinha(Linha2D linha, int r, int g, int b) {
		Ponto2D pa = linha.getp0();
		Ponto2D pb = linha.getp1();
		
		if(pa.isValidCoordenates(W, H) && pb.isValidCoordenates(W, H)) {
			if(pa.getpY() <= pb.getpY()) {
				bresenhamAlgorithmPos(pa.getpX(), pa.getpY(), pb.getpX(), pb.getpY(), r, g, b);
			} else {
				bresenhamAlgorithmNeg(pa.getpX(), pa.getpY(), pb.getpX(), pb.getpY(), r, g, b);
			}
		}
	}
	
	public void desenhaLinhaHorizontal(int x, int y,int w) {
		int pospix = y*(W*4)+x*4;
		
		for(int i = 0; i < w;i++) {
			
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)0;
			pospix+=4;
		}
	}
	
	public void desenhaLinhaVertical(int x, int y,int h) {
		int pospix = y*(W*4)+x*4;
		
		for(int i = 0; i < h;i++) {
			
			bufferDeVideo[pospix] = (byte)255;
			bufferDeVideo[pospix+1] = (byte)0;
			bufferDeVideo[pospix+2] = (byte)0;
			bufferDeVideo[pospix+3] = (byte)255;
			pospix+=(W*4);
		}
	}
	
	public void desenhaPixel(int x, int y,int r,int g,int b) {
		int pospix = y*(W*4)+x*4;
			
		bufferDeVideo[pospix] = (byte)255;
		bufferDeVideo[pospix+1] = (byte)(b&0xff);
		bufferDeVideo[pospix+2] = (byte)(g&0xff);
		bufferDeVideo[pospix+3] = (byte)(r&0xff);
	
	}
	
	public void start(){
		runner = new Thread(this);
		runner.start();
	}
	
	int timer = 0;
	public void simulaMundo(long diftime){
		
		float difS = diftime/1000.0f;
		float vel = 50;
		
		timer+=diftime;
		if(timer>=1000) {
			timer = 0;
			filtroR = rand.nextFloat();
			filtroG = rand.nextFloat();
			filtroB = rand.nextFloat();
		}
		
		if(UP) {
			posy -= vel*difS;
		}
		if(DOWN) {
			posy += vel*difS;
		}
		if(LEFT) {
			posx -= vel*difS;
		}
		if(RIGHT) {
			posx += vel*difS;
		}
	}
	
	
	@Override
	public void run() {
		long time = System.currentTimeMillis();
		long segundo = time/1000;
		long diftime = 0;
		while(ativo){
			simulaMundo(diftime);
			paintImmediately(0, 0, 640, 480);
			paintcounter+=100;
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long newtime = System.currentTimeMillis();
			long novoSegundo = newtime/1000;
			diftime = System.currentTimeMillis() - time;
			time = System.currentTimeMillis();
			framecount++;
			if(novoSegundo!=segundo) {	
				fps = framecount;
				framecount = 0;
				segundo = novoSegundo;
			}
		}
	}
	
	public BufferedImage loadImage(String filename) {
		try {
			imgtmp = ImageIO.read(new File(filename));
			
			BufferedImage imgout = new BufferedImage(imgtmp.getWidth(), imgtmp.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			
			imgout.getGraphics().drawImage(imgtmp, 0, 0, null);
			
			imgtmp = null;
			
			return imgout;
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}
