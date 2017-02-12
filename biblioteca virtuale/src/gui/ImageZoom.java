package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mortennobel.imagescaling.ResampleOp;

public class ImageZoom {

    private JPanel frmImageZoomIn;
    private  ImageIcon inputImage ; // give image path here
    private JLabel label = null; 
    private double zoom = 1.0;  // zoom factor
    private BufferedImage image = null;
    private JButton zoomIn;
    private JButton zoomOut;
    /**
     * Launch the application.
     */


    /**
     * Create the application.
     * @throws IOException 
     */
    public ImageZoom(ImageIcon im) throws IOException {
    	this.inputImage=im;
    	Image temp = this.inputImage.getImage();
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	GraphicsDevice gs = ge.getDefaultScreenDevice();
    	GraphicsConfiguration gc = gs.getDefaultConfiguration();
    	this.image = gc.createCompatibleImage(temp.getWidth(null),
    	temp.getHeight(null),BufferedImage.TYPE_INT_RGB);
    	this.image.getGraphics().drawImage(this.inputImage.getImage(), 0,0,this.inputImage.getImageObserver());
    	 
    }

    /**
     * Initialize the contents of the frame.
     * @return 
     * @throws IOException 
     */
    public JPanel initialize() throws IOException {
    	
        frmImageZoomIn = new JPanel(new BorderLayout());
        zoomIn=new JButton("+");
        zoomOut=new JButton("-");
        zoomOut.setOpaque(false);
       JPanel panelBut=new JPanel();
        panelBut.setLayout(new FlowLayout());
        panelBut.add(zoomIn);
        panelBut.add(zoomOut);
        
        frmImageZoomIn.add( panelBut,BorderLayout.SOUTH);
       // frmImageZoomIn.setBounds(100, 100, 400, 250);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(600,580));
        
        frmImageZoomIn.add(scrollPane, BorderLayout.CENTER);
        
        //image = ImageIO.read(new File(inputImage));
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        // display image as icon 
        Icon imageIcon = inputImage;
        label = new JLabel( imageIcon );
        panel.add(label, BorderLayout.CENTER);
        
       zoomIn.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			 int notches= -1;
             double temp = zoom - (notches * 0.2);
             // minimum zoom factor is 1.0
             temp = Math.max(temp, 1.0);
             if (temp != zoom) {
                 zoom = temp;
                 resizeImage();
			
		}}
	});
       zoomOut.addActionListener(new ActionListener() {
   		
   		@Override
   		public void actionPerformed(ActionEvent e) {
   			 int notches= +1;
                double temp = zoom - (notches * 0.2);
                // minimum zoom factor is 1.0
                temp = Math.max(temp, 1.0);
                if (temp != zoom) {
                    zoom = temp;
                    resizeImage();
   			
   		}}
   	}); 
       
        scrollPane.setViewportView(panel);
        frmImageZoomIn.setVisible(true);
        return frmImageZoomIn;
    }
    
    public void resizeImage() {
          // System.out.println(zoom);
           ResampleOp  resampleOp = new ResampleOp((int)(this.image.getWidth()*zoom), (int)(this.image.getHeight()*zoom));
               BufferedImage resizedIcon = resampleOp.filter(this.image, null);
           Icon imageIcon = new ImageIcon(resizedIcon);
           label.setIcon(imageIcon);
        }


}