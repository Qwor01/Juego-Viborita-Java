
package Paquete;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelSnake extends JPanel {
    
    Color colorsnake=Color.blue;
    Color colorcomida =Color.red;
    int tammax,tam,can,res;
    List<int[]> snake = new ArrayList<>();
    int[] comida =new int[2];
    String direccion = "de";
    String direccionproxima="de";
    
    Thread hilo;
    Caminante camino;
    
    public PanelSnake(int tammax, int can){
        this.tammax=tammax;
        this.can=can;
        this.tam=tammax/can;
        this.res=tammax%can;
        int[] a={can/2-1,can/2-1};
        int[] b={can/2,can/2-1};
        snake.add(a);
        snake.add(b);
        generarcomida();
        
        camino = new Caminante(this);
        hilo = new Thread(camino);
        hilo.start();
        
    }
    @Override
    public void paint(Graphics pintor){
        super.paint(pintor);
        pintor.setColor(colorsnake);
        for(int i =0;i < snake.size(); i ++){
            pintor.fillRect(res/2 + snake.get(i)[0]*tam, res/2 + snake.get(i)[1]*tam, tam -1, tam - 1);
        }
        
//                pintor.fillRect(res/2 + i*tam, res/2 + j*tam, tam -1, tam - 1);
    
        //pintando comida
        pintor.setColor(colorcomida);
        pintor.fillRect(res/2 +comida[0]*tam, res/2 + comida[1]*tam, tam -1, tam - 1);
    }
    
    public void avanzar(){
        int[] ultimo = snake.get(snake.size()-1);
        int agregarx = 0;
        int agregary = 0;
        switch(direccion){
            case "de": agregarx=1;break;
            case "iz": agregarx=-1;break;
            case "ar": agregary=-1;break;
            case "ab": agregary=1;break;                
        }    
        
        int[] nuevo={Math.floorMod(ultimo[0] + agregarx,can) , 
            Math.floorMod(ultimo[1] + agregary, can)};
        boolean existe = false;
        for (int i = 0; i < snake.size(); i++){
            if(nuevo[0]==snake.get(i)[0] && nuevo[1] == snake.get(i)[1]){
                existe=true;
                break;
            }
        }
        if(existe){
            JOptionPane.showMessageDialog(this, "¡Has Perdido!");
        }else{
            if(nuevo[0] == comida[0] && nuevo[1] == comida[1]){
                snake.add(nuevo);
                generarcomida();
            }else{
                snake.add(nuevo);
                snake.remove(0);
            }
        }
        
    }
    
    
    public void generarcomida(){
        boolean existe = false;
        int a=(int)(Math.random()*can);
        int b=(int)(Math.random()*can);
        for(int[] par:snake){
            if(par[0] == a && par[1] == b){
                    existe=true;
                    generarcomida();
                    break;
             }
            }
        if(!existe){
            this.comida[0] = a;
            this.comida[1] = b;
        }   
    }
    
    public void cambiardireccion(String dir){
        if((this.direccion.equals("de") || this.direccion.equals("iz")) && (dir.equals("ar") || dir.equals("ab"))){
            this.direccionproxima= dir;
        } 
        if((this.direccion.equals("ar") || this.direccion.equals("ab")) && (dir.equals("de") || dir.equals("iz"))){
            this.direccionproxima= dir;
        }
        
        
        
        
        igualardir();
    }
    
    public void igualardir(){
        this.direccion = this.direccionproxima;
    }
}
