import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;

// javac -d bin *.java && java -cp bin LabLive

public class LabLive extends JFrame{

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
       LabLive frame = new LabLive("Simple web search");
       frame.setVisible(true);
    }

    private JLabel siteLabel;
    private JTextField siteField;
    private JEditorPane sitePane;

    public LabLive(String title){
        super(title);
        siteLabel = new JLabel("URL:");
        siteField = new JTextField();
        siteField.addActionListener(e->{
            try{
                URL url = new URL(siteField.getText().trim());
                sitePane.setPage(url);
            }catch(IOException ex){}
        });
        sitePane = new JEditorPane();
        sitePane.setEditable(false);
        JPanel panel = new JPanel();
        panel.setLayout( new BorderLayout(3,3) );
        panel.add(siteLabel, BorderLayout.WEST);
        panel.add(siteField,BorderLayout.CENTER);

        JPanel content = new JPanel();
        content.setLayout( new BorderLayout(3,3) );
        content.add(panel, BorderLayout.NORTH);
        content.add(new JScrollPane(sitePane), BorderLayout.CENTER);

        setContentPane(content);
        setBounds(50, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //to go further
        sitePane.addHyperlinkListener(aa->{
            if(aa.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
                try{
                    sitePane.setPage(aa.getURL());
                }catch(IOException e){}
            }
        });
    }



}