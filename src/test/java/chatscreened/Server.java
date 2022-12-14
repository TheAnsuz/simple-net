package chatscreened;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.amrv.net.deprecated.EasyThread;
import org.amrv.net.deprecated.PacketString;

/**
 *
 * @author Adrian MRV. aka AMRV || Ansuz
 */
public class Server extends javax.swing.JFrame implements Serializable {

    /**
     * Creates new form Server
     */
    private static final PacketString TRANSFORMER = new PacketString();

    private ServerSocket server;
    private List<Socket> clients = new ArrayList<>();
    private List<OutputStream> outputs = new ArrayList<>();
    private List<InputStream> inputs = new ArrayList<>();

    public Server() {
        initComponents();
        super.setAlwaysOnTop(true);
        try {
            server = new ServerSocket(42069);
            super
                    .setTitle(server.getInetAddress().getHostAddress() + ":" + server
                            .getLocalPort());
            log(Color.GREEN, "Connection started");
        } catch (IOException ex) {
            log(Color.RED, ex.getMessage());
        }

        EasyThread clientListener = new EasyThread(() -> {
            try {
                final Socket client = server.accept();
                outputs.add(client.getOutputStream());
                inputs.add(client.getInputStream());
                clients.add(client);
                System.out.println("Client connected");
                log(Color.GREEN, "Client joined - " + client.getInetAddress()
                        .getHostAddress());
                send("Client connected");
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        });

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        EasyThread clientReciver = new EasyThread(() -> {
            for (int i = 0; i < inputs.size(); i++) {
                buffer.clear();
                try {
                    inputs.get(i).read(buffer.array());
                    log(Color.BLUE, TRANSFORMER.assemble(Arrays
                            .copyOfRange(buffer
                                    .array(), 0, buffer.limit())));
                } catch (IOException ex) {
                    log(Color.RED, ex.getMessage());
                }
            }

        });
    }

    public void send(String text) {
        try {
            for (OutputStream output : outputs) {
                output.write(TRANSFORMER.disassemble(text));
            }
            log(Color.BLUE, text);
        } catch (IOException ex) {
            log(Color.RED, ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jScrollPane1.setFocusable(false);
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jEditorPane1.setContentType("text/html"); // NOI18N
        jEditorPane1.setFocusable(false);
        jEditorPane1.setName("jEditorPane1"); // NOI18N
        jScrollPane1.setViewportView(jEditorPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                send("Hola");
                break;
            case KeyEvent.VK_SPACE:
                send("Buenos dias");
                break;
            case KeyEvent.VK_0: {
                try {
                    final ObjectOutputStream oos = new ObjectOutputStream(System.out);
                    oos.writeObject(Arrays.asList(123123,123123,3241234,234,1,31,3,132,1,4,43,5,345,23,45,23,45,234,52,345,2,345,23,45,234,5));
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }

        }
    }//GEN-LAST:event_formKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    private static final String HTML_START = "<html><head></head><body><p style=\"margin-top: 0\">";
    private static final String HTML_END = "</p></body></html>";

    private StringBuilder logs = new StringBuilder();
//
//    public void write(String text) {
//        logs.setLength(0);
//        jEditorPane1.setText(HTML_START + text + HTML_END);
//        logs.append(text);
//    }
//
//    public void message(String msg) {
//        append("<label style='color: rgb(100,120,245)'>" + msg + "</label><br>");
//    }
//
//    public void error(String err) {
//        append("<label style='color: rgb(240,100,120)'>" + err + "</label><br>");
//    }
//
//    public void append(String text) {
//        logs.append(text);
//        jEditorPane1.setText(HTML_START + logs.toString() + HTML_END);
//    }

    public void log(Color color, String text) {
        String formatted = "<p style='rgb(" + color
                .getRed() + "," + color.getGreen() + "," + color.getBlue() + ")'>" + text + "</p><br>";
        logs.append(formatted);
        jEditorPane1.setText(HTML_START + logs.toString() + HTML_END);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
